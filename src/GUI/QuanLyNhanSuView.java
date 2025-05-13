package GUI;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class QuanLyNhanSuView {
	
	private JPanel contentPanel; // contentPanel để hiển thị các giao diện
	JLabel lblMaNV, lblHoTen, lblChucVu, lblKho, dataMaNV, dataHoTen, dataChucVu, dataKho;

	
	public QuanLyNhanSuView() {
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
		JFrame f = new JFrame("Quản lý nhân sự");
		f.setSize(1500, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel, menuPanel;

		// Main Panel chứa menuPanel và contentPanel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.BLUE);

		// ======================= Menu Panel (Chứa 3 phần) ============================//
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout()); 
		menuPanel.setBackground(Color.white);
        menuPanel.setPreferredSize(new Dimension(1000, 500));
		GridBagConstraints gbc = new GridBagConstraints();

		// ======================= 1. Info Panel ============================//
		JPanel infoPanel = new JPanel(new GridBagLayout());
		infoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#F38B2B")));	//top=0, left=0, bottom=2, right=0
		
		
		JPanel infoLeftPanel, infoRightPanel;
		infoLeftPanel = new JPanel(null);
		infoLeftPanel.setPreferredSize(new Dimension(200, 200));
		infoLeftPanel.setBackground(Color.white);
		gbc.weightx = 0.43;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		infoPanel.add(infoLeftPanel, gbc);
		
		/// ======================= Thêm ảnh vào infoLeftPanel =======================//
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/hr-manager.png")); // Đường dẫn ảnh
		Image image = icon.getImage();

		// Tạo JLabel chứa hình ảnh, chưa cần set kích thước vội
		JLabel imageLabel = new JLabel(new ImageIcon(image));
		infoLeftPanel.add(imageLabel);

		// Lắng nghe sự kiện resize của infoLeftPanel để cập nhật ảnh
		infoLeftPanel.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e) {
		        // Chỉ thực hiện khi kích thước Panel đã sẵn sàng
		        if (infoLeftPanel.getWidth() > 0 && infoLeftPanel.getHeight() > 0) {
		            // Điều chỉnh kích thước ảnh vừa với infoLeftPanel
		            Image scaledImage = image.getScaledInstance(infoLeftPanel.getWidth()-25, infoLeftPanel.getHeight()-25, Image.SCALE_SMOOTH);

		            // Cập nhật icon cho JLabel
		            imageLabel.setIcon(new ImageIcon(scaledImage));
		            
		            // Set kích thước cho label trùng với panel
		            imageLabel.setBounds(0, 0, infoLeftPanel.getWidth(), infoLeftPanel.getHeight());

		            // Vẽ lại panel
		            infoLeftPanel.revalidate();
		            infoLeftPanel.repaint();
		        }
		    }
		});


		
		
		
		infoRightPanel = new JPanel(null);
		infoRightPanel.setBackground(Color.white);
		gbc.weightx = 0.57;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		infoPanel.add(infoRightPanel, gbc);
		
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setPreferredSize(new Dimension(menuPanel.getWidth(), 20));
		gbc.weightx = 1.0;
		gbc.weighty = 0.18;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		menuPanel.add(infoPanel, gbc);
		
		dataHoTen = new JLabel("ABC");
		dataHoTen.setBounds(10, 25, 200, 20);
		infoRightPanel.add(dataHoTen);
		
		dataChucVu = new JLabel("DEF");
		dataChucVu.setBounds(10, 45, 200, 20);
		infoRightPanel.add(dataChucVu);
		
		
	
		
		Font lblFont = new Font("Arial", Font.BOLD, 13);
		dataHoTen.setFont(lblFont);
		Font lblFont2 = new Font("Arial", Font.PLAIN, 12);
		dataChucVu.setFont(lblFont2);
        

        //======================= 2. Menu Panel (0.6) ============================//
        JPanel menuBarPanel = new JPanel();
        menuBarPanel.setLayout(new BoxLayout(menuBarPanel, BoxLayout.Y_AXIS));
        menuBarPanel.setBackground(Color.white);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));

        // ================================ MENU ITEMS ================================//
        JMenu menuNhanVien = createRightAlignedMenu("NHÂN VIÊN");
		JMenuItem menuItemDanhSachNhanVien = new JMenuItem("Danh sách nhân viên");
		// JMenuItem menuItemBangChamCong = new JMenuItem("Danh sách chấm công");
		JMenuItem menuItemDanhSachDonXin = new JMenuItem("Danh sách đơn xin nghỉ");
		JMenuItem menuItemDanhSachLuong = new JMenuItem("Danh sách lương");
		menuNhanVien.add(menuItemDanhSachNhanVien);
		// menuNhanVien.add(menuItemBangChamCong);
		menuNhanVien.add(menuItemDanhSachDonXin);
		menuNhanVien.add(menuItemDanhSachLuong);
        
        JMenu menuChamCong = createRightAlignedMenu("CHẤM CÔNG");
        
        JMenu menuTaoDon = createRightAlignedMenu("TẠO ĐƠN");
                
//        JMenu menuThongKe = createRightAlignedMenu("THỐNG KÊ");

        // Thêm vào menuBar
        menuBar.add(menuNhanVien);
        menuBar.add(menuChamCong);
        menuBar.add(menuTaoDon);
//        menuBar.add(menuThongKe);
        

        // Thêm menuBar vào menuBarPanel
        menuBarPanel.add(menuBar);
        
        gbc.weightx = 1.0;
        gbc.weighty = 0.82;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        menuPanel.add(menuBarPanel, gbc);

        //======================= 3. Logout Panel (0.1) ============================//
        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(Color.white);
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
        contentPanel.setBackground(Color.decode("#F38B2B"));
        contentPanel.setPreferredSize(new Dimension(1400, 900));
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        //Thiết lập tỷ lệ hiển thị cho menuPanel và contentPanel
        int menuWidth = f.getWidth() / 8; // Chiều rộng của thanhDieuHuong là 1/4 của frame
        int contentWidth = f.getWidth() - menuWidth; // Chiều rộng của noiDung
        menuPanel.setPreferredSize(new Dimension(menuWidth, f.getHeight())); // Đặt kích thước cho menuPanel
        contentPanel.setPreferredSize(new Dimension(contentWidth, f.getHeight())); // Đặt kích thước cho contentPanel
        
        // Định danh cho các thẻ giao diện
		final String danhSachNhanVien_Identity = "DANH SACH NHAN VIEN";
//		final String danhSachBangChamCong_Identity_ = "DANH SACH BANG CHAM CONG";
		final String danhSachDonXin_Identity = "DANH SACH DON XIN";
		final String danhSachBangLuong_Identity = "DANH SACH BANG LUONG";

		final String chamCong_Identity = "CHAM CONG";
        final String dsChamCong_Identity = "DANH SACH CHAM CONG";
        
        final String taoDon_Identity = "TAO DON";
                
        final String thongKe_Identity = "THONG KE";
        
        final String thongTinCaNhan_Identity = "THONG TIN CA NHAN";
        
        f.add(mainPanel);
        f.setResizable(false);
        f.setVisible(true);
        
        
        
        
//=========================================== Khu vực add Listener cho các nút/menu/menuItem ==========================================================//
   
        
     // ActionListener cho menuItem Danh sách nhân viên
     		menuItemDanhSachNhanVien.addActionListener(e -> {
     			NhanVienGUI employeeObj = new NhanVienGUI();
     			contentPanel.add(employeeObj, danhSachNhanVien_Identity);
     			CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
     			cardLayout.show(contentPanel, danhSachNhanVien_Identity);
     		});
     		
			// ActionListener cho menuItem Danh sách lương
			menuItemDanhSachLuong.addActionListener(e -> {
				DSBangLuongGUI dsBangLuongObj = new DSBangLuongGUI();
				contentPanel.add(dsBangLuongObj, danhSachBangLuong_Identity);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, danhSachBangLuong_Identity);
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
					CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
					DSBangChamCongGUI dschamCongObj = new DSBangChamCongGUI(cardLayout, contentPanel);
					contentPanel.add(dschamCongObj, dsChamCong_Identity);
					cardLayout.show(contentPanel, dsChamCong_Identity);

					ChiTietChamCongGUI bcc = new ChiTietChamCongGUI(cardLayout, contentPanel);
					contentPanel.add(bcc, chamCong_Identity);
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
        
		
//		// MouseListener cho menu thống kê
//		menuThongKe.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				ThongKeGUI statisticsObj = new ThongKeGUI();
//				contentPanel.add(statisticsObj, thongKe_Identity);
//				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
//				cardLayout.show(contentPanel, thongKe_Identity);
//			}
//		});
		
		
     		personalInfoButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
    					System.out.println("Clicked personalInfoButton");
                        JFXPanel fxPanel = new JFXPanel(); // Khởi tạo toolkit JavaFX
                        ThongTinCaNhanGUI personalInforObj = new ThongTinCaNhanGUI();
                        Parent content = personalInforObj.getContent(); // Load FXML sau khi toolkit sẵn sàng
                        javafx.application.Platform.runLater(() -> {
                            fxPanel.setScene(new Scene(content));
                        });
                        contentPanel.add(fxPanel, thongTinCaNhan_Identity);
                        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
                        cardLayout.show(contentPanel, thongTinCaNhan_Identity);
                    } catch (Exception ex) {
    					System.out.println("error personalInfoButton");

                        ex.printStackTrace();
                    }
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
		
		//mouseListener cho nút logoutButton
		logoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				logoutButton.setBackground(Color.decode("#F38B2B")); //để đổi màu khi rê chuột vào
				logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
    		public void mousePressed(MouseEvent e) {
				logoutButton.setBackground(Color.decode("#F38B2B")); //để đổi màu khi nhấn nút
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
        			menu.setBackground(Color.decode("#F38B2B")); //để đổi màu khi rê chuột vào
        			menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        		}
        		
        		@Override
                public void mouseClicked(MouseEvent e) {
        			menu.setBackground(Color.decode("#F38B2B")); //để đổi màu khi nhấn chuột
                }
        		
        		@Override
        		public void mousePressed(MouseEvent e) { // Khi chuột được nhấn xuống
        			menu.setBackground(Color.decode("#F38B2B")); //để đổi màu khi nhấn chuột

        		}

        		@Override
        		public void mouseReleased(MouseEvent e) {         			// Khi chuột được nhả ra
        			menu.setBackground(Color.white); //để đổi màu Khi chuột được nhả ra

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
                menu.setBackground(Color.decode("#F38B2B")); // Khi click vào, đổi màu cam
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
                menu.setBackground(Color.white); 
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                menu.setBackground(Color.white); 
            }
            
        });

        return menu;
	}

    public void showProductsGUI() {
    	contentPanel.removeAll();	//Xóa giao diện cũ
    	contentPanel.add(new SanPhamGUI()); //Thêm giao diện ProductsGUI
    	contentPanel.revalidate();
    	contentPanel.repaint();
    }
    
	public void hienThiThongTinNguoiDung(String hoTen, String chucVu) {
		dataHoTen.setText(hoTen);
		dataChucVu.setText(chucVu);
	}

	//hàm hiển thị thông tin dòng code
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}
    
    public static void main(String[] args) {
        new QuanLyNhanSuView();
    }
}
