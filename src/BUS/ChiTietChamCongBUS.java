package BUS;

import java.util.ArrayList;

import DAO.ChiTietChamCongDAO;
import DTO.ChiTietChamCongDTO;

public class ChiTietChamCongBUS {
	ChiTietChamCongDAO ctccDAO = new ChiTietChamCongDAO();
    
    public ArrayList<ChiTietChamCongDTO> getChiTietCCTheoMaCC(String macc) {
        return ctccDAO.getChiTietCCTheoMaCC(macc);
    }
    public ChiTietChamCongDTO selectById(String temp) {
        return ctccDAO.selectById(temp);
    }
    
    public ChiTietChamCongDTO getChiTietChamCongTheoMaCT(String mact) {
        return ctccDAO.GetChiTietChamCongTheoMaCT(mact);
    }

    public int insertChiTietCC(ChiTietChamCongDTO ct) {
        return ctccDAO.insert(ct);
    }

    public int deleteById(String mact) {
        return ctccDAO.deleteById(mact);
    }
    
    public ArrayList<ChiTietChamCongDTO> getThongTinTangCa(String maBCC){
		return ctccDAO.getThongTinTangCa(maBCC);
    }
    
    public int xoaChiTietChamCongTheoMaCC(String macc) {
        return ctccDAO.deleteByMaCC(macc);
    }

    public ArrayList<ChiTietChamCongDTO> selectAll() {
        return ctccDAO.selectAll();
    }

}
