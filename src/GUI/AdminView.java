package GUI;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class AdminView {
	
	private JPanel contentPanel; // contentPanel để hiển thị các giao diện
	
	public AdminView() {
        // Tạo JFrame
        JFrame f = new JFrame("Admin");
        f.setSize(1400, 800);
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
        infoPanel.setBackground(Color.cyan);
        infoPanel.setPreferredSize(new Dimension(menuPanel.getWidth(), 100));
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(infoPanel, gbc);
        

        //======================= 2. Menu Panel (0.6) ============================//
        JPanel menuBarPanel = new JPanel();
        menuBarPanel.setLayout(new BoxLayout(menuBarPanel, BoxLayout.Y_AXIS));
        menuBarPanel.setBackground(Color.ORANGE);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));

        // ================================ MENU ITEMS ================================//
        JMenu menuProducts = createRightAlignedMenu("PRODUCTS");
        
        JMenu menuSuppliers = createRightAlignedMenu("SUPPLIERS");
        
        JMenu menuStockReceipt = createRightAlignedMenu("STOCK RECEIPT");
        JMenuItem importMenuItem = new JMenuItem("Import");
        JMenuItem stockReceipt = new JMenuItem("Stock Inward Slip");
        menuStockReceipt.add(importMenuItem);
        menuStockReceipt.add(stockReceipt);
        
        JMenu menuStockRelease = createRightAlignedMenu("STOCK RELEASE");
        JMenuItem exportMenuItem = new JMenuItem("Export");
        JMenuItem stockRelease = new JMenuItem("Stock Outward Slip");
        menuStockRelease.add(exportMenuItem);
        menuStockRelease.add(stockRelease);
        
        JMenu menuEmployeeManagement = createRightAlignedMenu("EMPLOYEE");
        JMenuItem employeeMenuItem = new JMenuItem("Employees");
        JMenuItem accountMenuItem = new JMenuItem("Accounts");
        JMenuItem timesheetMenuItem = new JMenuItem("Timesheets");
        JMenuItem leaveRequestMenuItem = new JMenuItem("Leave requests");
        menuEmployeeManagement.add(employeeMenuItem);
        menuEmployeeManagement.add(accountMenuItem);
        menuEmployeeManagement.add(timesheetMenuItem);
        menuEmployeeManagement.add(leaveRequestMenuItem);
        
        JMenu menuCreateLeaveRequest = createRightAlignedMenu("CREATE LEAVE REQUEST");
        
        JMenu menuWarehouse = createRightAlignedMenu("WAREHOUSE");
        
        JMenu menuStatistics = createRightAlignedMenu("STATISTICS");

        // Thêm vào menuBar
        menuBar.add(menuProducts);
        menuBar.add(menuSuppliers);
        menuBar.add(menuStockReceipt);
        menuBar.add(menuStockRelease);
        menuBar.add(menuEmployeeManagement);
        menuBar.add(menuCreateLeaveRequest);
        menuBar.add(menuWarehouse);
        menuBar.add(menuStatistics);
        

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

        
        JButton changeInfoButton = new JButton("Personal Information");
        changeInfoButton.setBorderPainted(false);
        changeInfoButton.setPreferredSize(new Dimension(logoutPanel.getWidth(), 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        logoutPanel.add(changeInfoButton, gbc);
        
        JButton logoutButton = new JButton("Logout");
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
        contentPanel.setPreferredSize(new Dimension(1200, 800));
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        //Thiết lập tỷ lệ hiển thị cho menuPanel và contentPanel
        int menuWidth = f.getWidth() / 8; // Chiều rộng của thanhDieuHuong là 1/4 của frame
        int contentWidth = f.getWidth() - menuWidth; // Chiều rộng của noiDung
        menuPanel.setPreferredSize(new Dimension(menuWidth, f.getHeight())); // Đặt kích thước cho menuPanel
        contentPanel.setPreferredSize(new Dimension(contentWidth, f.getHeight())); // Đặt kích thước cho contentPanel
        
        // Định danh cho các thẻ giao diện
        final String productsMenu_Identification = "PRODUCTS";
        final String suppliersMenu_Identification = "SUPPLIERS";
        final String stockReceiptMenu_Identification = "STOCK RECEIPT";
        final String importMenuItem_Identification = "IMPORT";
        final String stockInwardSlipMenuItem_Identification = "STOCK INWARD SLIP";
        final String stockReleaseMenu_Identification = "STOCK RELEASE";
        final String exportMenuItem_Identification = "EXPORT";
        final String stockOutwardSlipMenuItem_Identification = "STOCK OUTWARD SLIP";
        final String employeeMenu_Identification = "EMPLOYEE";
        final String employeeListMenuItem_Identification = "EMPLOYEES";
        final String accountListMenuItem_Identification = "ACCOUNTS";
        final String timesheetMenuItem_Identification = "TIMESHEETs";
        final String leaveRequestMenuItem_Identification = "LEAVE REQUEST";
        final String leaveRequestMenu_Identification = "CREATE LEAVE REQUEST";
        final String warehouseMenu_Identification = "WAREHOUSE";
        final String statisticsMenu_Identification = "STATISTICS";
        final String personalInfoButton_Identification = "Personal Information";
        
        f.add(mainPanel);
        f.setResizable(false);
        f.setVisible(true);
        
        
        
        
//=========================================== Khu vực add Listener cho các nút/menu/menuItem ==========================================================//
   
        //mouseListener cho nút changeInfoButton
		changeInfoButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				changeInfoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		
		//mouseListener cho nút logoutButton
		logoutButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

		});
	
		//mouseListener cho menu "PRODUCTS"
		menuProducts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductsGUI productObj = new ProductsGUI();	//tạo 1 instance của ProductsGUI
				contentPanel.add(productObj, productsMenu_Identification);	//thêm instance đó vào contentPanel kèm với định danh của nó
				CardLayout cardLayout = (CardLayout)contentPanel.getLayout(); //dùng CardLayout để hiển thị giao diện của lớp ProductsGUI khi click vào menu
				cardLayout.show(contentPanel, productsMenu_Identification);
				
			}
		});
		
		//MouseListener cho menu "SUPPLIERS"
		menuSuppliers.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					SuppliersGUI supplierObj = new SuppliersGUI();
					contentPanel.add(supplierObj, suppliersMenu_Identification);
					CardLayout cardLayout = (CardLayout)contentPanel.getLayout();
					cardLayout.show(contentPanel, suppliersMenu_Identification);
				}
		});
		
		//ActionListener cho Imports. Vì menuItem thì kích hoạt sự kiện bằng ActionListener chứ k phải MouseListener
		importMenuItem.addActionListener(e -> {
		    ImportGUI importObj = new ImportGUI(); // Tạo instance của ImportGUI
		    contentPanel.add(importObj, importMenuItem_Identification); // Thêm vào contentPanel
		    CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
		    cardLayout.show(contentPanel, importMenuItem_Identification); // Chuyển sang ImportGUI
		});
		
		//ActionListener cho menuItem Stock Inward Slip
		stockReceipt.addActionListener(e ->{
			StockInwardSlipGUI stockInwardSlipObj = new StockInwardSlipGUI();
			contentPanel.add(stockInwardSlipObj, stockInwardSlipMenuItem_Identification);
			CardLayout cardLayout = (CardLayout)contentPanel.getLayout();
			cardLayout.show(contentPanel, stockInwardSlipMenuItem_Identification);
		});
		
		//ActionListener cho menuItem Export 
		exportMenuItem.addActionListener(e ->{
			ExportGUI exportObj = new ExportGUI();
			contentPanel.add(exportObj, employeeListMenuItem_Identification);
			CardLayout cartLayout = (CardLayout)contentPanel.getLayout();
			cartLayout.show(contentPanel,employeeListMenuItem_Identification);
		});
		
		//ActionListener cho menuItem Stock Outward Slip
		stockRelease.addActionListener(e ->{
			StockOutwardSlipGUI stockOutwardSlipObj = new StockOutwardSlipGUI();
			contentPanel.add(stockOutwardSlipObj, stockOutwardSlipMenuItem_Identification);
			CardLayout cardLayout = (CardLayout)contentPanel.getLayout();
			cardLayout.show(contentPanel, stockOutwardSlipMenuItem_Identification);
		});
		
		//ActionListener cho menuItem Employees
		employeeMenuItem.addActionListener(e -> {
			EmployeeGUI employeeObj = new EmployeeGUI();
			contentPanel.add(employeeObj, employeeListMenuItem_Identification);
			CardLayout cardLayout = (CardLayout)contentPanel.getLayout();
			cardLayout.show(contentPanel, employeeListMenuItem_Identification);
		});
		
		//ActionListener cho menuItem Accounts
		accountMenuItem.addActionListener(e -> {
			AccountGUI accountObj = new AccountGUI();
			contentPanel.add(accountObj, accountListMenuItem_Identification);
			CardLayout cardLayout = (CardLayout)contentPanel.getLayout();
			cardLayout.show(contentPanel, accountListMenuItem_Identification);
		});
		
		//ActionListener cho menuItem Timesheet
		timesheetMenuItem.addActionListener(e -> {
			TimesheetGUI timesheetObj = new TimesheetGUI();
			contentPanel.add(timesheetObj, timesheetMenuItem_Identification);
			CardLayout cardLayout = (CardLayout)contentPanel.getLayout();
			cardLayout.show(contentPanel, timesheetMenuItem_Identification);
		});
		
		//ActionListener cho menuItem Leave Requests
		leaveRequestMenuItem.addActionListener(e -> {
			LeaveRequestGUI leaveRequestObj = new LeaveRequestGUI();
			contentPanel.add(leaveRequestObj, leaveRequestMenu_Identification);
			CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
			cardLayout.show(contentPanel, leaveRequestMenu_Identification);
		});
		
		//MouseListener cho menu Create Leave Request
		menuCreateLeaveRequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CreateLeaveRequestGUI createLeaveRequestObj = new CreateLeaveRequestGUI();
				contentPanel.add(createLeaveRequestObj, leaveRequestMenu_Identification);
				CardLayout cardLayout = (CardLayout)contentPanel.getLayout();
				cardLayout.show(contentPanel, leaveRequestMenu_Identification);
			}
		});
		
		// MouseListener cho menu Statistics
		menuStatistics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StatisticsGUI statisticsObj = new StatisticsGUI();
				contentPanel.add(statisticsObj, statisticsMenu_Identification);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, statisticsMenu_Identification);
			}
		});
		
		//ActionListener cho button Personal Information
		changeInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PersonalInformationGUI personalInformationObj = new PersonalInformationGUI();
				contentPanel.add(personalInformationObj, personalInfoButton_Identification);
				CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
				cardLayout.show(contentPanel, personalInfoButton_Identification);
			}
		});
		
		//MouseListener cho menu Warehouse
		menuWarehouse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WarehouseGUI warehouseObj = new WarehouseGUI();
				contentPanel.add(warehouseObj, warehouseMenu_Identification);
				CardLayout cardLayout = (CardLayout)contentPanel.getLayout();
				cardLayout.show(contentPanel, warehouseMenu_Identification);
			}
		});
//==========================================================================================================================================================//

	}

    private JMenu createRightAlignedMenu(String title) {
    	JMenu menu = new JMenu(title);
        menu.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái theo BoxLayout
        menu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Full chiều ngang, tăng chiều cao
        menu.setPreferredSize(new Dimension(200, 50)); // Điều chỉnh chiều cao theo ý muốn
        JPopupMenu popupMenu = menu.getPopupMenu();

        
        // Thêm MouseListener để thay đổi con trỏ chuột
        menu.addMouseListener(new MouseAdapter() {
        		@Override
        		public void mouseEntered(MouseEvent e) {
//        			menu.setBackground(Color.decode("")); để đổi màu khi rê chuột vào
        			menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        		}
        		
        		@Override
        		public void mouseExited(MouseEvent e) {
//        			menu.setBackground(Color.decode("")); để đổi màu về như cũ khi rê chuột vào
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
    	contentPanel.add(new ProductsGUI()); //Thêm giao diện ProductsGUI
    	contentPanel.revalidate();
    	contentPanel.repaint();
    }
    
    
    public static void main(String[] args) {
        new AdminView();
    }
}
