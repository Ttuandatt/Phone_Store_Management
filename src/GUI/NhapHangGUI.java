package GUI;

import BUS.ChiTietPhieuNhapBUS;
import BUS.DangNhapBUS;
import BUS.NhaCungCapBUS;
import BUS.PhienBanSanPhamBUS;
import BUS.PhieuNhapBUS;
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
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser; // Thêm thư viện JCalendar
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NhapHangGUI extends JPanel {

	PhienBanSanPhamBUS pbspBUS = new PhienBanSanPhamBUS();
	SanPhamBUS spBUS = new SanPhamBUS();
	PhieuNhapBUS pnBUS = new PhieuNhapBUS();
	ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();
	NhaCungCapBUS nccBUS = new NhaCungCapBUS();
	JTable productTable, chosenProductTable;
	DefaultTableModel productModel, chosenProductModel;
	ArrayList<PhienBanSanPhamDTO> arrPBSP = new ArrayList<PhienBanSanPhamDTO>(); // Tạo ArrayList sp với kiểu là ProductsDTO
	JComboBox<String> brandComboBox, nhaCungCapComboBox;
	JPanel pnContent;
	JLabel imageLabel, lblTongTien, lblMaPN, lblMaKho, lblMaNguoiTao, lblNhaCungCap, lblNgayTao;
	JTextField txtTimKiem, txtMaPN, txtMaKho, txtMaNguoiTao, txtNgayTao;
	// Lấy ngày hiện tại
	LocalDate currentDate = LocalDate.now();
	// Định dạng ngày thành dd/MM/yyyy
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	DangNhapBUS dnBUS = new DangNhapBUS();
	String maNV = dnBUS.getMaNV();

	// Constructor
	public NhapHangGUI() {
		this.setLayout(new GridLayout(1, 2, 10, 10));
		initComponents();
		loadProductList();
		loadChosenProduct();
	
		txtMaNguoiTao.setText(maNV);
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
		pnContent = new JPanel();
//		pnContent.setBackground(Color.green);
		pnContent.setBackground(Color.white);
		pnContent.setLayout(new GridLayout(1, 2, 15, 15));

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(pnContent, gbc); // Thêm vào ProductsGUI

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
		pnContent.add(leftPanel, gbc);

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
//		rightPanel.setBackground(Color.decode("#372083"));
		rightPanel.setBackground(Color.white);
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		pnContent.add(rightPanel, gbc);

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
				addToImportTable(productTable, chosenProductTable, txtSoLuong);
				productTable.clearSelection(); //sau khi chọn xong thì làm mới lại bảng bên trai để k có dòng nào được chọn
			}

		});
		quantityLeftPanel.add(quantityButton);

		JButton newProductButton = new ShadowButton("Thêm sản phẩm");
		newProductButton.setBounds(250, 20, 120, 25);
//        newProductButton.setOpaque(true);
		newProductButton.setBackground(Color.white);
		newProductButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				newProductButton.setBackground(Color.decode("#D2E4EE")); // Màu khi hover vào
				newProductButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				newProductButton.setBackground(Color.white); // Màu khi hover ra
			}

		});
		quantityLeftPanel.add(newProductButton);

		//Khu vực thêm các Listener
		newProductButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newProductDialog();
			}
		});


		
		
		
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
		
		lblMaPN = new JLabel("Mã phiếu nhập:");
		lblMaPN.setBounds(10, 10, 100, 20);
		informationRightPanel.add(lblMaPN);

		lblMaKho = new JLabel("Mã kho:");
		lblMaKho.setBounds(10, 40, 100, 20);
		informationRightPanel.add(lblMaKho);

		lblMaNguoiTao = new JLabel("Mã người tạo:");
		lblMaNguoiTao.setBounds(10, 70, 100, 20);
		informationRightPanel.add(lblMaNguoiTao);

		lblNhaCungCap = new JLabel("Nhà cung cấp:");
		lblNhaCungCap.setBounds(10, 100, 100, 20);
		informationRightPanel.add(lblNhaCungCap);

		// Add combobox suppliers
		String[] suppliers = { "", "Thêm nhà cung cấp..." };
		nhaCungCapComboBox = new JComboBox<String>(suppliers);
		nhaCungCapComboBox.setBounds(110, 100, 200, 20);
		informationRightPanel.add(nhaCungCapComboBox);
		nhaCungCapComboBox.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				String selected = (String) nhaCungCapComboBox.getSelectedItem();
				if ("Thêm nhà cung cấp...".equals(selected)) {
					newSupplierDialog();
				}
			}
		});
		fillNhaCungCapCombobox(nhaCungCapComboBox);
		
		lblNgayTao = new JLabel("Ngày tạo:");
		lblNgayTao.setBounds(340, 10, 100, 20);
		informationRightPanel.add(lblNgayTao);
		

		
		txtMaPN = new JTextField();
		txtMaPN.setBounds(110, 10, 100, 20);
		informationRightPanel.add(txtMaPN);

		txtMaKho = new JTextField();
//		txtMaKho.setEditable(false);
//		txtMaKho.setEnabled(false);
		txtMaKho.setBounds(110, 40, 100, 20);
		informationRightPanel.add(txtMaKho);

		txtMaNguoiTao = new JTextField();
//		txtMaNguoiTao.setEditable(false);
		txtMaNguoiTao.setEnabled(false);
		txtMaNguoiTao.setBounds(110, 70, 100, 20);
		informationRightPanel.add(txtMaNguoiTao);
		
		txtNgayTao = new JTextField();
		txtNgayTao.setEditable(false);
		txtNgayTao.setEnabled(false);
		txtNgayTao.setBounds(400, 10, 100, 20);
		informationRightPanel.add(txtNgayTao);
		String ngayTao = String.valueOf(new java.sql.Date(System.currentTimeMillis()));
        txtNgayTao.setText(ngayTao);
		
		
		
		
		///////////////////////////////////////// productChoseRightPanel ///////////////////////////////////////// 
		//Thêm bảng vào panel để hiển thị các sản phẩm đã được chọn để nhập
		chosenProductTable = new JTable();
		JScrollPane sp2 = new JScrollPane(chosenProductTable);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		productChoseRightPanel.add(sp2, gbc);
		
		
		
		///////////////////////////////////////// optionRightPanel /////////////////////////////////////////////
		JButton btnFixQuantity, btnRemoveProduct, btnImport;
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

		btnImport = new ShadowButton("Nhập hàng");
		btnImport.setBounds(520, 20, 100, 25);
		optionRightPanel.add(btnImport);
		btnImport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnImport.setBackground(Color.decode("#D2E4EE"));
				btnImport.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnImport.setBackground(Color.white);
			}
		});
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importProduct(chosenProductTable);
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

	// Hàm hiển thị JDialog để nhập sản phẩm mới
	private void newProductDialog() {
		JDialog newProductDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm sản phẩm", true);
		newProductDialog.setSize(600, 450);
		newProductDialog.setLayout(null);

		JLabel lblId = new JLabel("Mã sản phẩm:");
		lblId.setBounds(10, 20, 100, 20);
		newProductDialog.add(lblId);
		JTextField txtId = new JTextField();
//		txtId.setEditable(false); // sẽ lấy id mới nhất của bảng sản phẩm trong csdl ra để tạo mã, k cho nhập tự động
		txtId.setBounds(110, 20, 150, 20);
		newProductDialog.add(txtId);

		JLabel lblImage = new JLabel("Hình ảnh:");
		lblImage.setBounds(280, 20, 50, 20);
		newProductDialog.add(lblImage);
		JLabel productImg = new JLabel();
		productImg.setBounds(280, 0, 350, 350);
		newProductDialog.add(productImg);
		JButton browseButton = new ShadowButton("Chọn");
		browseButton.setBounds(340, 20, 90, 20);
		browseButton.setBorderPainted(false);
		browseButton.setBackground(Color.decode("#01BFF4"));
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
					Image img = icon.getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH);
					productImg.setIcon(new ImageIcon(img));
				}
			}
		});
		imageLabel = new JLabel();
		imageLabel.setPreferredSize(new DimensionUIResource(200, 200));
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		imageLabel.setBounds(20, 50, 200, 200);
		newProductDialog.add(browseButton);

		JLabel lblName = new JLabel("Tên sản phẩm:");
		lblName.setBounds(10, 45, 100, 20);
		newProductDialog.add(lblName);
		JTextField txtName = new JTextField();
		txtName.setBounds(110, 45, 150, 20);
		newProductDialog.add(txtName);

		JLabel lblBrand = new JLabel("Thương hiệu:");
		lblBrand.setBounds(10, 70, 100, 20);
		newProductDialog.add(lblBrand);
		String[] brands = { "Samsung", "Apple", "Xiaomi", "Thêm thương hiệu" }; // "Samsung", "Apple", "Xiaomi" là các
																				// brand thêm vào để khởi đầu thôi, còn
																				// khi kết nối csdl rồi thì khi thêm
																				// brand mới sẽ thêm vào csdl
		brandComboBox = new JComboBox<String>(brands);
		brandComboBox.setBounds(110, 70, 150, 20);
		newProductDialog.add(brandComboBox);
		brandComboBox.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				String selected = (String) brandComboBox.getSelectedItem();
				if ("Thêm thương hiệu".equals(selected)) {
					newBrandDialog();
				}
			}
		});

		JLabel lblBattery = new JLabel("Pin:");
		lblBattery.setBounds(10, 95, 100, 20);
		newProductDialog.add(lblBattery);
		JTextField txtBattery = new JTextField();
		txtBattery.setBounds(110, 95, 150, 20);
		newProductDialog.add(txtBattery);

		JLabel lblOS = new JLabel("HDH:");
		lblOS.setBounds(10, 120, 100, 20);
		newProductDialog.add(lblOS);
		JTextField txtOS = new JTextField();
		txtOS.setBounds(110, 120, 150, 20);
		newProductDialog.add(txtOS);

		JLabel lblOrigin = new JLabel("Xuất xứ:");
		lblOrigin.setBounds(10, 145, 100, 20);
		newProductDialog.add(lblOrigin);
		JTextField txtOrigin = new JTextField();
		txtOrigin.setBounds(110, 145, 150, 20);
		newProductDialog.add(txtOrigin);

		JLabel lblFrontCam = new JLabel("Cam trước:");
		lblFrontCam.setBounds(10, 170, 100, 20);
		newProductDialog.add(lblFrontCam);
		JTextField txtFrontCam = new JTextField();
		txtFrontCam.setBounds(110, 170, 150, 20);
		newProductDialog.add(txtFrontCam);

		JLabel lblBackCam = new JLabel("Cam sau:");
		lblBackCam.setBounds(10, 195, 100, 20);
		newProductDialog.add(lblBackCam);
		JTextField txtBackCam = new JTextField();
		txtBackCam.setBounds(110, 195, 150, 20);
		newProductDialog.add(txtBackCam);

		
		//nhập thông tin phiên bản sản phẩm
		JLabel lblMauSac = new JLabel("Màu sắc:");
		lblMauSac.setBounds(10, 245, 100, 20);
		newProductDialog.add(lblMauSac);
		JTextField txtMauSac = new JTextField();
		txtMauSac.setBounds(110, 245, 150, 20);
		newProductDialog.add(txtMauSac);
		
		JLabel lblRam = new JLabel("RAM:");
		lblRam.setBounds(10, 270, 100, 20);
		newProductDialog.add(lblRam);
		JTextField txtRam = new JTextField();
		txtRam.setBounds(110, 270, 150, 20);
		newProductDialog.add(txtRam);
		
		JLabel lblRom = new JLabel("ROM:");
		lblRom.setBounds(10, 295, 100, 20);
		newProductDialog.add(lblRom);
		JTextField txtRom = new JTextField();
		txtRom.setBounds(110, 295, 150, 20);
		newProductDialog.add(txtRom);
		
		JLabel lblPrice = new JLabel("Giá:");
		lblPrice.setBounds(10, 320, 100, 20);
		newProductDialog.add(lblPrice);
		JTextField txtPrice = new JTextField();
		txtPrice.setBounds(110, 320, 150, 20);
		newProductDialog.add(txtPrice);
		

		
		JLabel lbStatus = new JLabel("Trạng thái:");
		lbStatus.setBounds(10, 345, 100, 20);
		newProductDialog.add(lbStatus);
		JRadioButton rbOn = new JRadioButton("On");
		rbOn.setBounds(110, 345, 50, 20);
		newProductDialog.add(rbOn);
		JRadioButton rbOff = new JRadioButton("Off");
		rbOff.setBounds(160, 345, 70, 20);
		newProductDialog.add(rbOff);

		JButton btnSave = new ShadowButton("Lưu");
		btnSave.setBounds(395, 380, 70, 25);
		btnSave.setBorderPainted(false);
		btnSave.setBackground(Color.decode("#01BFF4"));
		btnSave.addActionListener(e -> {
			String name = txtName.getText();
			String brand = brandComboBox.getSelectedItem().toString();
			String battery = txtBattery.getText();
			String os = txtOS.getText();
			String origin = txtOrigin.getText();
			String frontCam = txtFrontCam.getText();
			String backCam = txtBackCam.getText();
			String price = txtPrice.getText();

			if (name.isEmpty() || brand.isEmpty() || battery.isEmpty() || os.isEmpty() || origin.isEmpty()
					|| frontCam.isEmpty() || backCam.isEmpty() || price.isEmpty()) {
				JOptionPane.showMessageDialog(newProductDialog, "Xin điền đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				// Thêm vào danh sách sản phẩm (có thể gọi ProductsBUS để xử lý)
				JOptionPane.showMessageDialog(newProductDialog, "Thêm sản phẩm thành công!", "Thành công",
						JOptionPane.INFORMATION_MESSAGE);
				newProductDialog.dispose(); // Đóng form sau khi lưu
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
				btnSave.setBackground(Color.decode("#01BFF4"));
			}
		});
		newProductDialog.add(btnSave);

		JButton btnRefresh = new ShadowButton("Làm mới");
		btnRefresh.setBounds(470, 380, 100, 25);
		btnRefresh.setBorderPainted(false);
		btnRefresh.setBackground(Color.decode("#01BFF4"));
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtName.setText("");
				brandComboBox.setSelectedIndex(0);
				txtBattery.setText("");
				txtOS.setText("");
				txtOrigin.setText("");
				txtFrontCam.setText("");
				txtBackCam.setText("");
				txtPrice.setText("");
				productImg.setIcon(null); // xóa ảnh vừa browse
			}
		});
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRefresh.setBackground(Color.decode("#3A96CF"));
				btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnRefresh.setBackground(Color.decode("#01BFF4"));
			}
		});
		newProductDialog.add(btnRefresh);

		newProductDialog.setLocationRelativeTo(this);
		newProductDialog.setVisible(true);
	}

	// Hàm hiển thị JDialog để nhập thương hiệu mới
	public void newBrandDialog() {
		String newBrand = JOptionPane.showInputDialog(this, "Nhập thương hiệu mới:", "Thêm thương hiệu", JOptionPane.PLAIN_MESSAGE);

		if (newBrand != null && !newBrand.trim().isEmpty()) {
			brandComboBox.insertItemAt(newBrand, brandComboBox.getItemCount() - 1); // Thêm vào trước "Add new brand"
			brandComboBox.setSelectedItem(newBrand);
		}
	}

	public void newSupplierDialog() {
		// Tạo panel chứa form nhập
		JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5)); // row:3, column:2, hgap:5, wgap:5

		JLabel nameLabel = new JLabel("Tên nhà cung cấp:");
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
		int result = JOptionPane.showConfirmDialog(this, panel, "Thêm nhà cung cấp", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

		// Nếu nhấn OK
		if (result == JOptionPane.OK_OPTION) {
			String newSupplier = nameField.getText().trim();
			String address = addressField.getText().trim();
			String phone = phoneField.getText().trim();

			if (!newSupplier.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
				nhaCungCapComboBox.insertItemAt(newSupplier, nhaCungCapComboBox.getItemCount() - 1);
				nhaCungCapComboBox.setSelectedItem(newSupplier);
				JOptionPane.showMessageDialog(this,
						"Nhà cung cấp đã được thêm:\nTên: " + newSupplier + "\nĐịa chỉ: " + address + "\nSố điện thoại: " + phone);
			} else {
				JOptionPane.showMessageDialog(this, "Xin điền đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			}
		}
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
	
	private void addToImportTable(JTable productTable, JTable chosenProductTable, JTextField txtSoLuong) {
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
	
	private void importProduct(JTable table) {
		//Thêm phiếu nhập. Nhưng phiếu nhập lúc này đang ở trạng thái là "Chờ xác nhận", khi nào quản lý kho chuyển trạng thái thành "Đã nhận hàng" thì mới tăng số lượng pbsp lên.
		//Nên bước tăng số lượng đó thì ta sẽ xử lý ở bên giao diện danh sách các phiếu nhập vì ở đó mới có chức năng thay đổi trạng thái PN
		PhieuNhapDTO pn = new PhieuNhapDTO();
		pn.setMaPN(txtMaPN.getText().trim());
		//Format ngày tạo trước khi insert vào csdl vì date ở csdl chỉ chấp nhận dạng yyyy-mm-dd trong khi input từ txtNgayTao là dạng dd/mm/yyyy
		String ngayTaoStr = txtNgayTao.getText().trim();
		java.sql.Date sqlDate = DateConverter.convertToSQLDate(ngayTaoStr);
		if (sqlDate == null) {
		    JOptionPane.showMessageDialog(null, "Ngày nhập không hợp lệ! Vui lòng nhập theo định dạng DD/MM/YYYY.");
		} else {
		    pn.setNgayTao(sqlDate);
		}
		//Format lại giá trị tổng tiền cho chuẩn vì lấy từ giao diện đang ở dạng có dấu phẩy và ký tự "đ". VD: 120,000,000d
		String tongTienStr = lblTongTien.getText().trim();
		tongTienStr = tongTienStr.replaceAll("[^0-9.]", ""); // Chỉ giữ lại số và dấu chấm
		double tongTien = Double.parseDouble(tongTienStr);
		pn.setTongTien(tongTien);
		//maNV, maKho, maNCC chờ Minh code xong phần dăng nhập, nhà cung cấp sẽ hoàn thiện
		pn.setMaNV(txtMaNguoiTao.getText().trim());
		pn.setMaKho(txtMaKho.getText().trim());
//		pn.setMaNCC(nhaCungCapComboBox.getSelectedItem().toString());	
		pn.setMaNCC("NCC001");	
		pn.setTrangThai("Chờ xác nhận");
		String messagePN = pnBUS.insert(pn);
		if(messagePN.equalsIgnoreCase("Thêm phiếu nhập thành công!")) {
			
			for(int i=0; i<table.getRowCount(); i++) {
				ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO();

				ctpn.setSoLuong(Integer.parseInt(table.getValueAt(i, 5).toString()));
				String giaString = table.getValueAt(i, 6).toString();
				//Loại bỏ các ký tự không phải số
				giaString = giaString.replaceAll("[^0-9]", "");
				ctpn.setGiaNhap(Double.parseDouble(giaString));
				ctpn.setMaPN(txtMaPN.getText());
				ctpn.setMaPBSP(table.getValueAt(i, 0).toString());
				
				int ketQuaThemCTPN = ctpnBUS.insert(ctpn);
				if(ketQuaThemCTPN>0) {
					JOptionPane.showMessageDialog(null, messagePN);
				}
			}
		}else if(messagePN.equalsIgnoreCase("Thêm phiếu nhập thất bại!")){
			JOptionPane.showMessageDialog(null, messagePN);
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
        nhaCungCapComboBox.setSelectedIndex(0);
        txtTimKiem.setText("");
    }
	
	private void fillNhaCungCapCombobox(JComboBox<String> combobox) {
    	ArrayList<NhaCungCapDTO> arrNCC = nccBUS.selectAll();
    	nhaCungCapComboBox.removeAllItems(); // Xóa dữ liệu cũ (nếu có)
    	for(NhaCungCapDTO ncc: arrNCC) {
    		nhaCungCapComboBox.addItem(ncc.getTenNCC());
    	}
    	nhaCungCapComboBox.addItem("Thêm nhà cung cấp...");
    }
	
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}

	
}
