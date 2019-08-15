package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Seller;
import model.dao.GenericDao;
import model.entities.Department;

public class SellerDaoJDBC implements GenericDao<Seller>{
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller find(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName  "
					+ "FROM seller INNER JOIN department  "
					+ "ON seller.DepartmentId = department.Id  "
					+ "WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				Seller s = new Seller();
				s.setName(rs.getString("Name"));
				s.setEmail(rs.getString("Email"));
				s.setBaseSalary(rs.getDouble("BaseSalary"));
				s.setBirthDate(rs.getDate("BirthDate"));
				s.setDepartment(dep);
				return s;
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return null;
	}


	@Override
	public List<Seller> searchAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
