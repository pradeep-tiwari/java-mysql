package reference;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ReadOperationsDemo.java
 *
 * @author pradeep
 */
public class ReadOperationsDemo {

    /**
     * Database configurations.
     *
     * We can keep these in a separate config file.
     */
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "12345";
    public static final String HOST_URL = "jdbc:mysql://localhost/tshop";

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        try (
            Connection conn = DriverManager.getConnection(HOST_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM categories");
        ) {
            /**
             * Display all categories.
             */
            displayCategories(rs);

            /**
             *
             */
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Loops and displays all categories.
     *
     * @param rs The ResultSet object.
     * @throws SQLException
     */
    public static void displayCategories(ResultSet rs) throws SQLException {
        StringBuilder builder = new StringBuilder();

        while (rs.next()) {
            builder.append("Category ID: " + rs.getInt("categoryID") + ", ");
            builder.append("Category Name: " + rs.getString("categoryName"));
            builder.append("\n");
        }
        
        rs.last();
        builder.append("Total Number of Records: " + rs.getRow());

        System.out.println(builder.toString());
    }
}
