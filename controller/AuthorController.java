package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.AuthorModel;

public class AuthorController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();

		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(AuthorModel author) {
		String query = "INSERT INTO lbms.authors (name) VALUES (?)";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setString(1, author.getName());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	public int getIdByName(AuthorModel author) {
		String query = "SELECT id from lbms.authors WHERE name=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setString(1, author.getName());
						
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int hasDuplicate(AuthorModel author) {
		String query = "SELECT id from lbms.authors WHERE name=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setString(1, author.getName());
						
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
