package pacote.bean;

import java.io.Serializable;

public class CargoBean implements Serializable {
	public String nome;
	public String descricao;
	public String status;
	public String id;
	
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
	
}
