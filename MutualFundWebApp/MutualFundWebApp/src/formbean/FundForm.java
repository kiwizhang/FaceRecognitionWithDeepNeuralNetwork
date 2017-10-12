package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class FundForm extends FormBean {
    private String name;
    private String symbol;
    private String action;

    public void setName(String s)    { name = sanitize(s.trim());   }
    public void setSymbol(String s)  { symbol = sanitize(s.trim()); }
    public void setAction(String s)	 { action = s;	}

    public String getName()      { return name;   }
    public String getSymbol()    { return symbol; }
    public String getAction()	 { return action; }
    
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();	
        if (name == null || name.length() == 0) {
            errors.add("Fund Name is required.");
        }
        
        if(name.length() > 30) {
        	errors.add("Fund name must be less than 30 letters");
        }
        
        
        if (symbol == null || symbol.length() > 5) {
            errors.add("Length of Ticker must be 5 or less.");
        }
        
        if(isAlpha(symbol) == false) {
        	errors.add("Ticker can only contain letters");
        }
        
        if (action == null || action.length() == 0) {
            errors.add("Action is required.");
        }
        
        if (errors.size() > 0) {
            return errors;
        }
        
        if (!action.equals("Create New Fund")) errors.add("Invalid button.");
        return errors;
    }

    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }
    
    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
}
