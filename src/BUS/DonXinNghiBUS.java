package BUS;

import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import DAO.DonXinNghiDAO;
import DTO.DonXinNghiDTO;

public class DonXinNghiBUS {
	DonXinNghiDAO dxnDAO = new DonXinNghiDAO();
	
	public ArrayList<DonXinNghiDTO> selectAll(){
		return dxnDAO.selectAll();
	}
	
	public ArrayList<DonXinNghiDTO> selectDonXinNghiDaTao(String maNV){
		return dxnDAO.selectDonXinNghiDaTao(maNV);
	}
	
	public ArrayList<DonXinNghiDTO> getThongTinNgayNghi(String maNV){
		return dxnDAO.getThongTinNgayNghi(maNV);
	}
	
	public String insert(DonXinNghiDTO dxn) {
		if(dxnDAO.insert(dxn)>0)
			return "Tạo đơn xin nghỉ thành công!";
		return "Tạo đơn xin nghỉ thất bại!";
	}
	
	public int demSoLanXinNghiConLai(String maNV) {
		log("maNV="+maNV);
		return dxnDAO.demSoLanXinNghiConLai(maNV);
	}
	
	public String updateTrangThai(String maDon, String trangThai, String maNguoiDuyet) {
		if(dxnDAO.updateTrangThai(maDon, trangThai, maNguoiDuyet)>0)
			return "Cập nhật trạng thái đơn thành công!";
		return "Cập nhật trạng thái đơn thất bại!";
	}
	
	//hàm hiển thị thông tin dòng code
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}
	
}
