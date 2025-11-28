import java.sql.*;

public class crudtable {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database successfully!");
            
            stmt = conn.createStatement();
            
            String dropTable = "DROP TABLE IF EXISTS students";
            stmt.executeUpdate(dropTable);
            System.out.println("Dropped existing students table if any");
            
            String createTable = "CREATE TABLE students (" +
                                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                                "name VARCHAR(100) NOT NULL, " +
                                "address VARCHAR(200), " +
                                "email VARCHAR(100))";
            stmt.executeUpdate(createTable);
            System.out.println("Table 'students' created successfully!");
            
            String insert1 = "INSERT INTO students (name, address, email) VALUES " +
                           "('Sujal', 'Kathmandu', 'sujal@email.com')";
            stmt.executeUpdate(insert1);
            
            String insert2 = "INSERT INTO students (name, address, email) VALUES " +
                           "('Bivash', 'Lalitpur', 'bivash@email.com')";
            stmt.executeUpdate(insert2);
            
            String insert3 = "INSERT INTO students (name, address, email) VALUES " +
                           "('Divesh', 'Bhaktapur', 'divesh@email.com')";
            stmt.executeUpdate(insert3);
            
            String insert4 = "INSERT INTO students (name, address, email) VALUES " +
                           "('Aalok', 'Pokhara', 'aalok@email.com')";
            stmt.executeUpdate(insert4);
            
            System.out.println("4 student records inserted successfully!");
            
            String selectAll = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(selectAll);
            
            System.out.println("\n--- Students Table ---");
            System.out.println("ID\tName\tAddress\t\tEmail");
            System.out.println("-----------------------------------------------");
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                
                System.out.println(id + "\t" + name + "\t" + address + "\t\t" + email);
            }
            
            rs.close();
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error occurred!");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("\nDatabase connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
