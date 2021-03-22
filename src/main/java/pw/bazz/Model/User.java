/**
 * Represents user with a portfolio
 * @author Oleg Bazylnikov
 * @date 03/22/2021
 */
package pw.bazz.Model;

import org.apache.commons.io.FileUtils;

import java.io.File;
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

    /**
     * creates a user with an empty portfolio
     * uses scanner to get username and email
     * not validates any data
     * @return User object with empty portfolio
     */
    public static User createEmptyUser(){
        System.out.println("Enter username: ");
        String username = input.nextLine();
        System.out.println("Enter email (I won't send you spam, but only portfolio alerts): ");
        String email = input.nextLine();
        return new User(email, username);
    }

    /**
     * creates user with portfolio that is loaded from the file that matches user's username
     * @return User object with portfolio from matching file
     */
    public static User createUser(){
        User user = createEmptyUser();
        String pfName = user.getName();
        Portfolio pf = Portfolio.loadPortfolio(pfName);
        user.setPortfolio(pf);
        saveUser(user);
        return user;
    }

    /**
     * creates user with provided credentials and loads appropriate file
     * @param name username
     * @param email user's email
     * @return user object with appropriate portfolio
     */
    public static User createUser(String name, String email){
        User user = new User(email, name);
        String pfName = user.getName();
        Portfolio pf = Portfolio.loadPortfolio(pfName);
        user.setPortfolio(pf);
        saveUser(user);
        return user;
    }

    /**
     * Loads user based
     * asks for username and loads user from "<USERNAME>_user.txt" with appropriate portfolio
     * @return
     */
    public static User loadUser(){
        System.out.println("Enter user name: ");
        String username = input.nextLine();
        File f = new File(username+"_user.txt");
        User user = null;
        if(!f.exists()){
            System.out.println("User with such name "+username+" does not exist.");
            return user;
        }
        try{
            String userString = FileUtils.readFileToString(f,"UTF-8");
            String email = userString.split(":")[1];
            user = new User( email, username);

        }catch(Exception err){
            System.out.println(err.getMessage());
        }
        Portfolio pf = Portfolio.loadPortfolio(username);
        user.setPortfolio(pf);
        return user;
    }

    /**
     * saves user info in *usernaem*_user.txt file
     * @param user that needs to be saved
     */
    public static void saveUser(User user){
        File f = new File(user.getName()+"_user.txt");
        try{
            FileUtils.writeStringToFile(f,user.getName()+":"+user.getEmail(),"UTF-8");
        }
        catch(Exception err){
            System.out.println(err.getMessage());
        }
        user.getPortfolio().savePortfolio(user.getName());
    }
    public static User loadUser(String username){
        File f = new File(username+"_user.txt");
        User user = null;
        if(!f.exists()){
            System.out.println("User with such name "+username+" does not exist.");
            return user;
        }
        try{
            String userString = FileUtils.readFileToString(f,"UTF-8");
            String email = userString.split(":")[1];
            user = new User(email, username);
            Portfolio pf = Portfolio.loadPortfolio(username);
            user.setPortfolio(pf);

        }catch(Exception err){
            System.out.println(err.getMessage());
        }

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
