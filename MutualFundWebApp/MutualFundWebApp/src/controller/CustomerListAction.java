package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.CustomerDAO;

import org.genericdao.RollbackException;

public class CustomerListAction extends Action {

    private CustomerDAO customerDAO;

    public CustomerListAction(Model model) {
        customerDAO  = model.getCustomerDAO();
    }

    public String getName() { return "customerList.do"; }

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

            session.setAttribute("customerList",customerDAO.getCustomers());
            return "empl-customer-list.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
