/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author PC
 */
public class BangLuongDTO {
    private String maLuong;
    private int thangLuong;
    private int namLuong;
    private float luongCB;
    private float heSo;
    private float phuCapDiLai;
    private float phuCapAnTrua;
    private float thuong;
    private float bhxh;
    private float bhyt;
    private float bhtn;
    private float thue;
    private float tamUng;
    private float thucNhan;
    private String maNV;
    private String trangThai;

    public BangLuongDTO() {
    }

    public BangLuongDTO(String maLuong, int thangLuong, int namLuong, float luongCB, float heSo, float phuCapDiLai, float phuCapAnTrua, float thuong, float bhxh, float bhyt, float bhtn, float thue, float tamUng, float thucNhan, String maNV, String trangThai) {
        this.maLuong = maLuong;
        this.thangLuong = thangLuong;
        this.namLuong = namLuong;
        this.luongCB = luongCB;
        this.heSo = heSo;
        this.phuCapDiLai = phuCapDiLai;
        this.phuCapAnTrua = phuCapAnTrua;
        this.thuong = thuong;
        this.bhxh = bhxh;
        this.bhyt = bhyt;
        this.bhtn = bhtn;
        this.thue = thue;
        this.tamUng = tamUng;
        this.thucNhan = thucNhan;
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    public String getMaLuong() {
        return maLuong;
    }

    public int getThangLuong() {
        return thangLuong;
    }

    public int getNamLuong() {
        return namLuong;
    }


    public float getThue() {
        return thue;
    }

    public float getPhuCapDiLai() {
        return phuCapDiLai;
    }

    public float getPhuCapAnTrua() {
        return phuCapAnTrua;
    }

    public float getThuong() {
        return thuong;
    }

    public float getTamUng() {
        return tamUng;
    }

    public float getThucNhan() {
        return thucNhan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaLuong(String maLuong) {
        this.maLuong = maLuong;
    }

    public void setThangLuong(int thangLuong) {
        this.thangLuong = thangLuong;
    }

    public void setNamLuong(int namLuong) {
        this.namLuong = namLuong;
    }

    public void setThue(float thue) {
        this.thue = thue;
    }

    public void setPhuCapDiLai(float phuCapDiLai) {
        this.phuCapDiLai = phuCapDiLai;
    }

    public void setPhuCapAnTrua(float phuCapAnTrua) {
        this.phuCapAnTrua = phuCapAnTrua;
    }

    public void setThuong(float thuong) {
        this.thuong = thuong;
    }

    public void setTamUng(float tamUng) {
        this.tamUng = tamUng;
    }

    public void setThucNhan(float thucNhan) {
        this.thucNhan = thucNhan;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public float getLuongCB() {
        return luongCB;
    }

    public void setLuongCB(float luongCB) {
        this.luongCB = luongCB;
    }

    public float getHeSo() {
        return heSo;
    }

    public void setHeSo(float heSo) {
        this.heSo = heSo;
    }

    public float getBhyt() {
        return bhyt;
    }

    public void setBhyt(float bhyt) {
        this.bhyt = bhyt;
    }

    public float getBhtn() {
        return bhtn;
    }

    public void setBhtn(float bhtn) {
        this.bhtn = bhtn;
    }

    public float getBhxh() {
        return bhxh;
    }

    public void setBhxh(float bhxh) {
        this.bhxh = bhxh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
}