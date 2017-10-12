package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
    private String username;
    private String password;
    private String action;

    public String getUsername()     { return username;   }
    public String getPassword()  { return password;}
    public String getAction()    { return action;  }

    public void setUsername(String s)    { username = sanitize(s.trim());   }
    public void setPassword(String s) {	password = sanitize(s.trim());}
    public void setAction(String s)   { action = s;         }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (username == null || username.length() == 0) {
            errors.add("Username is required.");
        }

        if (password == null || password.length() == 0) {
            errors.add("Password is required.");
        }

        if (action == null || action.length() == 0) {
            errors.add("Action is required.");
        }

        if (errors.size() > 0) return errors;

        if (!action.equals("Login")) errors.add("Invalid action.");

        return errors;
    }

    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }
}
