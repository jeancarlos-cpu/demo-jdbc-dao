package application;


import java.util.List;

import model.dao.DaoFactory;
import model.dao.GenericDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		

			
			GenericDao<Seller> sellerDao = DaoFactory.createSellerDao();
			Seller seller = sellerDao.find(2);
			System.out.println(seller);
			
			//////////////////////////////////////
			Department d = new Department(2, null);
			List<Seller> list = sellerDao.findByDepartment(d);
			list.forEach(System.out::println);
			//////////////////////////////////////
			System.out.println();
			list = sellerDao.searchAll();
			list.forEach(System.out::println);


	}
}
