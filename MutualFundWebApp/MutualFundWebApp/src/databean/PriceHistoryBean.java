package databean;
import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId,priceDate")
public class PriceHistoryBean {
    private int    fundId;
    private Date priceDate;
    private long price;

    public int    getFundId()             { return fundId;           }
    public Date getPriceDate()                { return priceDate;         }
    public long getPrice()                    { return price;         }

    public void   setFundId(int i)        { fundId = i;              }
    public void   setPriceDate(Date d)        { priceDate = d;            }
    public void   setPrice(long l)            { price = l;            }
}
