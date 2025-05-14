package BUS;

import java.util.ArrayList;

import DAO.ChucVuDAO;
import DAO.KhoDAO;
import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class NhanVienBUS {
	NhanVienDAO nvDAO = new NhanVienDAO();
	ChucVuDAO cvDAO = new ChucVuDAO();
	KhoDAO khoDAO = new KhoDAO();
	
	public ArrayList<NhanVienDTO> selectAll(){
		return nvDAO.selectAll();
	}
	
	public NhanVienDTO selectById(String maNV) {
		return nvDAO.selectById(maNV);
	}
	
	public String insert(NhanVienDTO nv) {
		//Vì chức vụ và nơi làm việc ở NhanVienGUI là ở dạng tên chứ k phải ở dạng mã, nên khi insert vào bảng nhân viên thì lúc so sánh khóa ngoại tới bảng chức vụ, kho thì sẽ bị lỗi. 
		//Bây giờ ta lấy cái tên chức vụ, tên kho lấy được từ dialog thêm nhân viên và gọi DAO của chức vu, kho để lấy mã CV, mã Kho từ tên CV, tên Kho ta lấy được
		//Sau đó set mã CV, nơi làm việc bằng mã ta lấy được, khi này insert vào thì giá trị của khóa ngoại maCV, ChiNhanh sẽ k bị báo lỗi
		if(cvDAO.getIdByName(nv.getChucVu()) != null && khoDAO.getIdByName(nv.getChiNhanh()) != null) {
			nv.setChucVu(cvDAO.getIdByName(nv.getChucVu()));
			nv.setChiNhanh(khoDAO.getIdByName(nv.getChiNhanh()));
			if(nvDAO.insert(nv)>0)
				return "Thêm nhân viên thành công";
		}
		return "Thêm nhân viên thất bại";
	}
	
	public String update(NhanVienDTO nv) {
		if(cvDAO.getIdByName(nv.getChucVu()) != null && khoDAO.getIdByName(nv.getChiNhanh()) != null){
			nv.setChucVu(cvDAO.getIdByName(nv.getChucVu()));
			nv.setChiNhanh(khoDAO.getIdByName(nv.getChiNhanh()));
			if(nvDAO.update(nv)==-1)
				return "Cập nhật nhân viên thành công";
		}
		return "Cập nhật nhân viên thất bại";
	}
	
	public String updateWithoutChangingImage(NhanVienDTO nv) {
		if(cvDAO.getIdByName(nv.getChucVu()) != null && khoDAO.getIdByName(nv.getChiNhanh()) != null){
			nv.setChucVu(cvDAO.getIdByName(nv.getChucVu()));
			nv.setChiNhanh(khoDAO.getIdByName(nv.getChiNhanh()));
			if(nvDAO.updateWithoutChangingImage(nv)==-1) {	//ly do == -1 thi xem o ben NhanVienDAO ngay phuong thuc nay luon se co giai thich
				log("nvDAO.updateWithoutChangingImage(nv)="+nvDAO.updateWithoutChangingImage(nv));
				return "Cập nhật nhân viên thành công";
			}
		}
		
		return "Cập nhật nhân viên thất bại";
	}
	
	public String getRoleNameByRoleId(String maCV) {
		String roleName = "";
		if(cvDAO.getRoleNameByRoleId(maCV) != null) {
			roleName = cvDAO.getRoleNameByRoleId(maCV);
		}else {
    		System.out.println("NhanVienBUS: k lấy được tên chức vụ");
		}
		return roleName;
	}
	
	public Double getBaseSalaryByRoleID(String maCV) {
		return nvDAO.getBaseSalaryByRoleID(maCV);
	}
	
	public String deleteEmployee(NhanVienDTO nv) {
		if(nvDAO.delete(nv)>0)
			return "Xóa nhân viên thành công";
		return "Xóa nhân viên thất bại";
	}
	
	public ArrayList<NhanVienDTO> selectAllByRoleName(String role){
		return nvDAO.selectAllByRoleName(role);
	}
	
	public ArrayList<NhanVienDTO> selectAllByWarehouseName(String tenKho){
		String khoId="";
		if(tenKho.equalsIgnoreCase("Kho Hà Nội"))
			khoId="HN";
		else if(tenKho.equalsIgnoreCase("Kho Đà Nẵng"))
			khoId="DN";
		else if(tenKho.equalsIgnoreCase("Kho Hồ Chí Minh"))
			khoId="HCM";
		
		return nvDAO.selectAllByWarehouseId(khoId);
	}
	
	public int getSoNgayCong(int thangCC, int namCC, String maNV) {
		return nvDAO.getSoNgayCong(thangCC, namCC, maNV);
	}
	
	public int getSoNgayNghiPhepCoLuong(int thangCC, int namCC, String maNV) {
		return nvDAO.getSoNgayNghiPhepCoLuong(thangCC, namCC, maNV);
	}
	
	public int getSoNgayNghiPhepKhongLuong(int thangCC, int namCC, String maNV) {
		return nvDAO.getSoNgayNghiPhepKhongLuong(thangCC, namCC, maNV);
	}
	
	public int getSoNgayNghiKhongPhep(int thangCC, int namCC, String maNV) {
		return nvDAO.getSoNgayNghiKhongPhep(thangCC, namCC, maNV);
	}
	
	public double getSoGioTangCa(int thangCC, int namCC, String maNV) {
		return nvDAO.getSoGioTangCa(thangCC, namCC, maNV);
	}
	
	// update thong tin ban than
	public String updatePersonalInfo(NhanVienDTO nv) {
		if (nvDAO.updatePersonalInfo(nv))
			return "Cập nhật thông tin cá nhân thành công";
		return "Cập nhật thông tin cá nhân thất bại";
	}
	
	public String getChucVuByMaNV(String maNV) {
		return nvDAO.getChucVuByMaNV(maNV);
	}
	
	
    public NhanVienDTO selectByKeyWord(String maNV) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<NhanVienDTO> selectNhanVienMoi() {
        return nvDAO.selectNhanVienMoi();
    }
	
	
	//hàm hiển thị thông tin dòng code
		public static void log(String message) {
		    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
		    System.out.println(element.getClassName() + " | method: " 
		        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
		}
}
