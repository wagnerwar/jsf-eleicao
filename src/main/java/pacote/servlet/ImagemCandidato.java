package pacote.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pacote.bean.CandidatoBean;
import pacote.dao.CandidatoDB;

@WebServlet("/imagemCandidato")
public class ImagemCandidato extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			CandidatoDB db = new CandidatoDB();
			String id = request.getParameter("id_candidato");
			
			CandidatoBean candidato = db.getCandidato(id);
			if(candidato != null) {
				byte[] bytes = db.getFotoCandidatoBinary(candidato);
				if(bytes != null) {
					String tipo_foto = db.getTipoFotoCandidato(candidato);					
					response.reset();
					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", 0);
					response.setContentType(tipo_foto);					
					response.setContentLength(bytes.length);
					response.getOutputStream().write(bytes);
				}
			}
		}catch(Exception ex) {
			out.println("Erro no processamento da requisição");
			ex.printStackTrace();
		}
	}
}
