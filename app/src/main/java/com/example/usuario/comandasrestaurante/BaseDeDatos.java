package com.example.usuario.comandasrestaurante;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by usuario on 09/03/2016.
 */
public class BaseDeDatos extends SQLiteOpenHelper{

    String sqlCreateMesas = "CREATE TABLE Mesas (IdMesa INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT)";

    public BaseDeDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateMesas);
        if(db != null) {
            for (int i = 1; i <= 6; i++) {
                db.execSQL("INSERT INTO Mesas (Nombre) " +
                        "VALUES ('Mesa" + i + "')");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
