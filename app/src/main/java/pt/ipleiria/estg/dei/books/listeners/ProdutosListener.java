package pt.ipleiria.estg.dei.books.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Produto;


public interface ProdutosListener {
    void onRefreshListaProdutos(ArrayList<Produto> listaProdutos);


}
