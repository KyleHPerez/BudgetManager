package budget;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class Main {

    static List<String[]> purchaseList = new ArrayList<>();
    static List<String> incomeList = new ArrayList<>();

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            System.out.println("""
                \nChoose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                5) Save
                6) Load
                7) Analyze (Sort)
                0) Exit""");
            String inputSymbol = scanner.nextLine();
            switch (inputSymbol) {
                case "0": {exit = true; System.out.println("\nBye!"); break;}
                case "1": {addIncome(logger); break;}
                case "2": {addPurchase(logger); break;}
                case "3": {showList(); break;}
                case "4": {balance(); break;}
                case "5": {save(); break;}
                case "6": {load(); break;}
                case "7": {analyze(); break;}
                default : { break;}
            }
        }
    }

    private static void addIncome(Logger logger) {
        System.out.println("\nEnter income:");
        try (Scanner scanner = new Scanner(System.in)) {
            Map<String, String> item = new HashMap<>();
            String amount = scanner.nextLine();
            incomeList.add(amount);
            System.out.println("Income was added!");
        } catch (InputMismatchException ex) {
            logger.info("Invalid input for item!");
        }
    }

    private static void addPurchase(Logger logger) {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    \nChoose the type of purchase
                    1) Food
                    2) Clothes
                    3) Entertainment
                    4) Other
                    5) Back""");
            try (Scanner scanner = new Scanner(System.in)) {
                String option = scanner.nextLine();
                String type = switch (option) {
                    case "1" -> "Food";
                    case "2" -> "Clothes";
                    case "3" -> "Entertainment";
                    case "4" -> "Other";
                    default -> "Back";
                };
                if (type.equals("Back")) {
                    back = true;
                } else {
                    System.out.println("\nEnter purchase name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter its price:");
                    String price = scanner.nextLine();
                    String[] item = {type, name, price};
                    purchaseList.add(item);
                    System.out.println("Purchase was added!");
                }
            } catch (InputMismatchException e) {
                logger.info("Input error in addPurchase" + e.getMessage());
            }
        }
    }

    private static void showList() {
        if (purchaseList.isEmpty()) {
            System.out.println("\nThe purchase list is empty");
        } else {
            boolean back = false;
            while (!back) {
                System.out.println("""
                        \nChoose the type of purchases
                        1) Food
                        2) Clothes
                        3) Entertainment
                        4) Other
                        5) All
                        6) Back""");
                try (Scanner scanner = new Scanner(System.in)) {
                    String option = scanner.nextLine();
                    String type = switch (option) {
                        case "1" -> "Food";
                        case "2" -> "Clothes";
                        case "3" -> "Entertainment";
                        case "4" -> "Other";
                        case "5" -> "All";
                        default -> "Back";
                    };
                    if (type.equals("Back")) {
                        back = true;
                    } else {
                        System.out.printf("\n%s:\n", type);
                        double totalSum = 0;
                        int counter = 0;
                        for (String[] item : purchaseList) {
                            if (type.equals("All")) {
                                System.out.printf("%s $%.2f\n", item[1], Double.parseDouble(item[2]));
                                totalSum += Double.parseDouble(item[2]);
                                counter++;
                            } else {
                                if (item[0].equals(type)) {
                                    System.out.printf("%s $%.2f\n", item[1], Double.parseDouble(item[2]));
                                    totalSum += Double.parseDouble(item[2]);
                                    counter++;
                                }
                            }
                        }
                        if (counter == 0 || totalSum == 0) {
                            System.out.println("\nThe purchase list is empty");
                        } else {
                            System.out.printf("Total sum: $%.2f\n", totalSum);
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input error in showList" + e.getMessage());
                }
            }
        }
    }

    private static void balance() {
        double balance = 0;
        for (int i = 0; i < incomeList.size(); i++) {
            balance += Double.parseDouble(incomeList.get(i));
        }
        for (int i = 0; i < purchaseList.size(); i++) {
            balance -= Double.parseDouble(purchaseList.get(i)[2]);
        }
        balance = balance < 0 ? 0 : balance;
        System.out.printf("\nBalance: $%.2f\n", balance);
    }

    private static void save() {
        File file = new File("./purchases.txt");
        try (PrintWriter printWriter = new PrintWriter(file)) {
            purchaseList.forEach(item -> printWriter.printf("{{%s}, {%s}, {%s}}\n", item[0], item[1], item[2]));
            incomeList.forEach(credit -> printWriter.printf("{{%s}, {%s}, {%s}}\n", "Income", "NA", credit));
        } catch (IOException e) {
            System.out.println("File exists but cannot be written to or opened.");
        }
        System.out.println("Purchases were saved!");
    }

    private static void load() {
        File file = new File("./purchases.txt");
        if (file.isFile()) {
            try (Scanner fileReader = new Scanner(file)) {
                purchaseList.clear();
                incomeList.clear();
                while (fileReader.hasNextLine()) {
                    String line = fileReader.nextLine();
                    String type = line.substring(2, line.indexOf('}'));
                    line = line.substring(line.indexOf('}') + 2);
                    String name = line.substring(2, line.indexOf('}'));
                    line = line.substring(line.indexOf('}') + 2);
                    String price = line.substring(2, line.indexOf('}'));
                    if (type.equals("Income")) {
                        incomeList.add(price);
                    } else {
                        String[] item = {type, name, price};
                        purchaseList.add(item);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist.");
            }
            System.out.println("\nPurchases were loaded!");
        }
    }

    public static void analyze() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                        \nHow do you want to sort?
                        1) Sort all purchases
                        2) Sort by type
                        3) Sort certain type
                        4) back""");
            try (Scanner scanner = new Scanner(System.in)) {
                String option = scanner.nextLine();
                switch (option) {
                    case "1": {
                        ListSorter listSorter = new ListSorter(new SortAllMethod());
                        listSorter.sort(purchaseList);
                        break;
                    }
                    case "2": {
                        ListSorter listSorter = new ListSorter(new SortByTypeMethod());
                        listSorter.sort(purchaseList);
                        break;
                    }
                    case "3": {
                        System.out.println("""
                                \nChoose the type of purchase
                                1) Food
                                2) Clothes
                                3) Entertainment
                                4) Other""");
                        String option2 = scanner.nextLine();
                        String type = switch (option2) {
                            case "1" -> "Food";
                            case "2" -> "Clothes";
                            case "3" -> "Entertainment";
                            case "4" -> "Other";
                            default -> "Invalid";
                        };
                        if (!type.equals("Invalid")) {
                            ListSorter listSorter = new ListSorter(new SortCertainTypeMethod(type));
                            listSorter.sort(purchaseList);
                        }
                        break;
                    }
                    default : {
                        back = true;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Input error in analyze " + e.getMessage());
            }
        }
    }
}
