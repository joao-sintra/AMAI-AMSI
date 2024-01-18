package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.Modelo.Utilizador;
import pt.ipleiria.estg.dei.books.listeners.SignupListener;

public class SignupActivity extends AppCompatActivity implements SignupListener {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "token";

    private EditText etUsername, etEmail, etPassword;

    private final int MIN_PASS=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Signup");

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        SingletonProdutos.getInstance(this).setSignupListener(this);
    }


    public void onLoginButtonClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean isUsernameValido(String username) {
        if (username == null) {
            return false;
        }

        // Define your username validation pattern
        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";

        return username.matches(usernamePattern);
    }

    private boolean isEmailValido(String email) {
        if (email == null) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean isPasswordValida(String pass){
        if(pass==null)
            return false;

        return pass.length()>=MIN_PASS;
    }

    public void onClickSignup(View view) {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();

        if(!isUsernameValido(username)) {
            etUsername.setError("Username Inválido!");
            return;
        }
        if(!isEmailValido(email)) {
            etEmail.setError("Email Inválido!");
            return;
        }
        if(!isPasswordValida(pass)) {
            etPassword.setError("Password Inválida!");
            return;
        }

        SingletonProdutos.getInstance(this).signupAPI(etUsername.getText().toString(), pass, email, this);

    }

    public void onUpdateSignup(Utilizador user) {
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(TOKEN, user.getAuth_key());
            intent.putExtra(USERNAME, etUsername.getText().toString());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Signup sem sucesso", Toast.LENGTH_SHORT).show();
        }

    }
}