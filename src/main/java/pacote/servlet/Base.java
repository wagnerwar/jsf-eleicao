package pacote.servlet;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Base {
	
	public String mensagem;

	public Boolean autenticaUsuario() {
		Boolean retorno = false;
		
		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();

		Cookie[] cookies = request.getCookies();

		if(cookies != null)
		{
		  for(Cookie cookie: cookies)
		  {
		    if(cookie.getName().equals("usuario"))
		    {
		       retorno = true;
		    }
		  }
		}
		
		return retorno;
	}
	
	public String getMensagem() {
		return this.mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public void sair() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
		Cookie[] cookies = request.getCookies();

		if(cookies != null)
		{
		  for(Cookie cookie: cookies)
		  {
		    if(cookie.getName().equals("usuario"))
		    {
		       // TODO: Remover o cookie
		    	cookie.setMaxAge(0);
		    	FacesContext context = FacesContext.getCurrentInstance();
				((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(cookie);
				try {
					context.getExternalContext().redirect("login.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				context.responseComplete();
				
		    }
		  }
		}
	}
}
