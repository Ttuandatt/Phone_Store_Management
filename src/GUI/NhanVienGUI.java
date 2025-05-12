package GUI;

import BUS.BangChamCongBUS;
import BUS.ChucVuBUS;
import BUS.KhoBUS;
import BUS.NhanVienBUS;
import DTO.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Components.ImageRenderer;
import Components.ShadowButton;
import java.text.DecimalFormat;


public class NhanVienGUI extends JPanel{
	
	NhanVienBUS nvBUS = new NhanVienBUS();
	ChucVuBUS cvBUS = new ChucVuBUS();
	KhoBUS khoBUS = new KhoBUS();
	BangChamCongBUS bccBUS = new BangChamCongBUS();
    JTable employeeTable, leaveDetailTable;
    DefaultTableModel employeeModel = new DefaultTableModel();
    DefaultTableModel leaveDetailModel = new DefaultTableModel();
    ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>(); 
    JComboBox<String> sortComboBox, genderCombobox, roleCombobox, workplaceCombobox, khoCombobox;
	JComboBox<Integer> monthCombobox, yearCombobox;
    ArrayList<ChucVuDTO> arrChucVu = cvBUS.selectAll();
    ArrayList<KhoDTO> arrNoiLamViec = khoBUS.selectAll();
    boolean comboboxChucVuClicked = false;
    boolean comboboxKhoClicked = false;
    String[] roles = new String[arrChucVu.size()];
    String[] workplaces =  new String[arrNoiLamViec.size()];
    JPanel nhanVienContent;
    JTextField txtTimKiem;
	JLabel dataSoNgayCong, dataSoNgayNghiPhepCoLuong, dataSoNgayNghiPhepKhongLuong, dataSoNgayNghiKhongPhep, dataSoGioOT, dataTongSoNgayTinhLuong, lblTongSoNgayTinhLuong;
	
	
	
	
	final byte[][] imageBytes = new byte[1][];
	String selectedFilePath;	//biến lưu đường dẫn của ảnh được chọn
	
	//Constructor
    public NhanVienGUI(){
        initComponents();
        loadNhanVienList();
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
        nhanVienContent = new JPanel();
        nhanVienContent.setBackground(Color.white);
        nhanVienContent.setLayout(new GridBagLayout());
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nhanVienContent, gbc); // Thêm vào ProductsGUI
        
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
        nhanVienContent.add(topPanel, gbc);
        
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
        nhanVienContent.add(bottomPanel, gbc);
        
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
        ImageIcon iconDelete = new ImageIcon(getClass().getResource("/img/delete.png")); // Đặt đường dẫn ảnh ở đây
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
                deleteEmployee(employeeTable);
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
        String[] sortCriterias = {"Tất cả", "A-Z", "Z-A"};
        sortComboBox = new JComboBox<String>(sortCriterias);
        sortComboBox.setBounds(10, 24, 75, 25);
        searchInputPanel.add(sortComboBox);
        sortComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedOption = (String)sortComboBox.getSelectedItem();
				switch(selectedOption) {
					case "A-Z":
						sortAZ();
						break;
					case "Z-A":
						sortZA();
						break;
				}
			}
		});
        
        
        String[] genders = {"Giới tính", "Nam", "Nữ"};
        genderCombobox = new JComboBox<String>(genders);
        genderCombobox.setBounds(90,  24,  90, 25);
        searchInputPanel.add(genderCombobox);
        genderCombobox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedOption = (String)genderCombobox.getSelectedItem();
				switch(selectedOption) {
				case "Nam":
					sortGenderMale();
					break;
				case "Nữ":
					sortGenderFeMale();
					break;
				}
				
			}
		});
        
     
        for(int i=0; i<arrChucVu.size(); i++) {        		
        	roles[i] =  arrChucVu.get(i).getTenCV();
        }
        roleCombobox = new JComboBox<String>(roles);
        roleCombobox.setBounds(185, 24, 100, 25);
    	fillChucVuCombobox(roleCombobox);
        searchInputPanel.add(roleCombobox);
        roleCombobox.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		comboboxChucVuClicked = true;
        	}
        });
        roleCombobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboboxChucVuClicked) {
					String role = roleCombobox.getSelectedItem().toString(); 
					log("role="+role);
					sortRole(role);
					comboboxChucVuClicked = false;
					
				}
			}
		});
        roleCombobox.addItemListener(e -> {
        	if(e.getStateChange() == ItemEvent.SELECTED) {
        		String selected = (String)roleCombobox.getSelectedItem();
        		if("Thêm chức vụ...".equals(selected)) {
        			newRoleDialog();
        		}
        	}
        	
        });

        
        for(int i=0; i<arrNoiLamViec.size(); i++) {
        	workplaces[i] = arrNoiLamViec.get(i).getTenKho();
        	log("kho="+workplaces[i]);
        }
        khoCombobox = new JComboBox<String>(workplaces);
        khoCombobox.setBounds(290, 24, 120, 25);
        fillKhoCombobox(khoCombobox);
        searchInputPanel.add(khoCombobox);
        khoCombobox.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		comboboxKhoClicked = true;
        	}
        });
        khoCombobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboboxKhoClicked) {
					String kho = khoCombobox.getSelectedItem().toString(); 
					log("kho="+kho);
					sortKho(kho);
					comboboxKhoClicked = false;
					
				}
			}
		});
        khoCombobox.addItemListener(e -> {
        	if(e.getStateChange() == ItemEvent.SELECTED) {
        		String selected = (String)khoCombobox.getSelectedItem();
        		if("Thêm kho...".equals(selected)) {
        			newRoleDialog();
        		}
        	}
        	
        });
        
        
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(440,  24,  200, 25);
        searchInputPanel.add(txtTimKiem);
        
        
        
        //==================================== searchButtonPanel =======================================================//
        ImageIcon iconSearch = new ImageIcon(getClass().getResource("/img/loupe2.png")); // Đặt đường dẫn ảnh ở đây
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
                searchPerformed(employeeTable);
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
        
        ImageIcon iconRefresh = new ImageIcon(getClass().getResource("/img/refresh.png")); // Đặt đường dẫn ảnh ở đây
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


        
        
        
        //========================= Table =========================//
        employeeTable = new JTable();
        JScrollPane sp = new JScrollPane(employeeTable);
        gbc.weightx = 0.65;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(sp, gbc);
		
		
		
		
		JPanel attendancePanel = new JPanel(new GridBagLayout());
		attendancePanel.setBackground(Color.white);
//		attendancePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 2)));
		gbc.weightx = 0.35;
		gbc.weighty = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(attendancePanel, gbc);
		
		//Chia 2 panel top & bottom trong attendancePanel
		JPanel avatarPanel, bottomAttendancePanel;
		
		avatarPanel = new JPanel(null);
		avatarPanel.setBackground(Color.white);
		avatarPanel.setBorder(BorderFactory.createTitledBorder(""));
		gbc.weightx = 1.0;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		attendancePanel.add(avatarPanel, gbc);
		
		bottomAttendancePanel = new JPanel(null);
		bottomAttendancePanel.setBackground(Color.white);
		bottomAttendancePanel.setBorder(BorderFactory.createTitledBorder(""));
		gbc.weightx = 1.0;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		attendancePanel.add(bottomAttendancePanel, gbc);
		
		employeeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()>=1) {	//nếu nhấn vào dòng đó từ 1 lần trở lên
					hienThiThongTinChamCong(employeeTable);
					hienThiAnhNhanVien(employeeTable, avatarPanel);
				}
			}
		});
		
		//topAttendancePanel
		//combobox tháng
		JLabel lblThang, lblNam;
		
		lblThang = new JLabel("Tháng");
		lblThang.setBounds(10,10,50,20);
		bottomAttendancePanel.add(lblThang);
		
		lblNam = new JLabel("Năm");
		lblNam.setBounds(90,10,50,20);
		bottomAttendancePanel.add(lblNam);
		
		Integer[] thang = {1,2,3,4,5,6,7,8,9,10,11,12};
		monthCombobox = new JComboBox<Integer>(thang);
		monthCombobox.setBounds(10,30,70,20);
		bottomAttendancePanel.add(monthCombobox);
		
		//combobox năm
		Integer[] nam = new Integer[100];
		for(int i=0; i<nam.length; i++) {
			nam[i] = 2000 + i;
		}
		yearCombobox = new JComboBox<Integer>(nam);
		yearCombobox.setBounds(90,30,70,20);
		bottomAttendancePanel.add(yearCombobox);
		
		
		
		JLabel lblSoNgayCong, lblSoNgayNghiPhepCoLuong, lblSoNgayNghiPhepKhongLuong, lblSoNgayNghiKhongPhep, lblSoGioOT;

		
		lblSoNgayCong = new JLabel("Số ngày công: ");
		lblSoNgayCong.setBounds(10, 80, 100, 20);
		bottomAttendancePanel.add(lblSoNgayCong);
		dataSoNgayCong = new JLabel("0");
		dataSoNgayCong.setBounds(190,80,50,20);
		bottomAttendancePanel.add(dataSoNgayCong);
		
		
		lblSoNgayNghiPhepCoLuong = new JLabel("Số ngày nghỉ phép có lương: ");
		lblSoNgayNghiPhepCoLuong.setBounds(10, 110, 200, 20);
		bottomAttendancePanel.add(lblSoNgayNghiPhepCoLuong);
		dataSoNgayNghiPhepCoLuong = new JLabel("0");
		dataSoNgayNghiPhepCoLuong.setBounds(190,110,50,20);
		bottomAttendancePanel.add(dataSoNgayNghiPhepCoLuong);
		
		lblSoNgayNghiPhepKhongLuong = new JLabel("Số ngày nghỉ phép không lương: ");
		lblSoNgayNghiPhepKhongLuong.setBounds(10, 140, 200, 20);
		bottomAttendancePanel.add(lblSoNgayNghiPhepKhongLuong);
		dataSoNgayNghiPhepKhongLuong = new JLabel("0");
		dataSoNgayNghiPhepKhongLuong.setBounds(190,140,50,20);
		bottomAttendancePanel.add(dataSoNgayNghiPhepKhongLuong);
		
		
		lblSoNgayNghiKhongPhep = new JLabel("Số ngày nghỉ không phép: ");
		lblSoNgayNghiKhongPhep.setBounds(10, 170, 150, 20);
		bottomAttendancePanel.add(lblSoNgayNghiKhongPhep);
		dataSoNgayNghiKhongPhep = new JLabel("0");
		dataSoNgayNghiKhongPhep.setBounds(190,170,50,20);
		bottomAttendancePanel.add(dataSoNgayNghiKhongPhep);
		
		lblSoGioOT = new JLabel("Tổng số giờ tăng ca: ");
		lblSoGioOT.setBounds(10, 200, 150, 20);
		bottomAttendancePanel.add(lblSoGioOT);
		dataSoGioOT = new JLabel("0.0");
		dataSoGioOT.setBounds(190,200,50,20);
		bottomAttendancePanel.add(dataSoGioOT);
		
		lblTongSoNgayTinhLuong = new JLabel("");
		lblTongSoNgayTinhLuong.setBounds(10, 240, 300, 20);
		bottomAttendancePanel.add(lblTongSoNgayTinhLuong);

		


		
		
    }

    
    private void loadNhanVienList() {
    	employeeTable.setDefaultEditor(Object.class, null); // không cho click vào & edit nội dung các cell trong bảng
    	
    	employeeTable.setModel(employeeModel);
    	employeeModel.addColumn("ID");
    	employeeModel.addColumn("Họ và tên");
    	employeeModel.addColumn("Ngày sinh");
    	employeeModel.addColumn("Giới tính");
    	employeeModel.addColumn("Địa chỉ");
    	employeeModel.addColumn("Chức vụ");
    	employeeModel.addColumn("Mật khẩu");
    	employeeModel.addColumn("Trạng thái");
    	employeeModel.addColumn("Hình ảnh");
    	employeeModel.addColumn("Chi nhánh");


		arrNhanVien = nvBUS.selectAll();
		for(int i=0; i<arrNhanVien.size(); i++) {
			NhanVienDTO nv = arrNhanVien.get(i);
			String maNV = nv.getMaNV();
			String hoTen = nv.getHoTen();
			Date ngaySinh = nv.getNgaySinh();
			String gioiTinh = nv.getGioiTinh();
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
		    String chiNhanh = nv.getChiNhanh();
			
			
		    Object[] row = {maNV, hoTen, ngaySinh, gioiTinh, diaChi, chucVu, matKhau, trangThai, imageIcon, chiNhanh};
			employeeModel.addRow(row);
		}
		
		
		//Điều chỉnh kích thước các cột
		TableColumnModel tcm = employeeTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(100);
		tcm.getColumn(1).setPreferredWidth(120);
		tcm.getColumn(2).setPreferredWidth(70);
		tcm.getColumn(3).setPreferredWidth(70);
		tcm.getColumn(4).setPreferredWidth(200);
		tcm.getColumn(5).setPreferredWidth(50);
		tcm.getColumn(6).setPreferredWidth(90);
		tcm.getColumn(7).setPreferredWidth(70);
		tcm.getColumn(8).setPreferredWidth(100);
		// **Thêm ImageRenderer vào cột "Hình ảnh"**
	    employeeTable.getColumnModel().getColumn(8).setCellRenderer(new ImageRenderer());


		employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize
    }
    

    
    private void newRoleDialog() {
    	//Tạo Jpanel chứa form nhập
    	JPanel panel = new JPanel(null); // row:3, column:2, hgap:5, wgap:5
    	
    	JLabel lblId = new JLabel("Mã chức vụ:");
		JTextField tfId = new JTextField();

    	
    	JLabel lblTenChucVu = new JLabel("Tên chức vụ:");
		JTextField tfTenChucVu = new JTextField(15);

		JLabel lblLuongCoBan = new JLabel("Lương cơ bản:");
		JTextField tfLuongCoBan = new JTextField(15);
		
		JLabel lblTrangThai = new JLabel("Trạng thái:");
		
    	JRadioButton rbOn = new JRadioButton("on");
    	JRadioButton rbOff = new JRadioButton("off");
		
		panel.add(lblId);
		panel.add(tfId);
		panel.add(lblTenChucVu);
		panel.add(tfTenChucVu);
		panel.add(lblLuongCoBan);
		panel.add(tfLuongCoBan);
		panel.add(lblTrangThai);
		panel.add(rbOn);
		


		// Hiển thị dialog với panel
		int result = JOptionPane.showConfirmDialog(this, panel, "Thêm chức vụ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		// Nếu nhấn OK
		if (result == JOptionPane.OK_OPTION) {
			String maCV = tfId.getText();
			String tenCV = tfTenChucVu.getText();
			float luongCB = Float.parseFloat(tfLuongCoBan.getText());
			String trangThai = rbOn.isSelected()?"on":"off";
			ChucVuDTO cv = new ChucVuDTO(maCV, tenCV, luongCB, trangThai);
			String message =cvBUS.insert(cv);
			String newRole = tfTenChucVu.getText().trim();


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
    	gbc.weightx = 0.75;
    	gbc.weighty = 1.0;
    	gbc.fill = GridBagConstraints.BOTH;
    	newEmployeeDialog.add(leftPanel, gbc);
    	
    	rightPanel = new JPanel(new GridBagLayout());
    	rightPanel.setBackground(Color.white);
    	rightPanel.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	gbc.weightx = 0.25;
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
    	gbc.weighty = 0.73;
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
    	gbc.weighty = 0.12;
    	gbc.fill = GridBagConstraints.BOTH;
    	gbc.insets = new Insets(3, 3, 3, 3);
    	leftPanel.add(bottomLeftPanel, gbc);
    
		
		JLabel lblName = new JLabel("Họ tên:");
		lblName.setBounds(10, 47, 100, 20);
		topLeftPanel.add(lblName);
    	JTextField txtName = new JTextField();
    	txtName.setBounds(100, 47, 100, 25);
    	topLeftPanel.add(txtName);
		
		JLabel lblDOB = new JLabel("Ngày sinh:");
		lblDOB.setBounds(10, 74, 100, 20);
		topLeftPanel.add(lblDOB);
    	JDateChooser dateChooser = new JDateChooser();
    	dateChooser.setBounds(100, 74, 123, 25);
    	topLeftPanel.add(dateChooser);
    	
    	JLabel lblGender = new JLabel("Giới tính:");
    	lblGender.setBounds(10, 101, 100, 20);
    	topLeftPanel.add(lblGender);
    	String[] genders = {"Nam", "Nữ"};
    	genderCombobox = new JComboBox<String>(genders);
    	genderCombobox.setBounds(100, 101, 70, 25);
    	topLeftPanel.add(genderCombobox);
    	
    	JLabel lblAddress = new JLabel("Địa chỉ:");
    	lblAddress.setBounds(10, 128, 100, 20);
    	topLeftPanel.add(lblAddress);
    	JTextField txtAddress = new JTextField();
    	txtAddress.setBounds(100, 128, 320, 25);
    	topLeftPanel.add(txtAddress);
    	
    	JLabel lblPhone = new JLabel("Số điện thoại:");
    	lblPhone.setBounds(10, 155, 100, 20);
    	topLeftPanel.add(lblPhone);
    	JTextField txtPhone = new JTextField();
    	txtPhone.setBounds(100, 155, 100, 25);
    	topLeftPanel.add(txtPhone);
    	
    	JLabel lblEmail = new JLabel("Email:");
    	lblEmail.setBounds(10, 182, 100, 20);
    	topLeftPanel.add(lblEmail);
    	JTextField txtEmail = new JTextField();
    	txtEmail.setBounds(100, 182, 160, 25);
    	topLeftPanel.add(txtEmail);
    	
    	JLabel lblRole = new JLabel("Chức vụ:");
    	lblRole.setBounds(10, 209, 130, 20);
    	topLeftPanel.add(lblRole);
    	
    	JComboBox<String> roleCombobox = new JComboBox<String>(roles);
    	//lấy dữ liệu chức vụ từ database để fill vào combobox
    	roleCombobox.setBounds(100, 209, 130, 25);
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
    	lblWorkplace.setBounds(10, 236, 100, 20);
    	topLeftPanel.add(lblWorkplace);
    	
    	ArrayList<KhoDTO> arrKho = khoBUS.selectAll();
        for(int i=0; i<arrKho.size(); i++) {        		
        	workplaces[i] =  arrKho.get(i).getTenKho();
        }
    	JComboBox<String> workplaceCombobox = new JComboBox<String>(workplaces);
    	workplaceCombobox.setBounds(100, 236, 130, 25);
    	topLeftPanel.add(workplaceCombobox);
    	
    	
    	JLabel lblStatus = new JLabel("Trạng thái:");
    	lblStatus.setBounds(10, 263, 100, 20);
    	topLeftPanel.add(lblStatus);
    	JRadioButton rbOn = new JRadioButton("on");
    	rbOn.setBounds(100, 263, 100, 25);
    	topLeftPanel.add(rbOn);
    	JRadioButton rbOff = new JRadioButton("off");
    	rbOff.setBounds(150, 263, 100, 25);
    	topLeftPanel.add(rbOff);
    	
    	JLabel lblImage = new JLabel("Hình ảnh:");
    	lblImage.setBounds(10, 290, 100, 20);
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
    	btnBrowse.setBounds(100, 290, 70, 20);
    	

    	topLeftPanel.add(btnBrowse);
    	
    	//middleLeftPanel
    	JLabel lblPassword;

    	
    	lblPassword = new JLabel("Mật khẩu:");
    	lblPassword.setBounds(10, 25, 100, 20);
    	middleLeftPanel.add(lblPassword);
    	
    	JTextField txtPassword;
    	
    	txtPassword = new JTextField();
    	txtPassword.setBounds(100, 25, 100, 25);
    	middleLeftPanel.add(txtPassword);
    	
    	//bottomLeftPanel
    	JButton btnSave = new ShadowButton("Lưu");
    	btnSave.setBounds(270, 25, 70, 25);
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
					selectedFilePath = selectedFile.getAbsolutePath();
					//Hiển thị đường dẫn của ảnh  chọn
					log("Đường dẫn của ảnh: " + selectedFilePath);
					ImageIcon icon = new ImageIcon(selectedFile.getAbsoluteFile().getAbsolutePath());
					Image img = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
					employeeImg.setIcon(new ImageIcon(img));
					
				}
			}

			
		});
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
					String hoTen = txtName.getText();
					java.util.Date utilDate = dateChooser.getDate(); // Lấy ngày từ JDateChooser
					System.out.println("Thời gian của biến utilDate: "+utilDate);
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Chuyển sang SQL Date
					System.out.println("Thời gian của biến sqlDate: "+sqlDate);
					String gioiTinh = genderCombobox.getSelectedItem().toString();
					String diaChi = txtAddress.getText();
					String sdt = txtPhone.getText();
					String email = txtEmail.getText();
					String trangThai = rbOn.isSelected()? "on":"off"; //rbOn có được chọn hay không, nếu isSelected thì giá của trangThai là "off", không thì là "off"
					String matKhau = txtPassword.getText();
					String chucVu = roleCombobox.getSelectedItem().toString();
					System.out.println("Role picked: " + chucVu);
					String noiLamViec = workplaceCombobox.getSelectedItem().toString();
					
					// Chuyển ảnh thành byte[]
					File imageFile = new File(selectedFilePath);
					byte[] hinhAnh = convertImageToBytes(imageFile);
					
					
					NhanVienDTO nv = new NhanVienDTO(hoTen, sqlDate, gioiTinh, diaChi, sdt, email, matKhau, hinhAnh, trangThai, chucVu, noiLamViec);
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
    
    private void fillChucVuCombobox(JComboBox<String> combobox) {
    	ArrayList<ChucVuDTO> arrChucVu = cvBUS.selectAll();
    	roleCombobox.removeAllItems(); // Xóa dữ liệu cũ (nếu có)
    	for(ChucVuDTO cv: arrChucVu) {
    		roleCombobox.addItem(cv.getTenCV());
    	}
    	roleCombobox.addItem("Thêm chức vụ...");
    }
    
    
    private void fillKhoCombobox(JComboBox<String> combobox) {
    	ArrayList<KhoDTO> arrKho = khoBUS.selectAll();
    	khoCombobox.removeAllItems(); // Xóa dữ liệu cũ (nếu có)
    	for(KhoDTO kho: arrKho) {
    		khoCombobox.addItem(kho.getTenKho());
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
        	gbc.weightx = 0.75;
        	gbc.weighty = 1.0;
        	gbc.fill = GridBagConstraints.BOTH;
        	updateEmployeeDialog.add(leftPanel, gbc);
        	
        	rightPanel = new JPanel(new GridBagLayout());
        	rightPanel.setBackground(Color.white);
        	rightPanel.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
        	gbc.gridx = 1;
        	gbc.gridy = 0;
        	gbc.weightx = 0.25;
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
        	gbc.weighty = 0.73;
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
        	gbc.weighty = 0.12;
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.insets = new Insets(3, 3, 3, 3);
        	leftPanel.add(bottomLeftPanel, gbc);
        	
        	JLabel lblId = new JLabel("Mã nhân viên:");
        	lblId.setBounds(10, 20, 100, 20);
        	topLeftPanel.add(lblId);
        	JTextField txtId = new JTextField();
    		txtId.setBounds(100, 20, 100, 25);
    		txtId.setEnabled(false);
    		topLeftPanel.add(txtId);
    		
    		JLabel lblName = new JLabel("Họ tên:");
    		lblName.setBounds(10, 47, 100, 20);
    		topLeftPanel.add(lblName);
        	JTextField txtName = new JTextField();
        	txtName.setBounds(100, 47, 100, 25);
        	topLeftPanel.add(txtName);
    		
    		JLabel lblDOB = new JLabel("Ngày sinh:");
    		lblDOB.setBounds(10, 74, 100, 20);
    		topLeftPanel.add(lblDOB);
        	JDateChooser dateChooser = new JDateChooser();
        	dateChooser.setBounds(100, 74, 123, 25);
        	topLeftPanel.add(dateChooser);
        	
        	JLabel lblGender = new JLabel("Giới tính:");
        	lblGender.setBounds(10, 101, 100, 20);
        	topLeftPanel.add(lblGender);
        	String[] genders = {"Nam", "Nữ"};
        	genderCombobox = new JComboBox<String>(genders);
        	genderCombobox.setBounds(100, 101, 70, 25);
        	topLeftPanel.add(genderCombobox);
        	
        	JLabel lblAddress = new JLabel("Địa chỉ:");
        	lblAddress.setBounds(10, 128, 100, 20);
        	topLeftPanel.add(lblAddress);
        	JTextField txtAddress = new JTextField();
        	txtAddress.setBounds(100, 128, 320, 25);
        	topLeftPanel.add(txtAddress);
        	
        	JLabel lblPhone = new JLabel("Số điện thoại:");
        	lblPhone.setBounds(10, 155, 100, 20);
        	topLeftPanel.add(lblPhone);
        	JTextField txtPhone = new JTextField();
        	txtPhone.setBounds(100, 155, 100, 25);
        	topLeftPanel.add(txtPhone);
        	
        	JLabel lblEmail = new JLabel("Email:");
        	lblEmail.setBounds(10, 182, 100, 20);
        	topLeftPanel.add(lblEmail);
        	JTextField txtEmail = new JTextField();
        	txtEmail.setBounds(100, 182, 160, 25);
        	topLeftPanel.add(txtEmail);
        	
        	JLabel lblRole = new JLabel("Chức vụ:");
        	lblRole.setBounds(10, 209, 130, 20);
        	topLeftPanel.add(lblRole);
        	
        	JComboBox<String> roleCombobox = new JComboBox<String>(roles);
        	//lấy dữ liệu chức vụ từ database để fill vào combobox
        	roleCombobox.setBounds(100, 209, 130, 25);
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
        	lblWorkplace.setBounds(10, 236, 100, 20);
        	topLeftPanel.add(lblWorkplace);
        	
        	ArrayList<KhoDTO> arrKho = khoBUS.selectAll();
            for(int i=0; i<arrKho.size(); i++) {        		
            	workplaces[i] =  arrKho.get(i).getTenKho();
            }
        	JComboBox<String> workplaceCombobox = new JComboBox<String>(workplaces);
        	workplaceCombobox.setBounds(100, 236, 130, 25);
        	topLeftPanel.add(workplaceCombobox);
        	
        	
        	JLabel lblStatus = new JLabel("Trạng thái:");
        	lblStatus.setBounds(10, 263, 100, 20);
        	topLeftPanel.add(lblStatus);
        	JRadioButton rbOn = new JRadioButton("on");
        	rbOn.setBounds(100, 263, 100, 25);
        	topLeftPanel.add(rbOn);
        	JRadioButton rbOff = new JRadioButton("off");
        	rbOff.setBounds(150, 263, 100, 25);
        	topLeftPanel.add(rbOff);
        	
        	JLabel lblImage = new JLabel("Hình ảnh:");
        	lblImage.setBounds(10, 290, 100, 20);
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
        	btnBrowse.setBounds(100, 290, 70, 20);
        	

        	topLeftPanel.add(btnBrowse);
        	
        	//middleLeftPanel
        	JLabel lblPassword;

        	
        	lblPassword = new JLabel("Mật khẩu:");
        	lblPassword.setBounds(10, 25, 100, 20);
        	middleLeftPanel.add(lblPassword);
        	
        	JTextField txtPassword;
        	
        	txtPassword = new JTextField();
        	txtPassword.setBounds(100, 25, 100, 25);
        	middleLeftPanel.add(txtPassword);
        	
        	//bottomLeftPanel
        	JButton btnSave = new ShadowButton("Lưu");
        	btnSave.setBounds(270, 25, 70, 25);
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
    					selectedFilePath = selectedFile.getAbsolutePath();
    					//Hiển thị đường dẫn của ảnh được
    					log("Đường dẫn ảnh: " + selectedFilePath);
    					ImageIcon icon = new ImageIcon(selectedFile.getAbsoluteFile().getAbsolutePath());
    					Image img = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
    					employeeImg.setIcon(new ImageIcon(img));
    					
    				}
    			}

    			
    		});
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
    					System.out.println("Class: NhanVienGUI | Method: updateEmployeeDialog: Thời gian của biến utilDate: "+utilDate);
    					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Chuyển sang SQL Date
    					System.out.println("Class: NhanVienGUI | Method: updateEmployeeDialog: Thời gian của biến sqlDate: "+sqlDate);
    					String gioiTinh = genderCombobox.getSelectedItem().toString();
    					String diaChi = txtAddress.getText();
    					String sdt = txtPhone.getText();
    					String email = txtEmail.getText();
    					String trangThai = rbOn.isSelected()? "on":"off"; //rbOn có được chọn hay không, nếu isSelected thì giá của trangThai là "off", không thì là "off"
    					String matKhau = txtPassword.getText();
    					String chucVu = roleCombobox.getSelectedItem().toString();
    					System.out.println("Class: NhanVienGUI | Method: updateEmployeeDialog: Role picked: " + chucVu);
    					String noiLamViec = workplaceCombobox.getSelectedItem().toString();
    					
    					
    					if(btnBrowse.isSelected()) { //Nếu có cập nhật ảnh 
    						File imageFile = new File(selectedFilePath);
        					byte[] hinhAnh = convertImageToBytes(imageFile);
	    					// Chuyển ảnh thành byte[]
	    					NhanVienDTO nv = new NhanVienDTO(maNV, hoTen, sqlDate, gioiTinh, diaChi, sdt, email, matKhau, hinhAnh, trangThai, chucVu, noiLamViec);
	    					System.out.println("Class: NhanVienGUI | Method: updateEmployeeDialog: "+ chucVu);
	    					System.out.println("Class: NhanVienGUI | Method: updateEmployeeDialog: selectedFilePathName: " + selectedFilePath);
	    					int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật nhân viên này?", "Xác nhận cập nhật", JOptionPane.OK_CANCEL_OPTION);
	    					if(dialogResult == JOptionPane.OK_OPTION) {
	    						// Gọi phương thức insert từ NhanVienBUS
		    					NhanVienBUS nvBUS = new NhanVienBUS();
		    					String message = nvBUS.update(nv);
		    					JOptionPane.showMessageDialog(null, message);
		    					
//		    					//Insert thông tin chỉnh sửa vào bảng LSCHINHSUA
//		    					String maNguoiChinhSua = "";
//		    					String maNguoiBiChinhSua = maNV;
//		    					Date thoiGian = Date.valueOf(LocalDate.now());	//Lấy thời gian hiện tại của hệ thống, sau đó ép kiểu về kiểu sql.Date
//		    					Scanner sc = new Scanner(System.in);
//		    					String noiDung = sc.nextLine();
	    					}
	    					
//	    					
    					}else {
    						int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật nhân viên này?", "Xác nhận cập nhật", JOptionPane.OK_CANCEL_OPTION);
	    					if(dialogResult == JOptionPane.OK_OPTION) {
	    						NhanVienDTO nv = new NhanVienDTO(maNV, hoTen, sqlDate, gioiTinh, diaChi, sdt, email, matKhau, trangThai, chucVu, noiLamViec);
	    						// Gọi phương thức insert từ NhanVienBUS
	        					NhanVienBUS nvBUS = new NhanVienBUS();
	        					String message = nvBUS.updateWithoutChangingImage(nv);
	        					JOptionPane.showMessageDialog(null, message);
		    					
//		    					//Insert thông tin chỉnh sửa vào bảng LSCHINHSUA
//		    					String maNguoiChinhSua = "";
//		    					String maNguoiBiChinhSua = maNV;
//		    					Date thoiGian = Date.valueOf(LocalDate.now());	//Lấy thời gian hiện tại của hệ thống, sau đó ép kiểu về kiểu sql.Date
//		    					Scanner sc = new Scanner(System.in);
//		    					String noiDung = sc.nextLine();
	    					}
    						
    					}
    					
    				}catch (Exception e2) {
    					e2.printStackTrace();
    					e2.getMessage();
    		            JOptionPane.showMessageDialog(updateEmployeeDialog, "Lỗi khi cập nhật nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    				}
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
        		System.out.println("Class: NhanVienGUI | Method: updateEmployeeDialog: "+ nvBUS.getRoleNameByRoleId(nv.getChucVu()));
        		System.out.println("Class: NhanVienGUI | Method: updateEmployeeDialog: "+ nv.getChucVu());
        		txtAddress.setText(nv.getDiaChi());
        		txtPhone.setText(nv.getSoDienThoai());
        		txtEmail.setText(nv.getEmail());
        		if(nv.getTrangThai().equalsIgnoreCase("on")) {	//lấy trạng thái của nhân viên, nếu là "off" thì set radio button rbOn lên
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
        	gbc.weightx = 0.75;
        	gbc.weighty = 1.0;
        	gbc.fill = GridBagConstraints.BOTH;
        	employeeDetailDialog.add(leftPanel, gbc);
        	
        	rightPanel = new JPanel(new GridBagLayout());
        	rightPanel.setBackground(Color.white);
        	rightPanel.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
        	gbc.gridx = 1;
        	gbc.gridy = 0;
        	gbc.weightx = 0.25;
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
        	gbc.weighty = 0.73;
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
        	gbc.weighty = 0.12;
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.insets = new Insets(3, 3, 3, 3);
        	leftPanel.add(bottomLeftPanel, gbc);
        	
        	JLabel lblId = new JLabel("Mã nhân viên:");
        	lblId.setBounds(10, 20, 100, 20);
        	topLeftPanel.add(lblId);
        	JTextField txtId = new JTextField();
    		txtId.setBounds(100, 20, 100, 25);
    		txtId.setEnabled(false);
    		topLeftPanel.add(txtId);
    		
    		JLabel lblName = new JLabel("Họ tên:");
    		lblName.setBounds(10, 47, 100, 20);
    		topLeftPanel.add(lblName);
        	JTextField txtName = new JTextField();
        	txtName.setBounds(100, 47, 100, 25);
        	txtName.setEnabled(false);
        	topLeftPanel.add(txtName);
    		
    		JLabel lblDOB = new JLabel("Ngày sinh:");
    		lblDOB.setBounds(10, 74, 100, 20);
    		topLeftPanel.add(lblDOB);
        	JDateChooser dateChooser = new JDateChooser();
        	dateChooser.setBounds(100, 74, 123, 25);
        	dateChooser.setEnabled(false);
        	topLeftPanel.add(dateChooser);
        	
        	JLabel lblGender = new JLabel("Giới tính:");
        	lblGender.setBounds(10, 101, 100, 20);
        	topLeftPanel.add(lblGender);
        	String[] genders = {"Nam", "Nữ"};
        	genderCombobox = new JComboBox<String>(genders);
        	genderCombobox.setBounds(100, 101, 70, 25);
        	genderCombobox.setEnabled(false);
        	topLeftPanel.add(genderCombobox);
        	
        	JLabel lblAddress = new JLabel("Địa chỉ:");
        	lblAddress.setBounds(10, 128, 100, 20);
        	topLeftPanel.add(lblAddress);
        	JTextField txtAddress = new JTextField();
        	txtAddress.setBounds(100, 128, 320, 25);
        	txtAddress.setEnabled(false);
        	topLeftPanel.add(txtAddress);
        	
        	JLabel lblPhone = new JLabel("Số điện thoại:");
        	lblPhone.setBounds(10, 155, 100, 20);
        	topLeftPanel.add(lblPhone);
        	JTextField txtPhone = new JTextField();
        	txtPhone.setBounds(100, 155, 100, 25);
        	txtPhone.setEnabled(false);
        	topLeftPanel.add(txtPhone);
        	
        	JLabel lblEmail = new JLabel("Email:");
        	lblEmail.setBounds(10, 182, 100, 20);
        	topLeftPanel.add(lblEmail);
        	JTextField txtEmail = new JTextField();
        	txtEmail.setBounds(100, 182, 160, 25);
        	txtEmail.setEnabled(false);
        	topLeftPanel.add(txtEmail);
        	
        	JLabel lblRole = new JLabel("Chức vụ:");
        	lblRole.setBounds(10, 209, 100, 20);
        	topLeftPanel.add(lblRole);

        	roleCombobox = new JComboBox<String>(roles);
        	roleCombobox.setBounds(100, 209, 130, 25);
        	roleCombobox.setEnabled(false);
        	topLeftPanel.add(roleCombobox);

        	
        	JLabel lblBaseSalary = new JLabel("Lương cơ bản:");
        	lblBaseSalary.setBounds(10, 236, 100, 20);
        	topLeftPanel.add(lblBaseSalary);
        	JTextField txtBaseSalary = new JTextField();
        	txtBaseSalary.setBounds(100, 236, 130, 25);
        	txtBaseSalary.setEnabled(false);
        	topLeftPanel.add(txtBaseSalary);
        	
        	
        	JLabel lblWorkplace = new JLabel("Nơi làm việc:");
        	lblWorkplace.setBounds(10, 263, 100, 20);
        	topLeftPanel.add(lblWorkplace);
        	
        	ArrayList<KhoDTO> arrKho = khoBUS.selectAll();
            for(int i=0; i<arrKho.size(); i++) {        		
            	workplaces[i] =  arrKho.get(i).getTenKho();
            }
        	JComboBox<String> workplaceCombobox = new JComboBox<String>(workplaces);
        	workplaceCombobox.setBounds(100, 263, 130, 25);
        	workplaceCombobox.setEnabled(false);
        	topLeftPanel.add(workplaceCombobox);
        	
        	JLabel lblStatus = new JLabel("Trạng thái:");
        	lblStatus.setBounds(10, 290, 100, 20);
        	topLeftPanel.add(lblStatus);
        	JRadioButton rbOn = new JRadioButton("on");
        	rbOn.setBounds(100, 290, 100, 20);
        	rbOn.setEnabled(false);
        	topLeftPanel.add(rbOn);

        	JRadioButton rbOff = new JRadioButton("off");
        	rbOff.setBounds(150, 290, 100, 20);
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
        	lblPassword.setBounds(10, 25, 100, 20);
        	middleLeftPanel.add(lblPassword);
        	
        	JTextField txtPassword;
        	
        	txtPassword = new JTextField();
        	txtPassword.setBounds(100, 25, 100, 25);
        	txtPassword.setEnabled(false);
        	middleLeftPanel.add(txtPassword);
        	
        	//bottomLeftPanel

        	
        	
        	//Lấy giá trị từ csdl và truyền vào các trường dữ liệu
        	String maNV = employeeTable.getValueAt(selectedRow, 0).toString();	
        	NhanVienDTO nv = nvBUS.selectById(maNV);
        	if(nv!=null) {
        		txtId.setText(nv.getMaNV());
        		txtName.setText(nv.getHoTen());
        		dateChooser.setDate(nv.getNgaySinh());
        		genderCombobox.setSelectedItem(nv.getGioiTinh());	
        		roleCombobox.setSelectedItem(nvBUS.getRoleNameByRoleId(nv.getChucVu()));	//lấy mã chưc vụ của nhân viên để lấy tên chức vụ
        		log("roleName="+ nvBUS.getRoleNameByRoleId(nv.getChucVu()));
        		log("role="+ nv.getChucVu());
        		txtAddress.setText(nv.getDiaChi());
        		txtPhone.setText(nv.getSoDienThoai());
        		txtEmail.setText(nv.getEmail());
        		if(nv.getTrangThai().equalsIgnoreCase("on")) {	//lấy trạng thái của nhân viên, nếu là "off" thì set radio button rbOn lên
        			rbOn.setSelected(true);
        			rbOff.setSelected(false);
        		}else {
        			rbOn.setSelected(false);
        			rbOff.setSelected(true);
        		}
        		
        		txtPassword.setText(nv.getMatKhau());
        		
        		// Lấy dữ liệu ảnh từ database (kiểu VARBINARY)
    		    byte[] imageData = nv.getHinhAnh(); // Phương thức này phải trả về byte[]
    		    if (imageData != null) {
    		        // Chuyển đổi byte[] thành ImageIcon
    		        Image image = Toolkit.getDefaultToolkit().createImage(imageData);
    		        Image scaledImage = image.getScaledInstance(450, 450, Image.SCALE_SMOOTH); // Resize ảnh
    		        //rightPanel
					employeeImg.setIcon(new ImageIcon(scaledImage));
					
    		    }
    		    
    		    //lấy lương cơ bản từ bảng chức vụ
    		    DecimalFormat df = new DecimalFormat("#,###");
    		    txtBaseSalary.setText(df.format(nvBUS.getBaseSalaryByRoleID(nv.getChucVu())) + " VND");
        		
        		
        	}
        	
//        	bottomLeftPanel.add(btnSave);
        	
        	employeeDetailDialog.setLocationRelativeTo(this);
        	employeeDetailDialog.setVisible(true);
    	}else {
    		JOptionPane.showMessageDialog(null,"Vui lòng chọn nhân viên để xem chi tiết");
    	}
    	
    }
    
    public void hienThiAnhNhanVien(JTable table, JPanel panel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNV = (String)employeeModel.getValueAt(selectedRow, 0);
            NhanVienDTO nv = nvBUS.selectById(maNV);

            // Xóa hết thành phần cũ trong panel
            panel.removeAll();
            panel.revalidate();
            panel.repaint();

            JLabel employeeImg = new JLabel();
            employeeImg.setHorizontalAlignment(JLabel.CENTER);
            employeeImg.setVerticalAlignment(JLabel.CENTER);
            employeeImg.setBounds(0, 0, panel.getWidth(), panel.getHeight()); // full panel
            // (Hoặc bạn vẫn dùng setPreferredSize(new Dimension(200,200)) nếu muốn cố định kích thước)

            byte[] imageData = nv.getHinhAnh();
            if (imageData != null) {
                Image image = Toolkit.getDefaultToolkit().createImage(imageData);
                Image scaledImage = image.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
                employeeImg.setIcon(new ImageIcon(scaledImage));
            }

            panel.add(employeeImg);
            panel.revalidate();
            panel.repaint();
        }
    }

    
    public void deleteEmployee(JTable employeeTable) {
    	int selectedRow = employeeTable.getSelectedRow();
    	if(selectedRow != -1) {
    		int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.OK_CANCEL_OPTION);
    		if(dialogResult == JOptionPane.OK_OPTION) {
    			String manv = (String)employeeModel.getValueAt(selectedRow, 0);
    			NhanVienDTO nv = new NhanVienDTO(manv);	//Vì NhanVienDAO và NhanVienBUS có tham số là đối tượng nhân viên nên phải tạo 1 nv, chứ k thì dùng mã nv cho nhanh r, mà do mình set ở DAOInterface là tham số là NhanVienDTO nên chịu khó
    			String message = nvBUS.deleteEmployee(nv);
    			if(message.equals("Xóa nhân viên thành công")){
                    employeeModel.removeRow(selectedRow);
                    arrNhanVien.remove(selectedRow);
                }
                JOptionPane.showMessageDialog(this, message);
    		}
    	}else {
    		JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa");
    	}
    	
    }
    
    private void refreshList(){
        // Xóa tất cả các dòng trong mô hình bảng
        employeeModel.setRowCount(0);
        employeeModel.setColumnCount(0);
        loadNhanVienList();
        sortComboBox.setSelectedIndex(0);
        genderCombobox.setSelectedIndex(0);
        roleCombobox.setSelectedItem("");
    }
    
    
    private void searchPerformed(JTable tb){
        String searchContent = txtTimKiem.getText().trim(); // Lấy nội dung tìm kiếm từ textField và loại bỏ khoảng trắng ở đầu và cuối chuỗi
        if (!searchContent.isEmpty()) { // Kiểm tra xem nội dung tìm kiếm có rỗng không
            ArrayList<NhanVienDTO> dsTimKiem = new ArrayList<>(); // Tạo một danh sách để lưu trữ kết quả tìm kiếm

            // Duyệt qua danh sách sản phẩm và lọc những sản phẩm thỏa mãn điều kiện tìm kiếm
            boolean found = false;
            for (NhanVienDTO nv: arrNhanVien) {
                // Kiểm tra xem thông tin của nhân viên có chứa chuỗi tìm kiếm hay không (sử dụng phương thức contains)
                if (nv.getMaNV().toLowerCase().contains(searchContent.toLowerCase().trim()) ||
                    nv.getHoTen().toLowerCase().contains(searchContent.toLowerCase().trim())||
                    nv.getGioiTinh().toLowerCase().contains(searchContent.toLowerCase().trim())||
                    nv.getDiaChi().toLowerCase().contains(searchContent.toLowerCase().trim())||
                    nv.getSoDienThoai().toLowerCase().contains(searchContent.toLowerCase().trim())||
                    nv.getChucVu().toLowerCase().contains(searchContent.toLowerCase().trim()))
                 {
                    dsTimKiem.add(nv); // Nếu sản phẩm thỏa mãn, thêm vào danh sách lọc
                    found = true;
                }
                
            }
            // Kiểm tra nếu không tìm thấy sản phẩm nào
            if(!found){
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên");
                refreshList();
                return; // Kết thúc phương thức sau khi hiển thị thông báo
            }
            
            // Xóa tất cả các dòng hiện có trong bảng
            DefaultTableModel tableModel = (DefaultTableModel) tb.getModel();
            tableModel.setRowCount(0);

            // Thêm các sản phẩm thỏa mãn vào bảng
            for (NhanVienDTO nv : dsTimKiem) {
    			String maNV = nv.getMaNV();
    			String hoTen = nv.getHoTen();
    			Date ngaySinh = nv.getNgaySinh();
    			String gioiTinh = nv.getGioiTinh();
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
    			
    			
    		    Object[] row = {maNV, hoTen, ngaySinh, gioiTinh, diaChi, chucVu, matKhau, trangThai, imageIcon};
                tableModel.addRow(row);
            }
        } else {
            // Nếu người dùng không nhập nội dung tìm kiếm, thực hiện làm mới bảng để hiển thị tất cả sản phẩm
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm");
            refreshList();
        }
    }
    
    private void sortAZ(){
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(employeeModel);
        employeeTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        
        int columnIndexSort = 1; // 1 là chỉ số cột tên nhân viên, cần sắp xếp
        sortKeys.add(new RowSorter.SortKey(columnIndexSort, SortOrder.ASCENDING));
        
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
    private void sortZA(){
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(employeeModel);
        employeeTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        
        int columnIndexSort = 1; // 1 là chỉ số cột tên nhân viên, cần sắp xếp
        sortKeys.add(new RowSorter.SortKey(columnIndexSort, SortOrder.DESCENDING));
        
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
    private void sortGenderMale() {
    	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(employeeModel);
        employeeTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        
        int columnIndexSort = 3; // 3 là chỉ số cột giới tính nhân viên, cần sắp xếp
        sortKeys.add(new RowSorter.SortKey(columnIndexSort, SortOrder.ASCENDING));
        
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
    private void sortGenderFeMale() {
    	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(employeeModel);
        employeeTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        
        int columnIndexSort = 3; // 3 là chỉ số cột giới tính nhân viên, cần sắp xếp
        sortKeys.add(new RowSorter.SortKey(columnIndexSort, SortOrder.DESCENDING));
        
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
    private void sortRole(String role) {
    	ArrayList<NhanVienDTO> dsTimKiem = nvBUS.selectAllByRoleName(role);
    	
    	// Xóa tất cả các dòng hiện có trong bảng
        DefaultTableModel tableModel = (DefaultTableModel) employeeTable.getModel();
        tableModel.setRowCount(0);

        // Thêm các sản phẩm thỏa mãn vào bảng
        for (NhanVienDTO nv : dsTimKiem) {
			String maNV = nv.getMaNV();
			String hoTen = nv.getHoTen();
			Date ngaySinh = nv.getNgaySinh();
			String gioiTinh = nv.getGioiTinh();
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
			
			
		    Object[] row = {maNV, hoTen, ngaySinh, gioiTinh, diaChi, chucVu, matKhau, trangThai, imageIcon};
            tableModel.addRow(row);
        }
    
    }
    
    private void sortKho(String kho) {
    	ArrayList<NhanVienDTO> dsTimKiem = nvBUS.selectAllByWarehouseName(kho);
    	
    	// Xóa tất cả các dòng hiện có trong bảng
        DefaultTableModel tableModel = (DefaultTableModel) employeeTable.getModel();
        tableModel.setRowCount(0);

        // Thêm các sản phẩm thỏa mãn vào bảng
        for (NhanVienDTO nv : dsTimKiem) {
			String maNV = nv.getMaNV();
			String hoTen = nv.getHoTen();
			Date ngaySinh = nv.getNgaySinh();
			String gioiTinh = nv.getGioiTinh();
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
		    String chiNhanh = nv.getChiNhanh();
			
			
		    Object[] row = {maNV, hoTen, ngaySinh, gioiTinh, diaChi, chucVu, matKhau, trangThai, imageIcon, chiNhanh};
            tableModel.addRow(row);
        }
    
    }
    
    
    private void hienThiThongTinChamCong(JTable table) {
    	int selectedRow = table.getSelectedRow();
    	if(selectedRow!=-1) {
    		DefaultTableModel model = (DefaultTableModel)table.getModel();
    		String maNV = (String)model.getValueAt(selectedRow, 0);
    		int thangCC = Integer.parseInt(monthCombobox.getSelectedItem().toString());
    		int namCC = Integer.parseInt(yearCombobox.getSelectedItem().toString());
    		log("maNV=" + maNV);
    		
    		dataSoNgayCong.setText(String.valueOf(nvBUS.getSoNgayCong(thangCC, namCC, maNV)));
    		dataSoNgayNghiPhepCoLuong.setText(String.valueOf(nvBUS.getSoNgayNghiPhepCoLuong(thangCC, namCC, maNV)));
    		dataSoNgayNghiPhepKhongLuong.setText(String.valueOf(nvBUS.getSoNgayNghiPhepKhongLuong(thangCC, namCC, maNV)));
    		dataSoNgayNghiKhongPhep.setText(String.valueOf(nvBUS.getSoNgayNghiKhongPhep(thangCC, namCC, maNV)));
    		dataSoGioOT.setText(String.valueOf(nvBUS.getSoGioTangCa(thangCC, namCC, maNV)));
    		
    		int soNgayCong = Integer.parseInt(dataSoNgayCong.getText());
    		log("soNgayCong=" + soNgayCong);
    		int soNgayNghiPhepCoLuong = Integer.parseInt(dataSoNgayNghiPhepCoLuong.getText());
    		log("soNgayNghiPhepCoLuong=" + soNgayNghiPhepCoLuong);
    		int tong = soNgayCong + soNgayNghiPhepCoLuong;
    		lblTongSoNgayTinhLuong.setText("Tổng số ngày tính lương = " + soNgayCong + " + " + soNgayNghiPhepCoLuong + " = " + tong);
    	}
    }
    
    private void sortKho() {
    	
    }
    
    
	// hàm hiển thị thông tin dòng code
	public static void log(String message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
		System.out.println(element.getClassName() + " | method: " + element.getMethodName() + " | line: "
				+ element.getLineNumber() + " | " + message);
	}
}
