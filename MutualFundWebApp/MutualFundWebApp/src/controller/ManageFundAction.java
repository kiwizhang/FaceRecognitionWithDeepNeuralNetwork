package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;
import model.TransactionFundDAO;

import org.genericdao.RollbackException;

import databean.Customer;
import databean.TransactionFundBean;

public class ManageFundAction extends Action {
    private TransactionFundDAO transactionFundDAO;
    private Model model;
    private CustomerDAO customerDAO;

    public ManageFundAction(Model model) {
        transactionFundDAO = model.getTransactionFundDAO();
        customerDAO = model.getCustomerDAO();
           this.model = model;
    }

    public String getName() { return "managefund.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();

        try {
            Customer customer = (Customer) request.getSession().getAttribute("customer");
 
            Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
            if (isEmployee) {
            	errors.add("Sorry, you are not authorized to access this information.");
                return "empl-customer-list.jsp";
            }

            if(customer != null) {
            	customer = customerDAO.read(customer.getCustomerId());
            	TransactionFundBean[] recommendationlist = model.getRecommendationFundList(customer.getCustomerId());
            	int length = recommendationlist.length;
            	
                session.setAttribute("length",length);
                session.setAttribute("mytransactionlist", transactionFundDAO.getCustomerFundList(customer.getCustomerId()));
                session.setAttribute("recommendationlist", recommendationlist);
                session.setAttribute("fundId", request.getParameter("fundId"));
                session.setAttribute("name", request.getParameter("name"));
                session.setAttribute("symbol", request.getParameter("symbol"));
                session.setAttribute("shares", request.getParameter("shares"));
                session.setAttribute("pendingShares", request.getParameter("pendingShares"));
                session.setAttribute("price", request.getParameter("price"));
                session.setAttribute("customer",customer);
              
                String action = request.getParameter("action");
                if ("View".equals(action)) {
                    return "view.do";
                } else if ("Sell".equals(action)) {
                    return "sell-fund.jsp";
                } else if ("Buy".equals(action)) {
                    return "buy-fund.jsp";
                }
            } else{
            	return "login-customer.jsp";
            }

            return "manage.jsp";
        } catch (RollbackException e) {
        	errors.add("Concurrent user");
            return "manage.jsp";
        }
    }
}
