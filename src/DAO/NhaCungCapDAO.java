/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.NhaCungCapDTO;
import Database.JDBCConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



/**
 *
 * @author ACER
 */
public class NhaCungCapDAO implements DAOInterface<NhaCungCapDTO>{
  	JDBCConnection jdbc = new JDBCConnection();
    
    
    @Override
    public ArrayList<NhaCungCapDTO> selectAll(){
        ArrayList<NhaCungCapDTO> arr = new ArrayList<NhaCungCapDTO>();
        
            try{
            	jdbc.openConnection();
                String query = "SELECT * FROM nhacungcap";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    NhaCungCapDTO nhacungcapDTO = new NhaCungCapDTO();
                    nhacungcapDTO.setMaNCC(rs.getString(1));
                    nhacungcapDTO.setTenNCC(rs.getString(2));
                    nhacungcapDTO.setSdt(rs.getString(3));
                    nhacungcapDTO.setEmail(rs.getString(4));
                    nhacungcapDTO.setDiaChi(rs.getString(5));
                    nhacungcapDTO.setTrangthai(rs.getString(6));
                    
                    arr.add(nhacungcapDTO);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                jdbc.closeConnection();
            }
        
        return arr;
    }
    
    public boolean has(String mancc){
        boolean result = false;
            try{
            	jdbc.openConnection();
                String query = "SELECT * FROM nhacungcap WHERE mancc = ?";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, mancc);
                ResultSet rs =  ps.executeQuery();
                result = rs.next();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                jdbc.closeConnection();
            }
      
        
        return result;
    }
    
    @Override
    public int insert(NhaCungCapDTO ncc){
        int result = 0;
            try{
            	jdbc.openConnection();
                String query = "INSERT INTO nhacungcap VALUES (?,?,?,?,?)";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, ncc.getMaNCC());
                ps.setString(2, ncc.getTenNCC());
                ps.setString(3, ncc.getDiaChi());
                ps.setString(4, ncc.getSdt());
                ps.setInt(5, 1);
                    result=ps.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                jdbc.closeConnection();
            }
        
        return result;
    }
    
    
    
    @Override
    public int delete(NhaCungCapDTO ncc) {
        int result = 0;
            try {
            	jdbc.openConnection();
                String query = "UPDATE nhacungcap SET trangthai = 0 WHERE maNCC = ?";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, ncc.getMaNCC());
                    result = ps.executeUpdate();
            }catch (Exception e) {
                e.printStackTrace();
            }finally{
                jdbc.closeConnection();
            }
        return result;
    }
    
    
    @Override
    public int update(NhaCungCapDTO ncc) {
        int result = 0;
            try {
            	jdbc.openConnection();
                String query = "UPDATE nhacungcap SET tenncc = ?, diachi = ?, sdt = ? WHERE mancc = ?";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, ncc.getTenNCC());
                ps.setString(2, ncc.getDiaChi());
                ps.setString(3, ncc.getSdt());
                ps.setString(4, ncc.getMaNCC());
        
                result=ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                jdbc.closeConnection();
            }
        
        return result;
    }
    
    @Override
    public NhaCungCapDTO selectById(String maNCC){
        NhaCungCapDTO ncc = null;
            try{
            	jdbc.openConnection();
                String query = "SELECT * FROM nhacungcap WHERE mancc = ?";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, maNCC);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    String mancc = rs.getString(1);
                    String tenncc = rs.getString(2);
                    String diachi = rs.getString(3);
                    String sdt = rs.getString(5);
                    String trangThai = rs.getString(6);
                    String email = rs.getString(4); 
                    
                
                    ncc = new NhaCungCapDTO(mancc, tenncc, diachi, email,sdt, trangThai);
                }
                
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                jdbc.closeConnection();
            }
        
        return ncc;
    }
    
    
    public ArrayList<NhaCungCapDTO> search(String searchContent){
        ArrayList<NhaCungCapDTO> arr = new ArrayList<>();
            try{
            	jdbc.openConnection();
                String query = "SELECT * FROM sanpham WHERE "
                        + "mancc LIKE ? OR "
                        + "tenncc LIKE ? OR"
                        + "diachi LIKE ? OR"
                        + "sdt LIKE ? OR";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, "%" + searchContent + "%");
                ps.setString(2, "%" + searchContent + "%");
                ps.setString(3, "%" + searchContent + "%");
                ps.setString(4, "%" + searchContent + "%");
                
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    NhaCungCapDTO ncc = new NhaCungCapDTO();
                    ncc.setMaNCC(rs.getString(1));
                    ncc.setTenNCC(rs.getString(2));
                    ncc.setDiaChi(rs.getString(3));
                    ncc.setSdt(rs.getString(4));
                    
                    
                    arr.add(ncc);

                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                jdbc.closeConnection();
            }
        
        return arr;
    }
    
    public NhaCungCapDTO getByName(String tenncc) {
		NhaCungCapDTO ncc = null;
			try {
				jdbc.openConnection();
				String query = "select mancc,tenncc,diachi,sdt from nhacungcap where tenncc like ?";
				PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
				ps.setString(1, "%" + tenncc + "%");
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String mancc = rs.getString(1);
					String tencc=rs.getString(2);
					String diachi=rs.getString(3);
					String sdt=rs.getString(4);
					ncc=new NhaCungCapDTO(mancc,tencc,diachi,sdt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jdbc.closeConnection();
			}
		
		return ncc;

	}
  

}
