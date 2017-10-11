package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateEmpForm extends FormBean {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String confirm;
    private String action;

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public String getUserName()  { return userName;  }
    public String getPassword()  { return password;  }
    public String getConfirm()   { return confirm;   }
    public String getAction()    { return action;  }

    public void setFirstName(String s) { firstName = sanitize(s.trim());  }
    public void setLastName(String s)  { lastName  = sanitize(s.trim());  }
    public void setUserName(String s)  { userName  = sanitize(s.trim());  }
    public void setPassword(String s)  { password  = sanitize(s.trim());  }
    public void setConfirm(String s)   { confirm   = sanitize(s.trim());  }
    public void setAction(String s)    { action = s; }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (firstName == null || firstName.length() == 0) {
            errors.add("First Name is required.");
        }
        
        if( firstName.length() > 16) {
        	errors.add("First name must be less than 16 letters");
        }

        if (lastName == null || lastName.length() == 0) {
            errors.add("Last Name is required.");
        }
        
        if( lastName.length() > 16) {
        	errors.add("Last name must be less than 16 letters");
        }

        if (userName == null || userName.length() == 0) {
            errors.add("Username is required.");
        }
        
        if( userName.length() > 16) {
        	errors.add("Username must be less than 16 letters");
        }

        if (password == null || password.length() == 0) {
            errors.add("Password is required.");
        }
        
        if( password.length() > 16) {
        	errors.add("Password must be less than 16 letters");
        }

        if (confirm == null || confirm.length() == 0) {
            errors.add("Confirm Password is required.");
        }

        if (!password.equals(confirm)) {
            errors.add("Passwords do not match.");
        }

        if (action == null || action.length() == 0) {
            errors.add("Action is required.");
        }

        if (errors.size() > 0) return errors;

        if (!action.equals("Create Employee Account")) errors.add("Invalid button.");
        return errors;
    }

    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }
}
