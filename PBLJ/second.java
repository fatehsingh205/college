import java.sql.*;
import java.util.*;

public class ProductCRUDApp {

    // Database configuration
    private static final String URL = "jdbc:mysql://localhost:3306/store_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password_here";

    // Load MySQL JDBC Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver Loaded Successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Failed to load driver: " + e.getMessage());
        }
    }

    // Get database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ----------- CREATE -----------
    private static void addProduct(Scanner sc) {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, qty);
            ps.executeUpdate();
            System.out.println("✅ Product added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding product: " + e.getMessage());
        }
    }

    // ----------- READ -----------
    private static void viewProducts() {
        String sql = "SELECT * FROM Product";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n------ Product List ------");
            System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Qty");
            System.out.println("--------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-10.2f %-10d%n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching products: " + e.getMessage());
        }
    }

    // ----------- UPDATE (With Transaction Handling) -----------
    private static void updateProduct(Scanner sc) {
        System.out.print("Enter Product ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter new Quantity: ");
        int qty = sc.nextInt();

        String sql = "UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // start transaction

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit(); // commit transaction
                System.out.println("✅ Product updated successfully!");
            } else {
                conn.rollback(); // rollback transaction
                System.out.println("⚠️ Product not found. Transaction rolled back.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error updating product: " + e.getMessage());
        }
    }

    // ----------- DELETE (With Transaction Handling) -----------
    private static void deleteProduct(Scanner sc) {
        System.out.print("Enter Product ID to Delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM Product WHERE ProductID=?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // begin transaction

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product deleted successfully!");
            } else {
                conn.rollback();
                System.out.println("⚠️ No product found. Transaction rolled back.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error deleting product: " + e.getMessage());
        }
    }

    // ----------- MAIN MENU -----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== Product Management System ======");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: addProduct(sc); break;
                case 2: viewProducts(); break;
                case 3: updateProduct(sc); break;
                case 4: deleteProduct(sc); break;
                case 5:
                    System.out.println("👋 Exiting...");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("⚠️ Invalid choice! Try again.");
            }
        }
    }
}
