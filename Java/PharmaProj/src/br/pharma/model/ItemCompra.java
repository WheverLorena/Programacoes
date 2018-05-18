package br.pharma.model;

/**
 * Classe de relação entre a compra e o produto
 *
 * @author Inocencio
 */
public class ItemCompra {

    private long codigo;
    private ComprasModel compra;
    private ProdutoModel medicamento;
    private int quantidade;
    private Double valorUnitario;

    public ItemCompra() {
        this.codigo = 0;
        this.compra = new ComprasModel();
        this.medicamento = new ProdutoModel();
        this.quantidade = 0;
        this.valorUnitario = 0.0;
    }

    public ItemCompra(int codigo) {
        this.codigo = codigo;
        this.compra = new ComprasModel();
        this.medicamento = new ProdutoModel();
        this.quantidade = 0;
        this.valorUnitario = 0.0;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public ComprasModel getCompra() {
        return compra;
    }

    public void setCompra(ComprasModel compra) {
        this.compra = compra;
    }

    public ProdutoModel getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(ProdutoModel produto) {
        this.medicamento = produto;
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
        return getMedicamento().getDescriçao();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ItemCompra) {
            ItemCompra iv = (ItemCompra) o;
            if (iv.getCodigo() == this.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
