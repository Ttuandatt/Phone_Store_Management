package BUS;

import java.util.ArrayList;

import DAO.KhoDAO;
import DTO.KhoDTO;

public class KhoBUS {
	KhoDAO khoDAO = new KhoDAO();
	
	
	public ArrayList<KhoDTO> selectAll() {
		return khoDAO.selectAll();
	}
	 
}
