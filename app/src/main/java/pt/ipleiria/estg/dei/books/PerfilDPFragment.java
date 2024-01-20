package pt.ipleiria.estg.dei.books;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import pt.ipleiria.estg.dei.books.Modelo.SingletonProdutos;
import pt.ipleiria.estg.dei.books.Modelo.Utilizador;

public class PerfilDPFragment extends Fragment {

    private Button btnEditarPerfil;
    private LinearLayout btnPerfilDM, btnPerfilDP, btnFaturas;
    private View view;
    private TextView tvUsername, tvPrimeiroNome, tvApelido, tvEmail, tvTelemovel, tvNIF, tvGenero, tvDtaNascimento;
    private TextView textoGenero, textoNIF, textoTelemovel;
    private View view3;
    private LinearLayout linearLayoutDtaNascimento;
    private String username, primeiroNome, apelido, email, telemovel, nif, genero, dtaNascimento, rua, localidade, codigoPostal;
    private Utilizador utilizador, utilizadorData;
    private SingletonProdutos singleton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_perfil_dp, container, false);

        singleton = SingletonProdutos.getInstance(getContext());
        singleton.getUserDataAPI(getContext());

        utilizador = singleton.getUtilizador();
        utilizadorData = singleton.getUtilizadorData();

        username = utilizador != null ? utilizador.getUsername() : null;
        primeiroNome = utilizadorData != null ? utilizadorData.getPrimeironome() : null;
        apelido = utilizadorData != null ? utilizadorData.getApelido() : null;
        email = utilizador != null ? utilizador.getEmail() : null;
        telemovel = utilizadorData != null ? utilizadorData.getTelefone() : null;
        nif = utilizadorData != null ? utilizadorData.getNif() : null;
        genero = utilizadorData != null ? utilizadorData.getGenero() : null;
        dtaNascimento = utilizadorData != null ? utilizadorData.getDtanasc() : null;

        // Find TextView objects by their IDs
        tvUsername = view.findViewById(R.id.tvUsername);
        tvPrimeiroNome = view.findViewById(R.id.tvPrimeiroNome);
        tvApelido = view.findViewById(R.id.tvApelido);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvTelemovel = view.findViewById(R.id.tvTelemovel);
        tvNIF = view.findViewById(R.id.tvNIF);
        tvGenero = view.findViewById(R.id.tvGenero);
        tvDtaNascimento = view.findViewById(R.id.tvDtaNascimento);

        // Perform null checks before accessing properties
        if (tvUsername != null) {
            tvUsername.setText(username != null ? username : "");
        }
        if (tvPrimeiroNome != null) {
            tvPrimeiroNome.setText(primeiroNome != null && !Objects.equals(primeiroNome, "null") ? primeiroNome : "por definir");
        }
        if (tvApelido != null) {
            tvApelido.setText(apelido != null && !Objects.equals(apelido, "null") ? apelido : "por definir");
        }
        if (tvEmail != null) {
            tvEmail.setText(email != null ? email : "");
        }
        if (tvTelemovel != null) {
            tvTelemovel.setText(telemovel != null && !Objects.equals(telemovel, "null") ? telemovel : "por definir");
        }
        if (tvNIF != null) {
            tvNIF.setText(nif != null && !Objects.equals(nif, "null") ? nif : "por definir");
        }

        String generoText = "por definir";
        if (genero != null) {
            if (genero.equals("M")) {
                generoText = "Masculino";
            } else if (genero.equals("F")) {
                generoText = "Feminino";
            }
        }

        if (tvGenero != null) {
            tvGenero.setText(generoText);
        }

        if (tvDtaNascimento != null) {
            tvDtaNascimento.setText(dtaNascimento != null && !Objects.equals(dtaNascimento, "null") ? dtaNascimento : "por definir");
        }

        textoGenero = view.findViewById(R.id.textoGenero);
        textoNIF = view.findViewById(R.id.textoNIF);
        textoTelemovel = view.findViewById(R.id.textoTelemovel);

        btnPerfilDM = view.findViewById(R.id.linearLayoutPerfilDM);
        btnPerfilDP = view.findViewById(R.id.linearLayoutPerfilDP);
        btnFaturas = view.findViewById(R.id.linearLayoutFaturas);
        btnEditarPerfil = view.findViewById(R.id.btnPerfilDP);

        view3 = view.findViewById(R.id.view3);
        linearLayoutDtaNascimento = view.findViewById(R.id.linearLayoutDtaNascimento);

        btnPerfilDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("ClickEvent", "btnPerfilDM clicked");
                onClickPerfilDM(v);
            }
        });

        btnPerfilDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("ClickEvent", "btnPerfilDP clicked");
                onClickPerfilDP(v);
            }
        });

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("ClickEvent", "btnEditarPerfil clicked");
                onClickBtnPerfilDP();
            }
        });
        btnFaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("ClickEvent", "btnFaturas clicked");
                Intent intent = new Intent(getContext(), ListaFaturasActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


    public void onClickPerfilDP(View view) {
        if (utilizador != null && utilizadorData != null) {
            username = utilizador.getUsername();
            primeiroNome = utilizadorData.getPrimeironome();
            apelido = utilizadorData.getApelido();
            email = utilizador.getEmail();
            telemovel = utilizadorData.getTelefone();
            nif = utilizadorData.getNif();
            genero = utilizadorData.getGenero();
            dtaNascimento = utilizadorData.getDtanasc();

            // Find TextView objects by their IDs
            tvUsername = this.view.findViewById(R.id.tvUsername);
            tvPrimeiroNome = this.view.findViewById(R.id.tvPrimeiroNome);
            tvApelido = this.view.findViewById(R.id.tvApelido);
            tvEmail = this.view.findViewById(R.id.tvEmail);
            tvTelemovel = this.view.findViewById(R.id.tvTelemovel);
            tvNIF = this.view.findViewById(R.id.tvNIF);
            tvGenero = this.view.findViewById(R.id.tvGenero);
            tvDtaNascimento = this.view.findViewById(R.id.tvDtaNascimento);

            // Perform null checks before accessing properties
            if (tvUsername != null) {
                tvUsername.setText(username != null ? username : "");
            }
            if (tvPrimeiroNome != null) {
                tvPrimeiroNome.setText(primeiroNome != null && !Objects.equals(primeiroNome, "null") ? primeiroNome : "por definir");
            }
            if (tvApelido != null) {
                tvApelido.setText(apelido != null && !Objects.equals(apelido, "null") ? apelido : "por definir");
            }
            if (tvEmail != null) {
                tvEmail.setText(email != null ? email : "");
            }
            if (tvTelemovel != null) {
                tvTelemovel.setText(telemovel != null && !Objects.equals(telemovel, "null") ? telemovel : "por definir");
            }
            if (tvNIF != null) {
                tvNIF.setText(nif != null && !Objects.equals(nif, "null") ? nif : "por definir");
            }

            String generoText = "por definir";
            if (genero != null) {
                if (genero.equals("M")) {
                    generoText = "Masculino";
                } else if (genero.equals("F")) {
                    generoText = "Feminino";
                }
            }

            if (tvGenero != null) {
                tvGenero.setText(generoText);
            }

            if (tvDtaNascimento != null) {
                tvDtaNascimento.setText(dtaNascimento != null && !Objects.equals(dtaNascimento, "null") ? dtaNascimento : "por definir");
            }

            // Find other TextView objects by their IDs
            TextView textoGenero = this.view.findViewById(R.id.textoGenero);
            TextView textoNIF = this.view.findViewById(R.id.textoNIF);
            TextView textoTelemovel = this.view.findViewById(R.id.textoTelemovel);

            textoTelemovel.setText("Telemóvel:");
            textoNIF.setText("NIF:");
            textoGenero.setText("Género:");

            // Find other View objects by their IDs
            View view3 = this.view.findViewById(R.id.view3);
            LinearLayout linearLayoutDtaNascimento = this.view.findViewById(R.id.linearLayoutDtaNascimento);

            view3.setVisibility(View.VISIBLE);
            linearLayoutDtaNascimento.setVisibility(View.VISIBLE);
        }
    }

    public void onClickPerfilDM(View view) {
        // Check if utilizadorData is not null
        if (utilizadorData != null) {
            tvUsername = this.view.findViewById(R.id.tvUsername);
            tvPrimeiroNome = this.view.findViewById(R.id.tvPrimeiroNome);
            tvApelido = this.view.findViewById(R.id.tvApelido);
            tvEmail = this.view.findViewById(R.id.tvEmail);
            tvTelemovel = this.view.findViewById(R.id.tvTelemovel);
            tvNIF = this.view.findViewById(R.id.tvNIF);
            tvGenero = this.view.findViewById(R.id.tvGenero);
            tvDtaNascimento = this.view.findViewById(R.id.tvDtaNascimento);

            // Check and set values for TextViews, handling null cases
            if (tvTelemovel != null) {
                tvTelemovel.setText(utilizadorData.getRua() != null && !Objects.equals(utilizadorData.getRua(), "null") ? utilizadorData.getRua() : "por definir");
            }
            if (tvNIF != null) {
                tvNIF.setText(utilizadorData.getNif() != null && !Objects.equals(utilizadorData.getNif(), "null") ? utilizadorData.getNif() : "por definir");
            }
            if (tvGenero != null) {
                tvGenero.setText(utilizadorData.getCodigopostal() != null && !Objects.equals(utilizadorData.getCodigopostal(), "null") ? utilizadorData.getCodigopostal() : "por definir");
            }

            // Find other TextView objects by their IDs
            TextView textoGenero = this.view.findViewById(R.id.textoGenero);
            TextView textoNIF = this.view.findViewById(R.id.textoNIF);
            TextView textoTelemovel = this.view.findViewById(R.id.textoTelemovel);

            textoTelemovel.setText("Rua:");
            textoNIF.setText("Localidade:");
            textoGenero.setText("Código Postal:");

            // Find other View objects by their IDs
            View view3 = this.view.findViewById(R.id.view3);
            LinearLayout linearLayoutDtaNascimento = this.view.findViewById(R.id.linearLayoutDtaNascimento);

            // Update text before changing visibility
            view3.setVisibility(View.GONE);
            linearLayoutDtaNascimento.setVisibility(View.GONE);
        } else {
            Log.e("ClickEvent", "utilizadorData is null");
        }

    }


    private void onClickBtnPerfilDP() {
        Intent intent = new Intent(getContext(), PerfilEditActivity.class);

        // You can add extra data to the intent if needed
        // intent.putExtra("key", "value");

        // Start the activity
        startActivity(intent);
    }
}