package pt.ipleiria.estg.dei.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Carrinho;
import pt.ipleiria.estg.dei.books.Modelo.LinhaCarrinho;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.adaptadores.LinhaCarrinhoAdaptador;
import pt.ipleiria.estg.dei.books.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.LinhaCarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.LinhasCarrinhosListener;

public class CarrinhosFragment extends Fragment implements LinhasCarrinhosListener, LinhaCarrinhoListener, CarrinhoListener {

    public static final int ADD = 100, EDIT = 200, DEL = 300;
    private RecyclerView rvLinhaProdutos;
    private ArrayList<LinhaCarrinho> listaLinhaCarrinho;
    private TextView tvNomeProdutoCarrinho, tvPrecoProdutoCarrinho, tvQuantidadeProdutoCarrinho;
    private LinhasCarrinhosListener linhasCarrinhosListener;
    private LinhaCarrinhoAdaptador adapter;
    private CarrinhoListener carrinhoListener;
    private Carrinho carrinho;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SingletonProdutos.getInstance(getContext()).setCarrinhoListener(this);
        SingletonProdutos.getInstance(getContext()).getCarrinhoAPI(getContext());
        carrinho = SingletonProdutos.getInstance(getContext()).getCarrinho();


        SingletonProdutos.getInstance(getContext()).setLinhasCarrinhosListener(this);
        SingletonProdutos.getInstance(getContext()).getLinhasCarrinhosAPI(getContext(), carrinho);

        View view = inflater.inflate(R.layout.fragment_carrinhos, container, false);


        rvLinhaProdutos = view.findViewById(R.id.rvListaLinhaCarrinho);
        rvLinhaProdutos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        listaLinhaCarrinho = SingletonProdutos.getInstance(getContext()).getLinhaCarrinhos();


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onRefreshListaLinhasCarrinhos(ArrayList<LinhaCarrinho> listaLinhaCarrinho) {
        //rvLinhaProdutos = getView().findViewById(R.id.rvListaLinhaCarrinho);
        adapter = new LinhaCarrinhoAdaptador(getContext(), this, listaLinhaCarrinho);
        rvLinhaProdutos.setAdapter(adapter);
    }

    @Override
    public void onItemUpdate() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshListaCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;

    }
}