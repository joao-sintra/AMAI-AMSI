package pt.ipleiria.estg.dei.books;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Livro;
import pt.ipleiria.estg.dei.books.Modelo.SingletonGestorLivros;

public class DetalhesDinamicoActivity extends AppCompatActivity {

    private TextView tvTitulo, tvSerie, tvAutor, tvAno;
    private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_dinamico);
        setTitle("Dados dinâmicos");
        tvTitulo=findViewById(R.id.tvTitulo);
        tvSerie=findViewById(R.id.tvSerie);
        tvAutor=findViewById(R.id.tvAutor);
        tvAno=findViewById(R.id.tvAno);
        imgCapa=findViewById(R.id.imgCapa);

        carregarLivro();
    }

    private void carregarLivro() {
        ArrayList<Livro> livros = SingletonGestorLivros.getInstance(getApplicationContext()).getLivrosBD();

        if(livros.size()>0){ //se o tiver algum livro
            Livro livro = livros.get(0); //buscar o 1ºlivro da lista
            tvTitulo.setText(livro.getTitulo());
            tvSerie.setText(livro.getSerie());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(livro.getAno() + "");
            //imgCapa.setImageResource(livro.getCapa());
            Glide.with(getApplicationContext())
                    .load(livro.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }
    }
}