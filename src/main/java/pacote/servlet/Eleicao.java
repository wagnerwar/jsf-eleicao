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
import pacote.bean.EleicaoBean;

@ManagedBean(name = "eleicao")
@ViewScoped
public class Eleicao extends BaseUsuarioLogado implements Serializable {
	private static final long serialVersionUID = 2L;
	public EleicaoBean selecionado;
	public EleicaoBean eleicao;
	
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
	
	public void cadastrar() {
		
	}
	
	public void atualizar() {
		
	}

	public void limpar() {
		
	}
}
