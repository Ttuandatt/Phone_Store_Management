package DTO;

public class ChiTietPhieuXuatDTO {
	private int soLuong;
	private double giaXuat;
	private String maPX;
	private String maPBSP;
	
	
	
	
	public ChiTietPhieuXuatDTO() {
		super();
	}

	public ChiTietPhieuXuatDTO(int soLuong, double giaXuat, String maPX, String maPBSP) {
		super();
		this.soLuong = soLuong;
		this.giaXuat = giaXuat;
		this.maPX = maPX;
		this.maPBSP = maPBSP;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getGiaXuat() {
		return giaXuat;
	}

	public void setGiaXuat(double giaXuat) {
		this.giaXuat = giaXuat;
	}

	public String getMaPX() {
		return maPX;
	}

	public void setMaPX(String maPX) {
		this.maPX = maPX;
	}

	public String getMaPBSP() {
		return maPBSP;
	}

	public void setMaPBSP(String maPBSP) {
		this.maPBSP = maPBSP;
	}
	
	
	
}
