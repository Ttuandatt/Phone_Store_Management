package BUS;

import java.util.ArrayList;

import DAO.PhienBanSanPhamDAO;
import DAO.SanPhamDAO;
import DTO.PhienBanSanPhamDTO;
import javafx.scene.control.Alert;

public class PhienBanSanPhamBUS {
	PhienBanSanPhamDAO pbspDAO = new PhienBanSanPhamDAO();
	ValidateProducts validator = new ValidateProducts();
	public ArrayList<PhienBanSanPhamDTO> selectAll(){
		return pbspDAO.selectAll();
	}
	public int addPhienBanSanPham(PhienBanSanPhamDTO pbsp){
		validator.isRequired(pbsp.getMaPBSP(), "Mã Phiên Bản Sản Phẩm");
		validator.isRequired(pbsp.getMauSac(), "Màu sắc");
		validator.isRequired(pbsp.getRam(), "RAM");
		validator.isRequired(pbsp.getRom(), "ROM");
		validator.isRequired(pbsp.getGiaBan(), "Giá bán");
		validator.isRequired(pbsp.getSoLuong(), "Số lượng");
		if(validator.showError())
            return 0;
		if(pbspDAO.insert(pbsp)>0){
            this.showInfoMessage("Thêm sản phẩm thành công");
            return 1;
        }
        return -1;
	}
	public int updatePhienBanSanPham(PhienBanSanPhamDTO pbsp){
		validator.isRequired(pbsp.getMaPBSP(), "Mã Phiên Bản Sản Phẩm");
		validator.isRequired(pbsp.getMauSac(), "Màu sắc");
		validator.isRequired(pbsp.getRam(), "RAM");
		validator.isRequired(pbsp.getRom(), "ROM");
		validator.isRequired(pbsp.getGiaBan(), "Giá bán");
		validator.isRequired(pbsp.getSoLuong(), "Số lượng");
		if(validator.showError())
            return 0;
		if(pbspDAO.update(pbsp)>0){
            this.showInfoMessage("Sửa Phiên Bản Sản Phẩm thành công");
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
	public int deletePhienBanSanPham(PhienBanSanPhamDTO pbsp) {
		validator.isRequired(pbsp.getMaPBSP(), "Mã Phiên Bản Sản Phẩm");
		if(validator.showError())
            return 0;
		if(pbspDAO.delete(pbsp)>0){
            this.showInfoMessage("Xóa Phiên Bản Sản Phẩm thành công");
            return 1;
        }
        return -1;
	}
}
