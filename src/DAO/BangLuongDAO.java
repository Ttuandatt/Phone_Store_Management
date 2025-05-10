package DAO;
/*Mới update*/
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.BangChamCongDTO;
import DTO.BangLuongDTO;
import Database.JDBCConnection;

public class BangLuongDAO implements DAOInterface<BangLuongDTO>{
    JDBCConnection jdbc = new JDBCConnection();

    @Override
    public ArrayList<BangLuongDTO> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }
    
    public ArrayList<BangLuongDTO> selectByNV(String maNV) {
        ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<BangLuongDTO>();
        try {
            jdbc.openConnection();

            String query = "select * from bangluong where maNV=?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maNV);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BangLuongDTO bl = new BangLuongDTO();
                bl.setMaBL(rs.getString("maBL"));
                bl.setThangLuong(rs.getInt("thangLuong"));
                bl.setNamLuong(rs.getInt("namLuong"));
                bl.setLuongCB(rs.getDouble("luongCB"));
                bl.setHeSo(rs.getFloat("heSo"));
                bl.setPhuCapAnTrua(rs.getDouble("phuCapAnTrua"));
                bl.setPhuCapDiLai(rs.getDouble("phuCapDiLai"));
                bl.setThuong(rs.getDouble("thuong"));
                bl.setBhxh(rs.getDouble("bhxh"));
                bl.setBhyT(rs.getDouble("bhyt"));
                bl.setBhtn(rs.getDouble("bhtn"));
                bl.setThueTNCN(rs.getDouble("thueTNCN"));
                bl.setTamUng(rs.getDouble("tamUng"));
                bl.setThucNhan(rs.getDouble("thucNhan"));
                bl.setMaNV(rs.getString("maNV"));

                arrBangLuong.add(bl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }
        return arrBangLuong;
    }

    @Override
    public int insert(BangLuongDTO t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public int delete(BangLuongDTO t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public int update(BangLuongDTO t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public BangLuongDTO selectById(String t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }
    
}
