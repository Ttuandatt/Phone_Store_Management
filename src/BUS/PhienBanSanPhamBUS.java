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
	
	public String tangSoLuong(String maPBSP, int soLuong) {
		if(pbspDAO.tangSoLuong(maPBSP, soLuong)>0)
			return "Tăng số lượng thành công";
		return "Tăng số lượng thất bại";
	}
	
	public String updateSoLuong(String maPBSP, int soLuong) {
		if(pbspDAO.updateSoLuong(maPBSP, soLuong)>0)
			return "Cập nhật số lượng PBSP thành công!";
		return "Cập nhật số lượng PBSP thất bại!";
	}
}
