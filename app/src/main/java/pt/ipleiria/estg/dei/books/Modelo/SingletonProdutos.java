package pt.ipleiria.estg.dei.books.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import pt.ipleiria.estg.dei.books.listeners.LoginListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutosListener;
import pt.ipleiria.estg.dei.books.listeners.UtilizadorDataListener;
import pt.ipleiria.estg.dei.books.utils.LoginJsonParser;
import pt.ipleiria.estg.dei.books.utils.ProdutoJsonParser;

public class SingletonProdutos {

    public ArrayList<Produto> produtos = new ArrayList<>();
    public Utilizador utilizador;
    public Utilizador utilizadorData;
    private static volatile SingletonProdutos instance = null;
    private static final String mUrlAPIProdutos = "http://172.22.21.211/AMAI-SIS/backend/web/api/produtos/all?access-token=";
    private static final String mUrlAPILogin = "http://172.22.21.211/AMAI-SIS/backend/web/api/auth/login";
    private UtilizadorBDHelper utilizadoresBD = null;

    /*private ProdutoBDHelper produtosBD=null;*/

    private static RequestQueue volleyQueue = null;


    //private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";
    //public static final String TOKEN = "AMSI-TOKEN";

    private LoginListener loginListener;
    private UtilizadorDataListener utilizadorDataListener;
    private ProdutosListener produtosListener;
    private ProdutoListener produtoListener;

    public static synchronized SingletonProdutos getInstance(Context context) {
        if (instance == null) {
            synchronized (SingletonProdutos.class) {
                if (instance == null) {
                    instance = new SingletonProdutos(context);
                    volleyQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return instance;
    }

    private SingletonProdutos(Context context) {
        produtos = new ArrayList<>();
        utilizadoresBD = new UtilizadorBDHelper(context);
        /*produtosBD = new ProdutoBDHelper(context);*/
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setUtilizadorDataListener(UtilizadorDataListener utilizadorDataListener) {
        this.utilizadorDataListener = utilizadorDataListener;
    }

    public ArrayList<Produto> getProdutos() {

        return produtos;
    }

    public ArrayList<Produto> getFilteredProdutos(String query) {
        ArrayList<Produto> filteredProdutos = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getNome() != null && produto.getNome().toLowerCase().trim().contains(query.toLowerCase())) {
                filteredProdutos.add(produto);
            }
        }
        return filteredProdutos;
    }

    public void setProdutosListener(ProdutosListener produtosListener) {
        this.produtosListener = produtosListener;
    }

   /* public ArrayList<Produto> getProdutosBD() {
        produtos = produtosBD.getAllProdutosBD();
        return new ArrayList<>(produtos);
    }*/

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

    public void adicionarUtilizadorBD(Utilizador utilizador) {
        utilizadoresBD.adicionarUtilizadorBD(utilizador);
    }

    public void editarUtilizadorBD(Utilizador utilizador) {
        if(utilizador != null){
            utilizadoresBD.editarUtilizadorBD(utilizador);
        }
    }

    public void getAllProdutosAPI(final Context context) {
        String token = getUserToken(context);
        if (token == null) {
            // Handle the case where the token is not available
            return;
        }

        String urlWithToken = mUrlAPIProdutos + token;

        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();

            if (produtosListener != null)
                produtosListener.onRefreshListaProdutos(produtos);
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, urlWithToken, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // Add this line to log the response
                    // converter json em livros
                    produtos = ProdutoJsonParser.parserJsonProdutos(response);

                    // informar a vista
                    if (produtosListener != null) {
                        produtosListener.onRefreshListaProdutos(produtos);
                    }
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

    private int getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return preferences.getInt("user_id", 0); // 0 is the default value if the user ID is not found
    }

    private String getUserToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return preferences.getString("user_token", null);
    }

    public void loginAPI(final String username, final String password, final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        else {
            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("username", username);
                jsonParams.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, mUrlAPILogin, jsonParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Parse the JSON response and add the user to the local database
                    utilizador = LoginJsonParser.parserJsonLogin(response);

                    // Save the user's token to SharedPreferences
                    saveUserId(context, utilizador.getId());
                    saveUserToken(context, utilizador.getAuth_key());

                    // Add the user to the local database only if it doesn't already exist
                    if (!isUsernameExists(context, username)) {
                        if(utilizador.getId() != 0 || utilizador.getAuth_key() != null) {
                            getUserDataAPI(context, utilizador.getId(), utilizador.getAuth_key(), utilizador);
                        }
                    }

                    if (loginListener != null) {
                        loginListener.onUpdateLogin(utilizador);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Credenciais incorretas", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    private boolean isUsernameExists(Context context, String username) {
        UtilizadorBDHelper dbHelper = new UtilizadorBDHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM UtilizadoresTable WHERE username=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbHelper.close();

        return count > 0;
    }

    public void getUserDataAPI(final Context context, int utilizadorID, String TOKEN_USER_LOGIN, final Utilizador utilizador) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, getmUrlAPIUserData(utilizadorID, TOKEN_USER_LOGIN), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject item = response.getJSONObject(i);
                            utilizadorData = LoginJsonParser.parserJsonGetUtilizadorData(item);

                            // Move the database insertion logic here
                            adicionarUtilizadorDataBD(context, utilizadorData, utilizador);

                            if (utilizadorDataListener != null) {
                                utilizadorDataListener.onGetUtilizadorData(utilizadorData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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

    private String getmUrlAPIUserData(int utilizadorID, String TOKEN_USER_LOGIN) {
        return "http://172.22.21.211/AMAI-SIS/backend/web/api/users/"+ utilizadorID +"?access-token=" + TOKEN_USER_LOGIN;
    }

    public void saveUserToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_token", token);
        editor.apply();
    }

    public void saveUserId(Context context, int userId) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user_id", userId);
        editor.apply();
    }

    private void adicionarUtilizadorDataBD(Context context, Utilizador utilizadorData, Utilizador utilizador) {
        String TABLE_NAME = "UtilizadoresTable";

        ContentValues values = new ContentValues();

        values.put("username", utilizador.getUsername());
        values.put("primeironome", utilizadorData.getPrimeironome());
        values.put("apelido", utilizadorData.getApelido());
        values.put("email", utilizador.getEmail());
        values.put("codigopostal", utilizadorData.getCodigopostal());
        values.put("rua", utilizadorData.getRua());
        values.put("localidade", utilizadorData.getLocalidade());
        values.put("dtanasc", utilizadorData.getDtanasc());
        values.put("telefone", utilizadorData.getTelefone());
        values.put("nif", utilizadorData.getNif());
        values.put("genero", utilizadorData.getGenero());
        values.put("auth_key", utilizador.getAuth_key());
        values.put("password_hash", utilizador.getPassword_hash());
        values.put("password_reset_token", utilizador.getPassword_reset_token());
        values.put("status", utilizador.getStatus());
        values.put("created_at", utilizador.getCreated_at());
        values.put("updated_at", utilizador.getUpdated_at());
        values.put("verification_token", utilizador.getVerification_token());


        // Assuming you have a UtilizadorDataBDHelper instance and a writable database
        UtilizadorBDHelper dbHelper = new UtilizadorBDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the user data into the database
        // Example query (you should adjust it based on your database schema):
        db.insert(TABLE_NAME, null, values);

        dbHelper.close();
    }

}
