package pt.ipleiria.estg.dei.books;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.databinding.ActivityDetalhesProdutoBinding;
import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;


public class DetalhesProdutoActivity extends AppCompatActivity {

    private ActivityDetalhesProdutoBinding binding;
    private TextView tvNomeProduto, tvPrecoProduto, tvDescricaoProduto;
    private ImageView imgCapaProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);


        Intent intent = getIntent();
        if (intent != null) {


                tvNomeProduto = findViewById(R.id.tvNomeProduto);
                tvNomeProduto.setText(intent.getStringExtra("NOME"));
                tvPrecoProduto = findViewById(R.id.tvPrecoProduto);
                tvPrecoProduto.setText(intent.getStringExtra("PRECO"));
                tvDescricaoProduto = findViewById(R.id.tvDescricaoProduto);
                tvDescricaoProduto.setText(intent.getStringExtra("DESCRICAO"));
                imgCapaProduto = findViewById(R.id.imgProduto);
            String imageUrl= "http://172.22.21.211/AMAI-plataformas/frontend/web/public/imagens/produtos/"+intent.getStringExtra("IMAGEM");

            Glide.with(getApplicationContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapaProduto);
        }

            }
        }


        // Your custom logic here






