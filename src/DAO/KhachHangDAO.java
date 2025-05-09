package DAO;

import DTO.KhachHangDTO;
import Database.JDBCConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class KhachHangDAO implements DAOInterface<KhachHangDTO> {
    JDBCConnection jdbc = new JDBCConnection();
    
    @Override
    public ArrayList<KhachHangDTO> selectAll() {
        ArrayList<KhachHangDTO> arr = new ArrayList<KhachHangDTO>();
        
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM khachhang";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHangDTO khachHangDTO = new KhachHangDTO();
                khachHangDTO.setMaKH(rs.getString(1));
                khachHangDTO.setHoTen(rs.getString(2));
                khachHangDTO.setNgaySinh(rs.getString(3));
                khachHangDTO.setGioiTinh(rs.getString(4));
                khachHangDTO.setDiaChi(rs.getString(5));
                khachHangDTO.setSdt(rs.getString(6));
                khachHangDTO.setEmail(rs.getString(7));
                khachHangDTO.setTrangThai(rs.getString(8));

                arr.add(khachHangDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        
        return arr;
    }
    
    public boolean has(String maKH) {
        boolean result = false;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM khachhang WHERE maKH = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            result = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        
        return result;
    }

    @Override
    public int insert(KhachHangDTO kh) {
        int result = 0;
        try {
            jdbc.openConnection();
            String query = "INSERT INTO khachhang (maKH, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, trangThai) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getNgaySinh());
            ps.setString(4, kh.getGioiTinh());
            ps.setString(5, kh.getDiaChi());
            ps.setString(6, kh.getSdt());
            ps.setString(7, kh.getEmail());
            ps.setString(8, kh.getTrangThai());

            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return result;
    }

    @Override
    public int delete(KhachHangDTO kh) {
        int result = 0;
        try {
            jdbc.openConnection();
            String query = "UPDATE khachhang SET trangThai = 'Inactive' WHERE maKH = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, kh.getMaKH());
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    @Override
    public int update(KhachHangDTO kh) {
        int result = 0;
        try {
            jdbc.openConnection();
            String query = "UPDATE khachhang SET hoTen = ?, ngaySinh = ?, gioiTinh = ?, diaChi = ?, sdt = ?, email = ?, trangThai = ? WHERE maKH = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getNgaySinh());
            ps.setString(3, kh.getGioiTinh());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getSdt());
            ps.setString(6, kh.getEmail());
            ps.setString(7, kh.getTrangThai());
            ps.setString(8, kh.getMaKH());

            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return result;
    }

    @Override
    public KhachHangDTO selectById(String maKH) {
        KhachHangDTO kh = null;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM khachhang WHERE maKH = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kh = new KhachHangDTO();
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setNgaySinh(rs.getString(3));
                kh.setGioiTinh(rs.getString(4));
                kh.setDiaChi(rs.getString(5));
                kh.setSdt(rs.getString(6));
                kh.setEmail(rs.getString(7));
                kh.setTrangThai(rs.getString(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        
        return kh;
    }

    public ArrayList<KhachHangDTO> search(String searchContent) {
        ArrayList<KhachHangDTO> arr = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM khachhang WHERE "
                         + "maKH LIKE ? OR "
                         + "hoTen LIKE ? OR "
                         + "diaChi LIKE ? OR "
                         + "sdt LIKE ? OR "
                         + "email LIKE ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + searchContent + "%");
            ps.setString(2, "%" + searchContent + "%");
            ps.setString(3, "%" + searchContent + "%");
            ps.setString(4, "%" + searchContent + "%");
            ps.setString(5, "%" + searchContent + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setDiaChi(rs.getString(3));
                kh.setSdt(rs.getString(4));
                kh.setEmail(rs.getString(5));
                arr.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }

        return arr;
    }

    public KhachHangDTO getByName(String hoTen) {
        KhachHangDTO kh = null;
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM khachhang WHERE hoTen LIKE ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, "%" + hoTen + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kh = new KhachHangDTO();
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setDiaChi(rs.getString(3));
                kh.setSdt(rs.getString(4));
                kh.setEmail(rs.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        
        return kh;
    }
}
