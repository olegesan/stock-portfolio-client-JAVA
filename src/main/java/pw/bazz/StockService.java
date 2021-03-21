package pw.bazz;

import yahoofinance.YahooFinance;

import java.math.BigDecimal;

public class StockService {
    public static BigDecimal getPrice(String symbol) {
        BigDecimal price = BigDecimal.valueOf(0);
        try{

            price = YahooFinance.get(symbol).getQuote().getPrice();
        }catch(Exception err){
            System.out.println(err.getMessage());
        }
        return price;
    }
}
