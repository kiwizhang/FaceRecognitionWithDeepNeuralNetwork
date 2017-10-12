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
import formbean.RequestCheckForm;

public class RequestCheckAction extends Action {
    private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);

    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;

    public RequestCheckAction(Model model) {
        customerDAO = model.getCustomerDAO();
        transactionDAO = model.getTransactionDAO();
    }

    public String getName() { return "request-check.do"; }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
            RequestCheckForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            
            Customer customer = (Customer) session.getAttribute("customer");
            if(customer==null){
            	return "loginCustomer.do";
            }
            
            customer = customerDAO.read(customer.getUserName());
            session.setAttribute("customer", customer);   

            if (!form.isPresent()) {
                return "request-check.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "request-check.jsp";
            }

            Customer updatedCustomer = customerDAO.getCustomerById(customer.getCustomerId());
            long pendingBalance = updatedCustomer.getPendingCash();
            
            long requestAmount = form.getAmount();;

            System.out.println("request vs current");
            System.out.println(requestAmount);
            System.out.println(pendingBalance);

            if(requestAmount <= pendingBalance)
            {
                //add to transaction table
                TransactionBean tb = new TransactionBean();
                tb.setAmount(requestAmount);
                tb.setCustomerId(customer.getCustomerId());
                tb.setTransactionType("Request Check");
                transactionDAO.add(tb);

                //update customer's pending balance in customer table
                pendingBalance = pendingBalance - requestAmount;
                customerDAO.setPendingCash(customer.getUserName(), pendingBalance);
                Customer updateCustomer = customerDAO.read(customer.getUserName());
                session.setAttribute("customer", updateCustomer);     
                
                DecimalFormat df = new DecimalFormat("#,###.00");
                String sharesFormat = df.format((double)tb.getAmount()/100);
                request.setAttribute("message", "Dear " + customer.getUserName() +", Request check $ "+ sharesFormat +" has been added to pending transaction.");

            }
            else
            {
                errors.add("Amount requested is greater than pending balance");
                return "request-check.jsp";
            }
            
            return "request-check.jsp";
        } catch (RollbackException e) {
            errors.add("Sorry, your transaction is being processed now. Please request check later.");
            return "request-check.jsp";
        } catch (FormBeanException e) {
        	errors.add("Input format error");
        	return "request-check.jsp";
        }
    }    
}
