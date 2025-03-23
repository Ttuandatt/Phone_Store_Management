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
}
