package mtrs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class NewAdmin {

	Scanner sc = new Scanner(System.in);
	
	public void createNewAdmin() throws Exception{
		Connection con = DBConnection.getConnection();
		
		System.out.println("Enter Admin Name: ");
		String name = sc.nextLine();
		System.out.println("Create New Password: ");
		String password = sc.nextLine();
		String query = "insert into admin_table (admin_name, admin_password)values(?,?)";
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, name);
		pst.setString(2, password);
		pst.executeUpdate();
		System.out.println("---------------------------------------");
		System.out.println("New Admin Account Created Successfully!");
		System.out.println("---------------------------------------");
	}
}
