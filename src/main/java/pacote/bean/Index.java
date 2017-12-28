package pacote.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ManagedBean(name = "index")
public class Index extends Base {

	public String mensagem;
	
	@PostConstruct
	public void init() {
		// TODO: Checar se usuário está logado
		if(this.autenticaUsuario() == false) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletResponse resposta = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			try {
				resposta.sendRedirect("login.xhtml");
			}catch(Exception ex) {
				ex.printStackTrace();
			}					
		}
	}
	
}
