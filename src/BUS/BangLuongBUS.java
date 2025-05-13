package BUS;

import DAO.BangLuongDAO;
import DTO.BangLuongDTO;
import java.util.ArrayList;

public class BangLuongBUS {
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
