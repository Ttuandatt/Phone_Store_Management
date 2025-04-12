package BUS;

import java.util.ArrayList;

import DAO.PhieuXuatDAO;
import DTO.PhieuXuatDTO;

public class PhieuXuatBUS {
	PhieuXuatDAO pxDAO = new PhieuXuatDAO();
	
	public ArrayList<PhieuXuatDTO> selectAll(){
		return pxDAO.selectAll();
	}
	
	public String insert(PhieuXuatDTO px) {
		if(pxDAO.insert(px)>0)
			return "Thêm phiếu nhập thành công!";
		return "Thêm phiếu nhập thất bại!";
	}
	
	public ArrayList<PhieuXuatDTO> getThongTinPhieuXuat(String mapx){
		return pxDAO.getThongTinPhieuXuat(mapx);
	}
	
	public String getTrangThai(String mapx) {
		return pxDAO.getTrangThai(mapx);
	}
	
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}

	public String updateTrangThai(String mapx, String trangThai) {
		log("mapx="+mapx);
		log("trangThai="+trangThai);
		if(pxDAO.updateTrangThai(mapx, trangThai)>0)
			return "Cập nhật trạng thái thành công!";
		return "Cập nhật trạng thái thất bại!";
	}
}
