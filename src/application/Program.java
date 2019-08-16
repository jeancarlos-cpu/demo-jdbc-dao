package application;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.GenericDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		

			
			GenericDao<Seller> sellerDao = DaoFactory.createSellerDao();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
			System.out.println();
			seller = sellerDao.find(13);
			seller.setName("Not Anakin");
			seller.setEmail("notthefather@gmail.com");
			seller.setBaseSalary(1000.00);
			System.out.println(seller);
			sellerDao.delete(5);

	}
}
