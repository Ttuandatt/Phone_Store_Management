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
        arrBangLuong=bangLuongDAO.selectByNV(maNV);
        return arrBangLuong;
    }
}
