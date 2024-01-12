package pt.ipleiria.estg.dei.books.Modelo;

public class Favoritos {

    private int id, id_user, id_produto;

    public Favoritos(int id, int id_user, int id_produto) {
        this.id = id;
        this.id_user = id_user;
        this.id_produto = id_produto;
    }

    public int getId() {
        return id;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_produto() {
        return id_produto;
    }
}
