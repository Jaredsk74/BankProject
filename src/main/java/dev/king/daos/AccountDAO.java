package dev.king.daos;

import java.util.Set;

import dev.king.entities.Account;

// CRUD (Create, Read, Update, Delete)
public interface AccountDAO {
	// Create
	public boolean createAccount(Account account);
	
	// Read
	public Account getAccount(int id);
	public Account getAccount(String name, int userId);
	public Set<Account> getAccounts();
	public Set<Account> getAccounts(int userId);
	
	// Update
	public boolean updateAccount(Account account);
	
	// Delete
	public boolean deleteAccount(Account account);
}
