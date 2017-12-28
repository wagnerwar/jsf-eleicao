package pacote.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@ManagedBean(name = "login")
public class Login extends Base { 
	public String loginUsuario;
	public String senhaUsuario;
	public String mensagem;
	public static String login = "admin";
	public static String senha = "admin";
	
	public String getLoginUsuario() {
		return this.loginUsuario;
	}
	
	public void setLoginUsuario(String login_usuario) {
		this.loginUsuario = login_usuario;
	}
	
	public void setSenhaUsuario(String senha_usuario) {
		this.senhaUsuario = senha_usuario;
	}
	
	public String getSenhaUsuario() {
		return this.senhaUsuario;
	}
	
	public String getMensagem() {
		return this.mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String logar() {
		System.out.println("Houve clique de login");
		System.out.println("Login informado: " + this.getLoginUsuario());
		if(this.getLoginUsuario().equals(Login.login) && this.getSenhaUsuario().equals(Login.senha)) {
			// Guardando informações de usuário na sessão
			try {
				Cookie cookie = new Cookie("usuario", this.getLoginUsuario());
				cookie.setMaxAge(30 * 60);
				
				// Guardando o cookie
				FacesContext context = FacesContext.getCurrentInstance();
				((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(cookie);
				HttpServletResponse resposta = (HttpServletResponse)  context.getExternalContext().getResponse();
				resposta.sendRedirect("index.xhtml");
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return "login";
		}else {
			this.setMensagem("Usuário/senha incorretos");
			System.out.println(this.getMensagem());
			return "login";
		}	
	}
	

}
