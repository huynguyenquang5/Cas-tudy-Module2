package Manager;

import Data.Admin;
import Data.User;
import IOFile.IOFunction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Login {
    private final Regex regex = new Regex();
    private final List<Admin> adminList;
    private List<User> userList;
    protected static String userAccount;
    protected static String userPassword;

    private final IOFunction<User> ioFunction = new IOFunction<>();

    public Login() {
        adminList = new ArrayList<>();
        userList = ioFunction.readFile("src/IOFile/User.txt");
    }
    public void readFile() {
        userList = ioFunction.readFile("src/IOFile/User.txt");
    }
    public void addAdminAccount(Admin admin) {
        adminList.add(admin);
    }

    public boolean loginAdminAccount(Scanner scanner) {
        boolean flagId = false;
        System.out.println("Enter account:");
        String staffAccount = scanner.nextLine();
        if (staffAccount.equals("")) {
            throw new InputMismatchException();
        }
        System.out.println("Enter password:");
        String staffPassword = scanner.nextLine();
        if (staffPassword.equals("")) {
            throw new InputMismatchException();
        }
        for (Admin admin: adminList) {
            if (admin.getStaffId().equals(staffAccount)) {
                flagId = true;
                if (admin.getStaffPassword().equals(staffPassword)) {
                    System.out.println("Login Successful!");
                    return true;
                }
            }
        } if (!flagId) {
            System.err.println("Wrong admin account!");
        } else {
            System.err.println("Wrong password account!");
        }
        return false;
    }
    public boolean loginUserAccount(Scanner scanner) {
        readFile();
        boolean flagUserId = false;
        System.out.println("Enter account:");
        userAccount = scanner.nextLine();
        if (!regex.validateUserId(userAccount)) {
            throw new InputMismatchException();
        }
        System.out.println("Enter password:");
        userPassword = scanner.nextLine();
        if (!regex.validatePassword(userPassword)) {
            throw new InputMismatchException();
        }
        for (User user: userList) {
            if (user.getUserId().equals(userAccount)) {
                flagUserId = true;
                if (user.getPassword().equals(userPassword)) {
                    System.out.println("Login Successful!");
                    return true;
                }
            }
        } if (!flagUserId) {
            System.err.println("Wrong admin account!");
        } else {
            System.err.println("Wrong password account!");
        }
        return false;
    }

    public boolean changePassword(Scanner scanner) {
        readFile();
        System.out.println("Enter old password:");
        String oldPassword = scanner.nextLine();
        if (!regex.validatePassword(oldPassword)) {
            throw new InputMismatchException();
        }
        if (oldPassword.equals(userPassword)) {
            System.out.println("Enter new password:");
            String newPassword = scanner.nextLine();
            if (!regex.validatePassword(newPassword)) {
                throw new InputMismatchException();
            }
            System.out.println("Enter confirm new password:");
            String confirmPassword = scanner.nextLine();
            if (!regex.validatePassword(confirmPassword)) {
                throw new InputMismatchException();
            } else if (confirmPassword.equals(newPassword)) {
                for (User user: userList) {
                    if (user.getUserId().equals(userAccount)) {
                        user.setPassword(newPassword);
                        ioFunction.writeFile(userList, "src/IOFile/User.txt");
                        System.out.println("Update password successful!");
                        return true;
                    }
                }
            } else {
                System.err.println("Wrong confirm password!");
            }
        } else {
            System.err.println("Wrong password!");
        } return false;
    }
}
