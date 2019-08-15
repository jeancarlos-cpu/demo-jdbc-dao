package model.dao;

import model.dao.impl.GenericDaoJDBC;

public class DaoFactory {
	
	public static GenericDao createGenericDao() {
		return new GenericDaoJDBC();
	}

}
