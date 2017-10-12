package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.FundBean;

public class FundDAO extends GenericDAO<FundBean> {
    public FundDAO(String tableName, ConnectionPool pool) throws DAOException {
        super(FundBean.class, tableName, pool);
    }
    public FundBean read(int fid) throws RollbackException{
        FundBean[] fbs = match(MatchArg.equals("fundId", fid));
        if (fbs.length != 0) return fbs[0];
        return null;
    }
    
    public boolean existName( int fundId ) throws RollbackException{
        FundBean[] fbs = match(MatchArg.equals("fundId", fundId));
        if (fbs.length != 0) return true;
        return false;
    }


    public FundBean[] getFunds() throws RollbackException {
        FundBean[] allFund = match();
        return allFund;
    }	
    
    public boolean existName( String name, String symbol ) throws RollbackException{
        FundBean[] fbs = match( MatchArg.and(
                MatchArg.equals("name", name),
                MatchArg.equals("symbol",  symbol)));

        if (fbs.length != 0) return true;
        return false;
    }


    public void add(FundBean item) throws RollbackException {
        try {
            Transaction.begin();

            // Create a new FundBean in the database with the next id number
            createAutoIncrement(item);

            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
}


