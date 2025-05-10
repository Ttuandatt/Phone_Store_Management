package DTO;

public class ChucVuDTO {
	private String maCV;
	private String tenCV;
	private float luongCoBan;
	private String trangThai;
	//Constructor
	public ChucVuDTO() {
	}
	
	
	public ChucVuDTO(String maCV, String tenCV, float heSoLuong, String trangThai) {
		super();
		this.maCV = maCV;
		this.tenCV = tenCV;
		this.luongCoBan = luongCoBan;
                this.trangThai = trangThai;
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
	public float getLuongCoBan() {
		return luongCoBan;
	}
	public void setLuongCoBan(float luongCoBan) {
		this.luongCoBan = luongCoBan;
	}

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
	

}
