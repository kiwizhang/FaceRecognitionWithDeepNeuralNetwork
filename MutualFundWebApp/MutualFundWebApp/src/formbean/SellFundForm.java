package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SellFundForm extends FormBean {
	private long sharesToSell;
	private String action;
	private double tempVal;
	private boolean flag;

	public long getSharesToSell() {
		return sharesToSell;
	}

	public void setSharesToSell(String s) {
		s = sanitize(s);
		if (s != null) {
			try {
				tempVal = Double.parseDouble(s);
        		double temp = tempVal * (double)1000;
        		sharesToSell = Math.round(temp);

			} catch (NumberFormatException e) {
				flag = true;
			}
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String a) {
		action = a;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (flag) {
			errors.add("Invalid Shares input.");
		}
		
		if (sharesToSell > 1000000000) {
			errors.add("Shares to sell cannot exceed 1 million shares.");
		}

		if (action == null || action.length() == 0) {
			errors.add("Action is required.");
		}
		
		if (errors.size() > 0) {
			return errors;
		}
		
		if (!action.equals("Sell")) {
			errors.add("Invalid action.");
		}

		
		return errors;
	}

	private String sanitize(String s) {
		return s.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;").replace("\"", "&quot;");
	}
}
