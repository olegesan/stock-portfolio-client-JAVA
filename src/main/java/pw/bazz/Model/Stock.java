/**
 * Represents stock in a portfolio
 * @author Oleg Bazylnikov
 * @date 03/22/2021
 */
package pw.bazz.Model;

import pw.bazz.Controller.StockService;

import java.math.BigDecimal;

public class Stock {
    private String ticker;
    private BigDecimal price;
    private long shares;

    public Stock(String ticker, double price, long shares) {
        this.ticker = ticker;
        this.price = BigDecimal.valueOf(price);
        this.shares = shares;
    }
    public Stock(String ticker, double price) {
        this.ticker = ticker;
        this.price = BigDecimal.valueOf(price);
        this.shares = 0L;
    }

    /**
     * Provided ticker and number of shares it fetches the price of the stock automatically.
     * Use this constructor.
     * @param ticker of the stock
     * @param shares long number of shares
     */
    public Stock(String ticker, long shares){
        this.ticker = ticker;
        this.shares = shares;
        fetchPrice();
    }

    public double getPrice() {
        return price.doubleValue();
    }

    public String getTicker() {
        return ticker;
    }

    public void setPrice(double newPrice) {
        price = BigDecimal.valueOf(newPrice);
    }

    /**
     * using yahoo finance library updates the price of the current stock
     */
    public void fetchPrice(){
        setPrice(StockService.getPrice(ticker).doubleValue());
    }

    public long getSahres() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    /**
     * provides worth which is "number of shares"*"price per share"
     * @return BigDecimal worth of the shares of the given stock
     */
    public BigDecimal getWorth(){
        return price.multiply(BigDecimal.valueOf(shares));
    }


}
