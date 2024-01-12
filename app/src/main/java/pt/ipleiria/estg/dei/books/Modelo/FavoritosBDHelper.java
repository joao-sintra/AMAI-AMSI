package pt.ipleiria.estg.dei.books.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FavoritosBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BDFavoritos";
    public static final String TABLE_NAME = "Favoritos";

    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    private static final String utilizador_id = "utilizador_id", ID = "ID",
            produto_id = "produto_id";


    public FavoritosBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTableFavoritos = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "utilizador_id INTEGER," +
                "produto_id INTEGER," +
                "PRIMARY KEY (utilizador_id, produto_id)," +
                "FOREIGN KEY (utilizador_id) REFERENCES utilizador(id)," +
                "FOREIGN KEY (produto_id) REFERENCES produto(id));";
        db.execSQL(sqlTableFavoritos);
    }

    public Favoritos adicionarFavoritosBD(Favoritos favoritos) {
        ContentValues values = new ContentValues();

        values.put(utilizador_id, favoritos.getId_user());
        values.put(produto_id, favoritos.getId_produto());

        db.insert(TABLE_NAME, null, values);

        return favoritos;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
