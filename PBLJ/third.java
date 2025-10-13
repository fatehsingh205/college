import java.sql.*; // JDBC classes

public class FetchEmployeeData {
    public static void main(String[] args) {
        // Step 1: Database details
        String url = "jdbc:mysql://localhost:3306/company_db";
        String user = "root";
        String password = "your_password_here";  // change to your MySQL password

        try {
            // Step 2: Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ JDBC Driver Loaded Successfully!");

            // Step 3: Establish Connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to Database Successfully!");

            // Step 4: Create SQL Statement
            Statement stmt = conn.createStatement();

            // Step 5: Execute SQL Query
            String sql = "SELECT * FROM Employee";
            ResultSet rs = stmt.executeQuery(sql);

            // Step 6: Display Data
            System.out.println("\n------ Employee Details ------");
            System.out.printf("%-10s %-20s %-10s\n", "EmpID", "Name", "Salary");
            System.out.println("-------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.printf("%-10d %-20s %-10.2f\n", id, name, salary);
            }

            // Step 7: Close all connections
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("\n✅ Connection Closed Successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }
}
