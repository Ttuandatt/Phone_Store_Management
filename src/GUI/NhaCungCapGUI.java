package GUI;

import BUS.NhaCungCapBUS;
import Components.ShadowButton;
import DAO.NhaCungCapDAO;
import DTO.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import popup.PopupNhaCungCap;



public class NhaCungCapGUI extends JPanel{
	JTable suppliersTable;
	NhaCungCapBUS suppliersBUS = new NhaCungCapBUS();
	DefaultTableModel suppliersModel = new DefaultTableModel();
	ArrayList<NhaCungCapDTO> suppliersArr = new ArrayList<NhaCungCapDTO>(); // Tạo ArrayList sp với kiểu là ProductsDTO
	private JComboBox sortComboBox;
	private JPanel nhaCungCapContent;
	private JTextField txtTimKiem;
	
	//Constructor
    public NhaCungCapGUI(){
        initComponents();
        loadNhaCungCapList();
    }
    
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents() {
        setLayout(new GridBagLayout()); //set Layout
        GridBagConstraints gbc = new GridBagConstraints();
        nhaCungCapContent = new JPanel();
        nhaCungCapContent.setBackground(Color.white);
        nhaCungCapContent.setLayout(new GridBagLayout());
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nhaCungCapContent, gbc); // Thêm vào ProductsGUI
        
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
        nhaCungCapContent.add(topPanel, gbc);
        
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
        nhaCungCapContent.add(bottomPanel, gbc);
        
        
        
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
//                newEmployeeDialog();
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
//                deleteEmployee(employeeTable);
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
//						sortAZ();
						break;
					case "Z-A":
//						sortZA();
						break;
				}
			}
		});
        
                
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(375,  24,  260, 25);
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
                searchPerformed(suppliersTable);
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

        
        suppliersTable = new JTable();
        JScrollPane sp = new JScrollPane(suppliersTable);
        gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(sp, gbc);
    }

    
	private void loadNhaCungCapList() {
		suppliersTable.setDefaultEditor(Object.class, null);
		
		suppliersTable.setModel(suppliersModel);
		suppliersModel.addColumn("ID");
		suppliersModel.addColumn("Tên NCC");
		suppliersModel.addColumn("SDT");
		suppliersModel.addColumn("Email");
		suppliersModel.addColumn("Địa chỉ");
		suppliersModel.addColumn("Trạng thái");
		
		suppliersArr = suppliersBUS.selectAll();
		for (int i = 0; i < suppliersArr.size(); i++) {
			NhaCungCapDTO ncc = suppliersArr.get(i);
			String maNCC = ncc.getMaNCC();
			log("maNCC="+maNCC);
			String tenNCC = ncc.getTenNCC();
			String sdt = ncc.getSdt();
			String email = ncc.getEmail();
			String diaChi = ncc.getDiaChi();
			String trangThai = ncc.getTrangthai();
			Object[] row = { maNCC, tenNCC, sdt, email, diaChi, trangThai };
			suppliersModel.addRow(row);
		}
		TableColumnModel tcm = suppliersTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(100);
		tcm.getColumn(1).setPreferredWidth(120);
		tcm.getColumn(2).setPreferredWidth(100);
		tcm.getColumn(3).setPreferredWidth(70);
		tcm.getColumn(4).setPreferredWidth(200);
		tcm.getColumn(5).setPreferredWidth(100);

		suppliersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize

	}
         private void addPerformed(JTable tb){
             PopupNhaCungCap frameThem = new PopupNhaCungCap("Thêm nhà cung cấp", "THÊM NHÀ CUNG CẤP","add");
    }
        private void refreshList(){
        //Xóa tất cả các dòng trong model table
        suppliersModel.setRowCount(0);
        loadNhaCungCapList();
        txtTimKiem.setText("");
        
    }
       private void deletePerformed(JTable tb){
    int selectedRow = tb.getSelectedRow();
    if(selectedRow != -1){
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhà cung cấp?", "Xác nhận xóa", JOptionPane.OK_CANCEL_OPTION);
       if (dialogResult == JOptionPane.OK_OPTION) {
    String mancc = (String) suppliersModel.getValueAt(selectedRow, 0);

    // Tạo đối tượng DTO để truyền vào BUS
    NhaCungCapDTO ncc = new NhaCungCapDTO();
    ncc.setMaNCC(mancc);

    String message = suppliersBUS.deleteNhaCungCap(ncc);
    if ("Xóa nhà cung cấp thành công".equals(message)) {
        suppliersModel.removeRow(selectedRow);
        suppliersArr.remove(selectedRow);
    }
    JOptionPane.showMessageDialog(null, message);
}

    } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 dòng để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
}

       private void updatePerformed(JTable tb){
        int selected = suppliersTable.getSelectedRow();
        if(selected!=-1){
            PopupNhaCungCap frameSua = new PopupNhaCungCap("Sửa nhà cung cấp", "SỬA NHÀ CUNG CẤP", "fix");
        }
        else
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
       
          private void searchPerformed(JTable tb){
        String searchContent = txtTimKiem.getText().trim();
        if(!searchContent.isEmpty()){
            ArrayList<NhaCungCapDTO> dsTimKiem = new ArrayList<>();
            
            boolean found = false;
            for(NhaCungCapDTO ncc: suppliersArr){
                if(ncc.getMaNCC().toLowerCase().contains(searchContent.toLowerCase()) ||
                   ncc.getTenNCC().toLowerCase().contains(searchContent.toLowerCase())||
                   ncc.getDiaChi().toLowerCase().contains(searchContent.toLowerCase())||
                   ncc.getSdt().toLowerCase().contains(searchContent.toLowerCase())||
                   ncc.getEmail().toLowerCase().contains(searchContent.toLowerCase())
                        
                        ) 
                {
                    dsTimKiem.add(ncc);
                    found = true;
                }
            }
            
            if(!found){
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp");
                refreshList();
                return;
            }
            
            DefaultTableModel tableModel = (DefaultTableModel) tb.getModel();
            tableModel.setRowCount(0);
            
            for(NhaCungCapDTO nhaCungCap: dsTimKiem){
                Object[] row ={
                    nhaCungCap.getMaNCC(),
                    nhaCungCap.getTenNCC(),
                    nhaCungCap.getDiaChi(),
                    nhaCungCap.getSdt(),
                    nhaCungCap.getEmail(),
                    nhaCungCap.getTrangthai()
                };
                tableModel.addRow(row);
            }
        }else{
            JOptionPane.showMessageDialog(this,"Vui lòng nhập thông tin tìm kiếm");
            refreshList();
        }
    }

       
           private void xemPerformed(JTable tb){
        int selectedRow = suppliersTable.getSelectedRow();
        if(selectedRow!=-1){
            JLabel lbmancc, lbtenncc, lbdiachi, lbsdt , lbemail,lbtrangthai;
            JTextField tfmancc, tftenncc, tfdiachi, tfsdt ,tfemail,tftrangthai;
            JFrame f = new JFrame("Thông tin");
        
            f.setLayout(new GridBagLayout());

            JPanel banner = new JPanel();
            banner.setBackground(Color.decode("#56c2f5"));
            JPanel content = new JPanel();
            content.setBackground(Color.GREEN);
            content.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weightx = 1.0;
            gbc.weighty = 0.15;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 0;
            f.add(banner, gbc);
            gbc.weighty = 0.85;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridy = 1;
            f.add(content, gbc);

            banner.setLayout(new GridBagLayout());
            JLabel lb = new JLabel("THÔNG TIN NHÀ CUNG CẤP");
            Font font = new Font("Arial", Font.BOLD, 15);
            lb.setFont(font);
            lb.setForeground(Color.WHITE);
            lb.setHorizontalAlignment(JLabel.CENTER);
            lb.setVerticalAlignment(JLabel.CENTER);
            GridBagConstraints gbcLabel = new GridBagConstraints();
            gbcLabel.weightx = 1.0;
            gbcLabel.weighty = 1.0;
            banner.add(lb, gbcLabel);

            JPanel pnMa, pnTen, pnDiaChi, pnSdt,pnEmail,pnTrangThai;
            pnMa = new JPanel();
            pnMa.setBackground(Color.WHITE);
            pnTen = new JPanel();
            pnTen.setBackground(Color.WHITE);
            pnDiaChi = new JPanel();
            pnDiaChi.setBackground(Color.WHITE);
            pnSdt = new JPanel();
            pnSdt.setBackground(Color.WHITE);
              pnEmail = new JPanel();
            pnEmail.setBackground(Color.WHITE);
              pnTrangThai = new JPanel();
            pnTrangThai.setBackground(Color.WHITE);

            gbc.gridx = 0;
            gbc.gridy = 0;
            content.add(pnMa, gbc);
            gbc.gridy = 1;
            content.add(pnTen, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            content.add(pnDiaChi, gbc);
            gbc.gridy = 1;
            content.add(pnSdt, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            content.add(pnEmail, gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            content.add(pnTrangThai, gbc);


            lbmancc = new JLabel("Mã nhà cung cấp");
            lbtenncc = new JLabel("Tên nhà cung cấp");
            lbdiachi = new JLabel("Địa chỉ");
            lbsdt = new JLabel("SĐT");
            lbemail = new JLabel("Email");
            lbtrangthai = new JLabel("Trạng thái");
            tfmancc = new JTextField();
            tftenncc = new JTextField();
            tfdiachi = new JTextField();
            tfsdt = new JTextField();
            tfemail = new JTextField();
            tftrangthai = new JTextField();
            

            lbmancc.setBounds(20,10,100,20);
            tfmancc.setBounds(20,30,180,30);
            pnMa.setLayout(null);
            pnMa.add(lbmancc);  pnMa.add(tfmancc);

            lbtenncc.setBounds(20,10,110,20);
            tftenncc.setBounds(20,30,250,30);
            pnTen.setLayout(null);
            pnTen.add(lbtenncc);    pnTen.add(tftenncc);

            lbdiachi.setBounds(0,10,110,20);
            tfdiachi.setBounds(0,30,335,30);
            pnDiaChi.setLayout(null);
            pnDiaChi.add(lbdiachi); pnDiaChi.add(tfdiachi);

            lbsdt.setBounds(0,10,110,20);
            tfsdt.setBounds(0,30,100,30);
            pnSdt.setLayout(null);
            pnSdt.add(lbsdt);   pnSdt.add(tfsdt);
            
              lbemail.setBounds(0,10,110,20);
            tfemail.setBounds(0,30,100,30);
            pnEmail.setLayout(null);
            pnEmail.add(lbemail);   pnEmail.add(tfemail);
            
              lbtrangthai.setBounds(0,10,110,20);
            tftrangthai.setBounds(0,30,100,30);
            pnTrangThai.setLayout(null);
            pnTrangThai.add(lbtrangthai);   pnTrangThai.add(tftrangthai);


            String maNCC = suppliersTable.getValueAt(selectedRow, 0).toString();
            NhaCungCapDAO nccDAO = new NhaCungCapDAO();
            NhaCungCapDTO ncc = nccDAO.selectById(maNCC);
            if(ncc!=null){
                tfmancc.setText(ncc.getMaNCC());
                tftenncc.setText(ncc.getTenNCC());
                tfdiachi.setText(ncc.getDiaChi());
                tfsdt.setText(ncc.getSdt());
                  tfemail.setText(ncc.getEmail());
                tftrangthai.setText(ncc.getTrangthai());
            }

            // phương thức setEditable(false) set các textfield ở mode read only
            tfmancc.setEditable(false);
            tftenncc.setEditable(false);
            tfdiachi.setEditable(false);
            tfsdt.setEditable(false);
             tfemail.setEditable(false);
            tftrangthai.setEditable(false);

            // phương thức setFocusable(false) không cho click vào các textfield
            tfmancc.setFocusable(false);
            tftenncc.setFocusable(false);
            tfdiachi.setFocusable(false);
            tfsdt.setFocusable(false);
               tfemail.setFocusable(false);
            tftrangthai.setFocusable(false);
            
            f.setSize(720,400);
            f.setResizable(false);
            f.setVisible(true);
        } else{
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 dòng để xem thông tin");
        }
        
    }

       
       
       
       
       
       
	public static void log(String message) {
  	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
  	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
  	    System.out.println(element.getClassName() + " | method: " 
  	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
  	}
}
