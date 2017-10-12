package model;

import org.genericdao.MatchArg;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;
import org.genericdao.ConnectionPool;
import org.genericdao.Transaction;

import databean.TransactionBean;

import java.sql.Date;

public class TransactionDAO extends GenericDAO<TransactionBean> {
    public TransactionDAO(String tableName, ConnectionPool pool) throws DAOException {
        super(TransactionBean.class, tableName, pool);
    }

    public TransactionBean read(int transactionId) throws RollbackException {
        TransactionBean[] a = match(MatchArg.equals("transactionId", transactionId));
        if(a.length == 0) return null;
        return a[0];
    }

    public TransactionBean[] readAll() throws RollbackException {
        TransactionBean[] a = match();
        return a;
    }

    public TransactionBean[] readByCustomerId(int customerId) throws RollbackException {
        TransactionBean[] a = match(MatchArg.equals("customerId", customerId));
        return a;
    }

    public java.util.Date getLastDate(int customerId) throws RollbackException {
        TransactionBean[] fphs = match(MatchArg.equals("customerId", customerId));

        if(fphs.length > 0){
            java.util.Date latest = null;
            for(int j = 0; j < fphs.length; j ++){
                java.util.Date cur = fphs[j].getExecuteDate();
                if ( cur == null) 
                	continue;
                if ( latest == null || cur.after(latest) )
                    latest = cur;  
            }
            return latest;
        }
        return null;
    }


    public void add(TransactionBean item) throws RollbackException {
        try {
            Transaction.begin();

            // Create a new TransactionBean in the database with the next id number
            createAutoIncrement(item);

            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    public void setDate(int tid, Date d) throws RollbackException {
        try {
            Transaction.begin();
            TransactionBean tb = read(tid);

            if (tb == null) {
                throw new RollbackException("Transaction doesn't exists.");
            }

            tb.setExecuteDate(d);;

            update(tb);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
}
