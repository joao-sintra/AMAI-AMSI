package pt.ipleiria.estg.dei.books;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Livro;
import pt.ipleiria.estg.dei.books.Modelo.SingletonGestorLivros;
import pt.ipleiria.estg.dei.books.adaptadores.GrelhaLivrosAdaptador;
import pt.ipleiria.estg.dei.books.adaptadores.ListaLivrosAdaptador;
import pt.ipleiria.estg.dei.books.listeners.LivrosListener;


public class GrelhaLivrosFragment extends Fragment implements LivrosListener {

    private GridView gvLivros;

    private FloatingActionButton fabGrelha;



    public GrelhaLivrosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_grelha_livros, container, false);
        gvLivros = view.findViewById(R.id.gvLivros);

        gvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int)id);
                startActivityForResult(intent, MenuMainActivity.EDIT);
            }
        });

        fabGrelha = view.findViewById(R.id.fabLista);
        //click no actionBtn
        fabGrelha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                startActivityForResult(intent, MenuMainActivity.ADD);
            }
        });

        SingletonGestorLivros.getInstance(getContext()).setLivrosListener(this);
        SingletonGestorLivros.getInstance(getContext()).getAllLivrosAPI(getContext());

        return view;
    }

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
    public void onRefreshListaLivros(ArrayList<Livro> listaLivros) {
        if(listaLivros != null)
            gvLivros.setAdapter(new GrelhaLivrosAdaptador(getContext(), listaLivros));
    }
}