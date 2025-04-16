package BUS;

import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import DAO.DonXinNghiDAO;
import DTO.DonXinNghiDTO;

public class DonXinNghiBUS {
	DonXinNghiDAO dxnDAO = new DonXinNghiDAO();
	
	public ArrayList<DonXinNghiDTO> getThongTinNgayNghi(String maNV){
		return dxnDAO.getThongTinNgayNghi(maNV);
	}
}
