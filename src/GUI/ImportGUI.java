package GUI;

import BUS.ProductsBUS;
import DTO.*;
import DAO.ProductsDAO;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import com.toedter.calendar.JDateChooser; // Thêm thư viện JCalendar
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import javax.swing.filechooser.FileNameExtensionFilter;




public class ImportGUI extends JPanel{

	ProductsBUS productBUS = new ProductsBUS();
    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<ProductsDTO> productArr = new ArrayList<ProductsDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
    private JComboBox<String> brandComboBox, supplierComboBox;
    private JPanel productContent;
    private JLabel imageLabel;
    private JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
	
	
	//Constructor
    public ImportGUI(){
        initComponents();
        loadSanPhamList();
    }
    
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents() {
    	//Dùng thư viện FlatLaf để làm giao diện đẹp hơn
    	FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
        setLayout(new GridBagLayout()); //set Layout
        GridBagConstraints gbc = new GridBagConstraints();
        productContent = new JPanel();
        productContent.setBackground(Color.green);
        productContent.setLayout(new GridBagLayout());
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(productContent, gbc); // Thêm vào ProductsGUI
        
        //tạo 2 panel leftPanel, rightPanel
        JPanel leftPanel, rightPanel;
        //set thông số cho 2 panel
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.blue);
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productContent.add(leftPanel, gbc);
        
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Color.decode("#372083"));
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        productContent.add(rightPanel, gbc);
        
//========================================================== LEFT PANEL =============================================================================================//
        //Chia 3 panel con searchLeftPanel ở phần trên, productListLeftPanel ở phần giữa, quantityLeftPanel ở phần dưới;
        JPanel searchLeftPanel, productListLeftPanel, quantityLeftPanel;
        
        //set thông số cho 3 panel
        searchLeftPanel = new JPanel();
        searchLeftPanel.setLayout(null);
        searchLeftPanel.setBackground(Color.decode("#C62E65"));
        searchLeftPanel.setBorder(BorderFactory.createTitledBorder("Search"));
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(searchLeftPanel, gbc);
        
        productListLeftPanel = new JPanel();
        productListLeftPanel.setLayout(new GridBagLayout());
        productListLeftPanel.setBackground(Color.decode("#5D536B"));
        productListLeftPanel.setBorder(BorderFactory.createTitledBorder("Product List"));
        gbc.weightx = 1.0;
        gbc.weighty = 0.82;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        leftPanel.add(productListLeftPanel, gbc);
        
        quantityLeftPanel = new JPanel();
        quantityLeftPanel.setLayout(null);
        quantityLeftPanel.setBackground(Color.decode("#989FCE"));
        gbc.weightx = 1.0;
        gbc.weighty = 0.08;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        leftPanel.add(quantityLeftPanel, gbc);
        
        //===================================== searchLeftPanel ======================================//
        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(10, 14,50,20);
        searchLeftPanel.add(dateLabel);
        //DateChooser
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBounds(10, 33, 150, 25); // Định vị
        searchLeftPanel.add(dateChooser);
        
        
        JLabel searchLabel = new JLabel("Search");
        searchLabel.setBounds(330, 14, 50, 20);
        searchLeftPanel.add(searchLabel);
        JTextField searchTF = new JTextField();
        searchTF.setBounds(330, 33, 200, 25);
        searchLeftPanel.add(searchTF);
        
        ImageIcon iconSearch = new ImageIcon("C:\\Users\\ACER\\Dropbox\\My PC (LAPTOP-UGP9QJUT)\\Documents\\ITstudies\\JAVA_BACKEND\\JAVA PROJECTS\\Phone_Store_Management_HTTTDN\\Phone_Store_Management\\src\\img\\loupe2.png"); // Đặt đường dẫn ảnh ở đây
        Image imgSearch = iconSearch.getImage();
        Image newImgSearch = imgSearch.getScaledInstance(25,25, Image.SCALE_SMOOTH);
        if (iconSearch.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconSearch = new ImageIcon(newImgSearch);

        // Tạo nút Detail
        JButton btnSearch = new JButton(scaledIconSearch);
        btnSearch.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSearch.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setContentAreaFilled(true);
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
        btnSearch.setBounds(540, 18, 45, 45);
        searchLeftPanel.add(btnSearch);
        
        
        
        
        
        //======================= quantityLeftPanel =======================//
        JLabel quantityLabel = new JLabel("Enter quantity: ");
        quantityLabel.setBounds(10, 22, 100, 20);
        quantityLeftPanel.add(quantityLabel);
        
        JTextField quantityTF = new JTextField();
        quantityTF.setBounds(100, 20, 50, 25);
        quantityLeftPanel.add(quantityTF);
        
        JButton quantityButton = new JButton("OK");
        quantityButton.setBounds(180, 20,60,25);
        quantityButton.setOpaque(true);
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

        });
        quantityLeftPanel.add(quantityButton);
        
        JButton newProductButton = new JButton("New product");
        newProductButton.setBounds(250,20,120,25);
        newProductButton.setOpaque(true);
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
        
        ///////////////////////////////// Khu vực thêm các Listener //////////////////////////////////////
        newProductButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newProductDialog();
			}
		});

//========================================================== END LEFT PANEL =================================================================================================//   
        
        
//========================================================== RIGHT PANEL =============================================================================================//
        //Chia 3 panel con informationRightPanel ở phần trên, productChoseRightPanel ở phần giữa, optionRightPanel ở phần dưới;
        JPanel informationRightPanel, productChoseRightPanel, optionRightPanel;
        
        //set thông số cho 3 panel
        informationRightPanel = new JPanel();
        informationRightPanel.setLayout(null);
        informationRightPanel.setBackground(Color.decode("#F7C548"));
        informationRightPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(informationRightPanel, gbc);
        
        productChoseRightPanel = new JPanel();
        productChoseRightPanel.setLayout(new GridBagLayout());
        productChoseRightPanel.setBackground(Color.decode("#3AA7A3"));
        gbc.weightx = 1.0;
        gbc.weighty = 0.72;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(productChoseRightPanel, gbc);
        
        optionRightPanel = new JPanel();
        optionRightPanel.setLayout(new GridBagLayout());
        optionRightPanel.setBackground(Color.decode("#785589"));
        gbc.weightx = 1.0;
        gbc.weighty = 0.08;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        rightPanel.add(optionRightPanel, gbc);
        
        //Ste các labels, textfield để hiện thông tin phiếu nhập
        JLabel slipIdLabel, warehouseIdLabel, creatorIdLabel, supplierLabel;
        slipIdLabel = new JLabel("ID:");
        slipIdLabel.setBounds(10, 20, 30, 20);
        informationRightPanel.add(slipIdLabel);
        warehouseIdLabel = new JLabel("Warehouse ID:");
        warehouseIdLabel.setBounds(10, 50, 100, 20);
        informationRightPanel.add(warehouseIdLabel);
        creatorIdLabel = new JLabel("Creator ID:");
        creatorIdLabel.setBounds(10, 80, 100, 20);
        informationRightPanel.add(creatorIdLabel);
        supplierLabel = new JLabel("Suppliers:");
        supplierLabel.setBounds(10, 110, 100, 20);
        informationRightPanel.add(supplierLabel);
        //Add combobox suppliers
        String [] suppliers = {"", "Add new supplier"};
        supplierComboBox = new JComboBox<String>(suppliers);
        supplierComboBox.setBounds(130,110,200,20); informationRightPanel.add(supplierComboBox);
        supplierComboBox.addItemListener(e -> {
        	if(e.getStateChange() == ItemEvent.SELECTED) {
        		String selected = (String)supplierComboBox.getSelectedItem();
        		if("Add new supplier".equals(selected)) {
        			newSupplierDialog();
        		}
        	}
        });
        
        
        
        JTextField slipIdTF, warehouseIdTF, creatorIdTF;
        slipIdTF = new JTextField();
        slipIdTF.setEditable(false);
        slipIdTF.setBounds(130, 20, 100,20);
        informationRightPanel.add(slipIdTF);
        warehouseIdTF = new JTextField();
        warehouseIdTF.setEditable(false);
        warehouseIdTF.setBounds(130, 50, 100,20);
        informationRightPanel.add(warehouseIdTF);
        creatorIdTF = new JTextField();
        creatorIdTF.setEditable(false);
        creatorIdTF.setBounds(130, 80, 100,20);
        informationRightPanel.add(creatorIdTF);
//========================================================== END RIGHT PANEL =================================================================================================//   

    }
    
    
    	// Hàm hiển thị JDialog để nhập sản phẩm mới
    	private void newProductDialog() {
    	JDialog newProductDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Add New Product", true);
        newProductDialog.setSize(600, 400);
        newProductDialog.setLayout(null);
        
        
        JLabel lblId = new JLabel("Product ID:");
        lblId.setBounds(10,20,100,20); newProductDialog.add(lblId);
        JTextField txtId = new JTextField(); 
        txtId.setEditable(false); //sẽ lấy id mới nhất của bảng sản phẩm trong csdl ra để + thêm 1, k cho nhập tự động
        txtId.setBounds(110, 20, 150, 20); newProductDialog.add(txtId);
        
        JLabel lblImage = new JLabel("Image:");
        lblImage.setBounds(280,20,50,20); newProductDialog.add(lblImage);
        JLabel productImg = new JLabel();
        productImg.setBounds(280, 0, 350, 350); newProductDialog.add(productImg);
        JButton browseButton = new JButton("Browse");
        browseButton.setBounds(340,20,90,20); 
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
        
        JLabel lblName = new JLabel("Product Name:");
        lblName.setBounds(10,45,100,20); newProductDialog.add(lblName);
        JTextField txtName = new JTextField();
        txtName.setBounds(110, 45, 150, 20); newProductDialog.add(txtName);
        
        JLabel lblBrand = new JLabel("Brand:");
        lblBrand.setBounds(10,70,100,20); newProductDialog.add(lblBrand);
        String[] brands = {"Samsung", "Apple", "Xiaomi", "Add new brand"};	//"Samsung", "Apple", "Xiaomi" là các brand thêm vào để khởi đầu thôi, còn khi kết nối csdl rồi thì khi thêm brand mới sẽ thêm vào csdl
        brandComboBox = new JComboBox<String>(brands);
        brandComboBox.setBounds(110,70,150,20); newProductDialog.add(brandComboBox);
        brandComboBox.addItemListener(e -> {
        	if(e.getStateChange() == ItemEvent.SELECTED) {
        		String selected = (String)brandComboBox.getSelectedItem();
        		if("Add new brand".equals(selected)) {
        			newBrandDialog();
        		}
        	}
        });
        
        JLabel lblBattery = new JLabel("Battery capacity:");
        lblBattery.setBounds(10,95,100,20); newProductDialog.add(lblBattery);
        JTextField txtBattery = new JTextField();
        txtBattery.setBounds(110, 95, 150, 20); newProductDialog.add(txtBattery);
        
        JLabel lblOS = new JLabel("OS:");
        lblOS.setBounds(10,120,100,20); newProductDialog.add(lblOS);
        JTextField txtOS = new JTextField();
        txtOS.setBounds(110, 120, 150, 20); newProductDialog.add(txtOS);
       
        JLabel lblOrigin = new JLabel("Origin:");
        lblOrigin.setBounds(10,145,100,20); newProductDialog.add(lblOrigin);
        JTextField txtOrigin = new JTextField();
        txtOrigin.setBounds(110, 145, 150, 20); newProductDialog.add(txtOrigin);
        
        JLabel lblFrontCam = new JLabel("Front Cam:");
        lblFrontCam.setBounds(10,170,100,20); newProductDialog.add(lblFrontCam);
        JTextField txtFrontCam = new JTextField();
        txtFrontCam.setBounds(110, 170, 150, 20); newProductDialog.add(txtFrontCam);
        
        JLabel lblBackCam = new JLabel("Back Cam:");
        lblBackCam.setBounds(10,195,100,20); newProductDialog.add(lblBackCam);
        JTextField txtBackCam = new JTextField();
        txtBackCam.setBounds(110, 195, 150, 20); newProductDialog.add(txtBackCam);
        
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(10,220,100,20); newProductDialog.add(lblPrice);
        JTextField txtPrice = new JTextField();
        txtPrice.setBounds(110, 220, 150, 20); newProductDialog.add(txtPrice);
        
        JLabel lbStatus = new JLabel("Status:");
        lbStatus.setBounds(10,245,100,20); newProductDialog.add(lbStatus);
        JRadioButton rbOn = new JRadioButton("On");
        rbOn.setBounds(110, 245, 50, 20); newProductDialog.add(rbOn);
        JRadioButton rbOff = new JRadioButton("Off");
        rbOff.setBounds(160, 245, 70, 20); newProductDialog.add(rbOff);
        
        
        JButton btnSave = new JButton("Save");
        btnSave.setBounds(300,320, 70,25); 
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

            if (name.isEmpty() || brand.isEmpty() || battery.isEmpty() || os.isEmpty() || origin.isEmpty() || frontCam.isEmpty() || backCam.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(newProductDialog, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                // Thêm vào danh sách sản phẩm (có thể gọi ProductsBUS để xử lý)
                JOptionPane.showMessageDialog(newProductDialog, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(380, 320, 80,25);
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
				productImg.setIcon(null); //xóa ảnh vừa browse
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
    	String newBrand = JOptionPane.showInputDialog(this, "Enter new brand:", "Add Brand", JOptionPane.PLAIN_MESSAGE);

        if (newBrand != null && !newBrand.trim().isEmpty()) {
            brandComboBox.insertItemAt(newBrand, brandComboBox.getItemCount() - 1); // Thêm vào trước "Add new brand"
            brandComboBox.setSelectedItem(newBrand);
        }
    }
    
    public void newSupplierDialog() {
        // Tạo panel chứa form nhập
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5)); //row:3, column:2, hgap:5, wgap:5

        JLabel nameLabel = new JLabel("Supplier Name:");
        JTextField nameField = new JTextField(15);
        
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(15);

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(15);

        // Thêm các thành phần vào panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(phoneLabel);
        panel.add(phoneField);

        // Hiển thị dialog với panel
        int result = JOptionPane.showConfirmDialog(
            this, panel, "Add Supplier", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        // Nếu nhấn OK
        if (result == JOptionPane.OK_OPTION) {
            String newSupplier = nameField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();

            if (!newSupplier.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
                supplierComboBox.insertItemAt(newSupplier, supplierComboBox.getItemCount() - 1);
                supplierComboBox.setSelectedItem(newSupplier);
                JOptionPane.showMessageDialog(this, 
                    "Supplier Added:\nName: " + newSupplier + "\nAddress: " + address + "\nPhone: " + phone);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    

    private void loadSanPhamList() {
    	
    }
}
