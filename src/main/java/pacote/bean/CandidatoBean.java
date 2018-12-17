package pacote.bean;

import java.io.Serializable;
import java.util.Date;

import pacote.config.ConfigStatus;


public class CandidatoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id;
	public String nome;
	public String sobrenome;
	public String cpf;
	public String cpf_formatado;
	public Date dataNascimento;
	public String genero;
	public String generoDescricao;
	
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
	
	public void setCpfFormatado(String cpf) {
		this.cpf_formatado = cpf;
	}
	
	public String getCpfFormatado() {
		return this.cpf_formatado;
	}
	
	public Date getDataNascimento() {
		return this.dataNascimento;
	}
	
	public void setDataNascimento(Date data) {
		this.dataNascimento = data;
	}
	
	public void setGenero(String genero) {
		if(genero == null) {
			this.setGeneroDescricao("Não definido");
		}else {
			if(genero.equals(ConfigStatus.MASCULINO.valor())) {
				this.setGeneroDescricao(ConfigStatus.DESCRICAO_MASCULINO.valor());
			}else if(genero.equals(ConfigStatus.FEMININO.valor())) {
				this.setGeneroDescricao(ConfigStatus.DESCRICAO_FEMININO.valor());
			}
		}
		
		this.genero = genero;
	}
	
	public String getGenero() {
		
		return this.genero;
	}
	
	public void setGeneroDescricao(String genero) {
		this.generoDescricao = genero;
	}
	
	public String getGeneroDescricao() {
		return this.generoDescricao;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
