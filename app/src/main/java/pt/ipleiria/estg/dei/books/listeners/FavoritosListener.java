package pt.ipleiria.estg.dei.books.listeners;

import pt.ipleiria.estg.dei.books.Modelo.Favoritos;
import pt.ipleiria.estg.dei.books.Modelo.Produto;

public interface FavoritosListener {
    void onFavoritosItemRemoved(Favoritos favoritos);

}
