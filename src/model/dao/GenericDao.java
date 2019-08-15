package model.dao;

import java.util.List;

public interface GenericDao<T> {
	
	void insert(T t);
	void update(T t);
	void delete(Integer id);
	T find(Integer id);
	List<T> searchAll();

}
