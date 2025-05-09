package GUI;

import BUS.KhachHangBUS;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import Components.ShadowButton;
import DAO.NhaCungCapDAO;
import DTO.*;
import com.toedter.calendar.JDateChooser;

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
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;



public class KhachHangGUI extends JPanel{
	JTable KhachHangTable;
	KhachHangBUS KhachHangBUS = new KhachHangBUS();
	DefaultTableModel KhachHangModel = new DefaultTableModel();
	ArrayList<KhachHangDTO> KhachHangArr = new ArrayList<KhachHangDTO>(); // Tạo ArrayList sp với kiểu là ProductsDTO
	private JComboBox sortComboBox;
	private JPanel khachHangContent;
	private JTextField txtTimKiem;
	
	//Constructor
    public KhachHangGUI(){
        initComponents();
        loadKhachHangList();
    }
    
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents() {
        setLayout(new GridBagLayout()); //set Layout
        GridBagConstraints gbc = new GridBagConstraints();
        khachHangContent = new JPanel();
        khachHangContent.setBackground(Color.white);
        khachHangContent.setLayout(new GridBagLayout());
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(khachHangContent, gbc); // Thêm vào ProductsGUI
        
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
        khachHangContent.add(topPanel, gbc);
        
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
        khachHangContent.add(bottomPanel, gbc);
        
        
        
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
                newKhachHangDialog();
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
        int selectedRow = KhachHangTable.getSelectedRow();

        if (selectedRow != -1) {
            String maKH      = KhachHangTable.getValueAt(selectedRow, 0).toString();
            String hoTen     = KhachHangTable.getValueAt(selectedRow, 1).toString();
            String ngaySinh  = KhachHangTable.getValueAt(selectedRow, 2).toString();
            String gioiTinh  = KhachHangTable.getValueAt(selectedRow, 3).toString();
            String diaChi    = KhachHangTable.getValueAt(selectedRow, 4).toString();
            String sdt       = KhachHangTable.getValueAt(selectedRow, 5).toString();
            String email     = KhachHangTable.getValueAt(selectedRow, 6).toString();
            String trangThai = KhachHangTable.getValueAt(selectedRow, 7).toString();

            // Tạo DTO khách hàng từ dòng đã chọn
            KhachHangDTO selectedKH = new KhachHangDTO(
                maKH, hoTen, ngaySinh, gioiTinh,
                diaChi, sdt, email, trangThai
            );
            // Mở dialog cập nhật
            updateKhachHangDialog(selectedKH);
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Vui lòng chọn một khách hàng để cập nhật!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE
            );
        }
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
               deleteKhachHangDialog(KhachHangTable);
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
        int selectedRow = KhachHangTable.getSelectedRow();
        if (selectedRow != -1) {
            String maKH = KhachHangTable.getValueAt(selectedRow, 0).toString(); // Cột 0 là mã nhà cung cấp
            detailKhachHangDialog(maKH); // Truyền mã vào dialog
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhà cung cấp để xem chi tiết!");
        }
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
                 excelExporter ex = new excelExporter();
                ex.excelExporterKhachHang();
                JOptionPane.showMessageDialog(null, "Excel file exported successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
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
//                searchPerformed(employeeTable);
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
//            	refreshList();
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

        
        KhachHangTable = new JTable();
        JScrollPane sp = new JScrollPane(KhachHangTable);
        gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(sp, gbc);
    }

    
	private void loadKhachHangList() {
		KhachHangTable.setDefaultEditor(Object.class, null);
		
		KhachHangTable.setModel(KhachHangModel);
		KhachHangModel.addColumn("ID");
		KhachHangModel.addColumn("Họ tên");
		KhachHangModel.addColumn("Ngày sinh");
		KhachHangModel.addColumn("Giới tính");
		KhachHangModel.addColumn("Địa chỉ");
                KhachHangModel.addColumn("SDT");
                KhachHangModel.addColumn("Email");
		KhachHangModel.addColumn("Trạng thái");
		
		KhachHangArr = KhachHangBUS.selectAll();
		for (int i = 0; i < KhachHangArr.size(); i++) {
			KhachHangDTO ncc = KhachHangArr.get(i);
			String maKH = ncc.getMaKH();
			log("maKH="+maKH);
			String tenNCC = ncc.getHoTen();
			String ngaySinh = ncc.getNgaySinh();
			String gioiTinh = ncc.getGioiTinh();
			String diaChi = ncc.getDiaChi();
                        String sdt = ncc.getSdt();
			String email = ncc.getEmail();
                        String trangThai = ncc.getTrangThai();
			Object[] row = { maKH, tenNCC,ngaySinh, gioiTinh, diaChi, sdt, email,trangThai };
			KhachHangModel.addRow(row);
		}
		TableColumnModel tcm = KhachHangTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(100);
		tcm.getColumn(1).setPreferredWidth(120);
		tcm.getColumn(2).setPreferredWidth(100);
		tcm.getColumn(3).setPreferredWidth(70);
		tcm.getColumn(4).setPreferredWidth(200);
		tcm.getColumn(5).setPreferredWidth(100);
                tcm.getColumn(6).setPreferredWidth(200);
		tcm.getColumn(7).setPreferredWidth(100);

		KhachHangTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize

	}
        private String generateMaKH() {
    int count = new KhachHangBUS().getSoLuongKhachHang(); 
    return String.format("KH%03d", count + 1); 
}

    private void newKhachHangDialog() {
    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm khách hàng", true);
    dialog.setSize(550, 450);
    dialog.setLayout(new GridBagLayout());
    dialog.getContentPane().setBackground(Color.white);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.anchor = GridBagConstraints.WEST;

    // --- MÃ KH (tự sinh + disable) ---
    JLabel lblMaKH = new JLabel("Mã khách hàng:");
    JTextField txtMaKH = new JTextField(20);
    txtMaKH.setText(generateMaKH());    
    txtMaKH.setEnabled(false);

    // --- HỌ TÊN ---
    JLabel lblHoTen = new JLabel("Họ tên:");
    JTextField txtHoTen = new JTextField(20);

    // --- NGÀY SINH ---
    JLabel lblNgaySinh = new JLabel("Ngày sinh:");
    JDateChooser dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("yyyy-MM-dd");
    dateChooser.setPreferredSize(new Dimension(120, 25));

    // --- GIỚI TÍNH ---
    JLabel lblGioiTinh = new JLabel("Giới tính:");
    JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});

    // --- ĐỊA CHỈ ---
    JLabel lblDiaChi = new JLabel("Địa chỉ:");
    JTextField txtDiaChi = new JTextField(20);

    // --- SĐT ---
    JLabel lblSDT = new JLabel("Số điện thoại:");
    JTextField txtSDT = new JTextField(20);

    // --- EMAIL ---
    JLabel lblEmail = new JLabel("Email:");
    JTextField txtEmail = new JTextField(20);

    // --- TRẠNG THÁI ---
    JLabel lblTrangThai = new JLabel("Trạng thái:");
    JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"On", "Off"});

    // --- NÚT LƯU ---
    JButton btnSave = new JButton("Lưu");

    // --- Layout ---
    int y = 0;
    gbc.gridx = 0; gbc.gridy = y; dialog.add(lblMaKH, gbc);
    gbc.gridx = 1; dialog.add(txtMaKH, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblHoTen, gbc);
    gbc.gridx = 1; dialog.add(txtHoTen, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblNgaySinh, gbc);
    gbc.gridx = 1; dialog.add(dateChooser, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblGioiTinh, gbc);
    gbc.gridx = 1; dialog.add(cbGioiTinh, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblDiaChi, gbc);
    gbc.gridx = 1; dialog.add(txtDiaChi, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblSDT, gbc);
    gbc.gridx = 1; dialog.add(txtSDT, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblEmail, gbc);
    gbc.gridx = 1; dialog.add(txtEmail, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblTrangThai, gbc);
    gbc.gridx = 1; dialog.add(cbTrangThai, gbc);

    gbc.gridx = 1; gbc.gridy = ++y; gbc.anchor = GridBagConstraints.EAST;
    dialog.add(btnSave, gbc);

    // --- Sự kiện lưu ---
    btnSave.addActionListener(e -> {
        try {
            String maKH = txtMaKH.getText();
            String hoTen = txtHoTen.getText();
            java.util.Date ud = dateChooser.getDate();
            String ngaySinh = ud != null
                ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(ud)
                : "";
            String gioiTinh = cbGioiTinh.getSelectedItem().toString();
            String diaChi = txtDiaChi.getText();
            String sdt = txtSDT.getText();
            String email = txtEmail.getText();
            String trangThai = cbTrangThai.getSelectedItem().toString();

            KhachHangDTO kh = new KhachHangDTO(
                maKH, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, trangThai
            );
            KhachHangBUS bus = new KhachHangBUS();
            String message = bus.insert(kh);
            JOptionPane.showMessageDialog(dialog, message);

            if (message.contains("thành công")) {
                dialog.dispose();
                // TODO: reload bảng khách hàng nếu cần
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialog,
                "Lỗi khi thêm khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    });

    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}


 private void updateKhachHangDialog(KhachHangDTO selectedKH) {
    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Cập nhật khách hàng", true);
    dialog.setSize(550, 450);
    dialog.setLayout(new GridBagLayout());
    dialog.getContentPane().setBackground(Color.white);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.anchor = GridBagConstraints.WEST;

    // --- MÃ KH (disable) ---
    JLabel lblMaKH = new JLabel("Mã khách hàng:");
    JTextField txtMaKH = new JTextField(20);
    txtMaKH.setText(selectedKH.getMaKH());
    txtMaKH.setEnabled(false);

    // --- HỌ TÊN ---
    JLabel lblHoTen = new JLabel("Họ tên:");
    JTextField txtHoTen = new JTextField(selectedKH.getHoTen(), 20);

    // --- NGÀY SINH ---
    JLabel lblNgaySinh = new JLabel("Ngày sinh:");
    JDateChooser dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("yyyy-MM-dd");
    try {
        java.util.Date d = new java.text.SimpleDateFormat("yyyy-MM-dd")
                           .parse(selectedKH.getNgaySinh());
        dateChooser.setDate(d);
    } catch (Exception ex) {
        // nếu parse lỗi, bỏ trống
    }
    dateChooser.setPreferredSize(new Dimension(120, 25));

    // --- GIỚI TÍNH ---
    JLabel lblGioiTinh = new JLabel("Giới tính:");
    JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
    cbGioiTinh.setSelectedItem(selectedKH.getGioiTinh());

    // --- ĐỊA CHỈ ---
    JLabel lblDiaChi = new JLabel("Địa chỉ:");
    JTextField txtDiaChi = new JTextField(selectedKH.getDiaChi(), 20);

    // --- SĐT ---
    JLabel lblSDT = new JLabel("Số điện thoại:");
    JTextField txtSDT = new JTextField(selectedKH.getSdt(), 20);

    // --- EMAIL ---
    JLabel lblEmail = new JLabel("Email:");
    JTextField txtEmail = new JTextField(selectedKH.getEmail(), 20);

    // --- TRẠNG THÁI ---
    JLabel lblTrangThai = new JLabel("Trạng thái:");
    JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"On", "Off"});
    cbTrangThai.setSelectedItem(selectedKH.getTrangThai());

    // --- NÚT CẬP NHẬT ---
    JButton btnUpdate = new JButton("Cập nhật");

    // --- Layout ---
    int y = 0;
    gbc.gridx = 0; gbc.gridy = y; dialog.add(lblMaKH, gbc);
    gbc.gridx = 1; dialog.add(txtMaKH, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblHoTen, gbc);
    gbc.gridx = 1; dialog.add(txtHoTen, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblNgaySinh, gbc);
    gbc.gridx = 1; dialog.add(dateChooser, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblGioiTinh, gbc);
    gbc.gridx = 1; dialog.add(cbGioiTinh, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblDiaChi, gbc);
    gbc.gridx = 1; dialog.add(txtDiaChi, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblSDT, gbc);
    gbc.gridx = 1; dialog.add(txtSDT, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblEmail, gbc);
    gbc.gridx = 1; dialog.add(txtEmail, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblTrangThai, gbc);
    gbc.gridx = 1; dialog.add(cbTrangThai, gbc);

    gbc.gridx = 1; gbc.gridy = ++y; gbc.anchor = GridBagConstraints.EAST;
    dialog.add(btnUpdate, gbc);

    // --- Sự kiện cập nhật ---
    btnUpdate.addActionListener(e -> {
        try {
            String maKH     = txtMaKH.getText();
            String hoTen    = txtHoTen.getText();
            java.util.Date ud = dateChooser.getDate();
            String ngaySinh = ud != null
                ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(ud)
                : "";
            String gioiTinh = cbGioiTinh.getSelectedItem().toString();
            String diaChi   = txtDiaChi.getText();
            String sdt      = txtSDT.getText();
            String email    = txtEmail.getText();
            String trangThai= cbTrangThai.getSelectedItem().toString();

            KhachHangDTO kh = new KhachHangDTO(
                maKH, hoTen, ngaySinh, gioiTinh,
                diaChi, sdt, email, trangThai
            );
            KhachHangBUS bus = new KhachHangBUS();
            String message = bus.updateKhachHang(kh);
            JOptionPane.showMessageDialog(dialog, message);

            if (message.contains("thành công")) {
                dialog.dispose();
                // TODO: reload bảng khách hàng
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialog,
                "Lỗi khi cập nhật khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    });

    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}


 public void deleteKhachHangDialog(JTable customersTable) {
    int selectedRow = customersTable.getSelectedRow();
    
    if (selectedRow != -1) {
        int dialogResult = JOptionPane.showConfirmDialog(
            null,
            "Bạn có chắc muốn xóa khách hàng này?",
            "Xác nhận xoá",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (dialogResult == JOptionPane.OK_OPTION) {
            // Lấy mã khách hàng từ model (giả sử cột 0 là maKH)
            String maKH = (String) KhachHangModel.getValueAt(selectedRow, 0);
            KhachHangDTO kh = new KhachHangDTO();
            kh.setMaKH(maKH);

            // Gọi BUS để xoá
            KhachHangBUS bus = new KhachHangBUS();
            String message = bus.deleteKhachHang(kh);

            // Nếu xoá thành công thì cập nhật lại model và mảng dữ liệu
            if ("Xóa khách hàng thành công".equals(message)) {
                if (KhachHangModel != null) {
                    KhachHangModel.removeRow(selectedRow);
                }
                if (KhachHangModel != null) {
                    KhachHangArr.remove(selectedRow);
                }
            }

            JOptionPane.showMessageDialog(null, message);
        }
    } else {
        JOptionPane.showMessageDialog(
            null,
            "Vui lòng chọn khách hàng cần xóa!",
            "Thông báo",
            JOptionPane.WARNING_MESSAGE
        );
    }
}


private void detailKhachHangDialog(String maKH) {
    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Chi tiết khách hàng", true);
    dialog.setSize(550, 480);
    dialog.setLayout(new GridBagLayout());
    dialog.getContentPane().setBackground(Color.white);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.anchor = GridBagConstraints.WEST;

    // Lấy dữ liệu từ BUS
    KhachHangBUS bus = new KhachHangBUS();
    KhachHangDTO kh = bus.selectById(maKH);

    // --- MÃ KH (disable) ---
    JLabel lblMaKH = new JLabel("Mã khách hàng:");
    JTextField txtMaKH = new JTextField(kh.getMaKH(), 20);
    txtMaKH.setEnabled(false);

    // --- HỌ TÊN ---
    JLabel lblHoTen = new JLabel("Họ tên:");
    JTextField txtHoTen = new JTextField(kh.getHoTen(), 20);
    txtHoTen.setEditable(false);

    // --- NGÀY SINH ---
    JLabel lblNgaySinh = new JLabel("Ngày sinh:");
    JTextField txtNgaySinh = new JTextField(kh.getNgaySinh(), 20);
    txtNgaySinh.setEditable(false);

    // --- GIỚI TÍNH ---
    JLabel lblGioiTinh = new JLabel("Giới tính:");
    JTextField txtGioiTinh = new JTextField(kh.getGioiTinh(), 20);
    txtGioiTinh.setEditable(false);

    // --- ĐỊA CHỈ ---
    JLabel lblDiaChi = new JLabel("Địa chỉ:");
    JTextField txtDiaChi = new JTextField(kh.getDiaChi(), 20);
    txtDiaChi.setEditable(false);

    // --- SỐ ĐT ---
    JLabel lblSDT = new JLabel("Số điện thoại:");
    JTextField txtSDT = new JTextField(kh.getSdt(), 20);
    txtSDT.setEditable(false);

    // --- EMAIL ---
    JLabel lblEmail = new JLabel("Email:");
    JTextField txtEmail = new JTextField(kh.getEmail(), 20);
    txtEmail.setEditable(false);

    // --- TRẠNG THÁI ---
    JLabel lblTrangThai = new JLabel("Trạng thái:");
    JTextField txtTrangThai = new JTextField(kh.getTrangThai(), 20);
    txtTrangThai.setEditable(false);

    // --- Layout ---
    int y = 0;
    gbc.gridx = 0; gbc.gridy = y; dialog.add(lblMaKH, gbc);
    gbc.gridx = 1; dialog.add(txtMaKH, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblHoTen, gbc);
    gbc.gridx = 1; dialog.add(txtHoTen, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblNgaySinh, gbc);
    gbc.gridx = 1; dialog.add(txtNgaySinh, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblGioiTinh, gbc);
    gbc.gridx = 1; dialog.add(txtGioiTinh, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblDiaChi, gbc);
    gbc.gridx = 1; dialog.add(txtDiaChi, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblSDT, gbc);
    gbc.gridx = 1; dialog.add(txtSDT, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblEmail, gbc);
    gbc.gridx = 1; dialog.add(txtEmail, gbc);

    gbc.gridx = 0; gbc.gridy = ++y; dialog.add(lblTrangThai, gbc);
    gbc.gridx = 1; dialog.add(txtTrangThai, gbc);

    // Hiển thị dialog
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}


 
 
        private void refreshList(){
        //Xóa tất cả các dòng trong model table
        KhachHangModel.setRowCount(0);
        loadKhachHangList();
        txtTimKiem.setText("");
        
    }
     

      
       
         private void searchPerformed(JTable tb) {
    String searchContent = txtTimKiem.getText().trim().toLowerCase();
    if (!searchContent.isEmpty()) {
        ArrayList<KhachHangDTO> dsTimKiem = new ArrayList<>();
        for (KhachHangDTO kh : KhachHangArr) {
            if (kh.getMaKH().toLowerCase().contains(searchContent) ||
                kh.getHoTen().toLowerCase().contains(searchContent) ||
                kh.getNgaySinh().toLowerCase().contains(searchContent) ||
                kh.getGioiTinh().toLowerCase().contains(searchContent) ||
                kh.getDiaChi().toLowerCase().contains(searchContent) ||
                kh.getSdt().toLowerCase().contains(searchContent) ||
                kh.getEmail().toLowerCase().contains(searchContent) ||
                kh.getTrangThai().toLowerCase().contains(searchContent)
            ) {
                dsTimKiem.add(kh);
            }
        }

        if (dsTimKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào!");
            refreshList();
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tb.getModel();
        model.setRowCount(0);  // Xóa hết hàng cũ

        // Đổ kết quả tìm được lên bảng
        for (KhachHangDTO kh : dsTimKiem) {
            Object[] row = {
                kh.getMaKH(),
                kh.getHoTen(),
                kh.getNgaySinh(),
                kh.getGioiTinh(),
                kh.getDiaChi(),
                kh.getSdt(),
                kh.getEmail(),
                kh.getTrangThai()
            };
            model.addRow(row);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm");
        refreshList();
    }
}


       
    

       
       
       
       
       
       
	public static void log(String message) {
  	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
  	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
  	    System.out.println(element.getClassName() + " | method: " 
  	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
  	}
}
