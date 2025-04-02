package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.T;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import DTO.SanPhamDTO;
import DTO.PhienBanSanPhamDTO;
import Database.JDBCConnection;

public class SanPhamDAO implements DAOInterface<SanPhamDTO> {
    JDBCConnection jdbc = new JDBCConnection();

    @Override
    public ArrayList<SanPhamDTO> selectAll() {
        ArrayList<SanPhamDTO> products= new ArrayList<SanPhamDTO>();
        try {

            jdbc.openConnection();

            String query = "SELECT * FROM SANPHAM";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DTO.SanPhamDTO pd = new DTO.SanPhamDTO();
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
    public ArrayList<PhienBanSanPhamDTO> selectPBSPBymaSP(String maSP){
        ArrayList<PhienBanSanPhamDTO> PBSPs=new ArrayList<PhienBanSanPhamDTO>();
        try {

            jdbc.openConnection();

            String query = "SELECT * FROM PBSP WHERE maSP=?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maSP);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	PhienBanSanPhamDTO pd = new PhienBanSanPhamDTO();
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
    public SanPhamDTO selectById(String t){
        return new SanPhamDTO();
    }
    @Override
    public int insert(SanPhamDTO t) {
        return 0;
    }

    @Override
    public int delete(SanPhamDTO t) {
        return 0;

    }

    @Override
    public int update(SanPhamDTO t) {
        return 0;

    }
    
    public String getTenSanPhamByMaPBSP(String maPBSP) {
    	String tenSP = "";
    	
    	try {
    		jdbc.openConnection();
    		
    		String query = "select tensp from sanpham join pbsp on sanpham.masp = pbsp.masp where pbsp.mapbsp=?";
    	
    		PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
    		ps.setString(1, maPBSP);
    	
    		
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			tenSP = rs.getString("tenSP");
    		}
    		
    		ps.close();
    		rs.close();
    	}catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }
    	
    	return tenSP;
    }
    
    public ArrayList<SanPhamDTO> getTenSanPhamByMaPBSP2(String maPBSP) {
    	ArrayList<SanPhamDTO> tenSP = new ArrayList<SanPhamDTO>();
    	
    	try {
    		jdbc.openConnection();
    		
    		String query = "select tensp from sanpham join pbsp on sanpham.masp = pbsp.masp where pbsp.mapbsp=?";
    	
    		PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
    		ps.setString(1, maPBSP);
    	
    		
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			SanPhamDTO sp = new SanPhamDTO();
    			
    			sp.setTenSP(rs.getString("tenSP"));
    			
    			tenSP.add(sp);
    		}
    		
    		ps.close();
    		rs.close();
    	}catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }
    	
    	return tenSP;
    }
}