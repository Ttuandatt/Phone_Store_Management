package BUS;

import java.util.ArrayList;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class NhanVienBUS {
	NhanVienDAO nvDAO = new NhanVienDAO();
	
	public ArrayList<NhanVienDTO> selectAll(){
		return nvDAO.selectAll();
	}
	
	public String insert(NhanVienDTO nv) {
		if(nvDAO.insert(nv)>0)
			return "Thêm nhân viên thành công";
		return "Thêm nhân viên thất bại";
	}
}
