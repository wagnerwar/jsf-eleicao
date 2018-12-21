package pacote.servlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import pacote.bean.CandidatoBean;
import pacote.bean.EleicaoBean;
import pacote.dao.CandidatoDB;
import pacote.dao.EleicaoDB;
import pacote.config.ConfigStatus;
import pacote.dao.FileManagerMongo;
import pacote.utils.Utils;

@ManagedBean(name = "candidatoFoto")
@ViewScoped
public class CandidatoFoto extends BaseUsuarioLogado implements Serializable {
	
	private CandidatoBean candidato;
	
	private UploadedFile uploadedFile;
		
	
	public void inicializar(CandidatoBean candidato) {
		this.setCandidato(candidato);
	}


	public CandidatoBean getCandidato() {
		return candidato;
	}


	public void setCandidato(CandidatoBean candidato) {
		this.candidato = candidato;
	}
	
	public void upload(FileUploadEvent event) {
		try {
			
			this.setUploadedFile(event.getFile());
			
			CandidatoDB db = new CandidatoDB();
			File file = new File("/tmp", uploadedFile.getFileName());
			String contentType = this.getUploadedFile().getContentType();
			String extensao = Utils.getExtension(contentType);
			boolean retorno = db.subirFoto(file, this.getCandidato(), extensao);
			
			if(retorno == false) {
				throw new Exception("Erro ao subir foto de perfil");
			}
			
			this.setUploadedFile(null);
			
			FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage("Upload completo feito com sucesso!"));
		}catch(Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", ex.getMessage()));
		}
	}


	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}


	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
}
