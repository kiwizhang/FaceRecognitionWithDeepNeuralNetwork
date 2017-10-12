package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FundDAO;
import model.FundPriceDAO;
import model.FundPriceHistoryDAO;
import model.Model;

import org.genericdao.RollbackException;

import databean.Customer;
import databean.FundBean;
import databean.FundPriceBean;
import databean.PriceHistoryBean;

public class FundStatsAction extends Action {

	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private FundDAO fundDAO;
	private FundPriceDAO fundPriceDAO;

	public FundStatsAction(Model model) {
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
		fundDAO = model.getFundDAO();
		fundPriceDAO = model.getFundPriceDAO();
	}

	public String getName() {
		return "stats.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();

		try {
			Customer customer = (Customer) request.getSession(false)
					.getAttribute("customer");
			request.getSession(true).setAttribute("customer", customer);
			Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
			
			FundPriceBean[] latestFund = fundPriceDAO.getLatestFundPrice();
	        session.setAttribute("latestFund", latestFund);
			
			String fundID = request.getParameter("fundId");
			if ( fundID == null || fundID.length() == 0) {
				errors.add("You need to choose a fund.");
				if (!isEmployee) {
					return "fund-list.jsp";
				} else {
					return "empl-customer-list.jsp";
				}
			}
			
			int fundId = 0;
			FundBean curFund = null;
			try {
				fundId = Integer.parseInt(fundID);
				curFund = fundDAO.read(fundId);
				if (curFund == null) {
					errors.add("Fund with ID "+fundId+" does not exist.");
				}
			} catch (NumberFormatException e) {
				errors.add("Fund ID is not an integer.");
			}
			
			request.setAttribute("curFund", curFund);

			PriceHistoryBean[] fph = fundPriceHistoryDAO.read(fundId);
			request.setAttribute("fph", fph);

			return "fund-stats.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
