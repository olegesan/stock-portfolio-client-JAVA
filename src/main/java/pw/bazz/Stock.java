package pw.bazz;

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

    public void fetchPrice(){
        setPrice(StockService.getPrice(ticker).doubleValue());
    }

    public long getSahres() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    public BigDecimal getWorth(){
        return price.multiply(BigDecimal.valueOf(shares));
    }


}
