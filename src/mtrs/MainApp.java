package mtrs;

//import java.sql.Connection;
import java.util.Scanner;

public class MainApp {

	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);

//		CustomerDAO cus = new CustomerDAO();
//		Admin_Process ap = new Admin_Process();
		AdminLogin al = new AdminLogin();
		Customer_login cl = new Customer_login();


		System.out.println("1. Admin");
		System.out.println("2. Customer");
		System.out.println("3. Exit");
		int num = sc.nextInt();
		if(num==1) {
			al.admin_login();
		
		}
		else if(num==2) {

			cl.customerLogin();
		}
		else if(num==3) {
			
			System.out.println("Thank You for Using Movie Ticket Reservation App!  ");
		}
		else 
			System.out.println("Invalid Option! Try Again!");
		sc.close();
	}

}
