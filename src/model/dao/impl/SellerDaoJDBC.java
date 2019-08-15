package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.entities.Seller;
import model.dao.GenericDao;
import model.entities.Department;

public class SellerDaoJDBC implements GenericDao<Seller> {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller s) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO seller " + 
					"(Name, Email, BirthDate, BaseSalary, DepartmentId)  "
					+ "VALUES  (?, ?, ?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, s.getName());
			st.setString(2, s.getEmail());
			st.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
			st.setDouble(4, s.getBaseSalary());
			st.setInt(5, s.getDepartment().getId());

			st.executeUpdate();

			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			s.setId(rs.getInt(1));
			DB.closeResultSet(rs);
			DB.closeStatement(st);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void update(Seller s) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE seller  "
					+ "SET Name = ?, Email = ?, BirthDate = ?, "
					+ "BaseSalary = ?, DepartmentId = ?  "
					+ "WHERE Id = ? ");

			st.setString(1, s.getName());
			st.setString(2, s.getEmail());
			st.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
			st.setDouble(4, s.getBaseSalary());
			st.setInt(5, s.getDepartment().getId());
			st.setInt(6, s.getId());
			
			st.executeUpdate();
			
			DB.closeStatement(st);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
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
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName  " + "FROM seller INNER JOIN department  "
							+ "ON seller.DepartmentId = department.Id  " + "WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDep(rs);
				Seller s = instatiateSeller(rs, dep);
				return s;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return null;
	}

	private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller s = new Seller();
		s.setName(rs.getString("Name"));
		s.setEmail(rs.getString("Email"));
		s.setBaseSalary(rs.getDouble("BaseSalary"));
		s.setBirthDate(rs.getDate("BirthDate"));
		s.setId(rs.getInt("Id"));
		s.setDepartment(dep);
		return s;
	}

	private Department instantiateDep(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> searchAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName  " + "FROM seller INNER JOIN department  "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();

			Map<Integer, Department> map = new HashMap<>();

			Seller s = new Seller();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDep(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				s = instatiateSeller(rs, dep);
				list.add(s);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department d) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName  " + "FROM seller INNER JOIN department  "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			st.setInt(1, d.getId());

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();

			Map<Integer, Department> map = new HashMap<>();

			Seller s = new Seller();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDep(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				s = instatiateSeller(rs, dep);
				list.add(s);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
