
package GUI;

import BUS.BangChamCongBUS;
import BUS.ChucVuBUS;
import BUS.NhanVienBUS;
import DTO.BangChamCongDTO;
import DTO.BangLuongDTO;
import DTO.ChucVuDTO;
import DTO.NhanVienDTO;
import com.sun.javafx.logging.PlatformLogger.Level;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public final class ChiTietBangLuongGUI extends JFrame {
    private JLabel lb_nhanvien, lb_chucvu, lb_luongcb, lb_heso, lb_bangluong;
    private JLabel jl_nhanvien, jl_chucvu, jl_luongcb, jl_heso;
    private JPanel jp_ttnv, jp_ctl, jp_main;
    private JLabel lb_luongtt, lb_luongot, lb_phucap, lb_thuong, lb_khoantru, lb_thuclinh, lb_tongtn, lb_soNL, lb_thanhtien;
    private JLabel lb_luongtt1, lb_luongot1, lb_luongot2, lb_luongot3, lb_phucap1, lb_phucap2, 
            lb_khoantru1, lb_khoantru2, lb_khoantru3, lb_khoantru4, lb_khoantru5, lb_soNL1, lb_soNL2, lb_soNL3, lb_soNL4, lb_soNL5, lb_soNL6;
    private JTextField tf_luongtt1, tf_luongot1, tf_luongot2, tf_luongot3, tf_phucap1, tf_phucap2, 
            tf_thuong, tf_khoantru1, tf_khoantru2, tf_khoantru3, tf_khoantru4, tf_khoantru5, tf_tongtn, tf_thuclinh;
    private JButton btn_chinhsua, btn_luu, btn_duyet;
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private BangChamCongBUS bccBUS = new BangChamCongBUS();
    private ChucVuBUS cvBUS = new ChucVuBUS();
    
    public ChiTietBangLuongGUI(BangLuongDTO bangLuong) {
        NhanVienDTO nv = nvBUS.selectById(bangLuong.getMaNV());
        String mabcc= bangLuong.getMaLuong().replaceFirst("BL", "CC");
        BangChamCongDTO bcc = bccBUS.selectById(mabcc);
        ChucVuDTO cv = cvBUS.selectById(nv.getChucVu());
        
        setTitle("CHI TIẾT BẢNG LƯƠNG");
        setSize(900, 700);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);  // Không sử dụng Layout Manager
        
        jp_main = new JPanel();
        jp_main.setLayout(null);
        jp_main.setBounds(0, 0, 900, 700);
        jp_main.setBackground(Color.WHITE);
        add(jp_main);
        
        Font titleFont = new Font("Serif", Font.BOLD, 20);
        Font labelFont = new Font("SansSerif", Font.BOLD, 15);
        Font tfFont = new Font("SansSerif", Font.PLAIN, 15);
        
        lb_bangluong = new JLabel("BẢNG LƯƠNG", SwingConstants.CENTER);
        lb_bangluong.setFont(titleFont);
        lb_bangluong.setBounds(300, 10, 300, 30);
        jp_main.add(lb_bangluong);

        // Thông tin chung
        jp_ttnv = new JPanel();
        jp_ttnv.setLayout(null); // hoặc dùng layout khác nếu cần
        TitledBorder border = BorderFactory.createTitledBorder("Thông tin nhân viên");
        border.setTitleFont(new Font("Tahoma", Font.BOLD, 16));
        jp_ttnv.setBorder(border);
        jp_ttnv.setBounds(20, 60, 850, 120);
        jp_ttnv.setBackground(Color.white);
        jp_main.add(jp_ttnv);
        
        lb_nhanvien = new JLabel("Nhân viên:");
        lb_nhanvien.setFont(labelFont);
        
        lb_nhanvien.setBounds(50, 25, 100, 30);
        jp_ttnv.add(lb_nhanvien);

        jl_nhanvien = new JLabel("000");
        jl_nhanvien.setBounds(150, 25, 160, 30);
        jl_nhanvien.setFont(tfFont);
        jp_ttnv.add(jl_nhanvien);

        lb_chucvu = new JLabel("Chức vụ:");
        lb_chucvu.setFont(labelFont);
        lb_chucvu.setBounds(430, 25, 160, 30);
        jp_ttnv.add(lb_chucvu);

        jl_chucvu = new JLabel("000");
        jl_chucvu.setBounds(520, 25, 200, 30);
        jl_chucvu.setFont(tfFont);
        jp_ttnv.add(jl_chucvu);

        lb_luongcb = new JLabel("Lương cơ bản:");
        lb_luongcb.setFont(labelFont);
        lb_luongcb.setBounds(50, 65, 200, 30);
        jp_ttnv.add(lb_luongcb);

        jl_luongcb = new JLabel("000");
        jl_luongcb.setBounds(180, 65, 200, 30);
        jl_luongcb.setFont(tfFont);
        jp_ttnv.add(jl_luongcb);

        lb_heso = new JLabel("Hệ số:");
        lb_heso.setFont(labelFont);
        lb_heso.setBounds(450, 65, 160, 30);
        jp_ttnv.add(lb_heso);

        jl_heso = new JLabel("000");
        jl_heso.setBounds(520, 65, 150, 30);
        jl_heso.setFont(tfFont);
        jp_ttnv.add(jl_heso);
        
        ///////////////////// Chi tiết lương
        jp_ctl = new JPanel();
        jp_ctl.setLayout(null); // hoặc dùng layout khác nếu cần
        TitledBorder border1 = BorderFactory.createTitledBorder("Chi tiết lương");
        border1.setTitleFont(new Font("Tahoma", Font.BOLD, 16));
        jp_ctl.setBorder(border1);
        jp_ctl.setBounds(20, 200, 850, 250);
        jp_ctl.setPreferredSize(new Dimension(900, 600));
        jp_ctl.setBackground(Color.white);
        JScrollPane scrollPane = new JScrollPane(jp_ctl);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(20, 200, 850, 250); // Đặt vị trí và kích thước
        scrollPane.getViewport().setBackground(Color.white);
        jp_main.add(scrollPane);
        
        lb_soNL = new JLabel("Số ngày làm");
        lb_soNL.setFont(labelFont);
        lb_soNL.setBounds(510, 15, 150, 30);
        jp_ctl.add(lb_soNL);

        lb_thanhtien = new JLabel("Thành tiền");
        lb_thanhtien.setBounds(705, 15, 160, 30);
        lb_thanhtien.setFont(labelFont);
        jp_ctl.add(lb_thanhtien);
        
        //
        lb_luongtt = new JLabel("1. Lương thực tế:");
        lb_luongtt.setFont(labelFont);
        lb_luongtt.setBounds(30, 50, 160, 30);
        jp_ctl.add(lb_luongtt);

        lb_luongtt1 = new JLabel("Lương ngày thường");
        lb_luongtt1.setBounds(200, 50, 160, 25);
        lb_luongtt1.setFont(tfFont);
        jp_ctl.add(lb_luongtt1);
        
        tf_luongtt1 = new JTextField(" ");
        tf_luongtt1.setBounds(680, 50, 130, 25);
        tf_luongtt1.setFont(tfFont);
        tf_luongtt1.setEditable(false);
        jp_ctl.add(tf_luongtt1);
        
        lb_soNL1 = new JLabel("000");
        lb_soNL1.setBounds(510, 50, 100, 25);
        lb_soNL1.setHorizontalAlignment(SwingConstants.CENTER);
        lb_soNL1.setFont(tfFont);
        jp_ctl.add(lb_soNL1);
        
        //
        lb_luongot = new JLabel("2. Lương tăng ca:");
        lb_luongot.setFont(labelFont);
        lb_luongot.setBounds(30, 90, 160, 25);
        jp_ctl.add(lb_luongot);
        
        lb_luongot1 = new JLabel("Lương tăng ca ngày thường (Hệ số 1.0)");
        lb_luongot1.setBounds(200, 90, 270, 25);
        lb_luongot1.setFont(tfFont);
        jp_ctl.add(lb_luongot1);
        
        tf_luongot1 = new JTextField("");
        tf_luongot1.setBounds(680, 90, 130, 25);
        tf_luongot1.setFont(tfFont);
        tf_luongot1.setEditable(false);
        jp_ctl.add(tf_luongot1);  
        
        lb_soNL2 = new JLabel("000");
        lb_soNL2.setBounds(510, 90, 100, 25);
        lb_soNL2.setHorizontalAlignment(SwingConstants.CENTER);
        lb_soNL2.setFont(tfFont);
        jp_ctl.add(lb_soNL2);
        
        lb_luongot2 = new JLabel("Lương tăng ca ngày lễ (Hệ số 3.0)");
        lb_luongot2.setBounds(200, 130, 250, 25);
        lb_luongot2.setFont(tfFont);
        jp_ctl.add(lb_luongot2);
        
        tf_luongot2 = new JTextField("");
        tf_luongot2.setBounds(680, 130, 130, 25);
        tf_luongot2.setFont(tfFont);
        tf_luongot2.setEditable(false);
        jp_ctl.add(tf_luongot2);
        
        lb_soNL3 = new JLabel("000");
        lb_soNL3.setBounds(510, 130, 100, 25);
        lb_soNL3.setHorizontalAlignment(SwingConstants.CENTER);
        lb_soNL3.setFont(tfFont);
        jp_ctl.add(lb_soNL3);
        
        lb_luongot3 = new JLabel("Lương tăng ca chủ nhật (Hệ số 2.0)");
        lb_luongot3.setBounds(200, 170, 250, 25);
        lb_luongot3.setFont(tfFont);
        jp_ctl.add(lb_luongot3);
        
        tf_luongot3 = new JTextField("");
        tf_luongot3.setBounds(680, 170, 130, 25);
        tf_luongot3.setFont(tfFont);
        tf_luongot3.setEditable(false);
        jp_ctl.add(tf_luongot3);
        
        lb_soNL4 = new JLabel("000");
        lb_soNL4.setBounds(510, 170, 100, 25);
        lb_soNL4.setHorizontalAlignment(SwingConstants.CENTER);
        lb_soNL4.setFont(tfFont);
        jp_ctl.add(lb_soNL4);
        
        //
        lb_phucap = new JLabel("3. Phụ cấp:");
        lb_phucap.setFont(labelFont);
        lb_phucap.setBounds(30, 210, 150, 25);
        jp_ctl.add(lb_phucap);

        lb_phucap1 = new JLabel("Phụ cấp cơm trưa");
        lb_phucap1.setFont(tfFont);
        lb_phucap1.setBounds(200, 210, 150, 25);
        jp_ctl.add(lb_phucap1);
        
        tf_phucap1 = new JTextField("");
        tf_phucap1.setBounds(680, 210, 130, 25);
        tf_phucap1.setFont(tfFont);
        tf_phucap1.setEditable(false);
        jp_ctl.add(tf_phucap1);
        
        lb_soNL5 = new JLabel("000");
        lb_soNL5.setBounds(510, 210, 100, 25);
        lb_soNL5.setHorizontalAlignment(SwingConstants.CENTER);
        lb_soNL5.setFont(tfFont);
        jp_ctl.add(lb_soNL5);
        
        lb_phucap2 = new JLabel("Phụ cấp đi lại");
        lb_phucap2.setFont(tfFont);
        lb_phucap2.setBounds(200, 250, 150, 25);
        jp_ctl.add(lb_phucap2);
        
        tf_phucap2 = new JTextField("");
        tf_phucap2.setBounds(680, 250, 130, 25);
        tf_phucap2.setFont(tfFont);
        tf_phucap2.setEditable(false);
        jp_ctl.add(tf_phucap2);
        
        lb_soNL6 = new JLabel("000");
        lb_soNL6.setBounds(510, 250, 100, 25);
        lb_soNL6.setHorizontalAlignment(SwingConstants.CENTER);
        lb_soNL6.setFont(tfFont);
        jp_ctl.add(lb_soNL6);
        
        //
        lb_thuong = new JLabel("4. Lương thưởng:");
        lb_thuong.setFont(labelFont);
        lb_thuong.setBounds(30, 290, 160, 25);
        jp_ctl.add(lb_thuong);

        tf_thuong = new JTextField("");
        tf_thuong.setBounds(680, 290, 130, 25);
        tf_thuong.setFont(tfFont);
        //tf_thuong.setEditable(false);
        jp_ctl.add(tf_thuong);
        
        //
        lb_khoantru = new JLabel("5. Các khoản trừ:");
        lb_khoantru.setFont(labelFont);
        lb_khoantru.setBounds(30, 330, 160, 25);
        jp_ctl.add(lb_khoantru);  
        
        lb_khoantru1 = new JLabel("Thuế thu nhập cá nhân");
        lb_khoantru1.setFont(tfFont);
        lb_khoantru1.setBounds(200, 330, 160, 25);
        jp_ctl.add(lb_khoantru1); 
        
        lb_khoantru2 = new JLabel("Bảo hiểm y tế");
        lb_khoantru2.setFont(tfFont);
        lb_khoantru2.setBounds(200, 370, 160, 25);
        jp_ctl.add(lb_khoantru2); 

        lb_khoantru3 = new JLabel("Bảo hiểm xã hội");
        lb_khoantru3.setFont(tfFont);
        lb_khoantru3.setBounds(200, 410, 160, 25);
        jp_ctl.add(lb_khoantru3); 
        
        lb_khoantru4 = new JLabel("Bảo hiểm tai nạn");
        lb_khoantru4.setFont(tfFont);
        lb_khoantru4.setBounds(200, 450, 160, 25);
        jp_ctl.add(lb_khoantru4); 
        
        lb_khoantru5 = new JLabel("Tạm ứng");
        lb_khoantru5.setFont(tfFont);
        lb_khoantru5.setBounds(200, 490, 160, 25);
        jp_ctl.add(lb_khoantru5); 
        
        tf_khoantru1 = new JTextField("");
        tf_khoantru1.setBounds(680, 330, 130, 25);
        tf_khoantru1.setFont(tfFont);
        //tf_khoantru1.setEditable(false);
        jp_ctl.add(tf_khoantru1);

        tf_khoantru2 = new JTextField("");
        tf_khoantru2.setBounds(680, 370, 130, 25);
        tf_khoantru2.setFont(tfFont);
        //tf_khoantru2.setEditable(false);
        jp_ctl.add(tf_khoantru2);
        
        tf_khoantru3 = new JTextField("");
        tf_khoantru3.setBounds(680, 410, 130, 25);
        tf_khoantru3.setFont(tfFont);
        //tf_khoantru3.setEditable(false);
        jp_ctl.add(tf_khoantru3);

        tf_khoantru4 = new JTextField("");
        tf_khoantru4.setBounds(680, 450, 130, 25);
        tf_khoantru4.setFont(tfFont);
        //tf_khoantru4.setEditable(false);
        jp_ctl.add(tf_khoantru4);    
        
        tf_khoantru5 = new JTextField("");
        tf_khoantru5.setBounds(680, 490, 130, 25);
        tf_khoantru5.setFont(tfFont);
        //tf_khoantru4.setEditable(false);
        jp_ctl.add(tf_khoantru5);

        //
        lb_tongtn = new JLabel("6. Tổng thu nhập thực tế (1)+(2)+(3)+(4)");
        lb_tongtn.setFont(labelFont);
        lb_tongtn.setBounds(30, 530, 300, 25);
        jp_ctl.add(lb_tongtn); 

        tf_tongtn = new JTextField("");
        tf_tongtn.setBounds(680, 530, 130, 25);
        tf_tongtn.setFont(tfFont);
        tf_tongtn.setEditable(false);
        jp_ctl.add(tf_tongtn);
        
        //
        lb_thuclinh = new JLabel("7. Lương thực lĩnh (6)-(5)");
        lb_thuclinh.setFont(labelFont);
        lb_thuclinh.setBounds(30, 570, 200, 25);
        jp_ctl.add(lb_thuclinh); 

        tf_thuclinh = new JTextField("");
        tf_thuclinh.setBounds(680, 570, 130, 25);
        tf_thuclinh.setFont(tfFont);
        tf_thuclinh.setEditable(false);
        jp_ctl.add(tf_thuclinh);
        
        jp_ctl.revalidate();
        jp_ctl.repaint();
        
        // Các nút
        JButton btn_chinhsua = new JButton("Lưu bảng lương");
        btn_chinhsua.setBounds(200, 490, 130, 35);
        btn_chinhsua.setFont(new Font("Arial", Font.BOLD, 14));
        btn_chinhsua.setForeground(Color.white);
        btn_chinhsua.setBackground(Color.decode("#37A4F2"));
        btn_chinhsua.setBorderPainted(false);
        btn_chinhsua.setFocusPainted(false);
        btn_chinhsua.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        jp_main.add(btn_chinhsua);
        btn_chinhsua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnChinhSuaActionPerformed(ae);
            } 
        });
        
        btn_duyet = new JButton("Duyệt bảng lương");
        btn_duyet.setBounds(500, 490, 130, 35);
        btn_duyet.setFont(new Font("Arial", Font.BOLD, 14));
        btn_duyet.setForeground(Color.white);
        btn_duyet.setBackground(Color.decode("#37A4F2"));
        btn_duyet.setBorderPainted(false);
        btn_duyet.setFocusPainted(false);
        btn_duyet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jp_main.add(btn_duyet);
        btn_duyet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnDuyetActionPerformed(ae);
            }     

            

        });
    }

    private void btnChinhSuaActionPerformed(ActionEvent ae) {
        
    }
    
    private void btnDuyetActionPerformed(ActionEvent ae) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            ChiTietBangLuongGUI frame = new ChiTietBangLuongGUI();
            frame.setVisible(true);
        });
    }*/
    
}
