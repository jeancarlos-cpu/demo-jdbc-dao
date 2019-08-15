package model.dao;

import java.util.List;

import model.entities.Department;

public interface GenericDao<T> {
	
	void insert(T t);
	void update(T t);
	void delete(Integer id);
	T find(Integer id);
	List<T> findByDepartment(Department d);
	List<T> searchAll();

}
