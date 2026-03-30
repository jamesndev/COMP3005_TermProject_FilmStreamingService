import javax.swing.*;
import java.util.Scanner;

public class View extends JFrame {

    Scanner scanner;
    Servicer servicer;

    public View(Scanner scanner, Servicer servicer) {
        this.scanner = scanner;
        this.servicer = servicer;
    }

    private void start() {

    }

    private void showMainMenu() {
        while(true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("   FILM STREAMING SERVICE");
            System.out.println("=================================");
            System.out.println("1) Login");
            System.out.println("2) Register");
            System.out.println("3) Exit");
            System.out.println("---------------------------------");
            System.out.print("Enter choice: ");

            int mainMenuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainMenuChoice) {
                case 1 -> loginMenu();
                case 2 -> registerMenu();
                case 3 -> exit();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void loginMenu() {
        while(true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("          LOGIN MENU");
            System.out.println("=================================");
            System.out.print("Enter your email:");
            String email = scanner.nextLine();
            System.out.print("\n");
            System.out.print("Enter your password:");
            String password = scanner.nextLine();
            System.out.print("\n");
            servicer.setCurrentUser(servicer.login(email, password));
            if (servicer.getCurrentUser() == null) {
                System.out.println("USER DOES NOT EXIST!  - Try Again? (y/n): ");
                String tryAgainChoice = scanner.nextLine();
                switch (tryAgainChoice) {
                    case "y" -> {
                        continue;
                    }
                    case "n" -> {
                        return;
                    }
                    default -> {
                        System.out.println("Invalid input");
                        return;
                    }
                }
            }
            System.out.println("LOGIN SUCCESSFUL!");
            System.out.println("You are logged into " + servicer.getCurrentUser().getUsername());
            System.out.println("---------------------------------");

            watchedOrBrowseMenu();
        }

    }

    private void registerMenu() {
        while(true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("       REGISTRATION MENU");
            System.out.println("=================================");
            System.out.print("Enter your email:");
            String email = scanner.nextLine();
            System.out.print("\n");
            System.out.print("Enter a username:");
            String username = scanner.nextLine();
            System.out.print("\n");
            System.out.print("Enter a password:");
            String password = scanner.nextLine();
            System.out.print("\n");
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            if (servicer.addUser(user)) {
                System.out.println("ACCOUNT CREATED!");
                System.out.println("---------------------------------");
                break;
            }
            System.out.println("REGISTRATION UNSUCCESSFUL");

        }
    }

    private void watchedOrBrowseMenu() {
        while(true) {
            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("1) Browse movies");
            System.out.println("2) Recently Watched");
            System.out.println("3) Exit");
            System.out.println("---------------------------------");
            System.out.print("Enter choice: ");

            int watchedOrBrowseMenuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (watchedOrBrowseMenuChoice) {
                case 1 -> browseMoviesMenu();
                case 2 -> recentlyWatchedMenu();
                case 3 -> exit();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void browseMoviesMenu() {


    }

    public void recentlyWatchedMenu() {

    }





    private void exit() {
        System.out.println("Exiting the Film Streaming Service!");
        System.exit(0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Servicer servicer = new Servicer();
        View view = new View(scanner, servicer);

        view.showMainMenu();

    }


}
