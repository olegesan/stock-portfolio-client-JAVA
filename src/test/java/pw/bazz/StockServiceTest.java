package pw.bazz;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class StockServiceTest {
    @Test
    public void checkStockPrice(){
        BigDecimal price  = StockService.getPrice("GME");
        Assert.assertTrue("Checking it is fetching price",BigDecimal.valueOf(0).compareTo(price) < 0);
    }
}
