package BUS;

import java.util.ArrayList;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

public class PhieuNhapBUS {
	PhieuNhapDAO pnDAO = new PhieuNhapDAO();
	
	public ArrayList<PhieuNhapDTO> selectAll(){
		return pnDAO.selectAll();
	}
	

	
	public ArrayList<PhieuNhapDTO> getThongTinPhieuNhap(String maPN){
		return pnDAO.getThongTinPhieuNhap(maPN);
	}
}
