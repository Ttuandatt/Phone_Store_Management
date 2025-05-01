package BUS;

import DAO.ThuongHieuDAO;

public class ThuongHieuBUS {
	ThuongHieuDAO thDAO = new ThuongHieuDAO();
	
	public String getIdByName(String tenTH) {
		return thDAO.getIdByName(tenTH);
	}
}
