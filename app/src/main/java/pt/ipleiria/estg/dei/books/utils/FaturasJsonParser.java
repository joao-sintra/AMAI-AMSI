package pt.ipleiria.estg.dei.books.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Fatura;
import pt.ipleiria.estg.dei.books.Modelo.Produto;

public class FaturasJsonParser {

    public static ArrayList<Fatura> parserJsonFaturas(JSONArray response) {
        ArrayList<Fatura> faturas = new ArrayList<Fatura>();
        try {
            for (int i = 0; i < response.length(); i++) {
                if (response.get(i) instanceof JSONObject) {
                    JSONObject produtoJSON = (JSONObject) response.get(i);
                    int id = produtoJSON.getInt("id");
                    String data = produtoJSON.getString("data");
                    float valortotal = (float) produtoJSON.getDouble("valortotal");
                    String status = produtoJSON.getString("status");
                    int user_id = produtoJSON.getInt("user_id");

                    Fatura fatura = new Fatura(id, user_id, data, status, valortotal);
                    faturas.add(fatura);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return faturas;


    }
}
