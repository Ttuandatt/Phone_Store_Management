package GUI;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import BUS.BangChamCongBUS;
import BUS.BangLuongBUS;
import BUS.DangNhapBUS;
import BUS.NhanVienBUS;
import DTO.BangChamCongDTO;
import DTO.BangLuongDTO;
import DTO.NhanVienDTO;
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
    private byte[] selectedImageBytes = null;

    @FXML
    public void initialize() {
        // get data from database
        arrBangLuong = blBUS.getBangLuongByNV(DangNhapBUS.maNV);
        // clearn cbYear and cbMonth
        cbMonth.getItems().clear();
        cbYear.getItems().clear();
        // set disable button bt_update
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
        for (int year = 1980; year <= currentYear; year++) {
            cbYear.getItems().add(year);
        }
        // Mặc định chọn tháng và nam hiện tại
        cbMonth.getSelectionModel().select(java.time.LocalDate.now().getMonthValue() - 1); // chọn tháng hiện tại
        cbYear.getSelectionModel().select(java.time.LocalDate.now().getYear() - 1980); // chọn năm hiện tại
        setBangLuong(arrBangLuong);
        setChamCong(DangNhapBUS.maNV, java.time.LocalDate.now().getMonthValue(), java.time.LocalDate.now().getYear());
        // set image avatar
        showImageToPane(nv.getHinhAnh());
        // Gắn sự kiện click để chọn ảnh mới
        pn_image.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn ảnh đại diện");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(pn_image.getScene().getWindow());

            if (selectedFile != null) {
                try {
                    selectedImageBytes = Files.readAllBytes(selectedFile.toPath());
                    showImageToPane(selectedImageBytes);
                    bt_update.setDisable(false); // Kích hoạt nút cập nhật khi có ảnh mới
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        root.setOnMousePressed(event -> {
            root.requestFocus(); // click nền để thoát focus khỏi TextField
        });
    }

    private void showImageToPane(byte[] imageBytes) {
        try {
            Image image = (imageBytes == null)
                    ? new Image("file:src/img/th (4).jpeg")
                    : new Image(new ByteArrayInputStream(imageBytes));

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(false);

            // Bo tròn
            Circle clip = new Circle(100, 100, 100);
            imageView.setClip(clip);

            pn_image.getChildren().clear();
            pn_image.getChildren().add(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBangLuong(ArrayList<BangLuongDTO> arrBangLuong) {
        ObservableList<BangLuongDTO> bangLuongDTOs = FXCollections.observableArrayList(arrBangLuong);
        
        tv_heso.setCellValueFactory(new PropertyValueFactory<>("heSo"));

        tv_thoigian.setCellValueFactory(cellData -> {
            BangLuongDTO dto = cellData.getValue();
            return new SimpleStringProperty(dto.getThangLuong() + "/" + dto.getNamLuong());
        });

        tv_luongCB.setCellValueFactory(cellData -> {
            //BangLuongDTO dto = cellData.getValue();
            String formatted = formatter.format(cellData.getValue().getLuongCB());
            return new SimpleStringProperty(formatted);
        });
        
        tv_luongthucte.setCellValueFactory(cellData -> {
            BangLuongDTO dto = cellData.getValue();
            String mabcc = dto.getMaLuong().replaceFirst("BL", "CC");
            BangChamCongDTO bcc = bccBUS.selectById(mabcc);
            double luongThucTe = 0.0;
            if (bcc != null) {
                luongThucTe = (dto.getLuongCB() * dto.getHeSo()) / 24 * bcc.getSoNgayLam();
            }
            String formatted = formatter.format(luongThucTe);
            return new SimpleStringProperty(formatted);
        });

        tv_luongOT.setCellValueFactory(cellData -> {
            BangLuongDTO dto = cellData.getValue();
            String mabcc = dto.getMaLuong().replaceFirst("BL", "CC");
            BangChamCongDTO bcc = bccBUS.selectById(mabcc);
            double luongOT = 0.0;
            if (bcc != null) {
                double t = (dto.getLuongCB() * dto.getHeSo()) / (24 * 8);
                luongOT = t * bcc.getSoGioOTNgayThuong()
                        + t * 2 * bcc.getSoGioOTCN()
                        + t * 3 * bcc.getSoGioOTNgayLe();
            }
            String formatted = formatter.format(luongOT);
            return new SimpleStringProperty(formatted);
        });

        tv_phucap.setCellValueFactory(cellData -> {
            BangLuongDTO dto = cellData.getValue();
            double tongPhuCap = dto.getPhuCapAnTrua() + dto.getPhuCapDiLai();
            String formatted = formatter.format(tongPhuCap);
            return new SimpleStringProperty(formatted);
        });

        tv_luongthuong.setCellValueFactory(cellData -> {
            String formatted = formatter.format(cellData.getValue().getThuong());
            return new SimpleStringProperty(formatted);
        });

        tv_cackhoantru.setCellValueFactory(cellData -> {
            BangLuongDTO dto = cellData.getValue();
            double tongTru = dto.getBhxh() + dto.getBhyt() + dto.getBhtn() + dto.getThue() + dto.getTamUng();
            log("maBL="+dto.getMaLuong());
            log("BHTN="+dto.getBhtn());
            log("BHXH="+dto.getBhxh());
            log("BHYT="+dto.getBhyt());
            log("thue="+dto.getThue());
            log("tamUng="+dto.getTamUng());
            log("tongTru="+tongTru);


            
            String formatted = formatter.format(tongTru);
            return new SimpleStringProperty(formatted);
        });


        tv_thucnhan.setCellValueFactory(cellData -> {
            BangLuongDTO dto = cellData.getValue();
            String mabcc = dto.getMaLuong().replaceFirst("BL", "CC");
            BangChamCongDTO bcc = bccBUS.selectById(mabcc);

            // Tính lương thực tế
            double luongThucTe = 0.0;
            if (bcc != null) {
                luongThucTe = (dto.getLuongCB() * dto.getHeSo()) / 24 * bcc.getSoNgayLam();
            }

            // Tính lương OT
            double luongOT = 0.0;
            if (bcc != null) {
                double t = (dto.getLuongCB() * dto.getHeSo()) / (24 * 8);
                luongOT = t * bcc.getSoGioOTNgayThuong()
                        + t * 2 * bcc.getSoGioOTCN()
                        + t * 3 * bcc.getSoGioOTNgayLe();
            }

            // Phụ cấp
            double tongPhuCap = dto.getPhuCapAnTrua() + dto.getPhuCapDiLai();

            // Tổng các khoản trừ
            double tongTru = dto.getBhxh()
                    + dto.getBhyt()
                    + dto.getBhtn()
                    + dto.getThue()
                    + dto.getTamUng();

            // Tổng thực nhận
            double thucNhan = luongThucTe + luongOT + tongPhuCap + dto.getThuong() - tongTru;

            String formatted = formatter.format(thucNhan);
            log("thucNhan=" + formatted);
            return new SimpleStringProperty(formatted);
        });

        tb_bangluong.getColumns().clear();
        tb_bangluong.setItems(bangLuongDTOs);
        tb_bangluong.getColumns().addAll(tv_thoigian, tv_heso, tv_luongCB, tv_luongthucte, tv_luongOT, tv_phucap, tv_luongthuong, tv_cackhoantru, tv_thucnhan);
    }

    // set bang cham cong, tham so la nv va thang nam
    public void setChamCong(String maNV, int thang, int nam) {
        bcc = bccBUS.getBangChamCongByThangNam(maNV, thang, nam);
        if (bcc != null) {
            tf_songaycong.setText(String.valueOf(bcc.getSoNgayLam()));
            tf_songaynghikhongphep.setText(String.valueOf(bcc.getSoNgayNghiKP()));
            tf_songaynghiphep.setText(String.valueOf(bcc.getSoNPCoLuong()));
            tf_songaynghiphepkhongluong.setText(String.valueOf(bcc.getSoNPKhongLuong()));
            tf_sogiotangca.setText(String.valueOf(bcc.getSoGioOTCN()));
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
        Integer selectedMonth = cbMonth.getSelectionModel().getSelectedItem();
        Integer selectedYear = cbYear.getSelectionModel().getSelectedItem();

        if (selectedMonth == null || selectedYear == null) {
            System.out.println("Chưa chọn đủ tháng hoặc năm.");
            return;
        }

        setChamCong(DangNhapBUS.maNV, selectedMonth, selectedYear);
        System.out.println("Tháng: " + selectedMonth + " Năm: " + selectedYear);
    }

    @FXML
    void handleChangeYear(ActionEvent event) {
        Integer selectedMonth = cbMonth.getSelectionModel().getSelectedItem();
        Integer selectedYear = cbYear.getSelectionModel().getSelectedItem();

        if (selectedMonth == null || selectedYear == null) {
            System.out.println("Chưa chọn tháng hoặc năm.");
            return;
        }

        setChamCong(DangNhapBUS.maNV, selectedMonth, selectedYear);
    }

    @FXML
    void handleClickUpdate(MouseEvent event) {
        String maNV = DangNhapBUS.maNV;
        String hoTen = tfName.getText();
        String soDienThoai = tfNumberPhone.getText();
        String diaChi = tfAddress.getText();
        String ngaySinhStr = tfBirthday.getText();
        Date ngaySinhDate = Date.valueOf(ngaySinhStr);
        String gioiTinh = rbMale.isSelected() ? "Nam" : "Nữ";
        byte[] hinhAnh = selectedImageBytes != null ? selectedImageBytes : nv.getHinhAnh();
        // new NhanVienDTO(maNV, hoTen, soDienThoai, diaChi, ngaySinh, gioiTinh,
        // hinhAnh);
        nv = new NhanVienDTO(maNV, hoTen, soDienThoai, diaChi, ngaySinhDate, gioiTinh, hinhAnh);
        nvBUS.updatePersonalInfo(nv);
        // load lai du lieu
        initialize();
        bt_update.setDisable(true);

    }

    @FXML
    private Pane pn_image;

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
    private TableColumn<BangLuongDTO, String> tv_thoigian;
    
    @FXML
    private TableColumn<BangLuongDTO, Float> tv_heso;

    @FXML
    private TableColumn<BangLuongDTO, String> tv_luongCB;

    @FXML
    private TableColumn<BangLuongDTO, String> tv_luongthucte;
    
    
    @FXML
    private TableColumn<BangLuongDTO, String> tv_luongOT;


    @FXML
    private TableColumn<BangLuongDTO, String> tv_phucap;
    
    @FXML
    private TableColumn<BangLuongDTO, String> tv_luongthuong;
    
    
    @FXML
    private TableColumn<BangLuongDTO, String> tv_cackhoantru;

    
    @FXML
    private TableColumn<BangLuongDTO, String> tv_thucnhan;
    


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
    
  //hàm hiển thị thông tin dòng code
  	public static void log(String message) {
  	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
  	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
  	    System.out.println(element.getClassName() + " | method: " 
  	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
  	}

}