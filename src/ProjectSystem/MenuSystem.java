package ProjectSystem;
import Data.Admin;
import Data.Bill;
import Data.User;
import IOFile.IOFunction;
import Manager.BillManager;
import Manager.Display;
import Manager.Login;
import Manager.UserManager;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuSystem {
    private final Scanner scanner = new Scanner(System.in);
    BillManager billManager = new BillManager();
    UserManager userManager = new UserManager();
    IOFunction<User> ioFunctionUser = new IOFunction<>();
    IOFunction<Bill> ioFunctionBill = new IOFunction<>();
    Display display = new Display();
    Login login = new Login();
    List<User> users;
    List<Bill> bills;
    public MenuSystem() {
        Admin admin =  new Admin("staff1111", "123456");
        login.addAdminAccount(admin);
        users = ioFunctionUser.readFile("src/IOFile/User.txt");
        bills = ioFunctionBill.readFile("src/IOFile/Bill.txt");
    }
    public void readFile() {
         users = ioFunctionUser.readFile("src/IOFile/User.txt");
     }

    public void menuBillSystem() {
        do {
            try {
                System.out.println("MENU");
                System.out.println("1. Add bill");
                System.out.println("2. Update bill");
                System.out.println("3. Remove bill");
                System.out.println("4. Display bill");
                System.out.println("0. Back to user screen");
                System.out.println("Choose a number:");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        readFile();
                        billManager.addBill(users, scanner);
                        break;
                    case 2:
                        readFile();
                        billManager.updateBill(users, scanner);
                        break;
                    case 3:
                        readFile();
                        billManager.removeBill(users, scanner);
                        break;
                    case 4:
                        menuDisplayBill();
                        break;
                    case 0:
                        menuUserManager();
                        break;
                    default:
                        System.err.println("Wrong number!");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            }
        } while (true);
    }

    public void menuUserManager() {
        do {
            try {
                System.out.println("MENU");
                System.out.println("1. Add user");
                System.out.println("2. Edit user");
                System.out.println("3. Remove user");
                System.out.println("4. Display user");
                System.out.println("5. Move to bill menu");
                System.out.println("9. Back to login screen");
                System.out.println("0. Exit Program");
                System.out.println("Choose a number:");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        userManager.addUser(scanner);
                        break;
                    case 2:
                        menuUpdateUserManager();
                        break;
                    case 3:
                        userManager.removeUser(scanner);
                        break;
                    case 4:
                        menuDisplayUser();
                        break;
                    case 5:
                        menuBillSystem();
                        break;
                    case 9:
                        loginSystem();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.err.println("Wrong number!");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            }
        } while (true);
    }
    public void menuUserDisplay() {
        do {
            try {
                System.out.println("USER SYSTEM");
                System.out.println("1. Id information");
                System.out.println("2. Change password");
                System.out.println("3. Display all bills");
                System.out.println("4. Display bill information");
                System.out.println("9. Back to login screen");
                System.out.println("0. Exit Program");
                System.out.println("Choose a number:");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        display.displaySingleUser();
                        break;
                    case 2:
                        if (login.changePassword(scanner)) {
                            loginSystem();
                        }
                        break;
                    case 3:
                        display.displayAllBillSingleUser();
                        break;
                    case 4:
                        display.displayBillInformation(scanner);
                        break;
                    case 9:
                        loginSystem();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.err.println("Wrong number!");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            }
        } while (true);
    }

    public void menuUpdateUserManager() {
        int choice;
        do {
            try {
                System.out.println("Choose type of update user:");
                System.out.println("1. Update user name:");
                System.out.println("2. Update user telephone:");
                System.out.println("3. Update user address:");
                System.out.println("4. Update user date register:");
                System.out.println("5. Update user password:");
                System.out.println("0. Back");
                System.out.println("Choose a number:");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    break;
                }
                userManager.updateUser(choice, scanner);
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            }
        } while (true);
    }

    public void menuDisplayUser() {
        int choice;
        do {
            try {
                System.out.println("Choose type of display:");
                System.out.println("1. Display user by id");
                System.out.println("2. Display user by name");
                System.out.println("3. Display user by address");
                System.out.println("4. Display all user");
                System.out.println("0. Back");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    break;
                }
                display.displayUser(choice, scanner);
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            }
        } while (true);
    }
    public void menuDisplayBill() {
        int choice;
        do {
            try {
                System.out.println("Choose type of display:");
                System.out.println("1. Display bill by user id");
                System.out.println("2. Display bill by month");
                System.out.println("0. Back");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    break;
                }
                display.displayBill(choice, scanner);
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            }
        } while (true);
    }

    public void loginSystem() {
        do {
            try {
                System.out.println("LOGIN SYSTEM.");
                System.out.println("Choose type of account:");
                System.out.println("1. Admin");
                System.out.println("2. User");
                System.out.println("0. Exit program!");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        if (login.loginAdminAccount(scanner)) {
                            menuUserManager();
                        }
                        break;
                    case 2:
                        if (login.loginUserAccount(scanner)) {
                            menuUserDisplay();
                        }
                        break;
                    case 0:
                        System.exit(0);
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            }
        } while (true);
    }
}


