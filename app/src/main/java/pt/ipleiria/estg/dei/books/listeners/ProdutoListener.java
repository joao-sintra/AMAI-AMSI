package pt.ipleiria.estg.dei.books.listeners;

import pt.ipleiria.estg.dei.books.Modelo.Produto;

public interface ProdutoListener {
    void onItemClick(int position, Produto product);

}
