package BUS;

import java.util.ArrayList;

import DAO.ChiTietChamCongDAO;
import DTO.ChiTietChamCongDTO;

public class ChiTietChamCongBUS {
	ChiTietChamCongDAO ctccDAO = new ChiTietChamCongDAO();
	
	public ArrayList<ChiTietChamCongDTO> getThongTinTangCa(String maBCC){
		return ctccDAO.getThongTinTangCa(maBCC);
	}

    public ArrayList<ChiTietChamCongDTO> searchById(String temp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ChiTietChamCongDTO selectById(String temp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void insertChiTietCC(ChiTietChamCongDTO ct) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
