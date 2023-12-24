package pt.ipleiria.estg.dei.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String email;
    private String password;
    private String token;
    private FragmentManager fragmentManager;
    public static final String EMAIL = "EMAIL", TOKEN="TOKEN";
    public static final int ADD=100, EDIT=200, DEL=300;
    public static final String OP_CODE= "operacao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        carregarCabecalho();
        fragmentManager = getSupportFragmentManager();

        navigationView.setNavigationItemSelectedListener(this);
        carregarFragmentoInicial();
    }

    private boolean carregarFragmentoInicial() {
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        Intent intent = getIntent();
        email = intent.getStringExtra(LoginActivity.EMAIL);
        token = intent.getStringExtra(LoginActivity.TOKEN);
        //buscar token e guardar shared
        SharedPreferences sharedPreUser = getSharedPreferences("DADOS_USER", MODE_PRIVATE);

        if (email != null) {
            SharedPreferences.Editor editorUser = sharedPreUser.edit();
            editorUser.putString(EMAIL, email);
            editorUser.putString(TOKEN, token);
            editorUser.apply();
        } else {
            email = sharedPreUser.getString(EMAIL, "Sem email");
        }

        View hView = navigationView.getHeaderView(0);
        TextView tvEmail = hView.findViewById(R.id.tvEmail);
        tvEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        if(item.getItemId()==R.id.navEstatico) {
            fragment = new ListaProdutosFragment();
            setTitle(item.getTitle());
        } else if(item.getItemId()==R.id.navDinamico) {
            fragment = new GrelhaProdutosFragment();
            setTitle(item.getTitle());
        } else if(item.getItemId()==R.id.navEmail) {
            enviarEmail();
        }

        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void enviarEmail() {
        String subject = "AMSI 2023/24";
        String message = "Este email enviado com sucesso!";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL,email);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Não existe nenhuma aplicação para enviar email", Toast.LENGTH_SHORT).show();
        }
    }
}