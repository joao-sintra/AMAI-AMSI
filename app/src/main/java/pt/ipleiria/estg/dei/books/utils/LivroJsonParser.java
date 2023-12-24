package pt.ipleiria.estg.dei.books.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Livro;

public class LivroJsonParser {

    public static ArrayList<Livro> parserJsonLivros(JSONArray response){
        ArrayList<Livro> livros = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject livroJSON = (JSONObject) response.get(i);
                int id = livroJSON.getInt("id");
                int ano = livroJSON.getInt("ano");
                String titulo = livroJSON.getString("titulo");
                String serie = livroJSON.getString("serie");
                String autor = livroJSON.getString("autor");
                String capa = livroJSON.getString("capa");
                Livro livro = new Livro(id, capa, ano, titulo, serie, autor);
                livros.add(livro);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return livros;
    }

    public static Livro parserJsonLivro(String response) {
        Livro livro = null;
        try {
            JSONObject livroJSON = new JSONObject(response);
            int id = livroJSON.getInt("id");
            int ano = livroJSON.getInt("ano");
            String titulo = livroJSON.getString("titulo");
            String serie = livroJSON.getString("serie");
            String autor = livroJSON.getString("autor");
            String capa = livroJSON.getString("capa");
            livro = new Livro(id, capa, ano, titulo, serie, autor);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return livro;
    }

    public static String parserJsonLogin(String response) {
        String token = null;
        try {
            JSONObject loginJSON = new JSONObject(response);
            token = loginJSON.getString("token");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
