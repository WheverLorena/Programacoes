package br.pharma.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Classe contendo os dados da compra
 *
 * @author Inocencio
 */

public class ComprasModel {
	private long codigo;
	private int qtd;
	private FornecedorModel fornecedor;
	private double valorTotal;
	private Calendar dataCompras;
	private int codigo_prod;
	private Situacao situacao;
	private List<ItemCompra> itens;
    private List<ItemCompra> itensRemover;
	
    public ComprasModel() {
        this.codigo = 0;
        this.fornecedor = new FornecedorModel();
        //this.dataCompras = new Calendar().getInstance();
        this.valorTotal = 0.0;
        this.itens = new ArrayList<>();
        this.itensRemover = new ArrayList<>();
    }

    public ComprasModel(int codigo) {
        this.codigo = codigo;
        this.fornecedor = new FornecedorModel();
//        this.dataCompra = new Date();
        this.valorTotal = 0.0;
        this.itens = new ArrayList<>();
        this.itensRemover = new ArrayList<>();

    }
    
	public long getCodigo() {
		return codigo;
	}
	public int getQtd() {
		return qtd;
	}
	public FornecedorModel getFornecedor() {
		return fornecedor;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public Calendar getDataCompras() {
		return dataCompras;
	}
	
	public int getCodigo_prod() {
		return codigo_prod;
	}
	public Situacao getSituacao() {
		return situacao;
	}
	
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	public void setFornecedor(FornecedorModel fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public void setDataCompras(Calendar dataCompras) {
		this.dataCompras = dataCompras;
	}
	public void setCodigo_prod(int codigo_prod) {
		this.codigo_prod = codigo_prod;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	public void setSituacao(int situacao) {
        if (situacao == Situacao.ABERTA.getId()) {
            setSituacao(Situacao.ABERTA);
        } else if (situacao == Situacao.FINALIZADA.getId()) {
            setSituacao(Situacao.FINALIZADA);
        } else if (situacao == Situacao.CANCELADA.getId()) {
            setSituacao(Situacao.CANCELADA);
        }
    }

    public List<ItemCompra> getItens() {
        return itens;
    }

    public List<ItemCompra> getItensRemover() {
        return itensRemover;
    }

    public void addItem(ItemCompra itemCompra) {
        itens.add(itemCompra);
    }

    public void removeItem(ItemCompra itemCompra) {
        itens.remove(itemCompra);
        if (itemCompra.getCodigo() != 0) {
            itensRemover.add(itemCompra);
        }
    }

    public int quantidadeItens() {
        return itens.size();
    }

    @Override
    public String toString() {
        return String.valueOf(this.codigo);
    }
}
