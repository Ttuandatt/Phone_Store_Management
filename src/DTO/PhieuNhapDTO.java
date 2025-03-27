package DTO;

import java.sql.Date;

public class PhieuNhapDTO {
	private String maPN;
	private Date ngayTao;
	private double tongTien;
	private String trangThai;
	private String maNV;
	private String maKho;
	private String maNCC;
	
	//Constructor
	public PhieuNhapDTO() {
	}
	
	public PhieuNhapDTO(String maPN, Date ngayTao, double tongTien, String trangThai, String maNV, String maKho, String maNCC) {
		this.maPN = maPN;
		this.ngayTao = ngayTao;
		this.tongTien = tongTien;
		this.trangThai = trangThai;
		this.maNV = maNV;
		this.maKho = maKho;
		this.maNCC = maNCC;
	}
	
	//getter, setter
	public String getMaPN() {
		return maPN;
	}

	public void setMaPN(String maPN) {
		this.maPN = maPN;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getMaKho() {
		return maKho;
	}

	public void setMaKho(String maKho) {
		this.maKho = maKho;
	}

	public String getMaNCC() {
		return maNCC;
	}

	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}

	
	
	
	
	
	
	
	
}
