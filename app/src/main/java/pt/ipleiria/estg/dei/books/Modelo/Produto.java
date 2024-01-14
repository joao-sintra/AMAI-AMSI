package pt.ipleiria.estg.dei.books.Modelo;

public class Produto {
    private int id,iva_id, categoria_produto_id;
    private String nome, descricao,obs;
    private float preco;

    public Produto(int id, String nome, String descricao,float preco, String obs, int categoria_produto_id, int iva_id) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.obs = obs;
        this.preco = preco;
        this.iva_id = iva_id;
        this.categoria_produto_id = categoria_produto_id;

    }

    public int getId() {
        return id;
    }

    public int getIvaId() {
        return iva_id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getObs() {
        return obs;
    }


    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getCategoriaProdutosId() {
        return categoria_produto_id;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", iva_id=" + iva_id +
                ", categoria_produto_id=" + categoria_produto_id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", obs='" + obs + '\'' +
                ", preco=" + preco +
                '}';
    }
}
