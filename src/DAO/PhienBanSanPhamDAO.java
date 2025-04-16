package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ChiTietPhieuNhapDTO;
import DTO.PhienBanSanPhamDTO;
import Database.JDBCConnection;

public class PhienBanSanPhamDAO implements DAOInterface<PhienBanSanPhamDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	@Override
	public ArrayList<PhienBanSanPhamDTO> selectAll() {
		ArrayList<PhienBanSanPhamDTO> arrPBSP = new ArrayList<PhienBanSanPhamDTO>();

		try {
			jdbc.openConnection();

			String query = "SELECT * FROM PBSP WHERE trangThai='on'";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PhienBanSanPhamDTO pbsp = new PhienBanSanPhamDTO();
				pbsp.setMaPBSP(rs.getString("maPBSP"));
				pbsp.setMauSac(rs.getString("mauSac"));
				pbsp.setRam(rs.getString("ram"));
				pbsp.setRom(rs.getString("rom"));
				pbsp.setGiaBan(rs.getInt("giaBan"));
				pbsp.setSoLuong(rs.getInt("soLuong"));
				pbsp.setTrangThai(rs.getString("trangThai"));
				pbsp.setMaSP(rs.getString("maSP"));
				
				arrPBSP.add(pbsp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}
		return arrPBSP;
	}

	@Override
	public PhienBanSanPhamDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(PhienBanSanPhamDTO pbsp) {
		int rowsInserted = 0;
		try {
			jdbc.openConnection();
			String query = "SELECT * FROM PBSP WHERE maPBSP=?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, pbsp.getMaPBSP());
			
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
                query="INSERT INTO PBSP(maPBSP,mauSac,ram, rom, giaBan, soLuong, trangThai,maSP) "+
						" VALUES (?,?,?,?,?,?,'on',?)";
                ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, pbsp.getMaPBSP());
                ps.setString(2, pbsp.getMauSac());
                ps.setString(3, pbsp.getRam());
                ps.setString(4, pbsp.getRom());
                ps.setInt(5, pbsp.getGiaBan());
                ps.setInt(6, pbsp.getSoLuong());
                ps.setString(7, pbsp.getMaSP());
                rowsInserted = ps.executeUpdate();
            }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return rowsInserted;
	}

	@Override
	public int delete(PhienBanSanPhamDTO pbsp) {
		int rowsUpdate = 0;
        try {
            jdbc.openConnection();
            String query = "UPDATE PBSP SET trangThai='off' WHERE maPBSP=?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, pbsp.getMaPBSP());
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
	public int update(PhienBanSanPhamDTO pbsp) {
		int rowsUpdate = 0;
        try {
            jdbc.openConnection();
            String query = "UPDATE PBSP SET mauSac=?,ram=?,rom=?,giaBan=?,soLuong=? WHERE maPBSP=?";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, pbsp.getMauSac());
			ps.setString(2, pbsp.getRam());
			ps.setString(3, pbsp.getRom());
			ps.setInt(4, pbsp.getGiaBan());
			ps.setInt(5, pbsp.getSoLuong());
			ps.setString(6, pbsp.getMaPBSP());

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
	
	public int updateSoLuong(String maPBSP, int soLuong) {
		int result=0;
		
		try {
			jdbc.openConnection();
			
			String query = "update pbsp set soLuong=soluong + ? where maPBSP=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setInt(1, soLuong);
			ps.setString(2,maPBSP);
			
			result = ps.executeUpdate();
			
			ps.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return result;
	}
	
	public ArrayList<PhienBanSanPhamDTO> getThongTinPBSP(String maPBSP){
		ArrayList<PhienBanSanPhamDTO> thongTinPBSP = new ArrayList<PhienBanSanPhamDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select mausac, ram, rom, masp from pbsp where mapbsp=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maPBSP);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PhienBanSanPhamDTO pbsp = new PhienBanSanPhamDTO();
				
				pbsp.setMauSac(rs.getString("mauSac"));
				pbsp.setRam(rs.getString("ram"));
				pbsp.setRom(rs.getString("rom"));
				pbsp.setMaSP(rs.getString("maSP"));
				
				thongTinPBSP.add(pbsp);
			}
			
			ps.close();
			rs.close();
		
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return thongTinPBSP;
	}
	
	public int tangSoLuong(String maPBSP, int soLuong) {
		int result = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "update pbsp set soLuong=? where mapbsp=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setInt(1, soLuong);
			ps.setString(2, maPBSP);
			
			result = ps.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		
		return result;
	}

}
