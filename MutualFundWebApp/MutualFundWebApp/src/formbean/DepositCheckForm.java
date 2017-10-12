package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean {
    private long amount;
    private String action;
    private boolean flag;
    private double tempVal;

    public long getAmount() 		   { return amount; }
    public String getAction()		   { return action; }
    public boolean getFlag()           { return flag;}

    public void setAmount(String s){
        s = sanitize(s);
        if(s != null) {
        	try {
        		tempVal = Double.parseDouble(s);
        		double temp = tempVal * (double)100;
        		amount = Math.round(temp);
        	} catch (NumberFormatException e) {
        		flag = true;
        	}
        }
    }
    
    public void setAction(String s)	   { 
        s = sanitize(s);
        if(s != null)
            action = s;	
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (flag) {
        	errors.add("Amount must be numbers. Please input a dollar amount.");
        	return errors;
        }
        
        if (amount <= 0) {
            errors.add("Please input an amount greater than 0.");
        }

        if(amount > 100000000) {
        	errors.add("Deposit amount cannot exceed $1 million");
        }
        
        if(action == null || action.length() == 0)
        {
        	errors.add("Action is required");
        }
        
        if (errors.size() > 0) {
            return errors;
        }
        
        if (!action.equals("Deposit Check")) errors.add("Invalid button.");

        return errors;
    }

    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }

}
