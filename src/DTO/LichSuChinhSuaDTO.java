package DTO;

import java.sql.Date;

public class LichSuChinhSuaDTO {
	private String maLSCS;
	private String maNguoiChinhSua;
	private String maNguoiBiChinhSua;
	private Date thoiGian;
	private String noiDungChinhSua;
	
	
	//constructor
	public LichSuChinhSuaDTO() {
		super();
	}
	
	public LichSuChinhSuaDTO(String maLSCS, String maNguoiChinhSua, String maNguoiBiChinhSua, Date thoiGian,
			String noiDungChinhSua) {
		super();
		this.maLSCS = maLSCS;
		this.maNguoiChinhSua = maNguoiChinhSua;
		this.maNguoiBiChinhSua = maNguoiBiChinhSua;
		this.thoiGian = thoiGian;
		this.noiDungChinhSua = noiDungChinhSua;
	}

	

	//getters, setters
	public String getMaLSCS() {
		return maLSCS;
	}

	public void setMaLSCS(String maLSCS) {
		this.maLSCS = maLSCS;
	}

	public String getMaNguoiChinhSua() {
		return maNguoiChinhSua;
	}

	public void setMaNguoiChinhSua(String maNguoiChinhSua) {
		this.maNguoiChinhSua = maNguoiChinhSua;
	}

	public String getMaNguoiBiChinhSua() {
		return maNguoiBiChinhSua;
	}

	public void setMaNguoiBiChinhSua(String maNguoiBiChinhSua) {
		this.maNguoiBiChinhSua = maNguoiBiChinhSua;
	}

	public Date getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(Date thoiGian) {
		this.thoiGian = thoiGian;
	}

	public String getNoiDungChinhSua() {
		return noiDungChinhSua;
	}

	public void setNoiDungChinhSua(String noiDungChinhSua) {
		this.noiDungChinhSua = noiDungChinhSua;
	}

}
