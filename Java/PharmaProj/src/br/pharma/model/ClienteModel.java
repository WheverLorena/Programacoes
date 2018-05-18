package br.pharma.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class ClienteModel {
	private long codigo;
	private String nome;
	private String cpf;
	private String sexo;
	private String end;
	private Calendar dataNasc;
	private String cel;
	private String email;
	private String probOsseo;
	private String fumante;
	private String alcoolatra;
	private String gravida;
	private String cronica;
	private String alergico;
	
	public ClienteModel(){
		
	}
	
	// INSERT EXEMPLO
	public ClienteModel(String nome, String cpf, String sexo, String end, String dataNasc, String cell, String email){
		this.nome = nome; this.cpf = cpf; this.sexo = sexo; this.end = end;
		
		try {
			java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dataNasc);
			this.dataNasc = Calendar.getInstance();
			this.dataNasc.setTime(date);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao converter data!");
			e.printStackTrace();
		}
		this.cel = cell;
		this.email = email;
	}
	
	//UPDATE EXEMPLO
	public ClienteModel(String nome, String sexo, String end, String dataNasc, String cell, String email, long codigo){
		this.nome = nome; this.sexo = sexo; this.end = end;
		this.cel = cell; this.email = email; this.codigo = codigo;
		try {
			java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dataNasc);
			this.dataNasc = Calendar.getInstance();
			this.dataNasc.setTime(date);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao converter data!");
			e.printStackTrace();
		}
	 
	}
	
	public long getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public String getCpf() {
		return cpf;
	}
	public String getSexo() {
		return sexo;
	}
	public String getEnd() {
		return end;
	}
	public Calendar getDataNasc() {
		return dataNasc;
	}
	public String getCel() {
		return cel;
	}
	public String getEmail() {
		return email;
	}
	public String getProbOsseo() {
		return probOsseo;
	}
	public String getFumante() {
		return fumante;
	}
	public String getAlcoolatra() {
		return alcoolatra;
	}
	public String getGravida() {
		return gravida;
	}
	public String getCronica() {
		return cronica;
	}
	public String getAlergico() {
		return alergico;
	}
	
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public void setDataNasc(Calendar dataNasc) {
		this.dataNasc = dataNasc;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setProbOsseo(String probOsseo) {
		this.probOsseo = probOsseo;
	}
	public void setFumante(String fumante) {
		this.fumante = fumante;
	}
	public void setAlcoolatra(String alcoolatra) {
		this.alcoolatra = alcoolatra;
	}
	public void setGravida(String gravida) {
		this.gravida = gravida;
	}
	public void setCronica(String cronica) {
		this.cronica = cronica;
	}
	public void setAlergico(String alergico) {
		this.alergico = alergico;
	}
	
	public void clientes(ClienteModel cli) {
		System.out.println("\n"+cli.getCodigo());
		System.out.println(cli.getNome());
		System.out.println(cli.getCpf());
		System.out.println(cli.getSexo());
		System.out.println(cli.getEnd());
		System.out.println(cli.getDataNasc().getTime());
		System.out.println(cli.getCel());
		System.out.println(cli.getEmail()+"\n");
	}
}
