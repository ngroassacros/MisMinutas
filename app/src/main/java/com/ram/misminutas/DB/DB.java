package com.ram.misminutas.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ramses on 12/11/14.
 */
public class DB extends SQLiteOpenHelper {
    private static final int VERSION_BASEDATOS = 1;

    // Nombre de nuestro archivo de base de datos
    private static final String NOMBRE_BASEDATOS = "MisMinutas.db";

    // CONSTRUCTOR de la clase
    public DB(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);

    }

    // Sentencia SQL para la creación de una tabla
    private static final String TABLA_USUARIO = "CREATE TABLE Usuario " + "( id INT PRIMARY KEY, Nombre TEXT, Email TEXT, Telefono INT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIO);
        onCreate(db);
    }
}
