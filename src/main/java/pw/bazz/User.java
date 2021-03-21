package pw.bazz;

import java.util.Scanner;

public class User {
    private Portfolio portfolio;
    private String email;
    private String username;
    private static Scanner input = new Scanner(System.in);

    public User(Portfolio portfolio, String email, String username) {
        this.portfolio = portfolio;
        this.email = email;
        this.username = username;
    }

    public User() {
        portfolio = new Portfolio();
        email = "deafult Email";
        username = "default Name";
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public static User createEmptyUser(){
        System.out.println("Enter username: ");
        String username = input.nextLine();
        System.out.println("Enter email (I won't send you spam, but only portfolio alerts): ");
        String email = input.nextLine();
        return new User(email, username);
    }

    public static User createUser(){
        User user = createEmptyUser();
        System.out.println("Enter portfolio file name: ");
        String pfName = input.nextLine();
        Portfolio pf = Portfolio.loadPortfolio(pfName);
        user.setPortfolio(pf);
        return user;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return username ;
    }

    public void setName(String username) {
        this.username = username;
    }
}
