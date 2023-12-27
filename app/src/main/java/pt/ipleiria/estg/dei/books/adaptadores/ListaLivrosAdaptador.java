package pt.ipleiria.estg.dei.books.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Livro;
import pt.ipleiria.estg.dei.books.R;

public class ListaLivrosAdaptador extends BaseAdapter {
    public Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public ListaLivrosAdaptador(Context context, ArrayList<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int position) {
        return livros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return livros.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = inflater.inflate(R.layout.item_lista_produtos, null);
        }

        //otimização
        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null) {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }
        viewHolder.update(livros.get(position));

        return view;
    }

    private class ViewHolderLista {
        private TextView tvTitulo, tvSerie, tvAutor, tvAno;
        private ImageView imgCapa;

        public ViewHolderLista (View view) {
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvSerie = view.findViewById(R.id.tvSerie);
            tvAutor = view.findViewById(R.id.tvAutor);
            tvAno = view.findViewById(R.id.tvAno);
            imgCapa = view.findViewById(R.id.imgCapa);
        }

        public void update(Livro livro) {
            tvTitulo.setText(livro.getTitulo());
            tvSerie.setText(livro.getSerie());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(livro.getAno()+"");
            //imgCapa.setImageResource(livro.getCapa());
            Glide.with(context)
                    .load(livro.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }
    }
}
