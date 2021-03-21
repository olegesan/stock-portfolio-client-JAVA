package pw.bazz;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}
