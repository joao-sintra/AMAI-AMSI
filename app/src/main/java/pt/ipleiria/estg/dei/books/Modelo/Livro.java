package pt.ipleiria.estg.dei.books.Modelo;

public class Livro {
    private int id, ano;
    private String titulo, serie, autor, capa;
    //private static int autoIncrementId = 1;

    public Livro(int id, String capa, int ano, String titulo, String serie, String autor) {
        this.id = id;
        //this.id = autoIncrementId++;
        this.capa = capa;
        this.ano = ano;
        this.titulo = titulo;
        this.serie = serie;
        this.autor = autor;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
