package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.GenericDao;
import model.entities.Department;

public class Program2 {
	public static void main(String[] arg) {

		// insert test
		GenericDao<Department> depDao = DaoFactory.createDepartmentDao();
		Department d = new Department(null, "Music");
		// depDao.insert(d);

		// update test
		//d.setId(6);
		//d.setName("Guitar");
		//depDao.update(d);
		
		// remove test
		//depDao.delete(8);
		
		// find department by id test
		// System.out.println(depDao.find(6));
		
		// find all
		List<Department> list = new ArrayList<>();
		list = depDao.searchAll();
		list.forEach(System.out::println);

	}
}
