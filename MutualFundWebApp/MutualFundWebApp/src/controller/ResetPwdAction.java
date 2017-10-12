package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import formbean.IdForm;

public class ResetPwdAction extends Action {
	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);
    private CustomerDAO customerDAO;

    public ResetPwdAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    public String getName() { return "reset-pwd.do"; }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
        	Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
            if (!isEmployee) {
            	errors.add("Sorry, you are not authorized to access this information.");
                return "managefund.do";
            }
            
            IdForm form = formBeanFactory.create(request);
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "empl-customer-list.jsp";
            }
            
            int id = form.getIdAsInt();
            Customer customer = customerDAO.read(id);
            if (customer == null) {
                errors.add("Customer with ID "+id+" does not exist.");
                return "empl-customer-list.jsp";
            }
            
            customerDAO.setPassword(customer.getUserName(),"000");

            request.setAttribute("message","Password for customer "+customer.getUserName()+ " has been reset as 000");

            return "empl-customer-list.jsp";
        } catch (RollbackException e) {
            errors.add(e.toString());
            return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.toString());
            return "error.jsp";
		}
    }
}
