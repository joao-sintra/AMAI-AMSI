package pt.ipleiria.estg.dei.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Fatura;
import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.listeners.FaturasListener;
import pt.ipleiria.estg.dei.books.listeners.PagamentoListener;

public class CheckoutActivity extends AppCompatActivity implements FaturasListener, PagamentoListener {
    private Spinner spinnerShippingMethod ;
    private Spinner spinnerPaymentMethod ;
    private Button buttonProceedCheckout ;
    private FaturasListener faturaListener;
    private PagamentoListener pagamentoListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        spinnerPaymentMethod = findViewById(R.id.spinnerPaymentMethod);
        spinnerShippingMethod = findViewById(R.id.spinnerShippingMethod);
        buttonProceedCheckout = findViewById(R.id.buttonProceedCheckout);
        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve data from the Intent
            // Your data retrieval code here
        }
        buttonProceedCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedShippingMethod = spinnerShippingMethod.getSelectedItem().toString();
                String selectedPaymentMethod = spinnerPaymentMethod.getSelectedItem().toString();

                //metodo api para finalizar encomenda, pagamentos e fatura
                SingletonProdutos.getInstance(getApplicationContext()).setFaturasListener(faturaListener);
                SingletonProdutos.getInstance(getApplicationContext()).adicionarFaturaAPI(getApplicationContext());
                SingletonProdutos.getInstance(getApplicationContext()).setPagamentoListener(pagamentoListener);
                SingletonProdutos.getInstance(getApplicationContext()).adicionarPagamentoAPI(getApplicationContext(), selectedPaymentMethod, selectedShippingMethod);
                //make a toast
                Toast.makeText(getApplicationContext(), "Encomenda finalizada com sucesso", Toast.LENGTH_SHORT).show();
                //i want to send to main activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onRefreshPagamento() {

    }

    @Override
    public void onRefreshListaFatura(ArrayList<Fatura> listaFaaturas) {

    }
}
