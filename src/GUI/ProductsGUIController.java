package GUI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;
 
import BUS.SanPhamBUS;
import BUS.ValidateProducts;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
import DTO.ThuongHieuDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class ProductsGUIController {
    private SanPhamBUS SanPhamBUS = new SanPhamBUS();
    private BUS.PhienBanSanPhamBUS PhienBanSanPhamBUS = new BUS.PhienBanSanPhamBUS();
    private byte[] selectedImageBytes = null;
    private SanPhamDTO product=new SanPhamDTO();
    ValidateProducts validator = new ValidateProducts();
    @FXML
    private Pane versionPane;

    @FXML
    private Button bt_sua_sp;

    @FXML
    private Button bt_xoa_sp;   

    @FXML
    private Button bt_show_pannel_pb;

    @FXML
    private Pane productPane;

    @FXML
    private ImageView imageView;
    @FXML
    private TableColumn<SanPhamDTO, String> tb_c_camsau;

    @FXML
    private TableColumn<SanPhamDTO, String> tb_c_camtruoc;

    @FXML
    private TableColumn<SanPhamDTO, String> tb_c_masp;

    @FXML
    private TableColumn<SanPhamDTO, String> tb_c_os;

    @FXML
    private TableColumn<SanPhamDTO, String> tb_c_pin;

    @FXML
    private TableColumn<SanPhamDTO, String> tb_c_tensp;

    @FXML
    private TableColumn<SanPhamDTO, String> tb_c_xuatxu;

    @FXML
    private TableView<SanPhamDTO> tb_products;
    @FXML
    private TextField textFieldTimKiem;
    @FXML
    private TextField tf_camsau;

    @FXML
    private TextField tf_camtruoc;

    @FXML
    private TextField tf_masp;

    @FXML
    private TextField tf_os;

    @FXML
    private TextField tf_pin;

    @FXML
    private TextField tf_tensp;

    @FXML
    private TextField tf_xuatxu;

    @FXML
    private TableColumn<PhienBanSanPhamDTO, Integer> tb_c2_giaban;

    @FXML
    private TableColumn<PhienBanSanPhamDTO, String> tb_c2_mapb;

    @FXML
    private TableColumn<PhienBanSanPhamDTO, String> tb_c2_mausac;

    @FXML
    private TableColumn<PhienBanSanPhamDTO, String> tb_c2_ram;

    @FXML
    private TableColumn<PhienBanSanPhamDTO, String> tb_c2_rom;

    @FXML
    private TableColumn<PhienBanSanPhamDTO, Integer> tb_c2_soluong;

    @FXML
    private TableColumn<PhienBanSanPhamDTO, String> tb_c2_trangthai;

    @FXML
    private TableView<PhienBanSanPhamDTO> tb_pbsp;

    @FXML
    private TextField tf_vs_giaban;

    @FXML
    private TextField tf_vs_mapb;

    @FXML
    private TextField tf_vs_mausac;

    @FXML
    private TextField tf_vs_ram;

    @FXML
    private TextField tf_vs_rom;

    @FXML
    private TextField tf_vs_soluong;

    @FXML
    private ComboBox<ThuongHieuDTO> cbb_thuonghieu;

    @FXML
    private TextField tf_vs_masp;


    @FXML
    public void initialize() {
        ArrayList<SanPhamDTO> arr = SanPhamBUS.getAllProducts();
        selectedImageBytes = null;
        insertIntoTableSanPham(arr);
        upsetComboBoxThuongHieu(null);

    }

    @FXML
    void handleClickTableProducts(MouseEvent event) {
        product = tb_products.getSelectionModel().getSelectedItem();
        if(product!=null){
            productPane.setVisible(true);
            versionPane.setVisible(false);
            insertIntoTablePBSP(SanPhamBUS.getAllPBSMBymaSP(product.getMaSP()));
            tf_masp.setText(product.getMaSP());
            tf_tensp.setText(product.getTenSP());
            tf_pin.setText(product.getPin());
            tf_os.setText(product.getOS());
            tf_camtruoc.setText(product.getCamTruoc());
            tf_camsau.setText(product.getCamSau());
            tf_xuatxu.setText(product.getXuatXu());
            upsetComboBoxThuongHieu(product.getMaTH());
            // bt_show_pannel_pb.setVisible(true);
            setupButtonSP(true);
            selectedImageBytes=product.getHinhAnh();
            ByteArrayInputStream bis = new ByteArrayInputStream(selectedImageBytes);
            Image image = new Image(bis);
            imageView.setImage(image); 
            tf_vs_masp.setText(product.getMaSP());
        }
        else 
        setupButtonSP(false);
        // bt_show_pannel_pb.setVisible(false);
    }

    public void insertIntoTableSanPham(ArrayList<SanPhamDTO> a) {
        ObservableList<SanPhamDTO> dataListSanPham = FXCollections.observableArrayList(a);

        tb_products.getColumns().clear();
        tb_c_masp.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        tb_c_tensp.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        tb_c_pin.setCellValueFactory(new PropertyValueFactory<>("pin"));
        tb_c_os.setCellValueFactory(new PropertyValueFactory<>("OS"));
        tb_c_camtruoc.setCellValueFactory(new PropertyValueFactory<>("camTruoc"));
        tb_c_camsau.setCellValueFactory(new PropertyValueFactory<>("camSau"));
        tb_c_xuatxu.setCellValueFactory(new PropertyValueFactory<>("xuatXu"));

        tb_products.setItems(dataListSanPham);

        tb_products.getColumns().addAll(tb_c_masp, tb_c_tensp, tb_c_pin, tb_c_os, tb_c_camtruoc, tb_c_camsau,
                tb_c_xuatxu);
    }

    public void insertIntoTablePBSP(ArrayList<PhienBanSanPhamDTO> a) {
        ObservableList<PhienBanSanPhamDTO> dataListPBSP = FXCollections.observableArrayList();
        dataListPBSP.addAll(a);
        tb_pbsp.getColumns().clear();
        tb_c2_mapb.setCellValueFactory(new PropertyValueFactory<>("maPBSP"));
        tb_c2_mausac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
        tb_c2_ram.setCellValueFactory(new PropertyValueFactory<>("ram"));
        tb_c2_rom.setCellValueFactory(new PropertyValueFactory<>("rom"));
        tb_c2_giaban.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        tb_c2_soluong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        tb_pbsp.setItems(dataListPBSP);

        tb_pbsp.getColumns().addAll(tb_c2_mapb, tb_c2_mausac, tb_c2_ram, tb_c2_rom, tb_c2_giaban, tb_c2_soluong);
        return;
    }

    @FXML
    void handleClickTableVersion(MouseEvent event) {
        PhienBanSanPhamDTO version = tb_pbsp.getSelectionModel().getSelectedItem();
        if (version!=null) {
            tf_vs_mapb.setText(version.getMaPBSP());
            tf_vs_mausac.setText(version.getMauSac());
            tf_vs_ram.setText(version.getRam());
            tf_vs_rom.setText(version.getRom());
            tf_vs_giaban.setText(version.getGiaBan() + "");
            tf_vs_soluong.setText(version.getSoLuong() + "");
            
            versionPane.setVisible(true);
            productPane.setVisible(false);
        }
        

    }

    @FXML
    void handlePressTimKiem(KeyEvent event) {
        insertIntoTableSanPham(SanPhamBUS.timKiem(textFieldTimKiem.getText()));
    }

    @FXML
    void handleClickButtonResetVersion(MouseEvent event) {
        ResetVersion();
    }
    void ResetVersion() {
        tf_vs_mapb.setText("");
        tf_vs_mausac.setText("");
        tf_vs_ram.setText("");
        tf_vs_rom.setText("");
        tf_vs_giaban.setText("");
        tf_vs_soluong.setText("");
    }
    @FXML
    void handleClickButtonResetProduct(MouseEvent event) {
        ResetProduct();
    }
    void ResetProduct() {
        tf_masp.setText("");
        tf_tensp.setText("");
        tf_pin.setText("");
        tf_os.setText("");
        tf_camtruoc.setText("");
        tf_camsau.setText("");
        tf_xuatxu.setText("");
        selectedImageBytes =null;
        imageView.setImage(null);
        setupButtonSP(false);
        // bt_show_pannel_pb.setVisible(false);
    }
    void upsetComboBoxThuongHieu(String maTH) {
        ObservableList<ThuongHieuDTO> listComboBoxthuonghieu = FXCollections.observableArrayList(SanPhamBUS.getAllThuongHieu());
        cbb_thuonghieu.setItems(listComboBoxthuonghieu);
        if(maTH!=null) {
            for (ThuongHieuDTO item : listComboBoxthuonghieu) {
                if (item.getMaTH().equals(maTH)) {
                    cbb_thuonghieu.setValue(item);
                    break;
                }
            }
        }
        else {
            cbb_thuonghieu.setValue(listComboBoxthuonghieu.get(0));
        }
    }

    @FXML
    void handleCilckChooseFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Chọn ảnh", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null); // Hiển thị hộp thoại chọn tệp
        if (file != null) {
            try {
                selectedImageBytes  = Files.readAllBytes(file.toPath());
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
    
        }

    }

    @FXML
    void handleClickAddProduct(MouseEvent event) {
        int flag = SanPhamBUS.addProduct(new SanPhamDTO(tf_masp.getText(), tf_tensp.getText(), tf_pin.getText(), tf_os.getText(), tf_camtruoc.getText(), tf_camsau.getText(), tf_xuatxu.getText(),selectedImageBytes,cbb_thuonghieu.getSelectionModel().getSelectedItem().getMaTH()));
        if(flag==1){
            ResetProduct();
            initialize();
        }else if (flag==-1) {
            SanPhamBUS.showInfoMessage("Mã sản phẩm đã tồn tại!");
        }
    }
    @FXML
    void handleCilckQLPB(MouseEvent event) {
        versionPane.setVisible(true);
        productPane.setVisible(false);
    }

    @FXML
    void handleClickSuaSP(MouseEvent event) {
        int flag = SanPhamBUS.updateProduct(new SanPhamDTO(tf_masp.getText(), tf_tensp.getText(), tf_pin.getText(), tf_os.getText(), tf_camtruoc.getText(), tf_camsau.getText(), tf_xuatxu.getText(), selectedImageBytes, cbb_thuonghieu.getSelectionModel().getSelectedItem().getMaTH()));
        if(flag==1){
            ResetProduct();
            initialize();
        }else if (flag==-1) {
            SanPhamBUS.showInfoMessage("Sửa sản phẩm không thành công, hãy thử lại!");
        }
        
    }
    @FXML
    void handleClickXoaSP(MouseEvent event) {
        if(SanPhamBUS.showConfirmation("Bạn có chắc muốn xóa sản phẩm có mã sản phẩm là "+tf_masp.getText()+" không ?")){
            int flag = SanPhamBUS.deleteProduct(new SanPhamDTO(tf_masp.getText(), tf_tensp.getText(), tf_pin.getText(), tf_os.getText(), tf_camtruoc.getText(), tf_camsau.getText(), tf_xuatxu.getText(), selectedImageBytes, cbb_thuonghieu.getSelectionModel().getSelectedItem().getMaTH()));
            if(flag==1){
                ResetProduct();
                initialize();
            }else if (flag==-1) {
                SanPhamBUS.showInfoMessage("Xóa sản phẩm không thành công, hãy thử lại!");
            }
        }
    }
    void setupButtonSP(boolean turn_on) {
        bt_show_pannel_pb.setVisible(turn_on);
        bt_sua_sp.setDisable(!turn_on);
        bt_xoa_sp.setDisable(!turn_on);
    }
    @FXML
    void handleCilckSuaPB(MouseEvent event) {
        validator.isNumber(tf_vs_giaban.getText(), "Giá bán");
        validator.isNumber(tf_vs_soluong.getText(), "Số lượng");
        if(validator.showError())
            return;
        int flag=PhienBanSanPhamBUS.updatePhienBanSanPham(new PhienBanSanPhamDTO(tf_vs_mapb.getText(),tf_vs_mausac.getText(),tf_vs_ram.getText(),tf_vs_rom.getText(),Double.parseDouble(tf_vs_giaban.getText()),Integer.parseInt(tf_vs_soluong.getText()),tf_vs_masp.getText()));
        if(flag==1){
            insertIntoTablePBSP(SanPhamBUS.getAllPBSMBymaSP(tf_vs_masp.getText()));
            ResetVersion();
            return;
        }
        else if(flag==-1) PhienBanSanPhamBUS.showInfoMessage("Có lỗi xảy ra vui lòng thử lại!");
    }

    @FXML
    void handleCilckXoaPB(MouseEvent event) {
        if(SanPhamBUS.showConfirmation("Bạn có chắc muốn xóa Phiên Bản Sản Phẩm có mã là "+tf_vs_mapb.getText()+" không ?")){
            validator.isNumber(tf_vs_giaban.getText(), "Giá bán");
            validator.isNumber(tf_vs_soluong.getText(), "Số lượng");
            if(validator.showError())
                return;
            int flag=PhienBanSanPhamBUS.deletePhienBanSanPham(new PhienBanSanPhamDTO(tf_vs_mapb.getText(),tf_vs_mausac.getText(),tf_vs_ram.getText(),tf_vs_rom.getText(),Double.parseDouble(tf_vs_giaban.getText()),Integer.parseInt(tf_vs_soluong.getText()),tf_vs_masp.getText()));
            if(flag==1){
                insertIntoTablePBSP(SanPhamBUS.getAllPBSMBymaSP(tf_vs_masp.getText()));
                ResetVersion();
                return;
            }
            else if(flag==-1) PhienBanSanPhamBUS.showInfoMessage("Có lỗi xảy ra vui lòng thử lại!");
        }
    }
    @FXML
    void handleCilckAddPB(MouseEvent event) {
        validator.isNumber(tf_vs_giaban.getText(), "Giá bán");
        validator.isNumber(tf_vs_soluong.getText(), "Số lượng");
        if(validator.showError())
            return;
        int flag=PhienBanSanPhamBUS.addPhienBanSanPham(new PhienBanSanPhamDTO(tf_vs_mapb.getText(),tf_vs_mausac.getText(),tf_vs_ram.getText(),tf_vs_rom.getText(),Double.parseDouble(tf_vs_giaban.getText()),Integer.parseInt(tf_vs_soluong.getText()),tf_vs_masp.getText()));
        if(flag==1){
            insertIntoTablePBSP(SanPhamBUS.getAllPBSMBymaSP(tf_vs_masp.getText()));
            ResetVersion();
            return;
        }
        else if(flag==-1) PhienBanSanPhamBUS.showInfoMessage("Phiên bản sản phẩm đã tồn tại!");
    }
}