package Components.dashboardApp.test;

import Components.dashboardApp.raven.forms.DashboardForm;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class Test extends JPanel {
    private JPanel statisticsContent;

    // Constructor
    public Test() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Tạo panel chứa thống kê
        statisticsContent = createStatisticsContent();

        // Cấu hình constraints và thêm vào ThongKeGUI
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(statisticsContent, gbc);
    }

    private JPanel createStatisticsContent() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);

        // Thêm DashboardForm vào statisticsContent
        DashboardForm dashboard = new DashboardForm();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dashboard, gbc);

        return panel;
    }
}
