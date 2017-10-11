package databean;

import java.util.Date;

public class TransactionDayBean {
    private int fundId;
    private String name;
    private String symbol;
    private Date executeDate;
    private long price;

    public int getFundId() {
        return fundId;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setFundId(int i) {
        fundId = i;
    }

    public void setExecuteDate(Date d) {
        executeDate = d;
    }

    public void setPrice(long p) {
        price = p;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
