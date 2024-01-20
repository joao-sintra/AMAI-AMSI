package pt.ipleiria.estg.dei.books.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.books.Modelo.Favoritos;
import pt.ipleiria.estg.dei.books.Modelo.FavoritosBDHelper;
import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.databinding.ItemBestProdutosBinding;
import pt.ipleiria.estg.dei.books.databinding.ItemFavoritosBinding;
import pt.ipleiria.estg.dei.books.listeners.FavoritosListener;


public class FavoritosAdaptador extends RecyclerView.Adapter<FavoritosAdaptador.ViewHolder> {

    private Context context;
    private FavoritosListener FavoritosListener;

    private ArrayList<Favoritos> favoritos;

    public FavoritosAdaptador(Context context, ArrayList<Favoritos> favoritos, FavoritosListener FavoritosListener) {
        this.context = context;
        this.favoritos = favoritos;
        this.FavoritosListener = FavoritosListener;

    }

    public void setFavoritosListener(FavoritosListener FavoritosListener) {
        this.FavoritosListener = FavoritosListener;
    }


    public void removeFavorito(Favoritos favorito) {
        if (favoritos.contains(favorito)) {
            favoritos.remove(favorito);
            notifyDataSetChanged();
            Log.d("FavoritosAdaptador", "Dataset updated after removal");
        }
    }

    @NonNull
    @Override
    public FavoritosAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoritosBinding binding = ItemFavoritosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoritosAdaptador.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritosAdaptador.ViewHolder holder, int position) {
        Favoritos favorito = favoritos.get(position);
        String imageUrl = "http://172.22.21.211/AMAI-plataformas/frontend/web/public/imagens/produtos/" + favorito.getImagemProduto();

        holder.binding.nomeTxt.setText(favorito.getNomeProduto());
        holder.binding.precoTxt.setText(favorito.getPrecoProduto() + "€");


        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new CenterCrop(), new RoundedCorners(30)).
                into(holder.binding.pic);

        holder.binding.favoritosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FavoritosBDHelper dbHelper = new FavoritosBDHelper(context);
                    dbHelper.removerEstadoFavoritosBD(favorito.getId_user(), favorito.getIdProduto());

                    if (FavoritosListener != null) {
                        FavoritosListener.onFavoritosItemRemoved(favorito);
                    }
                } catch (Exception e) {
                    Log.e("FavoritosAdaptador", "Error during removal", e);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemFavoritosBinding binding;

        public ViewHolder(@NonNull ItemFavoritosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
