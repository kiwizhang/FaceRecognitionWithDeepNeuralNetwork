package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.FundPriceHistoryDAO;
import model.FundDAO;

import org.genericdao.RollbackException;

public class TransitionDayAction extends Action {
    private Model m;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private SimpleDateFormat mdyFormat;
    private FundDAO fundDAO;

    public TransitionDayAction(Model model) {
        m = model;
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
        mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
        fundDAO = model.getFundDAO();
    }

    public String getName() { return "transitionDay.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession(false);
        try {
            System.out.println("Check isEmp");
            Boolean isEmployee = (Boolean) session.getAttribute("isEmployee");
            if (!isEmployee) {
                errors.add("Sorry, you are not authorized to access this information.");
                return "manage.jsp";
            }

            System.out.println("Get fundlist");
            session.setAttribute("fundList", m.getFundList());

            System.out.println("Get transition");
            String action = (String)request.getParameter("transition");
            if(action == null || ! action.equals("Make Transition") )
                return "transition-day.jsp";
            System.out.println("Get date");
            String date = (String)request.getParameter("date");

            if(date == null || date.trim().length() == 0) {
                errors.add("Please choose a date!");
                return "transition-day.jsp";
            }
            System.out.println("Get latest date");
            Date latest = fundPriceHistoryDAO.getAllLastDate();
            if( latest != null ){
                Date tmp = mdyFormat.parse(date);
                if( tmp.compareTo(latest) <= 0 ){
                    errors.add("The date should be later than the last transition day: " + mdyFormat.format(latest) );
                    return "transition-day.jsp";
                }
            }

            String[] tokens = date.split("/");

            String month = tokens[0];
            String day = tokens[1];
            String year = tokens[2];
            String[] ids = request.getParameterValues("ids");
            String[] amount = request.getParameterValues("amount");
            System.out.println("Get request values");

            errors.addAll(getValidationErrors(month, day, year, ids, amount));
            if (errors.size() != 0) {
                return "transition-day.jsp";
            }

            System.out.println("Start model.transitionDay");
            m.transitionDay(month, day, year, ids, amount);
            request.setAttribute("message", "You have successfully updated the closing price!");
            System.out.print("You have successfully updated the closing price!");
            session.setAttribute("fundList", m.getFundList());
            return "transition-day.jsp";

        } catch (RollbackException e) {
            System.out.println(e.getMessage());
            errors.add("Another user is processing, please try again.");
            return "transition-day.jsp";
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            errors.add("Invalid Date format.");
            return "transition-day.jsp";
        }
    }

    private List<String> getValidationErrors(String month, String day, String year, String[] ids, String[] amount) throws RollbackException{
        List<String> errors = new ArrayList<String>();
        if(ids.length != amount.length){
            errors.add("The number of funds should be equal to the number of closing price");
            return errors;
        }
        for(int i = 0; i < ids.length; i ++){
            if(amount[i]== null || amount[i].trim().length()==0){
                errors.add("Sorry, you must fill in all the closing price.");  
                return errors;
            }
            int id = Integer.parseInt(ids[i]);
            if(fundDAO.read(id) == null){
                errors.add("Fund Id: " + id  + " doesn't exist.");         			
                return errors;
            }
        }

        double tmp;
        for(int i = 0; i < amount.length; i ++){
            try {
                tmp = Double.parseDouble(amount[i]);

            } catch (NumberFormatException e) {
                errors.add("Sorry, invalid closing price input.");
                break;
            }
            if(tmp <= 0){
                errors.add("Sorry, closing price must be greater than zero.");
                break;
            }
            if(tmp < 1.0 || tmp > 1000.0){
                errors.add("Sorry, closing price must be greater than $1 and less than $1,000.");
                break;
            }
        }
        return errors;
    }

}
