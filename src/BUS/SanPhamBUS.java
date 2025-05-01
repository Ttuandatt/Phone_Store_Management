package BUS;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.ButtonType;


import DAO.SanPhamDAO;
import DAO.ThuongHieuDAO;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
import DTO.ThuongHieuDTO;
import javafx.scene.control.Alert;

public class SanPhamBUS {
	SanPhamDAO spDAO = new SanPhamDAO();
	ValidateProducts validator = new ValidateProducts();
    ThuongHieuDAO thuongHieuDAO = new ThuongHieuDAO();
	public ArrayList<SanPhamDTO> getAllProducts(){
        return spDAO.selectAll();
    }
	
	public String getTenSanPhamByMaPBSP(String maPBSP) {
		return spDAO.getTenSanPhamByMaPBSP(maPBSP);
	}
	
	public ArrayList<PhienBanSanPhamDTO> getAllPBSMBymaSP(String maSP) {
        return spDAO.selectPBSPBymaSP(maSP);
    }

    public ArrayList<ThuongHieuDTO> getAllThuongHieu() {
        return thuongHieuDAO.selectAll();
    }
    
    public ArrayList<SanPhamDTO> getTenSanPhamByMaPBSP2(String maPBSP) {
 		return spDAO.getTenSanPhamByMaPBSP2(maPBSP);
 	}

    public String addProduct(SanPhamDTO product) {
        if(spDAO.insert(product)>0)
        	return "Thêm sản phẩm thành công!";
        return "Thêm sản phẩm thất bại!";
    }
    
    public int updateProduct(SanPhamDTO product){
        // validate
        validator.isRequired(product.getMaSP(), "Mã sản phẩm");
        validator.isRequired(product.getTenSP(), "Tên sản phẩm");
        validator.isRequired(product.getPin(), "Pin sản phẩm");
        validator.isRequired(product.getOS(), "Hệ điều sản phẩm");
        validator.isRequired(product.getCamTruoc(), "Cam trước sản phẩm");
        validator.isRequired(product.getCamSau(), "Cam sau sản phẩm");
        validator.isRequired(product.getXuatXu(), "Xuất xứ sản phẩm");
        validator.isRequired(product.getHinhAnh(), "Hình ảnh sản phẩm");
        if(validator.showError())
            return 0;
        // insert
        if(spDAO.update(product)>0){
            this.showInfoMessage("Sửa sản phẩm thành công!");
            return 1;
        }
        return -1;
    }

    
    public void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait(); // Chờ người dùng bấm OK
    }

    public boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    
    public int deleteProduct(SanPhamDTO product) {
        if(spDAO.delete(product)>0){
            this.showInfoMessage("Xóa sản phẩm thành công!");
            return 1;
        }
        return -1;
    }
    public ArrayList<SanPhamDTO> timKiem(String search_query) {
        return spDAO.TimKiemTheoTen(search_query);
    }


    
}
