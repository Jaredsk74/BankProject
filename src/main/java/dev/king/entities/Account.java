package dev.king.entities;

public class Account implements Comparable<Account> {
	private int id;
	private String name;
	private int amount;
	private int userId;
	
	public Account() {}
	
	public Account(int id, String name, int amount, int userId) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getUser() {
		return userId;
	}
	
	public void setUser(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", amount=" + amount + ", userId=" + userId + "]";
	}

	@Override
	public int compareTo(Account o) {
		return name.compareTo(o.name);
	}
}
