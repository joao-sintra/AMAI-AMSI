package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.adaptadores.ListaProdutosAdaptador;
import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutosListener;

public class ListaProdutosActivity extends AppCompatActivity implements ProdutosListener, ProdutoListener {

    private TextView tvNomeProduto, tvPrecoProduto;
    public ArrayList<Produto> listaProdutos;
    private RecyclerView rvProdutos;
    private ListaProdutosAdaptador adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        SingletonProdutos.getInstance(getApplicationContext()).setProdutosListener(this);
        SingletonProdutos.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());
        rvProdutos = findViewById(R.id.listProdutos);
        rvProdutos.setLayoutManager(new GridLayoutManager(this, 2));
        listaProdutos = SingletonProdutos.getInstance(getApplicationContext()).getProdutos();



    }

    @Override
    public void onRefreshListaProdutos(ArrayList<Produto> listaProdutos) {
        rvProdutos = findViewById(R.id.listProdutos);
        adapter = new ListaProdutosAdaptador(this, getApplicationContext(), listaProdutos);
        rvProdutos.setAdapter(adapter);
        progressBar = findViewById(R.id.progressBarProdutos);
        progressBar.setVisibility(ProgressBar.GONE);



    }

    @Override
    public void onItemClick(int position, Produto product) {
        // Handle the item click with the position information
        Toast.makeText(this, "Clicked item at position " + position, Toast.LENGTH_SHORT).show();

        // Optionally, you can still use the product data as needed
        Intent intent = new Intent(this, DetalhesProdutoActivity.class);
        intent.putExtra("NOME", product.getNome());
        intent.putExtra("PRECO", product.getPreco());
        intent.putExtra("IMAGEM", product.getImagem());
        intent.putExtra("DESCRICAO", product.getDescricao());


        startActivity(intent);
    }

}