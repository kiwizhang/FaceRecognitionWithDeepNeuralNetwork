package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import databean.Customer;
import databean.Employee;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        //r44
        Action.add(new ViewTransactionAction(model));
        Action.add(new CreateFundAction(model));
        Action.add(new TransitionDayAction(model));
        Action.add(new LogoutAction());

        //Raechel
        Action.add(new CreateCustomerAction(model));
        Action.add(new CreateEmployeeAction(model));
        Action.add(new CustomerListAction(model));
        Action.add(new ViewAccountAction(model));

        //Doris
        Action.add(new LoginActionEmployee(model));
        Action.add(new LoginActionCustomer(model));
        Action.add(new LatestFundAction(model));
        Action.add(new FundStatsAction(model));

        //Keith
        Action.add(new DepositCheckAction(model));
        Action.add(new RequestCheckAction(model));
        Action.add(new ChangePwdAction(model));
        Action.add(new ResetPwdAction(model));

        // Joven
        Action.add(new ManageFundAction(model));
        Action.add(new BuyFundAction(model));
        Action.add(new SellFundAction(model));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage, request, response);
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String action = getActionName(servletPath);
        HttpSession session = request.getSession(true);

        Customer customer = (Customer) session.getAttribute("customer");
        Employee employee = (Employee) session.getAttribute("employee");

        System.out.println("In performTheAction: " + action);

        if (action.equals("loginCustomer.do") || action.equals("loginEmployee.do")) {
            return Action.perform(action, request);
        }

        if (customer == null && employee == null) {
            return "index.jsp";
        }

        // Let the logged in user run his chosen action
        return Action.perform(action, request);
    }

    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        System.out.println("In sendToNextPage: " + nextPage);
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }

        // For *.html.
        RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                + nextPage);
        d.forward(request, response);

        return;

    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}

