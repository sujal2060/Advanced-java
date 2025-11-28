import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC Connection Utility Class
 * Provides database connection management for localhost MySQL database
 */
public class JDBCConnection {
    
    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Singleton connection instance
    private static Connection connection = null;
    
    /**
     * Private constructor to prevent instantiation
     */
    private JDBCConnection() {
        // Private constructor
    }
    
    /**
     * Get database connection (Singleton pattern)
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load MySQL JDBC Driver
                Class.forName(DB_DRIVER);
                
                // Establish connection
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Database connection established successfully!");
                
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found!");
                e.printStackTrace();
                throw new SQLException("Driver not found", e);
            } catch (SQLException e) {
                System.err.println("Failed to establish database connection!");
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Create a new database connection (non-singleton)
     * @return New Connection object
     * @throws SQLException if connection fails
     */
    public static Connection createNewConnection() throws SQLException {
        try {
            Class.forName(DB_DRIVER);
            Connection newConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("New database connection created successfully!");
            return newConnection;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException("Driver not found", e);
        }
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
    
    /**
     * Test the database connection
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try {
            Connection testConn = getConnection();
            if (testConn != null && !testConn.isClosed()) {
                System.out.println("Connection test successful!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Connection test failed!");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Main method for testing the connection
     */
    public static void main(String[] args) {
        System.out.println("Testing JDBC Connection...");
        
        try {
            // Test connection
            Connection conn = JDBCConnection.getConnection();
            
            if (conn != null) {
                System.out.println("✓ Connection successful!");
                System.out.println("Database: " + conn.getCatalog());
                System.out.println("URL: " + conn.getMetaData().getURL());
                System.out.println("User: " + conn.getMetaData().getUserName());
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Connection failed!");
            e.printStackTrace();
        } finally {
            // Close connection
            JDBCConnection.closeConnection();
        }
    }
}
