package DTO;

import java.time.LocalDateTime;


public class LichSuChinhSuaDTO {
	private String maNguoiChinhSua;
	private String maNguoiBiChinhSua;
	private LocalDateTime thoiGian;
	private String giaTriCu;
        private String giaTriMoi;

    public LichSuChinhSuaDTO(String maNguoiChinhSua, String maNguoiBiChinhSua, LocalDateTime thoiGian, String giaTriCu, String giaTriMoi) {
        this.maNguoiChinhSua = maNguoiChinhSua;
        this.maNguoiBiChinhSua = maNguoiBiChinhSua;
        this.thoiGian = thoiGian;
        this.giaTriCu = giaTriCu;
        this.giaTriMoi = giaTriMoi;
    }

    public LichSuChinhSuaDTO() {
    }
    
    public String getMaNguoiChinhSua() {
        return maNguoiChinhSua;
    }

    public void setMaNguoiChinhSua(String maNguoiChinhSua) {
        this.maNguoiChinhSua = maNguoiChinhSua;
    }

    public String getMaNguoiBiChinhSua() {
        return maNguoiBiChinhSua;
    }

    public void setMaNguoiBiChinhSua(String maNguoiBiChinhSua) {
        this.maNguoiBiChinhSua = maNguoiBiChinhSua;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getGiaTriMoi() {
        return giaTriMoi;
    }

    public void setGiaTriMoi(String giaTriMoi) {
        this.giaTriMoi = giaTriMoi;
    }

    public String getGiaTriCu() {
        return giaTriCu;
    }

    public void setGiaTriCu(String giaTriCu) {
        this.giaTriCu = giaTriCu;
    }

    
}