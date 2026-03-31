package mtrs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Customer_login {
	Scanner sc = new Scanner(System.in);
	
	
	public void customerLogin() throws Exception{
		Connection con = DBConnection.getConnection();
		CustomerDAO cus = new CustomerDAO();
		
		
		System.out.println("1. New User");
		System.out.println("2. Existing User");
		System.out.println("3. Exit");
		
		System.out.println("Enter Your Option: ");
		int choice = sc.nextInt();
		
		if(choice ==1 ) {
			sc.nextLine();
			System.out.println("Welcome to Movie Ticket Reservation System");
			System.out.println("-------------------------------------------\n");
			System.out.println("Enter Your Name: ");
			String name = sc.nextLine();
			System.out.println("Create New Password: ");
			String password = sc.nextLine();
			String query = "insert into user_table(user_name, user_password) values(?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, password);
			pst.executeUpdate();
			System.out.println("Your Account Created Successfully!");
			System.out.println("---------------------------------------------------------");
			System.out.println("Welcome to Movie Ticket Reservation System " + name);
			System.out.println("---------------------------------------------------------\n");
			cus.CustomerAccess();
			}
		else if(choice == 2) {
			boolean found = false;
			String foundName, foundPassword;
			sc.nextLine();
			System.out.println("Enter your Name: ");
			String name = sc.nextLine();
			System.out.println("Enter Your Password: ");
			String password = sc.nextLine();
			String query ="select user_name, user_password from user_table where user_name =? and user_password =?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, password);
//			pst.executeUpdate();
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				found = true;
				foundName = rs.getString("user_name");
				foundPassword = rs.getString("user_password");
				if(foundName.equals(name) && foundPassword.equals(password)) {
					System.out.println("-----------------------------------\n");
					System.out.println("Welcome Back " + foundName+"!");
					System.out.println("-----------------------------------\n");
					cus.CustomerAccess();
					
				}
				else if(foundName.equals(name) || foundPassword.equals(password)) {
					System.out.println("User Name and Password is Not Matched");
					System.out.println("Try Again!");
					break;
				}
				else {
					System.out.println("Invalid User Name & Password!!!!");
				}
			}
			if(!found) {
				System.out.println("NO user Found!!! ");
				System.out.println("Try Again!");
			}
			
		}
		else if (choice == 3) {
			System.out.println("Thank You!");
		}
		else {
			System.out.println("Invalid Option! Try Again!");
		}
	}
}
