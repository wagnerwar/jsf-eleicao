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
		System.out.println("Inicialização feita");
		
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
	
	public String getMensagem() {
		return this.mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
