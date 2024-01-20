package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Fatura;
import pt.ipleiria.estg.dei.books.Modelo.LinhasFaturas;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.adaptadores.LinhasFaturasAdaptador;
import pt.ipleiria.estg.dei.books.adaptadores.ListaProdutosAdaptador;
import pt.ipleiria.estg.dei.books.listeners.FaturaListener;
import pt.ipleiria.estg.dei.books.listeners.LinhasFaturasListener;

public class FaturaActivity extends AppCompatActivity implements LinhasFaturasListener, FaturaListener {
    private TextView tvNomeFatura, tvValorTotalFatura, tvPrecoProduto, tvprecoProdutoIva, tvPercentagemIva, tvQuantidade,infosFatura;
    private ArrayList<LinhasFaturas> linhasFaturas;
    private LinhasFaturasListener linhasFaturasListener;
    private LinhasFaturasAdaptador adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatura);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fatura")) {

            int idFatura = (int) intent.getIntExtra("fatura", 0);
            Fatura fatura = SingletonProdutos.getInstance(getApplicationContext()).getFaturaById(idFatura);

            infosFatura = findViewById(R.id.infosFatura);
            infosFatura.setText("Fatura: "+fatura.getId()+"\nData: "+fatura.getData()+"\nValor Total: "+fatura.getValorTotal()+" €"+"\nStatus: "+fatura.getStatus()+"\n");
            linhasFaturasListener = this;
            SingletonProdutos.getInstance(getApplicationContext()).getLinhasFaturasAPI(getApplicationContext(), fatura);
            linhasFaturas = SingletonProdutos.getInstance(getApplicationContext()).getLinhasFaturas();
            SingletonProdutos.getInstance(getApplicationContext()).setLinhasFaturasListener(this);
            RecyclerView recyclerView = findViewById(R.id.recyclerViewLinhasFaturas);
            recyclerView.setLayoutManager(new GridLayoutManager(this,1));
            LinhasFaturasAdaptador adapter = new LinhasFaturasAdaptador(this, linhasFaturas);
            recyclerView.setAdapter(adapter);
        }




    }

    @Override
    public void onRefreshListaLinhasFaturas(Fatura fatura) {
        linhasFaturas = SingletonProdutos.getInstance(getApplicationContext()).getLinhasFaturas();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLinhasFaturas);
        adapter = new LinhasFaturasAdaptador(getApplicationContext(), linhasFaturas);
        recyclerView.setAdapter(adapter);
        if(linhasFaturas != null)
            adapter.setLinhasFaturas(linhasFaturas);

        // Implement any additional logic you need when the data is refreshed
    }

    @Override
    public void onItemClick(int position, Fatura fatura) {
        SingletonProdutos.getInstance(getApplicationContext()).getLinhasFaturasAPI(getApplicationContext(), fatura);
        linhasFaturas = SingletonProdutos.getInstance(getApplicationContext()).getLinhasFaturas();

    }
}
