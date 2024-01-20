package pt.ipleiria.estg.dei.books;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.ipleiria.estg.dei.books.Modelo.Produto;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.Modelo.Utilizador;
import pt.ipleiria.estg.dei.books.adaptadores.BestProdutosAdaptador;
import pt.ipleiria.estg.dei.books.databinding.FragmentPaginaInicialBinding;
import pt.ipleiria.estg.dei.books.listeners.LoginListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutosListener;
import pt.ipleiria.estg.dei.books.listeners.SignupListener;

public class PaginaInicialFragment extends Fragment implements ProdutosListener, ProdutoListener, LoginListener, SignupListener {

    private FragmentPaginaInicialBinding binding;
    public ArrayList<Produto> listaProdutos;
    private BestProdutosAdaptador adapter;
    private ArrayList<Produto> filteredList;
    private SearchView searchView;
    private String username;
    private SingletonProdutos singleton;
    private Utilizador utilizador;
    private Utilizador loggedInUser;
    private TextView txtUsername, botaoFaturas;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaginaInicialBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        singleton = SingletonProdutos.getInstance(getContext());

        utilizador = SingletonProdutos.getInstance(getContext()).getUtilizador();

        SingletonProdutos.getInstance(getContext()).setLoginListener(this);

        ImageView imgLogout = view.findViewById(R.id.imgLogout);

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

        if (utilizador != null) {
            username = utilizador.getUsername();
            txtUsername = binding.txtUsername;
            txtUsername.setText(username);
        } else {
            // Handle the case when utilizador is null
            Log.e("PaginaInicialFragment", "Utilizador is null");
        }

        binding.VerTodosBtn.setOnClickListener(v -> goToListaProdutosActivity());

        SingletonProdutos.getInstance(getContext()).setProdutosListener(this);

        SingletonProdutos.getInstance(getContext()).getAllProdutosAPI(getContext());

        binding.bestProductView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        listaProdutos = SingletonProdutos.getInstance(getContext()).getProdutos();

        filteredList = new ArrayList<>();

        searchView = view.findViewById(R.id.searchView);

        setupSearchView();
        botaoFaturas = view.findViewById(R.id.botaoFaturas);
        //on click listener para o botao faturas
        botaoFaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListaFaturasActivity.class);
                startActivity(intent);
            }
        });

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
        // Shuffle the listaProdutos array
        Collections.shuffle(listaProdutos);

        // Limit the number of products to be displayed (e.g., 5)
        int numberOfProductsToDisplay = 4;
        List<Produto> limitedList = listaProdutos.subList(0, Math.min(numberOfProductsToDisplay, listaProdutos.size()));
        ArrayList<Produto> limitedArrayList = new ArrayList<>(limitedList);

        // Create the adapter with the limited list
        adapter = new BestProdutosAdaptador(getContext(), limitedArrayList);

        // Set the adapter to the RecyclerView
        binding.bestProductView.setAdapter(adapter);

        // Hide the progress bar
        binding.progressBarBestProduct.setVisibility(View.GONE);

    }

    @Override
    public void onItemClick(int position, Produto product) {
        Intent intent = new Intent(getContext(), DetalhesProdutoActivity.class);
        intent.putExtra("NOME", product.getNome());
        intent.putExtra("PRECO", product.getPreco());
        intent.putExtra("IMAGEM", product.getImagem());
        intent.putExtra("DESCRICAO", product.getDescricao());

        startActivity(intent);
    }

    private void goToListaProdutosActivity() {
        // Intent to start ListaProdutosActivity
        Intent intent = new Intent(getActivity(), ListaProdutosActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Logout");
        builder.setMessage("Tem a certeza que pretende terminar a sua sessão?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call the logout method from SingletonProdutos
                SingletonProdutos.getInstance(getContext()).logout(getContext());
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onUpdateLogin(Utilizador user) {
        // Update UI components with user information
        txtUsername.setText(user.getUsername());
        // Add other UI updates if needed
    }

    @Override
    public void onUpdateSignup(Utilizador utilizador) {
        // Handle UI updates after signup
        loggedInUser = utilizador;

        if (loggedInUser != null) {
            // Update UI components with user information
            txtUsername.setText(loggedInUser.getUsername());
            // Add other UI updates if needed
        } else {
            // Handle the case when the signup process didn't provide a valid user
            Log.e("PaginaInicialFragment", "Signup failed. loggedInUser is null.");
        }
    }
}
