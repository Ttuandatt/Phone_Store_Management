package BUS;

import java.util.ArrayList;

import DAO.ProductsDAO;
import DTO.PBSPDTO;
import DTO.ProductsDTO;

public class ProductsBUS {
    ProductsDAO productsDAO = new ProductsDAO();

	public ArrayList<ProductsDTO> getAllProducts(){
        return productsDAO.selectAll();
    }
    public ArrayList<PBSPDTO> getAllPBSMBymaSP(String maSP){
        return productsDAO.selectPBSPBymaSP(maSP);
    }
}
