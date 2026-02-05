import java.util.Scanner;

public class AuctSKMotors {

    public static void main(String[] args) {
    
                Scanner input = new Scanner(System.in);

        System.out.print("Enter vehicle registration number (e.g. UAB 123X): ");
        String regNumber = input.nextLine();

        System.out.print("Enter vehicle cost in UGX: ");
        double cost = input.nextDouble();

        System.out.print("Enter any outstanding balance on cost in UGX: ");
        double balance = input.nextDouble();
        input.nextLine();  

        
        String[] names = new String[3];
        double[] bids = new double[3];
        double maxBid = -1;
        String winner = "";

        System.out.println("\nEnter 3 bidders:");
        for (int i = 0; i < 3; i++) {
            System.out.print("Bidder " + (i+1) + " name: ");
            names[i] = input.nextLine();

            System.out.print("Bidder " + (i+1) + " bid (UGX): ");
            bids[i] = input.nextDouble();
            input.nextLine();  

            if (bids[i] > maxBid) {
                maxBid = bids[i];
                winner = names[i];
            }
        }

        
        System.out.print("\nEnter total expenses (UGX, e.g. repairs/transport): ");
        double expenses = input.nextDouble();

        
        double net = maxBid - cost - expenses - balance;

        
        System.out.println("\n=== SK. Motors Auction Result ===");
        System.out.println("Vehicle: " + regNumber);
        System.out.println("Cost: " + cost + " UGX");
        System.out.println("Balance owed: " + balance + " UGX");
        System.out.println("Expenses: " + expenses + " UGX");

        System.out.println("\nBids:");
        for (int i = 0; i < 3; i++) {
            System.out.println("  " + names[i] + ": " + bids[i] + " UGX");
        }

        System.out.println("\nWinner: " + winner + " (highest bid: " + maxBid + " UGX)");

        if (net >= 0) {
            System.out.println("Profit: " + net + " UGX");
        } else {
            System.out.println("Loss: " + (-net) + " UGX");
        }

        input.close();
    }
    
}
