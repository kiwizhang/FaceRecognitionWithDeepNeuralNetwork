package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.TransactionBean;
import formbean.DepositCheckForm;
import formbean.IdForm;

public class DepositCheckAction extends Action {
    private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory.getInstance(DepositCheckForm.class);
    private FormBeanFactory<IdForm> idFormBean = FormBeanFactory.getInstance(IdForm.class);
    private TransactionDAO transactionDAO;
    private CustomerDAO customerDAO;

    public DepositCheckAction(Model model) {
        transactionDAO = model.getTransactionDAO();
        customerDAO = model.getCustomerDAO();
    }

    public String getName() { return "deposit-check.do"; }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
        	Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
            if (!isEmployee) {
                errors.add("Sorry, you are not authorized to access this information.");
                return "manage.jsp";
            }
            
            IdForm idForm = idFormBean.create(request);
            errors.addAll(idForm.getValidationErrors());
            if (errors.size() != 0) {
                return "empl-customer-list.jsp";
            }
            int id = idForm.getIdAsInt();
            
            Customer customer = customerDAO.read(id);
            if (customer == null) {
                errors.add("Customer with ID "+id+" does not exist.");
                return "empl-customer-list.jsp";
            }
            session.setAttribute("currentCustomer",customer);

            DepositCheckForm form = formBeanFactory.create(request);
      //      request.setAttribute("form", form);

            if (!form.isPresent()) {
                return "deposit-check.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "deposit-check.jsp";
            }
            
            //add to queue
            TransactionBean tb = new TransactionBean();
            tb.setAmount(form.getAmount());
            tb.setCustomerId(id);
            tb.setTransactionType("Deposit Check");
            transactionDAO.add(tb);
            
            DecimalFormat df = new DecimalFormat("#,###.00");
            String dollar = df.format((double)tb.getAmount()/100);
            request.setAttribute("message","Deposit $"+dollar+" has been added to pending transaction for customer "+customer.getUserName());

            return "deposit-check.jsp";
        } catch (RollbackException e) {
            errors.add(e.toString());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.toString());
            return "error.jsp";
        }
    }
}
