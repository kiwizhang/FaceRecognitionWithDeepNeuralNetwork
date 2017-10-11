package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import formbean.LoginForm;
import model.CustomerDAO;
import model.Model;

public class LoginActionCustomer extends Action {
    private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

    private CustomerDAO customerDAO;

    public LoginActionCustomer(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    public String getName() { return "loginCustomer.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
           	request.setAttribute("userList", customerDAO.getCustomers());
            LoginForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            if (!form.isPresent()) {
                return "login-customer.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "login-customer.jsp";
            }

            Customer customer = customerDAO.read(form.getUsername());

            if (customer == null) {
                errors.add("User not found.");
                return "login-customer.jsp";
            }

            if (!form.getPassword().equals(customer.getPassword())) {
                errors.add("Incorrect password.");
                return "login-customer.jsp";
            }

            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            session.setAttribute("isEmployee", false);
            return "managefund.do";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
