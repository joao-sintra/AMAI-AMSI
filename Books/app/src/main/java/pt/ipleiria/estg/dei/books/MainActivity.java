package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvEmail;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO: carregar o email na tv
        tvEmail=findViewById(R.id.tvEmail);
        Intent intent = getIntent();
        email = intent.getStringExtra(LoginActivity.EMAIL);
        tvEmail.setText(email);
    }

    public void onClickEstatico(View view) {
        Intent intent = new Intent(this, DetalhesEstaticoActivity.class);
        startActivity(intent);
    }

    public void onClickDinamico(View view) {
        Intent intent = new Intent(this, DetalhesDinamicoActivity.class);
        startActivity(intent);
    }

    public void onClickEmail(View view) {


        //TODO: intent implicito do android
        //ver em https://developer.android.com/guide/components/intents-common?hl=pt-br#java
    }
}