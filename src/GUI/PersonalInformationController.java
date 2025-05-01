package GUI;

import java.util.ArrayList;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import BUS.BangChamCongBUS;
import BUS.DangNhapBUS;
import BUS.NhanVienBUS;
import DTO.BangChamCongDTO;
import DTO.NhanVienDTO;
import javafx.fxml.FXML;

public class PersonalInformationController {
    BangChamCongBUS bccBUS = new BangChamCongBUS();
    NhanVienBUS nvBUS = new NhanVienBUS();
    NhanVienDTO nv;

    @FXML
    public void initialize() {
        //clearn cbYear and cbMonth
        cbMonth.getItems().clear();
        cbYear.getItems().clear();

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
    }

    ArrayList<BangChamCongDTO> listBCC = new ArrayList<BangChamCongDTO>();

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

    @FXML
    void handleClickFemale(MouseEvent event) {
        rbFemale.setSelected(true);
        rbMale.setSelected(false);
    }

    @FXML
    void handleClickMale(MouseEvent event) {
        rbMale.setSelected(true);
        rbFemale.setSelected(false);
    }

    
}
