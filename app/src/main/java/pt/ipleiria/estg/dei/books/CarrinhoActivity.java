package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Carrinho;
import pt.ipleiria.estg.dei.books.Modelo.LinhaCarrinho;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.adaptadores.LinhaCarrinhoAdaptador;
import pt.ipleiria.estg.dei.books.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.LinhaCarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.LinhasCarrinhosListener;

public class CarrinhoActivity extends AppCompatActivity implements LinhasCarrinhosListener, LinhaCarrinhoListener, CarrinhoListener {
    private RecyclerView rvLinhaProdutos;
    private ArrayList<LinhaCarrinho> listaLinhaCarrinho;
    private TextView tvTotalCarrinho;
    private LinhasCarrinhosListener linhasCarrinhosListener;
    private LinhaCarrinhoAdaptador adapter;
    private CarrinhoListener carrinhoListener;
    private Carrinho carrinho;
    private Button btnFinalizarEncomenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        SingletonProdutos.getInstance(getApplicationContext()).getCarrinhoAPI(getApplicationContext());
        SingletonProdutos.getInstance(getApplicationContext()).setCarrinhoListener(this);
        carrinho = SingletonProdutos.getInstance(getApplicationContext()).getCarrinho();

        rvLinhaProdutos = this.findViewById(R.id.rvListaLinhaCarrinho);
        rvLinhaProdutos.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        listaLinhaCarrinho = SingletonProdutos.getInstance(getApplicationContext()).getLinhaCarrinhos();
        SingletonProdutos.getInstance(getApplicationContext()).setLinhasCarrinhosListener(this);
        btnFinalizarEncomenda = this.findViewById(R.id.btnFinalizarEncomenda);

        btnFinalizarEncomenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);

                startActivity(intent);
            }
        });
        tvTotalCarrinho = this.findViewById(R.id.tvTotalCarrinho);
        if (carrinho != null) {
            tvTotalCarrinho.setText(carrinho.getValorTotal() + " €");
        }
    }

    @Override
    public void onRefreshListaCarrinho(Carrinho carrinho) {
        //carrinho = SingletonProdutos.getInstance(getApplicationContext()).getCarrinho();

        SingletonProdutos.getInstance(getApplicationContext()).getLinhasCarrinhosAPI(getApplicationContext(), carrinho);
        listaLinhaCarrinho = SingletonProdutos.getInstance(getApplicationContext()).getLinhaCarrinhos();
        //SingletonProdutos.getInstance(getApplicationContext()).getCarrinhoAPI(getApplicationContext());


    }

    @Override
    public void onItemUpdate() {

        SingletonProdutos.getInstance(getApplicationContext()).getCarrinhoAPI(getApplicationContext());
        carrinho = SingletonProdutos.getInstance(getApplicationContext()).getCarrinho();

        tvTotalCarrinho.setText(carrinho.getValorTotal() + " €");
        if (carrinho.getValorTotal() == 0.0) {
            btnFinalizarEncomenda.setVisibility(View.INVISIBLE);
        }else {
            btnFinalizarEncomenda.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefreshListaLinhasCarrinhos(ArrayList<LinhaCarrinho> listaLinhaCarrinho) {
        // SingletonProdutos.getInstance(getApplicationContext()).getCarrinhoAPI(getApplicationContext());
        if (carrinho != null) {


            if (carrinho.getValorTotal() == 0.0) {
                btnFinalizarEncomenda.setVisibility(View.INVISIBLE);
            }else {
                btnFinalizarEncomenda.setVisibility(View.VISIBLE);
            }
        }
        carrinho = SingletonProdutos.getInstance(getApplicationContext()).getCarrinho();

        tvTotalCarrinho.setText(carrinho.getValorTotal() + " €");
        adapter = new LinhaCarrinhoAdaptador(getApplicationContext(), this, listaLinhaCarrinho, this, carrinho);
        rvLinhaProdutos.setAdapter(adapter);

    }

}