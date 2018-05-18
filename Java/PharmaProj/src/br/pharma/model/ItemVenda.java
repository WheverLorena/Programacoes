package br.pharma.model;

/**
 * Classe de relação entre a venda e o Produto
 *
 * @author Inocencio
 */
public class ItemVenda {

    private long codigo;
    private VendaModel venda;
    private ProdutoModel produto;
    private int quantidade;
    private Double valorUnitario;

    public ItemVenda() {
        this.codigo = 0;
        this.venda = new VendaModel();
        this.produto = new ProdutoModel();
        this.quantidade = 0;
        this.valorUnitario = 0.0;
    }

    public ItemVenda(int codigo) {
        this.codigo = codigo;
        this.venda = new VendaModel();
        this.produto = new ProdutoModel();
        this.quantidade = 0;
        this.valorUnitario = 0.0;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public VendaModel getVenda() {
        return venda;
    }

    public void setVenda(VendaModel venda) {
        this.venda = venda;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return getProduto().getDescriçao();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ItemVenda) {
            ItemVenda iv = (ItemVenda) o;
            if (iv.getCodigo() == this.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
