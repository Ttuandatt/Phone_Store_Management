package DTO;

import java.sql.Date;

public class ChiTietChamCongDTO {
	private String maCTCC;
	private Date ngayChamCong;
	private String loaiChamCong;
	private String chiTiet;
	String maBCC;
	Double soGioOT;
	
	
	
	
	public ChiTietChamCongDTO() {
		super();
	}

	public ChiTietChamCongDTO(String maCTCC, Date ngayChamCong, String loaiChamCong, String chiTiet, String maBCC,
			Double soGioOT) {
		this.maCTCC = maCTCC;
		this.ngayChamCong = ngayChamCong;
		this.loaiChamCong = loaiChamCong;
		this.chiTiet = chiTiet;
		this.maBCC = maBCC;
		this.soGioOT = soGioOT;
	}

	public String getMaCTCC() {
		return maCTCC;
	}

	public void setMaCTCC(String maCTCC) {
		this.maCTCC = maCTCC;
	}

	public Date getngayChamCong() {
		return ngayChamCong;
	}

	public void setNgayChamCong(Date ngayChamCong) {
		this.ngayChamCong = ngayChamCong;
	}

	public String getLoaiChamCong() {
		return loaiChamCong;
	}

	public void setLoaiChamCong(String loaiChamCong) {
		this.loaiChamCong = loaiChamCong;
	}

	public String getChiTiet() {
		return chiTiet;
	}

	public void setChiTiet(String chiTiet) {
		this.chiTiet = chiTiet;
	}

	public String getMaBCC() {
		return maBCC;
	}

	public void setMaBCC(String maBCC) {
		this.maBCC = maBCC;
	}

	public Double getSoGioOT() {
		return soGioOT;
	}

	public void setSoGioOT(double d) {
		this.soGioOT = d;
	}
	
	
	
}
