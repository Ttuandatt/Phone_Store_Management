package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.T;

import DTO.ProductsDTO;
import Database.JDBCConnection;

public class ProductsDAO implements DAOInterface<ProductsDTO> {
    JDBCConnection jdbc = new JDBCConnection();

    @Override
    public ArrayList<DTO.ProductsDTO> selectAll() {
        ArrayList<DTO.ProductsDTO> products= new ArrayList<DTO.ProductsDTO>();
        try {

            jdbc.openConnection();

            String query = "SELECT * FROM SANPHAM";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DTO.ProductsDTO pd = new DTO.ProductsDTO();
                pd.setMaSP(rs.getString("maSP"));
                pd.setTenSP(rs.getString("tenSP"));
                pd.setPin(rs.getString("pin"));
                pd.setOS(rs.getString("OS"));
                pd.setCamTruoc(rs.getString("camTruoc"));
                pd.setCamSau(rs.getString("camSau"));
                pd.setXuatXu(rs.getString("xuatXu"));
                pd.setTrangThai(rs.getString("trangThai"));
                pd.setMaTH(rs.getString("maTH"));

                products.add(pd);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }

        return products;
    }
    public ArrayList<DTO.PBSPDTO> selectPBSPBymaSP(String maSP){
        ArrayList<DTO.PBSPDTO> PBSPs=new ArrayList<DTO.PBSPDTO>();
        try {

            jdbc.openConnection();

            String query = "SELECT * FROM PBSP WHERE maSP=?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maSP);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DTO.PBSPDTO pd = new DTO.PBSPDTO();
                pd.setMaPBSP(rs.getString("maPBSP"));
                pd.setMauSac(rs.getString("mauSac"));
                pd.setRam(rs.getString("ram"));
                pd.setRom(rs.getString("rom"));
                pd.setGiaBan(rs.getInt("giaBan"));
                pd.setSoLuong(rs.getInt("soLuong"));
                pd.setTrangThai(rs.getString("trangThai"));
                pd.setMaSP(rs.getString("maSP"));
                PBSPs.add(pd);
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }
        return PBSPs;
    }
    @Override
    public ProductsDTO selectById(String t){
        return new ProductsDTO();
    }
    @Override
    public int insert(ProductsDTO t) {
        return 0;
    }

    @Override
    public int delete(ProductsDTO t) {
        return 0;

    }

    @Override
    public int update(ProductsDTO t) {
        return 0;

    }
}
