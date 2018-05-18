package br.pharma.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Classe contendo os dados da venda
 *
 * @author Inocencio
 */
public class VendaModel {

    private int codigo;
    private ClienteModel cliente;
    private Calendar dataVenda;
    private Double valorTotal;
    private Situacao situacao;
    private List<ItemVenda> itens;
    private List<ItemVenda> itensRemover;

    public VendaModel() {
        this.codigo = 0;
        this.cliente = new ClienteModel();
		this.dataVenda = Calendar.getInstance();
        this.setValorTotal(0.0);
        this.itens = new ArrayList<>();
        this.itensRemover = new ArrayList<>();
    }

    public VendaModel(int codigo) {
        this.codigo = codigo;
        this.cliente = new ClienteModel();
        this.dataVenda = Calendar.getInstance();
        this.setValorTotal(0.0);
        this.itens = new ArrayList<>();
        this.itensRemover = new ArrayList<>();

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public Calendar getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Calendar dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getValorTotal() {
        double total = 0;
        for (ItemVenda i : itens) {
            total += (i.getValorUnitario() * i.getQuantidade());
        }
        return total;
    }

    public Situacao getSituacao() {
        return situacao;
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

    public List<ItemVenda> getItens() {
        return itens;
    }

    public List<ItemVenda> getItensRemover() {
        return itensRemover;
    }

    public void addItem(ItemVenda itemVenda) {
        itens.add(itemVenda);
    }

    public void removeItem(ItemVenda itemVenda) {
        itens.remove(itemVenda);
        if (itemVenda.getCodigo() != 0) {
            itensRemover.add(itemVenda);
        }
    }

    public int quantidadeItens() {
        return itens.size();
    }

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
