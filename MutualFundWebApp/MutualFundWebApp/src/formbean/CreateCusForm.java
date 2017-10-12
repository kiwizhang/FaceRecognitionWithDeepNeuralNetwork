package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateCusForm extends FormBean {
    private String userName;
    private String password;
    private String confirm;
    private String firstName;
    private String lastName;
    private String addrLine1;
    private String addrLine2;
    private String city;
    private String state;
    private String zip;
    private String action;

    public String getUserName()    { return userName;   }
    public String getPassword()    { return password;   }
    public String getConfirm()     { return confirm;    }
    public String getFirstName()   { return firstName;  }
    public String getLastName()    { return lastName;   }
    public String getAddrLine1()   { return addrLine1;  }
    public String getAddrLine2()   { return addrLine2;  }
    public String getCity()        { return city;       }
    public String getState()       { return state;      }
    public String getZip()         { return zip;        }
    public String getAction()      { return action;     }

    public void setUserName(String s)   { userName = sanitize(s.trim());  }
    public void setFirstName(String s)  { firstName = sanitize(s.trim()); }
    public void setLastName(String s)   { lastName = sanitize(s.trim());  }
    public void setPassword(String s)   { password = sanitize(s.trim());  }
    public void setConfirm(String s)    { confirm   = sanitize(s.trim()); }
    public void setAddrLine1(String s)  { addrLine1 = sanitize(s.trim()); }
    public void setAddrLine2(String s)  { addrLine2 = sanitize(s.trim()); }
    public void setCity(String s)       { city = sanitize(s.trim());      }
    public void setState(String s)      { state = sanitize(s.trim());     }
    public void setZip(String s)        { zip = sanitize(s.trim());       }
    public void setAction(String s)     { action = s; }

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

        if (addrLine1 == null || addrLine1.length() == 0) {
            errors.add("Address Line1 is required.");
        }
        
        if( addrLine1.length() > 20) {
        	errors.add("Address Line1 must be less than 20 letters");
        }
        
        if( addrLine2.length() > 20) {
        	errors.add("Address Line2 must be less than 20 letters");
        }

        if (city == null || city.length() == 0) {
            errors.add("City is required.");
        }
        
        if( city.length() > 16) {
        	errors.add("City must be less than 16 letters");
        }

        if (state == null || state.length() == 0) {
            errors.add("State is required.");
        }
        
        if( state.length() > 16) {
        	errors.add("State must be less than 16 letters");
        }

        if (zip == null || zip.length() == 0) {
            errors.add("Zipcode is required.");
        }
        
        if (zip.length() != 5) {
            errors.add("Zipcode must be 5-digit long.");
        }
        
        try {
			int zipCode = Integer.parseInt(zip);
		} catch (NumberFormatException e) {
			errors.add("Zipcode must digit.");
		}

        if (!password.equals(confirm)) {
            errors.add("Passwords do not match.");
        }

        if (action == null || action.length() == 0) {
            errors.add("Action is required.");
        }

        if (errors.size() > 0) return errors;

        if (!action.equals("Create Customer Account")) errors.add("Invalid button.");

        return errors;
    }

    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }
}
