package dev.king.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import dev.king.entities.Account;
import dev.king.utilities.ConnectionUtil;
import dev.king.utilities.LogUtil;

//Data Access Object logic
public class AccountDAOimpl implements AccountDAO {
	
	private static AccountDAO adao;
	private AccountDAOimpl() {}
	
	// If DAO doesn't exist, create it
	public static AccountDAO getDAO() {
		return (adao == null) ? new AccountDAOimpl() : adao;
	}
	
	
	public boolean createAccount(Account account) {
		// Connect to database
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "INSERT INTO bank.account VALUES (?, ?, ?, ?)";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setInt(1, 0);
			ps.setString(2, account.getName());
			ps.setDouble(3, account.getAmount());
			ps.setInt(4, account.getUser());
			
			// Execute SQL
			ps.execute();
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}

	public Account getAccount(int id) {
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "SELECT * FROM bank.account WHERE id = ?";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setInt(1, id);
			
			// Get results
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			// Create user object from values of query
			Account a = new Account();
			a.setId(rs.getInt("id"));
			a.setUser(rs.getInt("user_id"));
			a.setAmount(rs.getInt("amount"));
			a.setName(rs.getString("name"));
			
			return a;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			LogUtil.error("Account id doesn't exist");
			return null;
		}	
	}
	
	public Account getAccount(String name, int userId) {
		try (Connection con = ConnectionUtil.getConnection()){
			// Create Statement
			String sql = "SELECT * FROM bank.account WHERE name = ? AND user_id = ?";
			
			// Prepared Statement
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setString(1, name);
			ps.setInt(2, userId);
			
			// Run query
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			// Create user object from values of query
			Account a = new Account();
			a.setId(rs.getInt("id"));
			a.setUser(rs.getInt("user_id"));
			a.setAmount(rs.getInt("amount"));
			a.setName(rs.getString("name"));
			
			return a;

		} catch (SQLException e) {
			//e.printStackTrace();
			LogUtil.error("Name or User is incorrect");
			return null;
		}
		
	}
	
	public Set<Account> getAccounts() {
		// Connect to database
		try (Connection con = ConnectionUtil.getConnection()){
			// Create Statement
			String sql = "SELECT * FROM bank.account";
			
			// Prepared statement
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Run query
			ResultSet rs = ps.executeQuery();
			
			// Build list of courses
			Set<Account> accounts = new HashSet<>();
			
			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setUser(rs.getInt("user_id"));
				a.setAmount(rs.getInt("amount"));
				a.setName(rs.getString("name"));
				// Add account to list
				accounts.add(a);
			}
			return accounts;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Set<Account> getAccounts(int userId) {
		// Connect to database
		try (Connection con = ConnectionUtil.getConnection()){
			// Create Statement
			String sql = "SELECT * FROM bank.account WHERE user_id = ?";
			
			// Prepared statement
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			
			// Run query
			ResultSet rs = ps.executeQuery();
			
			// Build list of accounts
			Set<Account> accounts = new HashSet<>();
			
			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setUser(rs.getInt("user_id"));
				a.setAmount(rs.getInt("amount"));
				a.setName(rs.getString("name"));
				// Add account to list
				accounts.add(a);
			}
			return accounts;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			LogUtil.error("User id doesn't exist");
			return null;
		}		
	}
	
	public boolean updateAccount(Account account) {
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "UPDATE bank.account SET name = ?, amount = ?, user_id = ? WHERE id = ?";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setString(1, account.getName());
			ps.setInt(2, account.getAmount());
			ps.setInt(3, account.getUser());
			ps.setInt(4, account.getId());
			
			// Execute SQL
			ps.execute();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	}
	
	public boolean deleteAccount(Account account) {
		try (Connection con = ConnectionUtil.getConnection()) { 
			// Create Statement
			String sql = "DELETE FROM bank.account WHERE id = ?";
			
			// Parameterized SQL
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Set each variable
			ps.setInt(1, account.getId());
			
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
