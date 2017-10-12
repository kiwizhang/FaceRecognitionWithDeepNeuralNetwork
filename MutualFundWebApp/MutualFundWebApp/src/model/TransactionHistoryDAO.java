package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericViewDAO;

import databean.TransactionHistory;
import databean.TransactionBean;

public class TransactionHistoryDAO extends GenericViewDAO<TransactionHistory> {
    public TransactionHistoryDAO(ConnectionPool pool) throws DAOException {
        super(TransactionHistory.class, pool);
    }
/*
    public TransactionHistory[] getTransactionList(int customerId)
        throws RollbackException {
    	
        String query = "select t.fundId, customerId, name, symbol, transactionType, shares, price, amount, priceDate as executeDate "
        		+ " from fund f, transaction t, fundpricehistory fph "
        		+ " where t.fundId = f.fundId and t.fundId = fph.fundId and t.executeDate = fph.priceDate and customerId = " + customerId;

        System.out.print(query);
        return executeQuery(query);
    }
*/
    public TransactionHistory setByTransaction(TransactionBean t){
        TransactionHistory th = new TransactionHistory();
        th.setTransactionType(t.getTransactionType());
        th.setExecuteDate(t.getExecuteDate());
        th.setShares(t.getShares());
        th.setAmount(t.getAmount());

        return th;
    }
}
