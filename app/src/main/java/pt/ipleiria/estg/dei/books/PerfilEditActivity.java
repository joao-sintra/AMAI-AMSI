package pt.ipleiria.estg.dei.books;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.Modelo.Utilizador;

public class PerfilEditActivity extends AppCompatActivity {

    String[] generoOptions = {"M", "F"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    private DatePickerDialog datePickerDialog;
    private Button botaoData, botaoCancelar;
    private String primeiroNome, apelido, telemovel, nif, genero, dtaNascimento, rua, localidade, codigoPostal;
    private EditText etNome, etApelido, etEmail, etTelemovel, etNif, etRua, etLocalidade, etCodigoPostal;
    private Button dataPickerButton;
    private Utilizador utilizador, utilizadorData;
    private SingletonProdutos singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_editar);
        initDatePicker();

        singleton = SingletonProdutos.getInstance(getApplicationContext());

        utilizador = singleton.getUtilizador();
        utilizadorData = singleton.getUtilizadorData();

        primeiroNome = utilizadorData.getPrimeironome();
        apelido = utilizadorData.getApelido();
        telemovel = utilizadorData.getTelefone();
        nif = utilizadorData.getNif();
        genero = utilizadorData.getGenero();
        dtaNascimento = utilizadorData.getDtanasc();
        rua = utilizadorData.getRua();
        localidade = utilizadorData.getLocalidade();
        codigoPostal = utilizadorData.getCodigopostal();

        etNome = findViewById(R.id.etNome);
        etNome.setText(primeiroNome);

        etApelido = findViewById(R.id.etApelido);
        etApelido.setText(apelido);

        etTelemovel = findViewById(R.id.etTelemovel);
        etTelemovel.setText(telemovel);

        etNif = findViewById(R.id.etNif);
        etNif.setText(nif);

        etRua = findViewById(R.id.etRua);
        etRua.setText(rua);

        etLocalidade = findViewById(R.id.etLocalidade);
        etLocalidade.setText(localidade);

        etCodigoPostal = findViewById(R.id.etCodigoPostal);
        etCodigoPostal.setText(codigoPostal);

        autoCompleteTxt = findViewById(R.id.autoComplete_txtGenero);
        autoCompleteTxt.setText(genero);

        botaoData = findViewById(R.id.dataPickerButton);

        if (dtaNascimento != null && !dtaNascimento.isEmpty()) {
            botaoData.setText(dtaNascimento);
        } else {
            botaoData.setText(getTodaysDate());
        }

        botaoData.setText(getTodaysDate());

        autoCompleteTxt = findViewById(R.id.autoComplete_txtGenero);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, generoOptions);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String genero = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selecionou: " + genero, Toast.LENGTH_SHORT).show();
            }
        });

        etLocalidade = findViewById(R.id.etLocalidade);
        etLocalidade.setFilters(new InputFilter[]{new LetterInputFilter()});

        Button btnSave = findViewById(R.id.btnGuardar);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the function to handle save button click
                onSaveButtonClick();
            }
        });

        botaoCancelar = findViewById(R.id.btnCancelar);
        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the function to handle button click
                onClickCancelarBtn();
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(year, month, day);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String data = makeDateString(day, month, year);
                botaoData.setText(data);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return year + "-" + month + "-" + day;
    }



    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public static class LetterInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // Only allow letters
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (!Character.isLetter(c)) {
                    return "";
                }
            }
            return null; // Accept the input
        }
    }

    private void onClickCancelarBtn() {
        // Finish the current activity, which will navigate back
        finish();
    }

    public void onSaveButtonClick() {
        // Call the singleton method to update the user profile
        singleton.updateProfileAPI(
                etNome.getText().toString(),
                etApelido.getText().toString(),
                etTelemovel.getText().toString(),
                etNif.getText().toString(),
                autoCompleteTxt.getText().toString(),
                botaoData.getText().toString(),
                etRua.getText().toString(),
                etLocalidade.getText().toString(),
                etCodigoPostal.getText().toString(),
                getApplicationContext()
        );
        singleton.getUserDataAPI(getApplicationContext());
        // Optionally, you can finish the activity or perform other actions after saving.
        finish();
    }
}
