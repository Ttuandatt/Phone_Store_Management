package BUS;

import java.util.ArrayList;

import DAO.ChucVuDAO;
import DTO.ChucVuDTO;

public class ChucVuBUS {
	
	ChucVuDAO cvDAO = new ChucVuDAO();
	
	public ArrayList<ChucVuDTO> selectAll(){
		return cvDAO.selectAll();
	}
	
	public ChucVuDTO selectById(String maCV) {
		return cvDAO.selectById(maCV);
	}
	
	public String insert(ChucVuDTO cv) {
		if(cvDAO.insert(cv)>0)
			return "Thêm chức vụ thành công";
		return "Thêm chức vụ thất bại";
	}
}
