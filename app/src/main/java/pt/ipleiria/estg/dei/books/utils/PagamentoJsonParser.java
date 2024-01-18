package pt.ipleiria.estg.dei.books.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.dei.books.Modelo.Carrinho;
import pt.ipleiria.estg.dei.books.Modelo.Pagamento;

public class PagamentoJsonParser {

    public static Pagamento parserJsonPagamento(String response) {
        Pagamento pagamento = null;
        try {
            JSONObject pagamentoJSON = new JSONObject(response);
            int id = pagamentoJSON.getInt("id");
            String data = pagamentoJSON.getString("data");
            String metodopag = pagamentoJSON.getString("metodopag");
            float valor = (float) pagamentoJSON.getDouble("valor");
            int faturaid = pagamentoJSON.getInt("fatura_id");

            pagamento = new Pagamento(id, faturaid, data, metodopag, valor);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Log.d("PagamentoJsonParser", "parserJsonPagamento: " + pagamento);
        return pagamento;
    }
}
