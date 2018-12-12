package pacote.servlet;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import pacote.bean.CandidatoBean;
import pacote.bean.EleicaoBean;
import pacote.dao.CandidatoDB;
import pacote.dao.EleicaoDB;

@ManagedBean(name = "candidato")
@ViewScoped
public class Candidato extends BaseUsuarioLogado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CandidatoBean informacoes;
	
	private UploadedFile file;
	
	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
	public CandidatoBean getInformacoes() {
		return informacoes;
	}

	public void setInformacoes(CandidatoBean informacoes) {
		this.informacoes = informacoes;
	}
	
	public void incluir() {
		try {
			
			Date agora = new Date();
			CandidatoDB db = new CandidatoDB();
			// Validações
			if(agora.compareTo(this.getInformacoes().dataNascimento) < 0) {
				throw new Exception("Data de início deve ser maior que a data de hoje");
			}
			
			if(!db.checarDuplicidadeCPF(this.getInformacoes())) {
				throw new Exception("CPF já cadastrado");
			}
			
			if(db.cadastrar(this.informacoes)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES, "Sucesso ao incluir registro"));
				
			}else {
				throw new Exception("Erro ao incluir candidato");
			}
			/*System.out.println(this.getFile());
			if(this.getFile() != null) {				
				throw new Exception("Arquivo subido: " + this.getFile().getFileName() );
			}else {
				throw new Exception("Não existe nenhum arquivo " );
			}*/
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}
	
	 public void handleUpload(FileUploadEvent event) {
		 try {
			 UploadedFile file = event.getFile();
		      this.setFile(file);
		      
		      byte[] contents = file.getContents();
		      String fileContent = new String(contents);
		      String fileName = file.getFileName();
		      System.out.println(fileName); 
		 }catch(Exception ex) {
			 ex.printStackTrace();
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		 }
	 }

	
	public void salvar() {
		
	}
	
	@PostConstruct
    public void init() {
		try {
			this.inicializar();
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));			
		}
    }
	
	public void inicializar() {
		this.informacoes = new CandidatoBean();
		this.informacoes.setCpf(null);
		this.informacoes.setNome(null);
		this.informacoes.setSobrenome(null);
		this.informacoes.setGenero(null);
		this.informacoes.setDataNascimento(null);
		//RequestContext.getCurrentInstance().reslet("frmCandidato");
	}
	
}
