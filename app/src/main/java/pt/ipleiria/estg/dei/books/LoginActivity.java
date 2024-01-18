package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pt.ipleiria.estg.dei.books.listeners.LoginListener;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static final String TOKEN = "token";

    //Declaração
    private EditText etEmail, etPassword;

    private final int MIN_PASS = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        //Inicialização
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        /*SingletonGestorLivros.getInstance(getApplicationContext()).setLoginListener(this);*/
    }

    private boolean isEmailValido(String email) {
        if (email == null)
            return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String pass) {
        if (pass == null)
            return false;

        return pass.length() >= MIN_PASS;
    }

    public void onSignupButtonClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();

        if (!isEmailValido(email)) {
            etEmail.setError("Email Inválido!");
            return;
        }
        if (!isPasswordValida(pass)) {
            etPassword.setError("Password Inválida!");
            return;
        }
        /*SingletonGestorLivros.getInstance(this).loginAPI(email, pass,getApplicationContext());*/


        //Ex 2.5
        /*Toast.makeText(this,"Login com sucesso!", Toast.LENGTH_SHORT).show();*/

        //Ex 3.2
        /*Intent intent = new Intent(this, MenuMainActivity.class);
        intent.putExtra(EMAIL, email);
        startActivity(intent);
        finish();*/

    }


    @Override
    public void onUpdateLogin(String token) {
        if (token != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(TOKEN, token);
            intent.putExtra(EMAIL, etEmail.getText().toString());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Token incorreto", Toast.LENGTH_SHORT).show();
        }

    }
}