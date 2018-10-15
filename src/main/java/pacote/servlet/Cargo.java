package pacote.servlet;

import java.io.Serializable;
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
import pacote.bean.CargoBean;

@ManagedBean(name = "cargo")
@ViewScoped
public class Cargo extends BaseUsuarioLogado implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String nome;
	public String descricao;
	public String status;
	public String id;
	public List<CargoBean> cargos;
	public CargoBean selecionado;
	public int quantidade;
	
	@PostConstruct
    public void init() {
		try {
			CargoDB model = new CargoDB();
			this.cargos = model.listarCargos();
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));			
		}
    }
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setCargos(List<CargoBean> cargos) {
		this.cargos = cargos;
	}
	
	public List<CargoBean> getCargos(){
		return this.cargos;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public int getQuantidade() {
		return this.quantidade;
	}
	
	public void cadastrar() {
		try {
			CargoDB model = new CargoDB();
			CargoBean cargoBean = new CargoBean();
			cargoBean.nome = this.getNome();
			cargoBean.descricao = this.getDescricao();
			cargoBean.status = this.getStatus();
			cargoBean.quantidade = this.getQuantidade();
			if(model.cadastrar(cargoBean)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success", "Registro inserido com sucesso"));
			}else {
				throw new Exception("Erro ao inserir documento");
			}
			
			this.limpar();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}
	
	public void atualizar() {
		try {
			CargoDB model = new CargoDB();
			CargoBean cargoBean = new CargoBean();
			cargoBean.nome = this.getSelecionado().getNome();
			cargoBean.descricao = this.getSelecionado().getDescricao();
			cargoBean.status = this.getSelecionado().getStatus();
			cargoBean.id = this.getSelecionado().getId();
			boolean retorno = model.atualizar(cargoBean);
			if(retorno == true) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success", "Sucesso ao atualizar registro"));
				this.cargos = model.listarCargos();
			}else {
				throw new Exception("Erro ao atualizar registros");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}

	public void excluir() {
		try {
			CargoDB model = new CargoDB();
			CargoBean cargoBean = new CargoBean();
			cargoBean.id = this.getSelecionado().getId();
			boolean retorno = model.excluir(cargoBean);
			if(retorno == true) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success", "Sucesso ao excluir registro"));
				this.cargos = model.listarCargos();
			}else {
				throw new Exception("Erro ao remover registro");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", ex.getMessage()));
		}
	}
	
	public void limpar() {
		this.setDescricao(null);
		this.setNome(null);
		this.setStatus(null);
		this.setId(null);
		this.setQuantidade(0);
	}
	
	public CargoBean getSelecionado() {
		return this.selecionado;
	}
	
	public void setSelecionado(CargoBean selecionado) {
		this.selecionado = selecionado;
	}
}
