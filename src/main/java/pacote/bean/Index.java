package pacote.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ManagedBean(name = "indexe")
@SessionScoped()
public class Index extends BaseUsuarioLogado {

	public String mensagem;
	
	@PostConstruct
	public void init() {
		System.out.print("Chamada função INIT");
	}
	
	public void before(ComponentSystemEvent event) {
		System.out.print("Chamada função BEFORE");
		super.init();
	}
	
}
