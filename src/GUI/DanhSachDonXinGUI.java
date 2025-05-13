package GUI;

import BUS.DangNhapBUS;
import BUS.DonXinNghiBUS;
import BUS.NhaCungCapBUS;
import Components.ShadowButton;
import DAO.NhaCungCapDAO;
import DTO.*;
import Printer.PrintFile;

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
import java.sql.Date;
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

public class DanhSachDonXinGUI extends JPanel {
	JTable donXinTable;
	DonXinNghiBUS donXinBUS = new DonXinNghiBUS();
	DefaultTableModel donXinModel = new DefaultTableModel();
	ArrayList<DonXinNghiDTO> donXinArr = new ArrayList<DonXinNghiDTO>(); // Tạo ArrayList sp với kiểu là ProductsDTO
	JComboBox sortComboBox, trangThaiDonXinComboBox;
	JPanel taoDonXinContent;
	JTextField txtTimKiem, txtNguoiDuyet;
	JLabel lblTrangThai, lblNguoiDuyet;
	DangNhapBUS dnBUS = new DangNhapBUS();
	boolean comboboxClicked = false;

	// Constructor
	public DanhSachDonXinGUI() {
		initComponents();
		loadDonXinNghiList();
	}

	////////////////////////////////////////// METHODS//////////////////////////////////////
	private void initComponents() {
		setLayout(new GridBagLayout()); // set Layout
		GridBagConstraints gbc = new GridBagConstraints();
		taoDonXinContent = new JPanel();
		taoDonXinContent.setBackground(Color.white);
		taoDonXinContent.setLayout(new GridBagLayout());

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(taoDonXinContent, gbc); // Thêm vào ProductsGUI

		// tạo 2 panel topPanel, bottomPanel cho khu vực tìm kiếm và khu vực hiển thị
		// bảng danh sách
		JPanel topPanel, bottomPanel;
		// set thông số cho 2 panel
		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		topPanel.setBackground(Color.white);
		gbc.weightx = 1.0;
		gbc.weighty = 0.26;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		taoDonXinContent.add(topPanel, gbc);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		bottomPanel.setBackground(Color.white);
		bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 2)));
		gbc.weightx = 1.0;
		gbc.weighty = 0.74;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		taoDonXinContent.add(bottomPanel, gbc);

		// === TOP PANEL =====//
		JPanel functionsPanel, searchPanel;

		// ======================================= functionsPanel
		// =====================================================//
		// set thông số cho functionsPanel
		functionsPanel = new JPanel();
		functionsPanel.setBackground(Color.white);
		functionsPanel.setLayout(new GridBagLayout());
		functionsPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2)); // Tạo border cho panel
		gbc.weightx = 0.4;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		topPanel.add(functionsPanel, gbc);

		// chia 2 panel con nữa, leftFunctionPanel cho các nút chức năng,
		// rightFunctionPanel cho nút xuất Excel
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

		// Chia tiếp các panel con để chứa các nút chức năng ở leftFunctionPanel
		JPanel addButtonPanel, updateButtonPanel, deleteButtonPanel, detailButtonPanel, excelButtonPanel,
				printButtonPanel;

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

		// ======================================= Đặt các nút chức năng vào các panel
		// ==========================================================//
		ImageIcon iconAdd = new ImageIcon(getClass().getResource("/img/plus.png"));
		Image imgAdd = iconAdd.getImage();
		Image newImgAdd = imgAdd.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		if (iconAdd.getIconWidth() == -1) {
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

		// Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
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

		// Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
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

		// Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
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

		// Nút Xuất Excel
		// Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
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
				ex.excelExporterDonXinNghi();
				JOptionPane.showMessageDialog(null, "Excel file exported successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
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
				PrintFile pr = new PrintFile();
				JTable table = pr.getTableDonXinNghiFromDatabase();
				if (table != null) {
					pr.printTableDonXinNghi(table); // Thực hiện in
				} else {
					JOptionPane.showMessageDialog(null, "Không thể lấy dữ liệu từ bảng đơn xin nghỉ.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
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

		// ======================================= seacrhPanel
		// ========================================================//
		// set thông số cho seacrhPanel
		searchPanel = new JPanel();
		searchPanel.setBackground(Color.white);
		searchPanel.setLayout(new GridBagLayout());
		searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 2))); // Tạo
																														// border
																														// cho
																														// panel
		gbc.weightx = 0.6;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		topPanel.add(searchPanel, gbc);

		// chia 2 panel con nữa: searchInputPanel & searchButtonPanel. searchButtonPanel
		// để chứa nút tìm kiềm và refresh
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

		// ==================================== searchInputPanel
		// =======================================================//
		String[] sortCriterias = { "Tất cả", "A-Z", "Z-A" };
		sortComboBox = new JComboBox<String>(sortCriterias);
		sortComboBox.setBounds(10, 24, 75, 25);
		searchInputPanel.add(sortComboBox);
		sortComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedOption = (String) sortComboBox.getSelectedItem();
				switch (selectedOption) {
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
		txtTimKiem.setBounds(375, 24, 260, 25);
		searchInputPanel.add(txtTimKiem);

		// ==================================== searchButtonPanel
		// =======================================================//
		ImageIcon iconSearch = new ImageIcon(getClass().getResource("/img/loupe2.png")); // Đặt đường dẫn ảnh ở đây
		Image imgSearch = iconSearch.getImage();
		Image newImgSearch = imgSearch.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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

		// ==== BOTTOM PANEL ===/
		JPanel tablePanel, statusPanel;
		tablePanel = new JPanel(new GridBagLayout());
		tablePanel.setBackground(Color.white);
		gbc.weightx = 1.0;
		gbc.weighty = 0.55;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(tablePanel, gbc);
		statusPanel = new JPanel(null);
		statusPanel.setBackground(Color.white);
		statusPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
		gbc.weightx = 1.0;
		gbc.weighty = 0.35;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(statusPanel, gbc);

		donXinTable = new JTable();
		JScrollPane sp = new JScrollPane(donXinTable);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		tablePanel.add(sp, gbc);

		lblTrangThai = new JLabel("Trạng thái: ");
		lblTrangThai.setBounds(10, 10, 100, 20);
		statusPanel.add(lblTrangThai);
		String[] trangThai = { "Chờ duyệt", "Đã duyệt", "Từ chối" };
		trangThaiDonXinComboBox = new JComboBox<String>(trangThai);
		trangThaiDonXinComboBox.setBounds(120, 10, 100, 20);
		statusPanel.add(trangThaiDonXinComboBox);
		trangThaiDonXinComboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				comboboxClicked = true;
			}
		});

		trangThaiDonXinComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = donXinTable.getSelectedRow();
				if (selectedRow != -1) {
					if (comboboxClicked) {
						DefaultTableModel model = (DefaultTableModel) donXinTable.getModel();
						String maDon = (String) model.getValueAt(selectedRow, 0);
						String trangThai = trangThaiDonXinComboBox.getSelectedItem().toString();
						updateTrangThai(maDon, trangThai);
						comboboxClicked = false;

					}
				}

			}
		});
		lblNguoiDuyet = new JLabel("Người chỉnh sửa: ");
		lblNguoiDuyet.setBounds(10, 40, 100, 20);
		statusPanel.add(lblNguoiDuyet);
		txtNguoiDuyet = new JTextField();
		txtNguoiDuyet.setEnabled(false);
		String maNguoiDuyet = dnBUS.getMaNV();
		txtNguoiDuyet.setText(maNguoiDuyet);
		txtNguoiDuyet.setBounds(120, 40, 100, 20);
		statusPanel.add(txtNguoiDuyet);

	}

	private void loadDonXinNghiList() {
		donXinTable.setDefaultEditor(Object.class, null);

		donXinTable.setModel(donXinModel);
		donXinModel.addColumn("Mã đơn");
		donXinModel.addColumn("Ngày tạo");
		donXinModel.addColumn("Ngày bắt đầu");
		donXinModel.addColumn("Ngày kết thúc");
		donXinModel.addColumn("Lý do");
		donXinModel.addColumn("Ngày duyệt");
		donXinModel.addColumn("Trạng thái");
		donXinModel.addColumn("Mã NV");
		donXinModel.addColumn("Mã người duyệt");

		donXinArr = donXinBUS.selectAll();
		for (int i = 0; i < donXinArr.size(); i++) {
			DonXinNghiDTO dxn = donXinArr.get(i);
			String maDon = dxn.getMaDon();
			log("maDon=" + maDon);
			Date ngayTao = dxn.getNgayTao();
			Date ngayBD = dxn.getNgayBD();
			Date ngayKT = dxn.getNgayKT();
			String lyDo = dxn.getLyDo();
			Date ngayDuyet = dxn.getNgayDuyet();
			String trangThai = dxn.getTrangThai();
			String maNV = dxn.getMaNV();
			String maNguoiDuyet = dxn.getMaNguoiDuyet();

			Object[] row = { maDon, ngayTao, ngayBD, ngayKT, lyDo, ngayDuyet, trangThai, maNV, maNguoiDuyet };
			donXinModel.addRow(row);
		}
		TableColumnModel tcm = donXinTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(100);
		tcm.getColumn(1).setPreferredWidth(120);
		tcm.getColumn(2).setPreferredWidth(120);
		tcm.getColumn(3).setPreferredWidth(110);
		tcm.getColumn(4).setPreferredWidth(400);
		tcm.getColumn(5).setPreferredWidth(120);
		tcm.getColumn(6).setPreferredWidth(99);
		tcm.getColumn(7).setPreferredWidth(100);
		tcm.getColumn(8).setPreferredWidth(90);

		donXinTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Ngăn các cột tự resize

	}

	private void refreshList() {
		// Xóa tất cả các dòng trong model table
		donXinModel.setRowCount(0);
		loadDonXinNghiList();
		txtTimKiem.setText("");

	}

	private void updateTrangThai(String maDon, String trangThai) {
		String maNguoiDuyet = dnBUS.getMaNV();
		int selectedIndex = trangThaiDonXinComboBox.getSelectedIndex();
		if (selectedIndex != -1) {
			String message = donXinBUS.updateTrangThai(maDon, trangThai, maNguoiDuyet);

			if (message.equalsIgnoreCase("Cập nhật trạng thái đơn thành công!"))
				JOptionPane.showMessageDialog(null, message);
			else if (message.equalsIgnoreCase("Cập nhật trạng thái đơn thất bại!"))
				JOptionPane.showMessageDialog(null, message);
		}
	}

	public static void log(String message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
		System.out.println(element.getClassName() + " | method: " + element.getMethodName() + " | line: "
				+ element.getLineNumber() + " | " + message);
	}
}