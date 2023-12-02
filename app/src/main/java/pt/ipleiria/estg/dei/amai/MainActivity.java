package pt.ipleiria.estg.dei.amai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvEmail;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvEmail = findViewById(R.id.tvEmail);
        Intent intent = getIntent();
        email = intent.getStringExtra(LoginActivity.EMAIL);
        tvEmail.setText(email);
    }
}