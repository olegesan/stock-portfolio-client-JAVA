package pw.bazz;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class PortfolioTest {
    public static Portfolio pf;
    public static Stock gme;
    public static Stock aapl;
    public static Stock msft;

    @Before
    public void setUp(){
        pf = new Portfolio();
        gme = new Stock("GME", 250.99, 20);
        aapl = new Stock("AAPL", 120, 1);
        msft = new Stock("MSFT", 290);
        pf.addStock(gme);
        pf.addBunchStock(Arrays.asList(aapl, msft));

    }

    @Test
    public void addingStockToPortfolioTest(){
        Assert.assertEquals("Checking number of stocks in portfolio: ", 3, pf.getStocks().size());
    }
    @Test
    public void checkingStockIsInPortfolio(){
        Assert.assertEquals("Checking if GME is in stocks",gme, pf.getStock("GME"));
    }

    @Test public void removingStockFromPortfolioTest(){
        pf.removeStock("GME");
        Assert.assertNull("Checking if GME was removed from portfolio", pf.getStock("GME"));
    }
    @Test public void removingNonExistingStockFromPortfolioTest(){
        pf.removeStock("PPE");
        Assert.assertEquals("Checking if PPE was removed from portfolio",3, pf.getStocks().size());
    }

    @Test
    public void testPortfolioWorth(){
        Assert.assertEquals("Checking worth to be ",5139.8, pf.getWorth(),2);
    }

    @Test
    public void testUpdateShares(){
        pf.setShares("MSFT", 10);
        Assert.assertEquals("Checking updated amount of shares: ", 10, pf.getShares("MSFT"));
    }
    @Test
    public void testUpdateWorth(){
        pf.setShares("MSFT", 10);
        Assert.assertEquals("Checking updated worth of portfolio: ",8039.8 , pf.getWorth(), 2);
    }

}
