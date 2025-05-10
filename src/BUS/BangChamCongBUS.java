package BUS;

import java.util.ArrayList;

import DAO.BangChamCongDAO;
import DTO.BangChamCongDTO;

public class BangChamCongBUS {
	BangChamCongDAO bccDAO = new BangChamCongDAO();
	public ArrayList<BangChamCongDTO> selectAll(){
		return bccDAO.selectAll();
	}
	
	public BangChamCongDTO selectById(String maNV) {
		return bccDAO.selectById(maNV);
	}
	
	public String insert(BangChamCongDTO bcc) {
		if(bccDAO.insert(bcc)>0)
			return "Thêm bảng chấm công thành công!";
		return "Thêm bảng chấm công thất bại!";
	}

    public ArrayList<BangChamCongDTO> sellectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
	
    public int updateById(BangChamCongDTO bcc) {
        return bccDAO.update(bcc);
    }

    public ArrayList<BangChamCongDTO> selectByTime(int thang, int nam) {
        return bccDAO.selectByTime(thang, nam);
    }

    
}
