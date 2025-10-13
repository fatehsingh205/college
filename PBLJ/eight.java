import java.io.*;

// Step 1: Create a Student class that implements Serializable
class Student implements Serializable {
    // serialVersionUID ensures version compatibility during deserialization
    private static final long serialVersionUID = 1L;

    private int studentID;
    private String name;
    private String grade;

    // Constructor
    public Student(int studentID, String name, String grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    // Display student info
    public void display() {
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Grade: " + grade);
    }
}

public class StudentSerializationDemo {
    public static void main(String[] args) {
        // File where the serialized object will be stored
        String filename = "student.ser";

        // Step 2: Create a Student object
        Student student = new Student(101, "Sumit Kumar", "A+");

        // Step 3: Serialize (write object to file)
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(student); // object -> file
            System.out.println("✅ Student object has been serialized and saved to " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error during serialization: " + e.getMessage());
        }

        // Step 4: Deserialize (read object back from file)
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Student deserializedStudent = (Student) in.readObject(); // file -> object
            System.out.println("\n✅ Student object has been deserialized successfully!");
            System.out.println("🔹 Student Details:");
            deserializedStudent.display();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error during deserialization: " + e.getMessage());
        }
    }
}
