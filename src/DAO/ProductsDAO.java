package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ProductsDTO;
import Database.JDBCConnection;

public class ProductsDAO implements DAOInterface<ProductsDTO> {
    JDBCConnection jdbc = new JDBCConnection();

    @Override
    public ArrayList<DTO.ProductsDTO> selectAll() {
        ArrayList<DTO.ProductsDTO> products= new ArrayList<DTO.ProductsDTO>();
        try {

            jdbc.openConnection();

            String query = "SELECT * FROM SANPHAM WHERE trangThai='on'";

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
                pd.setHinhAnh(rs.getBytes("hinhAnh"));

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

            String query = "SELECT * FROM PBSP WHERE maSP=? AND trangthai='on'";

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
                pd.setMaSP(rs.getString("maSP"));
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
    public int insert(ProductsDTO product) {
        System.out.println("vào này");
        int rowsInserted = 0;
        try {
            jdbc.openConnection();
            //check MaSP exist
            String query = "SELECT * FROM SANPHAM WHERE maSP=?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, product.getMaSP());

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                query="INSERT INTO SANPHAM (maSP, tenSP, pin, OS, camTruoc,camSau, xuatXu,hinhAnh, trangThai, maTH) " +
                        "VALUES (?,?,?,?,?,?,?,?,'on',?)";
                ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, product.getMaSP());
                ps.setString(2, product.getTenSP());
                ps.setString(3, product.getPin());
                ps.setString(4, product.getOS());
                ps.setString(5, product.getCamTruoc());
                ps.setString(6, product.getCamSau());
                ps.setString(7, product.getXuatXu());
                ps.setBytes(8, product.getHinhAnh());
                ps.setString(9, product.getMaTH());
                rowsInserted = ps.executeUpdate();
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }
        return rowsInserted;
    }

    @Override
    public int delete(ProductsDTO product) {
        int rowsUpdate = 0;
        try {
            jdbc.openConnection();
            String query = "UPDATE SANPHAM SET trangThai='off' WHERE maSP=? ";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, product.getMaSP());
            rowsUpdate = ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }
        return rowsUpdate;
    }

    @Override
    public int update(ProductsDTO product) {
        int rowsUpdate = 0;
        try {
            jdbc.openConnection();
            String query = "UPDATE SANPHAM SET tenSP = ?, pin = ?,OS=?,camTruoc=?,camSau=?,xuatXu=?,hinhAnh=?,maTH=? WHERE maSP=?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, product.getTenSP());
			ps.setString(2, product.getPin());
			ps.setString(3, product.getOS());
			ps.setString(4, product.getCamTruoc());
			ps.setString(5, product.getCamSau());
			ps.setString(6, product.getXuatXu());
			ps.setBytes(7, product.getHinhAnh());
			ps.setString(8, product.getMaTH());
			ps.setString(9, product.getMaSP());

            rowsUpdate = ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }
        return rowsUpdate;
    }
}
