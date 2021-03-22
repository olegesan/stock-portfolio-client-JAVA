package pw.bazz.View;

import pw.bazz.Model.Portfolio;
import pw.bazz.Model.Stock;
import pw.bazz.Model.User;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class StockClientUI {
    private Scanner input = new Scanner(System.in);
    public void displayMainMenu(){
        System.out.println("#####################################");
        System.out.println("Welcome to Stock Portfolio Shell App");
        System.out.println("Select What You Want to Do (enter digit): ");
        System.out.println("1. Create User");
        System.out.println("2. Select User");
        System.out.println("3. Exit");
        System.out.println();
    }
    public void displayPortfolioMenu(User user){
        System.out.printf("Username: %s Email: %s\n", user.getName(), user.getEmail());
        System.out.println("Portfolio Menu:");
        System.out.println("1. View portfolio");
        System.out.println("2. Add stocks");
        System.out.println("3. Remove stocks");
        System.out.println("4. Update stocks");
        System.out.println("5. Turn on alarm");
        System.out.println("6. Turn off alarm");
        System.out.println("7. Save");
        System.out.println("8. Back");
    }
    public void displayAlarmOn(String email){
        System.out.println("Alarm to "+email+" turned on successfully");
    }
    public void displayAlarmOff(String email){
        System.out.println("Alarm to "+email+" turned off successfully");
    }
    public void displayPortfolio(Portfolio pf){
        System.out.println("Loading...");
        pf.refreshPortfolio();
        StringBuilder sb = new StringBuilder();
        Date now = new Date();
        sb.append("Current time: ").append(now).append("\n");
        sb.append("Current portfolio worth: ").append(pf.getCalculatedWorth()).append("\n");
        for(Map.Entry<String, Stock> pair: pf.getStocks().entrySet()){
            Stock stock = pair.getValue();
            sb.append("Ticker: "+ stock.getTicker()).append(" | One Share price: "+stock.getPrice())
                    .append(" | Shares Owned: "+stock.getSahres()).append(" | Total Worth: "+ stock.getWorth()).append("\n");
        }
        System.out.println(sb.toString());
    }
    public void displayExit(){
        System.out.println("See Ya Later");
        System.out.println("#####################################");
    }

    public Stock addStockDialog(){
        System.out.println("Enter Ticker: ");
        String ticker = input.nextLine();
        System.out.println("Enter Amount of Shares: ");
        long shares = Long.parseLong(input.nextLine());
        return new Stock(ticker,shares);
    }

    public String getTickerDialog() {
        System.out.println("Enter Stock Ticker: ");
        String ticker = input.nextLine();
        return ticker;
    }
}
