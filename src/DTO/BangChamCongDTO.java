package DTO;
/*Mới update*/
public class BangChamCongDTO {
	private String maBCC;
	private int thangCC;
	private int namCC;
	private int soNgayLam;
	private int soNgayNghiKhongPhep;
	private int soNgayNghiPhepCoLuong;
	private int soNgayNghiPhepKhongLuong;
	private String maNV;
	private float soGioOT;
	

	//constructor
	public BangChamCongDTO() {
		super();
	}

	

	public BangChamCongDTO(String maBCC, int thangCC, int namCC, int soNgayLam, int soNgayNghiKhongPhep,
			int soNgayNghiPhepCoLuong, int soNgayNghiPhepKhongLuong, float soGioOT, String maNV) {
		super();
		this.maBCC = maBCC;
		this.thangCC = thangCC;
		this.namCC = namCC;
		this.soNgayLam = soNgayLam;
		this.soNgayNghiKhongPhep = soNgayNghiKhongPhep;
		this.soNgayNghiPhepCoLuong = soNgayNghiPhepCoLuong;
		this.soNgayNghiPhepKhongLuong = soNgayNghiPhepKhongLuong;
		this.maNV = maNV;
		this.soGioOT = soGioOT;
	}



	//getter, setter
	public float getSoGioOT() {
		return soGioOT;
	}
	public void setSoGioOT(float soGioOT) {
		this.soGioOT = soGioOT;
	}
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


	public int getSoNgayNghiKhongPhep() {
		return soNgayNghiKhongPhep;
	}



	public void setSoNgayNghiKhongPhep(int soNgayNghiKhongPhep) {
		this.soNgayNghiKhongPhep = soNgayNghiKhongPhep;
	}



	public int getSoNgayNghiPhepCoLuong() {
		return soNgayNghiPhepCoLuong;
	}



	public void setSoNgayNghiPhepCoLuong(int soNgayNghiPhepCoLuong) {
		this.soNgayNghiPhepCoLuong = soNgayNghiPhepCoLuong;
	}



	public int getSoNgayNghiPhepKhongLuong() {
		return soNgayNghiPhepKhongLuong;
	}



	public void setSoNgayNghiPhepKhongLuong(int soNgayNghiPhepKhongLuong) {
		this.soNgayNghiPhepKhongLuong = soNgayNghiPhepKhongLuong;
	}



	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	
	
		
}
