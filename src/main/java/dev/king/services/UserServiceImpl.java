package dev.king.services;

import java.util.ArrayList;
import java.util.List;
import dev.king.daos.AccountDAO;
import dev.king.daos.AccountDAOimpl;
import dev.king.daos.UserDAO;
import dev.king.daos.UserDAOimpl;
import dev.king.entities.Account;
import dev.king.entities.User;
import dev.king.utilities.LogUtil;

public class UserServiceImpl implements UserService {
	
	private UserDAO udao = UserDAOimpl.getDAO();
	private AccountDAO adao = AccountDAOimpl.getDAO();

	@Override
	public User newUser(User user) {
		// First create the user and then retrieve it from the database to grab the correct id
		if (udao.createUser(user)) {
			return udao.getUser(user.getUsername(), user.getPassword());
		}
		//udao.createUser(user);
		return null;
	}

	@Override
	public User Login(String username, String password) {
		// Make sure the supplied password matches the database record for the username
		User user = udao.getUser(username);

		if (user != null && user.getPassword().matches(password)) {
			return user;
		}
		return null;
	}

	@Override
	public Account newAccount(Account account) {
		// First create the account and then retrieve it from the database to get the correct id
		adao.createAccount(account);
		return adao.getAccount(account.getName(), account.getUser());
	}
	
	@Override
	public Account getAccount(int accountId) {
		return adao.getAccount(accountId);
	}

	@Override
	public Account getAccount(String name, int userId) {
		return adao.getAccount(name, userId);
	}

	@Override
	public List<Account> getSortedAccounts(int userId) {
		ArrayList<Account> list = new ArrayList<>(adao.getAccounts(userId));
		if (list.isEmpty()) {
			return null;
		}
		list.sort(null);
		return list;
	}

	// Test 
	@Override
	public Account deposit(int accountId, int amount) {
		if (amount <= 0) {
			return null;
		}
		Account account = adao.getAccount(accountId);
		account.setAmount(account.getAmount() + amount);
		adao.updateAccount(account);
		return account;
	}

	@Override
	public Account withdraw(int accountId, int amount) {
		if (amount <= 0) {
			return null;
		}
		Account account = adao.getAccount(accountId);
		if (account.getAmount() - amount < 0) {
			return null;
		}
		account.setAmount(account.getAmount() - amount);
		adao.updateAccount(account);
		return account;	
	}

	@Override
	public boolean deleteAccount(int accountId) {
		// Grab user account
		Account account = adao.getAccount(accountId);
		
		// Check account balance for 0
		if (account.getAmount() > 0) {
			LogUtil.error("This account has a balance");
			return false;
		}

		return adao.deleteAccount(account);
	}

}
