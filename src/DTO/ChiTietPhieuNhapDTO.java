package DTO;

public class ChiTietPhieuNhapDTO {
	private int soLuong;
	private double giaNhap;
	private String maPN;
	private String maPBSP;
	
	
	//constructor
	public ChiTietPhieuNhapDTO(int soLuong, double giaNhap, String maPN, String maPBSP) {
		this.soLuong = soLuong;
		this.giaNhap = giaNhap;
		this.maPN = maPN;
		this.maPBSP = maPBSP;
	}

	public ChiTietPhieuNhapDTO() {
	}

	//getter, setter
	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getGiaNhap() {
		return giaNhap;
	}

	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}

	public String getMaPN() {
		return maPN;
	}

	public void setMaPN(String maPN) {
		this.maPN = maPN;
	}

	public String getMaPBSP() {
		return maPBSP;
	}

	public void setMaPBSP(String maPBSP) {
		this.maPBSP = maPBSP;
	}
	
	
	
}
