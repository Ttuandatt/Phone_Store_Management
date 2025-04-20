package BUS;

import DAO.DangNhapDAO;
//import DAO.LoginDAO;
import DAO.NhanVienDAO;
//import DTO.LoginDTO;
import DTO.NhanVienDTO;
import GUI.AdminView;
import GUI.DangNhapGUI;
import GUI.NhanVienGUI;
import GUI.NhanVienKhoView;
import GUI.QuanLyKhoView;
import GUI.QuanLyNhanSuView;

import javax.swing.JOptionPane;

public class DangNhapBUS {
    private final DangNhapDAO dnDAO = new DangNhapDAO();
    private final NhanVienDAO nvDAO = new NhanVienDAO();
    public static String maNV = "";
    public static String hoTen = "";
    public static String chucVu = "";
    public static String kho = "";
    public static int loai;

    
    public boolean kiemTraDangNhap(String manv, String matKhau, DangNhapGUI view) {
    	boolean dangNhap = false;
    	try {
    		int kiemTra = dnDAO.kiemTraDangNhap(manv, matKhau);
    		log("kiemTra="+kiemTra);
    		
    		switch(kiemTra) {
	    		case 1:
	    			NhanVienDTO nv = nvDAO.selectById(manv);
	    			maNV = manv;
	    			hoTen = nv.getHoTen();
	    			chucVu = nv.getChucVu();
	    			kho = nv.getChiNhanh();
	    			
	    			if(chucVu.equalsIgnoreCase("CV001")) {
	    				QuanLyKhoView qlkView = new QuanLyKhoView();
	    				qlkView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
	    				view.closeLoginFrame();
		    			dangNhap = true;
	    			}else if(chucVu.equalsIgnoreCase("CV002")) {
		    			QuanLyNhanSuView qlnsView = new QuanLyNhanSuView();
		    			qlnsView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
		    			view.closeLoginFrame();
		    			dangNhap = true;
	    			}else if(chucVu.equalsIgnoreCase("CV003")) {
		    			NhanVienKhoView nvkView = new NhanVienKhoView();
		    			nvkView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
		    			view.closeLoginFrame();
		    			dangNhap = true;
	    			}else if(chucVu.equalsIgnoreCase("CV004")) {
	    				AdminView adminView = new AdminView();
	    				adminView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
	    				view.closeLoginFrame();
		    			dangNhap = true;
	    			}
	    			
	    			break;
	    		case 2:
                    JOptionPane.showMessageDialog(null, "Sai mật khẩu", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	    			break;
	    		case 3:
                    JOptionPane.showMessageDialog(null, "Nhân viên không tồn tại", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	    			break;
    		}
    	}catch (Exception e) {
    		JOptionPane.showMessageDialog(null, "Lỗi", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
    	return dangNhap;
    }
    
    public String getMaNV() {
    	return DangNhapBUS.maNV;
    }
    
  //hàm hiển thị thông tin dòng code
  	public static void log(String message) {
  	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
  	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
  	    System.out.println(element.getClassName() + " | method: " 
  	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
  	}

}
