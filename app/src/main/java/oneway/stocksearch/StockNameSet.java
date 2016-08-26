package oneway.stocksearch;

/**
 * Created by Oneway on 16/4/6.
 */
public class StockNameSet {
    String symbol,name;
    public StockNameSet(String symbol,String name){
        this.setSymbol(symbol);
        this.setName(name);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
