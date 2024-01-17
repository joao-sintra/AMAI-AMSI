package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.Modelo.Utilizador;
import pt.ipleiria.estg.dei.books.listeners.LoginListener;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "token";

    //Declaração
    private EditText etUsername, etPassword;

    private final int MIN_PASS=4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        //Inicialização
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        SingletonProdutos.getInstance(this).setLoginListener(this);
    }

    private boolean isUsernameValido(String username) {
        if (username == null) {
            return false;
        }

        // Define your username validation pattern
        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";

        return username.matches(usernamePattern);
    }

    private boolean isPasswordValida(String pass){
        if(pass==null)
            return false;

        return pass.length()>=MIN_PASS;
    }

    public void onSignupButtonClick (View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        String username = etUsername.getText().toString();
        String pass = etPassword.getText().toString();

        if(!isUsernameValido(username)) {
            etUsername.setError("Username Inválido!");
            return;
        }
        if(!isPasswordValida(pass)) {
            etPassword.setError("Password Inválida!");
            return;
        }
        SingletonProdutos.getInstance(this).loginAPI(username, pass, getApplicationContext());
    }

    @Override
    public void onUpdateLogin(Utilizador utilizador) {
        if(utilizador.getAuth_key() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(TOKEN, utilizador.getAuth_key());
            intent.putExtra(USERNAME, utilizador.getUsername());
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Token incorreto", Toast.LENGTH_SHORT).show();
        }

        navigateToFragment();
    }

    public void navigateToFragment() {
        String username = etUsername.getText().toString();

        // Create a new instance of PaginaInicialFragment
        PaginaInicialFragment fragment = new PaginaInicialFragment();

        // Use putExtra to pass the username to the fragment
        Intent intent = new Intent(this, PaginaInicialFragment.class);
        intent.putExtra(USERNAME, username);

        // Replace the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}