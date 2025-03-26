package GUI;


import BUS.SanPhamBUS;
import DTO.*;
import GUI.dashboardApp.raven.forms.DashboardForm;
import raven.popup.GlassPanePopup;
import DAO.SanPhamDAO;


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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.util.UIScale;



public class ThongKeGUI extends JPanel{

<<<<<<< HEAD
	SanPhamBUS productBUS = new SanPhamBUS();
=======
<<<<<<< HEAD
	SanPhamBUS productBUS = new SanPhamBUS();
=======
<<<<<<< HEAD
	SanPhamBUS productBUS = new SanPhamBUS();
=======
<<<<<<< HEAD
	SanPhamBUS productBUS = new SanPhamBUS();
=======
	ProductsBUS productBUS = new ProductsBUS();
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
>>>>>>> f2a8c620b20729783c4ed4c0304242dfb7d6b4f9
>>>>>>> 59a70570a1617ba4a09498c7fd48841322b0010b
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<SanPhamDTO> productArr = new ArrayList<SanPhamDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
    private JComboBox cb;
    private JPanel productContent;
    private JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
    private final boolean UNDECORATED = !true;

	
	//Constructor
    public ThongKeGUI(){
        initComponents();
//        loadSanPhamList();
    }
    
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents() {
        setLayout(new GridBagLayout()); //set Layout
        GridBagConstraints gbc = new GridBagConstraints();
        productContent = new JPanel();
<<<<<<< HEAD
        productContent.setBackground(Color.white);
=======
<<<<<<< HEAD
        productContent.setBackground(Color.white);
=======
<<<<<<< HEAD
        productContent.setBackground(Color.white);
=======
<<<<<<< HEAD
        productContent.setBackground(Color.white);
=======
        productContent.setBackground(Color.green);
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
>>>>>>> f2a8c620b20729783c4ed4c0304242dfb7d6b4f9
>>>>>>> 59a70570a1617ba4a09498c7fd48841322b0010b
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
        productContent.setLayout(new GridBagLayout());
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(productContent, gbc); // Thêm vào ProductsGUI
        
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
        setSize(UIScale.scale(new Dimension(1366, 800)));
//        GlassPanePopup.install(this);

>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
>>>>>>> f2a8c620b20729783c4ed4c0304242dfb7d6b4f9
>>>>>>> 59a70570a1617ba4a09498c7fd48841322b0010b
>>>>>>> 53e252ce0081b140258bbc26b7c778f1cf08c8d0
        // Thay đổi: Thêm trực tiếp DashboardForm vào JFrame
        DashboardForm dashboard = new DashboardForm();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productContent.add(dashboard, gbc);
        // Hiển thị JFrame
        setVisible(true);
        
    }

    
    private void loadSanPhamList() {
    	
    }
}
