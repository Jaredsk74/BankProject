package dev.king.daos;

import dev.king.entities.User;
import dev.king.utilities.ConnectionUtil;
import dev.king.utilities.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

// Data Access Object logic
public class UserDAOimpl implements UserDAO {
	
	// Single UserDAO
	private static UserDAO udao;
	
	// Create DAO if it doesn't exist
	public static UserDAO getDAO() {
		return (udao == null) ? new UserDAOimpl() : udao;
	}
	
	public boolean createUser(User user) {
		// Connect to database
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "INSERT INTO bank.user VALUES (?, ?, ?, ?, ?, ?)";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setInt(1, 0);
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getFname());
			ps.setString(5, user.getLname());
			ps.setInt(6, user.getSuperuserStatus());
			
			// Execute SQL
			ps.execute();
			return true;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public User getUser(int id) {
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "SELECT * FROM bank.user WHERE id = ?";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setInt(1, id);
			
			// Get results
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			// Create user object from values of query
			User u = new User();
			u.setId(id);
			u.setUsername(rs.getString("username"));
			u.setFname(rs.getString("fname"));
			u.setLname(rs.getString("lname"));
			u.setSuperuserStatus(rs.getInt("superuser"));
			u.setPassword(rs.getString("password"));
			
			return u;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			LogUtil.error("Id doesn't exist");
			return null;
		}
	}
	
	public User getUser(String username) {
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "SELECT * FROM bank.user WHERE username = ?";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setString(1, username);
			
			// Get results
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			// Create user object from values of query
			User u = new User();
			u.setId(rs.getInt("id"));
			u.setUsername(rs.getString("username"));
			u.setFname(rs.getString("fname"));
			u.setLname(rs.getString("lname"));
			u.setSuperuserStatus(rs.getInt("superuser"));
			u.setPassword(rs.getString("password"));
			
			return u;
		}
		catch (SQLException e) {
			// e.printStackTrace();
			LogUtil.error("Username doesn't exist");
			return null;
		}	
	}
	
	public User getUser(String username, String password) {
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "SELECT * FROM bank.user WHERE username = ? AND password = ?";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setString(1, username);
			ps.setString(2, password);
			
			// Get results
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			// Create user object from values of query
			User u = new User();
			u.setId(rs.getInt("id"));
			u.setUsername(username);
			u.setFname(rs.getString("fname"));
			u.setLname(rs.getString("lname"));
			u.setSuperuserStatus(rs.getInt("superuser"));
			u.setPassword(password);
			
			return u;
		}
		catch (SQLException e) {
			// e.printStackTrace();
			LogUtil.error("Incorrect username or password");
			return null;
		}	
	}
	
	public Set<User> getUsers() {
		// Connect to database
		try (Connection con = ConnectionUtil.getConnection()){
			// Create Statement
			String sql = "SELECT * FROM bank.user";
			
			// Prepared statement
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Run query
			ResultSet rs = ps.executeQuery();
			
			// Build list of courses
			Set<User> users = new HashSet<>();
			
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setSuperuserStatus(rs.getInt("superuser"));
				u.setPassword(rs.getString("password"));
				users.add(u);
			}
			return users;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateUser(User user) {
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "UPDATE bank.user SET username = ?, password = ?, fname = ?, lname = ?, superuser = ? WHERE id = ?";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFname());
			ps.setString(4, user.getLname());
			ps.setInt(5, user.getSuperuserStatus());
			ps.setInt(6, user.getId());
			
			// Execute SQL			
			ps.execute();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(User user) {
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "DELETE FROM bank.user WHERE id = ?";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setInt(1, user.getId());
			
			// Execute SQL
			ps.execute();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
