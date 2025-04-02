package BUS;

import java.util.ArrayList;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;

public class ChiTietPhieuNhapBUS {
	ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();
	public ArrayList<ChiTietPhieuNhapDTO> selectAllById(String maPN){
		return ctpnDAO.selectAllById(maPN);
	}
	
	public ArrayList<ChiTietPhieuNhapDTO> getThongTinCTPN(String maPN) {
		return ctpnDAO.getThongTinCTPN(maPN);
	}
}
