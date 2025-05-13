/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.BangLuongDTO;
import Database.JDBCConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class BangLuongDAO implements DAOInterface<BangLuongDTO>{
	JDBCConnection jdbc = new JDBCConnection();

    @Override
    public BangLuongDTO selectById(String maBL) {
        BangLuongDTO bl = new BangLuongDTO();
	try {
            jdbc.openConnection();
			
            String query = "select * from BangLuong where maBL = ?";
			
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maBL);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
		bl.setMaLuong(rs.getString("maBL"));
		bl.setThangLuong(rs.getInt("thangLuong"));
		bl.setNamLuong(rs.getInt("namLuong"));
		bl.setLuongCB(rs.getFloat("luongCB"));
		bl.setHeSo(rs.getFloat("heSo"));
		bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
		bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
                bl.setThuong(rs.getFloat("thuong"));
                bl.setBhxh(rs.getFloat("bhxh"));
                bl.setBhyt(rs.getFloat("bhyt"));
                bl.setBhtn(rs.getFloat("bhtn"));
                bl.setThue(rs.getFloat("thueTNCN"));
                bl.setTamUng(rs.getFloat("tamUng"));
                bl.setThucNhan(rs.getFloat("thucNhan"));
		bl.setMaNV(rs.getString("maNV"));
		bl.setTrangThai(rs.getString("trangThai"));		
            }
	}catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
	}finally {
            jdbc.closeConnection();
	}
	return bl;
    }

    public ArrayList<BangLuongDTO> selectByTime(String thang, String nam) {
        ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            jdbc.openConnection();

            StringBuilder query = new StringBuilder("SELECT * FROM BangLuong WHERE 1=1");
            ArrayList<Object> params = new ArrayList<>();

            if (!thang.equals("Tháng")) {
                query.append(" AND thangLuong = ?");
                params.add(Integer.parseInt(thang));
            }

            if (!nam.equals("Năm")) {
                query.append(" AND namLuong = ?");
                params.add(Integer.parseInt(nam));
            }

            // Không có điều kiện tìm kiếm → trả về rỗng hoặc toàn bộ, tuỳ yêu cầu
            if (params.isEmpty()) {
                return arrBangLuong; // hoặc query toàn bộ nếu muốn
            }

            ps = jdbc.getConnection().prepareStatement(query.toString());

            // Gán tham số cho PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                BangLuongDTO bl = new BangLuongDTO();
                bl.setMaLuong(rs.getString("maBL"));
                bl.setThangLuong(rs.getInt("thangLuong"));
                bl.setNamLuong(rs.getInt("namLuong"));
                bl.setLuongCB(rs.getFloat("luongCB"));
                bl.setHeSo(rs.getFloat("heSo"));
                bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
                bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
                bl.setThuong(rs.getFloat("thuong"));
                bl.setBhxh(rs.getFloat("bhxh"));
                bl.setBhyt(rs.getFloat("bhyt"));
                bl.setBhtn(rs.getFloat("bhtn"));
                bl.setThue(rs.getFloat("thueTNCN"));
                bl.setTamUng(rs.getFloat("tamUng"));
                bl.setThucNhan(rs.getFloat("thucNhan")); 
                bl.setMaNV(rs.getString("maNV"));
                bl.setTrangThai(rs.getString("trangThai"));

                arrBangLuong.add(bl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            jdbc.closeConnection();
        }

        return arrBangLuong;
    }


    @Override
    public ArrayList<BangLuongDTO> selectAll() {
        ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<BangLuongDTO>();
	try {
            jdbc.openConnection();
			
            String query = "select * from BangLuong";
			
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
		BangLuongDTO bl = new BangLuongDTO();
		bl.setMaLuong(rs.getString("maBL"));
		bl.setThangLuong(rs.getInt("thangLuong"));
		bl.setNamLuong(rs.getInt("namLuong"));
		bl.setLuongCB(rs.getFloat("luongCB"));
		bl.setHeSo(rs.getFloat("heSo"));
		bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
		bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
                bl.setThuong(rs.getFloat("thuong"));
                bl.setBhxh(rs.getFloat("bhxh"));
                bl.setBhyt(rs.getFloat("bhyt"));
                bl.setBhtn(rs.getFloat("bhtn"));
                bl.setThue(rs.getFloat("thueTNCN"));
                bl.setTamUng(rs.getFloat("tamUng"));
                bl.setThucNhan(rs.getFloat("thucNhan"));
		bl.setMaNV(rs.getString("maNV"));
		bl.setTrangThai(rs.getString("trangThai"));
                
		arrBangLuong.add(bl);
            }
	}catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
	}finally {
            jdbc.closeConnection();
	}
	return arrBangLuong;
    }


    @Override
    public int delete(BangLuongDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(BangLuongDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(BangLuongDTO t) {
        int result = 0;
        try {
            jdbc.openConnection();

            String query = "update BangLuong set luongCB=?, heSo=?, phuCapAnTrua=?, phuCapDiLai=?, thuong=?, bhxh=?, bhyt=?, bhtn=?, thueTNCN=?, tamUng=?, thucNhan=?, trangThai=? where maBL=?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setFloat(1, t.getLuongCB());
            ps.setFloat(2, t.getHeSo());
            ps.setFloat(3, t.getPhuCapAnTrua());
            ps.setFloat(4, t.getPhuCapDiLai());
            ps.setFloat(5, t.getThuong());
            ps.setFloat(6, t.getBhxh());
            ps.setFloat(7, t.getBhyt());
            ps.setFloat(8, t.getBhtn());
            ps.setFloat(9, t.getThue());
            ps.setFloat(10, t.getTamUng());
            ps.setFloat(11, t.getThucNhan());
            ps.setString(12, t.getTrangThai());
            ps.setString(13, t.getMaLuong());
            
            result = ps.executeUpdate(); // Sử dụng executeUpdate() để lấy số dòng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi để dễ debug
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }

    public ArrayList<BangLuongDTO> selectByKeyWord(String keyWord) {
        ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<>();
        try {
            jdbc.openConnection();

            String query = "SELECT * FROM BangLuong bl " +
                           "JOIN NhanVien nv ON bl.maNV = nv.maNV " +
                           "WHERE maBL LIKE ? OR bl.maNV LIKE ? OR hoTen LIKE ?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            String keywordPattern = "%" + keyWord + "%";
            ps.setString(1, keywordPattern);
            ps.setString(2, keywordPattern);
            ps.setString(3, keywordPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BangLuongDTO bl = new BangLuongDTO();
                bl.setMaLuong(rs.getString("maBL"));
                bl.setThangLuong(rs.getInt("thangLuong"));
                bl.setNamLuong(rs.getInt("namLuong"));
                bl.setLuongCB(rs.getFloat("luongCB"));
                bl.setHeSo(rs.getFloat("heSo"));
                bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
                bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
                bl.setThuong(rs.getFloat("thuong"));
                bl.setBhxh(rs.getFloat("bhxh"));
                bl.setBhyt(rs.getFloat("bhyt"));
                bl.setBhtn(rs.getFloat("bhtn"));
                bl.setThue(rs.getFloat("thueTNCN"));
                bl.setTamUng(rs.getFloat("tamUng"));
                bl.setThucNhan(rs.getFloat("thucNhan")); // sửa đúng thuộc tính
                bl.setMaNV(rs.getString("maNV"));
                bl.setTrangThai(rs.getString("trangThai"));

                arrBangLuong.add(bl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.closeConnection();
        }
        return arrBangLuong;
    }


    
}
