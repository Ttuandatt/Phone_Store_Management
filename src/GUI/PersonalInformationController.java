package GUI;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import BUS.BangChamCongBUS;
import BUS.BangLuongBUS;
import BUS.DangNhapBUS;
import BUS.NhanVienBUS;
import DTO.BangChamCongDTO;
import DTO.BangLuongDTO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PersonalInformationController {
    DecimalFormat formatter = new DecimalFormat("#,###");

    BangChamCongBUS bccBUS = new BangChamCongBUS();
    NhanVienBUS nvBUS = new NhanVienBUS();
    NhanVienDTO nv;
    BangChamCongDTO bcc = new BangChamCongDTO();
    BangLuongBUS blBUS = new BangLuongBUS();
    ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<BangLuongDTO>();
    @FXML
    public void initialize() {
        // get data from database
        arrBangLuong = blBUS.getBangLuongByNV(DangNhapBUS.maNV);
        //clearn cbYear and cbMonth
        cbMonth.getItems().clear();
        cbYear.getItems().clear();
        //set disable button bt_update
        bt_update.setDisable(true);
        nv = nvBUS.selectById(DangNhapBUS.maNV);
        labelName.setText(nv.getHoTen());
        labelEmail.setText(nv.getEmail());
        tfName.setText(nv.getHoTen());
        tfNumberPhone.setText(nv.getSoDienThoai());
        tfAddress.setText(nv.getDiaChi());
        tfBirthday.setText(nv.getNgaySinh().toString());
        if (nv.getGioiTinh().equals("Nam")) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }
        for (int i = 1; i <= 12; i++) {
            cbMonth.getItems().add(i);
        }
        int currentYear = java.time.LocalDate.now().getYear();
        for (int year = 1980; year <= 2025; year++) {
            cbYear.getItems().add(year);
        }
        //Mặc định chọn tháng và nam hiện tại
        cbMonth.getSelectionModel().select(java.time.LocalDate.now().getMonthValue() - 1); // chọn tháng hiện tại
        cbYear.getSelectionModel().select(java.time.LocalDate.now().getYear() - 1980); // chọn năm hiện tại
        setBangLuong(arrBangLuong);
        setChamCong(DangNhapBUS.maNV, java.time.LocalDate.now().getMonthValue(), java.time.LocalDate.now().getYear());

        root.setOnMousePressed(event -> {
            root.requestFocus(); // click nền để thoát focus khỏi TextField
        });
    }
    public void setBangLuong(ArrayList<BangLuongDTO> arrBangLuong) {
        ObservableList<BangLuongDTO> bangLuongDTOs = FXCollections.observableArrayList(arrBangLuong);

        tv_heso.setCellValueFactory(new PropertyValueFactory<>("heSo"));

        tv_thoigian.setCellValueFactory(cellData -> {
            BangLuongDTO dto = cellData.getValue();
            return new SimpleStringProperty(dto.getThangLuong() + "/" + dto.getNamLuong());
        });

        tv_tongluong.setCellValueFactory(cellData -> {
            double tongLuong = cellData.getValue().getThucNhan();
            String formatted = formatter.format(tongLuong);
            return new SimpleStringProperty(formatted);
        });
        
        tb_bangluong.getColumns().clear();
        tb_bangluong.setItems(bangLuongDTOs);
        tb_bangluong.getColumns().addAll(tv_heso, tv_thoigian, tv_tongluong);
    }
    // set bang cham cong, tham so la nv va thang nam
    public void setChamCong(String maNV, int thang, int nam) {
        bcc = bccBUS.getBangChamCongByThangNam(maNV, thang, nam);
        if (bcc != null) {
            tf_songaycong.setText(String.valueOf(bcc.getSoNgayLam()));
            tf_songaynghikhongphep.setText(String.valueOf(bcc.getSoNgayNghiKhongPhep()));
            tf_songaynghiphep.setText(String.valueOf(bcc.getSoNgayNghiPhepCoLuong()));
            tf_songaynghiphepkhongluong.setText(String.valueOf(bcc.getSoNgayNghiPhepKhongLuong()));
            tf_sogiotangca.setText(String.valueOf(bcc.getSoGioOT()));
        } else {
            System.out.println("Khong tim thay bang cham cong");
        }
    }
    @FXML
    void handleClickFemale(MouseEvent event) {
        rbFemale.setSelected(true);
        rbMale.setSelected(false);
        bt_update.setDisable(false);

    }

    @FXML
    void handleClickMale(MouseEvent event) {
        rbMale.setSelected(true);
        rbFemale.setSelected(false);
        bt_update.setDisable(false);

    }

    @FXML
    void handleKeyPressAddress(KeyEvent event) {
        bt_update.setDisable(false);
    }

    @FXML
    void handleKeyPressBirthday(KeyEvent event) {
        bt_update.setDisable(false);

    }

    @FXML
    void handleKeyPressName(KeyEvent event) {
        bt_update.setDisable(false);

    }

    @FXML
    void handleKeyPressPhoneNumber(KeyEvent event) {
        bt_update.setDisable(false);

    }
    @FXML
    void handleChangeMonth(ActionEvent event) {
        int month = cbMonth.getSelectionModel().getSelectedItem();
        int year = cbYear.getSelectionModel().getSelectedItem();
        setChamCong(DangNhapBUS.maNV, month, year);
        System.out.println("Tháng: " + month + " Năm: " + year);
    }

    @FXML
    void handleChangeYear(ActionEvent event) {
        int month = cbMonth.getSelectionModel().getSelectedItem();
        int year = cbYear.getSelectionModel().getSelectedItem();
        setChamCong(DangNhapBUS.maNV, month, year);
    }
    @FXML
    void handleClickUpdate(MouseEvent event) {
        String maNV = DangNhapBUS.maNV;
        String hoTen = tfName.getText();
        String soDienThoai = tfNumberPhone.getText();
        String diaChi = tfAddress.getText();
        String ngaySinh = tfBirthday.getText();
        String gioiTinh = rbMale.isSelected() ? "Nam" : "Nữ";
        NhanVienDTO nv = new NhanVienDTO(maNV, hoTen, soDienThoai, diaChi, ngaySinh, gioiTinh);
        nvBUS.updatePersonalInfo(nv);
        //load lai du lieu
        initialize();
        bt_update.setDisable(true);

    }

    @FXML
    private Pane root;
    
    @FXML
    private Label tf_sogiotangca;

    @FXML
    private Label tf_songaycong;

    @FXML
    private Label tf_songaynghikhongphep;

    @FXML
    private Label tf_songaynghiphep;

    @FXML
    private Label tf_songaynghiphepkhongluong;

    @FXML
    private TableColumn<BangLuongDTO, Float> tv_heso;

    @FXML
    private TableColumn<BangLuongDTO, String> tv_thoigian;

    @FXML
    private TableColumn<BangLuongDTO, String> tv_tongluong;

    
    @FXML
    private TableView<BangLuongDTO> tb_bangluong;

    @FXML
    private Button bt_update;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelName;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfBirthday;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfNumberPhone;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbMale;

    @FXML
    private ComboBox<Integer> cbMonth;

    @FXML
    private ComboBox<Integer> cbYear;

    
}
