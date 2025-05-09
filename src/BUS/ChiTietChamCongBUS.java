package BUS;

import java.util.ArrayList;

import DAO.ChiTietChamCongDAO;
import DTO.ChiTietChamCongDTO;

public class ChiTietChamCongBUS {
	ChiTietChamCongDAO ctccDAO = new ChiTietChamCongDAO();
	
	public ArrayList<ChiTietChamCongDTO> getThongTinTangCa(String maBCC){
		return ctccDAO.getThongTinTangCa(maBCC);
	}

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
        return ctccDAO.insert1(ct);
    }

    public int deleteById(String mact) {
        return ctccDAO.delete1(mact);
    }
    
    public int xoaChiTietChamCongTheoMaCT(String mact) {
        return ctccDAO.deleteByMaCT(mact);
    }
    
 	public ArrayList<ChiTietChamCongDTO> getThongTinNgayNghi(String maBCC){
 		return ctccDAO.getThongTinNgayNghi(maBCC);
 	}

}
