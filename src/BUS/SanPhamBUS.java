package BUS;

import java.util.ArrayList;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

public class SanPhamBUS {
	SanPhamDAO spDAO = new SanPhamDAO();
	public ArrayList<SanPhamDTO> selectAll(){
        return spDAO.selectAll();
    }
}
