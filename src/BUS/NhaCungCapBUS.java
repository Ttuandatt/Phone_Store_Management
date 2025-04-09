/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

public class NhaCungCapBUS {
    NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    
    public ArrayList<NhaCungCapDTO> getAllNhaCungCap(){
        return nccDAO.selectAll();
    }
    
    public String addNhaCungCap(NhaCungCapDTO ncc){
        if(nccDAO.has(ncc.getMaNCC()))
            return "Mã nhà cung cấp đã tồn tại";
        if(nccDAO.insert(ncc)>0)
            return "Thêm nhà cung cấp thành công";
        return "Thêm thất bại";
    }
    
    public String deleteNhaCungCap(NhaCungCapDTO ncc){
        if(nccDAO.delete(ncc)>0)
            return "Xóa nhà cung cấp thành công";
        return "Xóa nhà cung cấp thất bại";
    }
    
    public String updateNhaCungCap(NhaCungCapDTO ncc){
        if(nccDAO.update(ncc)>0)
            return "Cập nhật nhà cung cấp thành công";
        return "Cập nhật nhà cung cấp thất bại";
    }
    
    public NhaCungCapDTO getByName(String tenncc) {
    	return nccDAO.getByName(tenncc);
    }
   
}