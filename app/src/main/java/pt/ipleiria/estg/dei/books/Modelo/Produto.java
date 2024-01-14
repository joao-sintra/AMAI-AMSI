package pt.ipleiria.estg.dei.books.Modelo;

public class Produto {
    private int id,iva;
    private String nome, descricao,obs, categoria, imagem;
    private float preco;

    public Produto(int id, String nome, String descricao, float preco, String obs, String categoria, int iva, String imagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.obs = obs;
        this.categoria = categoria;
        this.iva = iva;
        this.preco = preco;
        this.imagem = imagem;
    }

    public int getId() {
        return id;
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

    public int getIva() {
        return iva;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImagem() {
        return imagem;
    }
}
