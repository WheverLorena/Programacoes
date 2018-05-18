package br.pharma.model;

import java.util.Calendar;;

public class EstoqueModel {
	private int id;
	private double entrada_dia;
	private double entrada_total;
	private double saida_dia;
	private double saida_total;
	private Calendar data_entrada;
	private int id_prod;
	
	public EstoqueModel() {
		
	}
	
	public EstoqueModel(double entDIa, double saidaDia, double saidaTotal, Calendar data, int idprod){
		this.entrada_dia = entDIa; this.saida_dia = saidaDia;
		this.data_entrada = data; this.saida_total = saidaTotal;
		this.id_prod = idprod;
	}
	
	public int getId() {
		return id;
	}
	public double getEntrada_dia() {
		return entrada_dia;
	}
	public double getEntrada_total() {
		return entrada_total;
	}
	public double getSaida_dia() {
		return saida_dia;
	}
	public double getSaida_total() {
		return saida_total;
	}
	public Calendar getData_entrada() {
		return data_entrada;
	}
	public int getId_prod() {
		return id_prod;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEntrada_dia(double entrada_dia) {
		this.entrada_dia = entrada_dia;
	}
	public void setEntrada_total(double entrada_total) {
		this.entrada_total = entrada_total;
	}
	public void setSaida_dia(double saida_dia) {
		this.saida_dia = saida_dia;
	}
	public void setSaida_total(double saida_total) {
		this.saida_total = saida_total;
	}
	public void setData_entrada(Calendar data_entrada) {
		this.data_entrada = data_entrada;
	}
	public void setId_prod(int cod_prod) {
		this.id_prod = cod_prod;
	}
	
	public void estoque(EstoqueModel estoque){
		System.out.println(estoque.getId());
		System.out.println(estoque.getEntrada_dia());
		System.out.println(estoque.getEntrada_total());
		System.out.println(estoque.getSaida_dia());
		System.out.println(estoque.getSaida_total());
		System.out.println(estoque.getData_entrada());
		System.out.println(estoque.getId_prod());
	}
}
