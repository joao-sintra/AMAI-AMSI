package pt.ipleiria.estg.dei.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import pt.ipleiria.estg.dei.books.Modelo.Carrinho;
import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.databinding.ActivityDetalhesProdutoBinding;
import pt.ipleiria.estg.dei.books.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;


public class DetalhesProdutoActivity extends AppCompatActivity implements CarrinhoListener{

    //private ActivityDetalhesProdutoBinding binding;
    private TextView tvNomeProduto, tvPrecoProduto, tvDescricaoProduto, btnAdicionarCarrinho;
    private ImageView imgCapaProduto;
    public static final String PRODUTO = "PRODUTO";
    private CarrinhoListener carrinhoListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);



        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PRODUTO)) {
            Produto produto = (Produto) intent.getParcelableExtra(PRODUTO);


            tvNomeProduto = findViewById(R.id.tvNomeProduto);
            tvNomeProduto.setText(produto.getNome());
            tvPrecoProduto = findViewById(R.id.tvPrecoProduto);
            tvPrecoProduto.setText(produto.getPreco() + " €");
            tvDescricaoProduto = findViewById(R.id.tvDescricaoProduto);
            tvDescricaoProduto.setText(produto.getDescricao());
            imgCapaProduto = findViewById(R.id.imgProduto);
            String imageUrl = "http://172.22.21.211/AMAI-plataformas/frontend/web/public/imagens/produtos/" + produto.getImagem();

            Glide.with(getApplicationContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapaProduto);
            btnAdicionarCarrinho = findViewById(R.id.btnAdicionarCarrinho);
            btnAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Carrinho carrinho = SingletonProdutos.getInstance(getApplicationContext()).getCarrinho();
                if(carrinho == null){
                   // SingletonProdutos.getInstance(getApplicationContext()).adicionarCarrinhoAPI(getApplicationContext());
                    SingletonProdutos.getInstance(getApplicationContext()).getCarrinhoAPI(getApplicationContext());
                    carrinho = SingletonProdutos.getInstance(getApplicationContext()).getCarrinho();
                }else{
                    SingletonProdutos.getInstance(getApplicationContext()).adicionarLinhaCarrinhoAPI(getApplicationContext(), produto,carrinho);
                    Toast.makeText(getApplicationContext(), "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
                }

            }
        }
            );

        }
    }

    @Override
    public void onRefreshListaCarrinho(Carrinho carrinho) {
        carrinho = SingletonProdutos.getInstance(getApplicationContext()).getCarrinho();
    }
}


// Your custom logic here






