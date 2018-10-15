package pacote.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pacote.config.ConfigStatus;

public class EleicaoBean implements Serializable{
	public String nome;
	public String descricao;
	public String id;
	public String status;
	public List<CargoBean> cargos;
	public Date dataInicio;
	public Date dataFim;
	public String statusDescricao;
	public String dataInicioDescricao;
	public String dataFimDescricao;
	public List<String> cargosSelected;
	
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
	
	public void setCargos(List<CargoBean> cargos) {
		this.cargos = cargos;
	}
	
	public List<CargoBean> getCargos(){
		return this.cargos;
	}
	
	public void setCargosSelected(List<String> cargos) {
		this.cargosSelected = cargos;
	}
	
	public List<String> getCargosSelected(){
		return this.cargosSelected;
	}
	
	public void setDataInicio(Date dt_inicio) {
		this.dataInicio = dt_inicio;
	}
	
	public Date getDataInicio() {
		return this.dataInicio;
	}
	
	public void setDataFim(Date dt_fim) {
		this.dataFim = dt_fim;
	}
	
	public Date getDataFim() {
		return this.dataFim;
	}
	
	public void setStatusDescricao(String nome) {
		this.statusDescricao = nome;
	}
	
	public String getStatusDescricao() {
		return this.statusDescricao;
	}
	
	public void setDataInicioDescricao(String nome) {
		this.dataInicioDescricao = nome;
	}
	
	public String getDataInicioDescricao() {
		return this.dataInicioDescricao;
	}
	
	public void setDataFimDescricao(String nome) {
		this.dataFimDescricao = nome;
	}
	
	public String getDataFimDescricao() {
		return this.dataFimDescricao;
	}
	
	public void limpar() {
		this.setNome(null);
		this.setStatus(null);
		this.setDataInicio(null);
		this.setDataFim(null);
		this.setId(null);
	}
	
}
