package pt.ipleiria.estg.dei.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Livro;
import pt.ipleiria.estg.dei.books.Modelo.SingletonGestorLivros;

public class DinamicoFragment extends Fragment {
    private TextView tvTitulo, tvSerie, tvAutor, tvAno;
    private ImageView imgCapa;

    public DinamicoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dinamico, container, false);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvSerie = view.findViewById(R.id.tvSerie);
        tvAutor = view.findViewById(R.id.tvAutor);
        tvAno = view.findViewById(R.id.tvAno);
        imgCapa = view.findViewById(R.id.imgCapa);

        carregarLivro();
        return view;
    }


    private void carregarLivro() {
        ArrayList<Livro> livros = SingletonGestorLivros.getInstance(getContext()).getLivrosBD();

        if(livros.size()>0){ //se o tiver algum livro
            Livro livro = livros.get(0); //buscar o 1ºlivro da lista
            tvTitulo.setText(livro.getTitulo());
            tvSerie.setText(livro.getSerie());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(livro.getAno() + "");
            //imgCapa.setImageResource(livro.getCapa());
            Glide.with(getContext())
                    .load(livro.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }
    }
}