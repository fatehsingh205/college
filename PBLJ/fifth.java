import java.util.*;

// Employee class definition
class Employee {
    private String name;
    private int age;
    private double salary;

    // Constructor
    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public double getSalary() {
        return salary;
    }

    // toString() for easy printing
    @Override
    public String toString() {
        return String.format("%-15s %-5d %-10.2f", name, age, salary);
    }
}

public class SortEmployeesUsingLambda {
    public static void main(String[] args) {

        // Step 1: Create a list of employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Amit", 28, 45000));
        employees.add(new Employee("Sneha", 25, 52000));
        employees.add(new Employee("Ravi", 32, 60000));
        employees.add(new Employee("Pooja", 29, 48000));
        employees.add(new Employee("Karan", 23, 40000));

        System.out.println("=== Original List ===");
        printEmployees(employees);

        // Step 2: Sort by Name (Alphabetically)
        employees.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        System.out.println("\n=== Sorted by Name (Alphabetical) ===");
        printEmployees(employees);

        // Step 3: Sort by Age (Ascending)
        employees.sort((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge()));
        System.out.println("\n=== Sorted by Age (Ascending) ===");
        printEmployees(employees);

        // Step 4: Sort by Salary (Descending)
        employees.sort((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        System.out.println("\n=== Sorted by Salary (Descending) ===");
        printEmployees(employees);
    }

    // Helper method to display employees
    private static void printEmployees(List<Employee> employees) {
        System.out.printf("%-15s %-5s %-10s\n", "Name", "Age", "Salary");
        System.out.println("------------------------------------");
        employees.forEach(System.out::println);
    }
}
