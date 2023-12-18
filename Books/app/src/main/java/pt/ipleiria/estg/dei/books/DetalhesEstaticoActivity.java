package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalhesEstaticoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_estatico);
        setTitle("Dados Estáticos");
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvSerie = findViewById(R.id.tvSerie);
        TextView tvAutor = findViewById(R.id.tvAutor);
        TextView tvAno = findViewById(R.id.tvAno);
        ImageView imgCapa = findViewById(R.id.imgCapa);
        tvTitulo.setText("Programar em Android AMSI");
        tvSerie.setText("Android Saga");
        tvAutor.setText("Equipa AMSI");
        tvAno.setText("2019");
        imgCapa.setImageResource(R.drawable.programarandroid1);
    }
}