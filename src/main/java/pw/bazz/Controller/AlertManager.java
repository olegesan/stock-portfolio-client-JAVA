package pw.bazz.Controller;

import org.apache.commons.io.FileUtils;
import pw.bazz.Model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlertManager {
    public static void addUser(User user){
        File f = new File("alerts.txt");
        try{
            String fString = FileUtils.readFileToString(f, "UTF-8");
            if(fString.contains(user.getEmail())){
                System.out.println("Alerts are already on");
                return;
            }
            fString+=user.getEmail()+":"+user.getName()+",";
            FileUtils.writeStringToFile(f, fString, "UTF-8");
        }catch(Exception err){
            System.out.println(err.getMessage());
        }
    }
    public static void removeUser(User user){
        File f = new File("alerts.txt");
        try{
            String fString = FileUtils.readFileToString(f, "UTF-8");
            if(fString.contains(user.getEmail())){
                fString = fString.replace(user.getEmail()+":"+user.getName()+",", "");
            }
            FileUtils.writeStringToFile(f, fString, "UTF-8");
        }catch(Exception err){
            System.out.println(err.getMessage());
        }
    }
    public static List<User> loadAlertUsers(){
        File f = new File("alerts.txt");
        List<User> users = new ArrayList<>();
        try{
            String fString = FileUtils.readFileToString(f, "UTF-8");
            String[] usersRaw = fString.split(",");
            for(String userPair : usersRaw){
                String username = userPair.split(":")[1];
                users.add(User.loadUser(username));
            }
            System.out.println(users.size());
        }catch(Exception err){
            System.out.println(err.getMessage());
        }
        return users;
    }
}
