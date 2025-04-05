package BUS;

import java.util.ArrayList;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

public class PhieuNhapBUS {
	PhieuNhapDAO pnDAO = new PhieuNhapDAO();
	
	public ArrayList<PhieuNhapDTO> selectAll(){
		return pnDAO.selectAll();
	}
	
	public String insert(PhieuNhapDTO pn) {
		if(pnDAO.insert(pn)>0)
			return "Thêm phiếu nhập thành công!";
		return "Thêm phiếu nhập thất bại!";
	}
	
	public ArrayList<PhieuNhapDTO> getThongTinPhieuNhap(String maPN){
		return pnDAO.getThongTinPhieuNhap(maPN);
	}
	
	public String getTrangThai(String mapn) {
		return pnDAO.getTrangThai(mapn);
	}
	
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}

	public String updateTrangThai(String maPN, String trangThai) {
		log("maPN="+maPN);
		log("trangThai="+trangThai);
		if(pnDAO.updateTrangThai(maPN, trangThai)>0)
			return "Cập nhật trạng thái thành công!";
		return "Cập nhật trạng thái thất bại!";
	}
}
