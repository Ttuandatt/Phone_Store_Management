package GUI;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class AdminView {

	private JPanel contentPanel; // contentPanel để hiển thị các giao diện
	JLabel lblMaNV, lblHoTen, lblChucVu, lblKho, dataMaNV, dataHoTen, dataChucVu, dataKho;
	
	
	public AdminView() {
		init();
	}

	private void init() {
		// Dùng thư viện FlatLaf để làm giao diện đẹp hơn
		FlatRobotoFont.install();
		FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
		FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
		FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
		FlatIntelliJLaf.registerCustomDefaultsSource("style");
		FlatIntelliJLaf.setup();

		// Tạo JFrame
		JFrame f = new JFrame("Admin");
		f.setSize(1500, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel, menuPanel;

		// Main Panel chứa menuPanel và contentPanel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.BLUE);

		// ======================= Menu Panel (Chứa 3 phần)
		// ============================//
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout()); // Chia theo chiều dọc
		menuPanel.setBackground(Color.green);
//        menuPanel.setPreferredSize(new Dimension(200, 800));
		GridBagConstraints gbc = new GridBagConstraints();

		// ======================= 1. Info Panel (0.3) ============================//
		JPanel infoPanel = new JPanel(null);
		infoPanel.setBackground(Color.decode("#01BFF4"));
		infoPanel.setPreferredSize(new Dimension(menuPanel.getWidth(), 100));
		gbc.weightx = 1.0;
		gbc.weighty = 0.3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		menuPanel.add(infoPanel, gbc);
		
		lblMaNV = new JLabel("Mã NV: ");
		lblMaNV.setBounds(5, 5, 50, 20);
		infoPanel.add(lblMaNV);
		dataMaNV = new JLabel("ABC");
		dataMaNV.setBounds(55, 5, 50, 20);
		infoPanel.add(dataMaNV);
		
		lblMaNV = new JLabel("Họ tên: ");
		lblMaNV.setBounds(5, 22, 50, 20);
		infoPanel.add(lblMaNV);
		dataHoTen = new JLabel("DEF");
		dataHoTen.setBounds(55, 22, 200, 20);
		infoPanel.add(dataHoTen);
		
		lblMaNV = new JLabel("Chức vụ: ");
		lblMaNV.setBounds(5, 37, 50, 20);
		infoPanel.add(lblMaNV);
		dataChucVu = new JLabel("GHI");
		dataChucVu.setBounds(55, 37, 50, 20);
		infoPanel.add(dataChucVu);
		
		lblMaNV = new JLabel("Kho: ");
		lblMaNV.setBounds(5, 52, 50, 20);
		infoPanel.add(lblMaNV);
		dataKho = new JLabel("JKL");
		dataKho.setBounds(55, 52, 50, 20);
		infoPanel.add(dataKho);
		

		// ======================= 2. Menu Panel (0.6) ============================//
		JPanel menuBarPanel = new JPanel();
		menuBarPanel.setLayout(new BoxLayout(menuBarPanel, BoxLayout.Y_AXIS));
		menuBarPanel.setBackground(Color.white);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));

		// ================================ MENU ITEMS
		// ================================//
		JMenu menuSanPham = createRightAlignedMenu("SẢN PHẨM");

		JMenu menuNhaCungCap = createRightAlignedMenu("NHÀ CUNG CẤP");

		JMenu menuNhapHang = createRightAlignedMenu("NHẬP HÀNG");
		JMenuItem menuItemTaoPhieuNhap = new JMenuItem("Tạo phiếu nhập");
		JMenuItem menuItemPhieuNhap = new JMenuItem("Phiếu nhập");
		menuNhapHang.add(menuItemTaoPhieuNhap);
		menuNhapHang.add(menuItemPhieuNhap);

		JMenu menuXuatHang = createRightAlignedMenu("XUẤT HÀNG");
		JMenuItem menuItemTaoPhieuXuat = new JMenuItem("Tạo phiếu xuất");
		JMenuItem menuItemPhieuXuat = new JMenuItem("Phiếu xuất");
		menuXuatHang.add(menuItemTaoPhieuXuat);
		menuXuatHang.add(menuItemPhieuXuat);

		JMenu menuNhanVien = createRightAlignedMenu("NHÂN VIÊN");
		JMenuItem menuItemDanhSachNhanVien = new JMenuItem("Danh sách nhân viên");
		JMenuItem menuItemBangChamCong = new JMenuItem("Danh sách bảng chấm công");
		JMenuItem menuItemDanhSachDonXin = new JMenuItem("Danh sách đơn xin");
		menuNhanVien.add(menuItemDanhSachNhanVien);
		menuNhanVien.add(menuItemBangChamCong);
		menuNhanVien.add(menuItemDanhSachDonXin);

		JMenu menuChamCong = createRightAlignedMenu("CHẤM CÔNG");

		JMenu menuTaoDon = createRightAlignedMenu("TẠO ĐƠN");

		JMenu menuThongKe = createRightAlignedMenu("THỐNG KÊ");

		JMenu menuKho = createRightAlignedMenu("KHO");

		// Thêm vào menuBar
		menuBar.add(menuSanPham);
		menuBar.add(menuNhaCungCap);
		menuBar.add(menuNhapHang);
		menuBar.add(menuXuatHang);
		menuBar.add(menuNhanVien);
		menuBar.add(menuChamCong);
		menuBar.add(menuTaoDon);
		menuBar.add(menuThongKe);
		menuBar.add(menuKho);

		// Thêm menuBar vào menuBarPanel
		menuBarPanel.add(menuBar);

		gbc.weightx = 1.0;
		gbc.weighty = 0.63;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		menuPanel.add(menuBarPanel, gbc);

		// ======================= 3. Logout Panel (0.1) ============================//
		JPanel logoutPanel = new JPanel();
		logoutPanel.setBackground(Color.YELLOW);
		logoutPanel.setLayout(new GridBagLayout());

		JButton personalInfoButton = new JButton("Thông tin cá nhân");
		personalInfoButton.setBorderPainted(false);
		personalInfoButton.setPreferredSize(new Dimension(logoutPanel.getWidth(), 50));
		gbc.gridx = 0;
		gbc.gridy = 0;
		logoutPanel.add(personalInfoButton, gbc);

		JButton logoutButton = new JButton("Đăng xuất");
		logoutButton.setBorderPainted(false);
		logoutButton.setPreferredSize(new Dimension(logoutPanel.getWidth(), 50));
		gbc.gridx = 0;
		gbc.gridy = 1;
		logoutPanel.add(logoutButton, gbc);

		// Set tỷ lệ và vị trí hiển thị của logoutPanel
		gbc.weightx = 1.0;
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 2;
		menuPanel.add(logoutPanel, gbc);

		// ============================ Content Panel ============================//
		contentPanel = new JPanel(new CardLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setPreferredSize(new Dimension(1400, 900));
		mainPanel.add(menuPanel, BorderLayout.WEST);
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// Thiết lập tỷ lệ hiển thị cho menuPanel và contentPanel
		int menuWidth = f.getWidth() / 8; // Chiều rộng của thanhDieuHuong là 1/4 của frame
		int contentWidth = f.getWidth() - menuWidth; // Chiều rộng của noiDung
		menuPanel.setPreferredSize(new Dimension(menuWidth, f.getHeight())); // Đặt kích thước cho menuPanel
		contentPanel.setPreferredSize(new Dimension(contentWidth, f.getHeight())); // Đặt kích thước cho contentPanel

		// Định danh cho các thẻ giao diện
		final String sanPham_Identity = "SAN PHAM";

		final String nhaCungCap_Identity = "NHA CUNG CAP";

		final String nhapHang_Identity = "NHAP HANG";
		final String phieuNhap_Identity = "PHIEU NHAP";

		final String xuatHang_Identity = "XUAT HANG";
		final String phieuXuat_Identity = "PHIEU XUAT";

		final String danhSachNhanVien_Identity = "DANH SACH NHAN VIEN";
		final String danhSachBangChamCong_Identity_ = "DANH SACH BANG CHAM CONG";
		final String danhSachDonXin_Identity = "DANH SACH DON XIN";

		final String chamCong_Identity = "CHAM CONG";

		final String taoDon_Identity = "TAO DON";

		final String kho_Identity = "KHO";

		final String thongKe_Identity = "THONG KE";

		final String thongTinCaNhan_Identity = "THONG TIN CA NHAN";

		f.add(mainPanel);
		f.setResizable(false);
		f.setVisible(true);

//=========================================== Khu vực add Listener cho các nút/menu/menuItem ==========================================================//

		// mouseListener cho menu sản phẩm
		menuSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    JFXPanel fxPanel = new JFXPanel(); // Khởi tạo toolkit JavaFX
                    SanPhamGUI productObj = new SanPhamGUI();
                    Parent content = productObj.getContent(); // Load FXML sau khi toolkit sẵn sàng
                    javafx.application.Platform.runLater(() -> {
                        fxPanel.setScene(new Scene(content));
                    });
                    contentPanel.add(fxPanel, sanPham_Identity);
                    CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
                    cardLayout.show(contentPanel, sanPham_Identity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
		
		// MouseListener cho menu "SUPPLIERS"
		menuNhaCungCap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				NhaCungCapGUI supplierObj = new NhaCungCapGUI();
				contentPanel.add(supplierObj, nhaCungCap_Identity);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, nhaCungCap_Identity);
			}
		});

		// ActionListener cho Tạo phiếu nhập. Vì menuItem thì kích hoạt sự kiện bằng
		// ActionListener chứ k phải MouseListener
		menuItemTaoPhieuNhap.addActionListener(e -> {
			NhapHangGUI importObj = new NhapHangGUI(); // Tạo instance của ImportGUI
			contentPanel.add(importObj, nhapHang_Identity); // Thêm vào contentPanel
			CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
			cardLayout.show(contentPanel, nhapHang_Identity); // Chuyển sang ImportGUI
		});

		// ActionListener cho menuItem phiếu nhập
		menuItemPhieuNhap.addActionListener(e -> {
			PhieuNhapGUI phieuNhapObj = new PhieuNhapGUI();
			contentPanel.add(phieuNhapObj, phieuNhap_Identity);
			CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
			cardLayout.show(contentPanel, phieuNhap_Identity);
		});

		// ActionListener cho menuItem Export
		menuItemTaoPhieuXuat.addActionListener(e -> {
			XuatHangGUI exportObj = new XuatHangGUI();
			contentPanel.add(exportObj, xuatHang_Identity);
			CardLayout cartLayout = (CardLayout) contentPanel.getLayout();
			cartLayout.show(contentPanel, xuatHang_Identity);
		});

		// ActionListener cho menuItem Stock Outward Slip
		menuItemPhieuXuat.addActionListener(e -> {
			PhieuXuatGUI stockOutwardSlipObj = new PhieuXuatGUI();
			contentPanel.add(stockOutwardSlipObj, phieuXuat_Identity);
			CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
			cardLayout.show(contentPanel, phieuXuat_Identity);
		});

		// ActionListener cho menuItem Danh sách nhân viên
		menuItemDanhSachNhanVien.addActionListener(e -> {
			NhanVienGUI employeeObj = new NhanVienGUI();
			contentPanel.add(employeeObj, danhSachNhanVien_Identity);
			CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
			cardLayout.show(contentPanel, danhSachNhanVien_Identity);
		});

		// ActionListener cho menuItem bảng chấm công
		menuItemBangChamCong.addActionListener(e -> {
			BangChamCongGUI timesheetObj = new BangChamCongGUI();
			contentPanel.add(timesheetObj, danhSachBangChamCong_Identity_);
			CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
			cardLayout.show(contentPanel, danhSachBangChamCong_Identity_);
		});

		// ActionListener cho menuItem Danh sách dơn xin
		menuItemDanhSachDonXin.addActionListener(e -> {
			DanhSachDonXinGUI leaveRequestObj = new DanhSachDonXinGUI();
			contentPanel.add(leaveRequestObj, danhSachDonXin_Identity);
			CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
			cardLayout.show(contentPanel, danhSachDonXin_Identity);
		});

		menuChamCong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChamCongGUI chamCongObj = new ChamCongGUI();
				contentPanel.add(chamCongObj, chamCong_Identity);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, chamCong_Identity);
			}
		});

		// MouseListener cho menu Tạo đơn
		menuTaoDon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TaoDonXinGUI createLeaveRequestObj = new TaoDonXinGUI();
				contentPanel.add(createLeaveRequestObj, taoDon_Identity);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, taoDon_Identity);
			}
		});

		// MouseListener cho menu thống kê
		menuThongKe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ThongKeGUI statisticsObj = new ThongKeGUI();
				contentPanel.add(statisticsObj, thongKe_Identity);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, thongKe_Identity);
			}
		});

		// MouseListener cho menu kho
		menuKho.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				KhoGUI statisticsObj = new KhoGUI();
				contentPanel.add(statisticsObj, kho_Identity);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, kho_Identity);
			}
		});

		// ActionListener cho button Personal Information
		personalInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ThongTinCaNhanGUI thongTinCaNhanObj = new ThongTinCaNhanGUI();
				contentPanel.add(thongTinCaNhanObj, thongTinCaNhan_Identity);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, thongTinCaNhan_Identity);
			}
		});

		// mouseListener cho nút changeInfoButton
		personalInfoButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				personalInfoButton.setBackground(Color.decode("#47CBFF")); // để đổi màu khi rê chuột vào
				personalInfoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				personalInfoButton.setBackground(Color.white); // để đổi màu về như cũ khi rê chuột vào
			}
		});

		// mouseListener cho nút logoutButton
		logoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				logoutButton.setBackground(Color.decode("#47CBFF")); // để đổi màu khi rê chuột vào
				logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logoutButton.setBackground(Color.white); // để đổi màu về như cũ khi rê chuột vào
			}

		});

		personalInfoButton.setOpaque(true);
		personalInfoButton.setBackground(Color.WHITE);

		logoutButton.setOpaque(true);
		logoutButton.setBackground(Color.WHITE);
	}

	private JMenu createRightAlignedMenu(String title) {
		JMenu menu = new JMenu(title);
		menu.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái theo BoxLayout
		menu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Full chiều ngang, tăng chiều cao
		menu.setPreferredSize(new Dimension(200, 50)); // Điều chỉnh chiều cao theo ý muốn
		menu.setOpaque(true);
		menu.setBackground(Color.WHITE);
		menu.setBorderPainted(false); // Không vẽ viền
		menu.setBorder(null); // Xóa viền
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		JPopupMenu popupMenu = menu.getPopupMenu();

		// Thêm MouseListener để thay đổi con trỏ chuột
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menu.setBackground(Color.decode("#47CBFF")); // để đổi màu khi rê chuột vào
				menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				menu.setBackground(Color.white); // để đổi màu về như cũ khi rê chuột vào
			}

		});

		// Lắng nghe sự kiện mở menu để hiển thị popup đúng vị trí
		menu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				if (!popupMenu.isVisible()) {
					SwingUtilities.invokeLater(() -> {
						Point location = menu.getLocationOnScreen();
						popupMenu.setLocation(location.x + menu.getWidth(), location.y);
						popupMenu.setVisible(true);
					});
				}
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}

		});

		return menu;
	}

	public void showProductsGUI() {
		contentPanel.removeAll(); // Xóa giao diện cũ
		contentPanel.add(new SanPhamGUI()); // Thêm giao diện ProductsGUI
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	
	public void hienThiThongTinNguoiDung(String maNV, String hoTen, String chucVu, String maKho) {
		dataMaNV.setText(maNV);
		dataHoTen.setText(hoTen);
		dataChucVu.setText(chucVu);
		dataKho.setText(maKho);
	}

	//hàm hiển thị thông tin dòng code
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}
	
	
	public static void main(String[] args) {
		new AdminView();
	}
}
