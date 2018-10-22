package pacote.validator;


import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;
 
@FacesValidator("custom.numeroValidator")
public class NumeroValidator implements Validator, ClientValidator{
	
	private Pattern pattern;
	  
    private static final String NUMBER_PATTERN = "^[0-9]+$";

	@Override
	public Map<String, Object> getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValidatorId() {
		// TODO Auto-generated method stub
		return "custom.numeroValidator";
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		pattern = Pattern.compile(NUMBER_PATTERN);
		if(value == null) {
            return;
        }
         
        if(!pattern.matcher(value.toString()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação", 
                        value + " não é um número válido;"));
        }
	}
}
