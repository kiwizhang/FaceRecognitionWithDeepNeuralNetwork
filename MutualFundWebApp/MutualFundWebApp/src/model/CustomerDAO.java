package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.Customer;

public class CustomerDAO extends GenericDAO<Customer> {

    public CustomerDAO(String tableName, ConnectionPool pool) throws DAOException {
        super(Customer.class, tableName, pool);
    }

    public Customer read(String userName) throws RollbackException {
        Customer[] customer = match(MatchArg.equals("userName", userName));
        if (customer.length != 0) return customer[0];
        return null;
    }

    public void create(Customer customer) throws RollbackException {
        try {
            Transaction.begin();
            createAutoIncrement(customer);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    public Customer[] getCustomers() throws RollbackException {
        Customer[] customers = match();
        return customers;
    }
    
    public Customer getCustomerById(int cid) throws RollbackException {
        Customer[] customer = match(MatchArg.equals("customerId", cid));
        if (customer.length != 0) return customer[0];
        return null;
    }

    public void setPendingCash(String userName, long cash) throws RollbackException {
        try {
            Transaction.begin();
            Customer dbCustomer = read(userName);

            if (dbCustomer == null) {
                throw new RollbackException("User "+userName+" doesn't exists.");
            }

            dbCustomer.setPendingCash(cash);;

            update(dbCustomer);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    public void setCash(String userName, long cash) throws RollbackException {
        try {
            Transaction.begin();
            Customer dbCustomer = read(userName);

            if (dbCustomer == null) {
                throw new RollbackException("User "+userName+" doesn't exists.");
            }

            dbCustomer.setCash(cash);;

            update(dbCustomer);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }


    public void setPassword(String userName, String password) throws RollbackException {
        try {
            Transaction.begin();
            Customer dbCustomer = read(userName);

            if (dbCustomer == null) {
                throw new RollbackException("User "+userName+" doesn't exists.");
            }

            dbCustomer.setPassword(password);

            update(dbCustomer);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
}
