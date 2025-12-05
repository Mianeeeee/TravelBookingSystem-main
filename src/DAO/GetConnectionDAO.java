package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnectionDAO {
    private static String url = "jdbc:mysql://localhost:3306/TravelBookingSystemApp";
    private static String username = "root";
    private static String password = "miane7755nW";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return (Connection) DriverManager.getConnection(url, username, password);
    }
}