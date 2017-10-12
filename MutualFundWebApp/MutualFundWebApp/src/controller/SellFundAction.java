package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.PositionBean;
import databean.TransactionBean;
import formbean.SellFundForm;

public class SellFundAction extends Action {
    private FormBeanFactory<SellFundForm> sellFundFactory = FormBeanFactory.getInstance(SellFundForm.class);
  
    private TransactionDAO transactionDAO;
    private PositionDAO positionDAO;
    private CustomerDAO customerDAO;

    public SellFundAction(Model model) {
        transactionDAO = model.getTransactionDAO();
        positionDAO = model.getPositionDAO();
        customerDAO = model.getCustomerDAO();
    }

    public String getName() { return "SellFund.do"; }

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
            
        	customer = customerDAO.read(customer.getUserName());
            session.setAttribute("customer", customer);   
            SellFundForm form = sellFundFactory.create(request);

            if (form == null) {
                return "sell-fund.jsp";
            }
            
            request.setAttribute("form", form);
            
            errors.addAll(form.getValidationErrors());
 
            if (errors.size() != 0) {
                return "sell-fund.jsp";
            }

            int customerId = customer.getCustomerId();
            int fundId = Integer.parseInt(request.getParameter("fundId"));
            
            if (request.getParameter("fundId").equals("null") || request.getParameter("fundId").trim().equals("")) {
            	errors.add("The fund doesn't exist!");
                return "sell-fund.jsp";
            }

            if(!positionDAO.existName(fundId)){
               	errors.add("The fund doesn't exist!");
                    return "sell-fund.jsp";
                }

            long requestShares = form.getSharesToSell();  
            if(requestShares <= 0) {
                errors.add("Shares input must greater than zero");
                return "sell-fund.jsp";
            }
            
            PositionBean pb = positionDAO.getPendingShares(fundId,customerId);
            if(pb == null) {
                errors.add("This customer doesn't have this fund.");
                return "sell-fund.jsp";
            }            

            long pendingShares = pb.getPendingShares();
            System.out.println("current shares:-------"+pendingShares);

            if( requestShares <= pendingShares){
                TransactionBean tb = new TransactionBean();
                tb.setCustomerId(customer.getCustomerId());
                tb.setFundId(fundId);
                tb.setShares(requestShares);
                tb.setTransactionType("Sell");
                transactionDAO.add(tb);

                pendingShares = pendingShares - requestShares;       		
                System.out.println("current shares2:-------"+ pendingShares);

                PositionBean updated = new PositionBean();
                updated.setCustomerId(customerId);
                updated.setFundId(fundId);
                updated.setShares(positionDAO.getPendingShares(fundId,customerId).getShares());
                updated.setPendingShares(pendingShares);
                positionDAO.update(updated);
                PositionBean updateShares = positionDAO.getPendingShares(fundId,customerId); 

                session.setAttribute("pendingShares", String.valueOf(updateShares.getPendingShares()));   
                
                DecimalFormat df = new DecimalFormat("#,###.000");
                String sharesFormat = df.format((double)tb.getShares()/1000);
            //    System.out.println("request shares output:-------"+ tb.getShares());
            //    System.out.println("request shares output:-------"+ tb.getShares()/1000);
                request.setAttribute("message", "Dear " + customer.getUserName() +",   your request shares:  "+sharesFormat+" has been added to pending transaction.");

            } else {
                errors.add("Shares requested is greater than available shares");
                return  "sell-fund.jsp";
            }
            
            return "sell-fund.jsp";
        } catch (FormBeanException e) {
        	errors.add("Input format error");
        	return "sell-fund.jsp";
        } catch(RollbackException e) {
            errors.add("Sorry, your transaction is being processed now. Please sell funds later.");
            return "sell-fund.jsp";
        }
    }

}
