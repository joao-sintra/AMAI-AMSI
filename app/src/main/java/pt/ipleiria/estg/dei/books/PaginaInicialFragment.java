package pt.ipleiria.estg.dei.books;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.adaptadores.BestProdutosAdaptador;
import pt.ipleiria.estg.dei.books.databinding.FragmentPaginaInicialBinding;
import pt.ipleiria.estg.dei.books.listeners.ProdutosListener;

public class PaginaInicialFragment extends Fragment implements ProdutosListener {

    private FragmentPaginaInicialBinding binding;
    public ArrayList<Produto> listaProdutos;
    private BestProdutosAdaptador adapter;
    private ArrayList<Produto> filteredList;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaginaInicialBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.VerTodosBtn.setOnClickListener(v -> goToListaProdutosActivity());

        SingletonProdutos.getInstance(getContext()).setProdutosListener(this);

        SingletonProdutos.getInstance(getContext()).getAllProdutosAPI(getContext());

        binding.bestProductView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        listaProdutos = SingletonProdutos.getInstance(getContext()).getProdutos();


        filteredList = new ArrayList<>();

        searchView = view.findViewById(R.id.searchView);

        setupSearchView();

        return view;
    }

    private void setupSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sendQuerytoActivity(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }
    private void sendQuerytoActivity(String query) {
        // Create an intent
        Intent intent = new Intent(getActivity(), ListaProdutosActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // Put the data into the intent
        intent.putExtra("query", query);

        // Start the activity
        startActivity(intent);
    }



    @Override
    public void onRefreshListaProdutos(ArrayList<Produto> listaProdutos) {
        adapter = new BestProdutosAdaptador(getContext(), listaProdutos);
        binding.bestProductView.setAdapter(adapter);
        binding.progressBarBestProduct.setVisibility(View.GONE);

    }

    private void goToListaProdutosActivity() {

        // Intent to start ListaProdutosActivity
        Intent intent = new Intent(getActivity(), ListaProdutosActivity.class);
        startActivity(intent);


    }
}

