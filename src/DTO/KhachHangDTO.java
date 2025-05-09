package DTO;

public class KhachHangDTO {
    
    private String maKH;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String sdt;
    private String email;
    private String trangThai;

    // Constructor mặc định
    public KhachHangDTO() {
    }

    // Constructor có tham số
    public KhachHangDTO(String maKH, String hoTen, String ngaySinh, String gioiTinh, String diaChi, String sdt, String email, String trangThai) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.trangThai = trangThai;
    }

    // Getter và Setter cho maKH
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    // Getter và Setter cho hoTen
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    // Getter và Setter cho ngaySinh
    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    // Getter và Setter cho gioiTinh
    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    // Getter và Setter cho diaChi
    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // Getter và Setter cho sdt
    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    // Getter và Setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter và Setter cho trangThai
    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    // Override phương thức toString để in ra thông tin khách hàng dễ dàng hơn
    @Override
    public String toString() {
        return "KhachHangDTO{" +
               "maKH='" + maKH + '\'' +
               ", hoTen='" + hoTen + '\'' +
               ", ngaySinh='" + ngaySinh + '\'' +
               ", gioiTinh='" + gioiTinh + '\'' +
               ", diaChi='" + diaChi + '\'' +
               ", sdt='" + sdt + '\'' +
               ", email='" + email + '\'' +
               ", trangThai='" + trangThai + '\'' +
               '}';
    }
}
