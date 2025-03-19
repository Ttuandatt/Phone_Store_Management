package GUI;

import BUS.ChucVuBUS;
import BUS.KhoBUS;
import BUS.NhanVienBUS;
import BUS.ProductsBUS;
import DTO.*;
import DAO.ProductsDAO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import Components.ImageRenderer;
import Components.ShadowButton;


public class NhanVienGUI extends JPanel{
	
	NhanVienBUS nvBUS = new NhanVienBUS();
	ChucVuBUS cvBUS = new ChucVuBUS();
	KhoBUS khoBUS = new KhoBUS();
    JTable employeeTable;
    DefaultTableModel employeeModel = new DefaultTableModel();
    ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>(); 
    JComboBox<String> sortComboBox, genderCombobox, roleCombobox, workplaceCombobox;
    ArrayList<ChucVuDTO> arrChucVu = cvBUS.selectAll();
    ArrayList<KhoDTO> arrNoiLamViec = khoBUS.selectAll();
    String[] roles = new String[arrChucVu.size()];
    String[] workplaces =  new String[arrNoiLamViec.size()];
    JPanel productContent;
    JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
	
	final byte[][] imageBytes = new byte[1][];
	String selectedFilePathName;	//biến lưu đường dẫn của ảnh được chọn
	
	//Constructor
    public NhanVienGUI(){
        initComponents();
        loadEmployeeList();
        for(String kho: workplaces) {
        	System.out.println(kho);
        }
        
        for(String cv: roles) {
        	System.out.println(cv);
        }
    }
    
    
    private void initComponents() {
        setLayout(new GridBagLayout()); //set Layout
        GridBagConstraints gbc = new GridBagConstraints();
        productContent = new JPanel();
        productContent.setBackground(Color.white);
        productContent.setLayout(new GridBagLayout());
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(productContent, gbc); // Thêm vào ProductsGUI
        
        //tạo 2 panel topPanel, bottomPanel cho khu vực tìm kiếm và khu vực hiển thị bảng danh sách
        JPanel topPanel, bottomPanel;
        //set thông số cho 2 panel
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(Color.white);
        gbc.weightx = 1.0;
        gbc.weighty = 0.23;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productContent.add(topPanel, gbc);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.setBackground(Color.white);
        bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 2)));
        gbc.weightx = 1.0;
        gbc.weighty = 0.77;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        productContent.add(bottomPanel, gbc);
        
//==================================================== TOP PANEL =============================================================================================//
        JPanel functionsPanel, searchPanel;
        
        
        //======================================= functionsPanel =====================================================//
        //set thông số cho functionsPanel
        functionsPanel = new JPanel();
        functionsPanel.setBackground(Color.white);
        functionsPanel.setLayout(new GridBagLayout());
        functionsPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));	//Tạo border cho panel
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(functionsPanel,gbc);
        
        //chia 2 panel con nữa, leftFunctionPanel cho các nút chức năng, rightFunctionPanel cho nút xuất Excel
        JPanel leftFunctionPanel, rightFunctionPanel;
        
        leftFunctionPanel = new JPanel();
        leftFunctionPanel.setBackground(Color.white);
        leftFunctionPanel.setLayout(null);
        gbc.weightx = 0.71;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        functionsPanel.add(leftFunctionPanel, gbc);
        
        rightFunctionPanel = new JPanel();
        rightFunctionPanel.setBackground(Color.white);
        rightFunctionPanel.setLayout(null);
        gbc.weightx = 0.29;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        functionsPanel.add(rightFunctionPanel, gbc);
        
        //Chia tiếp các panel con để chứa các nút chức năng ở leftFunctionPanel
        JPanel addButtonPanel, updateButtonPanel, deleteButtonPanel, detailButtonPanel, excelButtonPanel, printButtonPanel;
        
        addButtonPanel = new JPanel();
        addButtonPanel.setBackground(Color.white);
        addButtonPanel.setBounds(10, 4, 60, 60);
        leftFunctionPanel.add(addButtonPanel);
        
        updateButtonPanel = new JPanel();
        updateButtonPanel.setBackground(Color.white);
        updateButtonPanel.setBounds(79, 4, 60, 60);
        leftFunctionPanel.add(updateButtonPanel);
        
        deleteButtonPanel = new JPanel();
        deleteButtonPanel.setBackground(Color.white);
        deleteButtonPanel.setBounds(148, 4, 60, 60);
        leftFunctionPanel.add(deleteButtonPanel);
        
        detailButtonPanel = new JPanel();
        detailButtonPanel.setBackground(Color.white);
        detailButtonPanel.setBounds(217, 4, 60, 60);
        leftFunctionPanel.add(detailButtonPanel);
        
        excelButtonPanel = new JPanel();
        excelButtonPanel.setBackground(Color.white);
        excelButtonPanel.setBounds(0, 4, 60, 60);
        rightFunctionPanel.add(excelButtonPanel);
        
        printButtonPanel = new JPanel();
        printButtonPanel.setBackground(Color.white);
        printButtonPanel.setBounds(69, 4, 60, 60);
        rightFunctionPanel.add(printButtonPanel);
        
        //======================================= Đặt các nút chức năng vào các panel ==========================================================//
        ImageIcon iconAdd = new ImageIcon(getClass().getResource("/img/plus.png"));
        Image imgAdd = iconAdd.getImage();
        Image newImgAdd = imgAdd.getScaledInstance(30,30, Image.SCALE_SMOOTH);
        if(iconAdd.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconAdd = new ImageIcon(newImgAdd);
        // Tạo nút Add
        JButton btnAdd = new ShadowButton("Thêm", scaledIconAdd);
        btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(true);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Update
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEmployeeDialog();
            }
        });
        btnAdd.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		btnAdd.setBackground(Color.decode("#D6D6D6")); // Đổi màu khi hover vào
        		btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		btnAdd.setBackground(Color.white);
        		addButtonPanel.setBackground(Color.white);
        	}
        });

        // Thêm nút vào panel
        addButtonPanel.setLayout(new BorderLayout());
        addButtonPanel.add(btnAdd, BorderLayout.CENTER);		
        
        
        
        
        //Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
        ImageIcon iconUpdate = new ImageIcon(getClass().getResource("/img/update.png"));
        Image imgUpdate = iconUpdate.getImage();
        Image newImgUpdate = imgUpdate.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        if (iconUpdate.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconUpdate = new ImageIcon(newImgUpdate);

        // Tạo nút Update
        JButton btnUpdate = new ShadowButton("Sửa", scaledIconUpdate);
        btnUpdate.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnUpdate.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setBorderPainted(true);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Update
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	updateEmployeeDialog();
            }
        });
        btnUpdate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		btnUpdate.setBackground(Color.decode("#D6D6D6")); // Đổi màu khi hover vào
        		btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		btnUpdate.setBackground(Color.white);
        		updateButtonPanel.setBackground(Color.white);
        	}
        });

        // Thêm nút vào panel
        updateButtonPanel.setLayout(new BorderLayout());
        updateButtonPanel.add(btnUpdate, BorderLayout.CENTER);
        
        
        //Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
        ImageIcon iconDelete = new ImageIcon("C:\\\\Users\\\\ACER\\\\Dropbox\\\\My PC (LAPTOP-UGP9QJUT)\\\\Documents\\\\ITstudies\\\\JAVA_BACKEND\\\\JAVA PROJECTS\\\\Phone_Store_Management_HTTTDN\\\\Phone_Store_Management\\\\src\\\\img\\\\delete.png\\"); // Đặt đường dẫn ảnh ở đây
        Image imgDelete = iconDelete.getImage();
        Image newImgDelete = imgDelete.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        if (iconDelete.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconDelete = new ImageIcon(newImgDelete);

        // Tạo nút Delete
        JButton btnDelete = new ShadowButton("Xóa", scaledIconDelete);
        btnDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(true);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Delete
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Delete button clicked!");
            }
        });
        btnDelete.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		btnDelete.setBackground(Color.decode("#D6D6D6")); // Đổi màu khi hover vào
        		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		btnDelete.setBackground(Color.white);
        		deleteButtonPanel.setBackground(Color.white);
        	}
        });
        
        // Thêm nút vào panel
        deleteButtonPanel.setLayout(new BorderLayout());
        deleteButtonPanel.add(btnDelete, BorderLayout.CENTER);
        
        
        
        
     
        //Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
        ImageIcon iconDetail = new ImageIcon(getClass().getResource("/img/info.png")); // Đặt đường dẫn ảnh ở đây
        Image imgDetail = iconDetail.getImage();
        Image newImgDetail = imgDetail.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        if (iconDelete.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconDetail = new ImageIcon(newImgDetail);

        // Tạo nút Detail
        JButton btnDetail = new ShadowButton("Xem", scaledIconDetail);
        btnDetail.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnDetail.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDetail.setFocusPainted(false);
        btnDetail.setBorderPainted(true);
        btnDetail.setContentAreaFilled(false);
        btnDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetail.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Detail
        btnDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeDetailDialog();
            }
        });
        btnDetail.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		btnDetail.setBackground(Color.decode("#D6D6D6")); // Đổi màu khi hover vào
        		btnDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		btnDetail.setBackground(Color.white);
        		detailButtonPanel.setBackground(Color.white);
        	}
        });
        

        // Thêm nút vào panel
        detailButtonPanel.setLayout(new BorderLayout());
        detailButtonPanel.add(btnDetail, BorderLayout.CENTER);

        
        //Nút Xuất Excel
        //Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
        ImageIcon iconExcel = new ImageIcon(getClass().getResource("/img/excel.png")); // Đặt đường dẫn ảnh ở đây
        Image imgExcel = iconExcel.getImage();
        Image newImgExcel = imgExcel.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        if (iconExcel.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconExcel = new ImageIcon(newImgExcel);

        // Tạo nút Detail
        JButton btnExcel = new ShadowButton("Excel", scaledIconExcel);
        btnExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExcel.setHorizontalTextPosition(SwingConstants.CENTER);
//        btnExcel.setFocusPainted(false);
        btnExcel.setBorderPainted(true);
        btnExcel.setContentAreaFilled(false);
        btnExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExcel.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Detail
        btnExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Excel button clicked!");
            }
        });
        btnExcel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		btnExcel.setBackground(Color.decode("#D6D6D6")); // Đổi màu khi hover vào
        		btnExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		btnExcel.setBackground(Color.white);
        		excelButtonPanel.setBackground(Color.white);
        	}
        });
        

        // Thêm nút vào panel
        excelButtonPanel.setLayout(new BorderLayout());
        excelButtonPanel.add(btnExcel, BorderLayout.CENTER);
        
        
		// Nút Print
		// Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
		ImageIcon iconPrint = new ImageIcon(getClass().getResource("/img/printer.png")); // Đặt đường dẫn ảnh ở đây
		Image imgPrint = iconPrint.getImage();
		Image newImgPrint = imgPrint.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		if (iconPrint.getIconWidth() == -1) {
			System.out.println("Không tìm thấy ảnh!");
		}
		ImageIcon scaledIconPrint = new ImageIcon(newImgPrint);

		// Tạo nút Detail
		JButton btnPrint = new ShadowButton("In", scaledIconPrint);
		btnPrint.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPrint.setHorizontalTextPosition(SwingConstants.CENTER);
//        btnExcel.setFocusPainted(false);
		btnPrint.setBorderPainted(true);
		btnPrint.setContentAreaFilled(false);
		btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPrint.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

		// Thêm sự kiện click cho nút Print
		btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Excel button clicked!");
			}
		});
		btnPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnPrint.setBackground(Color.decode("#D6D6D6")); // Đổi màu khi hover vào
				btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnPrint.setBackground(Color.white);
				printButtonPanel.setBackground(Color.white);
			}
		});

		// Thêm nút vào panel
		printButtonPanel.setLayout(new BorderLayout());
		printButtonPanel.add(btnPrint, BorderLayout.CENTER);

        
        
        
        
        //======================================= seacrhPanel ========================================================//
        //set thông số cho seacrhPanel
        searchPanel = new JPanel();
        searchPanel.setBackground(Color.white);
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 2)));	//Tạo border cho panel        
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        topPanel.add(searchPanel,gbc);
        
        //chia 2 panel con nữa: searchInputPanel & searchButtonPanel. searchButtonPanel để chứa nút tìm kiềm và refresh
        JPanel searchInputPanel, searchButtonPanel;
        
        searchInputPanel = new JPanel();
        searchInputPanel.setBackground(Color.white);
        searchInputPanel.setLayout(null);
        gbc.weightx = 0.87;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(searchInputPanel, gbc);
        
        searchButtonPanel = new JPanel();
        searchButtonPanel.setBackground(Color.white);
        searchButtonPanel.setLayout(null);
        gbc.weightx = 0.13;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(searchButtonPanel, gbc);
        
        
        
        
        //==================================== searchInputPanel =======================================================//
        String[] sortCriterias = {"Tất cả", "A-Z", "Z-A", "Tăng dần", "Giảm dần"};
        sortComboBox = new JComboBox<String>(sortCriterias);
        sortComboBox.setBounds(10, 24, 75, 25);
        searchInputPanel.add(sortComboBox);
        
        
        String[] genders = {"Giới tính", "Nam", "Nữ"};
        genderCombobox = new JComboBox<String>(genders);
        genderCombobox.setBounds(90,  24,  90, 25);
        searchInputPanel.add(genderCombobox);
        
     
        ArrayList<ChucVuDTO> arrChucVu = cvBUS.selectAll();
        roles[0] = "Chức vụ";
        for(int i=0; i<arrChucVu.size(); i++) {        		
        	roles[i] =  arrChucVu.get(i).getTenCV();
        }
        roleCombobox = new JComboBox<String>(roles);
        roleCombobox.setBounds(185, 24, 140, 25);
    	fillRoleCombobox(roleCombobox);
        searchInputPanel.add(roleCombobox);
        roleCombobox.addItemListener(e -> {
        	if(e.getStateChange() == ItemEvent.SELECTED) {
        		String selected = (String)roleCombobox.getSelectedItem();
        		if("Thêm chức vụ...".equals(selected)) {
        			newRoleDialog();
        		}
        	}
        	
        });

        
        JTextField searchInputTF = new JTextField();
        searchInputTF.setBounds(375,  24,  260, 25);
        searchInputPanel.add(searchInputTF);
        
        
        
        //==================================== searchButtonPanel =======================================================//
        ImageIcon iconSearch = new ImageIcon("C:\\Users\\ACER\\Dropbox\\My PC (LAPTOP-UGP9QJUT)\\Documents\\ITstudies\\JAVA_BACKEND\\JAVA PROJECTS\\Phone_Store_Management_HTTTDN\\Phone_Store_Management\\src\\img\\loupe2.png"); // Đặt đường dẫn ảnh ở đây
        Image imgSearch = iconSearch.getImage();
        Image newImgSearch = imgSearch.getScaledInstance(20,20, Image.SCALE_SMOOTH);
        if (iconSearch.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconSearch = new ImageIcon(newImgSearch);

        // Tạo nút Detail
        JButton btnSearch = new ShadowButton(scaledIconSearch);
        btnSearch.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSearch.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(true);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setBackground(Color.white);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 9
        
        
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Search button clicked!");
            }
        });
        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSearch.setBackground(Color.decode("#D6D6D6")); // Màu khi hover vào
                btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSearch.setBackground(Color.white); // Màu khi hover ra
            }
        });

        
        btnSearch.setBounds(0, 15, 40, 40);
        searchButtonPanel.add(btnSearch);
        
        ImageIcon iconRefresh = new ImageIcon("C:\\Users\\ACER\\Dropbox\\My PC (LAPTOP-UGP9QJUT)\\Documents\\ITstudies\\JAVA_BACKEND\\JAVA PROJECTS\\Phone_Store_Management_HTTTDN\\Phone_Store_Management\\src\\img\\refresh.png"); // Đặt đường dẫn ảnh ở đây
        Image imgRefresh = iconRefresh.getImage();
        Image newImgRefresh = imgRefresh.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        if (iconRefresh.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconRefresh = new ImageIcon(newImgRefresh);

        // Tạo nút Detail
        JButton btnRefresh = new ShadowButton(scaledIconRefresh);
        btnRefresh.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnRefresh.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(true);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setBackground(Color.white);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 8)); // Đặt kích cỡ chữ là 10

        
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	refreshList();
            }
        });
        btnRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnRefresh.setBackground(Color.decode("#D6D6D6")); // Màu khi hover vào
            	btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	btnRefresh.setBackground(Color.white); // Màu khi hover ra
            }
        });

        
        btnRefresh.setBounds(45, 15, 40, 40);
        searchButtonPanel.add(btnRefresh);


        
        
        
        
//====================================================================== BOTTOM PANEL ==============================================================//

        //========================= table =========================//
        employeeTable = new JTable();
        JScrollPane sp = new JScrollPane(employeeTable);
        gbc.weightx = 0.55;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(sp, gbc);
		
		JPanel attendancePanel = new JPanel(new GridBagLayout());
		attendancePanel.setBackground(Color.white);
//		attendancePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 2)));
		gbc.weightx = 0.45;
		gbc.weighty = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(attendancePanel, gbc);
		
		//Chia 2 panel top & bottom trong attendancePanel
		JPanel topAttendancePanel, bottomAttendancePanel;
		
		topAttendancePanel = new JPanel(null);
		topAttendancePanel.setBackground(Color.white);
		topAttendancePanel.setBorder(BorderFactory.createTitledBorder(""));
		gbc.weightx = 1.0;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		attendancePanel.add(topAttendancePanel, gbc);
		
		bottomAttendancePanel = new JPanel();
		bottomAttendancePanel.setBackground(Color.white);
		bottomAttendancePanel.setBorder(BorderFactory.createTitledBorder("Chi tiết"));
		gbc.weightx = 1.0;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		attendancePanel.add(bottomAttendancePanel, gbc);
		
		//topAttendancePanel
		JLabel lblSoNgayCong, lblSoNgayNghiPhep, lblSoNgayNghiKhongPhep, lblSoGioOT;
		
		lblSoNgayCong = new JLabel("Số ngày công: ");
		lblSoNgayCong.setBounds(10, 10, 100, 20);
		topAttendancePanel.add(lblSoNgayCong);
		
		lblSoNgayNghiPhep = new JLabel("Số ngày nghỉ phép: ");
		lblSoNgayNghiPhep.setBounds(10, 40, 150, 20);
		topAttendancePanel.add(lblSoNgayNghiPhep);
		
		lblSoNgayNghiKhongPhep = new JLabel("Số ngày nghỉ không phép: ");
		lblSoNgayNghiKhongPhep.setBounds(10, 70, 150, 20);
		topAttendancePanel.add(lblSoNgayNghiKhongPhep);
		
		lblSoGioOT = new JLabel("Số giờ tăng ca: ");
		lblSoGioOT.setBounds(10, 100, 150, 20);
		topAttendancePanel.add(lblSoGioOT);
				

    }

    
    private void loadEmployeeList() {
    	employeeTable.setDefaultEditor(Object.class, null); // không cho click vào & edit nội dung các cell trong bảng
    	employeeModel  = new DefaultTableModel();
    	employeeTable.setModel(employeeModel);
    	employeeModel.addColumn("ID");
    	employeeModel.addColumn("Họ và tên");
    	employeeModel.addColumn("Ngày sinh");
    	employeeModel.addColumn("Địa chỉ");
    	employeeModel.addColumn("Chức vụ");
    	employeeModel.addColumn("Mật khẩu");
    	employeeModel.addColumn("Trạng thái");
    	employeeModel.addColumn("Hình ảnh");

		arrNhanVien = nvBUS.selectAll();
		for(int i=0; i<arrNhanVien.size(); i++) {
			NhanVienDTO nv = arrNhanVien.get(i);
			String maNV = nv.getMaNV();
			String hoTen = nv.getHoTen();
			Date ngaySinh = nv.getNgaySinh();
			String diaChi = nv.getDiaChi();
			String chucVu = nv.getChucVu();
			String matKhau = nv.getMatKhau();
			String trangThai = nv.getTrangThai();
			// Lấy dữ liệu ảnh từ database (kiểu VARBINARY)
		    byte[] imageData = nv.getHinhAnh(); // Phương thức này phải trả về byte[]
		    ImageIcon imageIcon = null;
		    if (imageData != null) {
		        // Chuyển đổi byte[] thành ImageIcon
		        Image image = Toolkit.getDefaultToolkit().createImage(imageData);
		        Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize ảnh
		        imageIcon = new ImageIcon(scaledImage);
		    }
			
			
		    Object[] row = {maNV, hoTen, ngaySinh, diaChi, chucVu, matKhau, trangThai, imageIcon};
			employeeModel.addRow(row);
		}
		
		
		//Điều chỉnh kích thước các cột
		TableColumnModel tcm = employeeTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(50);
		tcm.getColumn(1).setPreferredWidth(150);
		tcm.getColumn(2).setPreferredWidth(100);
		tcm.getColumn(3).setPreferredWidth(200);
		tcm.getColumn(4).setPreferredWidth(90);
		tcm.getColumn(5).setPreferredWidth(90);
		tcm.getColumn(6).setPreferredWidth(70);
		tcm.getColumn(7).setPreferredWidth(128);
		// **Thêm ImageRenderer vào cột "Hình ảnh"**
	    employeeTable.getColumnModel().getColumn(7).setCellRenderer(new ImageRenderer());


		employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize
    }
    
    public void newRoleDialog() {
    	//Tạo Jpanel chứa form nhập
    	JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5)); // row:3, column:2, hgap:5, wgap:5
    	
    	JLabel idLabel = new JLabel("Mã chức vụ:");
		JTextField idField = new JTextField(15);

    	
    	JLabel nameLabel = new JLabel("Tên chức vụ:");
		JTextField nameField = new JTextField(15);

		JLabel salaryCoefficientLabel = new JLabel("Hệ số lương:");
		JTextField salaryCoefficient = new JTextField(15);
		
		JLabel baseSalarylLabel = new JLabel("Lương cơ bản:");
		JTextField baseSalaryField = new JTextField(15);
		
		
		panel.add(idLabel);
		panel.add(idField);
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(salaryCoefficientLabel);
		panel.add(salaryCoefficient);
		panel.add(baseSalarylLabel);
		panel.add(baseSalaryField);
		


		// Hiển thị dialog với panel
		int result = JOptionPane.showConfirmDialog(this, panel, "Thêm chức vụ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		// Nếu nhấn OK
		if (result == JOptionPane.OK_OPTION) {
			String maCV = idField.getText();
			String tenCV = nameField.getText();
			float heSoLuong = Float.parseFloat(salaryCoefficient.getText());
			float heSluongCB = Float.parseFloat(baseSalaryField.getText());
			ChucVuDTO cv = new ChucVuDTO(maCV, tenCV, heSoLuong, heSluongCB);
			String message =cvBUS.insert(cv);
			String newRole = nameField.getText().trim();


			if (!newRole.isEmpty()) {
				roleCombobox.insertItemAt(newRole, roleCombobox.getItemCount() - 1);
				roleCombobox.setSelectedItem(newRole);
				JOptionPane.showMessageDialog(this, message);
			} else {
				JOptionPane.showMessageDialog(this, "Xin điền đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}
    
    
    private void newEmployeeDialog() {
    	JDialog newEmployeeDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm nhân viên", true);
    	newEmployeeDialog.setSize(800,500);
    	newEmployeeDialog.setLayout(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	JPanel leftPanel, rightPanel;
    	leftPanel = new JPanel(new GridBagLayout());
    	leftPanel.setBackground(Color.white);
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.weightx = 0.5;
    	gbc.weighty = 1.0;
    	gbc.fill = GridBagConstraints.BOTH;
    	newEmployeeDialog.add(leftPanel, gbc);
    	
    	rightPanel = new JPanel(new GridBagLayout());
    	rightPanel.setBackground(Color.white);
    	rightPanel.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	gbc.weightx = 0.5;
    	gbc.weighty = 1.0;
    	gbc.fill = GridBagConstraints.BOTH;
    	newEmployeeDialog.add(rightPanel, gbc);
    	
    	//topLeftPanel
    	JPanel topLeftPanel, middleLeftPanel, bottomLeftPanel;
    	topLeftPanel = new JPanel(null);
    	topLeftPanel.setBackground(Color.white);
    	topLeftPanel.setBorder(BorderFactory.createTitledBorder("Thông tin"));
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.weightx = 1.0;
    	gbc.weighty = 0.7;
    	gbc.fill = GridBagConstraints.BOTH;
    	gbc.insets = new Insets(3, 3, 3, 3);
    	leftPanel.add(topLeftPanel, gbc);

    	middleLeftPanel = new JPanel(null);
    	middleLeftPanel.setBackground(Color.white);
    	middleLeftPanel.setBorder(BorderFactory.createTitledBorder("Đăng nhập"));
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	gbc.weightx = 1.0;
    	gbc.weighty = 0.15;
    	gbc.fill = GridBagConstraints.BOTH;
    	gbc.insets = new Insets(3, 3, 3, 3);
    	leftPanel.add(middleLeftPanel, gbc);
    	
    	bottomLeftPanel = new JPanel(null);
    	bottomLeftPanel.setBackground(Color.white);
    	gbc.gridx = 0;
    	gbc.gridy = 2;
    	gbc.weightx = 1.0;
    	gbc.weighty = 0.15;
    	gbc.fill = GridBagConstraints.BOTH;
    	gbc.insets = new Insets(3, 3, 3, 3);
    	leftPanel.add(bottomLeftPanel, gbc);
    	
    	JLabel lblId = new JLabel("Mã nhân viên:");
    	lblId.setBounds(10, 20, 100, 20);
    	topLeftPanel.add(lblId);
    	JTextField txtId = new JTextField();
		txtId.setBounds(100, 20, 100, 20);
		topLeftPanel.add(txtId);
		
		JLabel lblName = new JLabel("Họ tên:");
		lblName.setBounds(10, 45, 100, 20);
		topLeftPanel.add(lblName);
    	JTextField txtName = new JTextField();
    	txtName.setBounds(100, 45, 100, 20);
    	topLeftPanel.add(txtName);
		
		JLabel lblDOB = new JLabel("Ngày sinh:");
		lblDOB.setBounds(10, 70, 100, 20);
		topLeftPanel.add(lblDOB);
    	JDateChooser dateChooser = new JDateChooser();
    	dateChooser.setBounds(100, 70, 123, 20);
    	topLeftPanel.add(dateChooser);
    	
    	JLabel lblGender = new JLabel("Giới tính:");
    	lblGender.setBounds(10, 95, 100, 20);
    	topLeftPanel.add(lblGender);
    	String[] genders = {"Nam", "Nữ"};
    	genderCombobox = new JComboBox<String>(genders);
    	genderCombobox.setBounds(100, 95, 70, 20);
    	topLeftPanel.add(genderCombobox);
    	
    	JLabel lblAddress = new JLabel("Địa chỉ:");
    	lblAddress.setBounds(10, 120, 100, 20);
    	topLeftPanel.add(lblAddress);
    	JTextField txtAddress = new JTextField();
    	txtAddress.setBounds(100, 120, 130, 20);
    	topLeftPanel.add(txtAddress);
    	
    	JLabel lblPhone = new JLabel("Số điện thoại:");
    	lblPhone.setBounds(10, 145, 100, 20);
    	topLeftPanel.add(lblPhone);
    	JTextField txtPhone = new JTextField();
    	txtPhone.setBounds(100, 145, 100, 20);
    	topLeftPanel.add(txtPhone);
    	
    	JLabel lblEmail = new JLabel("Email:");
    	lblEmail.setBounds(10, 170, 100, 20);
    	topLeftPanel.add(lblEmail);
    	JTextField txtEmail = new JTextField();
    	txtEmail.setBounds(100, 170, 100, 20);
    	topLeftPanel.add(txtEmail);
    	
    	JLabel lblRole = new JLabel("Chức vụ:");
    	lblRole.setBounds(10, 195, 130, 20);
    	topLeftPanel.add(lblRole);
    	
    	JComboBox<String> roleCombobox = new JComboBox<String>(roles);
    	//lấy dữ liệu chức vụ từ database để fill vào combobox
    	roleCombobox.setBounds(100, 195, 130, 20);
    	topLeftPanel.add(roleCombobox);
    	roleCombobox.addItemListener(e -> {
    		if(e.getStateChange() == ItemEvent.SELECTED) {
    			String selected = (String)roleCombobox.getSelectedItem();
    			if("Thêm chức vụ...".equals(selected)) {
    				newRoleDialog();
    			}
    		}
    	});
    	
    	
    	JLabel lblWorkplace = new JLabel("Nơi làm việc:");
    	lblWorkplace.setBounds(10, 220, 100, 20);
    	topLeftPanel.add(lblWorkplace);
    	
    	ArrayList<KhoDTO> arrKho = khoBUS.selectAll();
        for(int i=0; i<arrKho.size(); i++) {        		
        	workplaces[i] =  arrKho.get(i).getTenKho();
        }
    	JComboBox<String> workplaceCombobox = new JComboBox<String>(workplaces);
    	workplaceCombobox.setBounds(100, 220, 130, 20);
    	topLeftPanel.add(workplaceCombobox);
    	workplaceCombobox.addItemListener(e -> {
    		if(e.getStateChange() == ItemEvent.SELECTED) {
    			String selected = (String)workplaceCombobox.getSelectedItem();
    			if("Thêm kho...".equals(selected)) {
    				newWarehouseDialog();
    			}
    		}
    	});
    	
    	JLabel lblStatus = new JLabel("Trạng thái:");
    	lblStatus.setBounds(10, 245, 100, 20);
    	topLeftPanel.add(lblStatus);
    	JRadioButton rbOn = new JRadioButton("On");
    	rbOn.setBounds(100, 245, 100, 20);
    	topLeftPanel.add(rbOn);
    	JRadioButton rbOff = new JRadioButton("Off");
    	rbOff.setBounds(150, 245, 100, 20);
    	topLeftPanel.add(rbOff);
    	
    	JLabel lblImage = new JLabel("Hình ảnh:");
    	lblImage.setBounds(10, 270, 100, 20);
    	topLeftPanel.add(lblImage);
  
    	//rightPanel
    	JLabel employeeImg = new JLabel();
    	employeeImg.setHorizontalAlignment(JLabel.CENTER);
    	employeeImg.setVerticalAlignment(JLabel.CENTER);
    	employeeImg.setPreferredSize(new Dimension(200, 200)); // Tự động co giãn

		GridBagConstraints gbcImg = new GridBagConstraints();
		gbcImg.gridx = 0;
		gbcImg.gridy = 0;
		gbcImg.weightx = 1.0;
		gbcImg.weighty = 1.0;
		gbcImg.fill = GridBagConstraints.BOTH; // Ảnh sẽ fill toàn bộ panel
		rightPanel.add(employeeImg, gbcImg);
    	//End rightPanel
    	JButton btnBrowse = new ShadowButton("Chọn");
    	btnBrowse.setBounds(100, 270, 70, 20);
    	btnBrowse.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent e) {
    			btnBrowse.setBackground(Color.decode("#3A96CF"));
    			btnBrowse.setCursor(new Cursor(Cursor.HAND_CURSOR));
    		}
    		@Override
			public void mouseExited(MouseEvent e) {
    			btnBrowse.setBackground(Color.white);
			}
    	});
    	btnBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
				int returnValue = fileChooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					selectedFilePathName = selectedFile.getAbsolutePath();
					//Hiển thị đường dẫn của ảnh được
					System.out.println("Đường dẫn của ảnh được chọn: "+selectedFile.getAbsolutePath());
					ImageIcon icon = new ImageIcon(selectedFile.getAbsoluteFile().getAbsolutePath());
					Image img = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
					employeeImg.setIcon(new ImageIcon(img));
					
					// Chuyển ảnh sang byte array
				    byte[] imageData = convertImageToBytes(selectedFile);
				}
			}

			
		});

    	topLeftPanel.add(btnBrowse);
    	
    	//middleLeftPanel
    	JLabel lblPassword;

    	
    	lblPassword = new JLabel("Mật khẩu:");
    	lblPassword.setBounds(10, 20, 100, 20);
    	middleLeftPanel.add(lblPassword);
    	
    	JTextField txtPassword;
    	
    	txtPassword = new JTextField();
    	txtPassword.setBounds(100, 20, 100, 20);
    	middleLeftPanel.add(txtPassword);
    	
    	//bottomLeftPanel
    	JButton btnSave = new ShadowButton("Lưu");
    	btnSave.setBounds(215, 30, 70, 20);
    	btnSave.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent e) {
    			btnSave.setBackground(Color.decode("#3A96CF"));
    			btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
    		}
    		@Override
			public void mouseExited(MouseEvent e) {
    			btnSave.setBackground(Color.white);
			}
    	});
    	btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String maNV = txtId.getText();
					String hoTen = txtName.getText();
					java.util.Date utilDate = dateChooser.getDate(); // Lấy ngày từ JDateChooser
					System.out.println("Thời gian của biến utilDate: "+utilDate);
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Chuyển sang SQL Date
					System.out.println("Thời gian của biến sqlDate: "+sqlDate);
					String gioiTinh = genderCombobox.getSelectedItem().toString();
					String diaChi = txtAddress.getText();
					String sdt = txtPhone.getText();
					String email = txtEmail.getText();
					String trangThai = rbOn.isSelected()? "On":"Off"; //rbOn có được chọn hay không, nếu isSelected thì giá của trangThai là "On", không thì là "Off"
					String matKhau = txtPassword.getText();
					String chucVu = roleCombobox.getSelectedItem().toString();
					System.out.println("Role picked: " + chucVu);
					String noiLamViec = workplaceCombobox.getSelectedItem().toString();
					
					// Chuyển ảnh thành byte[]
					File imageFile = new File(selectedFilePathName);
					byte[] hinhAnh = convertImageToBytes(imageFile);
					
					
					NhanVienDTO nv = new NhanVienDTO(maNV, hoTen, sqlDate, gioiTinh, diaChi, sdt, email, matKhau, hinhAnh, trangThai, chucVu, noiLamViec);
					// Gọi phương thức insert từ NhanVienBUS
					NhanVienBUS nvBUS = new NhanVienBUS();
					String message = nvBUS.insert(nv);
					JOptionPane.showMessageDialog(null, message);
					
				}catch (Exception e2) {
					e2.printStackTrace();
					e2.getMessage();
		            JOptionPane.showMessageDialog(newEmployeeDialog, "Lỗi khi thêm nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
    	bottomLeftPanel.add(btnSave);
    	
    	newEmployeeDialog.setLocationRelativeTo(this);
    	newEmployeeDialog.setVisible(true);
    }

    private byte[] convertImageToBytes(File selectedFile) {
    	try {
            FileInputStream fis = new FileInputStream(selectedFile);
            byte[] data = new byte[(int) selectedFile.length()];
            fis.read(data);
            fis.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
    
    private void fillRoleCombobox(JComboBox<String> combobox) {
    	ArrayList<ChucVuDTO> arrChucVu = cvBUS.selectAll();
    	roleCombobox.removeAllItems(); // Xóa dữ liệu cũ (nếu có)
    	for(ChucVuDTO cv: arrChucVu) {
    		roleCombobox.addItem(cv.getTenCV());
    	}
    	roleCombobox.addItem("Thêm chức vụ...");
    }
    
    
    
    
    private void updateEmployeeDialog() {
    	int selectedRow = employeeTable.getSelectedRow();
    	if(selectedRow!=-1) {
    		JDialog updateEmployeeDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thông tin nhân viên", true);
    		updateEmployeeDialog.setSize(800,500);
    		updateEmployeeDialog.setLayout(new GridBagLayout());
        	GridBagConstraints gbc = new GridBagConstraints();
        	
        	JPanel leftPanel, rightPanel;
        	leftPanel = new JPanel(new GridBagLayout());
        	leftPanel.setBackground(Color.white);
        	gbc.gridx = 0;
        	gbc.gridy = 0;
        	gbc.weightx = 0.5;
        	gbc.weighty = 1.0;
        	gbc.fill = GridBagConstraints.BOTH;
        	updateEmployeeDialog.add(leftPanel, gbc);
        	
        	rightPanel = new JPanel(new GridBagLayout());
        	rightPanel.setBackground(Color.white);
        	rightPanel.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
        	gbc.gridx = 1;
        	gbc.gridy = 0;
        	gbc.weightx = 0.5;
        	gbc.weighty = 1.0;
        	gbc.fill = GridBagConstraints.BOTH;
        	updateEmployeeDialog.add(rightPanel, gbc);
        	
        	//topLeftPanel
        	JPanel topLeftPanel, middleLeftPanel, bottomLeftPanel;
        	topLeftPanel = new JPanel(null);
        	topLeftPanel.setBackground(Color.white);
        	topLeftPanel.setBorder(BorderFactory.createTitledBorder("Thông tin"));
        	gbc.gridx = 0;
        	gbc.gridy = 0;
        	gbc.weightx = 1.0;
        	gbc.weighty = 0.7;
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.insets = new Insets(3, 3, 3, 3);
        	leftPanel.add(topLeftPanel, gbc);

        	middleLeftPanel = new JPanel(null);
        	middleLeftPanel.setBackground(Color.white);
        	middleLeftPanel.setBorder(BorderFactory.createTitledBorder("Đăng nhập"));
        	gbc.gridx = 0;
        	gbc.gridy = 1;
        	gbc.weightx = 1.0;
        	gbc.weighty = 0.15;
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.insets = new Insets(3, 3, 3, 3);
        	leftPanel.add(middleLeftPanel, gbc);
        	
        	bottomLeftPanel = new JPanel(null);
        	bottomLeftPanel.setBackground(Color.white);
        	gbc.gridx = 0;
        	gbc.gridy = 2;
        	gbc.weightx = 1.0;
        	gbc.weighty = 0.15;
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.insets = new Insets(3, 3, 3, 3);
        	leftPanel.add(bottomLeftPanel, gbc);
        	
        	JLabel lblId = new JLabel("Mã nhân viên:");
        	lblId.setBounds(10, 20, 100, 20);
        	topLeftPanel.add(lblId);
        	JTextField txtId = new JTextField();
    		txtId.setBounds(100, 20, 100, 20);
    		txtId.setEnabled(false);
    		topLeftPanel.add(txtId);
    		
    		JLabel lblName = new JLabel("Họ tên:");
    		lblName.setBounds(10, 45, 100, 20);
    		topLeftPanel.add(lblName);
        	JTextField txtName = new JTextField();
        	txtName.setBounds(100, 45, 100, 20);
        	topLeftPanel.add(txtName);
    		
    		JLabel lblDOB = new JLabel("Ngày sinh:");
    		lblDOB.setBounds(10, 70, 100, 20);
    		topLeftPanel.add(lblDOB);
        	JDateChooser dateChooser = new JDateChooser();
        	dateChooser.setBounds(100, 70, 123, 20);
        	topLeftPanel.add(dateChooser);
        	
        	JLabel lblGender = new JLabel("Giới tính:");
        	lblGender.setBounds(10, 95, 100, 20);
        	topLeftPanel.add(lblGender);
        	String[] genders = {"Nam", "Nữ"};
        	genderCombobox = new JComboBox<String>(genders);
        	genderCombobox.setBounds(100, 95, 70, 20);
        	topLeftPanel.add(genderCombobox);
        	
        	JLabel lblAddress = new JLabel("Địa chỉ:");
        	lblAddress.setBounds(10, 120, 100, 20);
        	topLeftPanel.add(lblAddress);
        	JTextField txtAddress = new JTextField();
        	txtAddress.setBounds(100, 120, 130, 20);
        	topLeftPanel.add(txtAddress);
        	
        	JLabel lblPhone = new JLabel("Số điện thoại:");
        	lblPhone.setBounds(10, 145, 100, 20);
        	topLeftPanel.add(lblPhone);
        	JTextField txtPhone = new JTextField();
        	txtPhone.setBounds(100, 145, 100, 20);
        	topLeftPanel.add(txtPhone);
        	
        	JLabel lblEmail = new JLabel("Email:");
        	lblEmail.setBounds(10, 170, 100, 20);
        	topLeftPanel.add(lblEmail);
        	JTextField txtEmail = new JTextField();
        	txtEmail.setBounds(100, 170, 130, 20);
        	topLeftPanel.add(txtEmail);
        	
        	JLabel lblRole = new JLabel("Chức vụ:");
        	lblRole.setBounds(10, 195, 100, 20);
        	topLeftPanel.add(lblRole);

        	roleCombobox = new JComboBox<String>(roles);
        	roleCombobox.setBounds(100, 195, 130, 20);
        	topLeftPanel.add(roleCombobox);

        	
        	JLabel lblWorkplace = new JLabel("Nơi làm việc:");
        	lblWorkplace.setBounds(10, 220, 100, 20);
        	topLeftPanel.add(lblWorkplace);
        	
        	ArrayList<KhoDTO> arrKho = khoBUS.selectAll();
            for(int i=0; i<arrKho.size(); i++) {        		
            	workplaces[i] =  arrKho.get(i).getTenKho();
            }
        	JComboBox<String> workplaceCombobox = new JComboBox<String>(workplaces);
        	workplaceCombobox.setBounds(100, 220, 130, 20);
        	topLeftPanel.add(workplaceCombobox);
        	
        	JLabel lblStatus = new JLabel("Trạng thái:");
        	lblStatus.setBounds(10, 245, 100, 20);
        	topLeftPanel.add(lblStatus);
        	JRadioButton rbOn = new JRadioButton("On");
        	rbOn.setBounds(100, 245, 100, 20);
        	topLeftPanel.add(rbOn);
        	JRadioButton rbOff = new JRadioButton("Off");
        	rbOff.setBounds(150, 245, 100, 20);
        	topLeftPanel.add(rbOff);
        	
      
        	//rightPanel
        	JLabel employeeImg = new JLabel();
        	employeeImg.setHorizontalAlignment(JLabel.CENTER);
        	employeeImg.setVerticalAlignment(JLabel.CENTER);
        	employeeImg.setPreferredSize(new Dimension(200, 200)); // Tự động co giãn

    		GridBagConstraints gbcImg = new GridBagConstraints();
    		gbcImg.gridx = 0;
    		gbcImg.gridy = 0;
    		gbcImg.weightx = 1.0;
    		gbcImg.weighty = 1.0;
    		gbcImg.fill = GridBagConstraints.BOTH; // Ảnh sẽ fill toàn bộ panel
    		rightPanel.add(employeeImg, gbcImg);
        	//End rightPanel

        	
        	//middleLeftPanel
        	JLabel lblPassword;
        	lblPassword = new JLabel("Mật khẩu:");
        	lblPassword.setBounds(10, 20, 100, 20);
        	middleLeftPanel.add(lblPassword);
        	
        	JTextField txtPassword;
        	txtPassword = new JTextField();
        	txtPassword.setBounds(100, 20, 100, 20);
        	txtPassword.setEnabled(false);
        	middleLeftPanel.add(txtPassword);
        	
        	
        	
        	//bottomLeftPanel
        	JLabel lblEditor = new JLabel("Người chỉnh sứa:");
        	lblEditor.setBounds(10, 5, 130, 20);
        	bottomLeftPanel.add(lblEditor);
        	JButton btnSave = new ShadowButton("Lưu");
        	btnSave.setBounds(215, 35, 70, 20);
        	btnSave.addMouseListener(new MouseAdapter() {
        		@Override
        		public void mouseEntered(MouseEvent e) {
        			btnSave.setBackground(Color.decode("#3A96CF"));
        			btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        		}
        		@Override
    			public void mouseExited(MouseEvent e) {
        			btnSave.setBackground(Color.white);
    			}
        	});
        	bottomLeftPanel.add(btnSave);

        	
        	
        	
        	//Lấy giá trị từ csdl và truyền vào các trường dữ liệu
        	String maNV = employeeTable.getValueAt(selectedRow, 0).toString();	
        	NhanVienDTO nv = nvBUS.selectById(maNV);
        	if(nv!=null) {
        		txtId.setText(nv.getMaNV());
        		txtName.setText(nv.getHoTen());
        		dateChooser.setDate(nv.getNgaySinh());
        		genderCombobox.setSelectedItem(nv.getGioiTinh());	
        		roleCombobox.setSelectedItem(nvBUS.getRoleNameByRoleId(nv.getChucVu()));	//lấy mã chưc vụ của nhân viên để lấy tên chức vụ
        		System.out.println("Class: NhanVienGUI | Method: employeeDetailDialog: "+ nvBUS.getRoleNameByRoleId(nv.getChucVu()));
        		System.out.println("Class: NhanVienGUI | Method: employeeDetailDialog: "+ nv.getChucVu());
        		txtAddress.setText(nv.getDiaChi());
        		txtPhone.setText(nv.getSoDienThoai());
        		txtEmail.setText(nv.getEmail());
        		if(nv.getTrangThai().equalsIgnoreCase("On")) {	//lấy trạng thái của nhân viên, nếu là "On" thì set radio button rbOn lên
        			rbOn.setSelected(true);
        			rbOff.setSelected(false);
        		}else {
        			rbOn.setSelected(false);
        			rbOff.setSelected(true);
        		}
        		
        		txtPassword.setText(nv.getMatKhau());
        		// Lấy dữ liệu ảnh từ database (kiểu VARBINARY)
    		    byte[] imageData = nv.getHinhAnh(); // Phương thức này phải trả về byte[]
    		    ImageIcon imageIcon = null;
    		    if (imageData != null) {
    		        // Chuyển đổi byte[] thành ImageIcon
    		        Image image = Toolkit.getDefaultToolkit().createImage(imageData);
    		        Image scaledImage = image.getScaledInstance(450, 450, Image.SCALE_SMOOTH); // Resize ảnh
    		        imageIcon = new ImageIcon(scaledImage);
    		        //rightPanel
					employeeImg.setIcon(new ImageIcon(scaledImage));
					
    		    }
        		
        		
        	}
        	
//        	bottomLeftPanel.add(btnSave);
        	
        	updateEmployeeDialog.setLocationRelativeTo(this);
        	updateEmployeeDialog.setVisible(true);
    	}else {
    		JOptionPane.showMessageDialog(null,"Vui lòng chọn nhân viên cần chỉnh sửa!");
    	}
    }
    
    private void employeeDetailDialog() {
    	int selectedRow = employeeTable.getSelectedRow();
    	if(selectedRow!=-1) {
    		JDialog employeeDetailDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thông tin nhân viên", true);
    		employeeDetailDialog.setSize(800,500);
        	employeeDetailDialog.setLayout(new GridBagLayout());
        	GridBagConstraints gbc = new GridBagConstraints();
        	
        	JPanel leftPanel, rightPanel;
        	leftPanel = new JPanel(new GridBagLayout());
        	leftPanel.setBackground(Color.white);
        	gbc.gridx = 0;
        	gbc.gridy = 0;
        	gbc.weightx = 0.5;
        	gbc.weighty = 1.0;
        	gbc.fill = GridBagConstraints.BOTH;
        	employeeDetailDialog.add(leftPanel, gbc);
        	
        	rightPanel = new JPanel(new GridBagLayout());
        	rightPanel.setBackground(Color.white);
        	rightPanel.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
        	gbc.gridx = 1;
        	gbc.gridy = 0;
        	gbc.weightx = 0.5;
        	gbc.weighty = 1.0;
        	gbc.fill = GridBagConstraints.BOTH;
        	employeeDetailDialog.add(rightPanel, gbc);
        	
        	//topLeftPanel
        	JPanel topLeftPanel, middleLeftPanel, bottomLeftPanel;
        	topLeftPanel = new JPanel(null);
        	topLeftPanel.setBackground(Color.white);
        	topLeftPanel.setBorder(BorderFactory.createTitledBorder("Thông tin"));
        	gbc.gridx = 0;
        	gbc.gridy = 0;
        	gbc.weightx = 1.0;
        	gbc.weighty = 0.7;
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.insets = new Insets(3, 3, 3, 3);
        	leftPanel.add(topLeftPanel, gbc);

        	middleLeftPanel = new JPanel(null);
        	middleLeftPanel.setBackground(Color.white);
        	middleLeftPanel.setBorder(BorderFactory.createTitledBorder("Đăng nhập"));
        	gbc.gridx = 0;
        	gbc.gridy = 1;
        	gbc.weightx = 1.0;
        	gbc.weighty = 0.15;
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.insets = new Insets(3, 3, 3, 3);
        	leftPanel.add(middleLeftPanel, gbc);
        	
        	bottomLeftPanel = new JPanel(null);
        	bottomLeftPanel.setBackground(Color.white);
        	gbc.gridx = 0;
        	gbc.gridy = 2;
        	gbc.weightx = 1.0;
        	gbc.weighty = 0.15;
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.insets = new Insets(3, 3, 3, 3);
        	leftPanel.add(bottomLeftPanel, gbc);
        	
        	JLabel lblId = new JLabel("Mã nhân viên:");
        	lblId.setBounds(10, 20, 100, 20);
        	topLeftPanel.add(lblId);
        	JTextField txtId = new JTextField();
    		txtId.setEditable(false); // sẽ lấy id mới nhất của bảng nhân viên trong csdl ra để tạo mã, k cho nhập tự động
    		txtId.setBounds(100, 20, 100, 20);
    		txtId.setEditable(false);
    		txtId.setEnabled(false);
    		topLeftPanel.add(txtId);
    		
    		JLabel lblName = new JLabel("Họ tên:");
    		lblName.setBounds(10, 45, 100, 20);
    		topLeftPanel.add(lblName);
        	JTextField txtName = new JTextField();
        	txtName.setBounds(100, 45, 100, 20);
        	txtName.setEnabled(false);
        	topLeftPanel.add(txtName);
    		
    		JLabel lblDOB = new JLabel("Ngày sinh:");
    		lblDOB.setBounds(10, 70, 100, 20);
    		topLeftPanel.add(lblDOB);
        	JDateChooser dateChooser = new JDateChooser();
        	dateChooser.setBounds(100, 70, 123, 20);
        	dateChooser.setEnabled(false);
        	topLeftPanel.add(dateChooser);
        	
        	JLabel lblGender = new JLabel("Giới tính:");
        	lblGender.setBounds(10, 95, 100, 20);
        	topLeftPanel.add(lblGender);
        	String[] genders = {"Nam", "Nữ"};
        	genderCombobox = new JComboBox<String>(genders);
        	genderCombobox.setBounds(100, 95, 70, 20);
        	genderCombobox.setEnabled(false);
        	topLeftPanel.add(genderCombobox);
        	
        	JLabel lblAddress = new JLabel("Địa chỉ:");
        	lblAddress.setBounds(10, 120, 100, 20);
        	topLeftPanel.add(lblAddress);
        	JTextField txtAddress = new JTextField();
        	txtAddress.setBounds(100, 120, 130, 20);
        	txtAddress.setEnabled(false);
        	topLeftPanel.add(txtAddress);
        	
        	JLabel lblPhone = new JLabel("Số điện thoại:");
        	lblPhone.setBounds(10, 145, 100, 20);
        	topLeftPanel.add(lblPhone);
        	JTextField txtPhone = new JTextField();
        	txtPhone.setBounds(100, 145, 100, 20);
        	txtPhone.setEnabled(false);
        	topLeftPanel.add(txtPhone);
        	
        	JLabel lblEmail = new JLabel("Email:");
        	lblEmail.setBounds(10, 170, 100, 20);
        	topLeftPanel.add(lblEmail);
        	JTextField txtEmail = new JTextField();
        	txtEmail.setBounds(100, 170, 130, 20);
        	txtEmail.setEnabled(false);
        	topLeftPanel.add(txtEmail);
        	
        	JLabel lblRole = new JLabel("Chức vụ:");
        	lblRole.setBounds(10, 195, 100, 20);
        	topLeftPanel.add(lblRole);

        	roleCombobox = new JComboBox<String>(roles);
        	roleCombobox.setBounds(100, 195, 130, 20);
        	roleCombobox.setEnabled(false);
        	topLeftPanel.add(roleCombobox);

        	
        	JLabel lblWorkplace = new JLabel("Nơi làm việc:");
        	lblWorkplace.setBounds(10, 220, 100, 20);
        	topLeftPanel.add(lblWorkplace);
        	
        	ArrayList<KhoDTO> arrKho = khoBUS.selectAll();
            for(int i=0; i<arrKho.size(); i++) {        		
            	workplaces[i] =  arrKho.get(i).getTenKho();
            }
        	JComboBox<String> workplaceCombobox = new JComboBox<String>(workplaces);
        	workplaceCombobox.setBounds(100, 220, 130, 20);
        	workplaceCombobox.setEnabled(false);
        	topLeftPanel.add(workplaceCombobox);
        	
        	JLabel lblStatus = new JLabel("Trạng thái:");
        	lblStatus.setBounds(10, 245, 100, 20);
        	topLeftPanel.add(lblStatus);
        	JRadioButton rbOn = new JRadioButton("On");
        	rbOn.setBounds(100, 245, 100, 20);
        	rbOn.setEnabled(false);
        	topLeftPanel.add(rbOn);
        	JRadioButton rbOff = new JRadioButton("Off");
        	rbOff.setBounds(150, 245, 100, 20);
        	rbOff.setEnabled(false);
        	topLeftPanel.add(rbOff);
        	
      
        	//rightPanel
        	JLabel employeeImg = new JLabel();
        	employeeImg.setHorizontalAlignment(JLabel.CENTER);
        	employeeImg.setVerticalAlignment(JLabel.CENTER);
        	employeeImg.setPreferredSize(new Dimension(200, 200)); // Tự động co giãn

    		GridBagConstraints gbcImg = new GridBagConstraints();
    		gbcImg.gridx = 0;
    		gbcImg.gridy = 0;
    		gbcImg.weightx = 1.0;
    		gbcImg.weighty = 1.0;
    		gbcImg.fill = GridBagConstraints.BOTH; // Ảnh sẽ fill toàn bộ panel
    		rightPanel.add(employeeImg, gbcImg);
        	//End rightPanel

        	
        	//middleLeftPanel
        	JLabel lblPassword;
        	
        	lblPassword = new JLabel("Mật khẩu:");
        	lblPassword.setBounds(10, 20, 100, 20);
        	middleLeftPanel.add(lblPassword);
        	
        	JTextField txtPassword;
        	
        	txtPassword = new JTextField();
        	txtPassword.setBounds(100, 20, 100, 20);
        	txtPassword.setEnabled(false);
        	middleLeftPanel.add(txtPassword);
        	
        	//bottomLeftPanel
//        	JButton btnSave = new ShadowButton("Lưu");
//        	btnSave.setBounds(215, 30, 70, 20);
//        	btnSave.addMouseListener(new MouseAdapter() {
//        		@Override
//        		public void mouseEntered(MouseEvent e) {
//        			btnSave.setBackground(Color.decode("#3A96CF"));
//        			btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        		}
//        		@Override
//    			public void mouseExited(MouseEvent e) {
//        			btnSave.setBackground(Color.white);
//    			}
//        	});
        	
        	
        	//Lấy giá trị từ csdl và truyền vào các trường dữ liệu
        	String maNV = employeeTable.getValueAt(selectedRow, 0).toString();	
        	NhanVienDTO nv = nvBUS.selectById(maNV);
        	if(nv!=null) {
        		txtId.setText(nv.getMaNV());
        		txtName.setText(nv.getHoTen());
        		dateChooser.setDate(nv.getNgaySinh());
        		genderCombobox.setSelectedItem(nv.getGioiTinh());	
        		roleCombobox.setSelectedItem(nvBUS.getRoleNameByRoleId(nv.getChucVu()));	//lấy mã chưc vụ của nhân viên để lấy tên chức vụ
        		System.out.println("Class: NhanVienGUI | Method: employeeDetailDialog: "+ nvBUS.getRoleNameByRoleId(nv.getChucVu()));
        		System.out.println("Class: NhanVienGUI | Method: employeeDetailDialog: "+ nv.getChucVu());
        		txtAddress.setText(nv.getDiaChi());
        		txtPhone.setText(nv.getSoDienThoai());
        		txtEmail.setText(nv.getEmail());
        		if(nv.getTrangThai().equalsIgnoreCase("On")) {	//lấy trạng thái của nhân viên, nếu là "On" thì set radio button rbOn lên
        			rbOn.setSelected(true);
        			rbOff.setSelected(false);
        		}else {
        			rbOn.setSelected(false);
        			rbOff.setSelected(true);
        		}
        		
        		txtPassword.setText(nv.getMatKhau());
        		// Lấy dữ liệu ảnh từ database (kiểu VARBINARY)
    		    byte[] imageData = nv.getHinhAnh(); // Phương thức này phải trả về byte[]
    		    ImageIcon imageIcon = null;
    		    if (imageData != null) {
    		        // Chuyển đổi byte[] thành ImageIcon
    		        Image image = Toolkit.getDefaultToolkit().createImage(imageData);
    		        Image scaledImage = image.getScaledInstance(450, 450, Image.SCALE_SMOOTH); // Resize ảnh
    		        imageIcon = new ImageIcon(scaledImage);
    		        //rightPanel
					employeeImg.setIcon(new ImageIcon(scaledImage));
					
    		    }
        		
        		
        	}
        	
//        	bottomLeftPanel.add(btnSave);
        	
        	employeeDetailDialog.setLocationRelativeTo(this);
        	employeeDetailDialog.setVisible(true);
    	}else {
    		JOptionPane.showMessageDialog(null,"Vui lòng chọn nhân viên để xem chi tiết");
    	}
    	
    }
    
    public void newWarehouseDialog() {
    	
    }
    
    private void refreshList(){
        // Xóa tất cả các dòng trong mô hình bảng
        employeeModel.setRowCount(0);
        loadEmployeeList();
        sortComboBox.setSelectedIndex(0);
        genderCombobox.setSelectedIndex(0);
        roleCombobox.setSelectedIndex(0);
    }
}
