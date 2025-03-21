package DTO;

public class ChucVuDTO {
	private String maCV;
	private String tenCV;
	private float heSoLuong;
	private float luongCoBan;
	
	//Constructor
	public ChucVuDTO() {
	}
	
	
	public ChucVuDTO(String maCV, String tenCV, float heSoLuong, float luongCoBan) {
		super();
		this.maCV = maCV;
		this.tenCV = tenCV;
		this.heSoLuong = heSoLuong;
		this.luongCoBan = luongCoBan;
	}

	//getter, setter
	public String getMaCV() {
		return maCV;
	}
	public void setMaCV(String maCV) {
		this.maCV = maCV;
	}
	public String getTenCV() {
		return tenCV;
	}
	public void setTenCV(String tenCV) {
		this.tenCV = tenCV;
	}
	public float getHeSoLuong() {
		return heSoLuong;
	}
	public void setHeSoLuong(float heSoLuong) {
		this.heSoLuong = heSoLuong;
	}
	public float getLuongCoBan() {
		return luongCoBan;
	}
	public void setLuongCoBan(float luongCoBan) {
		this.luongCoBan = luongCoBan;
	}
	
	
}
