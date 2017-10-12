package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericViewDAO;

import databean.TransactionDayBean;

public class TransactionDayDAO extends GenericViewDAO<TransactionDayBean> {
    public TransactionDayDAO(ConnectionPool pool) throws DAOException {
        super(TransactionDayBean.class, pool);
    }
}


