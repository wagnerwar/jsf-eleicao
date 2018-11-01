package pacote.bean;

import java.io.Serializable;
import java.util.Date;

import pacote.config.ConfigStatus;


public class CandidatoBean implements Serializable {
	public String nome;
	public String sobrenome;
	public String cpf;
	public Date dataNascimento;
	public String genero;
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getSobrenome() {
		return this.sobrenome;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return this.cpf;
	}
	
	public Date getDataNascimento() {
		return this.dataNascimento;
	}
	
	public void setDataNascimento(Date data) {
		this.dataNascimento = data;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String getGenero() {
		return this.genero;
	}
}
