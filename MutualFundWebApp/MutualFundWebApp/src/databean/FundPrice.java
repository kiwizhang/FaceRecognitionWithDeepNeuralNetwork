package databean;
import java.util.Date;

public class FundPrice {
    private int fundId;
    private String name;
    private String symbol;
    private Date priceDate;
    private long price;

    public int getFundId() {
        return fundId;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public long getPrice() {
        return price;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public void setPriceDate(Date date) {
        this.priceDate = date;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}

