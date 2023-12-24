package pt.ipleiria.estg.dei.books;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Livro;
import pt.ipleiria.estg.dei.books.Modelo.SingletonGestorLivros;
import pt.ipleiria.estg.dei.books.adaptadores.ListaLivrosAdaptador;
import pt.ipleiria.estg.dei.books.listeners.LivrosListener;

public class ListaProdutosFragment extends Fragment implements LivrosListener {

    private ListView lvLivros;
    private ArrayList<Livro> livros;
    private FloatingActionButton fabLista;
    private SearchView searchView;

    public ListaProdutosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_produtos, container, false);
        setHasOptionsMenu(true);

        lvLivros = view.findViewById(R.id.lvLivros);

        lvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), livros.get(position).getTitulo(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int)id);
                //startActivity(intent);
                startActivityForResult(intent, MenuMainActivity.EDIT);
            }
        });

        fabLista = view.findViewById(R.id.fabLista);
        //click no actionBtn
        fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, MenuMainActivity.ADD);
            }
        });
        SingletonGestorLivros.getInstance(getContext()).setLivrosListener(this);
        SingletonGestorLivros.getInstance(getContext()).getAllLivrosAPI(getContext());
        return view;
    }

    //requestCode: resultado enviado para o DetalhesLivroActivity
    //resultCode: resultado enviado pelo DetalhesLivroActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == MenuMainActivity.ADD || requestCode == MenuMainActivity.EDIT) {
                SingletonGestorLivros.getInstance(getContext()).getAllLivrosAPI(getContext());
                if(requestCode == MenuMainActivity.ADD)
                    Toast.makeText(getContext(), "Livro adicionado com sucesso!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Livro editado com sucesso!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa, menu);

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList <Livro> tempListaLivros = new ArrayList<>();
                for (Livro l: SingletonGestorLivros.getInstance(getContext()).getLivrosBD()) {
                    if (l.getTitulo().toLowerCase().contains(newText.toLowerCase())) {
                        tempListaLivros.add(l);
                    }
                }
                lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), tempListaLivros));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    //Este método tem como função atualizar os livros
    @Override
    public void onRefreshListaLivros(ArrayList<Livro> listaLivros) {
        if(listaLivros != null)
            lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), listaLivros));
    }
}