package dev.king.daos;

import java.util.Set;

import dev.king.entities.User;

// CRUD (Create, Read, Update, Delete)
public interface UserDAO {
	// Create
	public boolean createUser(User user);
	
	// Read
	public User getUser(int id);
	public User getUser(String username);
	public User getUser(String username, String password);
	public Set<User> getUsers();
	
	// Update
	public boolean updateUser(User user);
	
	// Delete
	public boolean deleteUser(User user);
}
