package mtrs;

import java.util.Scanner;

public class Admin_Process {
	Scanner sc = new Scanner(System.in);
	MovieDAO mv = new MovieDAO();
	
	public void adminProcess() throws Exception{
		
		System.out.println("1. Add Movies");
		System.out.println("2. Add Shows");
		System.out.println("3. Add Seats");
		
		System.out.println("4. Exit");
		int num1 = sc.nextInt();
		if(num1==1) 
			mv.addMovies();
		else if(num1==2) {
			System.out.println("Enter Movie Id: ");
			int mid = sc.nextInt();
			sc.nextLine();
			System.out.println("Show Time: ");
			String stime = sc.nextLine();
			System.out.println("Ticket Amount: ");
			int amt = sc.nextInt();
			mv.addShows(mid, stime, amt);
		}	
		else if(num1==3) {
			System.out.println("Enter Total Seat: ");
			int total = sc.nextInt();
			System.out.println("Available Seat :");
			int available = sc.nextInt();
			int booked =0;
			System.out.println("Enter Show Id: ");
			int show_id = sc.nextInt();
			mv.addSeat(total,available, booked, show_id);
		}
			
		else
			System.out.println("Thank You for Using Movie Ticket Reservation App!  ");
	}

}
