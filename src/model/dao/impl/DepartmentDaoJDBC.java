package model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.GenericDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements GenericDao<Department>{
	
	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Department d) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO department"
					+ "(Name) "
					+ "VALUES (?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			
			st.setString(1, d.getName());
			st.executeUpdate();
			
			rs = st.getGeneratedKeys();
			rs.next();
			d.setId(rs.getInt(1));
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public void update(Department d) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ? ");
			
			st.setString(1, d.getName());
			st.setInt(2, d.getId());
			st.executeUpdate();
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void delete(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department "
					+ "WHERE Id = ? ");
			st.setInt(1, id);
			st.executeUpdate();
			}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department find(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM department "
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			rs.next();
			
			return new Department(rs.getInt("Id"), rs.getString("Name"));
			
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	// will not be used.
	@Override
	public List<Department> findByDepartment(Department d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> searchAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Department> list = new ArrayList<>();
		try {
			st = conn.prepareStatement("SELECT * FROM department ");
				
			rs = st.executeQuery();
			
			while(rs.next()) {
				list.add(new Department(rs.getInt("Id"),rs.getString("Name")));
			}
			return list;
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
