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


public class QuanLyKhoView {
	
	private JPanel contentPanel; // contentPanel để hiển thị các giao diện
	
	public QuanLyKhoView() {
		init();
    }
	
	
	private void init() {
		//Dùng thư viện FlatLaf để làm giao diện đẹp hơn
    	FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
		
        // Tạo JFrame
        JFrame f = new JFrame("Quản lý kho");
        f.setSize(1500, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel, menuPanel;

        // Main Panel chứa menuPanel và contentPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLUE);

        //======================= Menu Panel (Chứa 3 phần) ============================//
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout()); // Chia theo chiều dọc
        menuPanel.setBackground(Color.green);
//        menuPanel.setPreferredSize(new Dimension(200, 800));
        GridBagConstraints gbc = new GridBagConstraints();

        //======================= 1. Info Panel (0.3) ============================//
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.decode("#01BFF4"));
        infoPanel.setPreferredSize(new Dimension(menuPanel.getWidth(), 100));
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(infoPanel, gbc);
        

        //======================= 2. Menu Panel (0.6) ============================//
        JPanel menuBarPanel = new JPanel();
        menuBarPanel.setLayout(new BoxLayout(menuBarPanel, BoxLayout.Y_AXIS));
        menuBarPanel.setBackground(Color.white);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));

        // ================================ MENU ITEMS ================================//
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
        
                
        JMenu menuTaoDon = createRightAlignedMenu("TẠO ĐƠN");
        
        JMenu menuKho = createRightAlignedMenu("KHO");
        
        JMenu menuThongKe = createRightAlignedMenu("THỐNG KÊ");

        // Thêm vào menuBar
        menuBar.add(menuSanPham);
        menuBar.add(menuNhaCungCap);
        menuBar.add(menuNhapHang);
        menuBar.add(menuXuatHang);
        menuBar.add(menuTaoDon);
        menuBar.add(menuKho);
        menuBar.add(menuThongKe);
        

        // Thêm menuBar vào menuBarPanel
        menuBarPanel.add(menuBar);
        
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        menuPanel.add(menuBarPanel, gbc);

        //======================= 3. Logout Panel (0.1) ============================//
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
        
        
        //Set tỷ lệ và vị trí hiển thị của logoutPanel
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        menuPanel.add(logoutPanel, gbc);
        
        

        //============================ Content Panel ============================//
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.DARK_GRAY);
        contentPanel.setPreferredSize(new Dimension(1400, 900));
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        //Thiết lập tỷ lệ hiển thị cho menuPanel và contentPanel
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
				SanPhamGUI sanPhamObj = new SanPhamGUI(); // tạo 1 instance của SanPhamGUI
				contentPanel.add(sanPhamObj, sanPham_Identity); // thêm instance đó vào contentPanel kèm với định danh
																// của nó
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout(); // dùng CardLayout để hiển thị giao diện
																				// của lớp ProductsGUI khi click vào
																				// menu
				cardLayout.show(contentPanel, sanPham_Identity);

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
		
		
		
		// MouseListener cho menu Tạo đơn
		menuTaoDon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CreateLeaveRequestGUI createLeaveRequestObj = new CreateLeaveRequestGUI();
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
		
		
		//ActionListener cho button Personal Information
		personalInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PersonalInformationGUI personalInformationObj = new PersonalInformationGUI();
				contentPanel.add(personalInformationObj, thongTinCaNhan_Identity);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, thongTinCaNhan_Identity);
			}
		});
		
        //mouseListener cho nút changeInfoButton
		personalInfoButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				personalInfoButton.setBackground(Color.decode("#47CBFF")); //để đổi màu khi rê chuột vào
				personalInfoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
    		
    		@Override
    		public void mouseExited(MouseEvent e) {
    			personalInfoButton.setBackground(Color.white); //để đổi màu về như cũ khi rê chuột vào
    		}
		});
		
		//mouseListener cho nút logoutButton
		logoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				logoutButton.setBackground(Color.decode("#47CBFF")); //để đổi màu khi rê chuột vào
				logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
    		
    		@Override
    		public void mouseExited(MouseEvent e) {
    			logoutButton.setBackground(Color.white); //để đổi màu về như cũ khi rê chuột vào
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
        			menu.setBackground(Color.decode("#47CBFF")); //để đổi màu khi rê chuột vào
        			menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        		}
        		
        		@Override
        		public void mouseExited(MouseEvent e) {
        			menu.setBackground(Color.white); //để đổi màu về như cũ khi rê chuột vào
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
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
            
        });

        return menu;
	}

    public void showProductsGUI() {
    	contentPanel.removeAll();	//Xóa giao diện cũ
    	contentPanel.add(new SanPhamGUI()); //Thêm giao diện ProductsGUI
    	contentPanel.revalidate();
    	contentPanel.repaint();
    }
    
    
    public static void main(String[] args) {
        new QuanLyKhoView();
    }
}
