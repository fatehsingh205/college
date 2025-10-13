import java.sql.*;
import java.util.*;

public class StudentManagementApp {

    // ----------------- MODEL -----------------
    static class Student {
        private int studentId;
        private String name;
        private String department;
        private double marks;

        public Student(int studentId, String name, String department, double marks) {
            this.studentId = studentId;
            this.name = name;
            this.department = department;
            this.marks = marks;
        }

        // Getters
        public int getStudentId() { return studentId; }
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getMarks() { return marks; }

        @Override
        public String toString() {
            return "Student [ID=" + studentId + ", Name=" + name + ", Department=" + department + ", Marks=" + marks + "]";
        }
    }

    // ----------------- CONTROLLER -----------------
    static class StudentDAO {
        private static final String URL = "jdbc:mysql://localhost:3306/college_db";
        private static final String USER = "root";
        private static final String PASSWORD = "your_password_here";

        static {
            try {
                // Load MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver not found: " + e.getMessage());
            }
        }

        private Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        // Create
        public void addStudent(Student s) {
            String sql = "INSERT INTO students (studentId, name, department, marks) VALUES (?, ?, ?, ?)";
            try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, s.getStudentId());
                ps.setString(2, s.getName());
                ps.setString(3, s.getDepartment());
                ps.setDouble(4, s.getMarks());
                ps.executeUpdate();
                System.out.println("✅ Student added successfully!");
            } catch (SQLException e) {
                System.out.println("❌ Error adding student: " + e.getMessage());
            }
        }

        // Read
        public void viewAllStudents() {
            String sql = "SELECT * FROM students";
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                System.out.println("\n----- All Students -----");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("studentId") +
                                       ", Name: " + rs.getString("name") +
                                       ", Dept: " + rs.getString("department") +
                                       ", Marks: " + rs.getDouble("marks"));
                }
            } catch (SQLException e) {
                System.out.println("❌ Error retrieving students: " + e.getMessage());
            }
        }

        // Update
        public void updateStudentMarks(int id, double newMarks) {
            String sql = "UPDATE students SET marks=? WHERE studentId=?";
            try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1, newMarks);
                ps.setInt(2, id);
                int rows = ps.executeUpdate();
                if (rows > 0)
                    System.out.println("✅ Student marks updated successfully!");
                else
                    System.out.println("⚠️ Student not found!");
            } catch (SQLException e) {
                System.out.println("❌ Error updating student: " + e.getMessage());
            }
        }

        // Delete
        public void deleteStudent(int id) {
            String sql = "DELETE FROM students WHERE studentId=?";
            try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0)
                    System.out.println("✅ Student deleted successfully!");
                else
                    System.out.println("⚠️ Student not found!");
            } catch (SQLException e) {
                System.out.println("❌ Error deleting student: " + e.getMessage());
            }
        }
    }

    // ----------------- VIEW (Main Menu) -----------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {
            System.out.println("\n====== Student Management System ======");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Marks");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();
                    dao.addStudent(new Student(id, name, dept, marks));
                    break;

                case 2:
                    dao.viewAllStudents();
                    break;

                case 3:
                    System.out.print("Enter Student ID to update: ");
                    int uid = sc.nextInt();
                    System.out.print("Enter new Marks: ");
                    double newMarks = sc.nextDouble();
                    dao.updateStudentMarks(uid, newMarks);
                    break;

                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    int did = sc.nextInt();
                    dao.deleteStudent(did);
                    break;

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
