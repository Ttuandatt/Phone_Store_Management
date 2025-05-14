package BUS;

import java.util.ArrayList;

import DAO.LichSuChinhSuaDAO;
import DTO.LichSuChinhSuaDTO;

public class LichSuChinhSuaBUS {
	LichSuChinhSuaDAO lsDAO = new LichSuChinhSuaDAO();

	public ArrayList<LichSuChinhSuaDTO> selectByMaNV(String maNV) {
		return lsDAO.selectByMaNV(maNV);
	}

	public ArrayList<LichSuChinhSuaDTO> selectAll() {
		return lsDAO.selectAll();
	}

	public int insert(LichSuChinhSuaDTO ls) {
		return lsDAO.insert(ls);
	}
}
