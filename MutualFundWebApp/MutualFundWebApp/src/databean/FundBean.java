package databean;
import org.genericdao.PrimaryKey;

@PrimaryKey("fundId")
public class FundBean {
    private int    fundId;
    private String name;
    private String symbol;

    public int    getFundId()             { return fundId;           }
    public String getName()                { return name;         }
    public String getSymbol()                    { return symbol;         }

    public void   setFundId(int i)        { fundId = i;              }
    public void   setName(String s)        { name = s;            }
    public void   setSymbol(String s)            { symbol = s;            }
}
