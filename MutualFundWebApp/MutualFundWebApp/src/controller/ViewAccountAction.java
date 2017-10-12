package controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.CustomerDAO;
import model.TransactionFundDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.TransactionFundBean;
import formbean.IdForm;

public class ViewAccountAction extends Action {
    private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);
    private CustomerDAO customerDAO;
    private TransactionFundDAO transactionFundDAO;
    private TransactionDAO transactionDAO;

    public ViewAccountAction(Model model) {
        customerDAO  = model.getCustomerDAO();
        transactionFundDAO = model.getTransactionFundDAO();
        transactionDAO = model.getTransactionDAO();
    }

    public String getName() { return "viewAccount.do"; }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
        	int id = 0;
        	Customer customer = null;
        	Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
        	if (!isEmployee) {
        		Customer c = (Customer) session.getAttribute("customer");
        		if (c != null) {
        			id = c.getCustomerId();
        			customer = customerDAO.read(id);
                } else {
                	return "login-customer.jsp";
                }
        	} else {
        		IdForm form = formBeanFactory.create(request);
                errors.addAll(form.getValidationErrors());
                if (errors.size() != 0) {
                    return "empl-customer-list.jsp";
                }

                id = form.getIdAsInt();
                customer = customerDAO.read(id);
                if (customer == null) {
                    errors.add("Customer with ID "+id+" does not exist.");
                    return "empl-customer-list.jsp";
                }
            }

            TransactionFundBean[] fundList = transactionFundDAO.getCustomerFundList(id);
            Date date = transactionDAO.getLastDate(id);
            session.setAttribute("date", date);
            session.setAttribute("currentCustomer",customer);
            session.setAttribute("fundList",fundList);
            return "view-account.jsp";

        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
