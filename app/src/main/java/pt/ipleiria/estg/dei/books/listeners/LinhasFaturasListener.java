package pt.ipleiria.estg.dei.books.listeners;

import pt.ipleiria.estg.dei.books.Modelo.Fatura;

public interface LinhasFaturasListener {
    void onRefreshListaLinhasFaturas(Fatura fatura);
}
