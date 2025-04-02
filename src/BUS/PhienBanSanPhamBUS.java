package BUS;

import java.util.ArrayList;

import DAO.PhienBanSanPhamDAO;
import DTO.PhienBanSanPhamDTO;

public class PhienBanSanPhamBUS {
	PhienBanSanPhamDAO pbspDAO = new PhienBanSanPhamDAO();
	public ArrayList<PhienBanSanPhamDTO> selectAll(){
		return pbspDAO.selectAll();
	}
	
	public ArrayList<PhienBanSanPhamDTO> getThongTinPBSP(String maPBSP){
		return pbspDAO.getThongTinPBSP(maPBSP);
	}
}
