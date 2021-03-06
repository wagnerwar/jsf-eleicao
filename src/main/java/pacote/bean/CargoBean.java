package pacote.bean;

import java.io.Serializable;
import java.util.List;

import pacote.config.ConfigStatus;

public class CargoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String nome;
	public String descricao;
	public String status;
	public String id;
	public String statusDescricao;
	public int quantidade;
	public List<CandidatoBean> vagaCandidato;
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setDescricao(String nome) {
		this.descricao = nome;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setStatus(String nome) {
		this.status = nome;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setId(String nome) {
		this.id = nome;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setStatusDescricao(String nome) {
		this.statusDescricao = nome;
	}
	
	public String getStatusDescricao() {
		return this.statusDescricao;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public int getQuantidade() {
		return this.quantidade;
	}
	
	public void setVagaCandidato(List<CandidatoBean> candidato) {
		this.vagaCandidato = candidato;
	}
	
	public List<CandidatoBean> getVagaCandidato(){
		return this.vagaCandidato;
	}
	
}
