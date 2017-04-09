package reference;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
     * We can keep these in a separate configuration file.
     */
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "12345";
    public static final String HOST_URL = "jdbc:mysql://localhost/tshop";

    /**
     * Application entry point.
     */
    public static void main(String[] args) throws SQLException {
        String staticSQL = "SELECT * FROM categories";
        String parameterizedSQL = "SELECT * FROM categories WHERE categoryId = ?";
        String callableSQL = "{call GetCategoryInfo(?)}";
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        
        try (
            Connection conn = DriverManager.getConnection(HOST_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            PreparedStatement pstmt = conn.prepareStatement(parameterizedSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CallableStatement cstmt = conn.prepareCall(callableSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ) {
            
            rs1 = stmt.executeQuery(staticSQL);
            
            pstmt.setInt(1, 2);
            rs2 = pstmt.executeQuery(parameterizedSQL);
            
            cstmt.setInt(1,3);
            rs3 = cstmt.executeQuery(callableSQL);
            
            /**
             * Display all categories.
             */
            displayCategories(rs1);

            /**
             * Display category with specified id.
             */
            displayCategory(rs2);
            displayCategory(rs3);
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if(rs1 != null) rs1.close();
            if(rs2 != null) rs2.close();
            if(rs2 != null) rs2.close();
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
    
    /**
     * Displays a category with the given id.
     * 
     * @param rs The ResultSet object.
     */
    public static void displayCategory(ResultSet rs) throws SQLException {
        rs.next();
        
        StringBuilder builder = new StringBuilder();
        
        builder.append("\n====================================\n\n");
        builder.append("Category ID: " + rs.getObject("categoryId", String.class));
        builder.append("\n");
        builder.append("Category Name: " + rs.getObject("categoryName", String.class));
        
        System.out.println(builder.toString());
    }
}
