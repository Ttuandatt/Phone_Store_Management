package BUS;

import java.util.ArrayList;

import DAO.ChiTietPhieuXuatDAO;
import DTO.ChiTietPhieuXuatDTO;

public class ChiTietPhieuXuatBUS {
	ChiTietPhieuXuatDAO ctpxDAO = new ChiTietPhieuXuatDAO();
	public ArrayList<ChiTietPhieuXuatDTO> selectAllById(String maPX){
		return ctpxDAO.selectAllById(maPX);
	}
	
	public ArrayList<ChiTietPhieuXuatDTO> getThongTinctpx(String maPX) {
		return ctpxDAO.getThongTinCTPX(maPX);
	}
	
	public int insert(ChiTietPhieuXuatDTO ctpx) {
		return ctpxDAO.insert(ctpx);
	}
}
