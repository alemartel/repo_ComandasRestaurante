package com.example.usuario.comandasrestaurante;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by usuario on 09/03/2016.
 */
public class BaseDeDatos extends SQLiteOpenHelper{

    String sqlComandas = "CREATE TABLE Comandas (IdMesa INTEGER PRIMARY KEY AUTOINCREMENT, Fecha TEXT, Hora TEXT, Precio INTEGER)";
    String sqlLineaComand = "CREATE TABLE LineDeComanda (IdMesa INTEGER, IdProducto INTEGER, Cantidad INTEGER, Comentario TEXT, PRIMARY KEY(IdMesa, IdProducto))";
    String sqlCategorias = "CREATE TABLE Categorias (IdCategoria INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT)";
    String sqlProductos = "CREATE TABLE Productos (IdProducto INTEGER PRIMARY KEY AUTOINCREMENT, IdCategoria INTEGER, Nombre TEXT, Precio INTEGER, FOREIGN KEY(IdCategoria) REFERENCES Categorias(IdCategoria))";
    String sqlHistorial = "CREATE TABLE Historial (IdComanda INTEGER PRIMARY KEY AUTOINCREMENT, Fecha TEXT, Hora TEXT, Precio INTEGER)";
    String sqlAdminPanel = "CREATE TABLE AdminPanel (IdAdmin TEXT PRIMARY KEY, Contraseña TEXT, NMesas INTEGER)";

    public BaseDeDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlComandas);
        db.execSQL(sqlLineaComand);
        db.execSQL(sqlCategorias);
        db.execSQL(sqlProductos);
        db.execSQL(sqlHistorial);
        db.execSQL(sqlAdminPanel);
        int nmesas =20;
        db.execSQL("INSERT INTO AdminPanel (IdAdmin, Contraseña, NMesas) " + "VALUES ('admin','admin',"+nmesas+")");
        if(db != null) {
            for (int i = 1; i <= nmesas; i++) {
                db.execSQL("INSERT INTO Comandas (Fecha, Hora, Precio) " +
                        "VALUES ('','',0)");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean registro(String user, String pass){

        String[] args = new String[] {user, pass};

        Cursor log = getReadableDatabase().rawQuery("SELECT * FROM AdminPanel WHERE IdAdmin=? AND Contraseña=?", args);
        if(log != null){
            if(log.getCount() > 0){
                return true;
            }
        }
        return false;
    }

}