package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import pt.ipleiria.estg.dei.books.Modelo.Carrinho;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.databinding.ActivityMainBinding;
import pt.ipleiria.estg.dei.books.listeners.CarrinhoListener;

public class MainActivity extends AppCompatActivity implements CarrinhoListener {

    private ActivityMainBinding binding;
    private CarrinhoListener carrinhoListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SingletonProdutos.getInstance(getApplicationContext()).setCarrinhoListener(carrinhoListener);
        SingletonProdutos.getInstance(getApplicationContext()).getCarrinhoAPI(getApplicationContext());

        CarregarFragmentAtual(new PaginaInicialFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        PaginaInicialFragment paginaInicialFragment = new PaginaInicialFragment();

        PerfilFragment perfilFragment = new PerfilFragment();


        CarrinhosFragment carrinhosFragment = new CarrinhosFragment();
        //Criar as notificações quando se adiciona ao carrinho
        /*CriarNotificacaoCarrinho();*/

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                CarregarFragmentAtual(paginaInicialFragment);
                return true;
            } else if (itemId == R.id.carrinho) {
                CarregarFragmentAtual(carrinhosFragment);
                return true;
            } else if (itemId == R.id.perfil) {
                CarregarFragmentAtual(perfilFragment);
                return true;
            } else if (itemId == R.id.favoritos) {
                CarregarFragmentAtual(new FavoritosFragment());
                return true;
            }
            return false;
        });


    }

    private void CarregarFragmentAtual(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .addToBackStack(null)
                .commit();
    }

   /* public void CriarNotificacaoCarrinho() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.carrinho);

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(menuItem.getItemId());
        badgeDrawable.setNumber(10);
        badgeDrawable.setVisible(true);
    }*/


    @Override
    public void onRefreshListaCarrinho(Carrinho carrinho) {

    }
}
