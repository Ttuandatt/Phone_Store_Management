package DTO;

public class BangChamCongDTO {
    private String maBCC;
    private int thangCC;
    private int namCC;
    private float soNgayLam;
    private float soNgayNghiKP;
    private float soNPCoLuong;
    private float soNPKhongLuong;
    private float soGioOTNgayThuong;
    private float soGioOTNgayLe;
    private float soGioOTCN;
    private String maNV;

    public BangChamCongDTO(String maBCC, int thangCC, int namCC, float soNgayLam, float soNgayNghiKP, float soNPCoLuong, float soNPKhongLuong, float soGioOTNgayThuong, float soGioOTNgayLe, float soGioOTCN, String maNV) {
        this.maBCC = maBCC;
        this.thangCC = thangCC;
        this.namCC = namCC;
        this.soNgayLam = soNgayLam;
        this.soNgayNghiKP = soNgayNghiKP;
        this.soNPCoLuong = soNPCoLuong;
        this.soNPKhongLuong = soNPKhongLuong;
        this.soGioOTNgayThuong = soGioOTNgayThuong;
        this.soGioOTNgayLe = soGioOTNgayLe;
        this.soGioOTCN = soGioOTCN;
        this.maNV = maNV;
    }

    public BangChamCongDTO() {
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

    public float getSoNgayLam() {
        return soNgayLam;
    }

    public void setSoNgayLam(float soNgayLam) {
        this.soNgayLam = soNgayLam;
    }

    public float getSoNgayNghiKP() {
        return soNgayNghiKP;
    }

    public void setSoNgayNghiKP(float soNgayNghiKP) {
        this.soNgayNghiKP = soNgayNghiKP;
    }

    public float getSoNPCoLuong() {
        return soNPCoLuong;
    }

    public void setSoNPCoLuong(float soNPCoLuong) {
        this.soNPCoLuong = soNPCoLuong;
    }

    public float getSoNPKhongLuong() {
        return soNPKhongLuong;
    }

    public void setSoNPKhongLuong(float soNPKhongLuong) {
        this.soNPKhongLuong = soNPKhongLuong;
    }

    public float getSoGioOTNgayThuong() {
        return soGioOTNgayThuong;
    }

    public void setSoGioOTNgayThuong(float soGioOTNgayThuong) {
        this.soGioOTNgayThuong = soGioOTNgayThuong;
    }

    public float getSoGioOTNgayLe() {
        return soGioOTNgayLe;
    }

    public void setSoGioOTNgayLe(float soGioOTNgayLe) {
        this.soGioOTNgayLe = soGioOTNgayLe;
    }

    public float getSoGioOTCN() {
        return soGioOTCN;
    }

    public void setSoGioOTCN(float soGioOTCN) {
        this.soGioOTCN = soGioOTCN;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    
    
}    
