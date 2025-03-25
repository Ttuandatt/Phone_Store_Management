package DTO;

public class PhienBanSanPhamDTO {
	private String maPBSP;
	private String mauSac;
	private String ram;
	private String rom;
	private double giaBan;
	private int soLuong;
	private String trangThai;
	private String maSP;
	
	//constructor
	public PhienBanSanPhamDTO() {
	}

	public PhienBanSanPhamDTO(String maPBSP, String mauSac, String ram, String rom, double giaBan, int soLuong,
			String trangThai, String maSP) {
		super();
		this.maPBSP = maPBSP;
		this.mauSac = mauSac;
		this.ram = ram;
		this.rom = rom;
		this.giaBan = giaBan;
		this.soLuong = soLuong;
		this.trangThai = trangThai;
		this.maSP = maSP;
	}

	
	//getter, setter
	public String getMaPBSP() {
		return maPBSP;
	}

	public void setMaPBSP(String maPBSP) {
		this.maPBSP = maPBSP;
	}

	public String getMauSac() {
		return mauSac;
	}

	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}
	
	
	
}
