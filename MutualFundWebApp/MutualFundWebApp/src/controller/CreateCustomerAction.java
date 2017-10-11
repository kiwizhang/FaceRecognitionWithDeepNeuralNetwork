package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.CustomerDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import formbean.CreateCusForm;

public class CreateCustomerAction extends Action {
    private FormBeanFactory<CreateCusForm> formBeanFactory = FormBeanFactory.getInstance(CreateCusForm.class);
    private CustomerDAO  customerDAO;

    public CreateCustomerAction(Model model) {
        customerDAO  = model.getCustomerDAO();
    }

    public String getName() { return "createCustomer.do"; }

    public String perform(HttpServletRequest request) {
    	HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
            CreateCusForm form = formBeanFactory.create(request);
            request.setAttribute("form",form);
            
            Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
            if (!isEmployee) {
            	errors.add("Sorry, you are not authorized to access this information.");
                return "manage.jsp";
            }

            if (!form.isPresent()) {
                return "create-customer.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "create-customer.jsp";
            }

            Customer customer = customerDAO.read(form.getUserName());
            if (customer != null) {
                errors.add("Username already exists. Please change another username.");
                return "create-customer.jsp";
            }

            customer = new Customer();
            customer.setUserName(form.getUserName());
            customer.setFirstName(form.getFirstName());
            customer.setLastName(form.getLastName());
            customer.setPassword(form.getPassword());
            customer.setAddrLine1(form.getAddrLine1());
            customer.setAddrLine2(form.getAddrLine2());
            customer.setCity(form.getCity());
            customer.setState(form.getState());
            customer.setZip(form.getZip());
            customer.setCash(0);
            customerDAO.create(customer);
            request.setAttribute("message","New customer has been successfully created: "+customer.getUserName());
            request.removeAttribute("form");

            return "create-customer.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
