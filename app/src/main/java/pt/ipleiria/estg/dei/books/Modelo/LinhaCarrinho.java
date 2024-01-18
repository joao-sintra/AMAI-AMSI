package pt.ipleiria.estg.dei.books.Modelo;

public class LinhaCarrinho {
    int idLinha, quantidade, carrinhoID, produtoID;
    float valorIva, precoVenda, subtotal;

    public LinhaCarrinho(int idLinha, int quantidade, int carrinhoID, int produtoID, float valorIva, float precoVenda, float subtotal) {

        this.idLinha = idLinha;
        this.quantidade = quantidade;
        this.carrinhoID = carrinhoID;
        this.produtoID = produtoID;
        this.valorIva = valorIva;
        this.precoVenda = precoVenda;
        this.subtotal = subtotal;
    }

    public int getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(int idLinha) {
        this.idLinha = idLinha;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCarrinhoID() {
        return carrinhoID;
    }

    public void setCarrinhoID(int carrinhoID) {
        this.carrinhoID = carrinhoID;
    }

    public int getProdutoID() {
        return produtoID;
    }

    public void setProdutoID(int produtoID) {
        this.produtoID = produtoID;
    }

    public float getValorIva() {
        return valorIva;
    }

    public void setValorIva(float valorIva) {
        this.valorIva = valorIva;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public void adicionarQuantidade() {
        this.quantidade++;
    }

    public void diminuirQuantidade() {
        if (this.quantidade > 1)
            this.quantidade --;
    }

    @Override
    public String toString() {
        return "LinhaCarrinho{" +
                "idLinha=" + idLinha +
                ", quantidade=" + quantidade +
                ", carrinhoID=" + carrinhoID +
                ", produtoID=" + produtoID +
                ", valorIva=" + valorIva +
                ", precoVenda=" + precoVenda +
                ", subtotal=" + subtotal +
                '}';
    }
}
