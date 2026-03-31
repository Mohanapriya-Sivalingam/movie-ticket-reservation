package mtrs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerDAO {
	Scanner sc = new Scanner(System.in);
	int show_id;
	int movie_id;
	String show_time;
	int price;
	int available;
	int booked;
	int seat_id;
	boolean showFound = false;
	boolean bookingFound = false;
	
	public void CustomerAccess() throws Exception
	{
		Connection con = DBConnection.getConnection();	
		MovieDAO mv = new MovieDAO();
		int payment;
				
		System.out.println("Movie Ticket Reservation System!");
		System.out.println("--------------------------------");
		System.out.println("");
		System.out.println("1. View Movies");
		System.out.println("2. Book Tickets");
		System.out.println("3. Cancel Tickets");
		System.out.println("4. View Your Booking Details");
		System.out.println("5. Exit");
		System.out.println("Enter Your Choice - ");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			mv.showMovie();
			System.out.println("Select Movie Id: ");
			int id = sc.nextInt();
			String query= "select * from show_table where movie_id=?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				showFound= true;
				System.out.println("Available Shows: ");
				System.out.println("----------------");
				System.out.println(" ");
				show_id=rs.getInt("show_id");
				movie_id =rs.getInt("movie_id");
				show_time =rs.getString("show_time");
				price = rs.getInt("price");
				System.out.println("Show_id: " + show_id);
				System.out.println("Movie_id: " + movie_id);
				System.out.println("Show Time : "+show_time);
				System.out.println("Ticket Price : "+ price);
				System.out.println("------------------------");
				
			}	
			System.out.println("Do You Want to Book Tickets(yes/no)?");
			String option = sc.nextLine();
			if(option.equalsIgnoreCase("yes")) {
				bookTicket();
			}
			else System.out.println("ThankYou!!!");
			if(!showFound) {
				System.out.println("-----------------------------------------------");
				System.out.println("NO shows available for the Movie!!");
				System.out.println(" ");
				System.out.println("Sorry for Inconvenience! Try Another Movies!");
				System.out.println("-----------------------------------------------");
				break;
			}
			break;
			
		case 2:
			System.out.println("Movie List");
			System.out.println("----------");
			mv.showMovie();
			System.out.println("Select Movie Id: ");
			int id1 = sc.nextInt();
			String query1= "select * from show_table where movie_id=?";
			PreparedStatement pst1 = con.prepareStatement(query1);
			pst1.setInt(1, id1);
			ResultSet rs1= pst1.executeQuery();
			System.out.println("Available Shows: ");
			System.out.println("----------------");
			System.out.println(" ");
			while(rs1.next()) {
				showFound = true;
				show_id=rs1.getInt("show_id");
				movie_id =rs1.getInt("movie_id");
				show_time =rs1.getString("show_time");
				price = rs1.getInt("price");
				System.out.println("Show_id: " + show_id);
				System.out.println("Movie_id: " + movie_id);
				System.out.println("Show Time : "+show_time);
				System.out.println("Ticket Price : "+ price);
				System.out.println("------------------------");
			}
			if(!showFound) {
				System.out.println("NO shows available for the Movie!!");
				System.out.println("Sorry for Inconvenience! Try Another Movies!");
				break;
			}
			System.out.println("Enter Show Id to Book Tickets: ");
			int choice1 = sc.nextInt();
			String query2 ="select * from seat_table where show_id=?;";
			PreparedStatement pst2 = con.prepareStatement(query2);
			pst2.setInt(1, choice1);
			ResultSet rs2= pst2.executeQuery();
			System.out.println("Show Details: ");
			System.out.println("-------------");
			while(rs2.next()) {
				show_id=rs2.getInt("show_id");
				available=rs2.getInt("available");
				booked =rs2.getInt("booked");
				seat_id = rs2.getInt("seat_id");
				System.out.println("Show_id : " + show_id);
				System.out.println("Seat_id : " + seat_id);
				System.out.println("Available : "+ available);
				System.out.println("Booked : "+ booked);
				System.out.println("------------------------");
				
			}
			sc.nextLine();
			if(available == 0) {
				System.out.println("Show Housefull !!!!");
			}
			else {
				System.out.println("Enter Your Name: ");
				String name = sc.nextLine();
				System.out.println("Enter Total Number Of Tickets: ");
				int totalTickets = sc.nextInt();
				
				String query3 ="select available, booked from seat_table where show_id=?";
				PreparedStatement pst3 = con.prepareStatement(query3);
				pst3.setInt(1, choice1);
				ResultSet rs3 = pst3.executeQuery();

				if (rs3.next()) {
				    available = rs3.getInt("available");
				    booked = rs3.getInt("booked");
				} else {
				    System.out.println("No seat data found!");
				    return;
				}
//				available = rs3.getInt("available");
//				booked = rs3.getInt("booked");
				String query4 ="select price from show_table where show_id=?";
				PreparedStatement pst4 = con.prepareStatement(query4);
				pst4.setInt(1,choice1);
				ResultSet rs4 = pst4.executeQuery();
				if (rs4.next()) {
				    price = rs4.getInt("price");
				} 
				else {
				    System.out.println("Price not found!");
				    return;
				}
//				price = rs4.getInt("price");
				if(available==0) {
					System.out.print("Show House full!");
				}
				else if(totalTickets>available) 
					System.out.println("You Entered More than Available Tickets!!!!");
				else if(totalTickets==available) {
					System.out.println("Total Tickets Booked: " + available);
					payment = available*price;
					System.out.println("Total Amount to Pay: " + payment);
				
					String query5 ="insert into booking_table (show_id, seat_id, cus_name, total_amt, total_seat)values (?,?,?,?,?);";
					PreparedStatement pst5 = con.prepareStatement(query5);
					pst5.setInt(1, choice1);
					pst5.setInt(2, seat_id);
					pst5.setString(3, name);
					pst5.setInt(4, payment);
					pst5.setInt(5, totalTickets);
					pst5.executeUpdate();
										
					// Update seat_table
				    int newAvailable = available - totalTickets;
				    int newBooked = booked + totalTickets;

				    String updateQuery = "update seat_table set available=?, booked=? where show_id=?";
				    PreparedStatement pst6 = con.prepareStatement(updateQuery);

				    pst6.setInt(1, newAvailable);
				    pst6.setInt(2, newBooked);
				    pst6.setInt(3, choice1);

				    pst6.executeUpdate();

				    System.out.println("Tickets Booked Successfully!");
				}
				else if(totalTickets<available) {
					System.out.println("Total Tickets Booked: " + totalTickets);
					payment = totalTickets * price;
					System.out.println("Total Amount to Pay: " + payment);
				
					String query5 ="insert into booking_table (show_id, seat_id, cus_name, total_amt, total_seat)values (?,?,?,?,?);";
//					PreparedStatement pst5 = con.prepareStatement(query5);
					PreparedStatement pst5 = con.prepareStatement(query5, Statement.RETURN_GENERATED_KEYS);
					pst5.setInt(1, choice1);
					pst5.setInt(2, seat_id);
					pst5.setString(3, name);
					pst5.setInt(4, payment);
					pst5.setInt(5, totalTickets);
					pst5.executeUpdate();
										
					// Update seat_table
				    int newAvailable = available - totalTickets;
				    int newBooked = booked + totalTickets;

				    String updateQuery = "update seat_table set available=?, booked=? where show_id=?";
				    PreparedStatement pst6 = con.prepareStatement(updateQuery);

				    pst6.setInt(1, newAvailable);
				    pst6.setInt(2, newBooked);
				    pst6.setInt(3, choice1);

				    pst6.executeUpdate();
				    
				    ResultSet rsKey = pst5.getGeneratedKeys();

				    if (rsKey.next()) {
				        int bookingId = rsKey.getInt(1);

				        System.out.println("Tickets Booked Successfully!");
				        System.out.println("Your BOOKING ID: " + bookingId);
				    }
				}
				else
					System.out.println("Invalid Ticket Count!");
			}
			
			break;
		case 3:
			int booking_id=0, show_id1=0, seat_id=0, amt=0, total_seat=0;
			String name;
			System.out.println("Cancel Ticekts");
			System.out.println("--------------\n");
			System.out.println("Enter Your Booking Id: ");
			int book_id = sc.nextInt();
			String query6 = "select * from booking_table where booking_id = ?";
			PreparedStatement pst7 = con.prepareStatement(query6);
			pst7.setInt(1, book_id);
//			pst7.executeUpdate();
			ResultSet rs7 = pst7.executeQuery();
			while(rs7.next()) {
				bookingFound = true;
				 booking_id = rs7.getInt("booking_id");
				show_id1 = rs7.getInt("show_id");
				seat_id = rs7.getInt("seat_id");
				name = rs7.getString("cus_name");
				amt = rs7.getInt("total_amt");
				total_seat = rs7.getInt("total_seat");
				System.out.println("Your Booking Details");
				System.out.println("--------------------\n");
				System.out.println("Booking Id :" + booking_id);
				System.out.println("Show Id : " + show_id1);
				System.out.println("Seat Id : " + seat_id);
				System.out.println("Name : " + name);
				System.out.println("Total Seat : "+total_seat );
				System.out.println("Total Ticket Amount: " + amt);
				System.out.println("-----------------------------------");
				sc.nextLine();
				System.out.println("Confirm Cancel Tickets (yes/no) :");
				String s = sc.nextLine();
				if(s.equalsIgnoreCase("yes")) {
					System.out.println("WAIT.....");
					
					String query7 ="select available, booked from seat_table where show_id=?";
					PreparedStatement pst8 = con.prepareStatement(query7);
					pst8.setInt(1, show_id1);
					ResultSet rs8 = pst8.executeQuery();
					rs8.next();
					int available = rs8.getInt("available");
					int booked = rs8.getInt("booked");
					int newAvailable = available+total_seat;
					int newBooked = booked- total_seat;
					String query8 ="update seat_table set available=?, booked =? where show_id=?";
					PreparedStatement pst9 = con.prepareStatement(query8);
					pst9.setInt(1, newAvailable);
					pst9.setInt(2, newBooked);
					pst9.setInt(3, show_id1);
					pst9.executeUpdate();
					String query9 ="delete from booking_table where booking_id =?";
					PreparedStatement pst10 = con.prepareStatement(query9);
					pst10.setInt(1, booking_id);
					pst10.executeUpdate();
					System.out.println("Ticket Cancel Successful!");
					System.out.println("----------------------------");
				}
				else if(s.equalsIgnoreCase("no")){
					System.out.println("Thankyou! Enjoy Your Movie Time!!!!!");
				}
				else
					System.out.println("Invalid Option!");
			}
			if(!bookingFound) {
				System.out.println("No Booking Id Found!");
				System.out.println("Try Again");
			}
			break;
		case 4:
			System.out.println("Enter Your Booking Id: ");
			int book_id1 = sc.nextInt();
			String query7 = "select * from booking_table where booking_id = ?";
			PreparedStatement pst8 = con.prepareStatement(query7);
			pst8.setInt(1, book_id1);
//			pst7.executeUpdate();
			ResultSet rs8 = pst8.executeQuery();
			while(rs8.next()) {
				bookingFound = true;
				 booking_id = rs8.getInt("booking_id");
				show_id1 = rs8.getInt("show_id");
				seat_id = rs8.getInt("seat_id");
				name = rs8.getString("cus_name");
				amt = rs8.getInt("total_amt");
				total_seat = rs8.getInt("total_seat");
				System.out.println("Your Booking Details");
				System.out.println("--------------------\n");
				System.out.println("\tBooking Id :" + booking_id);
				System.out.println("\tShow Id : " + show_id1);
				System.out.println("\tSeat Id : " + seat_id);
				System.out.println("\tName : " + name);
				System.out.println("\tTotal Seat : "+total_seat );
				System.out.println("\tTotal Ticket Amount: " + amt);
				System.out.println("----------------------------------------------");
			}
			if(!bookingFound) {
				System.out.println("----------------------------------------------------");
				System.out.println("No Booking Id Found!");
				System.out.println("Try Again");
				System.out.println("----------------------------------------------------");
			}
			break;
		case 5:
			System.out.println("----------------------------------------------------");
			System.out.println("Thankyou for using Movie Ticket Reservation App!!!!");
			System.out.println("----------------------------------------------------");
			break;
		default:
			System.out.println("-----------------------------------------------");
			System.out.println("Incorrect Option...");
			System.out.println("Try Again!");
			System.out.println("-----------------------------------------------");
		}
	}
	
	public void bookTicket() throws Exception{
		int payment=0;
		Connection con = DBConnection.getConnection();
		System.out.println("Enter Show Id to Book Tickets: ");
		int choice1 = sc.nextInt();
		String query2 ="select * from seat_table where show_id=?;";
		PreparedStatement pst2 = con.prepareStatement(query2);
		pst2.setInt(1, choice1);
		ResultSet rs2= pst2.executeQuery();
		System.out.println("Show Details: ");
		System.out.println("-------------");
		while(rs2.next()) {
			show_id=rs2.getInt("show_id");
			available=rs2.getInt("available");
			booked =rs2.getInt("booked");
			seat_id = rs2.getInt("seat_id");
			System.out.println("Show_id : " + show_id);
			System.out.println("Seat_id : " + seat_id);
			System.out.println("Available : "+ available);
			System.out.println("Booked : "+ booked);
			System.out.println("------------------------");
			
		}
		sc.nextLine();
		if(available == 0) {
			System.out.println("Show Housefull !!!!");
		}
		else {
			System.out.println("Enter Your Name: ");
			String name = sc.nextLine();
			System.out.println("Enter Total Number Of Tickets: ");
			int totalTickets = sc.nextInt();
			
			String query3 ="select available, booked from seat_table where show_id=?";
			PreparedStatement pst3 = con.prepareStatement(query3);
			pst3.setInt(1, choice1);
			ResultSet rs3 = pst3.executeQuery();

			if (rs3.next()) {
			    available = rs3.getInt("available");
			    booked = rs3.getInt("booked");
			} else {
			    System.out.println("No seat data found!");
			    return;
			}
//			available = rs3.getInt("available");
//			booked = rs3.getInt("booked");
			String query4 ="select price from show_table where show_id=?";
			PreparedStatement pst4 = con.prepareStatement(query4);
			pst4.setInt(1,choice1);
			ResultSet rs4 = pst4.executeQuery();
			if (rs4.next()) {
			    price = rs4.getInt("price");
			} 
			else {
			    System.out.println("Price not found!");
			    return;
			}
//			price = rs4.getInt("price");
			if(available==0) {
				System.out.print("Show House full!");
			}
			else if(totalTickets>available) 
				System.out.println("You Entered More than Available Tickets!!!!");
			else if(totalTickets==available) {
				System.out.println("Total Tickets Booked: " + available);
				payment = available*price;
				System.out.println("Total Amount to Pay: " + payment);
			
				String query5 ="insert into booking_table (show_id, seat_id, cus_name, total_amt, total_seat)values (?,?,?,?,?);";
				PreparedStatement pst5 = con.prepareStatement(query5);
				pst5.setInt(1, choice1);
				pst5.setInt(2, seat_id);
				pst5.setString(3, name);
				pst5.setInt(4, payment);
				pst5.setInt(5, totalTickets);
				pst5.executeUpdate();
									
				// Update seat_table
			    int newAvailable = available - totalTickets;
			    int newBooked = booked + totalTickets;

			    String updateQuery = "update seat_table set available=?, booked=? where show_id=?";
			    PreparedStatement pst6 = con.prepareStatement(updateQuery);

			    pst6.setInt(1, newAvailable);
			    pst6.setInt(2, newBooked);
			    pst6.setInt(3, choice1);

			    pst6.executeUpdate();

			    System.out.println("Tickets Booked Successfully!");
			}
			else if(totalTickets<available) {
				System.out.println("Total Tickets Booked: " + totalTickets);
				payment = totalTickets * price;
				System.out.println("Total Amount to Pay: " + payment);
			
				String query5 ="insert into booking_table (show_id, seat_id, cus_name, total_amt, total_seat)values (?,?,?,?,?);";
//				PreparedStatement pst5 = con.prepareStatement(query5);
				PreparedStatement pst5 = con.prepareStatement(query5, Statement.RETURN_GENERATED_KEYS);
				pst5.setInt(1, choice1);
				pst5.setInt(2, seat_id);
				pst5.setString(3, name);
				pst5.setInt(4, payment);
				pst5.setInt(5, totalTickets);
				pst5.executeUpdate();
									
				// Update seat_table
			    int newAvailable = available - totalTickets;
			    int newBooked = booked + totalTickets;

			    String updateQuery = "update seat_table set available=?, booked=? where show_id=?";
			    PreparedStatement pst6 = con.prepareStatement(updateQuery);

			    pst6.setInt(1, newAvailable);
			    pst6.setInt(2, newBooked);
			    pst6.setInt(3, choice1);

			    pst6.executeUpdate();
			    
			    ResultSet rsKey = pst5.getGeneratedKeys();

			    if (rsKey.next()) {
			        int bookingId = rsKey.getInt(1);

			        System.out.println("Tickets Booked Successfully!");
			        System.out.println("Your BOOKING ID: " + bookingId);
			    }
			}
			else
				System.out.println("Invalid Ticket Count!");
	}
	}
}
