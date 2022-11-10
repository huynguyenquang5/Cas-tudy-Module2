package Manager;

import Data.User;
import IOFile.IOFunction;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private final Regex regex = new Regex();
    private final List<User> userList;
    private final IOFunction<User> ioFunction = new IOFunction<>();
    public UserManager() {
        userList = ioFunction.readFile("src/IOFile/User.txt");
    }

    public boolean checkExistId(String userId) {
        for (User user: userList) {
            if (user.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(Scanner scanner) {
            try {
                System.out.println("Enter user id:");
                String id = scanner.nextLine();
                if (!regex.validateUserId(id)) {
                    throw new InputMismatchException();
                }
                if (!checkExistId(id)) {
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    if (name.equals("")) {
                        throw new InputMismatchException();
                    }
                    System.out.println("Enter telephone:");
                    String telephone = scanner.nextLine();
                    if (telephone.equals("")) {
                        throw new InputMismatchException();
                    }
                    System.out.println("Enter address:");
                    String address = scanner.nextLine();
                    if (address.equals("")) {
                        throw new InputMismatchException();
                    }
                    System.out.println("Enter date register using electric (format dd/MM/yyyy):");
                    String date = scanner.nextLine();
                    String[] arrayDate = date.split("/");
                    LocalDate dateRegister = LocalDate.of(Integer.parseInt(arrayDate[2]),
                            Integer.parseInt(arrayDate[1]), Integer.parseInt(arrayDate[0]));
                    System.out.println("Enter user " + id + " password:");
                    String userPassword = scanner.nextLine();
                    if (!regex.validatePassword(userPassword)) {
                        throw new InputMismatchException();
                    }
                    userList.add(new User(id, name, telephone, address, dateRegister, userPassword));
                    ioFunction.writeFile(userList, "src/IOFile/User.txt");
                    System.out.println("Add completed!");
                } else {
                    System.err.println("User id exist! Enter new id.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Incorrect format input!");
            } catch (ArrayIndexOutOfBoundsException | DateTimeException e) {
                System.err.println("Incorrect format date input!");
            }
    }

    public void updateUser(int choice, Scanner scanner) {
        if (userList.isEmpty()) {
            System.err.println("No user in list!");
        } else {
            try {
                System.out.println("Enter update user id:");
                String id = scanner.nextLine();
                if (!regex.validateUserId(id)) {
                    throw new InputMismatchException();
                }
                for (User user : userList) {
                    if (user.getUserId().equals(id)) {
                        checkExistId(id);
                        if (choice < 1 || choice > 5) {
                            System.err.println("Choose the right number.");
                        } else {
                            switch (choice) {
                                case 1:
                                    updateUserName(scanner, id, user);
                                    break;
                                case 2:
                                    updateUserTelephone(scanner, id, user);
                                    break;
                                case 3:
                                    updateUserAddress(scanner, id, user);
                                    break;
                                case 4:
                                    updateUserDateRegister(scanner, id, user);
                                    break;
                                case 5:
                                    updateUserPassword(scanner, id, user);
                                    break;
                            }
                        }
                    }
                }
                if (!checkExistId(id)) {
                    System.err.println("User id not found!");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            } catch (ArrayIndexOutOfBoundsException | DateTimeException ex) {
                System.err.println("Incorrect date format input");
            }
        }
    }


    private void updateUserPassword(Scanner scanner, String id, User user) {
        System.out.println("Enter update password from id " + id + ":");
        String password = scanner.nextLine();
        if (regex.validatePassword(password)) {
            user.setPassword(password);
            ioFunction.writeFile(userList, "src/IOFile/User.txt");
            System.out.println("Update completed!");
        } else {
            System.err.println("Incorrect format input!");
        }
    }

    private void updateUserDateRegister(Scanner scanner, String id, User user) {
        System.out.println("Enter update date using electric from id " + id + " (format dd/MM/yyyy):");
        String date = scanner.nextLine();
        if (!date.equals("")){
            String[] arrayDate = date.split("/");
            user.setDateRegister(LocalDate.of(Integer.parseInt(arrayDate[2]),
                    Integer.parseInt(arrayDate[1]), Integer.parseInt(arrayDate[0])));
            ioFunction.writeFile(userList, "src/IOFile/User.txt");
            System.out.println("Update completed!");
        } else {
            System.out.println("No change!");
        }
    }

    private void updateUserAddress(Scanner scanner, String id, User user) {
        System.out.println("Enter update address from id " + id + ":");
        String address = scanner.nextLine();
        if (!address.equals("")) {
            user.setAddress(address);
            ioFunction.writeFile(userList, "src/IOFile/User.txt");
            System.out.println("Update completed!");
        } else {
            System.out.println("No change!");
        }
    }

    private void updateUserTelephone(Scanner scanner, String id, User user) {
        System.out.println("Enter update telephone from id " + id + ":");
        String telephone = scanner.nextLine();
        if (!telephone.equals("")) {
            user.setTelephone(telephone);
            ioFunction.writeFile(userList, "src/IOFile/User.txt");
            System.out.println("Update completed!");
        } else {
            System.out.println("No change!");
        }
    }

    private void updateUserName(Scanner scanner, String id, User user) {
        System.out.println("Enter update name from id " + id + ":");
        String name = scanner.nextLine();
        if (!name.equals("")) {
            user.setName(name);
            ioFunction.writeFile(userList, "src/IOFile/User.txt");
            System.out.println("Update completed!");
        } else {
            System.out.println("No change!");
        }
    }

    public void removeUser(Scanner scanner) {
        if (userList.isEmpty()) {
            System.err.println("No user in list!");
        } else {
            boolean flag = false;
            System.out.println("Enter user id want to remove: ");
            String id = scanner.nextLine();
            if (!regex.validateUserId(id)) {
                throw new InputMismatchException();
            }
            for (int i = 0; i < userList.size(); i++){
                if (userList.get(i).getUserId().equals(id)) {
                    flag = true;
                    confirmRemoveUser(scanner, i);
                }
            }
            if (!flag) {
                System.err.println("User id not found !!");
            }
        }

    }

    private void confirmRemoveUser(Scanner scanner, int i) {
        System.out.println("Do you want to remove?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("Choose a number");
        int choiceRemove = Integer.parseInt(scanner.nextLine());
        if (choiceRemove < 1 || choiceRemove > 2) {
            System.out.println("Choose the right number");
        } else {
            switch (choiceRemove) {
                case 1:
                    userList.remove(userList.get(i));
                    ioFunction.writeFile(userList, "src/IOFile/User.txt");
                    System.out.println("Remove Completed!");
                    break;
                case 2:
                    System.out.println("No remove!");
                    break;
            }
        }
    }
}

