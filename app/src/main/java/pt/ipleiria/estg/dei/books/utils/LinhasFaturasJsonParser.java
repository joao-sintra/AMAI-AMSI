package pt.ipleiria.estg.dei.books.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.books.Modelo.Fatura;
import pt.ipleiria.estg.dei.books.Modelo.LinhasFaturas;

public class LinhasFaturasJsonParser {

    public static ArrayList<LinhasFaturas> parserJsonLinhasFaturas(JSONArray response) {
        ArrayList<LinhasFaturas> linhasFaturas = new ArrayList<LinhasFaturas>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject linhaJson = (JSONObject) response.get(i);
                int quantidade = linhaJson.getInt("quantidade");
                int iva = linhaJson.getInt("iva");
                String nomeProduto = linhaJson.getString("nome_produto");
                float valor = (float) linhaJson.getDouble("valor");
                float valor_iva = (float) linhaJson.getDouble("valor_iva");
                float total = (float) linhaJson.getDouble("total");

                LinhasFaturas linha = new LinhasFaturas(quantidade, iva, nomeProduto, valor, valor_iva, total);
                linhasFaturas.add(linha);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return linhasFaturas;


    }


}
