package mtrs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AdminLogin {

	Scanner sc = new Scanner(System.in);
	MovieDAO mv = new MovieDAO();
	Admin_Process ap = new Admin_Process();
	
	public void admin_login() throws Exception{
		Connection con = DBConnection.getConnection();
		
		System.out.println("1. New Admin");
		System.out.println("2. Existing Admin");
		System.out.println("3. Exit");
		System.out.println("Enter Your Choice: ");
		int choice = sc.nextInt();
		if(choice==1) {
			sc.nextLine();
			System.out.println("Enter Your Name: ");
			String name = sc.nextLine();
			System.out.println("Create New Password: ");
			String password = sc.nextLine();
			String query = "insert into admin_table (admin_name, admin_password)values(?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, password);
			pst.executeUpdate();
			System.out.println("Your Account Created Successfully!");
			
			
		}
		else if(choice==2) {
			boolean found = false;
			String foundName, foundPassword;
			sc.nextLine();
			System.out.println("Enter your Name: ");
			String name = sc.nextLine();
			System.out.println("Enter Your Password: ");
			String password = sc.nextLine();
			String query ="select admin_name, admin_password from admin_table where admin_name =? and admin_password =?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, password);
//			pst.executeUpdate();
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				found = true;
				foundName = rs.getString("admin_name");
				foundPassword = rs.getString("admin_password");
				if(foundName.equals(name) && foundPassword.equals(password)) {
					System.out.println("-----------------------------------\n");
					System.out.println("Welcome Back " + foundName);
					System.out.println("-----------------------------------\n");
					
					ap.adminProcess();
					
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
		else if(choice==3) {
			System.out.println("Thank You!");
		}
		else {
			System.out.println("Invalid Option! Try Again");
		}
	}
	
}
