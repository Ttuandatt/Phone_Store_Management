package BUS;

import java.util.ArrayList;

import DAO.BangChamCongDAO;
import DTO.BangChamCongDTO;

public class BangChamCongBUS {
	BangChamCongDAO bccDAO = new BangChamCongDAO();
	public ArrayList<BangChamCongDTO> selectAll(){
		return bccDAO.selectAll();
	}
	
	public BangChamCongDTO selectById(String maBCC) {
		return bccDAO.selectById(maBCC);
	}
	
	public String insert(BangChamCongDTO bcc) {
		if(bccDAO.insert(bcc)>0)
			return "Thêm bảng chấm công thành công!";
		return "Thêm bảng chấm công thất bại!";
	}
	
	public int updateById(BangChamCongDTO bcc) {
        return bccDAO.update(bcc);
    }
	
	public ArrayList<BangChamCongDTO> selectByTime(String thang, String nam) {
        return bccDAO.selectByTime(thang, nam);
    }

    public ArrayList<BangChamCongDTO> selectByKeyWord(String tuKhoa) {
        return bccDAO.selectByKeyWord(tuKhoa);
    }
    
    //get bang cham cong by thang va nam
  	public BangChamCongDTO getBangChamCongByThangNam(String maNV, int thang, int nam) {
  		return bccDAO.getBangChamCongByNVAndThangNam(maNV, thang, nam);
  	}

}
