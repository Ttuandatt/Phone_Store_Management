/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ACER
 */
public class NhaCungCapDTO {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String sdt;
    private String email;
    private String trangthai;
    
    
    //Constructor
    public NhaCungCapDTO(){
        
    }
    public NhaCungCapDTO(String maNCC, String tenNCC, String diaChi, String sdt){
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }
    
    public NhaCungCapDTO(String maNCC){
        this.maNCC = maNCC;
    }
    
    
    public NhaCungCapDTO(String maNCC, String tenNCC, String diaChi,String email, String sdt, String trangthai){
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi= diaChi;
        this.sdt = sdt;
        this.email = email;
        this.trangthai = trangthai;
    }
    
    public String getMaNCC(){
        return this.maNCC;
    }
    public void setMaNCC(String maNCC){
        this.maNCC = maNCC;
    }
        public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getTenNCC(){
        return this.tenNCC;
    }
    public void setTenNCC(String tenNCC){
        this.tenNCC = tenNCC;
    }
    
    public String getDiaChi(){
        return this.diaChi;
    }
    public void setDiaChi(String diaChi){
        this.diaChi = diaChi;
    }
    
    public String getSdt(){
        return this.sdt;
    }
    public void setSdt(String sdt){
        this.sdt = sdt; 
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangThai) {
        this.trangthai = trangThai;
    }
    
}
