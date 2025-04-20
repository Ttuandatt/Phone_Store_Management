package GUI;

import BUS.ChiTietPhieuXuatBUS;
import BUS.ChiTietPhieuXuatBUS;
import BUS.PhienBanSanPhamBUS;
import BUS.PhieuXuatBUS;
import BUS.PhieuXuatBUS;
import BUS.SanPhamBUS;
import Components.DateConverter;
import Components.ShadowButton;
import DTO.*;
import net.miginfocom.layout.Grid;
import DAO.SanPhamDAO;

import java.awt.Color;
import java.awt.Cursor;
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
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser; // Thêm thư viện JCalendar
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

public class XuatHangGUI extends JPanel {

	PhienBanSanPhamBUS pbspBUS = new PhienBanSanPhamBUS();
	SanPhamBUS spBUS = new SanPhamBUS();
	PhieuXuatBUS pxBUS = new PhieuXuatBUS();
	ChiTietPhieuXuatBUS ctpxBUS = new ChiTietPhieuXuatBUS();
	JTable productTable, chosenProductTable;
	DefaultTableModel productModel, chosenProductModel;
	ArrayList<PhienBanSanPhamDTO> arrPBSP = new ArrayList<PhienBanSanPhamDTO>(); // Tạo ArrayList sp với kiểu là ProductsDTO
	JComboBox<String> htttComboBox;
	JPanel pxContent;
	JLabel imageLabel, lblTongTien, lblMapx, lblMaKho, lblMaNguoiTao, lblKhachHang, lblNgayTao, lblDiaChi, lblHTTT;
	JTextField txtTimKiem, txtMapx, txtMaKho, txtMaNguoiTao, txtNgayTao, txtDiaChi, txtKhachHang;
	// Lấy ngày hiện tại
	LocalDate currentDate = LocalDate.now();
	// Định dạng ngày thành dd/MM/yyyy
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	// Constructor
	public XuatHangGUI() {
		this.setLayout(new GridLayout(1, 2, 10, 10));
		initComponents();
		loadProductList();
		loadChosenProduct();
	}

	////////////////////////////////////////// METHODS//////////////////////////////////////
	private void initComponents() {
		// Dùng thư viện FlatLaf để làm giao diện đẹp hơn
		FlatRobotoFont.install();
		FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
		FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
		FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
		FlatIntelliJLaf.registerCustomDefaultsSource("style");
		FlatIntelliJLaf.setup();
		setLayout(new GridBagLayout()); // set Layout
		GridBagConstraints gbc = new GridBagConstraints();
		pxContent = new JPanel();
//		pxContent.setBackground(Color.green);
		pxContent.setBackground(Color.white);
		pxContent.setLayout(new GridLayout(1, 2, 15, 15));

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(pxContent, gbc); // Thêm vào ProductsGUI

		// tạo 2 panel leftPanel, rightPanel
		JPanel leftPanel, rightPanel;
		// set thông số cho 2 panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridBagLayout());
//		leftPanel.setBackground(Color.blue);
		leftPanel.setBackground(Color.white);
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		pxContent.add(leftPanel, gbc);

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
//		rightPanel.setBackground(Color.decode("#372083"));
		rightPanel.setBackground(Color.white);
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		pxContent.add(rightPanel, gbc);

		//LEFT PANEL
		// Chia 3 panel con searchLeftPanel ở phần trên, productListLeftPanel ở phần
		// giữa, quantityLeftPanel ở phần dưới;
		JPanel searchLeftPanel, productListLeftPanel, quantityLeftPanel;

		// set thông số cho 3 panel
		searchLeftPanel = new JPanel();
		searchLeftPanel.setLayout(null);
//		searchLeftPanel.setBackground(Color.decode("#C62E65"));
		searchLeftPanel.setBackground(Color.white);
		searchLeftPanel.setBorder(BorderFactory.createTitledBorder(""));
		gbc.weightx = 1.0;
		gbc.weighty = 0.2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		leftPanel.add(searchLeftPanel, gbc);
		
		
		productListLeftPanel = new JPanel();
		productListLeftPanel.setLayout(new GridBagLayout());
//		productListLeftPanel.setBackground(Color.decode("#5D536B"));
		productListLeftPanel.setBackground(Color.white);
		productListLeftPanel.setBorder(BorderFactory.createTitledBorder(""));
		gbc.weightx = 1.0;
		gbc.weighty = 0.52;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		leftPanel.add(productListLeftPanel, gbc);


		quantityLeftPanel = new JPanel();
		quantityLeftPanel.setLayout(null);
//		quantityLeftPanel.setBackground(Color.decode("#989FCE"));
		quantityLeftPanel.setBackground(Color.white);
		gbc.weightx = 1.0;
		gbc.weighty = 0.2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		leftPanel.add(quantityLeftPanel, gbc);

		// ===================================== searchLeftPanel ======================================//
//		// DateChooser
//		JDateChooser dateChooser = new JDateChooser();
//		dateChooser.setBounds(10, 22, 150, 25); // Định vị
//		searchLeftPanel.add(dateChooser);

		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(300, 22,235, 25);
		searchLeftPanel.add(txtTimKiem);

		// Tạo nút Search
		ImageIcon iconSearch = new ImageIcon(getClass().getResource("/img/loupe2.png"));
		Image imgSearch = iconSearch.getImage();
		Image newImgSearch = imgSearch.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		if (iconSearch.getIconWidth() == -1) {
			System.out.println("Không tìm thấy ảnh!");
		}
		ImageIcon scaledIconSearch = new ImageIcon(newImgSearch);

		ShadowButton btnSearch = new ShadowButton(scaledIconSearch);
		btnSearch.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSearch.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSearch.setFocusPainted(false);
		btnSearch.setBorderPainted(true);
		btnSearch.setBackground(Color.white);
		btnSearch.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchPerformed(productTable);
			}
		});
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSearch.setBackground(Color.decode("#D2E4EE")); // Màu khi hover vào
				btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSearch.setBackground(Color.white); // Màu khi hover ra
			}
		});
		btnSearch.setBounds(540, 15, 40, 40);
		searchLeftPanel.add(btnSearch);

		// Tạo nút Refresh
		ImageIcon iconRefresh = new ImageIcon(getClass().getResource("/img/refresh.png")); 
		Image imgRefresh = iconRefresh.getImage();
		Image newImgRefresh = imgRefresh.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		if (iconSearch.getIconWidth() == -1) {
			System.out.println("Không tìm thấy ảnh!");
		}
		ImageIcon scaledIconRefresh = new ImageIcon(newImgRefresh);
		JButton btnRefresh = new ShadowButton(scaledIconRefresh);
		btnRefresh.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRefresh.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRefresh.setFocusPainted(false);
		btnRefresh.setBorderPainted(true);
		btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRefresh.setBackground(Color.white);
		btnRefresh.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshList();
			}
		});
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRefresh.setBackground(Color.decode("#D2E4EE")); // Màu khi hover vào
				btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnRefresh.setBackground(Color.white); // Màu khi hover ra
			}
		});
		btnRefresh.setBounds(585, 15, 40, 40);
		searchLeftPanel.add(btnRefresh);

		
		
		// ======================= productListLeftPanel =======================//
		//Thêm table vào panel để hiển thị danh sách sản phẩm
		productTable = new JTable();
		JScrollPane sp = new JScrollPane(productTable);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		productListLeftPanel.add(sp, gbc);
		
		
		
		
		
		
		
		
		
		// ======================= quantityLeftPanel =======================//
		JLabel quantityLabel = new JLabel("Nhập số lượng: ");
		quantityLabel.setBounds(10, 22, 100, 20);
		quantityLeftPanel.add(quantityLabel);

		JTextField txtSoLuong = new JTextField();
		txtSoLuong.setBounds(100, 20, 50, 25);
		quantityLeftPanel.add(txtSoLuong);

		JButton quantityButton = new ShadowButton("OK");
		quantityButton.setBounds(180, 20, 60, 25);
//        quantityButton.setOpaque(true);
		quantityButton.setBackground(Color.white);
		quantityButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quantityButton.setBackground(Color.decode("#D2E4EE")); // Màu khi hover vào
				quantityButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				quantityButton.setBackground(Color.white); // Màu khi hover ra
			}
			
			//set hành động sau khi nhập số lượng và click vào nút ok thì sẽ hiển thị thông tin đã chọn qua bên bảng bên phải 
			@Override
			public void mouseClicked(MouseEvent e) {
				addToExportTable(productTable, chosenProductTable, txtSoLuong);
				productTable.clearSelection(); //sau khi chọn xong thì làm mới lại bảng bên trai để k có dòng nào được chọn
			}

		});
		quantityLeftPanel.add(quantityButton);

		

		
		
		
		//RIGHT PANEL
		// Chia 3 panel con informationRightPanel ở phần trên, productChoseRightPanel ở
		// phần giữa, optionRightPanel ở phần dưới;
		JPanel informationRightPanel, productChoseRightPanel, optionRightPanel;

		// set thông số cho 3 panel
		informationRightPanel = new JPanel();
		informationRightPanel.setLayout(null);
//		informationRightPanel.setBackground(Color.decode("#F7C548"));
		informationRightPanel.setBackground(Color.white);
		informationRightPanel.setBorder(BorderFactory.createTitledBorder(""));
		gbc.weightx = 1.0;
		gbc.weighty = 0.41;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		rightPanel.add(informationRightPanel, gbc);

		productChoseRightPanel = new JPanel();
		productChoseRightPanel.setLayout(new GridBagLayout());
//		productChoseRightPanel.setBackground(Color.decode("#3AA7A3"));
		productChoseRightPanel.setBackground(Color.white);
		productChoseRightPanel.setBorder(BorderFactory.createTitledBorder(""));
		gbc.weightx = 1.0;
		gbc.weighty = 0.31;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		rightPanel.add(productChoseRightPanel, gbc);

		optionRightPanel = new JPanel();
		optionRightPanel.setLayout(null);
//		optionRightPanel.setBackground(Color.decode("#785589"));
		optionRightPanel.setBackground(Color.white);
		gbc.weightx = 1.0;
		gbc.weighty = 0.2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		rightPanel.add(optionRightPanel, gbc);

		///////////////////////////////////////// informationRightPanel ///////////////////////////////////////// 
		
		lblMapx = new JLabel("Mã phiếu xuất:");
		lblMapx.setBounds(10, 10, 100, 20);
		informationRightPanel.add(lblMapx);
		txtMapx = new JTextField();
		txtMapx.setBounds(110, 10, 100, 20);
		informationRightPanel.add(txtMapx);


		lblMaKho = new JLabel("Mã kho:");
		lblMaKho.setBounds(10, 40, 100, 20);
		informationRightPanel.add(lblMaKho);
		txtMaKho = new JTextField();
//		txtMaKho.setEditable(false);
//		txtMaKho.setEnabled(false);
		txtMaKho.setBounds(110, 40, 100, 20);
		informationRightPanel.add(txtMaKho);


		lblMaNguoiTao = new JLabel("Mã người tạo:");
		lblMaNguoiTao.setBounds(10, 70, 100, 20);
		informationRightPanel.add(lblMaNguoiTao);
		txtMaNguoiTao = new JTextField();
//		txtMaNguoiTao.setEditable(false);
//		txtMaNguoiTao.setEnabled(false);
		txtMaNguoiTao.setBounds(110, 70, 100, 20);
		informationRightPanel.add(txtMaNguoiTao);
		

		lblKhachHang = new JLabel("Khách hàng:");
		lblKhachHang.setBounds(10, 100, 100, 20);
		informationRightPanel.add(lblKhachHang);
		txtKhachHang = new JTextField();
		txtKhachHang.setBounds(110, 100,100,20);
		informationRightPanel.add(txtKhachHang);
		// Add combobox suppliers
		
		
		lblNgayTao = new JLabel("Ngày tạo:");
		lblNgayTao.setBounds(340, 10, 100, 20);
		informationRightPanel.add(lblNgayTao);
		txtNgayTao = new JTextField();
		txtNgayTao.setEditable(false);
		txtNgayTao.setEnabled(false);
		txtNgayTao.setBounds(400, 10, 100, 20);
		informationRightPanel.add(txtNgayTao);
		String ngayTao = String.valueOf(new java.sql.Date(System.currentTimeMillis()));
        txtNgayTao.setText(ngayTao);


		lblDiaChi = new JLabel("Địa chỉ: ");
		lblDiaChi.setBounds(340, 40, 100,20);
		informationRightPanel.add(lblDiaChi);
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(400, 40, 100, 20);
		informationRightPanel.add(txtDiaChi);
		
		lblHTTT = new JLabel("HTTT: ");
		lblHTTT.setBounds(340, 70, 100,20);
		informationRightPanel.add(lblHTTT);
		String[] httt= {"Tiền mặt", "Chuyển khoản"};
		htttComboBox = new JComboBox<String>(httt);
		htttComboBox.setBounds(400, 70, 100, 20);
		informationRightPanel.add(htttComboBox);
		
		

		
		
		
		///////////////////////////////////////// productChoseRightPanel ///////////////////////////////////////// 
		//Thêm bảng vào panel để hiển thị các sản phẩm đã được chọn để nhập
		chosenProductTable = new JTable();
		JScrollPane sp2 = new JScrollPane(chosenProductTable);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
			gbc.fill = GridBagConstraints.BOTH;
			productChoseRightPanel.add(sp2, gbc);
		
		
		
		///////////////////////////////////////// optionRightPanel /////////////////////////////////////////////
		JButton btnFixQuantity, btnRemoveProduct, btnExport;
		btnFixQuantity = new ShadowButton("Sửa số lượng");
		btnFixQuantity.setBounds(20, 20, 110, 25);
		optionRightPanel.add(btnFixQuantity);
		btnFixQuantity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnFixQuantity.setBackground(Color.decode("#D2E4EE"));
				btnFixQuantity.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnFixQuantity.setBackground(Color.white);
			}
		});
		btnFixQuantity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeQuantity(chosenProductTable);
			}
		});

		btnRemoveProduct = new ShadowButton("Xóa sản phẩm");
		btnRemoveProduct.setBounds(140, 20, 125, 25);
		optionRightPanel.add(btnRemoveProduct);
		btnRemoveProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRemoveProduct.setBackground(Color.decode("#D2E4EE"));
				btnRemoveProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnRemoveProduct.setBackground(Color.white);
			}
		});
		btnRemoveProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeFromChosenTable(chosenProductTable);
			}
		});

		btnExport = new ShadowButton("Xuất hàng");
		btnExport.setBounds(520, 20, 100, 25);
		optionRightPanel.add(btnExport);
		btnExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExport.setBackground(Color.decode("#D2E4EE"));
				btnExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExport.setBackground(Color.white);
			}
		});
		btnExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportProduct(chosenProductTable);
			}
		});

		JLabel lblTotal;
		lblTotal = new JLabel("Tổng tiền: ");
		lblTotal.setBounds(350, 20, 120, 25);
		optionRightPanel.add(lblTotal);
		lblTongTien = new JLabel("0đ"); // cai nay se lay tong tien setText lai sau, khi da co csdl
		lblTongTien.setBounds(420, 20, 300, 25);
		optionRightPanel.add(lblTongTien);


	}

	
	
	
	

	public void newCustomerDialog() {
		// Tạo panel chứa form nhập
		JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5)); // row:3, column:2, hgap:5, wgap:5

		JLabel nameLabel = new JLabel("Tên Khách hàng:");
		JTextField nameField = new JTextField(15);

		JLabel addressLabel = new JLabel("Địa chỉ:");
		JTextField addressField = new JTextField(15);

		JLabel phoneLabel = new JLabel("Số điện thoại:");
		JTextField phoneField = new JTextField(15);

		// Thêm các thành phần vào panel
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(addressLabel);
		panel.add(addressField);
		panel.add(phoneLabel);
		panel.add(phoneField);

		// Hiển thị dialog với panel
		int result = JOptionPane.showConfirmDialog(this, panel, "Thêm Khách hàng", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

		
	}

	private void loadProductList() {
		productTable.setDefaultEditor(Object.class, null);
		
		productModel = new DefaultTableModel();
		productTable.setModel(productModel);
		productModel.addColumn("Mã PBSP");
		productModel.addColumn("Tên sản phẩm");
		productModel.addColumn("Màu sắc");
		productModel.addColumn("RAM");
		productModel.addColumn("ROM");
		productModel.addColumn("Giá");
		productModel.addColumn("Số lượng");
		productModel.addColumn("Trạng thái");

		
		DecimalFormat df = new DecimalFormat("#,###"); // Định dạng số có dấu phân cách
		arrPBSP = pbspBUS.selectAll();
//		arrSP = 
		for(int i=0; i<arrPBSP.size(); i++) {
			PhienBanSanPhamDTO pbsp = arrPBSP.get(i);
			String maPBSP = pbsp.getMaPBSP();
			// Gọi BUS để lấy tên sản phẩm
			String tenSP = spBUS.getTenSanPhamByMaPBSP(maPBSP);
			String mauSac = pbsp.getMauSac();
			String ram = pbsp.getRam();
			String rom = pbsp.getRom();
			Double giaBan = pbsp.getGiaBan();
			int soLuong = pbsp.getSoLuong();
			String trangThai = pbsp.getTrangThai();
			// Format giá trước khi thêm vào bảng
		    String formattedGiaBan = df.format(giaBan);
			
			
			
			Object[] row = {maPBSP, tenSP, mauSac, ram, rom, formattedGiaBan, soLuong, trangThai};
			productModel.addRow(row);
		}
		
		
		//Điều chỉnh kích thước các cột
		TableColumnModel tcm = productTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(80);
		tcm.getColumn(1).setPreferredWidth(200);
		tcm.getColumn(2).setPreferredWidth(80);
		tcm.getColumn(3).setPreferredWidth(50);
		tcm.getColumn(4).setPreferredWidth(50);
		tcm.getColumn(5).setPreferredWidth(90);
		tcm.getColumn(6).setPreferredWidth(54);

		productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize

	}
	
	private void loadChosenProduct() {
		chosenProductModel  = new DefaultTableModel();
		chosenProductTable.setModel(chosenProductModel);
		chosenProductModel.addColumn("Mã PBSP");
		chosenProductModel.addColumn("Tên SP");
		chosenProductModel.addColumn("Màu sắc");
		chosenProductModel.addColumn("RAM");
		chosenProductModel.addColumn("ROM");
		chosenProductModel.addColumn("Số lượng");
		chosenProductModel.addColumn("Giá");

		
		//Điều chỉnh kích thước các cột
		TableColumnModel tcm = chosenProductTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(80);
		tcm.getColumn(1).setPreferredWidth(190);
		tcm.getColumn(2).setPreferredWidth(80);
		tcm.getColumn(3).setPreferredWidth(50);
		tcm.getColumn(4).setPreferredWidth(50);
		tcm.getColumn(5).setPreferredWidth(90);
		tcm.getColumn(6).setPreferredWidth(80);
		
		chosenProductTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize

	}
	
	private void addToExportTable(JTable productTable, JTable chosenProductTable, JTextField txtSoLuong) {
		int selectedRow = productTable.getSelectedRow();
		if(txtSoLuong.getText().equals("")) {	// 1. Kiểm tra đầu vào
			JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!");
		} else if(selectedRow != -1 && Integer.parseInt(txtSoLuong.getText().trim())>0) { // 2. Xử lý khi có hàng được chọn và số lượng hợp lệ
			// 3. Lấy giá sản phẩm và chuẩn hóa giá trị
			DefaultTableModel model = (DefaultTableModel) chosenProductTable.getModel();
			String giaSP = (String) productTable.getModel().getValueAt(selectedRow, 5);		//Lấy giá của sản phẩm vừa chọn ở dòng được chọn, cột thứ 5, ta bắt đầu đếm cột từ 0 (là cột giá)
			giaSP = giaSP.replaceAll("\\,", "");	//loại bỏ các ký tự dư thừa
			giaSP = giaSP.replaceFirst("đ", "");
			giaSP = giaSP.substring(0, giaSP.length());
			
			// 4. Kiểm tra xem sản phẩm đã tồn tại trong chosenProductTable chưa
			Vector<String> data = new Vector<String>();
			String maSP = (String) productTable.getModel().getValueAt(selectedRow, 0);
			boolean isDuplicate = false;
			for(int i=0; i<model.getRowCount(); i++) {
				String ma = (String) model.getValueAt(i, 0);
				if(ma.equals(maSP)) { // Nếu sản phẩm đã tồn tại
					isDuplicate = true;
					// 5. Nếu sản phẩm đã có trong bảng nhập (chosenProductTable), cập nhật số lượng và giá tiền
					String oldValue = (String) model.getValueAt(selectedRow, 6);
					oldValue = oldValue.replaceAll("\\,", "");
					oldValue = oldValue.replaceFirst("đ", "");
					int newQuantity = Integer.parseInt(txtSoLuong.getText()) + Integer.parseInt((String) model.getValueAt(i, 5));
					String soluong = Integer.toString(newQuantity);
					Double value = newQuantity * Double.parseDouble(giaSP);
					String dongia = NumberFormat.getInstance().format(value) + "đ";
					
					// Cập nhật tổng tiền của đơn hàng
					String money = lblTongTien.getText();
					money = money.substring(0, money.length() - 1);
					money = money.replaceAll("\\,", "");
					lblTongTien.setText(NumberFormat.getInstance()
					    .format(Integer.parseInt(money) - Integer.parseInt(oldValue) + value) + "đ");
					txtSoLuong.setText("");
//					importCodeTextArea.setText(ma);
					break;
				}
			}
			if (!isDuplicate) {
				Double value = Double.parseDouble(txtSoLuong.getText()) * Double.parseDouble(giaSP);
				String tensp = (String) productTable.getModel().getValueAt(selectedRow, 1);
				String soluong = txtSoLuong.getText();
				String dongia = NumberFormat.getInstance().format(value) + "đ";
				String mauSac = (String) productTable.getModel().getValueAt(selectedRow, 2);
				String RAM = (String) productTable.getModel().getValueAt(selectedRow, 3);
				String ROM = (String) productTable.getModel().getValueAt(selectedRow, 4);
				data.add(maSP);
				data.add(tensp);
				data.add(maSP);
				data.add(RAM);
				data.add(ROM);
				data.add(dongia);

				model.addRow(new Object[] { maSP, tensp, mauSac, RAM, ROM, soluong, dongia });
				txtSoLuong.setText("");
				chosenProductTable.repaint();
				String money = lblTongTien.getText();

				money = money.substring(0, money.length() - 1);
				money = money.replaceAll("\\,", "");
				lblTongTien.setText(NumberFormat.getInstance().format(Integer.parseInt(money) + value) + "đ");

			}

		}else if (Integer.parseInt(txtSoLuong.getText()) <= 0) {
			JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
		} else {
			JOptionPane.showMessageDialog(null, "Xin hãy chọn 1 sản phẩm!");
		}
	}
	
	private void removeFromChosenTable(JTable table) {
		int selectedRow = table.getSelectedRow();
		if(selectedRow != -1) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			// Trừ đi số tiền của sản phẩm mà ta muốn loại bỏ
			String dongia = (String) model.getValueAt(selectedRow, 6);
			dongia = dongia.replaceAll("\\,", "");
			dongia = dongia.replaceFirst("đ", "");
			String oldMoney = lblTongTien.getText();
			oldMoney = oldMoney.replaceAll("\\,", "");
			oldMoney = oldMoney.replaceFirst("đ", "");
			int val = Integer.parseInt(oldMoney);
			val = val - Integer.parseInt(dongia);
			lblTongTien.setText(NumberFormat.getInstance().format(val) + "đ");
			model.removeRow(selectedRow);
			table.repaint();
		}else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần xóa!");
		}
	}
	
	private void changeQuantity(JTable table) {
		int selectedRow = table.getSelectedRow();
		if(selectedRow != -1) {
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			String maPBSP = (String) model.getValueAt(selectedRow, 0);
			String soLuongMoi = JOptionPane.showInputDialog("Vui lòng nhập số lượng mới cho " + maPBSP);
			if(soLuongMoi.equals("") || soLuongMoi.matches("%[a-zA-Z]%") || soLuongMoi.equals("0")) {
				JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ");
			}else {
				String mapbsp = (String) model.getValueAt(selectedRow, 0);
				String tensp = (String) model.getValueAt(selectedRow, 1);
				String dongia = (String) model.getValueAt(selectedRow, 6);
				model.setValueAt(table, selectedRow, selectedRow);
				dongia = dongia.replaceAll("\\,", "");
				dongia = dongia.replaceFirst("đ", "");
				int soluong = Integer.parseInt((String) model.getValueAt(selectedRow, 5));	//cột số 5 là cột số lượng, đếm từ 0
				int val = Integer.parseInt(dongia) / soluong;
				int soluongmoi = Integer.parseInt(soLuongMoi);
				model.setValueAt(mapbsp, selectedRow, 0);
				model.setValueAt(tensp, selectedRow, 1);
				model.setValueAt(Integer.toString(soluongmoi), selectedRow, 5);
				model.setValueAt(NumberFormat.getInstance().format(val * soluongmoi)+"đ", selectedRow, 6);

				String oldMoney = lblTongTien.getText();
				oldMoney = oldMoney.replaceAll("\\,", "");
				oldMoney = oldMoney.replaceFirst("đ", "");
				int oldMoneyInt = Integer.parseInt(oldMoney);
				int newMoney = oldMoneyInt - Integer.parseInt(dongia) + (val * soluongmoi);
				lblTongTien.setText(NumberFormat.getInstance().format(newMoney) + "đ"); 
			}
		}else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần sửa!");
		}
	}
	
	private void exportProduct(JTable table) {
		//Thêm phiếu xuất. Nhưng phiếu xuất lúc này đang ở trạng thái là "Chờ xác nhận", khi nào quản lý kho chuyển trạng thái thành "Đã nhận hàng" thì mới tăng số lượng pbsp lên.
		//Nên bước tăng số lượng đó thì ta sẽ xử lý ở bên giao diện danh sách các phiếu xuất vì ở đó mới có chức năng thay đổi trạng thái px
		PhieuXuatDTO px = new PhieuXuatDTO();
		px.setMaPX(txtMapx.getText().trim());
		//Format ngày tạo trước khi insert vào csdl vì date ở csdl chỉ chấp nhận dạng yyyy-mm-dd trong khi input từ txtNgayTao là dạng dd/mm/yyyy
		String ngayTaoStr = txtNgayTao.getText().trim();
		java.sql.Date sqlDate = DateConverter.convertToSQLDate(ngayTaoStr);
		if (sqlDate == null) {
		    JOptionPane.showMessageDialog(null, "Ngày nhập không hợp lệ! Vui lòng nhập theo định dạng DD/MM/YYYY.");
		} else {
		    px.setNgayTao(sqlDate);
		}
		//Format lại giá trị tổng tiền cho chuẩn vì lấy từ giao diện đang ở dạng có dấu phẩy và ký tự "đ". VD: 120,000,000d
		String tongTienStr = lblTongTien.getText().trim();
		tongTienStr = tongTienStr.replaceAll("[^0-9.]", ""); // Chỉ giữ lại số và dấu chấm
		double tongTien = Double.parseDouble(tongTienStr);
		px.setTongTien(tongTien);
		//maNV, maKho, maNCC chờ Minh code xong phần dăng nhập, Khách hàng sẽ hoàn thiện
		px.setMaNV(txtMaNguoiTao.getText().trim());
		px.setMaKho(txtMaKho.getText().trim());
//		px.setMaNCC(supplierComboBox.getSelectedItem().toString());	
		px.setMaKH("KH001");	
		px.setTrangThai("Chờ xác nhận");
		String messagepx = pxBUS.insert(px);
		if(messagepx.equalsIgnoreCase("Thêm phiếu xuất thành công!")) {
			
			for(int i=0; i<table.getRowCount(); i++) {
				ChiTietPhieuXuatDTO ctpx = new ChiTietPhieuXuatDTO();

				ctpx.setSoLuong(Integer.parseInt(table.getValueAt(i, 5).toString()));
				String giaString = table.getValueAt(i, 6).toString();
				//Loại bỏ các ký tự không phải số
				giaString = giaString.replaceAll("[^0-9]", "");
				ctpx.setGiaXuat(Double.parseDouble(giaString));
				ctpx.setMaPX(txtMapx.getText());
				ctpx.setMaPBSP(table.getValueAt(i, 0).toString());
				
				int ketQuaThemCTpx = ctpxBUS.insert(ctpx);
				if(ketQuaThemCTpx>0) {
					JOptionPane.showMessageDialog(null, messagepx);
				}
			}
		}else if(messagepx.equalsIgnoreCase("Thêm phiếu xuất thất bại!")){
			JOptionPane.showMessageDialog(null, messagepx);
		}
			
	}
	
	
	private void searchPerformed(JTable tb){
        String searchContent = txtTimKiem.getText().trim(); // Lấy nội dung tìm kiếm từ textField và loại bỏ khoảng trắng ở đầu và cuối chuỗi
        if (!searchContent.isEmpty()) { // Kiểm tra xem nội dung tìm kiếm có rỗng không
            ArrayList<PhienBanSanPhamDTO> dsTimKiem = new ArrayList<>(); // Tạo một danh sách để lưu trữ kết quả tìm kiếm

            // Duyệt qua danh sách sản phẩm và lọc những sản phẩm thỏa mãn điều kiện tìm kiếm
            boolean found = false;
            for (PhienBanSanPhamDTO pbsp: arrPBSP) {
                // Kiểm tra xem thông tin của pbsp có chứa chuỗi tìm kiếm hay không (sử dụng phương thức contains)
                if (pbsp.getMaPBSP().toLowerCase().contains(searchContent.toLowerCase().trim())||
                	pbsp.getMauSac().toLowerCase().contains(searchContent.toLowerCase().trim())||
                	pbsp.getRam().toLowerCase().contains(searchContent.toLowerCase().trim())||
                	pbsp.getRom().toLowerCase().contains(searchContent.toLowerCase().trim()))
                 {
                    dsTimKiem.add(pbsp); // Nếu sản phẩm thỏa mãn, thêm vào danh sách lọc
                    found = true;
                }
                
            }
            // Kiểm tra nếu không tìm thấy sản phẩm nào
            if(!found){
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiên bản sản phẩm");
                refreshList();
                return; // Kết thúc phương thức sau khi hiển thị thông báo
            }
            
            // Xóa tất cả các dòng hiện có trong bảng
            DefaultTableModel tableModel = (DefaultTableModel) tb.getModel();
            tableModel.setRowCount(0);

            // Thêm các sản phẩm thỏa mãn vào bảng
            for (PhienBanSanPhamDTO pbsp : dsTimKiem) {
    			String maPBSP = pbsp.getMaPBSP();
    			String tenSP = spBUS.getTenSanPhamByMaPBSP(maPBSP);
    			String mauSac = pbsp.getMauSac();
    			String ram = pbsp.getRam();
    			String rom = pbsp.getRom();
    			int soLuong = pbsp.getSoLuong();
    		    String gia = String.valueOf((Double) pbsp.getGiaBan());
    			String trangThai = pbsp.getTrangThai();
    			
    		    Object[] row = {maPBSP, tenSP, mauSac, ram, rom, gia, soLuong, trangThai};
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
        productModel.setRowCount(0);
        loadProductList();
        txtTimKiem.setText("");
    }
	
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}

	
}
