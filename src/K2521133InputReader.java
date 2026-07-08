import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Small wrapper over Scanner that keeps re-asking until the counter staff type
// something valid, so the rest of the code never deals with bad input.
public class K2521133InputReader {

    private final Scanner scanner;

    public K2521133InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(value);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the shared message below
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("Please use the format YYYY-MM-DD, e.g. 2026-08-15.");
            }
        }
    }
}
