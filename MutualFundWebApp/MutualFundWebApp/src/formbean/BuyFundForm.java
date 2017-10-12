package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class BuyFundForm extends FormBean {
	private String action;
	private long amount;
	private boolean flag;
	private double tempVal;

	public long getAmount() {
		return amount;
	}

	public void setAmount(String a) {
		a = sanitize(a);
		try {
			tempVal = Double.parseDouble(a);
    		double temp = tempVal * (double)100;
    		amount = Math.round(temp);

		} catch (NumberFormatException e) {
			flag = true;
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String s) {
		action = s;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (flag) {
			errors.add("Dollar amount must be number.");
		}
		
		if (amount > 100000000) {
			errors.add("Dollar amount cannot exceed $1 million.");
		}

		if (action == null || action.length() == 0) {
			errors.add("Action is required.");
		}

		if (errors.size() > 0)
			return errors;

		if (!action.equals("Buy"))
			errors.add("Invalid button.");

		return errors;
	}

	private String sanitize(String s) {
		return s.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;").replace("\"", "&quot;");
	}
}