package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Employee;
import formbean.LoginForm;
import model.EmployeeDAO;
import model.Model;

public class LoginActionEmployee extends Action {
    private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

    private EmployeeDAO employeeDAO;

    public LoginActionEmployee(Model model) {
        employeeDAO = model.getEmployeeDAO();
    }

    public String getName() { return "loginEmployee.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
            LoginForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            if (!form.isPresent()) {
                return "login-employee.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "login-employee.jsp";
            }

            Employee employee = employeeDAO.read(form.getUsername());

            if (employee == null) {
                errors.add("User not found.");
                return "login-employee.jsp";
            }

            if (!form.getPassword().equals(employee.getPassword())) {
                errors.add("Incorrect password.");
                return "login-employee.jsp";
            }

            HttpSession session = request.getSession();
            session.setAttribute("employee", employee);
            session.setAttribute("isEmployee", true);
            return "customerList.do";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
