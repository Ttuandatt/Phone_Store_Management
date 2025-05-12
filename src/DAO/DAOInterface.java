package DAO;

import java.util.ArrayList;

public interface DAOInterface<T> {
	public ArrayList<T> selectAll();
	public T selectById(String t);
	public int insert(T t);
	public int delete(T t);
	public int update(T t);
	
}
