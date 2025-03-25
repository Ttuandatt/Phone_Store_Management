package GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

<<<<<<< HEAD
import BUS.SanPhamBUS;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
=======
import BUS.ProductsBUS;
import DTO.PBSPDTO;
import DTO.ProductsDTO;
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ProductsGUIController {
<<<<<<< HEAD
    private SanPhamBUS productsBUS = new SanPhamBUS();
=======
    private ProductsBUS productsBUS = new ProductsBUS();
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7

    @FXML
    private Pane versionPane;

    @FXML
    private Pane productPane;

    @FXML
    private ImageView imageView;
    @FXML
<<<<<<< HEAD
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
=======
    private TableColumn<ProductsDTO, String> tb_c_camsau;

    @FXML
    private TableColumn<ProductsDTO, String> tb_c_camtruoc;

    @FXML
    private TableColumn<ProductsDTO, String> tb_c_masp;

    @FXML
    private TableColumn<ProductsDTO, String> tb_c_os;

    @FXML
    private TableColumn<ProductsDTO, String> tb_c_pin;

    @FXML
    private TableColumn<ProductsDTO, String> tb_c_tensp;

    @FXML
    private TableColumn<ProductsDTO, String> tb_c_xuatxu;

    @FXML
    private TableView<ProductsDTO> tb_products;
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
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
<<<<<<< HEAD
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
=======
    private TableColumn<PBSPDTO, Integer> tb_c2_giaban;

    @FXML
    private TableColumn<PBSPDTO, String> tb_c2_mapb;

    @FXML
    private TableColumn<PBSPDTO, String> tb_c2_mausac;

    @FXML
    private TableColumn<PBSPDTO, String> tb_c2_ram;

    @FXML
    private TableColumn<PBSPDTO, String> tb_c2_rom;

    @FXML
    private TableColumn<PBSPDTO, Integer> tb_c2_soluong;

    @FXML
    private TableColumn<PBSPDTO, String> tb_c2_trangthai;

    @FXML
    private TableView<PBSPDTO> tb_pbsp;
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7

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
    private TextField tf_vs_trangthai;

    @FXML
    public void initialize() {
<<<<<<< HEAD
        ArrayList<SanPhamDTO> arr = productsBUS.selectAll();
=======
        ArrayList<ProductsDTO> arr = productsBUS.getAllProducts();
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
        insertIntoTableSanPham(arr);
    }

    @FXML
    void handleClickTableProducts(MouseEvent event) {
        productPane.setVisible(true);
        versionPane.setVisible(false);

<<<<<<< HEAD
        SanPhamDTO product = tb_products.getSelectionModel().getSelectedItem();
//        insertIntoTablePBSP(productsBUS.getAllPBSMBymaSP(product.getMaSP()));
=======
        ProductsDTO product = tb_products.getSelectionModel().getSelectedItem();
        insertIntoTablePBSP(productsBUS.getAllPBSMBymaSP(product.getMaSP()));
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
        tf_masp.setText(product.getMaSP());
        tf_tensp.setText(product.getTenSP());
        tf_pin.setText(product.getPin());
        tf_os.setText(product.getOS());
        tf_camtruoc.setText(product.getCamTruoc());
        tf_camsau.setText(product.getCamSau());
        tf_xuatxu.setText(product.getXuatXu());
    }

<<<<<<< HEAD
    public void insertIntoTableSanPham(ArrayList<SanPhamDTO> a) {
        ObservableList<SanPhamDTO> dataListSanPham = FXCollections.observableArrayList();
=======
    public void insertIntoTableSanPham(ArrayList<ProductsDTO> a) {
        ObservableList<ProductsDTO> dataListSanPham = FXCollections.observableArrayList();
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
        dataListSanPham.addAll(a);
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

<<<<<<< HEAD
    public void insertIntoTablePBSP(ArrayList<PhienBanSanPhamDTO> a) {
        ObservableList<PhienBanSanPhamDTO> dataListPBSP = FXCollections.observableArrayList();
=======
    public void insertIntoTablePBSP(ArrayList<PBSPDTO> a) {
        ObservableList<PBSPDTO> dataListPBSP = FXCollections.observableArrayList();
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
        dataListPBSP.addAll(a);
        tb_pbsp.getColumns().clear();
        tb_c2_mapb.setCellValueFactory(new PropertyValueFactory<>("maPBSP"));
        tb_c2_mausac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
        tb_c2_ram.setCellValueFactory(new PropertyValueFactory<>("ram"));
        tb_c2_rom.setCellValueFactory(new PropertyValueFactory<>("rom"));
        tb_c2_giaban.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        tb_c2_soluong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tb_c2_trangthai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        tb_pbsp.setItems(dataListPBSP);

        tb_pbsp.getColumns().addAll(tb_c2_mapb, tb_c2_mausac, tb_c2_ram, tb_c2_rom, tb_c2_giaban, tb_c2_soluong,
                tb_c2_trangthai);
        return;
    }

    @FXML
    void handleClickTableVersion(MouseEvent event) {
<<<<<<< HEAD
    	PhienBanSanPhamDTO version = tb_pbsp.getSelectionModel().getSelectedItem();
=======
        PBSPDTO version = tb_pbsp.getSelectionModel().getSelectedItem();
>>>>>>> 6191ecdcdd96a8462964e30445b416b9c62f3cf7
        tf_vs_mapb.setText(version.getMaPBSP());
        tf_vs_mausac.setText(version.getMauSac());
        tf_vs_ram.setText(version.getRam());
        tf_vs_rom.setText(version.getRom());
        tf_vs_giaban.setText(version.getGiaBan() + "");
        tf_vs_soluong.setText(version.getSoLuong() + "");
        tf_vs_trangthai.setText(version.getTrangThai());

        versionPane.setVisible(true);
        productPane.setVisible(false);
    }

    @FXML
    void handlePressTimKiem(KeyEvent event) {
        System.out.println("checked");
    }

    @FXML
    void handleClickButtonResetVersion(MouseEvent event) {
        tf_vs_mapb.setText("");
        tf_vs_mausac.setText("");
        tf_vs_ram.setText("");
        tf_vs_rom.setText("");
        tf_vs_giaban.setText("");
        tf_vs_soluong.setText("");
        tf_vs_trangthai.setText("");
    }

    @FXML
    void handleClickButtonResetProduct(MouseEvent event) {
        tf_masp.setText("");
        tf_tensp.setText("");
        tf_pin.setText("");
        tf_os.setText("");
        tf_camtruoc.setText("");
        tf_camsau.setText("");
        tf_xuatxu.setText("");
    }
}
