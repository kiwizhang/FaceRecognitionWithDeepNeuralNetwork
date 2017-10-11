package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.TransactionHistory;
import formbean.IdForm;
import model.CustomerDAO;
import model.Model;

public class ViewTransactionAction extends Action {
    private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);
    private CustomerDAO customerDAO;
    private Model model;

    public ViewTransactionAction(Model model) {
        customerDAO  = model.getCustomerDAO();
        this.model = model;
    }

    public String getName() { return "viewTransaction.do"; }

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

            //TransactionHistory[] tranHistory = transactionHistoryDAO.getTransactionList(formId);
            TransactionHistory[] tranHistory = model.getTransactionList(id);
            System.out.println("testing view transaction 64: "+tranHistory.length);
            session.setAttribute("currentCustomer",customer);
            session.setAttribute("allTrans",tranHistory);
            return "transaction-history.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            System.out.println(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
