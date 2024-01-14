package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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


        rvProdutos = findViewById(R.id.listProdutos);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));

        SingletonProdutos.getInstance(getApplicationContext()).setProdutosListener(this);
        SingletonProdutos.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());


        /*listaProdutos = SingletonProdutos.getInstance(getApplicationContext()).getProdutos();
        adapter = new ListaProdutosAdaptador(getApplicationContext(), listaProdutos);
        rvProdutos.setAdapter(adapter);*/
    }

    @Override
    public void onRefreshListaProdutos(ArrayList<Produto> listaProdutos) {

        if (listaProdutos != null) {
            RecyclerView rvProdutos = findViewById(R.id.listProdutos); // Replace with the actual ID of your RecyclerView
            ListaProdutosAdaptador adaptador = new ListaProdutosAdaptador(getApplicationContext(), listaProdutos);
            rvProdutos.setAdapter(adaptador);
        }
    }

    private ArrayList<Produto> generateDummyData() {
        ArrayList<Produto> productList = new ArrayList<>();
        productList.add(new Produto(1, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));
        productList.add(new Produto(2, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));
        productList.add(new Produto(3, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));
        productList.add(new Produto(4, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));
        productList.add(new Produto(5, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));
        productList.add(new Produto(6, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));
        productList.add(new Produto(7, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));
        productList.add(new Produto(8, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));
        productList.add(new Produto(9, "Bolo de chocolate", "Bolo de chocolate com cobertura de chocolate", 10.00f, "obs", 1, 1));


        // Add more data as needed
        return productList;
    }
}