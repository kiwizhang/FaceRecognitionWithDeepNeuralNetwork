package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericViewDAO;
import org.genericdao.RollbackException;

import databean.TransactionFundBean;

import java.util.Date;

public class TransactionFundDAO extends GenericViewDAO<TransactionFundBean> {
    public TransactionFundDAO(ConnectionPool pool) throws DAOException {
        super(TransactionFundBean.class, pool);
    }

    public TransactionFundBean[] getCustomerFundList(int customerId)
        throws RollbackException {
       
    	String query = "select f.fundId, customerId,name, symbol,pendingShares, shares, price, priceDate as executeDate from fund f, position p, fundpricehistory fph  where f.fundId = p.fundId and fph.fundId = f.fundId and fph.priceDate = (select max(priceDate) from fundpricehistory) and customerId = " +  customerId + " group by f.fundId;";

    	//System.out.print(executeQuery(query));
        return executeQuery(query);
    }

    public Date getLastDate(int customerId)
        throws RollbackException{
        String query = "select f.fundId, name, symbol, customerId, shares, price, priceDate as executeDate from fund f, position p, fundpricehistory fph where f.fundId = p.fundId and f.fundId = fph.fundId and fph.priceDate = (select max(priceDate) from fundpricehistory) group by f.fundId;";
        TransactionFundBean[] tfb = executeQuery(query);
        if( tfb.length > 0  ){
            return tfb[0].getExecuteDate();
        }
        else{
            System.out.println("Empty list in TransactionFundDAO.java");
            return null;
        }
    }
}


