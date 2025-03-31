package GUI;

import Components.dashboardApp.raven.forms.DashboardForm;
import com.formdev.flatlaf.FlatClientProperties;
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

//    private final boolean UNDECORATED = !true;
    private JPanel productContent;

    //Constructor
    public StatisticsGUI() {
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
