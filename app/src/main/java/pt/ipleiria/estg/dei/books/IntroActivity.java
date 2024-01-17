package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pt.ipleiria.estg.dei.books.databinding.ActivityIntroBinding;  // Import the generated binding class

public class IntroActivity extends AppCompatActivity {

    private ActivityIntroBinding binding;  // Declare an instance of the binding class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());  // Initialize the binding
        setContentView(binding.getRoot());  // Set the root view from the binding


        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event
                onSignupButtonClick(view);
            }
        });
        binding.apiSettingsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Handle click event
                onSettingsClick(view);
            }
        });
    }

    private void onSettingsClick(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DefinicoesApiFragment())
                .commit();

    }


    public void onSignupButtonClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onLoginButtonClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
