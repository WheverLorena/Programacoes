package br.pharma.model;

public class FarmaciaModel {
	private int id;
	private String nome;
	private String end;
	private String cnpj;
	
	public int getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getEnd() {
		return end;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
