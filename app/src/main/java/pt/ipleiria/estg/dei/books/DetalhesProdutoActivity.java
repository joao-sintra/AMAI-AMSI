package pt.ipleiria.estg.dei.books;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import pt.ipleiria.estg.dei.books.databinding.ActivityDetalhesProdutoBinding;


public class DetalhesProdutoActivity extends AppCompatActivity {

    private ActivityDetalhesProdutoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Your custom logic here
    }
}

