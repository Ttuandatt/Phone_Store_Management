package DTO;

public class BangChamCongDTO {
	private String maBCC;
	private int thangCC;
	private int namCC;
	private int soNgayLam;
	private int soNgayNghiPhep;
	private int soNgayNghiKhongPhep;
	private float soGioOT;
	private String maNV;
	
	

	//constructor
	public BangChamCongDTO() {
		super();
	}

	public BangChamCongDTO(String maBCC, int thangCC, int namCC, int soNgayLam, int soNgayNghiPhep,
			int soNgayNghiKhongPhep, float soGioOT, String maNV) {
		super();
		this.maBCC = maBCC;
		this.thangCC = thangCC;
		this.namCC = namCC;
		this.soNgayLam = soNgayLam;
		this.soNgayNghiPhep = soNgayNghiPhep;
		this.soNgayNghiKhongPhep = soNgayNghiKhongPhep;
		this.soGioOT = soGioOT;
		this.maNV = maNV;
	}

	//getter, setter
	public String getMaBCC() {
		return maBCC;
	}

	public void setMaBCC(String maBCC) {
		this.maBCC = maBCC;
	}

	public int getThangCC() {
		return thangCC;
	}

	public void setThangCC(int thangCC) {
		this.thangCC = thangCC;
	}

	public int getNamCC() {
		return namCC;
	}

	public void setNamCC(int namCC) {
		this.namCC = namCC;
	}

	public int getSoNgayLam() {
		return soNgayLam;
	}

	public void setSoNgayLam(int soNgayLam) {
		this.soNgayLam = soNgayLam;
	}

	public int getSoNgayNghiPhep() {
		return soNgayNghiPhep;
	}

	public void setSoNgayNghiPhep(int soNgayNghiPhep) {
		this.soNgayNghiPhep = soNgayNghiPhep;
	}

	public int getSoNgayNghiKhongPhep() {
		return soNgayNghiKhongPhep;
	}

	public void setSoNgayNghiKhongPhep(int soNgayNghiKhongPhep) {
		this.soNgayNghiKhongPhep = soNgayNghiKhongPhep;
	}

	public float getSoGioOT() {
		return soGioOT;
	}

	public void setSoGioOT(float soGioOT) {
		this.soGioOT = soGioOT;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	
	
		
}
