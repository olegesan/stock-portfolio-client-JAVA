package pw.bazz;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pw.bazz.Model.Stock;

public class StockTest {

    public static Stock gme;
    @Before
    public  void setUpStock(){
        gme = new Stock("GME", 250.99);
    }
    @Test
    public void stockPriceTest(){
        Assert.assertEquals("Testing price to equal actual price: ", 250.99, gme.getPrice(), 2);
    }

    @Test
    public void stockTickerTest(){
        Assert.assertEquals("Testing ticker to equal actual ticker: ", "GME", gme.getTicker());
    }

    @Test
    public void updateStockPriceTest(){
        gme.setPrice(360.01);
        Assert.assertEquals("Testing price to equal new price: ", 360.01, gme.getPrice(), 2);
    }

    public void stockSharesTest(){
        Assert.assertEquals("Testing amount of shares to equal actual amount of shares: ", 0, gme.getSahres());
    }
    public void stockUpdatedSharesTest(){
        gme.setShares(50L);
        Assert.assertEquals("Testing amount of shares to equal updated amount of shares: ", 50L, gme.getSahres());
    }
}
