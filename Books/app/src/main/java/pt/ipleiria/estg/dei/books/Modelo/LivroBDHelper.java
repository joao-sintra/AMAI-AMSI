package pt.ipleiria.estg.dei.books.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LivroBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BDLivros", TABLE_NAME = "livros";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private static final String ID = "id", CAPA = "capa", ANO = "ano", TITULO = "titulo", SERIE = "serie", AUTOR = "autor";


    public LivroBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlTableLivros = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                CAPA + " INTEGER, " +
                ANO + " INTEGER NOT NULL, " +
                TITULO + " TEXT NOT NULL, " +
                SERIE + " TEXT NOT NULL, " +
                AUTOR + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(sqlTableLivros);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //region
    public Livro adicionarLivroBD(Livro livro){
        ContentValues values = new ContentValues();
        values.put(ID, livro.getId());
        values.put(TITULO, livro.getTitulo());
        values.put(SERIE, livro.getSerie());
        values.put(AUTOR, livro.getAutor());
        values.put(ANO, livro.getAno());
        values.put(CAPA, livro.getCapa());
        db.insert(TABLE_NAME, null, values);
        return livro;
    }

    public boolean editarLivroBD(Livro livro){
        ContentValues values = new ContentValues();
        values.put(TITULO, livro.getTitulo());
        values.put(SERIE, livro.getSerie());
        values.put(AUTOR, livro.getAutor());
        values.put(ANO, livro.getAno());
        values.put(CAPA, livro.getCapa());
        //insert devolve o id do livro inserido, caso não seja possível inderir devolve -1
        int nLinhasEdit = (int) db.update(TABLE_NAME,values, ID + " = ?", new String[] {livro.getId()+""});
        return nLinhasEdit>0;
    }

    public boolean removerLivroBD(int idLivro){
        int nLinhasDel = db.delete(TABLE_NAME, ID + " = ?", new String[] {idLivro+""});
        return nLinhasDel>0;
    }

    public void removerAllLivrosBD(){
        db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<Livro> getAllLivrosBD(){
        ArrayList<Livro> livros = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CAPA, ANO, TITULO, SERIE, AUTOR}, null, null,
                null, null, null);
        if(cursor.moveToFirst()){
            do{
                Livro auxLivro = new Livro(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5));
                livros.add(auxLivro);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return livros;
    }



    //endregion

}
