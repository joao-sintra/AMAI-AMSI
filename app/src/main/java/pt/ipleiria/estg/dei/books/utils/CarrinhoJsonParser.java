package pt.ipleiria.estg.dei.books.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Carrinho;
import pt.ipleiria.estg.dei.books.Modelo.Produto;

public class CarrinhoJsonParser {

    public static Carrinho parserJsonCarrinho(JSONObject response) {
        Carrinho carrinho = null;
        try {

            JSONObject carrinhoJSON = new JSONObject(response.toString());
            int id = carrinhoJSON.getInt("id");
            String dtapedido = carrinhoJSON.getString("dtapedido");
            String metodoenvio = carrinhoJSON.getString("metodoenvio");
            String status = carrinhoJSON.getString("status");
            float valortotal = (float) carrinhoJSON.getDouble("valortotal");
            int userid = carrinhoJSON.getInt("userid");

            carrinho = new Carrinho(id, userid, metodoenvio, status, dtapedido, valortotal);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Log.d("CarrinhoJsonParser", "parserJsonCarrinho: " + carrinho.toString());
        return carrinho;
    }
}
