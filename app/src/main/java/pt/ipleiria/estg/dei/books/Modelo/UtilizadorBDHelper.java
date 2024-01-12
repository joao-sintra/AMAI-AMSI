package pt.ipleiria.estg.dei.books.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UtilizadorBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BDUtilizadores";
    public static final String TABLE_NAME = "BDUtilizadores";

    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    public static final String USERNAME = "USERNAME", EMAIL = "email", ID = "id", PRIMEIRO_NOME = "PRIMEIRO_NOME", APELIDO = "APELIDO",
            PASSWORD = "password", MORADA = "morada", COD_POSTAL = "cod_postal", RUA = "rua", LOCALIDADE = "localidade", TELEFONE = "telefone", NIF = "nif";


    public UtilizadorBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTableUtilizadores = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " TEXT NOT NULL," +
                PRIMEIRO_NOME + " TEXT NOT NULL," +
                APELIDO + " TEXT NOT NULL," +
                EMAIL + " TEXT NOT NULL," +
                PASSWORD + " TEXT NOT NULL," +
                MORADA + " TEXT NOT NULL," +
                COD_POSTAL + " TEXT NOT NULL," +
                RUA + " TEXT NOT NULL," +
                LOCALIDADE + " TEXT NOT NULL," +
                TELEFONE + " TEXT NOT NULL," +
                NIF + " TEXT NOT NULL);";

        db.execSQL(sqlTableUtilizadores);

    }

    public Utilizador adicionarUtilizadorBD(Utilizador utilizador) {
        ContentValues values = new ContentValues();

        values.put(USERNAME, utilizador.getUsername());
        values.put(PRIMEIRO_NOME, utilizador.getPrimeiroNome());
        values.put(APELIDO, utilizador.getApelido());
        values.put(EMAIL, utilizador.getEmail());
        values.put(PASSWORD, utilizador.getPassword());
        values.put(MORADA, utilizador.getMorada());
        values.put(COD_POSTAL, utilizador.getCod_postal());
        values.put(RUA, utilizador.getRua());
        values.put(LOCALIDADE, utilizador.getLocalidade());
        values.put(TELEFONE, utilizador.getTelefone());
        values.put(NIF, utilizador.getNif());
        db.insert(TABLE_NAME, null, values);

        return utilizador;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
