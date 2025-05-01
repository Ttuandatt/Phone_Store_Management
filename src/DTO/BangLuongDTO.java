package DTO;

public class BangLuongDTO {
    /*[maBL]
      ,[thangLuong]
      ,[namLuong]
      ,[luongCB]
      ,[heSo]
      ,[phuCapAnTrua]
      ,[phuCapDiLai]
      ,[thuong]
      ,[bhxh]
      ,[bhyt]
      ,[bhtn]
      ,[thueTNCN]
      ,[tamUng]
      ,[thucNhan]
      ,[maNV] */
    private String maBL;
    private int thangLuong;
    private int namLuong;
    private double luongCB;
    private float heSo;
    private double phuCapAnTrua;
    private double phuCapDiLai;
    private double thuong;
    private double bhxh;
    private double bhyt;
    private double bhtn;
    private double thueTNCN;
    private double tamUng;
    private double thucNhan;
    private String maNV;
    // constructor
    public BangLuongDTO() {

    }
    public BangLuongDTO(String maBL, int thangLuong, int namLuong, double luongCB, float heSo, double phuCapAnTrua,
            double phuCapDiLai, double thuong, double bhxh, double bhyt, double bhtn, double thueTNCN, double tamUng,
            double thucNhan, String maNV) {
        super();
        this.maBL = maBL;
        this.thangLuong = thangLuong;
        this.namLuong = namLuong;
        this.luongCB = luongCB;
        this.heSo = heSo;
        this.phuCapAnTrua = phuCapAnTrua;
        this.phuCapDiLai = phuCapDiLai;
        this.thuong = thuong;
        this.bhxh = bhxh;
        this.bhyt = bhyt;
        this.bhtn = bhtn;
        this.thueTNCN = thueTNCN;
        this.tamUng = tamUng;
        this.thucNhan = thucNhan;
        this.maNV = maNV;
    }
    // getter, setter
    public String getMaBL() {
        return maBL;
    }
    public void setMaBL(String maBL) {
        this.maBL = maBL;
    }
    public int getThangLuong() {
        return thangLuong;
    }
    public void setThangLuong(int thangLuong) {
        this.thangLuong = thangLuong;
    }
    public int getNamLuong() {
        return namLuong;
    }
    public void setNamLuong(int namLuong) {
        this.namLuong = namLuong;
    }
    public double getLuongCB() {
        return luongCB;
    }
    public void setLuongCB(double luongCB) {
        this.luongCB = luongCB;
    }
    public float getHeSo() {
        return heSo;
    }
    public void setHeSo(float heSo) {
        this.heSo = heSo;
    }
    public double getPhuCapAnTrua() {
        return phuCapAnTrua;
    }
    public void setPhuCapAnTrua(double phuCapAnTrua) {
        this.phuCapAnTrua = phuCapAnTrua;
    }
    public double getPhuCapDiLai() {
        return phuCapDiLai;
    }
    public void setPhuCapDiLai(double phuCapDiLai) {
        this.phuCapDiLai = phuCapDiLai;
    }
    public double getThuong() {
        return thuong;
    }
    public void setThuong(double thuong) {
        this.thuong = thuong;
    }
    public double getBhxh() {
        return bhxh;
    }
    public void setBhxh(double bhxh) {
        this.bhxh = bhxh;
    }
    public double getBhyT() {
        return bhyt;
    }
    public void setBhyT(double bhyt) {
        this.bhyt = bhyt;
    }
    public double getBhtn() {
        return bhtn;
    }
    public void setBhtn(double bhtn) {
        this.bhtn = bhtn;
    }
    public double getThueTNCN() {
        return thueTNCN;
    }
    public void setThueTNCN(double thueTNCN) {
        this.thueTNCN = thueTNCN;
    }
    public double getTamUng() {
        return tamUng;
    }
    public void setTamUng(double tamUng) {
        this.tamUng = tamUng;
    }
    public double getThucNhan() {
        return thucNhan;
    }
    public void setThucNhan(double thucNhan) {
        this.thucNhan = thucNhan;
    }
    public String getMaNV() {
        return maNV;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
