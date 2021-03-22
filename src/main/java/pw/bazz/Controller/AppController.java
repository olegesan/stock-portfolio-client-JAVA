package pw.bazz.Controller;

import pw.bazz.Model.Stock;
import pw.bazz.Model.User;
import pw.bazz.View.StockClientUI;

import java.util.Arrays;
import java.util.Scanner;

public class AppController {
    private static StockClientUI ui = new StockClientUI();
    private static String state;
    private static User user;
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        if(args[1].equals("server")){
            serverRun();
        }
        else{
            clientRun();
        }

    }
    public static void clientRun(){
        state = "main";
        while(!state.equals("exit")){
            switch(state){
                case "main":
                    ui.displayMainMenu();
                    break;
                case "portfolio":
                    ui.displayPortfolioMenu(user);
                    break;
            }

            handleAction();
        }
    }
    public static void handleAction(){
        String userInput = input.nextLine();
        switch(state){
            case "main":
                if(userInput.equals("1")){
                    user = User.createUser();
                    state = "portfolio";
                }
                else if(userInput.equals("2")){
                    user = User.loadUser();
                    state = "portfolio";
                }else if(userInput.equals("3")){
                    exit();
                    return;
                }
                break;
            case "portfolio":
                switch(userInput){
                    case "1":
                        ui.displayPortfolio(user.getPortfolio());
                        break;
                    case "2":
                        Stock stock = ui.addStockDialog();
                        user.getPortfolio().addStock(stock);
                        break;
                    case "3":
                        String ticker = ui.getTickerDialog();
                        user.getPortfolio().removeStock(ticker);
                        break;
                    case "4":
                        Stock stockUpdate = ui.addStockDialog();
                        user.getPortfolio().setShares(stockUpdate.getTicker(), stockUpdate.getSahres());
                        break;
                    case "5":
                        AlertManager.addUser(user);
                        ui.displayAlarmOn(user.getEmail());
                        break;
                    case "6":
                        AlertManager.removeUser(user);
                        ui.displayAlarmOff(user.getEmail());
                        break;
                    case "7":
                        user.getPortfolio().savePortfolio(user.getName());
                        break;
                    case "8":
                        state = "main";
                        break;
                }

        }
    }

    public static void exit(){
        ui.displayExit();
        state = "exit";
    }

    public static void serverRun(){
        EmailService.emailAlerts();
    }

}
