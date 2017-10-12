package databean;

import java.util.Date;

public class TransactionHistory {

    private String name;
    private String symbol;
    private String transactionType;
    private long   shares;
    private long   price;
    private long   amount;
    private Date   executeDate;

    public Date   getExecuteDate()     { return executeDate;       }
    public long   getShares()          { return shares;            } 
    public long   getPrice()           { return price;             }
    public long   getAmount()          { return amount;            }
    public String getName()            { return name;              }
    public String getSymbol()          { return symbol;            }
    public String getTransactionType() { return transactionType;   }

    public void setExecuteDate(Date d) {
        executeDate = d;
    }

    public void setShares(long s) {
        shares = s;
    }

    public void setPrice(long p) {
        price = p;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setTransactionType(String type) {
        this.transactionType = type;
    }
}
