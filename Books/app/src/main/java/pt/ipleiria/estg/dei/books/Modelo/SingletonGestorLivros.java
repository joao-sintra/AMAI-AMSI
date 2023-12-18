package pt.ipleiria.estg.dei.books.Modelo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.books.MenuMainActivity;
import pt.ipleiria.estg.dei.books.listeners.LivroListener;
import pt.ipleiria.estg.dei.books.listeners.LivrosListener;
import pt.ipleiria.estg.dei.books.listeners.LoginListener;
import pt.ipleiria.estg.dei.books.utils.LivroJsonParser;

public class SingletonGestorLivros { //SingletonGestorStand

    private ArrayList<Livro> livros; //se fosse o meu projero era uma lista de Carros e Produtos em um unico Singleton
    private static SingletonGestorLivros instance = null;
    private LivroBDHelper livrosBD=null;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILivros = "http://amsi.dei.estg.ipleiria.pt/api/livros";
    private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";
    public static final String TOKEN = "AMSI-TOKEN";
    private LivrosListener livrosListener;
    private LivroListener livroListener;
    private LoginListener loginListener;



    //metodo define que se trata de um Singleton
    public static synchronized SingletonGestorLivros getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorLivros(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorLivros(Context context) {
        livros = new ArrayList<>();
        livrosBD = new LivroBDHelper(context);
    }

    public void setLivroListener(LivroListener livroListener) {
        this.livroListener = livroListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setLivrosListener(LivrosListener livrosListener) {
        this.livrosListener = livrosListener;
    }

    public ArrayList<Livro> getLivrosBD() {
        livros = livrosBD.getAllLivrosBD();
        return new ArrayList<>(livros);
    }

    public Livro getLivro(int id) {
        for ( Livro l : livros) {
            if (l.getId() == id)
                return l;
        }
        return null;
    }

    //region ACESSOS BD

    public void adicionarLivroBD(Livro livro) {
        livrosBD.adicionarLivroBD(livro);
    }

    public void adicionarAllLivrosBD(ArrayList<Livro> livros) {
        livrosBD.removerAllLivrosBD();
        for (Livro livro : livros) {
            adicionarLivroBD(livro);
        }
    }

    public void editarLivroBD(Livro livro) {
       if(livro != null){
           livrosBD.editarLivroBD(livro);
       }
    }

    public void removerLivroBD(int idLivro) {
        livrosBD.removerLivroBD(idLivro);
    }

    //endregion

    //region PEDIDOS API CRUD

    public void adicionarLivroAPI(final Livro livro, final Context context, final String tokenAPI) {
        if(!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPILivros, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //vou guardar no BDLocal para funcionar offline
                    adicionarLivroBD(LivroJsonParser.parserJsonLivro(response));

                    //informar a vista
                    if(livroListener != null) {
                        livroListener.onRefreshDetalhes(MenuMainActivity.ADD);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("token", tokenAPI);
                    params.put("titulo", livro.getTitulo());
                    params.put("serie", livro.getSerie());
                    params.put("autor", livro.getAutor());
                    params.put("ano", livro.getAno() + "");
                    params.put("capa", livro.getCapa());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getAllLivrosAPI(final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
            // se não tem internet vai buscar os livros à BD
            livros = livrosBD.getAllLivrosBD();

            if(livrosListener != null)
                livrosListener.onRefreshListaLivros(livros);
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPILivros, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    livros = LivroJsonParser.parserJsonLivros(response);
                    adicionarAllLivrosBD(livros);

                    // informar a vista
                    if(livrosListener != null)
                        livrosListener.onRefreshListaLivros(livros);
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

    public void editarLivroAPI(final Livro livro, final Context context, final String tokenAPI) {
        if (!LivroJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPILivros+"/"+livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   editarLivroBD(livro);

                    //informar a vista
                    if(livroListener != null) {
                        livroListener.onRefreshDetalhes(MenuMainActivity.EDIT);
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
                    Map<String,String> params = new HashMap<>();
                    params.put("token", tokenAPI);
                    params.put("titulo", livro.getTitulo());
                    params.put("serie", livro.getSerie());
                    params.put("autor", livro.getAutor());
                    params.put("ano", livro.getAno() + "");
                    params.put("capa", livro.getCapa());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void removerLivroAPI(final Livro livro, final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPILivros + "/" + livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    removerLivroBD(livro.getId());

                    //informar a vista
                    if(livroListener != null) {
                        livroListener.onRefreshDetalhes(MenuMainActivity.DEL);
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

    public void loginAPI(final String email, final String password, final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   String token = LivroJsonParser.parserJsonLogin(response);

                    if(loginListener != null) {
                        loginListener.onUpdateLogin(token);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Token incorreto", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    //endregion
}

