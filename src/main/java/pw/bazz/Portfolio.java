package pw.bazz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Portfolio {
    private List<String> stocksSymbols;
    private HashMap<String,Stock> stocks;
    private BigDecimal worth;

    public Portfolio(){
        stocksSymbols = new ArrayList<>();
        stocks = new HashMap<>();
        worth = BigDecimal.valueOf(0L);
    }


    public List<String> getStocksSymbols() {
        return stocksSymbols;
    }

    public void setStocksSymbols(List<String> stocksSymbols) {
        this.stocksSymbols = stocksSymbols;
    }

    public HashMap<String, Stock> getStocks() {
        return stocks;
    }

    public void setStocks(HashMap<String, Stock> stocks) {
        this.stocks = stocks;
    }

    public double getWorth() {
        return worth.doubleValue();
    }

    public void setWorth(BigDecimal worth) {
        this.worth = worth;
    }

    public Stock getStock(String symbol){
        Stock returnStock = stocks.get(symbol);
        if(returnStock == null){
            System.out.printf("No Stock with Ticker %s Found in Portfolio\n", symbol);
        }
        return returnStock;
    }
    public void addStock(Stock stock) {
        String symbol = stock.getTicker();
        stocksSymbols.add(symbol);
        stocks.put(symbol,stock );
        BigDecimal newWorth = worth.add(BigDecimal.valueOf(stock.getSahres()*stock.getPrice()));
        setWorth(newWorth);
    }

    public void addBunchStock(List<Stock> stocks) {
        for(Stock stock : stocks){
            this.addStock(stock);
        }
    }

    public void removeStock(String symbol) {
        if(stocks.get(symbol) != null){
            stocks.remove(symbol);
            return;
        }
        System.out.printf("No Stock with Ticker %s Found in Portfolio\nCannot Remove It\n", symbol);

    }

    private BigDecimal calculateWorth(BigDecimal newValue, BigDecimal oldValue){
        BigDecimal newWorth = worth.add(oldValue.negate());
        newWorth = newWorth.add(newValue);
        return newWorth;
    }

    public void setShares(String symbol, long amount) {
        Stock stock = this.getStock(symbol);
        if(stock != null){
            setWorth(calculateWorth(BigDecimal.valueOf(amount*stock.getPrice()), BigDecimal.valueOf(stock.getSahres()*stock.getPrice())));
            stock.setShares(amount);
        }
    }

    public long getShares(String symbol) {
        Stock stock = this.getStock(symbol);
        if(stock== null){
            return 0;
        }
        return stock.getSahres();
    }
}
