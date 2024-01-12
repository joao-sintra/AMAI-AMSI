package pt.ipleiria.estg.dei.books.Modelo;

public class Produto {
    private int id;
    private double preco, iva;
    private String nome, descricao, obs, categoria;

    public Produto(int id, double iva, String categoria, double preco, String nome, String descricao, String obs) {
        this.id = id;
        this.iva = iva;
        this.categoria = categoria;
        this.preco = preco;
        this.nome = nome;
        this.descricao = descricao;
        this.obs = obs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public double getPreco() {
        return preco;
    }

    public double getIva() {
        return iva;
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

    public String getCategoria() {
        return categoria;
    }
}
