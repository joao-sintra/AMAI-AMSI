package pt.ipleiria.estg.dei.books.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Fatura;

public interface FaturasListener {
    void onRefreshListaFatura(ArrayList<Fatura> listaFaaturas);
}
