package mtrs;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3306/movieticket";
        String user = "root";
        String password = "root123";

        return DriverManager.getConnection(url, user, password);
    }
	}
