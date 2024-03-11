package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.BookModel;


public class BookController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();

		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(BookModel book) {
		String query = "INSERT INTO lbms.books (author_id, title, description, price, available) VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setInt(1, book.getAuthorId());
			ps.setString(2, book.getTitle());
			ps.setString(3, book.getDescription());
			ps.setInt(4, book.getPrice());
			ps.setBoolean(5, book.isAvailable());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	public int update(BookModel book) {
		String query = "UPDATE lbms.books SET title=?, description=?, price=?, available=? WHERE id=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
//			ps.setInt(1, book.getAuthorId());
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getDescription());
			ps.setInt(3, book.getPrice());
			ps.setBoolean(4, book.isAvailable());
			ps.setInt(5, book.getId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	
	
	public int delete(BookModel book) {
		String query = "DELETE FROM lbms.books WHERE id=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setInt(1, book.getId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	public List<BookModel> getAllBooks() {
		String query = "SELECT books.id, books.title, authors.name, books.price, books.description, books.available FROM books JOIN authors ON books.author_id=authors.id ORDER BY title ASC";
		List<BookModel> books = new ArrayList<BookModel>();
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				BookModel book = new BookModel();
				
				book.setId(rs.getInt("id"));
//				book.setAuthorId(rs.getInt("author_id"));
				book.setAuthorName(rs.getString("name"));
				book.setTitle(rs.getString("title"));
				book.setDescription(rs.getString("description"));
				book.setPrice(rs.getInt("price"));
				book.setAvailable(rs.getBoolean("available"));
				
				books.add(book);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return books;
	}
	
	public List<BookModel> getAvailableBooks() {
		String query = "SELECT books.id, books.title, authors.name, books.price, books.description, books.available FROM books JOIN authors ON books.author_id=authors.id AND books.available=true ORDER BY title ASC";
		List<BookModel> books = new ArrayList<BookModel>();
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				BookModel book = new BookModel();
				
				book.setId(rs.getInt("id"));
				book.setAuthorName(rs.getString("name"));
				book.setTitle(rs.getString("title"));
				book.setDescription(rs.getString("description"));
				book.setPrice(rs.getInt("price"));
				book.setAvailable(rs.getBoolean("available"));
				
				books.add(book);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return books;
	}
	
}
