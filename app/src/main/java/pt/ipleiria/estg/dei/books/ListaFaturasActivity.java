package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Fatura;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.adaptadores.ListaFaturasAdaptador;
import pt.ipleiria.estg.dei.books.listeners.FaturaListener;
import pt.ipleiria.estg.dei.books.listeners.FaturasListener;

public class ListaFaturasActivity extends AppCompatActivity implements FaturasListener, FaturaListener {
    private RecyclerView rvListaFaturas;
    private ArrayList<Fatura> faturas;
    private FaturasListener faturasListener;
    private FaturaListener faturaListener;
    private ListaFaturasAdaptador adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_faturas);


        SingletonProdutos.getInstance(getApplicationContext()).setFaturasListener(this);
        SingletonProdutos.getInstance(getApplicationContext()).getFaturasAPI(getApplicationContext());
        faturas = SingletonProdutos.getInstance(getApplicationContext()).getFaturas();
        rvListaFaturas = findViewById(R.id.rvListaFaturas);

        // Use LinearLayoutManager instead of GridLayoutManager
        rvListaFaturas.setLayoutManager(new GridLayoutManager(this,1));

        adapter = new ListaFaturasAdaptador(this, this, faturas);
        rvListaFaturas.setAdapter(adapter);

    }

    @Override
    public void onRefreshListaFatura(ArrayList<Fatura> listaFaturas) {

        listaFaturas = SingletonProdutos.getInstance(getApplicationContext()).getFaturas();
       // rvListaFaturas= findViewById(R.id.rvListaFaturas);
        adapter = new ListaFaturasAdaptador(this, this, listaFaturas);
        rvListaFaturas.setAdapter(adapter);



    }


    @Override
    public void onItemClick(int position, Fatura fatura) {
        //send to another activity
        SingletonProdutos.getInstance(getApplicationContext()).getLinhasFaturasAPI(getApplicationContext(),fatura);
        SingletonProdutos.getInstance(getApplicationContext()).setFaturaListener(this);
        Intent itent = new Intent(this, FaturaActivity.class);

        itent.putExtra("fatura", fatura.getId());
        startActivity(itent);
    }



}