/**
 * Represents user's portfolio with stocks
 * @author Oleg Bazylnikov
 * @date 03/22/2021
 */
package pw.bazz.Model;

import java.io.File;
import java.math.BigDecimal;
import org.apache.commons.io.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private HashMap<String,Stock> stocks;
    private BigDecimal worth;

    public Portfolio(){
        stocks = new HashMap<>();
        worth = BigDecimal.valueOf(0L);
    }

    /**
     * loads portfolio from file
     * @param pfName of the file with portfolio. Supposed to be the same as username
     * @return Portfolio object with stocks for the specified file
     */
    public static Portfolio loadPortfolio(String pfName) {
        File f = new File(pfName+".txt");
        Portfolio pf = new Portfolio();
        if(f.exists()){

        try{
           String pfString = FileUtils.readFileToString(f, "UTF-8");
           pf = parsePortfolio(pfString);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        }else{
            try{
                FileUtils.touch(f);
                FileUtils.writeStringToFile(f,"","UTF-8");
            }
            catch(Exception err){
                System.out.println(err.getMessage());
            }
        }
        return pf;
    }

    /**
     * Parses string from <portfolio.txt> file into a Portfolio object
     * it is supposed to be "TICKER:AMOUNT_OF_SHARES"
     * @param pfString string from portfolio file.
     * @return Portfolio object
     */
    private static Portfolio parsePortfolio(String pfString){
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
        stocks.put(symbol,stock );
        /*
        updates worth. Should be deprecated and not used in the future
         */
        BigDecimal newWorth = worth.add(BigDecimal.valueOf(stock.getSahres()*stock.getPrice()));
        setWorth(newWorth);
    }

    /**
     * Adds multiple stock at a time
     * @param stocks a list with stocks
     */
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

    /**
     * Calculates worth in BigDecimal
     * Should not be used, will be removed in the future
     * @param newValue that is added to the worth, i.e. new stock worth
     * @param oldValue that is removed from the worth, i.e. old stock worth
     * @return the worth after updates.
     */
    private BigDecimal calculateWorth(BigDecimal newValue, BigDecimal oldValue){
        BigDecimal newWorth = worth.add(oldValue.negate());
        newWorth = newWorth.add(newValue);
        return newWorth;
    }

    /**
     * updates amount of shares for a given stock
     * if stock not in the portfolio, no data will be changed
     * @param symbol
     * @param amount
     */
    public void setShares(String symbol, long amount) {
        Stock stock = this.getStock(symbol);
        if(stock != null){
            setWorth(calculateWorth(BigDecimal.valueOf(amount*stock.getPrice()), stock.getWorth()));
            stock.setShares(amount);
        }
    }

    /**
     * Provides the number of shares in the portfolio for the given Symbol
     * @param symbol of the stock that needs to be loocked up
     * @return long number of shares if any
     */
    public long getShares(String symbol) {
        Stock stock = this.getStock(symbol);
        if(stock== null){
            return 0;
        }
        return stock.getSahres();
    }

    /**
     * updates price of the stock based on provided values
     * DEPRECATED and should not be used.
     * Stocks update their price automatically now within stock object using real data
     * @param symbol of the stock
     * @param newPrice to which price needs to be updated
     */
    public void updateStockPrice(String symbol, int newPrice) {
        Stock stock = this.getStock(symbol);
        BigDecimal nPrice = BigDecimal.valueOf(newPrice);
        if(stock != null){
            setWorth(calculateWorth(nPrice.multiply(BigDecimal.valueOf(stock.getSahres())), stock.getWorth()));
            stock.setPrice(newPrice);
        }
    }

    /**
     * Calculates the worth of the portfolio based on the worth of each stock in the portfolio
     * all prices are up to date because of yahoo finance library backing up the stock price
     * @return double value of the entire portfolio
     */
    public double getCalculatedWorth(){
        BigDecimal returnWorth = BigDecimal.valueOf(0);
        for(Map.Entry<String, Stock> pair: stocks.entrySet()){
            returnWorth = returnWorth.add(pair.getValue().getWorth());
        }
        return returnWorth.doubleValue();
    }

    /**
     * updates prices for each stock in the portfolio.
     * For better results should be called before @method getCalculatedWorth
     */
    public void refreshPortfolio(){
        for(Map.Entry<String, Stock> pair: stocks.entrySet()){
            pair.getValue().fetchPrice();
        }
    }

    /**
     * saves portfolio to a file with the provided name
     * username should be used as a name to save portfolio.
     * @param pfName
     */
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
