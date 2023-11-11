import java.util.Scanner;

public class Main {
    private static final GradeService gradeService = new GradeService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = getChoice(scanner);

            switch (choice) {
                case 1:
                    gradeService.displayGrades();
                    break;
                case 2:
                    gradeService.addGrade();
                    break;
                case 3:
                    gradeService.editGrade();
                    break;
                case 4:
                    gradeService.deleteGrade();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n1. Display Grades");
        System.out.println("2. Add Grade");
        System.out.println("3. Edit Grade");
        System.out.println("4. Delete Grade");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static int getChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
