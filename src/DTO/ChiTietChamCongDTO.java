package DTO;

import java.sql.Date;
import java.time.LocalDate;

public class ChiTietChamCongDTO {
	private String maCTCC;
	private LocalDate ngayTao;
	private String loaiChamCong;
	private String chiTiet;
	private String maBCC;
	private float soGioOT;
	
	public ChiTietChamCongDTO() {
		super();
	}

	public ChiTietChamCongDTO(String maCTCC, LocalDate ngayTao, String loaiChamCong, String chiTiet, String maBCC,
			float soGioOT) {
		this.maCTCC = maCTCC;
		this.ngayTao = ngayTao;
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

	public LocalDate getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(LocalDate ngayTao) {
		this.ngayTao = ngayTao;
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

	public float getSoGioOT() {
		return soGioOT;
	}

	public void setSoGioOT(float d) {
		this.soGioOT = d;
	}
	
	
	
}
