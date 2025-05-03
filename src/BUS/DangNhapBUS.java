//package BUS;
//
//import DAO.DangNhapDAO;
////import DAO.LoginDAO;
//import DAO.NhanVienDAO;
////import DTO.LoginDTO;
//import DTO.NhanVienDTO;
//import Database.DBConfig;
//import GUI.AdminView;
//import GUI.DangNhapGUI;
//import GUI.NhanVienGUI;
//import GUI.NhanVienKhoView;
//import GUI.QuanLyKhoView;
//import GUI.QuanLyNhanSuView;
//
//import javax.swing.JOptionPane;
//
//public class DangNhapBUS {
//    private final DangNhapDAO dnDAO = new DangNhapDAO();
//    private final NhanVienDAO nvDAO = new NhanVienDAO();
//    public static String maNV = "";
//    public static String hoTen = "";
//    public static String chucVu = "";
//    public static String kho = "";
//    public static int loai;
//
//    
//    public boolean kiemTraDangNhap(String manv, String matKhau, DangNhapGUI view) {
//    	boolean dangNhap = false;
//    	try {
//    		int kiemTra = dnDAO.kiemTraDangNhap(manv, matKhau);
//    		log("kiemTra="+kiemTra);
//    		
//    		switch(kiemTra) {
//	    		case 1:
//	    			NhanVienDTO nv = nvDAO.selectById(manv);
//	    			maNV = manv;
//	    			hoTen = nv.getHoTen();
//	    			chucVu = nv.getChucVu();
//	    			kho = nv.getChiNhanh();
//	    			
//	    			if(chucVu.equalsIgnoreCase("CV001")) {
//	    				QuanLyKhoView qlkView = new QuanLyKhoView();
//	    				qlkView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
//	    				view.closeLoginFrame();
//		    			dangNhap = true;
//	    			}else if(chucVu.equalsIgnoreCase("CV002")) {
//		    			QuanLyNhanSuView qlnsView = new QuanLyNhanSuView();
//		    			qlnsView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
//		    			view.closeLoginFrame();
//		    			dangNhap = true;
//	    			}else if(chucVu.equalsIgnoreCase("CV003")) {
//		    			NhanVienKhoView nvkView = new NhanVienKhoView();
//		    			nvkView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
//		    			view.closeLoginFrame();
//		    			dangNhap = true;
//	    			}else if(chucVu.equalsIgnoreCase("CV004")) {
//	    				//nếu chức vụ là "CV004" - Admin thì set dbUrl là kết nối tới server gốc
//	    				if (chucVu.equalsIgnoreCase("CV004")) {
//	    				    DBConfig.currentDbUrl = DBConfig.DB_URL_GOC; // Gán đường dẫn kết nối gốc
//	    				}
//
//	    				
//	    				AdminView adminView = new AdminView();
//	    				adminView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
//	    				view.closeLoginFrame();
//		    			dangNhap = true;
//	    			}
//	    			
//	    			break;
//	    		case 2:
//                    JOptionPane.showMessageDialog(null, "Sai mật khẩu", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//	    			break;
//	    		case 3:
//                    JOptionPane.showMessageDialog(null, "Nhân viên không tồn tại", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//	    			break;
//    		}
//    	}catch (Exception e) {
//    		JOptionPane.showMessageDialog(null, "Lỗi", "Thông báo", JOptionPane.WARNING_MESSAGE);
//		}
//    	return dangNhap;
//    }
//    
//    public String getMaNV() {
//    	return DangNhapBUS.maNV;
//    }
//    
//  //hàm hiển thị thông tin dòng code
//  	public static void log(String message) {
//  	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//  	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
//  	    System.out.println(element.getClassName() + " | method: " 
//  	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
//  	}
//
//}


package BUS;

import DAO.DangNhapDAO;
import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import GUI.AdminView;
import GUI.DangNhapGUI;
import GUI.NhanVienKhoView;
import GUI.QuanLyKhoView;
import GUI.QuanLyNhanSuView;
import Database.JDBCConnection;

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
            log("kiemTra=" + kiemTra);

            switch (kiemTra) {
                case 1:
                    NhanVienDTO nv = nvDAO.selectById(manv);
                    maNV = manv;
                    hoTen = nv.getHoTen();
                    chucVu = nv.getChucVu();
                    kho = nv.getChiNhanh();

                    // Cập nhật dbUrl theo chi nhánh hoặc quyền
                    switch (chucVu.toUpperCase()) {
                        case "CV004": // Admin
                            JDBCConnection.setDbUrl(JDBCConnection.DB_URL_GOC);
                            AdminView adminView = new AdminView();
                            adminView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
                            view.closeLoginFrame();
                            dangNhap = true;
                            break;
                        case "CV001": // Quản lý kho
                            JDBCConnection.setDbUrl(getDbUrlTheoChiNhanh(kho));
                            QuanLyKhoView qlkView = new QuanLyKhoView();
                            qlkView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
                            view.closeLoginFrame();
                            dangNhap = true;
                            break;
                        case "CV002": // Quản lý nhân sự
                            JDBCConnection.setDbUrl(getDbUrlTheoChiNhanh(kho));
                            QuanLyNhanSuView qlnsView = new QuanLyNhanSuView();
                            qlnsView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
                            view.closeLoginFrame();
                            dangNhap = true;
                            break;
                        case "CV003": // Nhân viên kho
                            JDBCConnection.setDbUrl(getDbUrlTheoChiNhanh(kho));
                            NhanVienKhoView nvkView = new NhanVienKhoView();
                            nvkView.hienThiThongTinNguoiDung(maNV, hoTen, chucVu, kho);
                            view.closeLoginFrame();
                            dangNhap = true;
                            break;
                    }
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Sai mật khẩu", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Nhân viên không tồn tại", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
        return dangNhap;
    }

    // Trả về dbUrl theo tên chi nhánh
    private String getDbUrlTheoChiNhanh(String chiNhanh) {
        if (chiNhanh == null) return JDBCConnection.DB_URL_GOC;
        switch (chiNhanh.toUpperCase()) {
            case "HÀ NỘI":
                return JDBCConnection.DB_URL_HN;
            case "ĐÀ NẴNG":
                return JDBCConnection.DB_URL_DN;
            case "HỒ CHÍ MINH":
                return JDBCConnection.DB_URL_HCM;
            default:
                return JDBCConnection.DB_URL_GOC;
        }
    }

    public String getMaNV() {
        return DangNhapBUS.maNV;
    }

    // Hàm hiển thị thông tin dòng code
    public static void log(String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
        System.out.println(element.getClassName() + " | method: "
                + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
    }
}
