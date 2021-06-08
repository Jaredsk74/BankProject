package dev.king.services;

import java.util.List;

import dev.king.entities.User;

public interface SuperuserService {
	List<User> getSortedUsers();
	boolean deleteUser(int userId);
	User updateUser(User user);
	User getUser(User user);
	// Create user is available to all users
	// Account CRUD is available to all users
}
