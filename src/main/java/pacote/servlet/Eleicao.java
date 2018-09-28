package pacote.servlet;

import java.io.Serializable;
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

@ManagedBean(name = "eleicao")
@ViewScoped
public class Eleicao extends BaseUsuarioLogado implements Serializable {
	private static final long serialVersionUID = 2L;
	public EleicaoBean selecionado;
	public EleicaoBean eleicao;
	public List<EleicaoBean> eleicoes;
	
	public EleicaoBean getSelecionado() {
		return this.selecionado;
	}
	
	public void setSelecionado(EleicaoBean selecionado) {
		this.selecionado = selecionado;
	}

	public EleicaoBean getEleicao() {
		return this.eleicao;
	}
	
	public void setEleicao(EleicaoBean eleicao) {
		this.eleicao = eleicao;
	}
	
	public List<EleicaoBean> getEleicoes() {
		return this.eleicoes;
	}
	
	public void setEleicoes(List<EleicaoBean> eleicoes) {
		this.eleicoes = eleicoes;
	}
	
	@PostConstruct
    public void init() {
		try {
			this.eleicao = new EleicaoBean();
			EleicaoDB model = new EleicaoDB();
			this.eleicoes = model.listarEleicoes();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));			
		}
    }
	
	public void cadastrar() {
		try {
			EleicaoDB db = new EleicaoDB();
			EleicaoBean bean = this.eleicao;
			Date agora = new Date();
			
			// Validações
			if(agora.compareTo(bean.dataInicio) > 0) {
				throw new Exception("Data de início deve ser maior que a data de hoje");
			}
			
			if(bean.dataInicio.compareTo(bean.dataFim) > 0) {
				throw new Exception("Data de início deve ser menor que a data fim");
			}
			
			if(db.cadastrar(bean)) {
				this.limpar();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success", "Registro inserido com sucesso"));
			}else {
				throw new Exception("Erro ao inserir registro");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}
	
	
	public void limpar() {
		this.setEleicao(null);
		this.setSelecionado(null);
	}
	
	public void excluir() {
		try {
			EleicaoDB model = new EleicaoDB();
			EleicaoBean eleicaoBean = new EleicaoBean();
			eleicaoBean.id = this.getSelecionado().getId();
			boolean retorno = model.excluir(eleicaoBean);
			if(retorno == true) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success", "Sucesso ao excluir registro"));
				this.eleicoes = model.listarEleicoes();
			}else {
				throw new Exception("Erro ao remover registro");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}
	
	public void atualizar() {
		try {
			EleicaoDB model = new EleicaoDB();
			EleicaoBean eleicaoBean = new EleicaoBean();
			eleicaoBean.nome = this.getSelecionado().getNome();
			eleicaoBean.descricao = this.getSelecionado().getDescricao();
			eleicaoBean.status = this.getSelecionado().getStatus();
			eleicaoBean.id = this.getSelecionado().getId();
			boolean retorno = model.atualizar(eleicaoBean);
			if(retorno == true) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success", "Sucesso ao atualizar registro"));
				this.eleicoes = model.listarEleicoes();
			}else {
				throw new Exception("Erro ao atualizar registros");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}
}
