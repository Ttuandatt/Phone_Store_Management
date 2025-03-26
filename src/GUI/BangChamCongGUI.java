package GUI;

import BUS.BangChamCongBUS;
import BUS.SanPhamBUS;

import Components.ShadowButton;
import DTO.*;
import DAO.SanPhamDAO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;



public class BangChamCongGUI extends JPanel{

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
	BangChamCongBUS bccBUS = new BangChamCongBUS();
    JTable bangChamCongTable;
    DefaultTableModel bangChamCongModel = new DefaultTableModel();
    ArrayList<BangChamCongDTO> arrBangChamCong = new ArrayList<BangChamCongDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
<<<<<<< HEAD
=======
=======
	SanPhamBUS productBUS = new SanPhamBUS();
    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<SanPhamDTO> productArr = new ArrayList<SanPhamDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
>>>>>>> 59a70570a1617ba4a09498c7fd48841322b0010b
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
    private JComboBox sortComboBox;
    private JPanel bangChamCongContent;
    private JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
	
	
	//Constructor
    public BangChamCongGUI(){
        initComponents();
        loadBangChamCongList();
    }
    
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents() {
        setLayout(new GridBagLayout()); //set Layout
        GridBagConstraints gbc = new GridBagConstraints();
        bangChamCongContent = new JPanel();
        bangChamCongContent.setBackground(Color.white);
        bangChamCongContent.setLayout(new GridBagLayout());
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(bangChamCongContent, gbc); // Thêm vào ProductsGUI
        

        JPanel topPanel, middlePanel, bottomPanel;
        //set thông số cho 2 panel
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(Color.white);
        gbc.weightx = 1.0;
        gbc.weighty = 0.24;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        bangChamCongContent.add(topPanel, gbc);
        
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridBagLayout());
<<<<<<< HEAD
        middlePanel.setBackground(Color.white);
=======
        middlePanel.setBackground(Color.green);
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
        middlePanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        bangChamCongContent.add(middlePanel, gbc);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.setBackground(Color.white);
        // Tạo viền với độ dày 3px và màu xám
        Border lineBorder = BorderFactory.createLineBorder(Color.lightGray, 2);
        // Tạo TitledBorder với tiêu đề "Thông tin chi tiết"
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Thông tin chi tiết");
        // Chỉnh cỡ chữ, kiểu chữ
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 13)); // Font: Arial, đậm, size 16
        titledBorder.setTitleColor(Color.black); // Đổi màu chữ tiêu đề thành xanh

        // Áp dụng border cho bottomPanel
        bottomPanel.setBorder(titledBorder);
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 3, 0, 3);
        bangChamCongContent.add(bottomPanel, gbc);
        
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
        gbc.insets = new Insets(5, 5, 5, 6);
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
                newBangChamCongDialog();
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
//            	updateEmployeeDialog();
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
//                employeeDetailDialog();
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
        gbc.insets = new Insets(5, 5, 5, 6);
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
                JOptionPane.showMessageDialog(null, "Refresh button clicked!");
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
        bangChamCongTable = new JTable();
        JScrollPane sp = new JScrollPane(bangChamCongTable);
        gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		middlePanel.add(sp, gbc);
    }

    
	private void loadBangChamCongList() {
		bangChamCongTable.setDefaultEditor(Object.class, null);

		bangChamCongTable.setModel(bangChamCongModel);
<<<<<<< HEAD
		bangChamCongModel.addColumn("Mã bảng chấm công");
		bangChamCongModel.addColumn("Tháng chấm công");
		bangChamCongModel.addColumn("Năm chấm công");
=======
		bangChamCongModel.addColumn("Mã BCC");
		bangChamCongModel.addColumn("Tháng CC");
		bangChamCongModel.addColumn("Năm CC");
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
		bangChamCongModel.addColumn("Số ngày làm");
    	bangChamCongModel.addColumn("Số ngày nghỉ phép");
    	bangChamCongModel.addColumn("Số ngày nghỉ không phép");
    	bangChamCongModel.addColumn("Số giờ tăng ca");
<<<<<<< HEAD
    	bangChamCongModel.addColumn("Mã nhân viên"); 
    	
    	arrBangChamCong = bccBUS.selectAll();
    	for(int i=0; i<arrBangChamCong.size(); i++) {
    		BangChamCongDTO bcc = arrBangChamCong.get(i);
    		String maBCC = bcc.getMaBCC();
    		int thangCC = bcc.getThangCC();
    		int namCC = bcc.getNamCC();
    		int soNgayLam = bcc.getSoNgayLam();
    		int soNgayNghiPhep = bcc.getSoNgayNghiPhep();
    		int soNgayNghiKhongPhep = bcc.getSoNgayNghiKhongPhep();
    		int soGioTangCa = bcc.getSoGioOT();
    		String maNV = bcc.getMaNV();
    		
    		Object[] row = {maBCC, thangCC, namCC, soNgayLam, soNgayNghiPhep, soNgayNghiKhongPhep, soGioTangCa, maNV};
    		bangChamCongModel.addRow(row);
    		
    	}
    	
    	//Điều chỉnh kích thước các cột
    	TableColumnModel tcm = bangChamCongTable.getColumnModel();
    	tcm.getColumn(0).setPreferredWidth(200);
		tcm.getColumn(1).setPreferredWidth(150);
		tcm.getColumn(2).setPreferredWidth(150);
		tcm.getColumn(3).setPreferredWidth(150);
		tcm.getColumn(4).setPreferredWidth(200);
		tcm.getColumn(5).setPreferredWidth(200);
		tcm.getColumn(6).setPreferredWidth(107);
		tcm.getColumn(7).setPreferredWidth(110);
=======
    	bangChamCongModel.addColumn("Mã NV"); 
    	
    	
    	
    	//Điều chỉnh kích thước các cột
    	TableColumnModel tcm = bangChamCongTable.getColumnModel();
    	tcm.getColumn(0).setPreferredWidth(100);
		tcm.getColumn(1).setPreferredWidth(40);
		tcm.getColumn(2).setPreferredWidth(50);
		tcm.getColumn(3).setPreferredWidth(100);
		tcm.getColumn(4).setPreferredWidth(200);
		tcm.getColumn(5).setPreferredWidth(200);
		tcm.getColumn(6).setPreferredWidth(100);
		tcm.getColumn(7).setPreferredWidth(70);
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
		
		bangChamCongTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize

	}
<<<<<<< HEAD
	
	private void newBangChamCongDialog() {
		JDialog newBangChamCongDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm bảng chấm công", true);
		newBangChamCongDialog.setSize(300, 300);
		newBangChamCongDialog.setLayout(null);

		JLabel lblMaBCC, lblThangCC, lblNamCC, lblMaNV;
		lblMaBCC = new JLabel("Mã bảng chấm công");
		lblMaBCC.setBounds(10, 10, 150, 20);
		newBangChamCongDialog.add(lblMaBCC);
		
		lblThangCC = new JLabel("Tháng chấm công");
		lblThangCC.setBounds(10, 60, 150, 20);
		newBangChamCongDialog.add(lblThangCC);
		
		lblNamCC = new JLabel("Năm chấm công");
		lblNamCC.setBounds(10, 110, 150, 20);
		newBangChamCongDialog.add(lblNamCC);
		
		lblMaNV = new JLabel("Mã nhân viên");
		lblMaNV.setBounds(10, 160, 150, 20);
		newBangChamCongDialog.add(lblMaNV);
		
		JTextField txtMaBCC, txtThangCC, txtNamCC, txtMaNV;
		txtMaBCC = new JTextField();
		txtMaBCC.setBounds(10, 30, 260, 25);
		newBangChamCongDialog.add(txtMaBCC);
		
		txtThangCC = new JTextField();
		txtThangCC.setBounds(10, 80, 260, 25);
		newBangChamCongDialog.add(txtThangCC);
		
		txtNamCC = new JTextField();
		txtNamCC.setBounds(10, 130, 260, 25);
		newBangChamCongDialog.add(txtNamCC);
		
		txtMaNV = new JTextField();
		txtMaNV.setBounds(10, 180, 260, 25);
		newBangChamCongDialog.add(txtMaNV);
		
		JButton btnSave = new ShadowButton("Lưu");
		btnSave.setBounds(200, 230, 70, 25);
		newBangChamCongDialog.add(btnSave);
		
		newBangChamCongDialog.setLocationRelativeTo(this);
		newBangChamCongDialog.setVisible(true);
	}
=======
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
}
