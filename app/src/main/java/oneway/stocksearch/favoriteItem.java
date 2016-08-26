package oneway.stocksearch;

/**
 * Created by Oneway on 16/4/10.
 */
public class favoriteItem {
    String symbol,value,change,name,marketCap;

    public favoriteItem(String symbol, String value, String change, String name, String marketCap) {
        this.symbol = symbol;
        this.value = value;
        this.change = change;
        this.name = name;
        this.marketCap = marketCap;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }
}
