package BUS;

import java.util.ArrayList;
import java.util.Optional;

import DAO.ProductsDAO;
import DAO.ThuongHieuDAO;
import DTO.PBSPDTO;
import DTO.ProductsDTO;
import DTO.ThuongHieuDTO;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ProductsBUS {
    ProductsDAO productsDAO = new ProductsDAO();
    ValidateProducts validator = new ValidateProducts();
    ThuongHieuDAO thuongHieuDAO = new ThuongHieuDAO();

    public ArrayList<ProductsDTO> getAllProducts() {
        return productsDAO.selectAll();
    }

    public ArrayList<PBSPDTO> getAllPBSMBymaSP(String maSP) {
        return productsDAO.selectPBSPBymaSP(maSP);
    }

    public ArrayList<ThuongHieuDTO> getAllThuongHieu() {
        return thuongHieuDAO.selectAll();
    }

    public int addProduct(ProductsDTO product) {
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
        if(productsDAO.insert(product)>0){
            this.showInfoMessage("Thêm sản phẩm thành công");
            return 1;
        }
        return -1;
    }
    public int updateProduct(ProductsDTO product){
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
        if(productsDAO.update(product)>0){
            this.showInfoMessage("Sửa sản phẩm thành công");
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

    public int deleteProduct(ProductsDTO product) {
        if(productsDAO.delete(product)>0){
            this.showInfoMessage("Xóa sản phẩm thành công!");
            return 1;
        }
        return -1;
    }
}
