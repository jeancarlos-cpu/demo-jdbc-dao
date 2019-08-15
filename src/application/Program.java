package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;
import db.DBIntegrityException;
import db.DbException;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false);
			
			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 5000 WHERE DepartmentId = 1");
			
			int x = 1;
			if (x > 2 ) {
				throw new SQLException("fake error");
			}
			
			int rows2= st.executeUpdate("UPDATE seller SET BaseSalary = 3000 WHERE DepartmentId = 2");
			
			conn.commit();
			
			System.out.println(rows1 + rows2);

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
