package pt.ipleiria.estg.dei.books.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.R;

public class ListaProdutosViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNomeProduto, tvPrecoProduto;
        //  private ImageView imgCapa;

        public ListaProdutosViewHolder(View view) {
            super(view);
            tvNomeProduto = view.findViewById(R.id.tvNomeProduto);
            tvPrecoProduto = view.findViewById(R.id.tvPrecoProduto);
            //imgCapa = view.findViewById(R.id.imgCapa);
        }

        public void update(Produto produto) {
            tvNomeProduto.setText(produto.getNome());
            tvPrecoProduto.setText(produto.getPreco()+" €");

            //imgCapa.setImageResource(livro.getCapa());
           /* Glide.with(context)
                    .load(produto.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }*/
        }


}
