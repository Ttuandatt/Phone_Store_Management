package GUI;

import BUS.ProductsBUS;
import DTO.*;
import DAO.ProductsDAO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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



public class ProductsGUI extends JPanel{

	ProductsBUS productBUS = new ProductsBUS();
    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<ProductsDTO> productArr = new ArrayList<ProductsDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
    private JComboBox cb;
    private JPanel productContent;
    private JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
	
	
	//Constructor
    public ProductsGUI(){
        initComponents();
        loadSanPhamList();
    }
    
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents() {
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
        
        //tạo 2 panel topPanel, bottomPanel cho khu vực tìm kiếm và khu vực hiển thị bảng danh sách
        JPanel topPanel, bottomPanel;
        //set thông số cho 2 panel
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(Color.blue);
        gbc.weightx = 1.0;
        gbc.weighty = 0.11;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productContent.add(topPanel, gbc);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(Color.magenta);
        gbc.weightx = 1.0;
        gbc.weighty = 0.89;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        productContent.add(bottomPanel, gbc);
        
//==================================================== TOP PANEL =============================================================================================//
        JPanel functionsPanel, searchPanel;
        
        
        //======================================= functionsPanel =====================================================//
        //set thông số cho functionsPanel
        functionsPanel = new JPanel();
        functionsPanel.setBackground(Color.lightGray);
        functionsPanel.setLayout(new GridBagLayout());
        functionsPanel.setBorder(BorderFactory.createTitledBorder("Functions"));	//Tạo border cho panel
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(functionsPanel,gbc);
        
        //chia 2 panel con nữa, leftFunctionPanel cho các nút chức năng, rightFunctionPanel cho nút xuất Excel
        JPanel leftFunctionPanel, rightFunctionPanel;
        
        leftFunctionPanel = new JPanel();
        leftFunctionPanel.setBackground(Color.decode("#1D2D44"));
        leftFunctionPanel.setLayout(null);
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        functionsPanel.add(leftFunctionPanel, gbc);
        
        rightFunctionPanel = new JPanel();
        rightFunctionPanel.setBackground(Color.decode("#3A96CF"));
        rightFunctionPanel.setLayout(null);
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        functionsPanel.add(rightFunctionPanel, gbc);
        
        //Chia tiếp các panel con để chứa các nút chức năng ở leftFunctionPanel
        JPanel updateButtonPanel, deleteButtonPanel, detailButtonPanel, excelButtonPanel;
        
        updateButtonPanel = new JPanel();
        updateButtonPanel.setBackground(Color.white);
        updateButtonPanel.setBounds(20, 7, 70, 70);
        leftFunctionPanel.add(updateButtonPanel);
        
        deleteButtonPanel = new JPanel();
        deleteButtonPanel.setBackground(Color.white);
        deleteButtonPanel.setBounds(94, 7, 70, 70);
        leftFunctionPanel.add(deleteButtonPanel);
        
        detailButtonPanel = new JPanel();
        detailButtonPanel.setBackground(Color.white);
        detailButtonPanel.setBounds(168, 7, 70, 70);
        leftFunctionPanel.add(detailButtonPanel);
        
        excelButtonPanel = new JPanel();
        excelButtonPanel.setBackground(Color.white);
        excelButtonPanel.setBounds(20, 7, 70, 70);
        rightFunctionPanel.add(excelButtonPanel);
        
        //======================================= Đặt các nút chức năng vào các panel ==========================================================//
        //======================================== Nút update ====================================//
        //Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
        ImageIcon iconUpdate = new ImageIcon("C:\\\\Users\\\\ACER\\\\Dropbox\\\\My PC (LAPTOP-UGP9QJUT)\\\\Documents\\\\ITstudies\\\\JAVA_BACKEND\\\\JAVA PROJECTS\\\\Phone_Store_Management_HTTTDN\\\\Phone_Store_Management\\\\src\\\\img\\\\update.png\\"); // Đặt đường dẫn ảnh ở đây
        Image imgUpdate = iconUpdate.getImage();
        Image newImgUpdate = imgUpdate.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        if (iconUpdate.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconUpdate = new ImageIcon(newImgUpdate);

        // Tạo nút Update
        JButton btnUpdate = new JButton("Update", scaledIconUpdate);
        btnUpdate.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnUpdate.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 10)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Update
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Update button clicked!");
            }
        });

        // Thêm nút vào panel
        updateButtonPanel.setLayout(new BorderLayout());
        updateButtonPanel.add(btnUpdate, BorderLayout.CENTER);
        //========================================== End nút update ==============================//
        
        
        //======================================== Nút delete ====================================//
        //Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
        ImageIcon iconDelete = new ImageIcon("C:\\\\Users\\\\ACER\\\\Dropbox\\\\My PC (LAPTOP-UGP9QJUT)\\\\Documents\\\\ITstudies\\\\JAVA_BACKEND\\\\JAVA PROJECTS\\\\Phone_Store_Management_HTTTDN\\\\Phone_Store_Management\\\\src\\\\img\\\\delete.png\\"); // Đặt đường dẫn ảnh ở đây
        Image imgDelete = iconDelete.getImage();
        Image newImgDelete = imgDelete.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        if (iconDelete.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconDelete = new ImageIcon(newImgDelete);

        // Tạo nút Delete
        JButton btnDelete = new JButton("Delete", scaledIconDelete);
        btnDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 10)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Delete
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Delete button clicked!");
            }
        });

        // Thêm nút vào panel
        deleteButtonPanel.setLayout(new BorderLayout());
        deleteButtonPanel.add(btnDelete, BorderLayout.CENTER);
        //========================================== End nút delete ===========================//
        
        
        //======================================== Nút detail ====================================//
        //Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
        ImageIcon iconDetail = new ImageIcon("C:\\\\Users\\\\ACER\\\\Dropbox\\\\My PC (LAPTOP-UGP9QJUT)\\\\Documents\\\\ITstudies\\\\JAVA_BACKEND\\\\JAVA PROJECTS\\\\Phone_Store_Management_HTTTDN\\\\Phone_Store_Management\\\\src\\\\img\\\\info.png\\"); // Đặt đường dẫn ảnh ở đây
        Image imgDetail = iconDetail.getImage();
        Image newImgDetail = imgDetail.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        if (iconDelete.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconDetail = new ImageIcon(newImgDetail);

        // Tạo nút Detail
        JButton btnDetail = new JButton("Info", scaledIconDetail);
        btnDetail.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnDetail.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDetail.setFocusPainted(false);
        btnDetail.setBorderPainted(false);
        btnDetail.setContentAreaFilled(false);
        btnDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetail.setFont(new Font("Arial", Font.BOLD, 10)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Detail
        btnDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Info button clicked!");
            }
        });

        // Thêm nút vào panel
        detailButtonPanel.setLayout(new BorderLayout());
        detailButtonPanel.add(btnDetail, BorderLayout.CENTER);
        //========================================== End nút detail ===========================//
        
        //Nút Xuất Excel
        //Tạo icon (cần đảm bảo đường dẫn hình ảnh đúng)
        ImageIcon iconExcel = new ImageIcon("C:\\\\Users\\\\ACER\\\\Dropbox\\\\My PC (LAPTOP-UGP9QJUT)\\\\Documents\\\\ITstudies\\\\JAVA_BACKEND\\\\JAVA PROJECTS\\\\Phone_Store_Management_HTTTDN\\\\Phone_Store_Management\\\\src\\\\img\\\\excel.png\\"); // Đặt đường dẫn ảnh ở đây
        Image imgExcel = iconExcel.getImage();
        Image newImgExcel = imgExcel.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        if (iconExcel.getIconWidth() == -1) {
            System.out.println("Không tìm thấy ảnh!");
        }
        ImageIcon scaledIconExcel = new ImageIcon(newImgExcel);

        // Tạo nút Detail
        JButton btnExcel = new JButton("Excel", scaledIconExcel);
        btnExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExcel.setFocusPainted(false);
        btnExcel.setBorderPainted(false);
        btnExcel.setContentAreaFilled(false);
        btnExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExcel.setFont(new Font("Arial", Font.BOLD, 10)); // Đặt kích cỡ chữ là 10

        // Thêm sự kiện click cho nút Detail
        btnExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Excel button clicked!");
            }
        });

        // Thêm nút vào panel
        excelButtonPanel.setLayout(new BorderLayout());
        excelButtonPanel.add(btnExcel, BorderLayout.CENTER);
        
        
        //==================================== End functionsPanel ====================================================//
        
        //======================================= seacrhPanel ========================================================//
        //set thông số cho seacrhPanel
        searchPanel = new JPanel();
        searchPanel.setBackground(Color.orange);
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));	//Tạo border cho panel
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        topPanel.add(searchPanel,gbc);
        
        //chia 2 panel con nữa: searchInputPanel & searchButtonPanel
        JPanel searchInputPanel, searchButtonPanel;
        
        searchInputPanel = new JPanel();
        searchInputPanel.setBackground(Color.decode("#717568"));
        searchInputPanel.setLayout(new GridBagLayout());
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(searchInputPanel, gbc);
        
        searchButtonPanel = new JPanel();
        searchButtonPanel.setBackground(Color.decode("#A8A8F0"));
        searchButtonPanel.setLayout(new GridBagLayout());
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(searchButtonPanel, gbc);
        //==================================== End searchPanel ====================================================//

//=====================================================END TOP PANEL =====================================================================================================//   
        
        
//==================================================== BOTTOM PANEL =============================================================================================//
        
        
//===================================================== END BOTTOM PANEL =======================================================================================================//   

        
    }

    
    private void loadSanPhamList() {
    	
    }
}
