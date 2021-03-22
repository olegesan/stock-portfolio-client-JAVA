/**
 * Helps to work with stock objects
 * @author Oleg Bazylnikov
 * @date 03/22/2021
 */
package pw.bazz.Controller;

import yahoofinance.YahooFinance;

import java.math.BigDecimal;

public class StockService {

    /**
     * Provides stock price based on yahoo finances data
     * @param symbol of the stock to lookup
     * @return BigDecimal price of one share or 0 in case of error.
     */
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
