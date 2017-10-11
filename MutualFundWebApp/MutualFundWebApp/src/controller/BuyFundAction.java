package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.TransactionBean;
import formbean.BuyFundForm;

public class BuyFundAction extends Action {
    private FormBeanFactory<BuyFundForm> fundFormFactory = FormBeanFactory
        .getInstance(BuyFundForm.class);
    private TransactionDAO transactionDAO;
    private CustomerDAO customerDAO;
    private FundDAO fundDAO;

    public BuyFundAction(Model model) {
        transactionDAO = model.getTransactionDAO();
        customerDAO = model.getCustomerDAO();
        fundDAO =model.getFundDAO();
    }

    public String getName() {
        return "BuyFund.do";
    }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        HttpSession session = request.getSession();
        request.setAttribute("errors", errors);
        try {
        	Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
            if (isEmployee) {
            	errors.add("Sorry, you are not authorized to access this information.");
                return "empl-customer-list.jsp";
            }
            
        	Customer customer = (Customer) session.getAttribute("customer");
            if(customer==null){
            	return "loginCustomer.do";
            }

            customer = customerDAO.read(customer.getCustomerId());
            session.setAttribute("customer", customer);   
            
            BuyFundForm form = fundFormFactory.create(request);
            request.setAttribute("form", form);

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "buy-fund.jsp";
            }

            long requestAmount = form.getAmount();
            long pendingBalance = customerDAO.getCustomerById(customer.getCustomerId()).getPendingCash();
            String fundId = request.getParameter("fundId");
            
            if (fundId.equals("null") || fundId.trim().equals("")) {
            	errors.add("The fund doesn't exist!");
                return "buy-fund.jsp";
            }
            
            int fundID =Integer.parseInt(fundId);
            
            if(!fundDAO.existName(fundID)){
           	errors.add("The fund doesn't exist!");
                return "buy-fund.jsp";
            }

            if (requestAmount <= 0){
                errors.add("Amount requested must greater than zero!");
                return "buy-fund.jsp";
            }

            if (requestAmount <= pendingBalance) {

                TransactionBean tb = new TransactionBean();
                tb.setCustomerId(customer.getCustomerId());
                tb.setAmount(form.getAmount());
                tb.setFundId(fundID);
                tb.setTransactionType("Buy");
                transactionDAO.add(tb);

                pendingBalance = pendingBalance - requestAmount;
                customerDAO.setPendingCash(customer.getUserName(), pendingBalance);
                Customer updateCustomer = customerDAO.read(customer.getUserName());
                session.setAttribute("customer", updateCustomer);
                
                DecimalFormat df = new DecimalFormat("#,###.00");
                String sharesFormat = df.format((double)tb.getAmount()/100);
                request.setAttribute("message", "Dear " + customer.getUserName()
                        + " :dollar amount $ " + sharesFormat
                        + " has been added to pending transaction.");

            } else {
                errors.add("Amount requested is greater than available amount");
            }
 
           return "buy-fund.jsp";

        } catch (RollbackException e) {
            errors.add("Sorry, your transaction is being processed now. Please buy funds later.");
            return "buy-fund.jsp";
        } catch (FormBeanException e) {
        	errors.add("Input format error");
        	return "buy-fund.jsp";        }
    }
}
