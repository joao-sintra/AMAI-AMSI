package pt.ipleiria.estg.dei.books.Modelo;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.books.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.LinhaCarrinhoListener;
import pt.ipleiria.estg.dei.books.listeners.LinhasCarrinhosListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutoListener;
import pt.ipleiria.estg.dei.books.listeners.ProdutosListener;
import pt.ipleiria.estg.dei.books.utils.CarrinhoJsonParser;
import pt.ipleiria.estg.dei.books.utils.LinhaCarrinhoJsonParser;
import pt.ipleiria.estg.dei.books.utils.ProdutoJsonParser;

public class SingletonProdutos {


    private static final String TOKEN = "fBF_qwu_kIXpMydCXbsqYSpcHfeJyk-E";
    public ArrayList<Produto> produtos = new ArrayList<>();
    public Carrinho carrinho;
    public ArrayList<LinhaCarrinho> linhaCarrinhos = new ArrayList<>();
    public LinhaCarrinho linhaCarrinho;
    private static volatile SingletonProdutos instance = null;
    private static final String mUrlAPIProdutos = "http://172.22.21.211/AMAI-plataformas/backend/web/api/produtos/all?access-token=" + TOKEN;
    private static int user_id;
    private static int carrinho_id;
    private static final String mUrlAPIGetCarrinho = "http://172.22.21.211/AMAI-plataformas/backend/web/api/carrinhos/" + user_id + "/dados?access-token=" + TOKEN;
    private static final String mUrlAPIGetCarrinhoProdutos = "http://172.22.21.211/AMAI-plataformas/backend/web/api/produtoscarrinhos/32/dados?access-token=" + TOKEN;
    private static final String mUrlAPIPostLinhaCarrinho = "http://172.22.21.211/AMAI-plataformas/backend/web/api/produtoscarrinhos/criar?access-token=" + TOKEN;

    //private LivroBDHelper livrosBD=null;
    private static RequestQueue volleyQueue = null;


    // private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";
    //public static final String TOKEN = "AMSI-TOKEN";


    private ProdutosListener produtosListener;
    private ProdutoListener produtoListener;
    private CarrinhoListener carrinhoListener;
    private LinhasCarrinhosListener linhasCarrinhosListener;
    private LinhaCarrinhoListener linhaCarrinhoListener;


    public static synchronized SingletonProdutos getInstance(Context context) {
        if (instance == null) {
            synchronized (SingletonProdutos.class){
                if (instance == null){
                    instance = new SingletonProdutos(context);
                    volleyQueue = Volley.newRequestQueue(context);
                }
            }
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

    public ArrayList<LinhaCarrinho> getLinhaCarrinhos() {
        return linhaCarrinhos;
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

    public Produto getProduto(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id)
                return produto;
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

    public void getAllProdutosAPI(final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();


            if (produtosListener != null)
                produtosListener.onRefreshListaProdutos(produtos);
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // Add this line to log the response
                    // converter json em livros
                    produtos = ProdutoJsonParser.parserJsonProdutos(response);
                    Log.d("API_Response", response.toString());
                    Log.d("Produtos", produtos.toString());

                    // informar a vista
                    if (produtosListener != null) {

                        Log.d("PRODUTOS LISTENER", produtosListener.toString());
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
            user_id = 75;
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlAPIGetCarrinho, null, new Response.Listener<JSONObject>() {
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
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getLinhasProdutosAPI(final Context context) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();


            if (linhasCarrinhosListener != null)
                linhasCarrinhosListener.onRefreshListaLinhasCarrinhos(linhaCarrinhos);
        } else {

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIGetCarrinhoProdutos, null, new Response.Listener<JSONArray>() {
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
            StringRequest req = new StringRequest(Request.Method.PUT, urlUpdateLinha(linhaCarrinho.getIdLinha()), new Response.Listener<String>() {
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
                    Map<String,String> params = new HashMap<>();
                    params.put("quantidade", linhaCarrinho.getQuantidade()+"");

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }
    private String urlUpdateLinha(int id) {

        return "http://172.22.21.211/AMAI-plataformas/backend/web/api/produtoscarrinhos/"+id+"/update?access-token=" + TOKEN;
    }

    public void adicionarLinhaCarrinhoAPI(final Context context, Produto produto) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIPostLinhaCarrinho, new Response.Listener<String>() {
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
                    Map<String,String> params = new HashMap<>();
                    params.put("quantidade", "1");
                    params.put("produto_id", produto.getId()+"");
                    params.put("carrinho_id", "32");

                    return params;
                }
            };
            volleyQueue.add(req);
        }

    }
    private String urlDeleteLinha(int id) {

        return "http://172.22.21.211/AMAI-plataformas/backend/web/api/produtoscarrinhos/" + id + "/delete?access-token=" + TOKEN;
    }
    public void deleteLinhaCarrinhoAPI(final Context context, final LinhaCarrinho linhaCarrinho) {
        if (!ProdutoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.DELETE, urlDeleteLinha(linhaCarrinho.getIdLinha()), new Response.Listener<String>() {
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
            });
            volleyQueue.add(req);
        }
    }
}
