package pt.ipleiria.estg.dei.books.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.LinhaCarrinho;


public interface LinhasCarrinhosListener {
    void onRefreshListaLinhasCarrinhos(ArrayList<LinhaCarrinho> listaLinhaCarrinho);
}
