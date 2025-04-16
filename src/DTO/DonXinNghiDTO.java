package DTO;

import java.sql.Date;
import java.util.ArrayList;

public class DonXinNghiDTO {
	private String maDon;
	private Date ngayTao;
	private Date ngayBD;
	private Date ngayKT;
	private String lyDo;
	private Date ngayDuyet;
	private String trangThai;
	private String maNV;
	private String maNguoiDuyet;
	
	
	
	public DonXinNghiDTO() {
		super();
	}

	public DonXinNghiDTO(String maDon, Date ngayTao, Date ngayBD, Date ngayKT, String lyDo, Date ngayDuyet,
			String trangThai, String maNV, String maNguoiDuyet) {
		this.maDon = maDon;
		this.ngayTao = ngayTao;
		this.ngayBD = ngayBD;
		this.ngayKT = ngayKT;
		this.lyDo = lyDo;
		this.ngayDuyet = ngayDuyet;
		this.trangThai = trangThai;
		this.maNV = maNV;
		this.maNguoiDuyet = maNguoiDuyet;
	}

	public String getMaDon() {
		return maDon;
	}

	public void setMaDon(String maDon) {
		this.maDon = maDon;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	public Date getNgayBD() {
		return ngayBD;
	}

	public void setNgayBD(Date ngayBD) {
		this.ngayBD = ngayBD;
	}

	public Date getNgayKT() {
		return ngayKT;
	}

	public void setNgayKT(Date ngayKT) {
		this.ngayKT = ngayKT;
	}

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}

	public Date getNgayDuyet() {
		return ngayDuyet;
	}

	public void setNgayDuyet(Date ngayDuyet) {
		this.ngayDuyet = ngayDuyet;
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

	public String getMaNguoiDuyet() {
		return maNguoiDuyet;
	}

	public void setMaNguoiDuyet(String maNguoiDuyet) {
		this.maNguoiDuyet = maNguoiDuyet;
	}
	
	
	
}
