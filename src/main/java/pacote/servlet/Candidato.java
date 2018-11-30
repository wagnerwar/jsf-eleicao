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
		System.out.println("CandidatoBean inicializado");
		this.informacoes = new CandidatoBean();
		this.informacoes.setCpf(null);
		RequestContext.getCurrentInstance().reset("frmCandidato");
	}
	
}
