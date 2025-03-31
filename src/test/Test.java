package test;

import Components.dashboardApp.raven.forms.DashboardForm;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.UIManager;

import raven.popup.GlassPanePopup;

public class Test extends JFrame {

    private final boolean UNDECORATED = !true;

    public Test() {
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIScale.scale(new Dimension(1366, 800)));
        setLocationRelativeTo(null);
        if (UNDECORATED) {
            setUndecorated(UNDECORATED);
            setBackground(new Color(0, 0, 0, 0));
        } else {
            getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        }
        setTitle("Chart tổng hợp Test");
        GlassPanePopup.install(this);
        
        // Thay đổi: Thêm trực tiếp DashboardForm vào JFrame
        DashboardForm dashboard = new DashboardForm();
        setContentPane(dashboard);

        // Hiển thị JFrame
        setVisible(true);
        
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
//        FlatLaf.registerCustomDefaultsSource("raven.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup(); // giao diện giống macOS : sáng và tối FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new Test().setVisible(true));
    }
}
