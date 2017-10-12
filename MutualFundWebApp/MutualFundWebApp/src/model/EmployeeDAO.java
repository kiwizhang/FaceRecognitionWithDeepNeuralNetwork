package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.Employee;

public class EmployeeDAO extends GenericDAO<Employee>  {

    public EmployeeDAO(String tableName, ConnectionPool pool) throws DAOException {
        super(Employee.class, tableName, pool);
    }

    public Employee read(String userName) throws RollbackException {
        Employee[] employee = match(MatchArg.equals("userName", userName));
        if (employee.length != 0) return employee[0];
        return null;
    }

    public Employee[] getEmployees() throws RollbackException {
        Employee[] employees = match();
        return employees;
    }

    public void setPassword(String userName, String password) throws RollbackException {
        try {
            Transaction.begin();
            Employee dbEmployee = read(userName);

            if (dbEmployee == null) {
                throw new RollbackException("User "+userName+" no longer exists.");
            }

            dbEmployee.setPassword(password);

            update(dbEmployee);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

}
