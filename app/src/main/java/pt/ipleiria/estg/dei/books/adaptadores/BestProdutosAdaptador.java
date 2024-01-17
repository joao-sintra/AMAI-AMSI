package pt.ipleiria.estg.dei.books.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.R;
import pt.ipleiria.estg.dei.books.databinding.ItemBestProdutosBinding;

public class BestProdutosAdaptador extends RecyclerView.Adapter<BestProdutosAdaptador.ViewHolder> {

    private ArrayList<Produto> produtos;
    private Context context;

    public BestProdutosAdaptador(Context context, ArrayList<Produto> produtos) {
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBestProdutosBinding binding = ItemBestProdutosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        String imageUrl = "http://172.22.21.211/AMAI-plataformas/frontend/web/public/imagens/produtos/" + produto.getImagem();

        holder.binding.nomeTxt.setText(produto.getNome());
        holder.binding.precoTxt.setText(produto.getPreco() + "€");


        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.pic);
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemBestProdutosBinding binding;

        public ViewHolder(@NonNull ItemBestProdutosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
