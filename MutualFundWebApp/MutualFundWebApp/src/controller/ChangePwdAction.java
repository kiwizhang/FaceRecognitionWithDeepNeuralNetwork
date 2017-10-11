package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.Employee;
import formbean.ChangePwdForm;

public class ChangePwdAction extends Action {
    private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);

    private CustomerDAO customerDAO;
    private EmployeeDAO employeeDAO;

    public ChangePwdAction(Model model) {
        customerDAO = model.getCustomerDAO();
        employeeDAO = model.getEmployeeDAO();
    }

    public String getName() { return "change-pwd.do"; }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try {
            ChangePwdForm form = formBeanFactory.create(request);

            if (!form.isPresent()) {
                return "change-pwd.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "change-pwd.jsp";
            }

            Customer c = (Customer) session.getAttribute("customer");
            Employee e = (Employee) session.getAttribute("employee");

            if(c != null) {
                Customer customer = (Customer) session.getAttribute("customer");	
                customerDAO.setPassword(customer.getUserName(),form.getNewPassword());
                request.setAttribute("message","Password has been successfully changed for "+customer.getUserName());	
                
            } else if(e != null) {
                Employee employee = (Employee) request.getSession().getAttribute("employee");	
                employeeDAO.setPassword(employee.getUserName(),form.getNewPassword());
                request.setAttribute("message","Password has been successfully changed for "+employee.getUserName());	
            }

            return "change-pwd.jsp";
        } catch (RollbackException e) {
        	errors.add("Concurrent user");
        	return "change-pwd.jsp";
        } catch (FormBeanException e) {
        	errors.add("Input format error");
            return "change-pwd.jsp";
        }
    }
}
