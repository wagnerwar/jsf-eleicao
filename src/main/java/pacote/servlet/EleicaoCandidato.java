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
import pacote.bean.CandidatoBean;
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
	public String cpf;
	public List<SelectItem> vagas;
	public SelectItem vagaSelecionada;
	
	public SelectItem getVagaSelecionada() {
		return this.vagaSelecionada;
	}
	
	public void setVagaSelecionada(SelectItem v) {
		this.vagaSelecionada = v;
	}
	
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
	
	public List<SelectItem> getVagas(){
		return this.vagas;
	}
	
	public void setVagas(List<SelectItem> vagas) {
		this.vagas = vagas;
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
	
	public void gerarVagas() {
		int x = 0;
		this.vagas = new ArrayList<SelectItem>();
		if(this.getCargoSelecionado().quantidade > 0) {
			SelectItem sl = null;
			for(x = 0; x < this.getCargoSelecionado().quantidade; x++) {
				sl = new SelectItem();
				if(this.getCargoSelecionado().vagaCandidato != null) {
					try {
						if(this.getCargoSelecionado().vagaCandidato.get(x) != null) {
							sl.setLabel(this.getCargoSelecionado().vagaCandidato.get(x).getNome());
							sl.setValue(x);
							//sl.setValue(this.getCargoSelecionado().vagaCandidato.get(x).getId());
						}
					}catch(Exception ex) {
						sl.setLabel(null);
						sl.setValue(x);
					}
				}
				//sl.setLabel("TESTE");
				//sl.setValue(x);
				this.vagas.add(sl);
			}
		}
	}
	
	public void preencherVaga(CandidatoBean candidato) {
		try {
			boolean retorno = false;
			EleicaoDB db = new EleicaoDB();
			retorno = db.preencherVagaCandidato(candidato, this.vagaSelecionada, this.selecionado, this.cargoSelecionado);
			if(retorno == false) {
				throw new Exception("Erro ao associar candidato");
			}else {
				this.init();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES, "Vaga preenchida com sucesso!"));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));			
		}
	}
}
