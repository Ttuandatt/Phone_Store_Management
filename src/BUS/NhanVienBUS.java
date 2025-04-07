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
		//Sau đó set mã CV, nơi làm việc bằng mã ta lấy được, khi này insert vào thì giá trị của khóa ngoại maCV, noiLamViec sẽ k bị báo lỗi
		if(cvDAO.getIdByName(nv.getChucVu()) != null && khoDAO.getIdByName(nv.getNoiLamViec()) != null) {
			nv.setChucVu(cvDAO.getIdByName(nv.getChucVu()));
			nv.setNoiLamViec(khoDAO.getIdByName(nv.getNoiLamViec()));
			if(nvDAO.insert(nv)>0)
				return "Thêm nhân viên thành công";
		}
		return "Thêm nhân viên thất bại";
	}
	
	public String update(NhanVienDTO nv) {
		if(cvDAO.getIdByName(nv.getChucVu()) != null && khoDAO.getIdByName(nv.getNoiLamViec()) != null){
			nv.setChucVu(cvDAO.getIdByName(nv.getChucVu()));
			nv.setNoiLamViec(khoDAO.getIdByName(nv.getNoiLamViec()));
			if(nvDAO.update(nv)>0)
				return "Cập nhật nhân viên thành công";
		}
		return "Cập nhật nhân viên thất bại";
	}
	
	public String updateWithoutChangingImage(NhanVienDTO nv) {
		if(cvDAO.getIdByName(nv.getChucVu()) != null && khoDAO.getIdByName(nv.getNoiLamViec()) != null){
			nv.setChucVu(cvDAO.getIdByName(nv.getChucVu()));
			nv.setNoiLamViec(khoDAO.getIdByName(nv.getNoiLamViec()));
			if(nvDAO.updateWithoutChangingImage(nv)>0)
				return "Cập nhật nhân viên thành công";
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
}
