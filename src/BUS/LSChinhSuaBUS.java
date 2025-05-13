/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.LSChinhSuaDAO;
import DTO.LSChinhSuaDTO;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class LSChinhSuaBUS {
    LSChinhSuaDAO lsDAO = new LSChinhSuaDAO();
    
    public ArrayList<LSChinhSuaDTO> selectByMaNV(String maNV) {
        return lsDAO.selectByMaNV(maNV);
    }

    public ArrayList<LSChinhSuaDTO> selectAll() {
        return lsDAO.selectAll();
    }
    
    public int insert(LSChinhSuaDTO ls) {
        return lsDAO.insert(ls);
    }    
    
}
