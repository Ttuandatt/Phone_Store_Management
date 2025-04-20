package DTO;

public class PhienBanSanPhamDTO {
	private String maPBSP;
	private String mauSac;
	private String ram;
	private String rom;
	private Double giaBan;
	private int soLuong;
	private String trangThai;
	private String maSP;
	
	//constructor
	public PhienBanSanPhamDTO() {
	}

	public PhienBanSanPhamDTO(String maPBSP, String mauSac, String ram, String rom, Double giaBan, int soLuong,
			 String maSP) {
		super();
		this.maPBSP = maPBSP;
		this.mauSac = mauSac;
		this.ram = ram;
		this.rom = rom;
		this.giaBan = giaBan;
		this.soLuong = soLuong;
		this.maSP = maSP;
	}
	
	public PhienBanSanPhamDTO(String mauSac, String ram, String rom) {
		this.mauSac = mauSac;
		this.ram = ram;
		this.rom = rom;
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

	public Double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(Double giaBan) {
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
