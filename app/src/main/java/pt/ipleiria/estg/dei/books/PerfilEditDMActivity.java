package pt.ipleiria.estg.dei.books;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.text.Spanned;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilEditDMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_dm_editar);

        EditText etCodigoPostal = findViewById(R.id.etCodigoPostal);
        //etCodigoPostal.setFilters(new InputFilter[]{new PostalCodeInputFilter()});

        EditText etLocalidade = findViewById(R.id.etLocalidade);
        etLocalidade.setFilters(new InputFilter[]{new LetterInputFilter()});
    }

    //TODO: Implementar o método isValidPostalCode para validar o código postal
    private class PostalCodeInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // Allow only digits and a single hyphen at the correct position
            StringBuilder filtered = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if (Character.isDigit(c) || (c == '-' && filtered.length() == 4)) {
                    filtered.append(c);
                }
            }

            // Check if the resulting text has the correct format "1234-123"
            if (!isValidPostalCode(filtered.toString())) {
                return dest.subSequence(dstart, dend);  // Revert to the original text
            }

            return filtered.toString(); // Accept the input
        }

        private boolean isValidPostalCode(String text) {
            return text.matches("\\d{4}-\\d{3}");
        }
    }


    public class LetterInputFilter implements InputFilter {
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
}