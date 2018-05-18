package br.pharma.model;

public class MedicamentoModel {
	private long codigo;
	private String descri�ao;
	private int qtd;
	private double precoVenda;
	private double precoCompra;
	
	
	public MedicamentoModel() {
        this.codigo = 0;
        this.descri�ao = "";
        this.setPrecoCompra(0.0);
        this.precoVenda = 0.0;
        this.qtd = 0;
    }

    public MedicamentoModel(int codigo) {
        this.codigo = codigo;
        this.descri�ao = "";
        this.setPrecoCompra(0.0);
        this.precoVenda = 0.0;
        this.qtd = 0;
    }
	
	public MedicamentoModel(String desc, int qtd, double valor) {
		this.descri�ao = desc; this.qtd = qtd; this.precoVenda = valor;
	}
	public MedicamentoModel(String desc, int qtd, double valor, long codigo) {
		this.descri�ao = desc; this.qtd = qtd; this.precoVenda = valor;
		this.codigo = codigo;
	}

	public long getCodigo() {
		return codigo;
	}
	public String getDescri�ao() {
		return descri�ao;
	}
	public int getQtd() {
		return qtd;
	}
	public double getprecoVenda() {
		return precoVenda;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public void setDescri�ao(String descri�ao) {
		this.descri�ao = descri�ao;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public void setprecoVenda(double valorUnit) {
		this.precoVenda = valorUnit;
	}
	public double getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(double precoCompra) {
		this.precoCompra = precoCompra;
	}
	
	public void medicamentos(MedicamentoModel md){
		System.out.println("\n"+md.getCodigo());
		System.out.println(md.getDescri�ao());
		System.out.println(md.getQtd());
		System.out.println(md.getprecoVenda()+"\n");
	}

	
}
