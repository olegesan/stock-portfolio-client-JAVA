package pw.bazz;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pw.bazz.Model.Portfolio;
import pw.bazz.Model.Stock;
import pw.bazz.Model.User;

import java.io.File;
import java.util.Arrays;

public class UserTest {
    public static User u;
    public static Portfolio pf;
    public static Stock gme;
    public static Stock aapl;
    public static Stock msft;

    @Before
    public void setUp(){
        u = new User();
        pf = new Portfolio();
        gme = new Stock("GME", 250.99, 20);
        aapl = new Stock("AAPL", 120, 1);
        msft = new Stock("MSFT", 290);
        pf.addStock(gme);
        pf.addBunchStock(Arrays.asList(aapl, msft));
        u.setPortfolio(pf);

    }

    @Test
    public void testUserHasPortfolio(){
        Assert.assertNotNull("Checking user portfolio exists", u.getPortfolio());
    }

    @Test
    public void testUserHasStockInPortfolio(){
        Assert.assertEquals("Testing amount of stock user has ", 3, u.getPortfolio().getStocks().size());
    }
    @Test
    public void testLoadUser(){
        User newU = User.loadUser("bazz");
        Assert.assertNotNull("Testing to load user", newU);
        Assert.assertNotNull("Testing to load user and its portfolio", newU.getPortfolio());
        Assert.assertEquals("Testing portfolio of loaded user",4, newU.getPortfolio().getStocks().size());
    }
    @Test
    public void testCreateUserSave(){
        User newU = User.createUser("pie", "pie@ggc.edu");
        Assert.assertTrue("Checking if user file exists: ", new File("pie_user.txt").exists());
        Assert.assertTrue("Checking if user portfolio exists: ", new File("pie.txt").exists());
    }
}
