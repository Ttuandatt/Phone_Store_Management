package DTO;

public class ThuongHieuDTO {
    private String maTH;
    private String tenTH;
    public ThuongHieuDTO() {
    }
    public ThuongHieuDTO(String maTH, String tenTH) {
        this.maTH = maTH;
        this.tenTH = tenTH;
    }
    public String getMaTH() {
        return maTH;
    }
    public void setMaTH(String maTH) {
        this.maTH = maTH;
    }
    public String getTenTH() {
        return tenTH;
    }
    public void setTenTH(String tenTH) {
        this.tenTH = tenTH;
    }
    @Override
    public String toString() {
        return tenTH;
    }
    
}