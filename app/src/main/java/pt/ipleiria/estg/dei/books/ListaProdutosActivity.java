package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.adaptadores.ListaProdutosAdaptador;
import pt.ipleiria.estg.dei.books.listeners.ProdutosListener;

public class ListaProdutosActivity extends AppCompatActivity implements ProdutosListener {

    private TextView tvNomeProduto, tvPrecoProduto;
    public ArrayList<Produto> listaProdutos;
    private RecyclerView rvProdutos;
    private ListaProdutosAdaptador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        setTitle("Lista de Produtos");

        SingletonProdutos.getInstance(getApplicationContext()).setProdutosListener(this);
        SingletonProdutos.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());
        rvProdutos = findViewById(R.id.listProdutos);
        rvProdutos.setLayoutManager(new GridLayoutManager(this, 2));
        listaProdutos = SingletonProdutos.getInstance(getApplicationContext()).getProdutos();

    }

    @Override
    public void onRefreshListaProdutos(ArrayList<Produto> listaProdutos) {
        rvProdutos = findViewById(R.id.listProdutos);
        adapter = new ListaProdutosAdaptador(getApplicationContext(), listaProdutos);
        rvProdutos.setAdapter(adapter);


    }
}