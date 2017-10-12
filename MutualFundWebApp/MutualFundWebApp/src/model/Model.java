package model;

import java.sql.Date;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import databean.*;

public class Model {
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;
    private EmployeeDAO employeeDAO;
    private CustomerDAO customerDAO;
    private FundPriceDAO fundPriceDAO;
    private TransactionFundDAO transactionFundDAO;
    private TransactionDayDAO transactionDayDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private TransactionHistoryDAO transactionHistoryDAO;
    private PositionDAO positionDAO;

    public Model(ServletConfig config) throws ServletException {
        try {
            String jdbcDriver = config.getInitParameter("jdbcDriverName");
            String jdbcURL    = config.getInitParameter("jdbcURL");

           // ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL, "root", "bazinga");
            ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);

            transactionDAO  = new TransactionDAO("Transaction", pool);
            fundDAO  = new FundDAO("Fund", pool);
            employeeDAO = new EmployeeDAO("Employee", pool);
            customerDAO = new CustomerDAO("Customer", pool);
            fundPriceDAO = new FundPriceDAO(pool);
            transactionFundDAO = new TransactionFundDAO(pool);
            transactionDayDAO = new TransactionDayDAO(pool);
            fundPriceHistoryDAO = new FundPriceHistoryDAO("FundPriceHistory", pool);
            transactionHistoryDAO = new TransactionHistoryDAO(pool);
            positionDAO = new PositionDAO("Position", pool);

        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }
    public TransactionDAO getTransactionDAO() {return transactionDAO;}
    public FundDAO getFundDAO() {return fundDAO;}
    public FundPriceDAO getFundPriceDAO() {return fundPriceDAO;}
    public TransactionFundDAO getTransactionFundDAO() {return transactionFundDAO;}
    public TransactionDayDAO getTransactionDayDAO() {return transactionDayDAO;}
    public TransactionHistoryDAO getTransactionHistoryDAO() {return transactionHistoryDAO;}
    public EmployeeDAO getEmployeeDAO() { return employeeDAO; }
    public CustomerDAO getCustomerDAO() { return customerDAO;  }
    public PositionDAO getPositionDAO() { return positionDAO;  }
    public FundPriceHistoryDAO getFundPriceHistoryDAO() { return fundPriceHistoryDAO;  }

    public void transitionDay(String month, String day, String year, String[] _ids, String[] _amount) throws RollbackException    {
        Date cur = Date.valueOf( year + "-" + month + "-" + day);

        int[] ids = new int[_ids.length];
        double[] price = new double[_ids.length];
        for(int i = 0; i < _ids.length; i ++){
            ids[i] = Integer.parseInt(_ids[i]);
            price[i] = Double.parseDouble(_amount[i]);
        }

        System.out.println("In Model.java,\n"+ month + "\t" + day + "\t" + year);
        System.out.println(cur);
        for(int i = 0; i < ids.length; i ++)
            System.out.printf("%d, %f\n", ids[i], price[i]);


        // Transaction Table.
        // PH table done.
        fundPriceHistoryDAO.transitionDay(cur, ids, price);
        TransactionBean[] tbs = transactionDAO.readAll();
        for(int i = 0; i < tbs.length; i ++){
            System.out.println(tbs[i].getAmount());
            if( tbs[i].getExecuteDate() != null )
                continue;
            processTransaction(tbs[i], ids, price, cur);
            //                System.out.println(tbs[i].getAmount());
        }

    }

    private double findPrice(int fid, int[] ids, double[] price){
        int i = 0;
        for(; i < ids.length; i ++){
            if( ids[i] == fid )
                return price[i];
        }
        return -1;
    }

    private void processTransaction( TransactionBean tb, int[] ids, double[] prices, Date d ) throws RollbackException{
        System.out.printf("Processing transaction: %d\n", tb.getTransactionId());
        String type = tb.getTransactionType();

        int fid = tb.getFundId();
        double price = findPrice(fid, ids, prices);

        int cid = tb.getCustomerId();
        double shares = (double)tb.getShares()/1000.0;
        double damount = (double)(tb.getAmount()/100.0);

        Customer c = customerDAO.getCustomerById(cid); 
        double cash = (double)c.getCash()/100.0;

        if(type.equals("Buy")){
            cash -= damount;
            double myshares = damount / price;
            long longshares = (long)(myshares*1000);
            positionDAO.addShare(cid, fid, longshares);
            positionDAO.setPendingShare(cid, fid);
        }
        else if(type.equals("Sell")){
            double amount = shares * price; 
            cash += amount;
            long longshares = (long)(shares*1000);
            positionDAO.addShare(cid, fid, -longshares);
            positionDAO.setPendingShare(cid, fid);

        }
        else if(type.equals("Deposit Check")){ // Add balance
            cash += damount;
        }
        else if(type.equals("Request Check")){ // Sub balance
            cash -= damount;
        }
        else{
            System.out.println("No such Transaction Type!!" +type);
        }
        long mycash = (long)(cash*100);
        customerDAO.setCash(c.getUserName(), mycash);
        customerDAO.setPendingCash(c.getUserName(), mycash);

        transactionDAO.setDate(tb.getTransactionId(), d);

    }


    public TransactionDayBean[] getFundList()
        throws RollbackException {

        FundBean[] fbs = fundDAO.getFunds();
        TransactionDayBean[] tdbs = new TransactionDayBean[fbs.length];
        for(int i = 0; i < fbs.length; i ++){
            TransactionDayBean tdb = new TransactionDayBean(); 
            tdb.setFundId(fbs[i].getFundId());
            tdb.setName(fbs[i].getName());
            tdb.setSymbol(fbs[i].getSymbol());
            PriceHistoryBean[] fphs = fundPriceHistoryDAO.read(fbs[i].getFundId());
            if(fphs.length > 0){
                java.util.Date latest = fphs[0].getPriceDate();
                long price = fphs[0].getPrice();
                for(int j = 1; j < fphs.length; j ++){
                    java.util.Date cur = fphs[j].getPriceDate();
                    if( cur.after(latest) ){
                        price = fphs[j].getPrice();
                        latest = cur;  
                    }
                }
                Date t = new Date(latest.getTime());
                tdb.setExecuteDate(t);
                tdb.setPrice(price);
            }
            tdbs[i] = tdb;
        }
        return tdbs;
    }


    public TransactionHistory[] getTransactionList(int cid) throws RollbackException{
        ArrayList<TransactionHistory> ths = new ArrayList<TransactionHistory>();
        System.out.printf("In getTList, cid=%d\n",cid);
        TransactionBean[] tbs = transactionDAO.readByCustomerId(cid);
        System.out.printf("tbs.len = %d\n", tbs.length);
        for(int i = 0; i < tbs.length; i ++){
            TransactionHistory th = transactionHistoryDAO.setByTransaction(tbs[i]);
            int fid = tbs[i].getFundId();
            System.out.printf("fid=%d\n",fid);
            if( fid != 0 ){
                FundBean fb = fundDAO.read(fid);
                th.setSymbol(fb.getSymbol());
                th.setName(fb.getName());
                Date d = tbs[i].getExecuteDate();
                if( d != null ){
                	long price = fundPriceHistoryDAO.getPriceByFundIdAndDate(fid, new java.util.Date(d.getTime()));
                	th.setPrice(price);
                	double dp = (double)price/100.0;
                	long sh = tbs[i].getShares();
                	double dsh = (double)sh/1000.0;
                	long amount = tbs[i].getAmount();
                	double da = (double)amount/100.0;
                	if(tbs[i].getTransactionType().equals("Sell")){
                		da = dp*dsh;
                		th.setAmount(Math.round(da*100.0));
                	}
                	else if(tbs[i].getTransactionType().equals("Buy")){
                		dsh = da / dp;
                		th.setShares(Math.round(dsh*1000.0));
                	}
                }
            }

            ths.add(th);
        }
        Collections.sort(ths, new Comparator<TransactionHistory>(){
            public int compare(TransactionHistory a, TransactionHistory b){
                java.util.Date da = a.getExecuteDate();
                java.util.Date db = b.getExecuteDate();
                if(da == null) return -1;
                if(db == null) return 1;
                return da.after(db) ? -1:1;
            }
        } );
        return ths.toArray(new TransactionHistory[ths.size()]); 
    }


    public TransactionFundBean[] getRecommendationFundList(int cid) throws RollbackException{
        ArrayList<TransactionFundBean> ths = new ArrayList<TransactionFundBean>();
        System.out.printf("In getTList, cid=%d\n",cid);
        ArrayList<Integer> not_sids = positionDAO.getSharesByCustomerId(cid);

        FundBean[] fbs = fundDAO.getFunds();
        for(int i = 0; i < fbs.length; i ++){
            int fid = fbs[i].getFundId();
            if( !not_sids.contains(fid) ){
                TransactionFundBean th = new TransactionFundBean();
                th.setSymbol(fbs[i].getSymbol());
                th.setName(fbs[i].getName());
                th.setFundId(fid);
                long price = fundPriceHistoryDAO.getPriceByFundId(fid);
                th.setPrice(price);
                ths.add(th);
            }
        }
        return ths.toArray(new TransactionFundBean[ths.size()]); 
    }

}
