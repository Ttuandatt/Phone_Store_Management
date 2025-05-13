package GUI;

import BUS.BangChamCongBUS;
import BUS.BangLuongBUS;
import BUS.NhanVienBUS;
import Components.ShadowButton;
import DTO.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class DSBangLuongGUI extends JPanel {
	NhanVienBUS nvBUS = new NhanVienBUS();
	BangChamCongBUS bccBUS = new BangChamCongBUS();
	BangLuongBUS blBUS = new BangLuongBUS();
	private static JTable blTable;
	DefaultTableModel blModel = new DefaultTableModel();
	ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>();
	private JComboBox<String> sortComboBox;
	private JComboBox<String> jc_thang, jc_nam;
	ArrayList<BangLuongDTO> arrBangLuong = null;
	JPanel bangLuongContent;
	JTextField txtTimKiem;

	JLabel lb_thuong, lb_khoanTru, lb_bhxh, lb_bhyt, lb_bhtn, lb_thue, lb_tamUng, lb_phuCap, lb_pcAnTrua, lb_pcDiChuyen;
	JTextField tf_thuong, tf_bhxh, tf_bhyt, tf_bhtn, tf_thue, tf_tamUng, tf_pcAnTrua, tf_pcDiChuyen;
	JButton btnLuu, btnDuyet;

	// Constructor
	public DSBangLuongGUI() {
		initComponents();
		loadBangLuongList();
	}

	private void initComponents() {
		setLayout(new GridBagLayout()); // set Layout
		GridBagConstraints gbc = new GridBagConstraints();
		bangLuongContent = new JPanel();
		bangLuongContent.setBackground(Color.white);
		bangLuongContent.setLayout(new GridBagLayout());

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(bangLuongContent, gbc); // Thêm vào ProductsGUI

		// tạo 2 panel topPanel, bottomPanel cho khu vực tìm kiếm và khu vực hiển thị
		// bảng danh sách
		JPanel topPanel, bottomPanel;
		// set thông số cho 2 panel
		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		topPanel.setBackground(Color.white);
		gbc.weightx = 1.0;
		gbc.weighty = 0.25;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		bangLuongContent.add(topPanel, gbc);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		bottomPanel.setBackground(Color.white);
		bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 2)));
		gbc.weightx = 1.0;
		gbc.weighty = 0.75;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		bangLuongContent.add(bottomPanel, gbc);

//==================================================== TOP PANEL =============================================================================================//
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
		JPanel updateButtonPanel, detailButtonPanel, excelButtonPanel, printButtonPanel;

		detailButtonPanel = new JPanel();
		detailButtonPanel.setBackground(Color.white);
		detailButtonPanel.setBounds(10, 4, 60, 60);
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

		// Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
		ImageIcon iconDetail = new ImageIcon(getClass().getResource("/img/info.png")); // Đặt đường dẫn ảnh ở đây
		Image imgDetail = iconDetail.getImage();
		Image newImgDetail = imgDetail.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
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
				int[] selectedRows = blTable.getSelectedRows();
				if (selectedRows.length == 1) {
					String maBL = blTable.getValueAt(selectedRows[0], 0).toString();
					BangLuongDTO bangLuong = blBUS.selectById(maBL);
					DecimalFormat df = new DecimalFormat("#,###");
					Float luongTT = Float.valueOf(blTable.getValueAt(selectedRows[0], 5).toString().replace(",", ""));
					Float luongOT = Float.valueOf(blTable.getValueAt(selectedRows[0], 6).toString().replace(",", ""));
					Float phuCap = Float.valueOf(blTable.getValueAt(selectedRows[0], 7).toString().replace(",", ""));
					Float thuong = Float.valueOf(blTable.getValueAt(selectedRows[0], 8).toString().replace(",", ""));
					Float khoanTru = Float.valueOf(blTable.getValueAt(selectedRows[0], 9).toString().replace(",", ""));

					Float tongtn = luongTT + luongOT + phuCap + thuong;
					String tongTN = df.format(tongtn);
					Float thucnhan = tongtn - khoanTru;
					String thucNhan = df.format(thucnhan);
					ChiTietBangLuongGUI ctbl = new ChiTietBangLuongGUI(bangLuong, tongTN, thucNhan);
					ctbl.setVisible(true);
				} else
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên!", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
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
                ex.excelExporterBangChamCong();
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
					sortAZ();
					break;
				case "Z-A":
					sortZA();
					break;
				}
			}
		});

		String[] thang = { "Tháng", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		jc_thang = new JComboBox<String>(thang);
		jc_thang.setBounds(90, 24, 80, 25);
		searchInputPanel.add(jc_thang);
		jc_thang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadBangLuongTheoThoiGian();
			}
		});

		LocalDate current = LocalDate.now();
		String[] nam_title = new String[6];
		nam_title[0] = "Năm";
		for (int i = 1; i < nam_title.length; i++) {
			nam_title[i] = current.getYear() - 2 + i + "";
		}
		jc_nam = new JComboBox<String>();
		jc_nam.setBounds(185, 24, 80, 25);
		jc_nam.setModel(new DefaultComboBoxModel<>(nam_title));
		searchInputPanel.add(jc_nam);
		jc_nam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadBangLuongTheoThoiGian();
			}
		});

		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(440, 24, 200, 25);
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
				searchPerformed();
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

		// ========================= Table =========================//
		blTable = new JTable();
		JScrollPane sp = new JScrollPane(blTable);
		gbc.weightx = 0.8;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(sp, gbc);
		blTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		blTable.setDefaultEditor(Object.class, null); // không cho click vào & edit nội dung các cell trong bảng
		blTable.setModel(blModel);
		blModel.addColumn("MaBL");
		blModel.addColumn("Nhân viên");
		blModel.addColumn("Thời gian");
		blModel.addColumn("Lương cơ bản");
		blModel.addColumn("Hệ số");
		blModel.addColumn("Lương thực tế");
		blModel.addColumn("Lương tăng ca");
		blModel.addColumn("Phụ cấp");
		blModel.addColumn("Lương thưởng");
		blModel.addColumn("Các khoản trừ");
		blModel.addColumn("Thực nhận");
		blModel.addColumn("Trạng thái");

		// Điều chỉnh kích thước các cột
		TableColumnModel tcm = blTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(120);
		tcm.getColumn(1).setPreferredWidth(150);
		tcm.getColumn(2).setPreferredWidth(80);
		tcm.getColumn(3).setPreferredWidth(80);
		tcm.getColumn(4).setPreferredWidth(50);
		tcm.getColumn(5).setPreferredWidth(90);
		tcm.getColumn(6).setPreferredWidth(90);
		tcm.getColumn(7).setPreferredWidth(90);
		tcm.getColumn(8).setPreferredWidth(90);
		tcm.getColumn(9).setPreferredWidth(90);
		tcm.getColumn(10).setPreferredWidth(90);
		tcm.getColumn(11).setPreferredWidth(90);

		blTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Ngăn các cột tự resize

		/*
		 * blTable.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent e) { if(e.getClickCount()>=1) {
		 * //nếu nhấn vào dòng đó từ 1 lần trở lên layChiTietBangLuong(); } } });
		 */

		JPanel attendancePanel = new JPanel(new GridBagLayout());
		attendancePanel.setBackground(Color.white);
		attendancePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 2)));
		gbc.weightx = 0.2;
		gbc.weighty = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomPanel.add(attendancePanel, gbc);
		attendancePanel.setBorder(BorderFactory.createTitledBorder("CHI TIẾT LƯƠNG"));

		lb_thuong = new JLabel("MỨC THƯỞNG (%)");
		gbc.gridx = 0;
		gbc.gridy = 0;
		lb_thuong.setFont(new Font("Arial", Font.BOLD, 13));
		attendancePanel.add(lb_thuong, gbc);
		tf_thuong = new JTextField();
		gbc.gridx = 1;
		attendancePanel.add(tf_thuong, gbc);

		/// Các khoản trừ
		lb_khoanTru = new JLabel("CÁC KHOẢN TRỪ ");
		lb_khoanTru.setFont(new Font("Arial", Font.BOLD, 13));
		gbc.gridx = 0;
		gbc.gridy = 1;
		attendancePanel.add(lb_khoanTru, gbc);

		lb_bhxh = new JLabel("Bảo hiểm xã hội: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		lb_bhxh.setFont(new Font("Arial", Font.PLAIN, 14));
		attendancePanel.add(lb_bhxh, gbc);
		tf_bhxh = new JTextField();
		gbc.gridx = 1;
		attendancePanel.add(tf_bhxh, gbc);

		lb_bhyt = new JLabel("Bảo hiểm y tế: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		lb_bhyt.setFont(new Font("Arial", Font.PLAIN, 14));
		attendancePanel.add(lb_bhyt, gbc);
		tf_bhyt = new JTextField();
		gbc.gridx = 1;
		attendancePanel.add(tf_bhyt, gbc);

		lb_bhtn = new JLabel("Bảo hiểm tai nạn: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		lb_bhtn.setFont(new Font("Arial", Font.PLAIN, 14));
		attendancePanel.add(lb_bhtn, gbc);
		tf_bhtn = new JTextField();
		gbc.gridx = 1;
		attendancePanel.add(tf_bhtn, gbc);

		lb_thue = new JLabel("Thuế thu nhập cá nhân: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		lb_thue.setFont(new Font("Arial", Font.PLAIN, 14));
		attendancePanel.add(lb_thue, gbc);
		tf_thue = new JTextField();
		gbc.gridx = 1;
		attendancePanel.add(tf_thue, gbc);

		lb_tamUng = new JLabel("Tạm ứng:");
		gbc.gridx = 0;
		gbc.gridy = 6;
		lb_tamUng.setFont(new Font("Arial", Font.PLAIN, 14));
		attendancePanel.add(lb_tamUng, gbc);
		tf_tamUng = new JTextField();
		gbc.gridx = 1;
		attendancePanel.add(tf_tamUng, gbc);

		lb_phuCap = new JLabel("PHỤ CẤP");
		lb_phuCap.setFont(new Font("Arial", Font.BOLD, 13));
		gbc.gridx = 0;
		gbc.gridy = 7;
		attendancePanel.add(lb_phuCap, gbc);

		lb_pcAnTrua = new JLabel("Phụ cấp cơm trưa:");
		gbc.gridx = 0;
		gbc.gridy = 8;
		lb_pcAnTrua.setFont(new Font("Arial", Font.PLAIN, 14));
		attendancePanel.add(lb_pcAnTrua, gbc);
		tf_pcAnTrua = new JTextField();
		gbc.gridx = 1;
		attendancePanel.add(tf_pcAnTrua, gbc);

		lb_pcDiChuyen = new JLabel("Phụ cấp đi lại:");
		lb_pcDiChuyen.setFont(new Font("Arial", Font.PLAIN, 14));
		gbc.gridx = 0;
		gbc.gridy = 9;
		attendancePanel.add(lb_pcDiChuyen, gbc);
		tf_pcDiChuyen = new JTextField();
		gbc.gridx = 1;
		attendancePanel.add(tf_pcDiChuyen, gbc);

		JPanel panelOptions = new JPanel(new GridBagLayout());
		panelOptions.setBackground(Color.white);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 10;
		attendancePanel.add(panelOptions, gbc);

		btnLuu = new JButton("Lưu");
		gbc.gridx = 0;
		gbc.gridy = 0;
		btnLuu.setFont(new Font("Arial", Font.BOLD, 14));
		btnLuu.setForeground(Color.white);
		btnLuu.setBackground(Color.decode("#37A4F2"));
		btnLuu.setBorderPainted(false);
		btnLuu.setFocusPainted(false);
		btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panelOptions.add(btnLuu, gbc);

		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLuuSuaBangLuong();
			}
		});

		btnDuyet = new JButton("Duyệt");
		gbc.gridx = 1;
		gbc.gridy = 0;
		btnDuyet.setFont(new Font("Arial", Font.BOLD, 14));
		btnDuyet.setForeground(Color.white);
		btnDuyet.setBackground(Color.decode("#37A4F2"));
		btnDuyet.setBorderPainted(false);
		btnDuyet.setFocusPainted(false);
		btnDuyet.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panelOptions.add(btnDuyet, gbc);

		btnDuyet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDuyetBangLuong();
			}
		});
	}

	private ArrayList<BangLuongDTO> loadBangLuongList() {
		blModel.setRowCount(0);
		ArrayList<BangLuongDTO> arrBangLuong = blBUS.selectAll();
		String trangThai = null;
		for (BangLuongDTO bl : arrBangLuong) {
			NhanVienDTO nv = nvBUS.selectById(bl.getMaNV());
			String mabcc = bl.getMaLuong().replaceFirst("BL", "CC");
			BangChamCongDTO bcc = bccBUS.selectById(mabcc);

			String maBL = bl.getMaLuong();
			String nvien = nv.getMaNV() + " - " + nv.getHoTen();
			String thoiGian = bl.getThangLuong() + "/" + bl.getNamLuong();

			DecimalFormat df = new DecimalFormat("#,###");

			String luongCB = df.format(bl.getLuongCB());

			Float heSo = bl.getHeSo();
			Float luongTT = ((bl.getLuongCB() * heSo) / 24) * bcc.getSoNgayLam();
			String luongThucTe = df.format(luongTT);

			Float t = (bl.getLuongCB() * heSo) / (24 * 8);
			Float luongOT = t * bcc.getSoGioOTNgayThuong() + t * 2 * bcc.getSoGioOTCN()
					+ t * 3 * bcc.getSoGioOTNgayLe();
			String luongTangCa = df.format(luongOT);

			Float pc = bl.getPhuCapAnTrua() + bl.getPhuCapDiLai();
			String phuCap = df.format(pc);

			Float luongThuong = bl.getThuong();
			String thuong = df.format(luongThuong);

			Float cacKhoanTru = bl.getBhxh() + bl.getBhtn() + bl.getBhyt() + bl.getTamUng() + bl.getThue();
			String khoanTru = df.format(cacKhoanTru);

			Float thucnhan = luongTT + luongOT + pc + luongThuong - cacKhoanTru;
			String thucNhan = df.format(thucnhan);
			bl.setThucNhan(thucnhan);
			blBUS.updateBangLuong(bl);
			// System.out.println("loadBangLuongList() - thucNhan: " + thucNhan);

			if (bl.getTrangThai().equals("on"))
				trangThai = "Đã duyệt";
			else if (bl.getTrangThai().equals("off"))
				trangThai = "Chưa duyệt";

			Object[] row = { maBL, nvien, thoiGian, luongCB, heSo, luongThucTe, luongTangCa, phuCap, thuong, khoanTru,
					thucNhan, trangThai };
			blModel.addRow(row);
		}
		return arrBangLuong;
	}

	private void loadBangLuongTheoThoiGian() {
		blModel.setRowCount(0);
		String thang = jc_thang.getSelectedItem().toString();
		String nam = jc_nam.getSelectedItem().toString();
		if (!thang.equals("Tháng") || !nam.equals("Năm")) {
			ArrayList<BangLuongDTO> arrBangLuong = blBUS.selectByTime(Integer.parseInt(thang), Integer.parseInt(nam));
			String trangThai = null;
			for (BangLuongDTO bl : arrBangLuong) {
				NhanVienDTO nv = nvBUS.selectById(bl.getMaNV());
				String mabcc = bl.getMaLuong().replaceFirst("BL", "CC");
				BangChamCongDTO bcc = bccBUS.selectById(mabcc);

				String maBL = bl.getMaLuong();
				String nvien = nv.getMaNV() + " - " + nv.getHoTen();
				String thoiGian = bl.getThangLuong() + "/" + bl.getNamLuong();
				DecimalFormat df = new DecimalFormat("#,###");

				String luongCB = df.format(bl.getLuongCB());

				Float heSo = bl.getHeSo();
				Float luongTT = ((bl.getLuongCB() * heSo) / 24) * bcc.getSoNgayLam();
				String luongThucTe = df.format(luongTT);

				Float t = (bl.getLuongCB() * heSo) / (24 * 8);
				Float luongOT = t * bcc.getSoGioOTNgayThuong() + t * 2 * bcc.getSoGioOTCN()
						+ t * 3 * bcc.getSoGioOTNgayLe();
				String luongTangCa = df.format(luongOT);

				Float pc = bl.getPhuCapAnTrua() + bl.getPhuCapDiLai();
				String phuCap = df.format(pc);

				Float luongThuong = bl.getThuong();
				String thuong = df.format(luongThuong);

				Float cacKhoanTru = bl.getBhxh() + bl.getBhtn() + bl.getBhyt() + bl.getTamUng() + bl.getThue();
				String khoanTru = df.format(cacKhoanTru);

				Float thucnhan = bl.getThucNhan();
				String thucNhan = df.format(thucnhan);

				if (bl.getTrangThai().equals("on"))
					trangThai = "Đã duyệt";
				else if (bl.getTrangThai().equals("off"))
					trangThai = "Chưa duyệt";

				Object[] row = { maBL, nvien, thoiGian, luongCB, heSo, luongThucTe, luongTangCa, phuCap, thuong,
						khoanTru, thucNhan, trangThai };
				blModel.addRow(row);
			}
		} else
			loadBangLuongList();
	}

	private void btnLuuSuaBangLuong() {
		int[] selectedRows = blTable.getSelectedRows();

		// Kiểm tra nếu không có dòng nào được chọn hoặc tất cả các ô nhập đều trống
		if (selectedRows.length < 1 || (tf_thuong.getText().trim().isEmpty() && tf_bhxh.getText().trim().isEmpty()
				&& tf_bhyt.getText().trim().isEmpty() && tf_bhtn.getText().trim().isEmpty()
				&& tf_thue.getText().trim().isEmpty() && tf_tamUng.getText().trim().isEmpty()
				&& tf_pcAnTrua.getText().trim().isEmpty() && tf_pcDiChuyen.getText().trim().isEmpty())) {

			JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên và nhập chi tiết!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Kiểm tra định dạng số và không âm
		JTextField[] fields = { tf_thuong, tf_bhxh, tf_bhyt, tf_bhtn, tf_thue, tf_tamUng, tf_pcAnTrua, tf_pcDiChuyen };
		String[] fieldNames = { "Thưởng", "BHXH", "BHYT", "BHTN", "Thuế TNCN", "Tạm ứng", "Phụ cấp ăn trưa",
				"Phụ cấp di chuyển" };

		for (int i = 0; i < fields.length; i++) {
			String text = fields[i].getText().trim();
			if (!text.isEmpty()) {
				try {
					float value = Float.parseFloat(text);
					if (value < 0) {
						JOptionPane.showMessageDialog(null, fieldNames[i] + " không được là số âm!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, fieldNames[i] + " phải là số hợp lệ!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

		int count = 0;
		for (int row : selectedRows) {
			String maBL = blTable.getValueAt(row, 0).toString();
			BangLuongDTO bl = blBUS.selectById(maBL);

			if (!tf_thuong.getText().trim().isEmpty()) {
				bl.setThuong((bl.getLuongCB() / 100) * Float.parseFloat(tf_thuong.getText()));
				// System.out.println("Thuong: " + bl.getThuong());
			}
			if (!tf_bhxh.getText().trim().isEmpty()) {
				bl.setBhxh(Float.parseFloat(tf_bhxh.getText()));
				// System.out.println("BHXH: " + bl.getBhxh());
			}
			if (!tf_bhyt.getText().trim().isEmpty()) {
				bl.setBhyt(Float.parseFloat(tf_bhyt.getText()));
				// System.out.println("BHYT: " + bl.getBhyt());
			}
			if (!tf_bhtn.getText().trim().isEmpty()) {
				bl.setBhtn(Float.parseFloat(tf_bhtn.getText()));
				// System.out.println("BHTN: " + bl.getBhtn());
			}
			if (!tf_thue.getText().trim().isEmpty()) {
				bl.setThue(Float.parseFloat(tf_thue.getText()));
				// System.out.println("Thue: " + bl.getThue());
			}
			if (!tf_tamUng.getText().trim().isEmpty()) {
				bl.setTamUng(Float.parseFloat(tf_tamUng.getText()));
				// System.out.println("Tam Ung: " + bl.getTamUng());
			}
			if (!tf_pcAnTrua.getText().trim().isEmpty()) {
				bl.setPhuCapAnTrua(Float.parseFloat(tf_pcAnTrua.getText()));
				// System.out.println("An: " + bl.getPhuCapAnTrua());
			}
			if (!tf_pcDiChuyen.getText().trim().isEmpty()) {
				bl.setPhuCapDiLai(Float.parseFloat(tf_pcDiChuyen.getText()));
				// System.out.println("Di lai: " + bl.getPhuCapDiLai());
			}

			String mabcc = bl.getMaLuong().replaceFirst("BL", "CC");
			BangChamCongDTO bcc = bccBUS.selectById(mabcc);

			Float luongTT = (bl.getLuongCB() * bl.getHeSo()) / 24 * bcc.getSoNgayLam();
			// luongTT = (float) Math.round(luongTT);

			Float t = (bl.getLuongCB() * bl.getHeSo()) / (24 * 8);
			Float luongTangCa = t * bcc.getSoGioOTNgayThuong() + t * 2 * bcc.getSoGioOTCN()
					+ t * 3 * bcc.getSoGioOTNgayLe();
			// luongTangCa = (float) Math.round(luongTangCa);

			Float phuCap = bl.getPhuCapAnTrua() + bl.getPhuCapDiLai();
			phuCap = (float) Math.round(phuCap);
			// System.out.println("Phu cap: " + phuCap); //
			Float thuong = bl.getThuong();
			Float khoanTru = bl.getBhxh() + bl.getBhtn() + bl.getBhyt() + bl.getTamUng() + bl.getThue();
			// System.out.println("Khoan tru: " + khoanTru); //
			// khoanTru = (float) Math.round(khoanTru);
			Float thucnhan = luongTT + luongTangCa + phuCap + thuong - khoanTru;
			int thucNhan = (int) Math.round(thucnhan);
			bl.setThucNhan(thucNhan);
			// System.out.println("Thuc Nhan: " + bl.getThucNhan() + " ; " + thucNhan); //

			int resultUpdate = blBUS.updateBangLuong(bl);
			if (resultUpdate > 0) {
				count++;
			}
		}
		System.out.println("== count:" + count);
		if (count == selectedRows.length) {
			JOptionPane.showMessageDialog(null, "Thêm chi tiết chấm công thành công", "Thành công",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Thêm chi tiết chấm công thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}

		loadBangLuongList();
	}

	private void btnDuyetBangLuong() {
		int[] selectedRows = blTable.getSelectedRows();
		if (selectedRows.length >= 1) {
			for (int row : selectedRows) {
				String maBL = blTable.getValueAt(row, 0).toString();
				BangLuongDTO bangLuong = blBUS.selectById(maBL);
				bangLuong.setTrangThai("on");
				blBUS.updateBangLuong(bangLuong);
			}
		}
		loadBangLuongList();
	}

	private void searchPerformed() {
		blModel.setRowCount(0);
		String searchContent = txtTimKiem.getText().trim();
		if (!searchContent.isEmpty()) {
			ArrayList<BangLuongDTO> dsTimKiem = blBUS.selectByKeyWord(searchContent);
			String trangThai = null;
			for (BangLuongDTO bl : dsTimKiem) {
				NhanVienDTO nv = nvBUS.selectById(bl.getMaNV());
				String mabcc = bl.getMaLuong().replaceFirst("BL", "CC");
				BangChamCongDTO bcc = bccBUS.selectById(mabcc);

				String maBL = bl.getMaLuong();
				String nvien = nv.getMaNV() + " - " + nv.getHoTen();
				String thoiGian = bl.getThangLuong() + "/" + bl.getNamLuong();
				DecimalFormat df = new DecimalFormat("#,###");

				String luongCB = df.format(bl.getLuongCB());

				Float heSo = bl.getHeSo();
				Float luongTT = ((bl.getLuongCB() * heSo) / 24) * bcc.getSoNgayLam();
				String luongThucTe = df.format(luongTT);

				Float t = (bl.getLuongCB() * heSo) / (24 * 8);
				Float luongOT = t * bcc.getSoGioOTNgayThuong() + t * 2 * bcc.getSoGioOTCN()
						+ t * 3 * bcc.getSoGioOTNgayLe();
				String luongTangCa = df.format(luongOT);

				Float pc = bl.getPhuCapAnTrua() + bl.getPhuCapDiLai();
				String phuCap = df.format(pc);

				Float luongThuong = bl.getThuong();
				String thuong = df.format(luongThuong);

				Float cacKhoanTru = bl.getBhxh() + bl.getBhtn() + bl.getBhyt() + bl.getTamUng() + bl.getThue();
				String khoanTru = df.format(cacKhoanTru);

				// Float thucNhan = luongTT + luongOT + pc + luongThuong - cacKhoanTru;
				String thucNhan = df.format(luongTT + luongOT + pc + luongThuong - cacKhoanTru);

				if (bl.getTrangThai().equals("on"))
					trangThai = "Đã duyệt";
				else if (bl.getTrangThai().equals("off"))
					trangThai = "Chưa duyệt";

				Object[] row = { maBL, nvien, thoiGian, luongCB, heSo, luongThucTe, luongTangCa, phuCap, thuong,
						khoanTru, thucNhan, trangThai };
				blModel.addRow(row);
			}
		} else {
			// Nếu người dùng không nhập nội dung tìm kiếm, thực hiện làm mới bảng để hiển
			// thị tất cả sản phẩm
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm");
			refreshList();
		}
	}

	private void refreshList() {
		// Xóa tất cả các dòng trong mô hình bảng
		blModel.setRowCount(0);
		blModel.setColumnCount(0);
		sortComboBox.setSelectedIndex(0);
		txtTimKiem.setText("");
		jc_thang.setSelectedIndex(0);
		jc_nam.setSelectedIndex(0);
		loadBangLuongList();
	}

	private void sortAZ() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(blModel);
		blTable.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();

		int columnIndexSort = 1; // 1 là chỉ số cột tên nhân viên, cần sắp xếp
		sortKeys.add(new RowSorter.SortKey(columnIndexSort, SortOrder.ASCENDING));

		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}

	private void sortZA() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(blModel);
		blTable.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();

		int columnIndexSort = 1; // 1 là chỉ số cột tên nhân viên, cần sắp xếp
		sortKeys.add(new RowSorter.SortKey(columnIndexSort, SortOrder.DESCENDING));

		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}

	// hàm hiển thị thông tin dòng code
	public static void log(String message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
		System.out.println(element.getClassName() + " | method: " + element.getMethodName() + " | line: "
				+ element.getLineNumber() + " | " + message);
	}
}
