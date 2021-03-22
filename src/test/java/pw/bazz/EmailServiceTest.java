package pw.bazz;

import org.junit.Assert;
import org.junit.Test;
import pw.bazz.Controller.EmailService;
import pw.bazz.Model.Portfolio;


public class EmailServiceTest {
    @Test
    public void testSendEmail(){
        Assert.assertEquals("Checking Response code to be 202: ", 202, EmailService.sendEmail("olegesan@gmail.com","empty body"));
    }

    @Test
    public void testSendingPortfolioValue(){
        Portfolio pf = Portfolio.loadPortfolio("bazz");
        String body = EmailService.composeBody("bazz", pf);
        EmailService.sendEmail("olegesan@gmail.com", body);
    }

    @Test
    public void testEmailAlerts(){
        EmailService.emailAlerts();
    }


}
