package pacote.servlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import pacote.dao.ConexaoMongo;
import com.mongodb.client.MongoDatabase;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient; 
import com.mongodb.client.MongoCollection; 
import pacote.dao.CargoDB;
import pacote.dao.EleicaoDB;
import pacote.bean.CargoBean;
import pacote.bean.EleicaoBean;
import javax.faces.model.SelectItem;
import pacote.config.ConfigStatus;

@ManagedBean(name = "eleicaoCandidato")
@ViewScoped
public class EleicaoCandidato extends BaseUsuarioLogado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<EleicaoBean> eleicoes;
	public EleicaoBean selecionado;
	public CargoBean cargoSelecionado;
	
	public List<EleicaoBean> getEleicoes() {
		return this.eleicoes;
	}
	
	public void setEleicoes(List<EleicaoBean> eleicoes) {
		this.eleicoes = eleicoes;
	}
	
	public EleicaoBean getSelecionado() {
		return this.selecionado;
	}
	
	public void setSelecionado(EleicaoBean selecionado) {
		this.selecionado = selecionado;
	}
	
	public CargoBean getCargoSelecionado() {
		return this.cargoSelecionado;
	}
	
	public void setCargoSelecionado(CargoBean cargo) {
		this.cargoSelecionado = cargo;
	}
	
	@PostConstruct
    public void init() {
		try {
			this.setEleicoes(new EleicaoDB().listarEleicoesAtivasNaoIniciadas());
			
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));			
		}
    }
}
