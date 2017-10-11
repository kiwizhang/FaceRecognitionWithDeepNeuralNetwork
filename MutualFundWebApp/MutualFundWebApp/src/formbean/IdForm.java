package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class IdForm extends FormBean {
    private String id;

    public String getId() { return id;    }

    public int getIdAsInt() {
        return Integer.parseInt(id);
    }

    public void setId(String id) { 
        this.id = sanitize(id);
        if(this.id != null) this.id = this.id.trim();
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (id == null || id.length() == 0) {
            errors.add("You must choose a customer.");
            return errors;
        }

        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            errors.add("ID is not an integer.");
        }

        return errors;
    }

    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }
}
