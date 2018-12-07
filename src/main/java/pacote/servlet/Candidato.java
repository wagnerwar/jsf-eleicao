package pacote.servlet;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

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
