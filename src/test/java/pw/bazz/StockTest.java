package pw.bazz;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StockTest {

    public static Stock gme;
    @BeforeClass
    public static void setUpStock(){
        gme = new Stock("GME", 250.99);
    }
    @Test
    public void stockPriceTest(){
        Assert.assertEquals("Testing price to equal actual price: ", 250.99, gme.getPrice(), 2);
    }

    @Test
    public void stockTickerTest(){
        Assert.assertEquals("Testing price to equal actual price: ", "GME", gme.getTicker());
    }
}
