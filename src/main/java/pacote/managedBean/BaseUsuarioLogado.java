package pacote.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseUsuarioLogado extends Base {
	
	@PostConstruct
	public void init() {
		if(this.autenticaUsuario() == false) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletResponse resposta = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			try {
				resposta.sendRedirect("login.xhtml");
			}catch(Exception ex) {
				ex.printStackTrace();
			}					
		}else {
			this.mensagem = "Seja bemvindo";
		}
	}

}
