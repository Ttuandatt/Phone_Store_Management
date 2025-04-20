package BUS;

import java.util.ArrayList;

import DAO.ChiTietChamCongDAO;
import DTO.ChiTietChamCongDTO;
import DTO.DonXinNghiDTO;

public class ChiTietChamCongBUS {
	ChiTietChamCongDAO ctccDAO = new ChiTietChamCongDAO();
	
	public ArrayList<ChiTietChamCongDTO> getThongTinTangCa(String maBCC){
		return ctccDAO.getThongTinTangCa(maBCC);
	}
	public ArrayList<ChiTietChamCongDTO> getThongTinNgayNghi(String maBCC){
		return ctccDAO.getThongTinNgayNghi(maBCC);
	}
}
