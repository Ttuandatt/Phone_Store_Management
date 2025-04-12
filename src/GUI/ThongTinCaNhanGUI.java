package GUI;

<<<<<<< HEAD:src/GUI/StatisticsGUI.java
import Components.dashboardApp.raven.forms.DashboardForm;
import com.formdev.flatlaf.FlatClientProperties;
=======
import BUS.SanPhamBUS;
import DTO.*;
import DAO.SanPhamDAO;

import java.awt.BorderLayout;
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/ThongTinCaNhanGUI.java
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import raven.popup.GlassPanePopup;

public class StatisticsGUI extends JPanel {

<<<<<<< HEAD:src/GUI/StatisticsGUI.java
//    private final boolean UNDECORATED = !true;
    private JPanel productContent;

    //Constructor
    public StatisticsGUI() {
=======

public class ThongTinCaNhanGUI extends JPanel{

	SanPhamBUS productBUS = new SanPhamBUS();
    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<SanPhamDTO> productArr = new ArrayList<SanPhamDTO>(); //Tạo ArrayList sp với kiểu là ProductsDTO
    private JComboBox cb;
    private JPanel productContent;
    private JTextField tfTimKiem, tfPriceStart, tfPriceEnd;
	
	
	//Constructor
    public ThongTinCaNhanGUI(){
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/ThongTinCaNhanGUI.java
        initComponents();
        loadSanPhamList();
    }

    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents() {
        setPreferredSize(new Dimension(1366, 800)); // Dùng setPreferredSize() thay vì setSize()
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        productContent = new JPanel();
        productContent.setBackground(Color.WHITE);
        productContent.setLayout(new GridBagLayout());
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(productContent, gbc);

        // Thêm DashboardForm nếu nó là JPanel
        DashboardForm dashboard = new DashboardForm();
        gbc.weightx = 1.0;
<<<<<<< HEAD:src/GUI/StatisticsGUI.java
=======
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productContent.add(topPanel, gbc);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(Color.decode("#D95D39"));
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
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/ThongTinCaNhanGUI.java
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productContent.add(dashboard, gbc);
        //==================================== End searchPanel ====================================================//
//============================================================================================================================================================//   
    }

    private void loadSanPhamList() {

    }

}
