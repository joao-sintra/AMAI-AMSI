package pt.ipleiria.estg.dei.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EstaticoFragment extends Fragment {

    public EstaticoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estatico, container, false);
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvSerie = view.findViewById(R.id.tvSerie);
        TextView tvAutor = view.findViewById(R.id.tvAutor);
        TextView tvAno = view.findViewById(R.id.tvAno);
        ImageView imgCapa = view.findViewById(R.id.imgCapa);
        tvTitulo.setText("Programar em Android AMSI");
        tvSerie.setText("Android Saga");
        tvAutor.setText("Equipa AMSI");
        tvAno.setText("2019");
        imgCapa.setImageResource(R.drawable.programarandroid1);
        return view;
    }
}