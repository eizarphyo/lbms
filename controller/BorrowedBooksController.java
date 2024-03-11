package controller;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.BorrowedBooksModel;

public class BorrowedBooksController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();

		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(BorrowedBooksModel book) {
		String query = "INSERT INTO lbms.borrowed_books (borrowed_by_id, book_id) VALUES (?, ?)";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setInt(1, book.getBorrowedById());
			ps.setInt(2, book.getBookId());			
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
}
