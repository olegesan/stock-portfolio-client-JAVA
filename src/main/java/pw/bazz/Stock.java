package pw.bazz;

import java.math.BigDecimal;

public class Stock {
    private String ticker;
    private BigDecimal price;

    public Stock(String ticker, double price) {
        this.ticker = ticker;
        this.price = BigDecimal.valueOf(price);
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
}
