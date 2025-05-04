package BUS;

import java.util.ArrayList;

import DAO.ChiTietChamCongDAO;
import DTO.ChiTietChamCongDTO;

public class ChiTietChamCongBUS {
	ChiTietChamCongDAO ctccDAO = new ChiTietChamCongDAO();
    
    public ArrayList<ChiTietChamCongDTO> getChiTietCCTheoMaCC(String macc, String makho) {
        return ctccDAO.getChiTietCCTheoMaCC(macc, makho);
    }
    public ChiTietChamCongDTO selectById(String temp) {
        return ctccDAO.selectById(temp);
    }
    
    public ChiTietChamCongDTO getChiTietChamCongTheoMaCT(String mact) {
        return ctccDAO.GetChiTietChamCongTheoMaCT(mact);
    }

    public int insertChiTietCC(ChiTietChamCongDTO ct, String makho) {
        return ctccDAO.insert1(ct, makho);
    }

    public int deleteById(String mact, String makho) {
        return ctccDAO.delete1(mact, makho);
    }
    
    public ArrayList<ChiTietChamCongDTO> getThongTinTangCa(String maBCC){
		return ctccDAO.getThongTinTangCa(maBCC);
    }

}
