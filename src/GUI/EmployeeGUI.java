package GUI;

import BUS.ProductsBUS;
import DTO.*;
import DAO.ProductsDAO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;



public class EmployeeGUI extends JPanel{

	ProductsBUS productBUS = new ProductsBUS();
    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<ProductsDTO> productArr = new ArrayList<ProductsDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
    private JComboBox cb;
    private JPanel productContent;
    private JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
	
	
	//Constructor
    public EmployeeGUI(){
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
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productContent.add(topPanel, gbc);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(Color.decode("#785BD7"));
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
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
        leftFunctionPanel.setBackground(Color.yellow);
        leftFunctionPanel.setLayout(new FlowLayout());
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        functionsPanel.add(leftFunctionPanel, gbc);
        
        rightFunctionPanel = new JPanel();
        rightFunctionPanel.setBackground(Color.decode("#3A96CF"));
        rightFunctionPanel.setLayout(new GridBagLayout());
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        functionsPanel.add(rightFunctionPanel, gbc);
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
        gbc.weightx = 0.8;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(searchInputPanel, gbc);
        
        searchButtonPanel = new JPanel();
        searchButtonPanel.setBackground(Color.decode("#A8A8F0"));
        searchButtonPanel.setLayout(new GridBagLayout());
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(searchButtonPanel, gbc);
        //==================================== End searchPanel ====================================================//

//============================================================================================================================================================//   
        
    }

    
    private void loadSanPhamList() {
    	
    }
}
