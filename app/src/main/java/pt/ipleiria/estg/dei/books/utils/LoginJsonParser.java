package pt.ipleiria.estg.dei.books.utils;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.dei.books.Modelo.Utilizador;

public class LoginJsonParser {

    public static Utilizador parserJsonLogin(JSONObject response) {
        Utilizador utilizador = null;
        try {
            JSONObject loginJSON = new JSONObject(response.toString());
            int id = loginJSON.getInt("id");
            String username = loginJSON.getString("username");
            String auth_key = loginJSON.getString("auth_key");
            String password_hash = loginJSON.getString("password_hash");
            String password_reset_token = "";
            String email = loginJSON.getString("email");
            String status = loginJSON.getString("status");
            String created_at = loginJSON.getString("created_at");
            String updated_at = loginJSON.getString("updated_at");
            String verification_token = "";

            utilizador = new Utilizador(id, username, "", "", email, "",
                    "","","","","", "", auth_key, password_hash, password_reset_token,
                    status, created_at, updated_at, verification_token);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return utilizador;
    }

    public static Utilizador parserJsonGetUtilizadorData(JSONObject response) {
        Utilizador utilizador = null;
        try {
            JSONObject loginJSON = new JSONObject(response.toString());
            int id = loginJSON.getInt("id");
            String primeironome = loginJSON.getString("primeironome");
            String apelido = loginJSON.getString("apelido");
            String codigopostal = loginJSON.getString("codigopostal");
            String localidade = loginJSON.getString("localidade");
            String rua = loginJSON.getString("rua");
            String nif = loginJSON.getString("nif");
            String dtanasc = loginJSON.getString("dtanasc");
            String telefone = loginJSON.getString("telefone");
            String genero = loginJSON.getString("genero");

            utilizador = new Utilizador(id, "", primeironome, apelido, "", codigopostal, rua,
                    localidade, dtanasc, telefone, nif, genero,"", "", "","",
                    "", "", "");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return utilizador;
    }
}
