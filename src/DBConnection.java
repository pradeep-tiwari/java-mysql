package reference;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 *
 * @author pradeep
 */
public class DBConnection {
  
    /**
     * Database configurations.
     *
     * We can keep these in a separate config file.
     */
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "12345";
    public static final String HOST_URL = "jdbc:mysql://localhost/tshop";

    /**
     * A sample test for database connection.
     */
    public static void main(String[] args) {
        try (
            Connection conn = DriverManager.getConnection(HOST_URL, USERNAME, PASSWORD);
        ) {
            System.out.println("Connected!");
        } catch(SQLException e) {
            System.err.println(e);
        }
    }
}