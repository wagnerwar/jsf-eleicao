package pacote.bean;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
}
