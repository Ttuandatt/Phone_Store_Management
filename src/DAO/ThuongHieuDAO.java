package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ThuongHieuDTO;
import Database.JDBCConnection;

public class ThuongHieuDAO implements DAOInterface<ThuongHieuDTO> {
    JDBCConnection jdbc = new JDBCConnection();
    @Override
    public ArrayList<DTO.ThuongHieuDTO> selectAll(){
        ArrayList<DTO.ThuongHieuDTO> thuongHieus= new ArrayList<DTO.ThuongHieuDTO>();
        try {

            jdbc.openConnection();
            String query = "SELECT * FROM THUONGHIEU WHERE trangThai='on'";

            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DTO.ThuongHieuDTO pd = new DTO.ThuongHieuDTO();
                pd.setMaTH(rs.getString("maTH"));
                pd.setTenTH(rs.getString("tenTH"));
                thuongHieus.add(pd);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            jdbc.closeConnection();
        }
        return thuongHieus;
    }
    @Override
	public ThuongHieuDTO selectById(String t){
        return new ThuongHieuDTO();
    }
    @Override
	public int insert(ThuongHieuDTO t){
        return 0;
    }
    @Override
	public int delete(ThuongHieuDTO t){
        return 0;
    }
    @Override
	public int update(ThuongHieuDTO t){
        return 0;
    }
}
