package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        TextView signupBtn = findViewById(R.id.signupBtn);
    }


    public void onSignupButtonClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onLoginButtonClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void onSettingsClick(View view) {
        Intent intent = new Intent(this, DefinicoesActivity.class);
        startActivity(intent);
    }

}