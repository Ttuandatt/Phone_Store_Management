package BUS;
/*Mới update*/
import java.lang.reflect.Array;
import java.util.ArrayList;

import DAO.BangLuongDAO;
import DTO.BangLuongDTO;

public class BangLuongBUS {
    private BangLuongDAO bangLuongDAO = new BangLuongDAO();
    public ArrayList<BangLuongDTO> getBangLuongByNV(String maNV) {
        ArrayList<BangLuongDTO> arrBangLuong = bangLuongDAO.selectByNV(maNV);
        return arrBangLuong;
    }
    
    BangLuongDAO blDAO = new BangLuongDAO();
    public ArrayList<BangLuongDTO> selectAll() {
        return blDAO.selectAll();
    }

    public BangLuongDTO selectById(String maBL) {
        return blDAO.selectById(maBL);
    }

    public int updateBangLuong(BangLuongDTO bangLuong) {
        return blDAO.update(bangLuong);
    }

    public ArrayList<BangLuongDTO> selectByTime(String thang, String nam) {
        return blDAO.selectByTime(thang, nam);
    }
    public ArrayList<BangLuongDTO> selectByKeyWord(String keyWord) {
        return blDAO.selectByKeyWord(keyWord);
    }
}