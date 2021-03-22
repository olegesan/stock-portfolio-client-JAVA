package pw.bazz;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pw.bazz.Model.Portfolio;
import pw.bazz.Model.Stock;

import java.io.File;
import java.util.Arrays;

public class PortfolioTest {
    public static Portfolio pf;
    public static Stock gme;
    public static Stock aapl;
    public static Stock msft;

    public void cleanUp() {
        File f = new File("bazz.txt");
        try {
            FileUtils.writeStringToFile(f, "GME:0,MSFT:5,YNDX:20,AAPL:2,", "UTF-8");
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    @Before
    public void setUp() {
        pf = new Portfolio();
        gme = new Stock("GME", 20);
        aapl = new Stock("AAPL", 1);
        msft = new Stock("MSFT", 0);
        pf.addStock(gme);
        pf.addBunchStock(Arrays.asList(aapl, msft));

    }

    @Test
    public void addingStockToPortfolioTest() {
        Assert.assertEquals("Checking number of stocks in portfolio: ", 3, pf.getStocks().size());
    }

    @Test
    public void checkingStockIsInPortfolio() {
        Assert.assertEquals("Checking if GME is in stocks", gme, pf.getStock("GME"));
    }

    @Test
    public void removingStockFromPortfolioTest() {
        pf.removeStock("GME");
        Assert.assertNull("Checking if GME was removed from portfolio", pf.getStock("GME"));
    }

    @Test
    public void removingNonExistingStockFromPortfolioTest() {
        pf.removeStock("PPE");
        Assert.assertEquals("Checking if PPE was removed from portfolio", 3, pf.getStocks().size());
    }

    @Test
    public void testPortfolioWorth() {
        Assert.assertTrue("Checking worth to be ", pf.getCalculatedWorth() > 0);
    }

    @Test
    public void testUpdateShares() {
        pf.setShares("MSFT", 10);
        Assert.assertEquals("Checking updated amount of shares: ", 10, pf.getShares("MSFT"));
    }

    @Test
    public void testUpdateWorth() {
        double worth = pf.getCalculatedWorth();
        System.out.println(worth);
        pf.setShares("MSFT", 10);
        Assert.assertEquals("Checking updated worth of portfolio: ", worth + (pf.getStock("MSFT").getPrice() * 10), pf.getCalculatedWorth(), 2);
    }

    @Test
    public void updateNoNStockPriceTest() {
        double worth = pf.getCalculatedWorth();
        pf.updateStockPrice("PPE", 140);
        Assert.assertEquals("Testing updated worth ", worth, pf.getCalculatedWorth(), 2);
        Assert.assertNull("Testing update stock price", pf.getStock("PPE"));
    }

    @Test
    public void removeStockPortfolioWorthUpdateTest() {
        pf.removeStock("GME");
        Assert.assertEquals("Testing updated worth ", pf.getStock("AAPL").getWorth().doubleValue(), pf.getCalculatedWorth(), 2);
    }

    @Test
    public void comparingWorthToCalculatedWorth() {
        Assert.assertEquals("Testing calculated worth with fast worth ", pf.getCalculatedWorth(), pf.getCalculatedWorth(), 2);
    }

    @Test
    public void testLoadPortfolioFromFile() {
        pf = Portfolio.loadPortfolio("bazz");
        Assert.assertEquals("Loaded correct amount of stock for bazz test", 4, pf.getStocks().size());
    }

    @Test
    public void testLoadPortfolioStock() {
        pf = Portfolio.loadPortfolio("bazz");
        Assert.assertNotNull("Loaded correct stock", pf.getStock("YNDX"));
    }

    @Test
    public void testLoadPortfolioNonStock() {
        pf = Portfolio.loadPortfolio("bazz");
        Assert.assertNull("Did not load incorrect stock", pf.getStock("PPE"));
    }

    @Test
    public void testSaveToFile() {
        pf = Portfolio.loadPortfolio("bazz");
        pf.removeStock("GME");
        pf.savePortfolio("bazz");
        Portfolio newPf = Portfolio.loadPortfolio("bazz");
        Assert.assertEquals("Loaded correct number of stocks for update bazz test", 3, pf.getStocks().size());
        cleanUp();
    }




}
