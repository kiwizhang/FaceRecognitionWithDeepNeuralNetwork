package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("customerId")
public class Customer {
    private int customerId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String addrLine1;
    private String addrLine2;
    private String city;
    private String state;
    private String zip;
    private long   cash;
    private long pendingCash;

    public int    getCustomerId()  { return customerId; }
    public String getUserName()    { return userName;   }
    public String getPassword()    { return password;   }
    public String getFirstName()   { return firstName;  }
    public String getLastName()    { return lastName;   }
    public String getAddrLine1()   { return addrLine1;  }
    public String getAddrLine2()   { return addrLine2;  }
    public String getCity()        { return city;       }
    public String getState()       { return state;      }
    public String getZip()         { return zip;        }
    public long   getCash()        { return cash;       }
    public long getPendingCash()   { return pendingCash;}

    public void setCustomerId(int i)    { customerId = i;}
    public void setUserName(String s)   { userName = s;  }
    public void setFirstName(String s)  { firstName = s; }
    public void setLastName(String s)   { lastName = s;  }
    public void setPassword(String s)   { password = s;  }
    public void setAddrLine1(String s)  { addrLine1 = s; }
    public void setAddrLine2(String s)  { addrLine2 = s; }
    public void setCity(String s)       { city = s;      }
    public void setState(String s)      { state = s;     }
    public void setZip(String s)        { zip = s;       }
    public void setCash(long i)         { cash = i;      }
    public void setPendingCash(long i)  { pendingCash = i;		 }
}
