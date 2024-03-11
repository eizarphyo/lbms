package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.BorrowedByModel;

public class BorrowedByController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();

		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(BorrowedByModel borrowedBy) {
		String query = "INSERT INTO lbms.borrowed_by (borrower_id, date) VALUES (?, ?)";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setInt(1, borrowedBy.getBorrowerId());
			ps.setString(2, borrowedBy.getDate());		
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	public int getLatestBorrowedBy(BorrowedByModel borrowedBy) {
		String query = "SELECT id FROM lbms.borrowed_by WHERE borrower_id=? ORDER BY id DESC";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setInt(1, borrowedBy.getBorrowerId());
//			ps.setInt(1, 1);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return -1;
	}
}
