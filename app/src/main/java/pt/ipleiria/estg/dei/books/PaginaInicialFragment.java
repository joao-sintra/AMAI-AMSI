package pt.ipleiria.estg.dei.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PaginaInicialFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pagina_inicial, container, false);

        TextView VerTodosBtn = (TextView) view.findViewById(R.id.VerTodosBtn);
        VerTodosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                goToListaProdutosActivity();
            }

        });
        return view;
    }

    private void goToListaProdutosActivity() {

        // Intent to start ListaProdutosActivity
        Intent intent = new Intent(getActivity(), ListaProdutosActivity.class);
        startActivity(intent);
    }
}
