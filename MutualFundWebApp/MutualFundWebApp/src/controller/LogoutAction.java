package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutAction extends Action {

    public LogoutAction() {
    }

    public String getName() { return "logout.do"; }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
        	session.invalidate();
        }

        return "index.jsp";
    }
}
