
package GUI;

import BUS.BangChamCongBUS;
import BUS.BangLuongBUS;
import BUS.ChucVuBUS;
import BUS.DangNhapBUS;
import BUS.NhanVienBUS;
import DTO.BangChamCongDTO;
import DTO.BangLuongDTO;
import DTO.ChucVuDTO;
import DTO.NhanVienDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.border.TitledBorder;
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
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private DangNhapBUS dnBUS =  new DangNhapBUS();
    private BangChamCongBUS bccBUS = new BangChamCongBUS();
    private ChucVuBUS cvBUS = new ChucVuBUS();
    private BangLuongBUS blBUS = new BangLuongBUS();

    public ChiTietBangLuongGUI(BangLuongDTO bl, String tongTN, String thucNhan) {
        NhanVienDTO nv = nvBUS.selectById(bl.getMaNV());
        String mabcc= bl.getMaLuong().replaceFirst("BL", "CC");
        BangChamCongDTO bcc = bccBUS.selectById(mabcc);
        log("maBCC="+bcc.getMaBCC());
        ChucVuDTO cv = cvBUS.selectById(nv.getChucVu());
        DecimalFormat df = new DecimalFormat("#,###");
        
        setTitle("CHI TIẾT BẢNG LƯƠNG");
        setSize(900, 700);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);  // Không sử dụng Layout Manager
        
        jp_main = new JPanel();
        jp_main.setLayout(null);
        jp_main.setBounds(0, 0, 900, 700);
        jp_main.setBackground(Color.WHITE);
        add(jp_main);
        
        Font titleFont = new Font("Serif", Font.BOLD, 20);
        Font labelFont = new Font("SansSerif", Font.BOLD, 15);
        Font tfFont = new Font("SansSerif", Font.PLAIN, 15);
        
        String title = "BẢNG LƯƠNG THÁNG " + bl.getThangLuong() + "/" + bl.getNamLuong();
        lb_bangluong = new JLabel(title, SwingConstants.CENTER);
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

        jl_nhanvien = new JLabel(nv.getMaNV() + " - " + nv.getHoTen());
        log("maNV="+nv.getMaNV());
        jl_nhanvien.setBounds(150, 25, 160, 30);
        jl_nhanvien.setFont(tfFont);
        jp_ttnv.add(jl_nhanvien);

        lb_chucvu = new JLabel("Chức vụ:");
        lb_chucvu.setFont(labelFont);
        lb_chucvu.setBounds(430, 25, 160, 30);
        jp_ttnv.add(lb_chucvu);

        jl_chucvu = new JLabel();
        String chucVu = nvBUS.getChucVuByMaNV(nv.getMaNV());
        jl_chucvu.setText(chucVu);
        jl_chucvu.setBounds(520, 25, 200, 30);
        jl_chucvu.setFont(tfFont);
        jp_ttnv.add(jl_chucvu);

        lb_luongcb = new JLabel("Lương cơ bản:");
        lb_luongcb.setFont(labelFont);
        lb_luongcb.setBounds(50, 65, 200, 30);
        jp_ttnv.add(lb_luongcb);

        String luongCB= df.format(bl.getLuongCB());
        jl_luongcb = new JLabel(luongCB);
        jl_luongcb.setBounds(180, 65, 200, 30);
        jl_luongcb.setFont(tfFont);
        jp_ttnv.add(jl_luongcb);

        lb_heso = new JLabel("Hệ số:");
        lb_heso.setFont(labelFont);
        lb_heso.setBounds(450, 65, 160, 30);
        jp_ttnv.add(lb_heso);

        jl_heso = new JLabel(String.valueOf(bl.getHeSo()));
        jl_heso.setBounds(520, 65, 150, 30);
        jl_heso.setFont(tfFont);
        jp_ttnv.add(jl_heso);
        
        ///////////////////// Chi tiết lương
        jp_ctl = new JPanel();
        jp_ctl.setLayout(null); // hoặc dùng layout khác nếu cần
        TitledBorder border1 = BorderFactory.createTitledBorder("Chi tiết lương");
        border1.setTitleFont(new Font("Tahoma", Font.BOLD, 16));
        jp_ctl.setBorder(border1);
        jp_ctl.setBounds(20, 200, 850, 500);
        jp_ctl.setPreferredSize(new Dimension(900, 600));
        jp_ctl.setBackground(Color.white);
        JScrollPane scrollPane = new JScrollPane(jp_ctl);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(20, 200, 850, 450); // Đặt vị trí và kích thước
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
        
        Float luongTT = ((bl.getLuongCB()* bl.getHeSo())/24)* bcc.getSoNgayLam();
        String luongThucTe= df.format(luongTT);
        tf_luongtt1 = new JTextField(luongThucTe);
        tf_luongtt1.setBounds(680, 50, 130, 25);
        tf_luongtt1.setFont(tfFont);
        tf_luongtt1.setEditable(false);
        jp_ctl.add(tf_luongtt1);
        
        lb_soNL1 = new JLabel(String.valueOf(bcc.getSoNgayLam()));
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
        
        Float t = (bl.getLuongCB()* bl.getHeSo())/(24* 8);
        String luongOT1 = df.format(bcc.getSoGioOTNgayThuong()*t);
        log("bcc.getSoGioOTNgayThuong()="+bcc.getSoGioOTNgayThuong());
        log("luongOT1="+luongOT1);
        tf_luongot1 = new JTextField(luongOT1);
        tf_luongot1.setBounds(680, 90, 130, 25);
        tf_luongot1.setFont(tfFont);
        tf_luongot1.setEditable(false);
        jp_ctl.add(tf_luongot1);  
        
        
        lb_soNL2 = new JLabel(String.valueOf(bcc.getSoGioOTNgayThuong()) + "h");
        lb_soNL2.setBounds(510, 90, 100, 25);
        lb_soNL2.setHorizontalAlignment(SwingConstants.CENTER);
        lb_soNL2.setFont(tfFont);
        jp_ctl.add(lb_soNL2);
        
        lb_luongot2 = new JLabel("Lương tăng ca ngày lễ (Hệ số 3.0)");
        lb_luongot2.setBounds(200, 130, 250, 25);
        lb_luongot2.setFont(tfFont);
        jp_ctl.add(lb_luongot2);
        
        String luongOT2 = df.format(bcc.getSoGioOTNgayLe()*t);
        tf_luongot2 = new JTextField(luongOT2);
        tf_luongot2.setBounds(680, 130, 130, 25);
        tf_luongot2.setFont(tfFont);
        tf_luongot2.setEditable(false);
        jp_ctl.add(tf_luongot2);
        
        lb_soNL3 = new JLabel(String.valueOf(bcc.getSoGioOTNgayLe()) + "h");
        lb_soNL3.setBounds(510, 130, 100, 25);
        lb_soNL3.setHorizontalAlignment(SwingConstants.CENTER);
        lb_soNL3.setFont(tfFont);
        jp_ctl.add(lb_soNL3);
        
        lb_luongot3 = new JLabel("Lương tăng ca chủ nhật (Hệ số 2.0)");
        lb_luongot3.setBounds(200, 170, 250, 25);
        lb_luongot3.setFont(tfFont);
        jp_ctl.add(lb_luongot3);
        
        String luongOT3 = df.format(bcc.getSoGioOTCN()*t);
        tf_luongot3 = new JTextField(luongOT3);
        tf_luongot3.setBounds(680, 170, 130, 25);
        tf_luongot3.setFont(tfFont);
        tf_luongot3.setEditable(false);
        jp_ctl.add(tf_luongot3);
        
        lb_soNL4 = new JLabel(String.valueOf(bcc.getSoGioOTCN()) + "h");
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
       
        String pcAn= df.format(bl.getPhuCapAnTrua());
        tf_phucap1 = new JTextField(pcAn);
        tf_phucap1.setBounds(680, 210, 130, 25);
        tf_phucap1.setFont(tfFont);
        tf_phucap1.setEditable(false);
        jp_ctl.add(tf_phucap1);
        
        lb_phucap2 = new JLabel("Phụ cấp đi lại");
        lb_phucap2.setFont(tfFont);
        lb_phucap2.setBounds(200, 250, 150, 25);
        jp_ctl.add(lb_phucap2);
        
        String pcDiLai= df.format(bl.getPhuCapDiLai());
        tf_phucap2 = new JTextField(pcDiLai);
        tf_phucap2.setBounds(680, 250, 130, 25);
        tf_phucap2.setFont(tfFont);
        tf_phucap2.setEditable(false);
        jp_ctl.add(tf_phucap2);
        

        //
        lb_thuong = new JLabel("4. Lương thưởng:");
        lb_thuong.setFont(labelFont);
        lb_thuong.setBounds(30, 290, 160, 25);
        jp_ctl.add(lb_thuong);
        
        String thuong= df.format(bl.getThuong());
        tf_thuong = new JTextField(thuong);
        tf_thuong.setBounds(680, 290, 130, 25);
        tf_thuong.setFont(tfFont);
        tf_thuong.setEditable(false);
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
        
        String thue= df.format(bl.getThue());
        tf_khoantru1 = new JTextField(thue);
        tf_khoantru1.setBounds(680, 330, 130, 25);
        tf_khoantru1.setFont(tfFont);
        tf_khoantru1.setEditable(false);
        jp_ctl.add(tf_khoantru1);

        String bhyt= df.format(bl.getBhyt());
        tf_khoantru2 = new JTextField(bhyt);
        tf_khoantru2.setBounds(680, 370, 130, 25);
        tf_khoantru2.setFont(tfFont);
        tf_khoantru2.setEditable(false);
        jp_ctl.add(tf_khoantru2);
        
        String bhxh= df.format(bl.getBhxh());
        tf_khoantru3 = new JTextField(bhxh);
        tf_khoantru3.setBounds(680, 410, 130, 25);
        tf_khoantru3.setFont(tfFont);
        tf_khoantru3.setEditable(false);
        jp_ctl.add(tf_khoantru3);

        String bhtn= df.format(bl.getBhtn());
        tf_khoantru4 = new JTextField(bhtn);
        tf_khoantru4.setBounds(680, 450, 130, 25);
        tf_khoantru4.setFont(tfFont);
        tf_khoantru4.setEditable(false);
        jp_ctl.add(tf_khoantru4);    
        
        String tamUng= df.format(bl.getTamUng());
        tf_khoantru5 = new JTextField(tamUng);
        tf_khoantru5.setBounds(680, 490, 130, 25);
        tf_khoantru5.setFont(tfFont);
        tf_khoantru5.setEditable(false);
        jp_ctl.add(tf_khoantru5);
        
        //
        lb_tongtn = new JLabel("6. Tổng thu nhập thực tế (1)+(2)+(3)+(4)");
        lb_tongtn.setFont(labelFont);
        lb_tongtn.setBounds(30, 530, 300, 25);
        jp_ctl.add(lb_tongtn); 

        tf_tongtn = new JTextField(tongTN);
        tf_tongtn.setBounds(680, 530, 130, 25);
        tf_tongtn.setFont(tfFont);
        tf_tongtn.setEditable(false);
        jp_ctl.add(tf_tongtn);
        
        //
        lb_thuclinh = new JLabel("7. Lương thực lĩnh (6)-(5)");
        lb_thuclinh.setFont(labelFont);
        lb_thuclinh.setBounds(30, 570, 200, 25);
        jp_ctl.add(lb_thuclinh); 

        tf_thuclinh = new JTextField(thucNhan);
        tf_thuclinh.setBounds(680, 570, 130, 25);
        tf_thuclinh.setFont(tfFont);
        tf_thuclinh.setEditable(false);
        jp_ctl.add(tf_thuclinh);
        
        jp_ctl.revalidate();
        jp_ctl.repaint();
        
    }

	// hàm hiển thị thông tin dòng code
	public static void log(String message) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
		System.out.println(element.getClassName() + " | method: " + element.getMethodName() + " | line: "
				+ element.getLineNumber() + " | " + message);
	}
}