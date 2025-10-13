import java.util.*;

public class SumOfIntegers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Create a list to store Integer objects (autoboxing will happen)
        List<Integer> numbers = new ArrayList<>();

        System.out.println("Enter integers (type 'done' to finish):");

        // Step 2: Read inputs as Strings and parse to Integer
        while (true) {
            String input = sc.nextLine();

            // Stop when user types "done"
            if (input.equalsIgnoreCase("done")) break;

            try {
                // String → int → Integer (autoboxing)
                int value = Integer.parseInt(input);  
                numbers.add(value);  // Autoboxing happens here
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number! Please enter a valid integer or 'done'.");
            }
        }

        // Step 3: Calculate sum (Unboxing happens automatically)
        int sum = 0;
        for (Integer num : numbers) {  // num is unboxed automatically to int
            sum += num;
        }

        // Step 4: Display result
        System.out.println("\nNumbers entered: " + numbers);
        System.out.println("✅ Total Sum = " + sum);

        sc.close();
    }
}
