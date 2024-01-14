package pt.ipleiria.estg.dei.books.Modelo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutosListener;
import pt.ipleiria.estg.dei.books.utils.ProdutoJsonParser;

public class SingletonProdutos {

    private static final String TOKEN = "fBF_qwu_kIXpMydCXbsqYSpcHfeJyk-E";
    private ArrayList<Produto> produtos;
    private static SingletonProdutos instance = null;
    private static final String mUrlAPIProdutos = "http://192.168.1.115/AMAI-SIS/backend/web/api/produtos?access-token=" + TOKEN;
    //private LivroBDHelper livrosBD=null;
    private static RequestQueue volleyQueue = null;

    // private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";
    //public static final String TOKEN = "AMSI-TOKEN";


    private ProdutosListener produtosListener;
    private ProdutoListener produtoListener;

    public static synchronized SingletonProdutos getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonProdutos(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonProdutos(Context context) {
        produtos = new ArrayList<>();
        //livrosBD = new LivroBDHelper(context);
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }


    public void setProdutosListener(ProdutosListener produtosListener) {
        this.produtosListener = produtosListener;
    }

    public void setProdutoListener(ProdutoListener produtoListener) {
        this.produtoListener = produtoListener;
    }

    public Produto getProduto(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id)
                return produto;
        }
        return null;
    }

    public void getAllProdutosAPI(final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();


            if (produtosListener != null)
                produtosListener.onRefreshListaProdutos(produtos);
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // converter json em livros
                    produtos = ProdutoJsonParser.parserJsonProdutos(response);

                    // informar a vista
                    if (produtoListener != null)
                        produtosListener.onRefreshListaProdutos(produtos);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }


}
