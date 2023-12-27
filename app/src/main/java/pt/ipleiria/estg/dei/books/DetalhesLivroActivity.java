package pt.ipleiria.estg.dei.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.ipleiria.estg.dei.books.Modelo.Livro;
import pt.ipleiria.estg.dei.books.Modelo.SingletonGestorLivros;
import pt.ipleiria.estg.dei.books.listeners.LivroListener;

public class DetalhesLivroActivity extends AppCompatActivity implements LivroListener {

    public static final String ID_LIVRO = "ID";
    public static final String DEFAULT_IMG = "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";
    private static final int MIN_CHAR = 3, MIN_NUM = 4;
    private TextView etTitulo, etSerie, etAutor, etAno;
    private FloatingActionButton fabGuardar;
    private ImageView imgCapa;
    private Livro livro;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        etTitulo = findViewById(R.id.etTitulo);
        etSerie = findViewById(R.id.etSerie);
        etAutor = findViewById(R.id.etAutor);
        etAno = findViewById(R.id.etAno);
        imgCapa = findViewById(R.id.imgCapa);
        fabGuardar = findViewById(R.id.fabGuardar);
        SingletonGestorLivros.getInstance(getApplicationContext()).setLivroListener(this);

        int id = getIntent().getIntExtra(ID_LIVRO, 0);
        SharedPreferences sharedPreUser = getSharedPreferences("DADOS_USER", MODE_PRIVATE);

        token = sharedPreUser.getString(MenuMainActivity.TOKEN, "");
        if (id != 0) {
            livro = SingletonGestorLivros.getInstance(this).getLivro(id);
            if (livro != null) {
                carregarLivro();
                fabGuardar.setImageResource(R.drawable.ic_action_guardar);
            }
            else { //significa que algo errado ocorreu
                finish();
            }
        }
        else {
            setTitle("Adicionar Livro");
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (livro != null) { // atualizar o info no array
                    if(isLivroValido()) {
                        livro.setTitulo(etTitulo.getText().toString());
                        livro.setSerie(etSerie.getText().toString());
                        livro.setAutor(etAutor.getText().toString());
                        livro.setAno(Integer.parseInt(etAno.getText().toString()));
                        SingletonGestorLivros.getInstance(getApplicationContext()).editarLivroAPI(livro, getApplicationContext(), token);
                    }

                } else { // criar um novo livro no array
                    if(isLivroValido()) {
                        livro = new Livro(0, DEFAULT_IMG, Integer.parseInt(etAno.getText().toString()), etTitulo.getText().toString(),
                                etSerie.getText().toString(), etAutor.getText().toString());
                        SingletonGestorLivros.getInstance(getApplicationContext()).adicionarLivroAPI(livro, getApplicationContext(),token);
                    }
                }
            }
        });
    }

    //verifica se todos os campos estão preenchidos
    private boolean isLivroValido() {
        String titulo = etTitulo.getText().toString();
        String serie = etSerie.getText().toString();
        String autor = etAutor.getText().toString();
        String ano = etAno.getText().toString();

        if (titulo.length() < MIN_CHAR) {
            etTitulo.setError("Titulo inválido");
            return false;
        }
        if (serie.length() < MIN_CHAR) {
            etSerie.setError("Série inválida");
            return false;
        }
        if (autor.length() < MIN_CHAR) {
            etAutor.setError("Autor inválido");
            return false;
        }
        if (ano.length() != MIN_NUM) {
            etAno.setError("Ano inválido");
            return false;
        }
        return true;
    }

    private void carregarLivro() {
        setTitle("Detalhes: " + livro.getTitulo());
        etTitulo.setText(livro.getTitulo());
        etSerie.setText(livro.getSerie());
        etAutor.setText(livro.getAutor());
        etAno.setText(livro.getAno() + "");
        //imgCapa.setImageResource(livro.getCapa());
        Glide.with(getApplicationContext())
                .load(livro.getCapa())
                .placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);
    }
    //confirmação de ação remover livro
    private void dialogRemover() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover Livro")
                .setMessage("Tem a certeza que pretende remover o livro?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // se confirma que quero eliminar o livro
                        SingletonGestorLivros.getInstance(getApplicationContext()).removerLivroAPI(livro, getApplicationContext());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          // se não quero eliminar o livro não faz nada
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    @Override
    public void onRefreshDetalhes(int op){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}