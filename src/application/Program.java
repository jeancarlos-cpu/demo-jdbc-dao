package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.DB;
import db.DBIntegrityException;
import db.DbException;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			conn.commit();
			
			Department obj = new Department(1, "Books");
			Seller s = new Seller(1, "Bob", "bob@gmail.com", new Date(), 3000.00, obj);
			System.out.println(s);

		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back: "+e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Failed to rollback");
			}
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

	}
}
