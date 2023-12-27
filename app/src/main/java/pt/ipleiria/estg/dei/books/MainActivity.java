package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        PaginaInicialFragment paginaInicialFragment = new PaginaInicialFragment();
        PerfilFragment perfilFragment = new PerfilFragment();

        DefinicoesApiFragment definicoesApiFragment = new DefinicoesApiFragment();

        CarrinhosFragment carrinhosFragment = new CarrinhosFragment();
        //Criar as notificações quando se adiciona ao carrinho
        CriarNotificacaoCarrinho();

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
            }
            else if (itemId == R.id.definicoes) {
                CarregarFragmentAtual(definicoesApiFragment);
                return true;
            }
            return false;
        });
    }
    private void CarregarFragmentAtual(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
    }
        public void CriarNotificacaoCarrinho(){
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
            MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.carrinho);

            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(menuItem.getItemId());
            badgeDrawable.setNumber(10);
            badgeDrawable.setVisible(true);
        }


}