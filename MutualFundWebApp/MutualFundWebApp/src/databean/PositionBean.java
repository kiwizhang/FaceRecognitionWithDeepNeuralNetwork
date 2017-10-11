package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId,customerId")
public class PositionBean {
    private int    customerId;
    private int    fundId;
    private long    shares;
    private long  pendingShares;

    public int    getFundId()             { return fundId;           }
    public int    getCustomerId()             { return customerId;           }
    public long    getShares()             { return shares;           }
    public long    getPendingShares()             { return pendingShares;           }

    public void   setFundId(int i)        { fundId = i;              }
    public void   setCustomerId(int i)        { customerId = i;              }
    public void   setShares(long i)        { shares = i;              }
    public void   setPendingShares(long i)        { pendingShares = i;              }
}
