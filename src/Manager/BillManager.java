package Manager;

import Data.Bill;
import Data.User;
import IOFile.IOFunction;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BillManager {
    private List<Bill> billList;
    private final IOFunction<Bill> ioFunction = new IOFunction<>();
    private final Regex regex = new Regex();
    public BillManager() {
        billList = ioFunction.readFile("src/IOFile/Bill.txt");
    }
    private void readFile(){
        billList = ioFunction.readFile("src/IOFile/Bill.txt");
    }
    public boolean checkExistDate(LocalDate dateBill) {
        for (Bill bill : billList) {
            if (bill.getDate().equals(dateBill)) {
                return true;
            }
        }
        return false;
    }
    public void addBill(List<User> userList, Scanner scanner) {
        readFile();
            try {
                boolean flag = false;
                System.out.println("Enter user ID want to add bill:");
                String idCheck = scanner.nextLine();
                if (!regex.validateUserId(idCheck)) {
                    throw new InputMismatchException();
                }
                for (User user : userList) {
                    if (user.getUserId().equals(idCheck)) {
                        flag = true;
                        System.out.println("Enter bill date (format dd/MM/yyyy):");
                        String date = scanner.nextLine();
                        String[] arrayDate = date.split("/");
                        LocalDate dateBill = LocalDate.of(Integer.parseInt(arrayDate[2]),
                                Integer.parseInt(arrayDate[1]), Integer.parseInt(arrayDate[0]));
                        if (!checkExistDate(dateBill)) {
                            System.out.println("Enter number electric used:");
                            int numberUsed = Integer.parseInt(scanner.nextLine());
                            boolean status = false;
                            billList.add(new Bill(user, dateBill, numberUsed, status));
                            ioFunction.writeFile(billList, "src/IOFile/Bill.txt");
                            System.out.println("Add completed!");
                        } else {
                            System.err.println("Date exist! Enter new date.");
                        }
                    }
                }
                if (!flag) {
                    System.err.println("User id not existed!");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.err.println("Incorrect date format input!");
            }
    }
    public void updateBill(List<User> userList, Scanner scanner) {
        readFile();
        if (billList.isEmpty()) {
            System.err.println("No bill in list!");
        } else {
            try {
                boolean flagId = false;
                boolean flagDate = false;
                System.out.println("Enter user ID want to update bill:");
                String idCheck = scanner.nextLine();
                if (!regex.validateUserId(idCheck)) {
                    throw new InputMismatchException();
                }
                for (User user : userList) {
                    if (user.getUserId().equals(idCheck)) {
                        flagId = true;
                        System.out.println("Enter bill date want to update (format dd/MM/yyyy):");
                        String date = scanner.nextLine();
                        String[] arrayDate = date.split("/");
                        LocalDate dateBill = LocalDate.of(Integer.parseInt(arrayDate[2]),
                                Integer.parseInt(arrayDate[1]), Integer.parseInt(arrayDate[0]));
                        for (Bill bill : billList) {
                            if (bill.getDate().equals(dateBill)) {
                                flagDate = true;
                                System.out.println("Enter update number electric used:");
                                String numberUsed = scanner.nextLine();
                                if (!numberUsed.equals("")) {
                                    bill.setNumberUsed(Integer.parseInt(numberUsed));
                                } else {
                                    System.out.println("No change!");
                                }
                                updateBillStatus(scanner, bill);
                                ioFunction.writeFile(billList, "src/IOFile/Bill.txt");
                                System.out.println("Update Completed!");
                            }
                        }
                    }
                }
                if (!flagId) {
                    System.err.println("User id not found!");
                } else if (!flagDate) {
                    System.err.println("Date not exist!");
                }
            } catch (InputMismatchException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Incorrect format input!");
            }
        }
    }
    private static void updateBillStatus(Scanner scanner, Bill bill) {
        System.out.println("Is this bill paid?");
        System.out.println("1. Paid");
        System.out.println("2. Not paid");
        System.out.println("Choose a number:");
        int choiceUpdate = Integer.parseInt(scanner.nextLine());
        if (choiceUpdate < 1 || choiceUpdate > 2) {
            System.out.println("Choose the right number");
        } else {
            switch (choiceUpdate) {
                case 1:
                    bill.setStatus(true);
                    break;
                case 2:
                    bill.setStatus(false);
                    break;
            }
        }
    }
    public void removeBill(List<User> userList, Scanner scanner) {
        readFile();
        if (billList.isEmpty()) {
            System.err.println("No bill in list!");
        } else {
            try {
                boolean flagId = false;
                boolean flagDate = false;
                System.out.println("Enter user ID want to remove bill:");
                String idCheck = scanner.nextLine();
                if (!regex.validateUserId(idCheck)) {
                    throw new InputMismatchException();
                }
                for (User user : userList) {
                    if (user.getUserId().equals(idCheck)) {
                        flagId = true;
                        System.out.println("Enter bill date want to remove (format dd/MM/yyyy):");
                        String date = scanner.nextLine();
                        String[] arrayDate = date.split("/");
                        LocalDate dateBill = LocalDate.of(Integer.parseInt(arrayDate[2]),
                                Integer.parseInt(arrayDate[1]), Integer.parseInt(arrayDate[0]));
                        for (int i = 0; i < billList.size(); i++) {
                            if (billList.get(i).getDate().equals(dateBill)) {
                                flagDate = true;
                                confirmRemoveBill(scanner, i);
                            }
                        }
                    }
                }
                if (!flagId) {
                    System.err.println("Id not found!");
                } else if (!flagDate) {
                    System.err.println("Date not found!");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Incorrect format input!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Incorrect date format input!");
            }
        }
    }
    private void confirmRemoveBill(Scanner scanner, int i) {
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
                    billList.remove(billList.get(i));
                    ioFunction.writeFile(billList, "src/IOFile/Bill.txt");
                    System.out.println("Remove Completed!");
                    break;
                case 2:
                    System.out.println("No remove!");
                    break;
            }
        }
    }

}
