import java.io.*;
import java.util.*;

// Employee class must be Serializable to be saved as an object
class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String designation;
    private double salary;

    public Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-20s %-15s %-10.2f", id, name, designation, salary);
    }
}

public class EmployeeManagementSystem {

    private static final String FILE_NAME = "employees.dat";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n========== Employee Management System ==========");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> displayAllEmployees();
                case 3 -> {
                    System.out.println("👋 Exiting Application. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("❌ Invalid Choice! Please try again.");
            }
        }
    }

    // Method to add employee details and save to file
    private static void addEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Designation: ");
            String designation = sc.nextLine();
            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();

            Employee emp = new Employee(id, name, designation, salary);

            // Append the employee to the file (without overwriting)
            List<Employee> employees = readEmployees();
            employees.add(emp);
            writeEmployees(employees);

            System.out.println("✅ Employee Added Successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error while adding employee: " + e.getMessage());
        }
    }

    // Method to display all employees from file
    private static void displayAllEmployees() {
        List<Employee> employees = readEmployees();
        if (employees.isEmpty()) {
            System.out.println("⚠️ No employee records found!");
            return;
        }

        System.out.println("\n=========== Employee Records ===========");
        System.out.printf("%-10s %-20s %-15s %-10s\n", "EmpID", "Name", "Designation", "Salary");
        System.out.println("-------------------------------------------------------------");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    // Helper: Read employees from file
    @SuppressWarnings("unchecked")
    private static List<Employee> readEmployees() {
        List<Employee> list = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            list = (List<Employee>) in.readObject();
        } catch (FileNotFoundException e) {
            // File may not exist on first run — that's okay
        } catch (EOFException e) {
            // End of file reached
        } catch (Exception e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
        return list;
    }

    // Helper: Write employees to file
    private static void writeEmployees(List<Employee> employees) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(employees);
        } catch (Exception e) {
            System.out.println("❌ Error writing to file: " + e.getMessage());
        }
    }
}
