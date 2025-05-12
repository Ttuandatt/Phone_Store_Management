package DTO;

public class KhoDTO {
	private String maKho;
	private String tenKho;
	private String diaChi;
	private String sdt;
	private String trangThai;

	
	//construcotr
	public KhoDTO() {
	}


	public KhoDTO(String maKho, String tenKho, String diaChi, String sdt, String trangThai) {
		super();
		this.maKho = maKho;
		this.tenKho = tenKho;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.trangThai = trangThai;
	}


	public String getMaKho() {
		return maKho;
	}


	public void setMaKho(String maKho) {
		this.maKho = maKho;
	}


	public String getTenKho() {
		return tenKho;
	}


	public void setTenKho(String tenKho) {
		this.tenKho = tenKho;
	}


	public String getDiaChi() {
		return diaChi;
	}


	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}


	public String getSdt() {
		return sdt;
	}


	public void setSdt(String sdt) {
		this.sdt = sdt;
	}


	public String getTrangThai() {
		return trangThai;
	}


	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	
	//getters, setters
	
}
