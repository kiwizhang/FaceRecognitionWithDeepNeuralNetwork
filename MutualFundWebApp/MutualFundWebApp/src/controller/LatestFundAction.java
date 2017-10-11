package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FundPriceDAO;
import model.Model;

import org.genericdao.RollbackException;

import databean.Customer;
import databean.FundPriceBean;

public class LatestFundAction extends Action {

    private FundPriceDAO fundPriceDAO;
  
    public LatestFundAction(Model model) {
        fundPriceDAO = model.getFundPriceDAO();
        
    }

    public String getName() { return "latestFund.do"; }

	public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();
        
		try {
			Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
            
            if (isEmployee) {
            	errors.add("Sorry, you are not authorized to access this information.");
                return "empl-customer-list.jsp";
            }
			
			Customer customer = (Customer) request.getSession(false).getAttribute("customer");
			
			FundPriceBean[] latestFund = fundPriceDAO.getLatestFundPrice();
	        request.setAttribute("latestFund", latestFund);
	        request.getSession(true).setAttribute("customer", customer);

	        return "fund-list.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
		}
    }
}
