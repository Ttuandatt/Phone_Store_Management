package BUS;

import java.util.ArrayList;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

public class SanPhamBUS {
	SanPhamDAO spDAO = new SanPhamDAO();
	public ArrayList<SanPhamDTO> selectAll(){
        return spDAO.selectAll();
    }
	
	public String getTenSanPhamByMaPBSP(String maPBSP) {
		return spDAO.getTenSanPhamByMaPBSP(maPBSP);
	}
	
	public ArrayList<SanPhamDTO> getTenSanPhamByMaPBSP2(String maPBSP) {
		return spDAO.getTenSanPhamByMaPBSP2(maPBSP);
	}
}
