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

public class GrelhaLivrosAdaptador extends BaseAdapter {
    public Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public GrelhaLivrosAdaptador(Context context, ArrayList<Livro> livros) {
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
            view = inflater.inflate(R.layout.item_grelha_livro, null);
        }

        //otimização
        ViewHolderGrelha viewHolder = (ViewHolderGrelha) view.getTag();
        if(viewHolder == null) {
            viewHolder = new ViewHolderGrelha(view);
            view.setTag(viewHolder);
        }
        viewHolder.update(livros.get(position));

        return view;
    }

    private class ViewHolderGrelha{
        private ImageView imgCapa;
        private TextView tvTitulo;

        public ViewHolderGrelha (View view) {

            imgCapa = view.findViewById(R.id.imgCapa);
            tvTitulo = view.findViewById(R.id.tvTitulo);
        }

        public void update(Livro livro) {
            Glide.with(context)
                    .load(livro.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
            tvTitulo.setText(livro.getTitulo());
        }
    }
}
