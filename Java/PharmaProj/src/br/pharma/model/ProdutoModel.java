package br.pharma.model;

public class ProdutoModel {
	private long codigo;
	private String descri�ao;
	private int qtd;
	private double precoVenda;
	private double precoCompra;
	
	
	public ProdutoModel() {
        this.codigo = 0;
        this.descri�ao = "";
        this.setPrecoCompra(0.0);
        this.precoVenda = 0.0;
        this.qtd = 0;
    }

    public ProdutoModel(int codigo) {
        this.codigo = codigo;
        this.descri�ao = "";
        this.setPrecoCompra(0.0);
        this.precoVenda = 0.0;
        this.qtd = 0;
    }
	
	public ProdutoModel(String desc, double precoCompra,double precoVenda, int qtd) {
		this.descri�ao = desc; this.qtd = qtd; 
		this.precoCompra = precoCompra;
		this.precoVenda = precoVenda;
	}
	public ProdutoModel(String desc,double preccoCompra, double precoVenda, int qtd, long codigo) {
		this.descri�ao = desc; this.qtd = qtd; 
		this.precoCompra = preccoCompra;
		this.precoVenda = precoVenda;
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
	
	public void produtos(ProdutoModel md){
		System.out.println("\n"+md.getCodigo());
		System.out.println(md.getDescri�ao());
		System.out.println(md.getQtd());
		System.out.println(md.getprecoVenda()+"\n");
	}

	
}
