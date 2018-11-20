package model;

public class Endereco {

	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
		

	//construtor
	public Endereco (String rua, String bairro, String cidade, String estado){
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	
	//setters e getters
	public void setRua (String rua){
		this.rua = rua;
	}
	public String getRua(){
		return this.rua;
	}
	public void setBairro(String bairro){
		this.bairro = bairro;
	}
	public String getBairro(){
		return this.bairro;
	}
	public void setCidade (String cidade){
		this.cidade = cidade;
	}
	public String getCidade(){
		return this.cidade;
	}
	public void setEstado(String estado){
		this.estado = estado;
	}
	public String getEstado(){
		return this.estado;
	}
							
					
}
