package mtrs;
import java.sql.*;
import java.util.Scanner;

public class MovieDAO {
//	Add Movies Details
	Scanner sc = new Scanner(System.in);
	public void addMovies()throws Exception {
		
		String query = "insert into movie_table(movie_name, movie_language) values(?,?)";

		
		Connection con = DBConnection.getConnection();
		String name, lang;
		PreparedStatement pst = con.prepareStatement(query);
	
			System.out.println("Movie Name: ");
			name = sc.nextLine();
			System.out.println("Language: ");
			lang = sc.nextLine();
			pst.setString(1, name);
			pst.setString(2, lang);
			int rows = pst.executeUpdate();
			System.out.println("NO.of MOvies Added: " + rows);
			pst.close();
			con.close();
	}

	public void showMovie()throws Exception{
		Connection con= DBConnection.getConnection();
		String query = "select * from movie_table;";
		Statement st= con.createStatement();
		ResultSet rs=st.executeQuery(query);
		while(rs.next()) {
			int id=rs.getInt("movie_id");
			String name = rs.getString("movie_name");
			String lang = rs.getString("movie_language");
			System.out.println("Id: " + id);
			System.out.println("Movie Name: " + name + " - "+ lang);
			}
		rs.close();
		st.close();
		con.close();
		
	}
	
	public void addShows(int movie_id, String show_time, int price) throws Exception{
		Connection con= DBConnection.getConnection();
		String query = "insert into show_table(movie_id, show_time, price) values(?,?,?);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,movie_id);
		ps.setString(2, show_time);
		ps.setInt(3,price);
		int rows = ps.executeUpdate();
		System.out.println("NO.of Shows Added : " + rows);
		
	}

	public void addSeat(int total, int available, int booked, int show_id) throws Exception{
		
		Connection con= DBConnection.getConnection();
		String query= "insert into seat_table (total, available, booked, show_id)values(?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,total);
		ps.setInt(2, available);
		ps.setInt(3,booked);
		ps.setInt(4, show_id);
		int rows = ps.executeUpdate();
		System.out.println("NO.of Shows Added : " + rows);
	}
	
}
