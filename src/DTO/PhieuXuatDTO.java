package DTO;

import java.sql.Date;

public class PhieuXuatDTO {
	private String maPX;
	private Date ngayTao;
	private String diaChi;
	private double tongTien;
	private String httt;
	private String trangThai;
	private String maNV;
	private String maKho;
	private String maKH;

	public PhieuXuatDTO() {
	}

	public PhieuXuatDTO(String maPX, Date ngayTao, String diaChi, double tongTien, String httt, String trangThai,
			String maNV, String maKho, String maKH) {
		super();
		this.maPX = maPX;
		this.ngayTao = ngayTao;
		this.diaChi = diaChi;
		this.tongTien = tongTien;
		this.httt = httt;
		this.trangThai = trangThai;
		this.maNV = maNV;
		this.maKho = maKho;
		this.maKH = maKH;
	}

	public String getMaPX() {
		return maPX;
	}

	public void setMaPX(String maPX) {
		this.maPX = maPX;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public String getHttt() {
		return httt;
	}

	public void setHttt(String httt) {
		this.httt = httt;
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

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	
	

	
	
	
}
