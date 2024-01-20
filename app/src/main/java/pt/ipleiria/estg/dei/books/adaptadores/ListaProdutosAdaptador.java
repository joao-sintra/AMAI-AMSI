package pt.ipleiria.estg.dei.books.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.R;
import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;

public class ListaProdutosAdaptador extends RecyclerView.Adapter<ListaProdutosAdaptador.ViewHolder> {
    private ProdutoListener produtoListener;

    public Context context;
    private List<Produto> filteredProdutos;
    private LayoutInflater inflater;
    private ArrayList<Produto> produtos;

    public ListaProdutosAdaptador(ProdutoListener produtoListener, Context context, ArrayList<Produto> produtos) {
        this.produtoListener = produtoListener;
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_lista_produtos, null);
        ViewHolder viewHolder = new ViewHolder(inflate, produtoListener);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto product = produtos.get(position);
        holder.tvNomeProduto.setText(product.getNome());
        holder.tvPrecoProduto.setText(product.getPreco() + " €");
        String imageUrl = "http://172.22.21.211/AMAI-plataformas/frontend/web/public/imagens/produtos/" + product.getImagem();

        Glide.with(holder.itemView.getContext()).load(imageUrl).transform(new CenterCrop(), new RoundedCorners(30)).into(holder.imgProduto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Notify the listener about the item click and pass the position
                if (produtoListener != null) {
                    produtoListener.onItemClick(position, product);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void setProdutos(ArrayList<Produto> listaProdutos) {
        this.produtos = listaProdutos;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeProduto;
        TextView tvPrecoProduto;
        ImageView imgProduto;

        public ViewHolder(@NonNull View itemView, ProdutoListener produtoListener) {
            super(itemView);
            tvNomeProduto = itemView.findViewById(R.id.nomeTxt);
            tvPrecoProduto = itemView.findViewById(R.id.precoTxt);
            imgProduto = itemView.findViewById(R.id.pic);

        }
    }
}

