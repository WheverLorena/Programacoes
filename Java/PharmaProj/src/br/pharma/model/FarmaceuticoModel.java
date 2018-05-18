package br.pharma.model;

public class FarmaceuticoModel {
	private int id;
	private String login;
	private String senha;
	public int getId() {
		return id;
	}
	public String getLogin() {
		return login;
	}
	public String getSenha() {
		return senha;
	}
	public String getEmail() {
		return email;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String email;

}
