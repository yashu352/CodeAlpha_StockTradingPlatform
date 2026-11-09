import java.util.*;

class Stock {
    String name;
    double price;

    Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    void display() {
        System.out.println(name + "  Rs." + price);
    }
}

class User {
    String name;
    double balance;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
}

class Transaction {
    String type;
    String stock;
    int quantity;
    double amount;

    Transaction(String type, String stock,
                int quantity, double amount) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.amount = amount;
    }

    void display() {
        System.out.println(type + "  " + stock +
                "  Qty: " + quantity +
                "  Rs." + amount);
    }
}

public class CodeAlpha_StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = new User("Jay", 100000);

        ArrayList<Stock> stocks = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        HashMap<String, Integer> portfolio = new HashMap<>();
        ArrayList<Double> performance = new ArrayList<>();

        stocks.add(new Stock("TCS", 3500));
        stocks.add(new Stock("INFOSYS", 1800));
        stocks.add(new Stock("RELIANCE", 1400));
        stocks.add(new Stock("WIPRO", 500));
        stocks.add(new Stock("TATAMOTORS", 800));

        int choice;

        do {
            System.out.println("\n===== STOCK TRADING SYSTEM =====");
            System.out.println("1. Display Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Check Balance");
            System.out.println("6. Transaction History");
            System.out.println("7. Portfolio Performance");
            System.out.println("8. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n--- MARKET DATA ---");

                    for (Stock s : stocks) {
                        s.display();
                    }
                    break;

                case 2:
                    System.out.print("Enter stock name: ");
                    String buyName = sc.next().toUpperCase();

                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();

                    Stock buyStock = null;

                    for (Stock s : stocks) {
                        if (s.name.equals(buyName)) {
                            buyStock = s;
                            break;
                        }
                    }

                    if (buyStock != null && buyQty > 0) {

                        double total = buyStock.price * buyQty;

                        if (user.balance >= total) {

                            user.balance -= total;

                            portfolio.put(buyName,
                                portfolio.getOrDefault(buyName, 0) + buyQty);

                            transactions.add(new Transaction(
                                "BUY", buyName, buyQty, total));

                            System.out.println("Stock bought successfully!");

                        } else {
                            System.out.println("Insufficient balance!");
                        }

                    } else {
                        System.out.println("Invalid stock or quantity!");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock name: ");
                    String sellName = sc.next().toUpperCase();

                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();

                    Stock sellStock = null;

                    for (Stock s : stocks) {
                        if (s.name.equals(sellName)) {
                            sellStock = s;
                            break;
                        }
                    }

                    int owned = portfolio.getOrDefault(sellName, 0);

                    if (sellStock != null && sellQty > 0 && owned >= sellQty) {

                        double total = sellStock.price * sellQty;

                        user.balance += total;

                        if (owned == sellQty) {
                            portfolio.remove(sellName);
                        } else {
                            portfolio.put(sellName, owned - sellQty);
                        }

                        transactions.add(new Transaction(
                            "SELL", sellName, sellQty, total));

                        System.out.println("Stock sold successfully!");

                    } else {
                        System.out.println("You don't have enough shares!");
                    }
                    break;

                case 4:
                    System.out.println("\n--- MY PORTFOLIO ---");

                    if (portfolio.isEmpty()) {
                        System.out.println("No stocks purchased.");
                    } else {
                        for (String name : portfolio.keySet()) {
                            System.out.println(name + " : " +
                                portfolio.get(name) + " shares");
                        }
                    }
                    break;

                case 5:
                    System.out.println("Available Balance: Rs." +
                        user.balance);
                    break;

                case 6:
                    System.out.println("\n--- TRANSACTION HISTORY ---");

                    if (transactions.isEmpty()) {
                        System.out.println("No transactions yet.");
                    }

                    for (Transaction t : transactions) {
                        t.display();
                    }
                    break;

                case 7:
                    double invested = 0;
                    double currentValue = 0;

                    for (String name : portfolio.keySet()) {
                        int qty = portfolio.get(name);

                        for (Stock s : stocks) {
                            if (s.name.equals(name)) {
                                currentValue += qty * s.price;
                                break;
                            }
                        }
                    }

                    double totalValue = user.balance + currentValue;

                    performance.add(totalValue);

                    System.out.println("\n--- PORTFOLIO PERFORMANCE ---");
                    System.out.println("Cash Balance: Rs." + user.balance);
                    System.out.println("Stock Value: Rs." + currentValue);
                    System.out.println("Total Portfolio Value: Rs." + totalValue);

                    System.out.println("Performance History:");

                    for (int i = 0; i < performance.size(); i++) {
                        System.out.println("Record " + (i + 1) +
                            " : Rs." + performance.get(i));
                    }
                    break;

                case 8:
                    System.out.println("Thank you for using Stock Trading System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 8);

        sc.close();
    }
}
