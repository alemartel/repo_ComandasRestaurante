package com.example.usuario.comandasrestaurante;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

/**
 * Created by usuario on 09/03/2016.
 */
public class BaseDeDatos extends SQLiteOpenHelper{

    String sqlMesas = "CREATE TABLE Mesas (idMesa INTEGER PRIMARY KEY AUTOINCREMENT, nombreMesa TEXT)";
    String sqlComandas = "CREATE TABLE Comandas (idComanda INTEGER PRIMARY KEY AUTOINCREMENT,idMesa INTEGER NOT NULL, horaCierre FLOAT DEFAULT 0, precio FLOAT DEFAULT 0, FOREIGN KEY(IdMesa) REFERENCES Mesas(idMesa))";
    String sqlLineaComand = "CREATE TABLE LineaComanda (idLinea INTEGER PRIMARY KEY AUTOINCREMENT, idComanda INTEGER NOT NULL, idProducto INTEGER NOT NULL, tipo INTEGER, cocineroFinalizacion FLOAT DEFAULT 0, comentario TEXT DEFAULT NULL, FOREIGN KEY(idComanda) REFERENCES Comandas(idComanda))";
    String sqlCategorias = "CREATE TABLE Categorias (idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)";
    String sqlProductos = "CREATE TABLE Productos (idProducto INTEGER PRIMARY KEY AUTOINCREMENT, idCategoria INTEGER, nombre TEXT, tipo INTEGER, precio FLOAT, FOREIGN KEY(idCategoria) REFERENCES Categorias(idCategoria))";
    /*
    tipo = 0 => no necesita preparacion
    tipo = 1 => necesita preparacion del cocinero
    */
    String sqlAdminPanel = "CREATE TABLE AdminPanel (idAdmin TEXT PRIMARY KEY, contraseña TEXT, nMesas INTEGER)";

    public BaseDeDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlMesas);
        db.execSQL(sqlComandas);
        db.execSQL(sqlLineaComand);
        db.execSQL(sqlCategorias);
        db.execSQL(sqlProductos);
        db.execSQL(sqlAdminPanel);
        int nmesas =20;
        db.execSQL("INSERT INTO AdminPanel (idAdmin, contraseña, nMesas) " + "VALUES ('admin','admin',"+nmesas+")");
        if(db != null) {
            for (int i = 0; i < nmesas; i++) {
                db.execSQL("INSERT INTO Mesas (nombreMesa) " +
                        "VALUES ('Mesa " + i+1 + "')");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean registro(String user, String pass){

        String[] args = new String[] {user, pass};

        Cursor log = getReadableDatabase().rawQuery("SELECT * FROM AdminPanel WHERE idAdmin=? AND contraseña=?", args);
        if(log != null){
            if(log.getCount() > 0){
                return true;
            }
        }
        return false;
    }

}