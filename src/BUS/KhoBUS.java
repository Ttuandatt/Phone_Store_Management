package BUS;

import java.util.ArrayList;

import DAO.KhoDAO;
import DTO.KhoDTO;
import DTO.NhanVienDTO;

public class KhoBUS {
	KhoDAO khoDAO = new KhoDAO();
	
	
	public ArrayList<KhoDTO> selectAll() {
		return khoDAO.selectAll();
	}
	public ArrayList<NhanVienDTO> getDanhSachNhanVienTheoKho(String tenKho) {
    ArrayList<NhanVienDTO> danhSachNhanVien = khoDAO.getNhanVienByKho(tenKho);
    return danhSachNhanVien;
}
        public KhoDTO getKhoByName(String tenKho) {
    return khoDAO.getKhoByName(tenKho);
}
public String updateKho(KhoDTO kho){
        if(khoDAO.update(kho) > 0)
            return "Cập nhật khách hàng thành công";
        return "Cập nhật khách hàng thất bại";
    }

public ArrayList<Object[]> getDanhSachPBSPTheoKho(String maKho) {
    return khoDAO.getDanhSachPBSPTheoKho(maKho);
}
public String getMaNVQuanLyKho(String maKho) {
    return new KhoDAO().getMaNVQuanLyKho(maKho); // Gọi từ DAO
}


}
