package GUI;

import BUS.BangChamCongBUS;
import BUS.ChiTietChamCongBUS;
import BUS.DonXinNghiBUS;
import BUS.NhanVienBUS;
import Components.ShadowButton;
import DTO.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;



public class DSBangChamCongGUI extends JPanel{

    BangChamCongBUS bccBUS = new BangChamCongBUS();
    ChiTietChamCongBUS ctccBUS = new ChiTietChamCongBUS();
    DonXinNghiBUS dxnBUS = new DonXinNghiBUS();
    NhanVienBUS nvienBUS = new NhanVienBUS();
    
    JTable bccTable, ngayNghiTable, tangCaTable;
    DefaultTableModel bccModel = new DefaultTableModel();
    DefaultTableModel ngayNghiModel = new DefaultTableModel();
    DefaultTableModel tangCaModel = new DefaultTableModel();
    ArrayList<BangChamCongDTO> arrBangChamCong = new ArrayList<BangChamCongDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
    ArrayList<ChiTietPhieuNhapDTO> arrCTCC = new ArrayList<ChiTietPhieuNhapDTO>();
    private JComboBox<String> sortComboBox, jc_thang, jc_nam;
    private JPanel bangChamCongContent;
    private JTextField txtTimKiem, tfPriceStart, tfPriceEnd;	
	
	//Constructor
    public DSBangChamCongGUI(CardLayout cardLayout, JPanel contentPanel){
        initComponents(cardLayout, contentPanel);
        loadBangChamCongList();
        loadNgayNghiList();
        loadTangCaList();
    }
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents(CardLayout cardLayout, JPanel contentPanel) {
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
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        bangChamCongContent.add(topPanel, gbc);
        
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridBagLayout());
        middlePanel.setBackground(Color.white);
        middlePanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        gbc.weightx = 1.0;
        gbc.weighty = 0.65;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        bangChamCongContent.add(middlePanel, gbc);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.setBackground(Color.white);

		
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 3, 0, 3);
        bangChamCongContent.add(bottomPanel, gbc);
        
        
        JPanel functionsPanel, searchPanel;
        //functionPanel
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
        
        //Đặt các nút chức năng vào các panel
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
        btnAdd.addActionListener(e -> {
            cardLayout.show(contentPanel, "CHAM CONG");
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
        // Thêm sự kiện click cho nút Update
        btnAdd.addActionListener(e -> {
            cardLayout.show(contentPanel, "CHAM CONG");
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

        //seacrhPanel
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
        
        
        
        //searchButtonPanel
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
                searchPerformed(bccTable);
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
        bccTable = new JTable();
        JScrollPane sp = new JScrollPane(bccTable);
        gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	middlePanel.add(sp, gbc);
	bccTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()>=1) {
                    viewNgayNghi();
                    viewTangCa();
		}
            }
	});
                bccTable.setDefaultEditor(Object.class, null);
        bccTable.setModel(bccModel);            
        bccModel.addColumn("Mã chấm công");
        bccModel.addColumn("Nhân viên");   //"maNV - Họ tên: NV001 - Nguyen Van A"
        bccModel.addColumn("Tháng");
        bccModel.addColumn("Năm");
        bccModel.addColumn("Ngày công");
        bccModel.addColumn("Nghỉ không phép");
        bccModel.addColumn("Nghỉ phép có lương");
        bccModel.addColumn("Nghỉ phép không lương");
        bccModel.addColumn("Tăng ca ngày thường");
        bccModel.addColumn("Tăng ca ngày lễ");
        bccModel.addColumn("Tăng ca chủ nhật");
        
        //Điều chỉnh kích thước các cột
    	TableColumnModel tcm = bccTable.getColumnModel();
    	tcm.getColumn(0).setPreferredWidth(100);
	tcm.getColumn(1).setPreferredWidth(150);
	tcm.getColumn(2).setPreferredWidth(50);
	tcm.getColumn(3).setPreferredWidth(50);
	tcm.getColumn(4).setPreferredWidth(100);
	tcm.getColumn(5).setPreferredWidth(100);
	tcm.getColumn(6).setPreferredWidth(100);
	tcm.getColumn(7).setPreferredWidth(100);
        tcm.getColumn(8).setPreferredWidth(100);
        tcm.getColumn(9).setPreferredWidth(100);
        tcm.getColumn(10).setPreferredWidth(100);
		
	bccTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize
		
	//searchInputPanel
        String[] sortCriterias = {"Tất cả", "A-Z", "Z-A", "Tăng dần", "Giảm dần"};
        sortComboBox = new JComboBox<String>(sortCriterias);
        sortComboBox.setBounds(10, 24, 75, 25);
        searchInputPanel.add(sortComboBox);
        
        String[] thang = {"Tháng", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        jc_thang = new JComboBox<String>(thang);
        jc_thang.setBounds(90, 24, 80, 25);
        searchInputPanel.add(jc_thang);
        jc_thang.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
                bccModel.setRowCount(0);
                String thang = jc_thang.getSelectedItem().toString();
                String nam = jc_nam.getSelectedItem().toString();
                if (!thang.equals("Tháng") || !nam.equals("Năm")) {
                    arrBangChamCong = bccBUS.selectByTime(Integer.parseInt(thang), Integer.parseInt(nam));
                    for(BangChamCongDTO bcc: arrBangChamCong) {
                        NhanVienDTO nvien = nvienBUS.selectById(bcc.getMaNV());

                        String maBCC = bcc.getMaBCC();
                        String nv = bcc.getMaNV() + " - " + nvien.getHoTen();
                        int thangCC = bcc.getThangCC();
                        int namCC = bcc.getNamCC();
                        float soNgayLam = bcc.getSoNgayLam();
                        float soNgayNghiKP = bcc.getSoNgayNghiKP();
                        float soNPCoLuong = bcc.getSoNPCoLuong();
                        float soNPKhongLuong = bcc.getSoNPKhongLuong();
                        float soGioOTNgayThuong = bcc.getSoGioOTNgayThuong();
                        float soGioOTNgayLe = bcc.getSoGioOTNgayLe();
                        float soGioOTCN = bcc.getSoGioOTCN();

                        Object[] row = {maBCC, nv, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN};
                        bccModel.addRow(row);

                    }
                } else loadBangChamCongList();
            }
        });
        
        LocalDate current = LocalDate.now();
        String[] nam_title= new String[6];
        nam_title[0] = "Năm";
    	for(int i=1;i<nam_title.length;i++) {
    		nam_title[i] = current.getYear()-2+i+"";
    	}
        jc_nam = new JComboBox<String>();
        jc_nam.setBounds(185, 24, 80, 25);
        jc_nam.setModel(new DefaultComboBoxModel<>(nam_title));
        searchInputPanel.add(jc_nam);
        jc_nam.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
                bccModel.setRowCount(0);
                String thang = jc_thang.getSelectedItem().toString();
                String nam = jc_nam.getSelectedItem().toString();
                if (!thang.equals("Tháng") || !nam.equals("Năm")) {
                    arrBangChamCong = bccBUS.selectByTime(Integer.parseInt(thang), Integer.parseInt(nam));
                    for(int i=0; i<arrBangChamCong.size(); i++) {
                        BangChamCongDTO bcc = arrBangChamCong.get(i);
                        System.out.println("///// MaNV" + bcc.getMaNV());
                        NhanVienDTO nvien = nvienBUS.selectById(bcc.getMaNV());

                        String maBCC = bcc.getMaBCC();
                        String nv = bcc.getMaNV() + " - " + nvien.getHoTen();
                        int thangCC = bcc.getThangCC();
                        int namCC = bcc.getNamCC();
                        float soNgayLam = bcc.getSoNgayLam();
                        float soNgayNghiKP = bcc.getSoNgayNghiKP();
                        float soNPCoLuong = bcc.getSoNPCoLuong();
                        float soNPKhongLuong = bcc.getSoNPKhongLuong();
                        float soGioOTNgayThuong = bcc.getSoGioOTNgayThuong();
                        float soGioOTNgayLe = bcc.getSoGioOTNgayLe();
                        float soGioOTCN = bcc.getSoGioOTCN();

                        Object[] row = {maBCC, nv, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN};
                        bccModel.addRow(row);
                    }
                } else loadBangChamCongList();
            }
        });
        
        
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(375,  24,  260, 25);
        searchInputPanel.add(txtTimKiem);
        
        
	//bottomPanel
	//Chia 2 panel con leftBottomPanel, rightBottomPanel để hiển thị 2 bảng nghỉ và tăng ca
	JPanel leftBottomPanel, rightBottomPanel;
	leftBottomPanel = new JPanel(new GridBagLayout());
	leftBottomPanel.setBackground(Color.white);
	Border lineBorder = BorderFactory.createLineBorder(Color.lightGray, 2); // Tạo viền với độ dày 3px và màu xám
	TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Ngày nghỉ"); // Tạo TitledBorder với tiêu đề 
	titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 13)); // Font: Arial, đậm, size 16
	titledBorder.setTitleColor(Color.black); // Đổi màu chữ tiêu đề thành xanh
	leftBottomPanel.setBorder(titledBorder); 		// Áp dụng border cho leftBottomPanel
	gbc.weightx = 0.5;
	gbc.weighty = 1.0;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	bottomPanel.add(leftBottomPanel, gbc);
		
	rightBottomPanel = new JPanel(new GridBagLayout());
	rightBottomPanel.setBackground(Color.white);
	Border lineBorder2 = BorderFactory.createLineBorder(Color.lightGray, 2); // Tạo viền với độ dày 3px và màu xám
	TitledBorder titledBorder2 = BorderFactory.createTitledBorder(lineBorder, "Tăng ca"); // Tạo TitledBorder với tiêu đề 
	titledBorder2.setTitleFont(new Font("Arial", Font.BOLD, 13)); // Font: Arial, đậm, size 16
	titledBorder2.setTitleColor(Color.black); // Đổi màu chữ tiêu đề thành xanh
	rightBottomPanel.setBorder(titledBorder2); 		// Áp dụng border cho leftBottomPanel
	gbc.weightx = 0.5;
	gbc.weighty = 1.0;
	gbc.gridx = 1;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	bottomPanel.add(rightBottomPanel, gbc);
		
	ngayNghiTable = new JTable();
	JScrollPane sp2 = new JScrollPane(ngayNghiTable);
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	leftBottomPanel.add(sp2, gbc);
		
	tangCaTable = new JTable();
	JScrollPane sp3 = new JScrollPane(tangCaTable);
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	rightBottomPanel.add(sp3, gbc);
    }
    
    private void loadBangChamCongList() {
        arrBangChamCong = bccBUS.selectAll();
        for(int i=0; i<arrBangChamCong.size(); i++) {
            BangChamCongDTO bcc = arrBangChamCong.get(i);
            NhanVienDTO nvien = nvienBUS.selectById(bcc.getMaNV());
            
            String maBCC = bcc.getMaBCC();
            String nv = bcc.getMaNV() + " - " + nvien.getHoTen();
            int thangCC = bcc.getThangCC();
            int namCC = bcc.getNamCC();
            float soNgayLam = bcc.getSoNgayLam();
            float soNgayNghiKP = bcc.getSoNgayNghiKP();
            float soNPCoLuong = bcc.getSoNPCoLuong();
            float soNPKhongLuong = bcc.getSoNPKhongLuong();
            float soGioOTNgayThuong = bcc.getSoGioOTNgayThuong();
            float soGioOTNgayLe = bcc.getSoGioOTNgayLe();
            float soGioOTCN = bcc.getSoGioOTCN();
    		
            Object[] row = {maBCC, nv, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN};
            bccModel.addRow(row);
    		
    	}
    }
	
    private void loadNgayNghiList() {
	ngayNghiTable.setDefaultEditor(Object.class, null);		
	ngayNghiTable.setModel(ngayNghiModel);
	ngayNghiModel.addColumn("Ngày bắt đầu nghỉ");
	ngayNghiModel.addColumn("Ngày kết thúc nghỉ");
	ngayNghiModel.addColumn("Loại");
	ngayNghiModel.addColumn("Lý do");
    }
	
    private void loadTangCaList() {
	tangCaTable.setDefaultEditor(Object.class, null);
	tangCaTable.setModel(tangCaModel);
	tangCaModel.addColumn("Ngày tăng ca");
	tangCaModel.addColumn("Loại tăng ca");
	tangCaModel.addColumn("Số giờ tăng ca");
    }
	
    private void newBangChamCongDialog() {
	JDialog newBangChamCongDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm bảng chấm công", true);
	newBangChamCongDialog.setSize(300, 300);
	newBangChamCongDialog.setLayout(null);

	JLabel lblMaBCC, lblThangCC, lblNamCC, lblMaNV;
	lblMaBCC = new JLabel("Mã chấm công");
	lblMaBCC.setBounds(10, 10, 150, 20);
	newBangChamCongDialog.add(lblMaBCC);
		
	lblThangCC = new JLabel("Tháng");
	lblThangCC.setBounds(10, 60, 150, 20);
	newBangChamCongDialog.add(lblThangCC);
		
	lblNamCC = new JLabel("Năm");
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
		
	btnSave.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
                BangChamCongDTO bcc = new BangChamCongDTO();
                bcc.setMaBCC(txtMaBCC.getText());
                bcc.setThangCC(Integer.parseInt(txtThangCC.getText()));
                bcc.setNamCC(Integer.parseInt(txtNamCC.getText()));
                bcc.setSoNgayLam(0);
                bcc.setSoNgayNghiKP(0);
                bcc.setSoNPCoLuong(0);
                bcc.setSoNPKhongLuong(0);
                bcc.setSoGioOTNgayThuong(0);
                bcc.setSoGioOTNgayLe(0);
                bcc.setSoGioOTCN(0);
                bcc.setMaNV(txtMaNV.getText());

                String message = bccBUS.insert(bcc);
                JOptionPane.showMessageDialog(null, message);
            }
        });
		
	newBangChamCongDialog.setLocationRelativeTo(this);
	newBangChamCongDialog.setVisible(true);
	}
	
    private void updateBangChamCongDialog() {
	JDialog updateBangChamCongDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Sửa bảng chấm công", true);
	updateBangChamCongDialog.setSize(600, 300);
	updateBangChamCongDialog.setLayout(null);
		
	JLabel lblMaBCC, lblNV, lblThangCC, lblNamCC, lblSoNgayLam, lblSoNgayNghiKP, lblSoNPCoLuong, lblSoNPKhongLuong, lbGioOTNgayThuong, lbGioOTLe, lbGioOTCN;
	lblMaBCC = new JLabel("Mã chấm công");
	lblMaBCC.setBounds(10, 10, 150, 20);
	updateBangChamCongDialog.add(lblMaBCC);
	
        lblNV = new JLabel("Nhân viên");
	lblNV.setBounds(10, 160, 150, 20);
	updateBangChamCongDialog.add(lblNV);
        
	lblThangCC = new JLabel("Tháng chấm công");
	lblThangCC.setBounds(10, 60, 150, 20);
	updateBangChamCongDialog.add(lblThangCC);
		
	lblNamCC = new JLabel("Năm chấm công");
	lblNamCC.setBounds(10, 110, 150, 20);
	updateBangChamCongDialog.add(lblNamCC);
		
	lblSoNgayLam = new JLabel("Ngày công");
	lblSoNgayLam.setBounds(170, 10, 150, 20);
	updateBangChamCongDialog.add(lblSoNgayLam);
		
	lblSoNgayNghiKP = new JLabel("Nghỉ không phép");
	lblSoNgayNghiKP.setBounds(170, 60, 150, 20);
	updateBangChamCongDialog.add(lblSoNgayNghiKP);
		
	lblSoNPCoLuong = new JLabel("Nghỉ phép có lương");
	lblSoNPCoLuong.setBounds(170, 110, 150, 20);
	updateBangChamCongDialog.add(lblSoNPCoLuong);
		
	lblSoNPKhongLuong = new JLabel("Nghỉ phép không lương");
	lblSoNPKhongLuong.setBounds(170, 160, 170, 20);
	updateBangChamCongDialog.add(lblSoNPKhongLuong);
        
        lbGioOTNgayThuong = new JLabel("Tăng ca ngày thường");
	lbGioOTNgayThuong.setBounds(170, 160, 170, 20);
	updateBangChamCongDialog.add(lbGioOTNgayThuong);
        
        lbGioOTLe = new JLabel("tăng ca ngày lễ");
	lbGioOTLe.setBounds(170, 160, 170, 20);
	updateBangChamCongDialog.add(lbGioOTLe);
        
        lbGioOTCN = new JLabel("Tăng ca chủ nhật");
	lbGioOTCN.setBounds(170, 160, 170, 20);
	updateBangChamCongDialog.add(lbGioOTCN);
		
	JTextField txtMaBCC, txtMaNV, txtThangCC, txtNamCC;
	txtMaBCC = new JTextField();
	txtMaBCC.setBounds(10, 30, 100, 25);
	updateBangChamCongDialog.add(txtMaBCC);
		
	txtThangCC = new JTextField();
	txtThangCC.setBounds(10, 80, 100, 25);
	updateBangChamCongDialog.add(txtThangCC);
		
	txtNamCC = new JTextField();
	txtNamCC.setBounds(10, 130, 100, 25);
	updateBangChamCongDialog.add(txtNamCC);
		
	txtMaNV = new JTextField();
	txtMaNV.setBounds(10, 180, 100, 25);
	updateBangChamCongDialog.add(txtMaNV);
		
	JButton btnSave = new ShadowButton("Lưu");
	btnSave.setBounds(400, 230, 70, 25);
	updateBangChamCongDialog.add(btnSave);
		
	btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		BangChamCongDTO bcc = new BangChamCongDTO();
		bcc.setMaBCC(txtMaBCC.getText());
		bcc.setThangCC(Integer.parseInt(txtThangCC.getText()));
		bcc.setNamCC(Integer.parseInt(txtNamCC.getText()));
		bcc.setSoNgayLam(0);
		bcc.setSoNgayNghiKP(0);
		bcc.setSoNPCoLuong(0);
		bcc.setSoNPKhongLuong(0);
                bcc.setSoGioOTNgayThuong(0);
                bcc.setSoGioOTNgayLe(0);
                bcc.setSoGioOTCN(0);
		bcc.setMaNV(txtMaNV.getText());
				
		String message = bccBUS.insert(bcc);
		JOptionPane.showMessageDialog(null, message);
            }
	});
		
	updateBangChamCongDialog.setLocationRelativeTo(this);
	updateBangChamCongDialog.setVisible(true);
}
	
    private void searchPerformed(JTable tb){
        String searchContent = txtTimKiem.getText().trim(); // Lấy nội dung tìm kiếm từ textField và loại bỏ khoảng trắng ở đầu và cuối chuỗi
        if (!searchContent.isEmpty()) { // Kiểm tra xem nội dung tìm kiếm có rỗng không
            ArrayList<BangChamCongDTO> dsTimKiem = new ArrayList<>(); // Tạo một danh sách để lưu trữ kết quả tìm kiếm

            // Duyệt qua danh sách sản phẩm và lọc những sản phẩm thỏa mãn điều kiện tìm kiếm
            boolean found = false;
            for (BangChamCongDTO bcc: arrBangChamCong) {
                // Kiểm tra xem thông tin của bảng chấm công có chứa chuỗi tìm kiếm hay không (sử dụng phương thức contains)
                if (bcc.getMaBCC().toLowerCase().contains(searchContent.toLowerCase().trim()) || 
                	bcc.getMaNV().toLowerCase().contains(searchContent.toLowerCase().trim())){
                    dsTimKiem.add(bcc); // Nếu sản phẩm thỏa mãn, thêm vào danh sách lọc
                    found = true;
                }
                else if(bcc.getThangCC()==Integer.parseInt(searchContent.trim()) || 
                    bcc.getNamCC()==Integer.parseInt(searchContent.trim()) ||
                    bcc.getSoNgayLam()== Float.parseFloat(searchContent.trim()) ||
                    bcc.getSoNgayNghiKP()== Float.parseFloat(searchContent.trim()) ||
                    bcc.getSoNPCoLuong()== Float.parseFloat(searchContent.trim()) ||
                    bcc.getSoNPKhongLuong()== Float.parseFloat(searchContent.trim())||
                    bcc.getMaNV().toLowerCase().contains(searchContent.toLowerCase())){
                	dsTimKiem.add(bcc); // Nếu sản phẩm thỏa mãn, thêm vào danh sách lọc
                        found = true;
                    }
                
            }
            // Kiểm tra nếu không tìm thấy sản phẩm nào
            if(!found){
                JOptionPane.showMessageDialog(this, "Không tìm thấy bảng chấm công!");
                refreshList();
                return; // Kết thúc phương thức sau khi hiển thị thông báo
            }
            
            // Xóa tất cả các dòng hiện có trong bảng
            DefaultTableModel tableModel = (DefaultTableModel) tb.getModel();
            tableModel.setRowCount(0);
            
            // Thêm các sản phẩm thỏa mãn vào bảng
            for (BangChamCongDTO bcc : dsTimKiem) {
                NhanVienDTO nvien = nvienBUS.selectById(bcc.getMaNV());
    		String maBCC = bcc.getMaBCC();
                String nv = bcc.getMaNV()+ " - " + nvien.getHoTen();
    		int thangCC = bcc.getThangCC();
    		int namCC = bcc.getNamCC();
    		float soNgayLam = bcc.getSoNgayLam();
    		float soNgayNghiKP = bcc.getSoNgayNghiKP();
    		float soNPCoLuong = bcc.getSoNPCoLuong();
    		float soNPKhongLuong = bcc.getSoNPKhongLuong();
                float soGioOTNgayThuong = bcc.getSoGioOTNgayThuong();
                float soGioOTNgayLe = bcc.getSoGioOTNgayLe();
                float soGioOTCN = bcc.getSoGioOTCN();	
    			
    		Object[] row = {maBCC, nv, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN};
                tableModel.addRow(row);
            }
        } else {
            // Nếu người dùng không nhập nội dung tìm kiếm, thực hiện làm mới bảng để hiển thị tất cả sản phẩm
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm");
            refreshList();
        }
    }
	
	private void refreshList(){
        // Xóa tất cả các dòng trong mô hình bảng
        bccModel.setRowCount(0);
        bccModel.setColumnCount(0);
        loadBangChamCongList();
        sortComboBox.setSelectedIndex(0);
        txtTimKiem.setText("");
    }
	
	public void viewNgayNghi() {
            int selectedRow = bccTable.getSelectedRow();
            if(selectedRow != -1) {
		DefaultTableModel bccModel = (DefaultTableModel)bccTable.getModel();
			
                String maNV = bccModel.getValueAt(selectedRow,7).toString();
		log("maNV=" + maNV);
		ArrayList<DonXinNghiDTO> thongTinNgayNghi = dxnBUS.getThongTinNgayNghi(maNV);
			
		ngayNghiModel.setRowCount(0);
		for(int i=0; i<thongTinNgayNghi.size(); i++) {
                    Date ngayBD = thongTinNgayNghi.get(i).getNgayBD();
                    log("ngayBD="+ngayBD);
                    Date ngayKT = thongTinNgayNghi.get(i).getNgayKT();
                    log("ngayKT="+ngayKT);
                    String loai = "";
                    String lyDo = thongTinNgayNghi.get(i).getLyDo();
				
                    Object[] row = {ngayBD, ngayKT, loai, lyDo};
                    ngayNghiModel.addRow(row);
                }
            }
	}
	
	public void viewTangCa() {
		int selectedRow = bccTable.getSelectedRow();
		if(selectedRow != -1) {
                    DefaultTableModel bccModel = (DefaultTableModel)bccTable.getModel();
			
                    String maBCC = (String) bccModel.getValueAt(selectedRow,0);
                    log("maBCC=" + maBCC);
                    ArrayList<ChiTietChamCongDTO> thongTinTangCa = ctccBUS.getThongTinTangCa(maBCC);
		
                    tangCaModel.setRowCount(0);
                    for(int i=0; i<thongTinTangCa.size(); i++) {
                        LocalDate ngayTangCa = thongTinTangCa.get(i).getNgayTao();
                        String loaiTangCa = thongTinTangCa.get(i).getLoaiChamCong();
                        Float soGioTangCa = thongTinTangCa.get(i).getSoGioOT();
				
                        Object[] row = {ngayTangCa, loaiTangCa, soGioTangCa};
                        tangCaModel.addRow(row);
                    }
		}
	}
	
	  //hàm hiển thị thông tin dòng code
  	public static void log(String message) {
  	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
  	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
  	    System.out.println(element.getClassName() + " | method: " 
  	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
  	}
 }
  	
	
