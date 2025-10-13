import java.util.*;
import java.util.stream.*;

class Student {
    private String name;
    private double marks;

    // Constructor
    public Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return name + " (" + marks + "%)";
    }
}

public class FilterAndSortStudents {
    public static void main(String[] args) {
        // Step 1: Create list of students
        List<Student> students = Arrays.asList(
                new Student("Amit", 82.5),
                new Student("Neha", 74.3),
                new Student("Ravi", 91.0),
                new Student("Priya", 68.5),
                new Student("Karan", 79.8)
        );

        System.out.println("✅ Students scoring above 75% (sorted by marks):");

        // Step 2: Stream operations
        students.stream()
                .filter(s -> s.getMarks() > 75) // filter students with marks > 75
                .sorted(Comparator.comparingDouble(Student::getMarks).reversed()) // sort by marks descending
                .map(Student::getName) // extract only names
                .forEach(System.out::println); // display each name
    }
}
