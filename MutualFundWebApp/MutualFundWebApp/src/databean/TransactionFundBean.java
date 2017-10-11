package databean;

import java.util.Date;

public class TransactionFundBean {
    //	private int transactionId;
    private int customerId;
    private int fundId;
    private String name;
    private String symbol;
    private Date executeDate;
    private long shares;
    private long pendingShares;

    //	private String transactionType;
    //private long amount;
    private long price;
    /*
       public int getTransactionId() {
       return transactionId;
       }
       */
    public int getCustomerId() {
        return customerId;
    }

    public int getFundId() {
        return fundId;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public long getShares() {
        return shares;
    }
    
    public long getPendingShares() {
        return pendingShares;
    }

    /*
       public String getTransactionType() {
       return transactionType;
       }
       */
    //public long getAmount() {
    //	return amount;
    //}

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
    /*
       public void setTransactionId(int i) {
       transactionId = i;
       }
       */
    public void setCustomerId(int i) {
        customerId = i;
    }

    public void setFundId(int i) {
        fundId = i;
    }

    public void setExecuteDate(Date d) {
        executeDate = d;
    }

    public void setShares(long s) {
        shares = s;
    }
    
    public void setPendingShares(long s) {
        pendingShares = s;
    }

    /*
       public void setTransactionType(String s) {
       transactionType = s;
       }
       */
    //	public void setAmount(long a) {
    //	amount = a;
    //}

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
