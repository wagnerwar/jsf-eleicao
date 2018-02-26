package pacote.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ManagedBean(name = "index")
public class Index extends BaseUsuarioLogado {

	public String mensagem;
	
	@PostConstruct
	public void init() {
		super.init();
	}
	
}
