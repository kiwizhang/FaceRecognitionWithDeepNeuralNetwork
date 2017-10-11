package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Employee;
import formbean.CreateEmpForm;

public class CreateEmployeeAction extends Action {
    private FormBeanFactory<CreateEmpForm> formBeanFactory = FormBeanFactory.getInstance(CreateEmpForm.class);

    private EmployeeDAO employeeDAO;

    public CreateEmployeeAction(Model model) {
        employeeDAO = model.getEmployeeDAO();
    }

    public String getName() { return "createEmployee.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();

        try {
        	Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
            if (!isEmployee) {
            	errors.add("Sorry, you are not authorized to access this information.");
                return "manage.jsp";
            }
            
            CreateEmpForm form = formBeanFactory.create(request);
            request.setAttribute("form",form);

            if (!form.isPresent()) {
                return "create-employee.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "create-employee.jsp";
            }

            Employee employee = employeeDAO.read(form.getUserName());
            if (employee != null) {
                errors.add("Username already exists. Please change another username.");
                return "create-employee.jsp";
            }

            employee = new Employee();
            employee.setUserName(form.getUserName());
            employee.setFirstName(form.getFirstName());
            employee.setLastName(form.getLastName());
            employee.setPassword(form.getPassword());
            employeeDAO.create(employee);
            request.setAttribute("message","New employee has been successfully created: "+employee.getUserName());
            request.removeAttribute("form");

            return "create-employee.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
