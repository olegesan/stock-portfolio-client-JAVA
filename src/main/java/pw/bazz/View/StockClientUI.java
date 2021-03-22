/**
 * CLI component that display data to the client and takes input
 * BEWARE: no data validation is done
 * @author Oleg Bazylnikov
 * @date 03/22/2021
 */
package pw.bazz.View;

import pw.bazz.Model.Portfolio;
import pw.bazz.Model.Stock;
import pw.bazz.Model.User;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class StockClientUI {
    private Scanner input = new Scanner(System.in);

    /**
     * displays main menu
     * TODO: add a method to display welcome and main menu separately
     */
    public void displayMainMenu(){
        System.out.println("#####################################");
        System.out.println("Welcome to Stock Portfolio Shell App");
        System.out.println("Select What You Want to Do (enter digit): ");
        System.out.println("1. Create User");
        System.out.println("2. Select User");
        System.out.println("3. Exit");
        System.out.println();
    }

    /**
     * display option available in portfolio state once user is loaded
     * @param user current one
     */
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

    /**
     * when user turned on the alarm
     * @param email that the action was done one
     */
    public void displayAlarmOn(String email){
        System.out.println("Alarm to "+email+" turned on successfully");
    }
    /**
     * when user turned off the alarm
     * @param email that the action was done one
     */
    public void displayAlarmOff(String email){
        System.out.println("Alarm to "+email+" turned off successfully");
    }

    /**
     * displays portfolio infromation with current time and up to date prices
     * @param pf portfolio object of the user
     */
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

    /**
     * accepts symbol and number of shares from user with prompting help
     * TODO: data validation and error handling
     * @return stock object based on user input
     */
    public Stock addStockDialog(){
        System.out.println("Enter Ticker: ");
        String ticker = input.nextLine();
        System.out.println("Enter Amount of Shares: ");
        long shares = Long.parseLong(input.nextLine());
        return new Stock(ticker,shares);
    }

    /**
     * dialog to get a ticker form the user
     * TODO: data validation and error handling
     * @return String representing a ticker
     */
    public String getTickerDialog() {
        System.out.println("Enter Stock Ticker: ");
        String ticker = input.nextLine();
        return ticker;
    }
}
