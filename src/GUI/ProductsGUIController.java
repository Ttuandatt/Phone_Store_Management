package GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import BUS.SanPhamBUS;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
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
    private SanPhamBUS productsBUS = new SanPhamBUS();

    @FXML
    private Pane versionPane;

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
    private TextField tf_vs_trangthai;

    @FXML
    public void initialize() {
        ArrayList<SanPhamDTO> arr = productsBUS.selectAll();
        insertIntoTableSanPham(arr);
    }

    @FXML
    void handleClickTableProducts(MouseEvent event) {
        productPane.setVisible(true);
        versionPane.setVisible(false);

        SanPhamDTO product = tb_products.getSelectionModel().getSelectedItem();
//        insertIntoTablePBSP(productsBUS.getAllPBSMBymaSP(product.getMaSP()));
        tf_masp.setText(product.getMaSP());
        tf_tensp.setText(product.getTenSP());
        tf_pin.setText(product.getPin());
        tf_os.setText(product.getOS());
        tf_camtruoc.setText(product.getCamTruoc());
        tf_camsau.setText(product.getCamSau());
        tf_xuatxu.setText(product.getXuatXu());
    }

    public void insertIntoTableSanPham(ArrayList<SanPhamDTO> a) {
        ObservableList<SanPhamDTO> dataListSanPham = FXCollections.observableArrayList();
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
        tb_c2_trangthai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        tb_pbsp.setItems(dataListPBSP);

        tb_pbsp.getColumns().addAll(tb_c2_mapb, tb_c2_mausac, tb_c2_ram, tb_c2_rom, tb_c2_giaban, tb_c2_soluong,
                tb_c2_trangthai);
        return;
    }

    @FXML
    void handleClickTableVersion(MouseEvent event) {
    	PhienBanSanPhamDTO version = tb_pbsp.getSelectionModel().getSelectedItem();
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
