package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class UserForm extends FormBean {
	private String username = "";
	
	public String getUsername()  { return username; }
	
	public void setUsername(String s)  { username = sanitize(s.trim()); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0) {
			errors.add("Username is required.");
		}
		
		return errors;
	}
	
	private String sanitize(String s) {
    	return s.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
	}
}
