package Manager;

import Data.Bill;
import Data.User;
import IOFile.IOFunction;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Display {
    private List<User> userList;
    private List<Bill> billList;
    private final IOFunction<User> ioFunctionUser = new IOFunction<>();
    private final IOFunction<Bill> ioFunctionBill = new IOFunction<>();
    private final Regex regex = new Regex();
    Locale locale = new Locale("vi", "VN");
    NumberFormat numberFormat = NumberFormat.getInstance(locale);
    NumberFormat numberCurrency = NumberFormat.getCurrencyInstance(locale);

    public Display() {
        readFile();
        userList = ioFunctionUser.readFile("src/IOFile/User.txt");
        billList = ioFunctionBill.readFile("src/IOFile/Bill.txt");
    }

    private void readFile() {
        userList = ioFunctionUser.readFile("src/IOFile/User.txt");
        billList = ioFunctionBill.readFile("src/IOFile/Bill.txt");
    }

    public void displayBill(int choice, Scanner scanner) {
        readFile();
        if (billList.isEmpty()) {
            System.err.println("No bill in list!");
        } else {
            if (choice < 1 || choice > 2) {
                System.err.println("Choose the right number.");
            } else {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                switch (choice) {
                    case 1:
                        displayBillByUserId(scanner, dateTimeFormatter);
                        break;
                    case 2:
                        displayBillByMonth(scanner, dateTimeFormatter);
                        break;
                }
            }
        }
    }
    private void displayBillByMonth(Scanner scanner, DateTimeFormatter dateTimeFormatter) {
        boolean flagMonth = false;
        System.out.println("Enter month:");
        int month = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter year:");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.printf("%-20s%-25s%-20s%-20s%-20s", "User Id", "Bill date", "Electric Used", "Amount",
                "Status");
        for (Bill bill: billList) {
            if (bill.getDate().getMonthValue() == month && bill.getDate().getYear() == year) {
                flagMonth = true;
                if (bill.isStatus()) {
                    System.out.printf("\n%-20s%-25s%-20s%-20s%-20s", bill.getUserId().getUserId(),
                            dateTimeFormatter.format(bill.getDate()), bill.getNumberUsed(),
                            numberCurrency.format(bill.getAmountAfterTax()) ,"Paid");
                } else {
                    System.out.printf("\n%-20s%-25s%-20s%-20s%-20s", bill.getUserId().getUserId(),
                            dateTimeFormatter.format(bill.getDate()), bill.getNumberUsed(),
                            numberCurrency.format(bill.getAmountAfterTax()) ,"Not Paid");
                }
            }
        }
        System.out.println("\n");
        if (!flagMonth) {
            System.out.println("Bill not found!");
        }
    }

    private void displayBillByUserId(Scanner scanner, DateTimeFormatter dateTimeFormatter) {
        boolean flagMonth = false;
        System.out.println("Enter user id:");
        String id = scanner.nextLine();
        if (!regex.validateUserId(id)) {
            throw new InputMismatchException();
        }
        System.out.printf("%-20s%-25s%-20s%-20s%-20s", "User Id", "Bill date", "Electric Used", "Amount",
                "Status");
        for (Bill bill: billList) {
            if (bill.getUserId().getUserId().equals(id)) {
                flagMonth = true;
                if (bill.isStatus()) {
                    System.out.printf("\n%-20s%-25s%-20s%-20s%-20s", bill.getUserId().getUserId(),
                            dateTimeFormatter.format(bill.getDate()), bill.getNumberUsed(),
                            numberCurrency.format(bill.getAmountAfterTax()) ,"Paid");
                } else {
                    System.out.printf("\n%-20s%-25s%-20s%-20s%-20s", bill.getUserId().getUserId(),
                            dateTimeFormatter.format(bill.getDate()), bill.getNumberUsed(),
                            numberCurrency.format(bill.getAmountAfterTax()) ,"Not Paid");
                }
            }
        }
        System.out.println("\n");
        if (!flagMonth) {
            System.out.println("No bill!");
        }
    }

    public void displayUser(int choice, Scanner scanner) {
        readFile();
        if (userList.isEmpty()) {
            System.err.println("No user in list!");
        } else {
            if (choice < 1 || choice > 4) {
                System.err.println("Choose the right number.");
            } else {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                switch (choice) {
                    case 1:
                        displayUserById(scanner, dateTimeFormatter);
                        break;
                    case 2:
                        displayUserByName(scanner, dateTimeFormatter);
                        break;
                    case 3:
                        displayUserByAddress(scanner, dateTimeFormatter);
                        break;
                    case 4:
                        displayAllUser(dateTimeFormatter);
                        break;
                }
            }
        }
    }

    public void displayAllUser(DateTimeFormatter dateTimeFormatter) {
        readFile();
        if (userList.isEmpty()) {
            System.err.println("No user in list!");
        } else {
            System.out.printf("%-20s%-25s%-20s%-45s%-20s%-20s", "User ID", "Name", "Telephone",
                    "Address", "Date Register", "Password");
            for (User user : userList) {
                System.out.printf("\n%-20s%-25s%-20s%-45s%-20s%-20s", user.getUserId(), user.getName(),
                        user.getTelephone(), user.getAddress(), dateTimeFormatter.format(user.getDateRegister()),
                        user.getPassword());
            }
            System.out.println("\n");
        }
    }

    public void displayUserByAddress(Scanner scanner, DateTimeFormatter dateTimeFormatter) {
        readFile();
        boolean flagAddress = false;
        if (userList.isEmpty()) {
            System.err.println("No user in list!");
        } else {
            System.out.println("Enter address:");
            String searchAddress = scanner.nextLine();
            if (searchAddress.equals(""))
            {
                throw new InputMismatchException();
            }
            System.out.printf("%-20s%-25s%-20s%-45s%-20s%-20s", "User ID", "Name", "Telephone",
                    "Address", "Date Register", "Password");
            for (User user: userList) {
                if (user.getAddress().toLowerCase().contains(searchAddress)) {
                    flagAddress = true;
                    System.out.printf("\n%-20s%-25s%-20s%-45s%-20s%-20s", user.getUserId(), user.getName(),
                            user.getTelephone(), user.getAddress(), dateTimeFormatter.format(user.getDateRegister()),
                            user.getPassword());
                }
            }
            System.out.println("\n");
            if (!flagAddress) {
                System.out.println("Address not found!");
            }
        }
    }
    public void displayUserByName(Scanner scanner, DateTimeFormatter dateTimeFormatter) {
        readFile();
        boolean flagName = false;
        if (userList.isEmpty()) {
            System.err.println("No user in list!");
        } else {
            System.out.println("Enter name:");
            String searchName = scanner.nextLine();
            if (searchName.equals(""))
            {
                throw new InputMismatchException();
            }
            System.out.printf("%-20s%-25s%-20s%-45s%-20s%-20s", "User ID", "Name", "Telephone",
                    "Address", "Date Register", "Password");
            for (User user: userList) {
                if (user.getName().toLowerCase().contains(searchName) || user.getName().equals(searchName)) {
                    flagName = true;
                    System.out.printf("\n%-20s%-25s%-20s%-45s%-20s%-20s", user.getUserId(), user.getName(),
                            user.getTelephone(), user.getAddress(), dateTimeFormatter.format(user.getDateRegister()),
                            user.getPassword());
                }
            }
            System.out.println("\n");
            if (!flagName) {
                System.out.println("Name not found!");
            }
        }
    }

    public void displayUserById(Scanner scanner, DateTimeFormatter dateTimeFormatter) {
        readFile();
        boolean flagId = false;
        if (userList.isEmpty()) {
            System.err.println("No user in list!");
        } else {
            System.out.println("Enter user id:");
            String searchId = scanner.nextLine();
            if (searchId.equals(""))
            {
                throw new InputMismatchException();
            }
            System.out.printf("%-20s%-25s%-20s%-45s%-20s%-20s", "User ID", "Name", "Telephone",
                    "Address", "Date Register", "Password");
            for (User user: userList) {
                if (user.getUserId().toLowerCase().contains(searchId) || user.getUserId().equals(searchId)) {
                    flagId = true;
                    System.out.printf("\n%-20s%-25s%-20s%-45s%-20s%-20s", user.getUserId(), user.getName(),
                            user.getTelephone(), user.getAddress(), dateTimeFormatter.format(user.getDateRegister()),
                            user.getPassword());
                }
            }
            System.out.println("\n");
            if (!flagId) {
                System.out.println("Id not found!");
            }
        }
    }

    public void displaySingleUser() {
        readFile();
        if (userList.isEmpty()) {
            System.err.println("No user in list!");
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.printf("%-20s%-25s%-20s%-45s%-20s%-20s", "User ID", "Name", "Telephone",
                    "Address", "Date Register", "Password");
            for (User user: userList) {
                if (user.getUserId().equals(Login.userAccount)) {
                    System.out.printf("\n%-20s%-25s%-20s%-45s%-20s%-20s", user.getUserId(), user.getName(),
                            user.getTelephone(), user.getAddress(), dateTimeFormatter.format(user.getDateRegister()),
                            user.getPassword());
                }
            }
            System.out.println("\n");
        }
    }

    public void displayAllBillSingleUser() {
        readFile();
        boolean flagBill = false;
        if (billList.isEmpty()) {
            System.err.println("No bill in list!");
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.printf("%-20s%-25s%-20s%-20s", "User Id", "Bill date", "Electric Used",
                    "Status");
            for (Bill bill: billList) {
                if (bill.getUserId().getUserId().equals(Login.userAccount)) {
                    if (bill.isStatus()) {
                        flagBill = true;
                        System.out.printf("\n%-20s%-25s%-20s%-20s", bill.getUserId().getUserId(),
                                dateTimeFormatter.format(bill.getDate()), bill.getNumberUsed(), "Paid");
                    } else {
                        flagBill = true;
                        System.out.printf("\n%-20s%-25s%-20s%-20s", bill.getUserId().getUserId(),
                                dateTimeFormatter.format(bill.getDate()), bill.getNumberUsed(), "Not Paid");
                    }
                }
            }
            System.out.println("\n");
            if (!flagBill) {
                System.out.println("No bill!");
            }
        }
    }

    public void displayBillInformation(Scanner scanner) {
        readFile();
        boolean flagMonth = false;
        if (billList.isEmpty()) {
            System.out.println("No bill in list!");
        } else {
            System.out.println("Enter month:");
            int month = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter year:");
            int year = Integer.parseInt(scanner.nextLine());
            for (Bill bill : billList) {
                if (bill.getUserId().getUserId().equals(Login.userAccount)) {
                    if (month == bill.getDate().getMonthValue() && year == bill.getDate().getYear()) {
                        flagMonth = true;
                        System.out.println("THÔNG TIN HÓA ĐƠN TIỀN ĐIỆN THÁNG " + bill.getDate().getMonthValue() +
                                " NĂM " + bill.getDate().getYear());
                        System.out.printf("\n%-20s%-40s%-20s%s", "", "ĐƠN GIÁ THEO QĐ 648/QĐ-BCT",
                                "SẢN LƯỢNG", "THÀNH TIỀN");
                        System.out.printf("\n%-25s%-37s%-20s%s", "", "(đồng/kWh)", "(kWh)", "(đồng)");
                        if (bill.getNumberUsed() <= 50) {
                            billInformationSmallerEqual50(numberFormat, bill);
                        } else if (bill.getNumberUsed() <= 100) {
                            billInformationSmallerEqual100(numberFormat, bill);
                        } else if (bill.getNumberUsed() <= 200) {
                            billInformationSmallerEqual200(numberFormat, bill);
                        } else if (bill.getNumberUsed() <= 300) {
                            billInformationSmallerEqual300(numberFormat, bill);
                        } else if (bill.getNumberUsed() <= 400) {
                            billInformationSmallerEqual400(numberFormat, bill);
                        } else {
                            billInformationLarger400(numberFormat, bill);
                        }
                        billInformationBeforeAfterTax(numberCurrency, bill);
                    }
                }
            }
            if (!flagMonth) {
                System.out.println("Bill not found!");
            }
        }
    }

    private static void billInformationBeforeAfterTax(NumberFormat numberCurrency, Bill bill) {
        System.out.println("Thành tiền:");
        System.out.println("Tiền điện chưa thuế: " + numberCurrency.format(bill.getAmountBeforeTax()));
        System.out.println("Thuế GTGT (8%) tiền điện: " + numberCurrency.format(bill.getTax()));
        System.out.println("Tổng cộng tiền thanh toán: " + numberCurrency.format(bill.getAmountAfterTax()));
        System.out.println("\n");
    }

    private static void billInformationLarger400(NumberFormat numberFormat, Bill bill) {
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 1", numberFormat.format(1678),
                50, numberFormat.format(1678 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 2", numberFormat.format(1734),
                50, numberFormat.format(1734 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 3", numberFormat.format(2014),
                100, numberFormat.format(2014 * 100));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 4", numberFormat.format(2536),
                100, numberFormat.format(2536 * 100));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 5", numberFormat.format(2834),
                100, numberFormat.format(2834 * 100));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 6", numberFormat.format(2927),
                bill.getNumberUsed() - 400,
                numberFormat.format(2927 * (bill.getNumberUsed() - 400)));
        System.out.println("\n");
    }

    private static void billInformationSmallerEqual400(NumberFormat numberFormat, Bill bill) {
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 1", numberFormat.format(1678),
                50, numberFormat.format(1678 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 2", numberFormat.format(1734),
                50, numberFormat.format(1734 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 3", numberFormat.format(2014),
                100, numberFormat.format(2014 * 100));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 4", numberFormat.format(2536),
                100, numberFormat.format(2536 * 100));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 5", numberFormat.format(2834),
                bill.getNumberUsed() - 300,
                numberFormat.format(2834 * (bill.getNumberUsed() - 300)));
        System.out.println("\n");
    }

    private static void billInformationSmallerEqual300(NumberFormat numberFormat, Bill bill) {
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 1", numberFormat.format(1678),
                50, numberFormat.format(1678 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 2", numberFormat.format(1734),
                50, numberFormat.format(1734 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 3", numberFormat.format(2014),
                100, numberFormat.format(2014 * 100));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 4", numberFormat.format(2536),
                bill.getNumberUsed() - 200,
                numberFormat.format(2536 * (bill.getNumberUsed() - 200)));
        System.out.println("\n");
    }

    private static void billInformationSmallerEqual200(NumberFormat numberFormat, Bill bill) {
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 1", numberFormat.format(1678),
                50, numberFormat.format(1678 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 2", numberFormat.format(1734),
                50, numberFormat.format(1734 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 3", numberFormat.format(2014),
                bill.getNumberUsed() - 100,
                numberFormat.format(2014 * (bill.getNumberUsed() - 100)));
        System.out.println("\n");
    }

    private static void billInformationSmallerEqual100(NumberFormat numberFormat, Bill bill) {
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 1", numberFormat.format(1678),
                50, numberFormat.format(1678 * 50));
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 2", numberFormat.format(1734),
                bill.getNumberUsed() - 50,
                numberFormat.format(1734 * (bill.getNumberUsed() - 50)));
        System.out.println("\n");
    }

    private static void billInformationSmallerEqual50(NumberFormat numberFormat, Bill bill) {
        System.out.printf("\n%-28s%-35s%-19s%s", "Bậc Thang 1", numberFormat.format(1678),
                bill.getNumberUsed(), numberFormat.format(bill.getAmountBeforeTax()));
        System.out.println("\n");
    }
}

