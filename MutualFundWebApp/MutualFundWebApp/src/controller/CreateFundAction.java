package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import formbean.FundForm;
import model.Model;
import model.FundDAO;
import databean.FundBean;

public class CreateFundAction extends Action {

    private FormBeanFactory<FundForm>  fundFormFactory  = FormBeanFactory.getInstance(FundForm.class);
    private FundDAO fundDAO;

    public CreateFundAction(Model model) {
        fundDAO = model.getFundDAO();
    }

    public String getName() { return "createFund.do"; }

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
            
            FundForm form = fundFormFactory.create(request);
            request.setAttribute("form", form);

            if (!form.isPresent()) {
                return "createFund.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() > 0) {
                return "createFund.jsp";
            }

            if( fundDAO.existName(form.getName(), form.getSymbol() )  ){
                errors.add("This fund name already exists.");
                return "createFund.jsp";
            }

            FundBean bean = new FundBean();
            bean.setName(form.getName());
            bean.setSymbol(form.getSymbol());

            fundDAO.add(bean);
            request.setAttribute("message","New fund has been successfully created: "+bean.getName());	

            return "createFund.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
