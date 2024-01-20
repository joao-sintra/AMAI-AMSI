package pt.ipleiria.estg.dei.books;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.books.Modelo.Favoritos;
import pt.ipleiria.estg.dei.books.Modelo.FavoritosBDHelper;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.adaptadores.FavoritosAdaptador;
import pt.ipleiria.estg.dei.books.databinding.FragmentFavoritosBinding;
import pt.ipleiria.estg.dei.books.databinding.FragmentPaginaInicialBinding;
import pt.ipleiria.estg.dei.books.listeners.FavoritosListener;


public class FavoritosFragment extends Fragment implements FavoritosListener {

    private FragmentFavoritosBinding binding;
    private int userID;

    private FavoritosListener favoritosListener;

    private FavoritosAdaptador adapter;

    ArrayList<Favoritos> favoritosList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.rvFavoritos.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //buscar o id do utilizador
        userID = SingletonProdutos.getInstance(getContext()).getUserId(getContext());
        //buscar os dados da BD
        FavoritosBDHelper dbHelper = new FavoritosBDHelper(getContext());
        favoritosList = (ArrayList<Favoritos>) dbHelper.getAllFavoritos(userID);


        adapter = new FavoritosAdaptador(getContext(), favoritosList, this);
        binding.rvFavoritos.setAdapter(adapter);
        adapter.setFavoritosListener(this);

        return view;
    }

    @Override
    public void onFavoritosItemRemoved(Favoritos favoritos) {
        FavoritosBDHelper dbHelper = new FavoritosBDHelper(getContext());
        dbHelper.removerEstadoFavoritosBD(favoritos.getId_user(), favoritos.getIdProduto());

        // Update the dataset by removing the item
        favoritosList.remove(favoritos);

        // Notify the adapter, that the data has changed
        adapter.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

}