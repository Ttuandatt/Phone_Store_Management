package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.ArrayList;

public class KhachHangBUS {
    KhachHangDAO khDAO = new KhachHangDAO();
    
    public ArrayList<KhachHangDTO> selectAll(){
        return khDAO.selectAll();
    }
    
    public String addKhachHang(KhachHangDTO kh){
        if(khDAO.has(kh.getMaKH()))
            return "Mã khách hàng đã tồn tại";
        if(khDAO.insert(kh) > 0)
            return "Thêm khách hàng thành công";
        return "Thêm thất bại";
    }
    
    public String deleteKhachHang(KhachHangDTO kh){
        if(khDAO.delete(kh) > 0)
            return "Xóa khách hàng thành công";
        return "Xóa khách hàng thất bại";
    }
    
    public String updateKhachHang(KhachHangDTO kh){
        if(khDAO.update(kh) > 0)
            return "Cập nhật khách hàng thành công";
        return "Cập nhật khách hàng thất bại";
    }
 
    public int getSoLuongKhachHang() {
        return khDAO.selectAll().size();
    }

    public String insert(KhachHangDTO kh) {
        if(khDAO.insert(kh) > 0)
            return "Thêm khách hàng thành công!";
        return "Thêm khách hàng thất bại!";
    }
    
    public KhachHangDTO getByName(String tenkh) {
        return khDAO.getByName(tenkh);
    }
    
    public KhachHangDTO selectById(String makh) {
        return khDAO.selectById(makh);
    }
}
