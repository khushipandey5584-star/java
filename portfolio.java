import java.util.Scanner;

// 1. Stock class to hold data
class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    // Changes the stock price slightly up or down
    public void changePrice() {
        double change = (Math.random() * 10) - 5; // random number between -5 and +5
        this.price = this.price + (this.price * change / 100);
    }
}

// 2. Portfolio class to track your cash and shares
class Portfolio {
    double cash = 5000.0;
    int appleShares = 0;
    int teslaShares = 0;

    public void display() {
        System.out.println("\n--- Your Portfolio ---");
        System.out.println("Cash Available: $" + Math.round(cash));
        System.out.println("Apple (AAPL) Shares: " + appleShares);
        System.out.println("Tesla (TSLA) Shares: " + teslaShares);
    }
}

// 3. Main Engine to run the program loop
public class pp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Portfolio myPortfolio = new Portfolio();

        // Create 2 stocks using an Array
        Stock[] market = {
            new Stock("AAPL", 150.0),
            new Stock("TSLA", 700.0)
        };

        while (true) {
            // Update stock prices automatically
            market[0].changePrice();
            market[1].changePrice();

            System.out.println("\n=== LIVE MARKET ===");
            System.out.println("1. " + market[0].symbol + " Price: $" + Math.round(market[0].price));
            System.out.println("2. " + market[1].symbol + " Price: $" + Math.round(market[1].price));
            System.out.println("3. View My Portfolio");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");
            
            int choice = scanner.nextInt();

            if (choice == 4) {
                System.out.println("Exiting Simulator. Goodbye!");
                break;
            }

            if (choice == 3) {
                myPortfolio.display();
                continue;
            }

            // Buy or Sell Logic for Option 1 (Apple) or Option 2 (Tesla)
            if (choice == 1 || choice == 2) {
                Stock selectedStock = market[choice - 1];
                
                System.out.print("1. Buy  2. Sell: ");
                int action = scanner.nextInt();
                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();

                double totalCost = selectedStock.price * qty;

                if (action == 1) { // BUY
                    if (myPortfolio.cash >= totalCost) {
                        myPortfolio.cash -= totalCost;
                        if (selectedStock.symbol.equals("AAPL")) myPortfolio.appleShares += qty;
                        if (selectedStock.symbol.equals("TSLA")) myPortfolio.teslaShares += qty;
                        System.out.println("Success! Bought " + qty + " shares.");
                    } else {
                        System.out.println("Not enough cash!");
                    }
                } 
                else if (action == 2) { // SELL
                    boolean hasEnoughShares = false;
                    if (selectedStock.symbol.equals("AAPL") && myPortfolio.appleShares >= qty) {
                        myPortfolio.appleShares -= qty;
                        hasEnoughShares = true;
                    }
                    if (selectedStock.symbol.equals("TSLA") && myPortfolio.teslaShares >= qty) {
                        myPortfolio.teslaShares -= qty;
                        hasEnoughShares = true;
                    }

                    if (hasEnoughShares) {
                        myPortfolio.cash += totalCost;
                        System.out.println("Success! Sold " + qty + " shares.");
                    } else {
                        System.out.println("You don't own enough shares to sell!");
                    }
                }
            }
        }
        scanner.close();
    }
}
