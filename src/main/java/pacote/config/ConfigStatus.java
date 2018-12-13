package pacote.config;

public enum ConfigStatus {
    ATIVO("1"),
    INATIVO("0"),
    DESCRICAO_ATIVO("Ativo"),
    DESCRICAO_INATIVO("Inativo"),
    MASCULINO("M"),
    FEMININO("F"),
    DESCRICAO_MASCULINO("Masculino"),
    DESCRICAO_FEMININO("Feminino");
	
	private String valor;

	ConfigStatus(String valor) {
		this.valor = valor;
	}
	
	public String valor() {
		return this.valor;
	}
 
}