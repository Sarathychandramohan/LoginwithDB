package RegLoginwithDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{

	private static final String URL = "jdbc:mysql://localhost:3306/loginsystem";
	private static final String USER = "root";
	private static final String PASSWORD = "Sarathy@2405";
	

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}