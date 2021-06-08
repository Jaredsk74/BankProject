package dev.king.app;

import java.util.ArrayList;
import java.util.Scanner;

import dev.king.entities.Account;
import dev.king.entities.User;
import dev.king.services.SuperuserServiceImpl;
import dev.king.services.UserServiceImpl;

public class App {
	static Scanner sc = new Scanner(System.in);
	static UserServiceImpl us = new UserServiceImpl();
	static SuperuserServiceImpl ss = new SuperuserServiceImpl();

	public static void main(String[] args) {
		mainMenu();
	}
	
	private static void mainMenu() {
		System.out.println("Welcome to the bank. Please input an option:");
		System.out.println("1. Create an account");
		System.out.println("2. Login");
		System.out.println("3. Exit");
		char input = sc.nextLine().charAt(0);
		
		switch (input) {
			case ('1'):
				newUser();
				break;
			case ('2'):
				login();
				break;
			case ('3'):
				System.exit(0);
				break;
			default:
				System.out.flush();
				System.out.println("Invalid input. Please try again.");
				break;
		}		
	}
	
	private static void newUser() {
		// Collect information
		System.out.println("Please enter your username:");
		String user = sc.nextLine();
		System.out.println("Please enter your password:");
		String pass = sc.nextLine();
		System.out.println("Please enter your first name:");
		String fname = sc.nextLine();
		System.out.println("Please enter your last name:");
		String lname = sc.nextLine();
		User u = new User(0, user, pass, fname, lname, 0);
		
		u = us.newUser(u);
		if (u != null) {
			System.out.println("User successfully created. You can login now.");
		}
		else {
			System.out.println("User could not be created. Username already exists or was left blank.");
		}
		mainMenu();
	}
	
	private static void login() {
		System.out.println("Please enter your username:");
		String user = sc.nextLine();
		System.out.println("Please enter your password:");
		String pass = sc.nextLine();
		
		User u = us.Login(user, pass);
		if (u != null) {
			if (u.isSuperuser()) {
				loggedInSu(u);
			}
			else {
				loggedIn(u);
			}
		}
		else {
			System.out.println("Username or password is incorrect.");
			mainMenu();
		}
	}

	private static void loggedIn(User u) {
		System.out.println("Welcome to the bank " + u.getFname() + " " + u.getLname() + ".");
		ArrayList<Account> accs = (ArrayList<Account>) us.getSortedAccounts(u.getId());
		
		// Check number of accounts
		if(accs != null) {
			optionsWithAcc(u, accs);
		}
		else {
			options(u, accs);
		}
	}

	private static void optionsWithAcc(User u, ArrayList<Account> accs) {
	
		System.out.println("Here are your account(s): ");
		for(int i = 0; i < accs.size(); i++) {
			Account a = accs.get(i);
			System.out.println(a.getName() + ": $" + a.getAmount() + " (Account ID: " + a.getId() + "}");
		}
		optionsWithAcc(u, (ArrayList<Account>) us.getSortedAccounts(u.getId()));
	}

	private static void options(User u, ArrayList<Account> accs) {
		System.out.println("Please select an option:");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Create an account");
		System.out.println("4. Delete an account");
		System.out.println("5. Logout");
		
		char input = sc.nextLine().charAt(0);
		switch (input) {
			case '1':
				deposit(u, accs);
				break;
			case '2':
				withdraw(u, accs);
				break;
			case '3':
				newAcc(u, accs);
				break;
			case '4':
				delAcc(u, accs);
				break;
			case '5':
				mainMenu();
				break;
			default:
				System.out.println("Invalid input. Please try again.");
				optionsWithAcc(u, (ArrayList<Account>) us.getSortedAccounts(u.getId()));
		}
	}

	private static void delAcc(User u, ArrayList<Account> accs) {
		System.out.println("Enter the id of the account you would like to delete");
		for(int i = 0; i < accs.size(); i++) {
			Account a = accs.get(i);
			System.out.println(a.getName() + ": $" + a.getAmount() + " (Account ID: " + a.getId() + "}");
		}
		int a = Integer.parseInt(sc.nextLine());
		Account acc = us.getAccount(a);
		if (acc == null || acc.getUser() != u.getId()) {
			acc = null;
		}
		
		if (acc != null) {
			us.deleteAccount(a);
			System.out.println("Account deleted.");
			optionsWithAcc(u, (ArrayList<Account>) us.getSortedAccounts(u.getId()));
		}
		else {
			System.out.println("Invalid account.");
			optionsWithAcc(u, (ArrayList<Account>) us.getSortedAccounts(u.getId()));
		}
	}

	private static void newAcc(User u, ArrayList<Account> accs) {
		System.out.println("Please enter the account name:");
		String name = sc.nextLine();
		Account output = null;
		if (name.length() > 0) {
			output = us.newAccount(new Account(0, name, 0, u.getId()));
		}
		
		if (output != null) {
			System.out.println("Created account.");
		}
		else {
			System.out.println("Error creating account.");
		}
		optionsWithAcc(u, (ArrayList<Account>) us.getSortedAccounts(u.getId()));
	}

	private static void withdraw(User u, ArrayList<Account> accs) {
		System.out.println("Which account are you withdrawing from? Enter the account id:");
		for(int i = 0; i < accs.size(); i++) {
			Account a = accs.get(i);
			System.out.println(a.getName() + ": $" + a.getAmount() + " (Account ID: " + a.getId() + "}");
		}
		int a = Integer.parseInt(sc.nextLine());
		Account acc = us.getAccount(a);
		if (acc == null || acc.getUser() != u.getId()) {
			acc = null;
		}
		
		if (acc != null) {
			System.out.println("Enter an amount to withdraw:");
			int amount = sc.nextInt();
			sc.nextLine();
			us.withdraw(acc.getId(), amount);
			System.out.println("Withdrew money");
			
		}
		else {
			System.out.println("Invalid account");
		}
		optionsWithAcc(u, (ArrayList<Account>) us.getSortedAccounts(u.getId()));
	}

	private static void deposit(User u, ArrayList<Account> accs) {
		System.out.println("Which account are you depositing to? Enter the account id:");
		for(int i = 0; i < accs.size(); i++) {
			Account a = accs.get(i);
			System.out.println(a.getName() + ": $" + a.getAmount() + " (Account ID: " + a.getId() + "}");
		}
		int a = Integer.parseInt(sc.nextLine());
		Account acc = us.getAccount(a);
		if (acc == null || acc.getUser() != u.getId()) {
			acc = null;
		}
		
		if (acc != null) {
			System.out.println("Enter an amount to deposit:");
			int amount = sc.nextInt();
			sc.nextLine();
			us.deposit(acc.getId(), amount);
			System.out.println("Deposited money");			
		}
		else {
			System.out.println("Invalid account");
		}
		optionsWithAcc(u, (ArrayList<Account>) us.getSortedAccounts(u.getId()));
	}

	private static void loggedInSu(User u) {
		System.out.println("Please select an option:");
		System.out.println("1. View users");
		System.out.println("2. Delete user");
		System.out.println("3. Update user");
		char input = sc.nextLine().charAt(0);
		switch (input) {
			case '1':
				viewUsers(u);
				break;
			case '2':
				deleteUser(u);
				break;
			case '3':
				updateUser(u);
				break;
			default:
		}
	}

	private static void updateUser(User u) {
		System.out.println("Please enter the user ID to update");
		int user = Integer.parseInt(sc.nextLine());
		System.out.println("Please enter the new username:");
		String username = sc.nextLine();
		System.out.println("Please enter the new password:");
		String pass = sc.nextLine();
		
		System.out.println("Please enter the new first name:");
		String fname = sc.nextLine();
		System.out.println("Please enter the new last name:");
		String lname = sc.nextLine();
		
		if (ss.updateUser(new User (user, username, pass, fname, lname, 0)) != null) {
			System.out.println("User has been updated");

		}
		else {
			System.out.println("User wasn't updated.");
		}
		loggedInSu(u);
		
	}

	private static void deleteUser(User u) {
		System.out.println("Please enter the user ID to delete:");
		int user = Integer.parseInt(sc.nextLine());
		
		// Check if user was actually able to be deleted
		if (ss.deleteUser(user)) {
			System.out.println("User deleted.");
		}
		else {
			System.out.println("User could not be deleted.");
		}
		loggedInSu(u);
	}

	private static void viewUsers(User u) {
		ArrayList<User> users = (ArrayList<User>) ss.getSortedUsers();
		System.out.println("Here are the user accounts:");
		
		for (User user : users) {
			System.out.println(user.getUsername() + " (User ID: " + user.getId() + ")");
		}
		loggedInSu(u);

	}



}
