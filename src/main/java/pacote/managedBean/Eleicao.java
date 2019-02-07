package pacote.managedBean;

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

@ManagedBean(name = "eleicao")
@ViewScoped
public class Eleicao extends BaseUsuarioLogado implements Serializable {
	private static final long serialVersionUID = 2L;
	public EleicaoBean selecionado;
	public EleicaoBean eleicao;
	public List<EleicaoBean> eleicoes;
	public List<CargoBean> cargosDisponiveis;
	public List<String> cargosSelecionados;
	private List<SelectItem> cargoOpcoes;
	
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
	
	public List<CargoBean> getCargosDisponiveis() {
		return this.cargosDisponiveis;
	}
	
	public void setCargosDisponiveis(List<CargoBean> cargos) {
		this.cargosDisponiveis = cargos;
	}
	
	public List<String> getCargosSelecionados() {
		return this.cargosSelecionados;
	}
	
	public void setCargosSelecionados(List<String> cargos) {
		this.cargosSelecionados = cargos;
	}
	
	public List<SelectItem> getcargoOpcoes() {
		return this.cargoOpcoes;
	}
	
	public void setCargosOpcoes(List<SelectItem> cargos) {
		this.cargoOpcoes = cargos;
	}
	
	@PostConstruct
    public void init() {
		try {
			this.eleicao = new EleicaoBean();
			EleicaoDB model = new EleicaoDB();
			this.eleicoes = model.listarEleicoes();
			this.cargosDisponiveis = new CargoDB().listarCargosAtivos();
			this.setCargosOpcoes(new ArrayList<SelectItem>());
			
			for(CargoBean c : this.cargosDisponiveis) {
				this.getcargoOpcoes().add(new SelectItem(c.id, c.nome));
			}
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
				this.eleicoes = new EleicaoDB().listarEleicoes();
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
	
	public boolean habilitarEdicao(EleicaoBean bean) {
		Date agora = new Date();
		try {
			if(agora.compareTo(bean.dataInicio) > 0) {
				return false;
			}else {
				return true;
			}			
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean habilitarInclusaoCargo(EleicaoBean bean) {
		Date agora = new Date();
		try {
			if(agora.compareTo(bean.dataInicio) > 0) {
				return false;
			}else {
				return true;
			}			
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean habilitarExclusaoEleicao(EleicaoBean eleicao) {
		try {
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
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
			eleicaoBean.dataInicio = this.getSelecionado().getDataInicio();
			eleicaoBean.dataFim = this.getSelecionado().getDataFim();
			Date agora = new Date();
			
			if(agora.compareTo(eleicaoBean.dataInicio) > 0) {
				this.eleicoes = model.listarEleicoes();
				throw new Exception("Data de início deve ser maior que a data de hoje");
			}
			
			if(eleicaoBean.dataInicio.compareTo(eleicaoBean.dataFim) > 0) {
				this.eleicoes = model.listarEleicoes();
				throw new Exception("Data de início deve ser menor que a data fim");
			}
			
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
	
	public void adicionarCargo() {
		try {
			EleicaoDB db = new EleicaoDB();
			EleicaoBean bean = this.selecionado;
			CargoDB cargoDB = new CargoDB();
			if(this.cargosSelecionados.size() > 0) {
				bean.setCargos(new ArrayList<CargoBean>());
				for(String item: this.cargosSelecionados) {
					CargoBean dados = cargoDB.getCargo(item);
					if(dados != null) {
						bean.getCargos().add(dados);					
					}
				}
				boolean retorno = db.adicionarCargo(bean);
				if(retorno == true) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success", "Sucesso ao atualizar registro"));
					this.eleicoes = new EleicaoDB().listarEleicoes();
				}else {
					throw new Exception("Erro ao atualizar registros");
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}
	
	public void desabilitarEleicao() {
		try {
			EleicaoDB model = new EleicaoDB();
			EleicaoBean eleicaoBean = new EleicaoBean();
			eleicaoBean.status = ConfigStatus.INATIVO.valor();
			eleicaoBean.id = this.getSelecionado().getId();
			System.out.println(eleicaoBean.id);
			boolean retorno = model.atualizarStatus(eleicaoBean);
			if(retorno == true) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success", "Registro desabilitado com sucesso!!"));
				this.eleicoes = model.listarEleicoes();
			}else {
				throw new Exception("Erro ao atualizar registros");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}
	
	public boolean isInativo(EleicaoBean eleicaoBean) {
		if(eleicaoBean.status.equals(ConfigStatus.INATIVO.valor()) ) {
			return true;
		}else {
			return false;
		}
	}
}
