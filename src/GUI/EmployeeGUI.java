package GUI;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import Components.ShadowButton;


public class EmployeeGUI extends JPanel{

	ProductsBUS productBUS = new ProductsBUS();
    JTable employeeTable;
    DefaultTableModel employeeModel = new DefaultTableModel();
    ArrayList<ProductsDTO> productArr = new ArrayList<ProductsDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
    private JComboBox sortComboBox, genderCombobox, departmentCombobox;
    private JPanel productContent;
    private JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
	
	
	//Constructor
    public EmployeeGUI(){
        initComponents();
        loadEmployeeList();
    }
    
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
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
        bottomPanel.setBorder(BorderFactory.createTitledBorder(""));
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
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Update
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Add button clicked!");
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
                JOptionPane.showMessageDialog(null, "Update button clicked!");
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
        ImageIcon iconDetail = new ImageIcon("C:\\\\Users\\\\ACER\\\\Dropbox\\\\My PC (LAPTOP-UGP9QJUT)\\\\Documents\\\\ITstudies\\\\JAVA_BACKEND\\\\JAVA PROJECTS\\\\Phone_Store_Management_HTTTDN\\\\Phone_Store_Management\\\\src\\\\img\\\\info.png\\"); // Đặt đường dẫn ảnh ở đây
        Image imgDetail = iconDetail.getImage();
        Image newImgDetail = imgDetail.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        if (iconDelete.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconDetail = new ImageIcon(newImgDetail);

        // Tạo nút Detail
        JButton btnDetail = new ShadowButton("Info", scaledIconDetail);
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
                JOptionPane.showMessageDialog(null, "Info button clicked!");
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
		JButton btnPrint = new ShadowButton("Print", scaledIconPrint);
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
        
        String[] departments = {"Phòng ban", "Thêm phòng ban mới..."};
        departmentCombobox = new JComboBox<String>(departments);
        departmentCombobox.setBounds(185, 24, 150, 25);
        searchInputPanel.add(departmentCombobox);
        departmentCombobox.addItemListener(e -> {
        	if(e.getStateChange() == ItemEvent.SELECTED) {
        		String selected = (String)departmentCombobox.getSelectedItem();
        		if("Add new department...".equals(selected)) {
        			newDepartmentDialog();
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
		attendancePanel.setBorder(BorderFactory.createTitledBorder(""));
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
    	employeeModel  = new DefaultTableModel();
    	employeeTable.setModel(employeeModel);
    	employeeModel.addColumn("ID");
    	employeeModel.addColumn("Họ và tên");
    	employeeModel.addColumn("Ngày sinh");
    	employeeModel.addColumn("Địa chỉ");
    	employeeModel.addColumn("Chức vụ");
    	employeeModel.addColumn("Mật khẩu");
    	employeeModel.addColumn("Trạng thái");

		
		//Điều chỉnh kích thước các cột
		TableColumnModel tcm = employeeTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(50);
		tcm.getColumn(1).setPreferredWidth(200);
		tcm.getColumn(2).setPreferredWidth(100);
		tcm.getColumn(3).setPreferredWidth(250);
		tcm.getColumn(4).setPreferredWidth(100);
		tcm.getColumn(5).setPreferredWidth(100);
		tcm.getColumn(6).setPreferredWidth(78);

		employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize
    }
    
    public void newDepartmentDialog() {
    	//Tạo Jpanel chứa form nhập
    	JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5)); // row:3, column:2, hgap:5, wgap:5
    	
    	
    	JLabel nameLabel = new JLabel("Tên phòng ban:");
		JTextField nameField = new JTextField(15);

		JLabel managerLabel = new JLabel("Trưởng phòng (ID):");
		JTextField managerField = new JTextField(15);
		
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(managerLabel);
		panel.add(managerField);

		// Hiển thị dialog với panel
		int result = JOptionPane.showConfirmDialog(this, panel, "Thêm phòng ban", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		// Nếu nhấn OK
		if (result == JOptionPane.OK_OPTION) {
			String newDepartment = nameField.getText().trim();
			String managerID = managerField.getText().trim();


			if (!newDepartment.isEmpty() && !managerID.isEmpty()) {
				departmentCombobox.insertItemAt(newDepartment, departmentCombobox.getItemCount() - 1);
				departmentCombobox.setSelectedItem(newDepartment);
				JOptionPane.showMessageDialog(this,
						"Department Added:\nName: " + newDepartment + "\nDepartment Manager: " + managerID);
			} else {
				JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}
}
