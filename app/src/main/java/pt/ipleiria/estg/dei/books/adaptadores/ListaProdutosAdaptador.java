package pt.ipleiria.estg.dei.books.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.ListaProdutosActivity;
import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.R;
import pt.ipleiria.estg.dei.books.viewholders.ListaProdutosViewHolder;

public class ListaProdutosAdaptador extends RecyclerView.Adapter<ListaProdutosAdaptador.ViewHolder> {
    public Context context;
    private LayoutInflater inflater;
    private ArrayList<Produto> produtos;

    public ListaProdutosAdaptador(Context context, ArrayList<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_lista_produtos, null);
        ViewHolder viewHolder = new ViewHolder(inflate);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto product = produtos.get(position);
        holder.tvNomeProduto.setText(product.getNome());
        holder.tvPrecoProduto.setText(product.getPreco() + " €");
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void setProdutos(ArrayList<Produto> listaProdutos) {
        this.produtos = listaProdutos;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeProduto;
        TextView tvPrecoProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvPrecoProduto = itemView.findViewById(R.id.tvPrecoProduto);
        }
    }
}

