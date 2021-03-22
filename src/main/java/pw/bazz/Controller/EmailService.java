package pw.bazz.Controller;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.apache.commons.io.FileUtils;
import pw.bazz.Model.Portfolio;
import pw.bazz.Model.Stock;
import pw.bazz.Model.User;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class EmailService {


    public static int sendEmail(String emailTo, String body) {
        Email from = new Email("obazylnikov@ggc.edu");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email(emailTo);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        System.out.println(System.getenv("SENDGRID_API_KEY"));
        System.out.println(System.getenv());
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            return response.getStatusCode();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return 400;
        }

    }

    public static String composeBody(String username, Portfolio pf) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(username).append(".\n");
        sb.append("This is an automated email sent to you with your stock portfolio.\n");
        Date now = new Date();
        sb.append("Current time: ").append(now).append("\n");
        sb.append("Current portfolio worth: ").append(pf.getCalculatedWorth()).append("\n");
        for (Map.Entry<String, Stock> pair : pf.getStocks().entrySet()) {
            Stock stock = pair.getValue();
            sb.append("Ticker: " + stock.getTicker()).append(" | One Share price: " + stock.getPrice())
                    .append(" | Shares Owned: " + stock.getSahres()).append(" | Total Worth: " + stock.getWorth()).append("\n");
        }
        return sb.toString();
    }

    public static boolean emailAlerts() {
        List<User> users = AlertManager.loadAlertUsers();
        try {
            for(User user : users){
                sendEmail(user.getEmail(), composeBody(user.getName(),user.getPortfolio()));
            }
            return true;
        } catch (Exception err) {
            System.out.println(EmailService.class);
            System.out.println(err.getMessage());
            return false;
        }

    }
}
