package pw.bazz.Model;

import java.io.File;
import java.math.BigDecimal;
import org.apache.commons.io.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
//    private List<String> stocksSymbols;
    private HashMap<String,Stock> stocks;
    private BigDecimal worth;

    public Portfolio(){
//        stocksSymbols = new ArrayList<>();
        stocks = new HashMap<>();
        worth = BigDecimal.valueOf(0L);
    }

    public static Portfolio loadPortfolio(String pfName) {
        File f = new File(pfName+".txt");
        Portfolio pf = new Portfolio();
        try{
           String pfString = FileUtils.readFileToString(f, "UTF-8");
           pf = parsePortfolio(pfString);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return pf;
    }

    public static Portfolio parsePortfolio(String pfString){
        Portfolio pf = new Portfolio();
        String[] pairs = pfString.split(",");
        for(String pair: pairs){
            String[] symbol_shares = pair.split(":");
            String symbol = symbol_shares[0];
            long shares = Long.parseLong(symbol_shares[1]);
            Stock stock = new Stock(symbol, shares);
            pf.addStock(stock);
        }
        return pf;
    }


    public HashMap<String, Stock> getStocks() {
        return stocks;
    }

    public void setStocks(HashMap<String, Stock> stocks) {
        this.stocks = stocks;
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
//        stocksSymbols.add(symbol);
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
        Stock stock =stocks.get(symbol);
        if( stock != null){
            setWorth(calculateWorth(BigDecimal.valueOf(0),stock.getWorth()));
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
            setWorth(calculateWorth(BigDecimal.valueOf(amount*stock.getPrice()), stock.getWorth()));
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

    public void updateStockPrice(String symbol, int newPrice) {
        Stock stock = this.getStock(symbol);
        BigDecimal nPrice = BigDecimal.valueOf(newPrice);
        if(stock != null){
            setWorth(calculateWorth(nPrice.multiply(BigDecimal.valueOf(stock.getSahres())), stock.getWorth()));
            stock.setPrice(newPrice);
        }
    }
    public double getCalculatedWorth(){
        BigDecimal returnWorth = BigDecimal.valueOf(0);
        for(Map.Entry<String, Stock> pair: stocks.entrySet()){
            returnWorth = returnWorth.add(pair.getValue().getWorth());
        }
        return returnWorth.doubleValue();
    }

    public void refreshPortfolio(){
        for(Map.Entry<String, Stock> pair: stocks.entrySet()){
            pair.getValue().fetchPrice();
        }
    }

    public void savePortfolio(String pfName) {
        StringBuilder pfString = new StringBuilder();

        File f = new File(pfName+".txt");
        for(Map.Entry<String, Stock> pair: stocks.entrySet()){
            String symbol = pair.getKey();
            String shares = String.valueOf(pair.getValue().getSahres());
            pfString.append(String.format("%s:%s,",symbol,shares));
        }
        try{

        FileUtils.writeStringToFile(f,pfString.toString(),"UTF-8");
            System.out.println("Saved Successfully");
        }
        catch(Exception err){
            System.out.println("in save file");
            System.out.println(err.getMessage());
        }
    }
}
