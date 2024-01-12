package pt.ipleiria.estg.dei.books.Modelo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.listeners.LoginListener;

public class SingletonGestorProdutos {

    private static SingletonGestorProdutos instance = null;
    public ArrayList<Produto> produtos;

    private ProdutoBDHelper produtosBD = null;

    private LoginListener loginListener;
    private static RequestQueue volleyQueue = null;

    public static synchronized SingletonGestorProdutos getInstance(Context context) {
        if (instance == null) {
            /*instance = new SingletonGestorProdutos(context);*/
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorProdutos(Context context) {
        produtos = new ArrayList<>();
        produtosBD = new ProdutoBDHelper(context);
    }


    public void adicionarProdutoBD(Produto produto) {
        produtosBD.adicionarProdutoBD(produto);
    }
 /*   public ArrayList<Produto> getProdutosBD() {
        produtos = produtosBD.getallProdutosBD();
        return new ArrayList<>(livros);
    }*/

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }


}








