package pt.ipleiria.estg.dei.books.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProdutoBDHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "BDProdutos", TABLE_NAME = "produtos";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    public static final String ID = "id", NOME = "nome", DESCRICAO = "descricao", PRECO = "preco",
            OBS = "obs", CATEGORIA = "categoria", IVA = "iva", IMAGEM = "imagens";


    public ProdutoBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlTableProdutos = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME + " TEXT NOT NULL," +
                DESCRICAO + " TEXT NOT NULL," +
                PRECO + " REAL NOT NULL," +
                OBS + " TEXT NOT NULL," +
                CATEGORIA + " TEXT NOT NULL," +
                IVA + "FLOAT NOT NULL," +
                IMAGEM + "TEXT NOT NULL" + ")";

        sqLiteDatabase.execSQL(sqlTableProdutos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //region
    public Produto adicionarProdutoBD(Produto produto) {
        ContentValues values = new ContentValues();
        values.put(ID, produto.getId());
        values.put(NOME, produto.getNome());
        values.put(DESCRICAO, produto.getDescricao());
        values.put(PRECO, produto.getPreco());
        values.put(OBS, produto.getObs());
        values.put(CATEGORIA, produto.getCategoria());
        values.put(IVA, produto.getIva());
        db.insert(TABLE_NAME, null, values);

        return produto;
    }

   /* public ArrayList<Produto> getAllProdutosBD() {
        ArrayList<Produto> produtos = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, NOME, DESCRICAO, PRECO, OBS, CATEGORIA, IVA}, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Produto auxProduto = new Produto(
                        cursor.getInt(0),
                        cursor.getDouble(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                produtos.add(auxProduto);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return produtos;
    }*/
}
