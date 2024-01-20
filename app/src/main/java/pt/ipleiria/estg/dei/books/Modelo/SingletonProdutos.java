package pt.ipleiria.estg.dei.books.Modelo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.books.LoginActivity;
import pt.ipleiria.estg.dei.books.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.FaturaListener;
import pt.ipleiria.estg.dei.books.listeners.FaturasListener;
import pt.ipleiria.estg.dei.books.listeners.LinhaCarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.LinhasCarrinhosListener;


import pt.ipleiria.estg.dei.books.listeners.LinhasFaturasListener;
import pt.ipleiria.estg.dei.books.listeners.LoginListener;
import pt.ipleiria.estg.dei.books.listeners.PagamentoListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutosListener;
import pt.ipleiria.estg.dei.books.listeners.ProfileUpdateListener;
import pt.ipleiria.estg.dei.books.utils.CarrinhoJsonParser;
import pt.ipleiria.estg.dei.books.utils.FaturasJsonParser;
import pt.ipleiria.estg.dei.books.utils.LinhaCarrinhoJsonParser;
import pt.ipleiria.estg.dei.books.listeners.SignupListener;
import pt.ipleiria.estg.dei.books.listeners.UtilizadorDataListener;
import pt.ipleiria.estg.dei.books.utils.LinhasFaturasJsonParser;
import pt.ipleiria.estg.dei.books.utils.LoginJsonParser;
import pt.ipleiria.estg.dei.books.utils.PagamentoJsonParser;
import pt.ipleiria.estg.dei.books.utils.ProdutoJsonParser;

public class SingletonProdutos {

    public ArrayList<Produto> produtos = new ArrayList<>();


    public ArrayList<Fatura> faturas = new ArrayList<>();
    public ArrayList<LinhasFaturas> linhasFaturas = new ArrayList<>();
    public ArrayList<LinhaCarrinho> linhaCarrinhos = new ArrayList<>();
    public Carrinho carrinho;
    public LinhaCarrinho linhaCarrinho;
    public Utilizador utilizador, utilizadorData;
    public Pagamento pagamento;
    private static volatile SingletonProdutos instance = null;
    private static int user_id;
    private static int carrinho_id;

    /*private ProdutoBDHelper produtosBD=null;*/

    private static RequestQueue volleyQueue = null;
    private LoginListener loginListener;
    private ProfileUpdateListener profileUpdateListener;
    private UtilizadorDataListener utilizadorDataListener;
    private SignupListener signupListener;
    private ProdutosListener produtosListener;
    private ProdutoListener produtoListener;
    private CarrinhoListener carrinhoListener;
    private LinhasCarrinhosListener linhasCarrinhosListener;
    private LinhaCarrinhoListener linhaCarrinhoListener;
    private FaturasListener faturasListener;
    private FaturaListener faturaListener;
    private LinhasFaturasListener linhasFaturasListener;
    private PagamentoListener pagamentoListener;
    private Utilizador loggedInUser;


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
        /*produtosBD = new ProdutoBDHelper(context);*/
    }

    //region GETTERS AND SETTERS

    public String getApiIP(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("api_url", Context.MODE_PRIVATE);
        return preferences.getString("API", null);
    }
    public ArrayList<Produto> getProdutos() {

        return produtos;
    }

    public ArrayList<Fatura> getFaturas() {
        return faturas;
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

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public Utilizador getUtilizadorData() {
        return utilizadorData;
    }

    public ArrayList<LinhaCarrinho> getLinhaCarrinhos() {
        return linhaCarrinhos;
    }

    public ArrayList<LinhasFaturas> getLinhasFaturas() {
        return linhasFaturas;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setSignupListener(SignupListener signupListener) {
        this.signupListener = signupListener;
    }

    public void setUtilizadorDataListener(UtilizadorDataListener utilizadorDataListener) {
        this.utilizadorDataListener = utilizadorDataListener;
    }

    public void setProdutosListener(ProdutosListener produtosListener) {
        this.produtosListener = produtosListener;
    }

    public void setLinhasCarrinhosListener(LinhasCarrinhosListener linhasCarrinhosListener) {
        this.linhasCarrinhosListener = linhasCarrinhosListener;
    }

    public void setLinhaCarrinhoListener(LinhaCarrinhoListener linhaCarrinhoListener) {
        this.linhaCarrinhoListener = linhaCarrinhoListener;
    }

    public void setCarrinhoListener(CarrinhoListener carrinhoListener) {
        this.carrinhoListener = carrinhoListener;
    }

    public void setProdutoListener(ProdutoListener produtoListener) {
        this.produtoListener = produtoListener;
    }

    public void setPagamentoListener(PagamentoListener pagamentoListener) {
        this.pagamentoListener = pagamentoListener;
    }

    public void setFaturasListener(FaturasListener faturaListener) {
        this.faturasListener = faturaListener;
    }

    public void setFaturaListener(FaturaListener faturaListener) {
        this.faturaListener = faturaListener;
    }

    public void setLinhasFaturasListener(LinhasFaturasListener linhasFaturasListener) {
        this.linhasFaturasListener = linhasFaturasListener;
    }

    public Produto getProduto(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id)
                return produto;
        }
        return null;
    }

    public Fatura getFaturaById(int id) {
        for (Fatura fatura : faturas) {
            if (fatura.getId() == id)
                return fatura;
        }
        return null;
    }

    public LinhaCarrinho getLinhaCarrinho(int id) {
        for (LinhaCarrinho linhaCarrinho : linhaCarrinhos) {
            if (linhaCarrinho.getIdLinha() == id)
                return linhaCarrinho;
        }
        return null;
    }
    //endregion

    //region PEDIDOS API CRUD
    public void getAllProdutosAPI(final Context context) {

        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();


            if (produtosListener != null)
                produtosListener.onRefreshListaProdutos(produtos);
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIProdutos(context), null, new Response.Listener<JSONArray>() {
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


    public void getCarrinhoAPI(final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();

        } else {

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlGetCarrinho(context), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Add this line to log the response
                    // converter json em livros
                    carrinho = CarrinhoJsonParser.parserJsonCarrinho(response);

                    Log.d("API_Response CARRINHO", response.toString());
                    Log.d("Carrinho", carrinho.toString());

                    // informar a vista
                    if (carrinhoListener != null) {

                        Log.d("CARRINHO LISTENER", carrinhoListener.toString());
                        carrinhoListener.onRefreshListaCarrinho(carrinho);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("API_Response CARRINHO", error.toString());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void adicionarCarrinhoAPI(final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlApiPostCarrinho(context), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //  Toast.makeText(context, "Erro ao adicionar carrinho", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", getUserId(context) + "");

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }


    public void getLinhasCarrinhosAPI(final Context context, Carrinho carrinho) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();


            if (linhasCarrinhosListener != null)
                linhasCarrinhosListener.onRefreshListaLinhasCarrinhos(linhaCarrinhos);
        } else {

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlGetLinhasCarrinho(carrinho.getId(), context), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // Add this line to log the response
                    // converter json em livros
                    linhaCarrinhos = LinhaCarrinhoJsonParser.parserJsonLinhaCarrinho(response);
                    Log.d("API_Response", response.toString());
                    Log.d("LinhaCarrinho", linhaCarrinhos.toString());

                    // informar a vista
                    if (linhasCarrinhosListener != null) {

                        Log.d("LINHAS CARRINHO LISTENER", linhasCarrinhosListener.toString());
                        linhasCarrinhosListener.onRefreshListaLinhasCarrinhos(linhaCarrinhos);
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

    public void updateLinhaCarrinhoAPI(final Context context, final LinhaCarrinho linhaCarrinho) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlUpdateLinha(linhaCarrinho.getIdLinha(), context), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (linhaCarrinhoListener != null) {
                        Log.d("LINHAS CARRINHO LISTENER", linhaCarrinhoListener.toString());
                        linhaCarrinhoListener.onItemUpdate();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("quantidade", linhaCarrinho.getQuantidade() + "");

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }


    public void adicionarLinhaCarrinhoAPI(final Context context, Produto produto, Carrinho carrinho) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIPostLinhaCarrinho(context), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (linhaCarrinhoListener != null) {
                        Log.d("LINHAS CARRINHO LISTENER", response);
                        linhaCarrinhoListener.onItemUpdate();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String response = new String(error.networkResponse.data);
                    Log.e("VolleyError", "Error Response: " + response);
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("quantidade", "1");
                    params.put("produto_id", produto.getId() + "");
                    params.put("carrinho_id", carrinho.getId() + "");

                    return params;
                }
            };
            volleyQueue.add(req);
        }

    }

    public void deleteLinhaCarrinhoAPI(final Context context, final LinhaCarrinho linhaCarrinho) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.DELETE, urlDeleteLinha(linhaCarrinho.getIdLinha(), context), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (linhaCarrinhoListener != null) {
                        Log.d("LINHAS CARRINHO LISTENER", linhaCarrinhoListener.toString());
                        linhaCarrinhoListener.onItemUpdate();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String response = new String(error.networkResponse.data);
                    Log.e("VolleyError", "Error Response: " + response);
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public int getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return preferences.getInt("user_id", 0); // 0 is the default value if the user ID is not found
    }

    public String getUserToken(Context context) {
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
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, mUrlAPILogin(context), jsonParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    utilizador = LoginJsonParser.parserJsonLogin(response);

                    // Save the user's ID, token, and username to SharedPreferences
                    saveUserId(context, utilizador.getId());
                    saveUserToken(context, utilizador.getAuth_key(), utilizador.getUsername());

                    // Add the user to the local database only if it doesn't already exist
                    if (utilizador.getId() != 0 || utilizador.getAuth_key() != null) {
                        getUserDataAPI(context);
                    }

                    if (loginListener != null) {
                        loginListener.onUpdateLogin(utilizador);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Credenciais incorretas", Toast.LENGTH_SHORT).show();

                    if (utilizador != null) {
                        Log.e("LoginAPI", "Utilizador not null: " + utilizador.getUsername());
                        // Check if other conditions or actions need to be taken
                    } else {
                        Log.e("LoginAPI", "Utilizador is null");
                    }

                    // Check if loginListener is not null before using it
                    if (loginListener != null) {
                        loginListener.onUpdateLogin(utilizador);
                    }
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getUserDataAPI(Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            int utilizadorID = getUserId(context); // Fetch user ID from SharedPreferences
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIUserData(context), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject item = response.getJSONObject(i);
                            utilizadorData = LoginJsonParser.parserJsonGetUtilizadorData(item);

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


    public void saveUserToken(Context context, String token, String username) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_token", token);
        editor.putString("username", username);
        editor.apply();
    }

    public void saveUserId(Context context, int userId) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user_id", userId);
        editor.apply();
    }

    public void signupAPI(final String username, final String password, final String email, final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("username", username);
                jsonParams.put("password", password);
                jsonParams.put("email", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, mUrlAPISignup(context), jsonParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    utilizador = LoginJsonParser.parserJsonLogin(response);

                    // Update the loggedInUser in SingletonProdutos with the new user data
                    loggedInUser = utilizador;

                    // Save the user's ID and token to SharedPreferences
                    saveUserId(context, utilizador.getId());
                    saveUserToken(context, utilizador.getAuth_key(), utilizador.getUsername());

                    // Perform additional actions as needed
                    if (signupListener != null) {
                        signupListener.onUpdateSignup(utilizador);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error durante o signup", Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void logout(Context context) {
        // Clear the SharedPreferences data
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        utilizador = null;
        utilizadorData = null;

        // Other logout-related actions can be added here if needed

        // Redirect the user to the login screen or perform any necessary actions
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish(); // Finish the current activity to prevent going back to it
    }


    public void updateProfileAPI(final String primeironome, final String apelido, final String telemovel, final String nif, final String genero, final String dtaNascimento, final String rua, final String localidade, final String codigoPostal, final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("primeironome", primeironome);
                jsonParams.put("apelido", apelido);
                jsonParams.put("telefone", telemovel);
                jsonParams.put("nif", nif);
                jsonParams.put("genero", genero);
                jsonParams.put("dtanasc", dtaNascimento);
                jsonParams.put("rua", rua);
                jsonParams.put("localidade", localidade);
                jsonParams.put("codigopostal", codigoPostal);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlPostAPIPerfilDados(context), jsonParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Handle the response from the server after updating the profile
                    // For example, you may parse the response JSON and update the UI or perform additional actions

                    // Notify listeners or update UI as needed
                    if (profileUpdateListener != null) {
                        profileUpdateListener.onProfileUpdated(response.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error response
                    Toast.makeText(context, "Error durante a atualização do perfil", Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void adicionarFaturaAPI(final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlApiPostFatura(context), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (faturasListener != null) {
                        Log.d("POST FATURAS", response);
                        faturasListener.onRefreshListaFatura(faturas);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String response = new String(error.networkResponse.data);
                    Log.e("VolleyError", "Error Response: " + response);
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", getUserId(context) + "");


                    return params;
                }
            };
            volleyQueue.add(req);
        }

    }


    public void getFaturasAPI(final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
            if (faturasListener != null) {

                faturasListener.onRefreshListaFatura(faturas);
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlGetFaturas(context), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    faturas = FaturasJsonParser.parserJsonFaturas(response);
                    //getLinhasFaturasAPI(context, faturas.get(0));
                    Log.d("Faturas", faturas.toString());
                    if (faturasListener != null) {
                        Log.d("GET FATURAS", response.toString());
                        faturasListener.onRefreshListaFatura(faturas);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("GET FATURAS error", error.getMessage());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            );
            volleyQueue.add(req);

        }
    }

    public void getLinhasFaturasAPI(final Context context, Fatura fatura) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlGetLinhasFaturas(fatura.getId(), context), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    linhasFaturas = LinhasFaturasJsonParser.parserJsonLinhasFaturas(response);
                    Log.d("Fatuas", linhasFaturas.toString());
                    if (linhasFaturasListener != null) {
                        Log.d("GET LINHAS FATURAS", response.toString());
                        linhasFaturasListener.onRefreshListaLinhasFaturas(fatura);
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

    public void adicionarPagamentoAPI(final Context context, String metodoPagamento, String metodoEnvio) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIPostPagamento(context), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (pagamentoListener != null) {
                        Log.d("POST PAGAMENTO", response);
                        pagamentoListener.onRefreshPagamento();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String response = new String(error.networkResponse.data);
                    Log.e("VolleyError", "Error Response: " + response);
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("metodopag", metodoPagamento);
                    params.put("metodo_envio", metodoEnvio);
                    params.put("user_id", +getUserId(context) + "");

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getPagamentoAPI(final Context context, int pagamentoid) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlGetPagamento(context, pagamentoid), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pagamento = PagamentoJsonParser.parserJsonPagamento(response.toString());
                    if (pagamentoListener != null) {
                        Log.d("GET PAGAMENTO", response.toString());
                        pagamentoListener.onRefreshPagamento();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao obter pagamento", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }
    //endregion

    //region URLS API
    public String mUrlAPIProdutos(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-SIS/backend/web/api/produtos/all?access-token=" + getUserToken(context);
    }

    private String urlPostAPIPerfilDados(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/users/" + getUserId(context) + "/criar?access-token=" + getUserToken(context);
    }

    private String urlDeleteLinha(int id, Context context) {

        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/produtoscarrinhos/" + id + "/delete?access-token=" + getUserToken(context);
    }

    private String mUrlAPIUserData(Context context) {
        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/users/" + getUserId(context) + "?access-token=" + getUserToken(context);
    }

    private String mUrlGetFaturas(Context context) {
        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/faturas/" + getUserId(context) + "/user?access-token=" + getUserToken(context);
    }

    private String mUrlGetLinhasFaturas(int fatura_id, Context context) {
        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/faturas/" + fatura_id + "/dados?access-token=" + getUserToken(context);
    }

    private String mUrlUpdateLinha(int id, Context context) {
        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/produtoscarrinhos/" + id + "/update?access-token=" + getUserToken(context);
    }

    private String mUrlGetLinhasCarrinho(int carrinho_id, Context context) {
        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/produtoscarrinhos/" + carrinho_id + "/dados?access-token=" + getUserToken(context);
    }

    private String mUrlGetCarrinho(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/carrinhos/" + getUserId(context) + "/dados?access-token=" + getUserToken(context);
    }

    private String mUrlGetPagamento(Context context, int pagamentoid) {
        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/pagamentos/" + pagamentoid + "/dados?access-token=" + getUserToken(context);
    }

    //MAKE A FUNCTION TO ALL THE LINKS BELLOW:
    private String mUrlAPIPostLinhaCarrinho(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/produtoscarrinhos/criar?access-token=" + getUserToken(context);
    }

    private String mUrlApiPostFatura(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/faturas/criar?access-token=" + getUserToken(context);
    }

    private String mUrlApiPostCarrinho(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/carrinhos/criar?access-token=" + getUserToken(context);
    }

    private String mUrlAPILogin(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/auth/login";
    }

    private String mUrlAPISignup(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-SIS/backend/web/api/auth/register";
    }

    private String mUrlAPIPostPagamento(Context context) {

        return "http://" + getApiIP(context) + "/AMAI-plataformas/backend/web/api/pagamentos/criar?access-token=" + getUserToken(context);
    }
//endregion


}
