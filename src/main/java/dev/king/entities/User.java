package dev.king.entities;

public class User implements Comparable<User> {
	private int id;
	private String username;
	private String password;
	private String fname;
	private String lname;
	private int superuserStatus;
	
	public User() {}

	public User(int id, String username, String password, String fname, String lname, int superuserStatus) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.superuserStatus = superuserStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getSuperuserStatus() {
		return superuserStatus;
	}

	public void setSuperuserStatus(int superuserStatus) {
		this.superuserStatus = superuserStatus;
	}
	
	public boolean isSuperuser() {
		return superuserStatus == 0 ? false : true;
	}
	
	public void isSuperuser(boolean status) {
		superuserStatus = status ? 1 : 0;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", fname=" + fname + ", lname="
				+ lname + ", superuserStatus=" + superuserStatus + "]";
	}

	@Override
	public int compareTo(User o) {
		return username.compareTo(o.username);
	}
}
