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
    		
    		switch(kiemTra) {
	    		case 1:
	    			NhanVienDTO nv = nvDAO.selectById(manv);
	    			maNV = manv;
	    			hoTen = nv.getHoTen();
	    			chucVu = nv.getChucVu();
	    			kho = nv.getNoiLamViec();
	    			
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

//public void checkAndLogin(LoginDTO user, LoginGUI view) {
//    try {
//        // Tạo danh sách tài khoản mặc định
//        String defaultEmail = "admin@example.com";
//        String defaultPassword = "123456";
//        String defaultMaCV = "CV001";  // Chức vụ Admin mặc định
//
//        // Kiểm tra thông tin đăng nhập
//        if (user.getEmail().equals(defaultEmail) && user.getPassword().equals(defaultPassword)) {
//            // Đăng nhập thành công với tài khoản mặc định
//            hoten = "Nguyễn Văn A";  // Tên nhân viên mặc định
//            id = "NV001";  // Mã nhân viên mặc định
//            chucvu = defaultMaCV;  // Mã chức vụ mặc định (Admin)
//
//            // Kiểm tra chức vụ và mở giao diện phù hợp
//            if ("CV001".equals(chucvu)) {
//                new AdminView();  // Giao diện Admin
//            } else if ("CV002".equals(chucvu)) {
//                new NhanVienGUI();  // Giao diện Nhân viên
//            } else {
//                JOptionPane.showMessageDialog(null, "Không xác định quyền truy cập", "Lỗi", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            // Đóng cửa sổ đăng nhập sau khi đăng nhập thành công
//            view.closeLoginFrame();
//        } else {
//            // Nếu thông tin đăng nhập sai
//            JOptionPane.showMessageDialog(null, "Sai email hoặc mật khẩu", "Lỗi", JOptionPane.WARNING_MESSAGE);
//        }
//
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(null, "Lỗi hệ thống: " + e.getMessage(), "Thông báo", JOptionPane.WARNING_MESSAGE);
//    }
}

//    public void checkAndLogin(LoginDTO user, LoginGUI view) {
//        try {
//            if (loginDAO.openConnection()) {
//                int checkResult = loginDAO.checkLogin(user);
//
//                switch (checkResult) {
//                    case 1:
//                        // Lấy thông tin tài khoản sau khi đăng nhập thành công
//                        LoginDTO account = loginDAO.getAccountInformation(user.getEmail(), user.getPassword());
//                        if (account == null) {
//                            JOptionPane.showMessageDialog(null, "Không thể lấy thông tin tài khoản", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        }
//
//                        NhanVienDTO thongTinUser = nvDAO.selectById(account.getManv());
//                        if (thongTinUser == null) {
//                            JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin nhân viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        }
//
//                        // Gán thông tin nhân viên sau khi lấy thành công
//                        hoten = thongTinUser.getHoTen();
//                        id = thongTinUser.getMaNV();
//                        chucvu = thongTinUser.getChucVu();
//
//                        // So sánh chuỗi đúng cách
//                        if ("CV001".equals(chucvu)) {
//                            new AdminView();
//                        } else if ("CV002".equals(chucvu)) {
//                            new NhanVienGUI();
//                        } else {
//                            JOptionPane.showMessageDialog(null, "Không xác định quyền truy cập", "Lỗi", JOptionPane.WARNING_MESSAGE);
//                            return;
//                        }
//
//                        view.closeLoginFrame();
//                        break;
//
//                    case 2:
//                        JOptionPane.showMessageDialog(null, "Nhân viên chưa có tài khoản");
//                        break;
//
//                    case 3:
//                        JOptionPane.showMessageDialog(null, "Nhân viên không tồn tại");
//                        break;
//
//                    case 4:
//                        JOptionPane.showMessageDialog(null, "Sai mật khẩu");
//                        break;
//
//                    default:
//                        JOptionPane.showMessageDialog(null, "Lỗi không xác định", "Lỗi", JOptionPane.WARNING_MESSAGE);
//                        break;
//                }
//                loginDAO.closeConnection();
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Lỗi hệ thống: " + e.getMessage(), "Thông báo", JOptionPane.WARNING_MESSAGE);
//        }
//    }
//}