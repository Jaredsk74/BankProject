package dev.king.services;

import java.util.List;

import dev.king.entities.Account;
import dev.king.entities.User;

public interface UserService {
	User newUser(User user);
	User Login(String username, String password);
	Account newAccount(Account account);
	Account getAccount(int accountId);
	Account getAccount(String name, int userId);
	List<Account> getSortedAccounts(int userId);
	Account deposit(int accountId, int amount);
	Account withdraw(int accountId, int amount);
	boolean deleteAccount(int accountId);
}