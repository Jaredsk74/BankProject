package dev.king.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dev.king.daos.AccountDAO;
import dev.king.daos.AccountDAOimpl;
import dev.king.daos.UserDAO;
import dev.king.daos.UserDAOimpl;
import dev.king.entities.Account;
import dev.king.entities.User;
import dev.king.utilities.LogUtil;

public class SuperuserServiceImpl implements SuperuserService {	
	
	private UserDAO udao = UserDAOimpl.getDAO();
	private AccountDAO adao = AccountDAOimpl.getDAO();

	@Override
	public List<User> getSortedUsers() {
		ArrayList<User> list = new ArrayList<>(udao.getUsers());
		if (list.isEmpty()) {
			return null;
		}
		list.sort(null);
		return list;
	}
	
	@Override
	public boolean deleteUser(int userId) {
		// Grab user and its accounts
		User user = udao.getUser(userId);
		Set<Account> accounts = adao.getAccounts(userId);
		
		// Check account balances for 0
		for (Account account : accounts) {
			if (account.getAmount() > 0) {
				LogUtil.error("An account has balance");
				return false;
			}
		}
	
		// Delete user and database will cascade
		udao.deleteUser(user);
		return true;
	}

	@Override
	public User updateUser(User user) {
		udao.updateUser(user);
		return user;
	}

	@Override
	public User getUser(User user) {
		int id = user.getId();
		return id == 0 ? udao.getUser(user.getUsername(), user.getPassword()) : udao.getUser(id);
	}

}
