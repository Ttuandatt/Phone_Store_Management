package BUS;

import java.util.ArrayList;

import DAO.BangChamCongDAO;
import DTO.BangChamCongDTO;

public class BangChamCongBUS {
	BangChamCongDAO bccDAO = new BangChamCongDAO();
	public ArrayList<BangChamCongDTO> selectAll(){
		return bccDAO.selectAll();
	}
	
	public BangChamCongDTO selectById(String maNV) {
		return bccDAO.selectById(maNV);
	}
	
	public String insert(BangChamCongDTO bcc) {
		if(bccDAO.insert(bcc)>0)
			return "Thêm bảng chấm công thành công!";
		return "Thêm bảng chấm công thất bại!";
	}
	

}
