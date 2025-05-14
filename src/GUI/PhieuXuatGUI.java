package GUI;

import BUS.ChiTietPhieuXuatBUS;
import BUS.DangNhapBUS;
import BUS.PhienBanSanPhamBUS;
import BUS.PhieuXuatBUS;
import BUS.SanPhamBUS;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Date;
import java.text.DecimalFormat;
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
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import javax.swing.filechooser.FileNameExtensionFilter;

public class PhieuXuatGUI extends JPanel {

	PhieuXuatBUS pxBUS = new PhieuXuatBUS();
	SanPhamBUS spBUS = new SanPhamBUS();
	ChiTietPhieuXuatBUS ctpxBUS = new ChiTietPhieuXuatBUS();
	PhienBanSanPhamBUS pbspBUS = new PhienBanSanPhamBUS();
	DangNhapBUS dnBUS = new DangNhapBUS();
	JTable pxTable, ctpxTable;
	DefaultTableModel pxModel, ctpxModel;
	ArrayList<PhieuXuatDTO> arrPhieuXuat = new ArrayList<PhieuXuatDTO>(); // Tạo ArrayList sp với kiểu là ProductsDTO
	ArrayList<ChiTietPhieuXuatDTO> arrCTPX = new ArrayList<ChiTietPhieuXuatDTO>();
	ArrayList<PhienBanSanPhamDTO> arrPBSP = new ArrayList<PhienBanSanPhamDTO>();
	JComboBox<String> cbbTrangThai;
	boolean comboboxClicked = false;
	JPanel ctpxContent;
	JLabel imageLabel;
	JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
	JLabel maPXValue, maKhoValue, maNguoiTaoValue, khachHangValue;

	// Constructor
	public PhieuXuatGUI() {
		this.setLayout(new GridLayout(1, 2, 10, 10));
		initComponents();
		loadDanhSachPN();
		loadctpx();
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
		ctpxContent = new JPanel();
//		ctpxContent.setBackground(Color.green);
		ctpxContent.setBackground(Color.white);
		ctpxContent.setLayout(new GridLayout(1, 2, 15, 15));

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(ctpxContent, gbc); // Thêm vào ProductsGUI

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
		ctpxContent.add(leftPanel, gbc);

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
//		rightPanel.setBackground(Color.decode("#372083"));
		rightPanel.setBackground(Color.white);
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		ctpxContent.add(rightPanel, gbc);

//========================================================== LEFT PANEL =============================================================================================//
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
		gbc.weighty = 0.62;
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
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		leftPanel.add(quantityLeftPanel, gbc);

		// ===================================== searchLeftPanel ======================================//
		// DateChooser
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(10, 22, 150, 25); // Định vị
		searchLeftPanel.add(dateChooser);

		JTextField searchTF = new JTextField();
		searchTF.setBounds(300, 22,235, 25);
		searchLeftPanel.add(searchTF);

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
		btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSearch.setBackground(Color.white);
		btnSearch.setFont(new Font("Arial", Font.BOLD, 9)); // Đặt kích cỡ chữ là 10

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Search button clicked!");
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
		pxTable = new JTable();
		JScrollPane sp = new JScrollPane(pxTable);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		productListLeftPanel.add(sp, gbc);
		pxTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() >= 1) {	//nếu nhấn vào dòng đó từ 1 lần trở lên tức là ta muốn xem chi tiết của PN đó
					viewDetail();
				}
			}
		});
		
		
		
		
		
		
		
		
		
		//================ RIGHT PANEL =========================//
		// Chia 3 panel con informationRightPanel ở phần trên, productChoseRightPanel ở
		// phần giữa, optionRightPanel ở phần dưới;
		JPanel productChoseRightPanel, optionRightPanel;



		productChoseRightPanel = new JPanel();
		productChoseRightPanel.setLayout(new GridBagLayout());
//		productChoseRightPanel.setBackground(Color.decode("#3AA7A3"));
		productChoseRightPanel.setBackground(Color.white);
		productChoseRightPanel.setBorder(BorderFactory.createTitledBorder(""));
		gbc.weightx = 1.0;
		gbc.weighty = 0.9;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
//		gbc.insets = new Insets(5, 5, 5, 5);
		rightPanel.add(productChoseRightPanel, gbc);

		optionRightPanel = new JPanel();
		optionRightPanel.setLayout(null);
//		optionRightPanel.setBackground(Color.decode("#785589"));
		optionRightPanel.setBackground(Color.white);
		gbc.weightx = 1.0;
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		rightPanel.add(optionRightPanel, gbc);

		
		
		///////////////////////////////////////// productChoseRightPanel ///////////////////////////////////////// 
		//Thêm bảng vào panel để hiển thị các sản phẩm đã được chọn để nhập
		ctpxTable = new JTable();
		JScrollPane sp2 = new JScrollPane(ctpxTable);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.37;
		gbc.fill = GridBagConstraints.BOTH;
		productChoseRightPanel.add(sp2, gbc);
		
		JPanel informationPanel = new JPanel(null);
		informationPanel.setBorder(BorderFactory.createTitledBorder(""));
		informationPanel.setBackground(Color.white);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.63;
		gbc.fill = GridBagConstraints.BOTH;
		productChoseRightPanel.add(informationPanel, gbc);
		
		JLabel lblmaPX, lblMaKho, lblMaNguoiTao, lblkhachHang, lblTrangThai;
		lblmaPX = new JLabel("Mã phiếu nhập:");
		lblmaPX.setBounds(10, 10, 100, 20);
		informationPanel.add(lblmaPX);

		lblMaKho = new JLabel("Mã kho:");
		lblMaKho.setBounds(10, 40, 100, 20);
		informationPanel.add(lblMaKho);

		lblMaNguoiTao = new JLabel("Mã người tạo:");
		lblMaNguoiTao.setBounds(10, 70, 100, 20);
		informationPanel.add(lblMaNguoiTao);

		lblkhachHang = new JLabel("Nhà cung cấp:");
		lblkhachHang.setBounds(10, 100, 100, 20);
		informationPanel.add(lblkhachHang); 
		
		lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setBounds(10, 130, 100, 20);
		if (dnBUS.getChucVu().equals("Admin") || dnBUS.getChucVu().equals("Quản lý kho")) {
			informationPanel.add(lblTrangThai); 
		}


		
		
		maPXValue = new JLabel("abc");
		maPXValue.setBounds(100, 10, 50, 20);
		informationPanel.add(maPXValue);
		
		maKhoValue = new JLabel("xyz");
		maKhoValue.setBounds(100, 40, 50, 20);
		informationPanel.add(maKhoValue);
		
		maNguoiTaoValue = new JLabel("mnp");
		maNguoiTaoValue.setBounds(100, 70, 50, 20);
		informationPanel.add(maNguoiTaoValue);

		khachHangValue = new JLabel("hij");
		khachHangValue.setBounds(100, 100, 50, 20);
		informationPanel.add(khachHangValue);
		
		
		if (dnBUS.getChucVu().equals("Admin") || dnBUS.getChucVu().equals("Quản lý kho")) {

			String[] trangThai = { "Chờ xác nhận", "Đã xác nhận", "Đã xuất hàng", "Từ chối" };
			cbbTrangThai = new JComboBox<String>(trangThai);
			cbbTrangThai.setBounds(100, 128, 115, 25);
			informationPanel.add(cbbTrangThai);

			// Gắn MouseListener để biết khi người dùng click vào combobox
			cbbTrangThai.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					comboboxClicked = true;
				}
			});

			// Gắn ActionListener để thực hiện hành động sau khi chọn
			cbbTrangThai.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (comboboxClicked) {
						String maPX = maPXValue.getText();
						String trangThai = cbbTrangThai.getSelectedItem().toString();
						updateTrangThai(maPX, trangThai);
						comboboxClicked = false; // Reset cờ để tránh lặp

						if (trangThai.equalsIgnoreCase("Đã xuất hàng"))
							updateSoLuongPBSP(pxTable, ctpxTable);
					}
				}
			});
		}
		
		
		JButton btnExcel = new ShadowButton("Xuất Excel");
		btnExcel.setBounds(510, 130, 100, 20);
		informationPanel.add(btnExcel);

	}

	private void loadDanhSachPN() {
		pxTable.setDefaultEditor(Object.class, null);
		
		pxModel = new DefaultTableModel();
		pxTable.setModel(pxModel);
		pxModel.addColumn("Mã PN");
		pxModel.addColumn("Ngày tạo");
		pxModel.addColumn("Tổng tiền");
		pxModel.addColumn("Trạng thái");
		pxModel.addColumn("Người tạo");
		pxModel.addColumn("Kho");
		pxModel.addColumn("Nhà cung cấp");

		arrPhieuXuat = pxBUS.selectAll();
		for(int i=0; i<arrPhieuXuat.size(); i++) {
			PhieuXuatDTO pn = arrPhieuXuat.get(i);
			String maPX = pn.getMaPX();
			Date ngayTao = pn.getNgayTao();
			Double tongTien = pn.getTongTien();
			String trangThai = pn.getTrangThai();
			String maNV = pn.getMaNV();
			String maKho = pn.getMaKho();
			String maNCC = pn.getMaKH();
			
			//Nếu k format thì khi hiển thị lên giao diện sẽ bị dạng E (exponential). VD: 8.0E7 thay vì 80000000.
			DecimalFormat df = new DecimalFormat("#,###.##"); // Định dạng có dấu phân tách hàng nghìn
			String tongTienFormatted = df.format(tongTien) + "đ";
			
			Object[] row = {maPX, ngayTao, tongTienFormatted, trangThai, maNV, maKho, maNCC};
			pxModel.addRow(row);
		}
		
		
		//Điều chỉnh kích thước các cột
		TableColumnModel tcm = pxTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(70);
		tcm.getColumn(1).setPreferredWidth(80);
		tcm.getColumn(2).setPreferredWidth(110);
		tcm.getColumn(3).setPreferredWidth(104);
		tcm.getColumn(4).setPreferredWidth(80);
		tcm.getColumn(5).setPreferredWidth(70);
		tcm.getColumn(6).setPreferredWidth(100);


		
		
		pxTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize

	}
	
	private void loadctpx() {
		ctpxModel  = new DefaultTableModel();
		ctpxTable.setModel(ctpxModel);
		ctpxModel.addColumn("Mã PBSP");
		ctpxModel.addColumn("Tên sản phẩm");
		ctpxModel.addColumn("Màu sắc");
		ctpxModel.addColumn("RAM");
		ctpxModel.addColumn("ROM");
		ctpxModel.addColumn("Số lượng");
		ctpxModel.addColumn("Giá");

		//Điều chỉnh kích thước các cột
		TableColumnModel tcm = ctpxTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(60);
		tcm.getColumn(1).setPreferredWidth(190);
		tcm.getColumn(2).setPreferredWidth(80);
		tcm.getColumn(3).setPreferredWidth(50);
		tcm.getColumn(4).setPreferredWidth(50);
		tcm.getColumn(5).setPreferredWidth(80);
		tcm.getColumn(6).setPreferredWidth(104);
		
		ctpxTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize
		
	}
	
	private void viewDetail() {
		int selectedRow = pxTable.getSelectedRow();
		if(selectedRow != -1) {
			DefaultTableModel pnModel = (DefaultTableModel) pxTable.getModel();
			
			DefaultTableModel ctpxModel = (DefaultTableModel) ctpxTable.getModel();
			String maPX = (String) pnModel.getValueAt(selectedRow, 0);
			log("maPX=" + maPX);
			ArrayList<ChiTietPhieuXuatDTO> thongTinctpx = ctpxBUS.getThongTinCTPX(maPX);	//để lấy số lượng & giá nhập của các ctpx, mã pbsp để lấy màu săc, ram, rom
			ArrayList<PhieuXuatDTO> thongTinPhieuXuat = pxBUS.getThongTinPhieuXuat(maPX);	//để lấy mã pn, mã người tạo, mã kho, mã ncc
			String trangThai = pxBUS.getTrangThai(maPX);
	        log("trangThai=" + trangThai);
	        
			ctpxModel.setRowCount(0);
			for(int i = 0; i < thongTinctpx.size(); i++) {
			    int soLuong = thongTinctpx.get(i).getSoLuong();
			    double giaNhap = thongTinctpx.get(i).getGiaXuat();
			    String maPBSP = thongTinctpx.get(i).getMaPBSP();

			    ArrayList<PhienBanSanPhamDTO> thongTinPBSP = pbspBUS.getThongTinPBSP(maPBSP);
			    if (thongTinPBSP.size() > 0) {  // Kiểm tra xem danh sách có phần tử không
			        String mauSac = thongTinPBSP.get(0).getMauSac();
			        String ram = thongTinPBSP.get(0).getRam();
			        String rom = thongTinPBSP.get(0).getRom();

			        ArrayList<SanPhamDTO> tenSanPham = spBUS.getTenSanPhamByMaPBSP2(maPBSP);
			        String tenSP = tenSanPham.size() > 0 ? tenSanPham.get(0).getTenSP() : "N/A";

			        DecimalFormat df = new DecimalFormat("#,###.##");
			        String giaNhapFormatted = df.format(giaNhap) + "đ";
			        			       
			        
			        Object[] row = { maPBSP, tenSP, mauSac, ram, rom, soLuong, giaNhapFormatted};
			        ctpxModel.addRow(row);
				}
			}

			// Đặt thông tin phiếu nhập (chỉ lấy phần tử đầu tiên nếu có)
			if (dnBUS.getChucVu().equals("Admin") || dnBUS.getChucVu().equals("Admin")) {
				if (!thongTinPhieuXuat.isEmpty()) {
					maPXValue.setText(thongTinPhieuXuat.get(0).getMaPX());
					maKhoValue.setText(thongTinPhieuXuat.get(0).getMaKho());
					maNguoiTaoValue.setText(thongTinPhieuXuat.get(0).getMaNV());
					khachHangValue.setText(thongTinPhieuXuat.get(0).getMaKH());
					if (trangThai.equalsIgnoreCase("Chờ xác nhận")) {
						cbbTrangThai.setSelectedIndex(0);
						cbbTrangThai.setEnabled(true);
					} else if (trangThai.equalsIgnoreCase("Đã xác nhận")) {
						cbbTrangThai.setSelectedIndex(1);
						cbbTrangThai.setEnabled(true);
					} else if (trangThai.equalsIgnoreCase("Đã xuất hàng")) {
						cbbTrangThai.setSelectedIndex(2);
						cbbTrangThai.setEnabled(false);
					} else if (trangThai.equalsIgnoreCase("Từ chối")) {
						cbbTrangThai.setSelectedIndex(3);
						cbbTrangThai.setEnabled(false);
					}

				}
			}
			ctpxTable.repaint();
			
			
			
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 phiếu nhập để xem!");
		}
	}
	
	private void refreshList(){
        // Xóa tất cả các dòng trong mô hình bảng
        ctpxModel.setRowCount(0);
        loadDanhSachPN();
    }
	
	//hàm hiển thị thông tin dòng code
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}
	
	
	private void updateTrangThai(String maPX, String trangThai) {
		int selectedIndex = cbbTrangThai.getSelectedIndex();
		if(selectedIndex != -1) {	//combobox được thao tác chọn
			String message = pxBUS.updateTrangThai(maPX, trangThai);
			
			if(message.equalsIgnoreCase("Cập nhật trạng thái thành công!"))
				JOptionPane.showMessageDialog(null, message);
			else if(message.equalsIgnoreCase("Cập nhật trạng thái thất bại!"))
				JOptionPane.showMessageDialog(null, message);
		}	
	}
	
	private void updateSoLuongPBSP(JTable tablePX, JTable tableCTPX) {
		DefaultTableModel modelPX = (DefaultTableModel)tablePX.getModel();
		int selecedRow = tablePX.getSelectedRow();
		if(selecedRow != -1) {
			String maKho = modelPX.getValueAt(selecedRow, 5).toString();
			for(int i=0; i<tableCTPX.getRowCount(); i++) {
				DefaultTableModel modelCTPX = (DefaultTableModel)tableCTPX.getModel();
				String maPBSP = modelCTPX.getValueAt(i, 0).toString();
				int soLuong = Integer.parseInt(modelCTPX.getValueAt(i, 5).toString());
				String message = pbspBUS.giamSoLuong(maKho, maPBSP,soLuong);
				if(message.equalsIgnoreCase("Cập nhật số lượng PBSP thành công!"))
					JOptionPane.showMessageDialog(null, message);
				else if(message.equals("Cập nhật số lượng PBSP thất bại!"))
					JOptionPane.showMessageDialog(null, message);
			}
		}
	}
}
