package DTO;

import java.sql.Date;

public class NhanVienDTO {
	private String maNV;         // Mã nhân viên
    private String hoTen;        // Họ và tên
    private Date ngaySinh;       // Ngày sinh
    private String gioiTinh;     // Giới tính
    private String diaChi;       // Địa chỉ
    private String soDienThoai;  // Số điện thoại
    private String email;        // Email
    private String matKhau;
    private byte[] hinhAnh;      // Ảnh nhân viên (lưu dưới dạng BLOB)
    private String trangThai;    // Trạng thái (đang làm, đã nghỉ, đang nghỉ phép)
    private String chucVu;		 // Khóa ngoại mã chức vụ
    private String noiLamViec;	 // Nơi làm việc
    
    //Constructors
    public NhanVienDTO() {
    	
    }
    
	public NhanVienDTO(String maNV, String hoTen, Date ngaySinh, String gioiTinh, String diaChi, String soDienThoai, String email, String matKhau, byte[] hinhAnh, String trangThai, String chucVu, String noiLamViec) {
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.soDienThoai = soDienThoai;
		this.email = email;
		this.hinhAnh = hinhAnh;
		this.trangThai = trangThai;
		this.matKhau = matKhau;
		this.chucVu = chucVu;
		this.noiLamViec = noiLamViec;
	}
	
	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	//getter & setter
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public byte[] getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(byte[] hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String maCV) {
		this.chucVu = maCV;
	}

	public String getNoiLamViec() {
		return noiLamViec;
	}

	public void setNoiLamViec(String noiLamViec) {
		this.noiLamViec = noiLamViec;
	}
	
	
	
}
