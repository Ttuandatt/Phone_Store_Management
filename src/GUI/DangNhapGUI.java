package GUI;

import BUS.DangNhapBUS;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;

public class DangNhapGUI extends JFrame implements KeyListener {
    JPanel pnlLogIn, pnLeft, pnRight, pnRightTop, pnRightMid, pnRightBottom;
    private DangNhapBUS dnBUS = new DangNhapBUS();
    private String maNV;
    private String matKhau;
    JLabel lblImage, lblLoginBanner;
    InputFormGUI txtMaNV;
    InputFormGUI txtMatKhau;
    Color FontColor = new Color(96, 125, 139);

    public DangNhapGUI() {
        initComponent();
        txtMaNV.setText("");
        txtMatKhau.setPass("");
    }

    private void initComponent() {
        FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
        UIManager.put("PasswordField.showRevealButton", true);
        this.setBackground(Color.WHITE);
        this.setSize(new Dimension(1000, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle("Login");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Left panel with image
        pnLeft = new JPanel();
        pnLeft.setPreferredSize(new Dimension(500, 500));
        pnLeft.setLayout(new BorderLayout());
        lblImage = new JLabel(new FlatSVGIcon("./Images/login2.svg"));
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        pnLeft.add(lblImage, BorderLayout.CENTER);
        this.add(pnLeft, BorderLayout.WEST);

        // Right panel with form
        pnRight = new JPanel(new GridBagLayout());
        pnRight.setBackground(Color.WHITE);
        pnRight.setPreferredSize(new Dimension(500, 500));
        this.add(pnRight, BorderLayout.EAST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;

        // RightTop panel
        pnRightTop = new JPanel(new BorderLayout());
        pnRightTop.setBackground(Color.WHITE);
        lblLoginBanner = new JLabel("LOGIN", SwingConstants.CENTER);
        lblLoginBanner.setFont(new Font(FlatRobotoFont.FAMILY_SEMIBOLD, Font.BOLD, 24));
        pnRightTop.add(lblLoginBanner, BorderLayout.CENTER);
        pnRight.add(pnRightTop, gbc);

        // RightMid panel
        gbc.gridy = 1;
        gbc.weighty = 0.6;
        pnRightMid = new JPanel(null);
        pnRightMid.setBackground(Color.WHITE);
        
        txtMaNV = new InputFormGUI("Username");
        txtMaNV.setBounds(50,20,400,80);
        pnRightMid.add(txtMaNV);
        txtMatKhau = new InputFormGUI("Password", "password");
        txtMatKhau.setBounds(50,90,400,80);
        pnRightMid.add(txtMatKhau);


        pnRight.add(pnRightMid, gbc);

        // RightBottom panel
        gbc.gridy = 2;
        gbc.weighty = 0.2;
        pnRightBottom = new JPanel(null);
        pnRightBottom.setBackground(Color.WHITE);
        pnlLogIn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        pnlLogIn.putClientProperty(FlatClientProperties.STYLE, "arc: 99");
        pnlLogIn.setBackground(Color.BLACK);
        pnlLogIn.setBounds(160,20,200,40);
        pnlLogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                pnlLogInMouseEntered(evt);
                pnlLogIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    login();
                } catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                try {
                    pnlLogInMousePressed(evt);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(DangNhapGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                pnlLogInMouseExited(evt);
            }
        });
        
        JLabel lblLogin = new JLabel("LOGIN", SwingConstants.CENTER);
        lblLogin.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16));
        lblLogin.setForeground(Color.white);
        pnlLogIn.add(lblLogin);
        pnRightBottom.add(pnlLogIn);
        pnRight.add(pnRightBottom, gbc);

        // Add Key Listeners
        txtMaNV.getTxtForm().addKeyListener(this);
        txtMatKhau.getTxtPass().addKeyListener(this);
    }

    private void login() throws UnsupportedLookAndFeelException {
        maNV = txtMaNV.getText();
        matKhau = txtMatKhau.getPass();
        dnBUS.kiemTraDangNhap(maNV, matKhau, this);
    }

    public void closeLoginFrame() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pnRight); // Lấy JFrame cha của panelRight
        frame.dispose(); // Đóng frame đăng nhập
    }

    private void pnlLogInMousePressed(MouseEvent evt) throws UnsupportedLookAndFeelException {
    }

    private void pnlLogInMouseEntered(MouseEvent evt) {
        pnlLogIn.setBackground(FontColor);
        pnlLogIn.setForeground(Color.black);
    }

    private void pnlLogInMouseExited(MouseEvent evt) {
        pnlLogIn.setBackground(Color.BLACK);
        pnlLogIn.setForeground(Color.white);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                login();
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        }
    }

}
