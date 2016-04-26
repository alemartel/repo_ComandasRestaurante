package com.example.usuario.comandasrestaurante;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Khondax on 26/04/2016.
 */
public class Historial extends AppCompatActivity{

    GridLayout gridComandas;


    @Override
    protected void onCreate(Bundle savedInstanBundle){
        super.onCreate(savedInstanBundle);
        setContentView (R.layout.activity_historial);

        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db = database.getReadableDatabase();

        gridComandas = (GridLayout) findViewById(R.id.gridComandas);
        Cursor comand = db.rawQuery("SELECT * FROM Comandas WHERE horaCierre>0 ORDER BY horaCierre DESC", null);

        TextView tituloIdComanda = new TextView(this);
        TextView tituloIdMesa = new TextView(this);
        TextView tituloFecha = new TextView(this);
        TextView tituloPrecio = new TextView(this);

        tituloIdComanda.append("Comanda");
        tituloIdMesa.append("Mesa");
        tituloFecha.append("Fecha");
        tituloPrecio.append("PVP");

        tituloIdComanda.setTypeface(Typeface.DEFAULT_BOLD);
        tituloIdMesa.setTypeface(Typeface.DEFAULT_BOLD);
        tituloFecha.setTypeface(Typeface.DEFAULT_BOLD);
        tituloPrecio.setTypeface(Typeface.DEFAULT_BOLD);

        GridLayout.LayoutParams gridTituloComanda = new GridLayout.LayoutParams();
        GridLayout.LayoutParams gridTituloMesa = new GridLayout.LayoutParams();
        GridLayout.LayoutParams gridTituloFecha = new GridLayout.LayoutParams();
        GridLayout.LayoutParams gridTituloPrecio = new GridLayout.LayoutParams();

        gridTituloComanda.setMargins(30, 0, 30, 0); // (left, top, right, bottom)
        gridTituloMesa.setMargins(40, 0, 40, 0);
        gridTituloFecha.setMargins(40, 0, 40, 0);
        gridTituloPrecio.setMargins(30, 0, 20, 0);

        tituloIdComanda.setLayoutParams(gridTituloComanda);
        tituloIdMesa.setLayoutParams(gridTituloMesa);
        tituloFecha.setLayoutParams(gridTituloFecha);
        tituloPrecio.setLayoutParams(gridTituloPrecio);

        gridComandas.addView(tituloIdComanda);
        gridComandas.addView(tituloIdMesa);
        gridComandas.addView(tituloFecha);
        gridComandas.addView(tituloPrecio);

        if (comand.moveToFirst()){
            do{
                TextView textIdComanda = new TextView(this);
                TextView textIdMesa = new TextView(this);
                TextView textFecha = new TextView(this);
                TextView textPrecio = new TextView(this);

                textIdComanda.append(comand.getString(0));
                textIdMesa.append(comand.getString(1));
                textFecha.append(comand.getString(2));
                textPrecio.append(comand.getString(3) + "€");

                GridLayout.LayoutParams gridLayoutIdComanda = new GridLayout.LayoutParams();
                GridLayout.LayoutParams gridLayoutIdMesa = new GridLayout.LayoutParams();
                GridLayout.LayoutParams gridLayoutHoraCierre = new GridLayout.LayoutParams();
                GridLayout.LayoutParams gridLayoutPrecio = new GridLayout.LayoutParams();

                gridLayoutIdComanda.setMargins(30, 0, 30, 0); // (left, top, right, bottom)
                gridLayoutIdMesa.setMargins(40, 0, 40, 0);
                gridLayoutHoraCierre.setMargins(40, 0, 40, 0);
                gridLayoutPrecio.setMargins(30, 0, 20, 0);

                textIdComanda.setLayoutParams(gridLayoutIdComanda);
                textIdMesa.setLayoutParams(gridLayoutIdMesa);
                textFecha.setLayoutParams(gridLayoutHoraCierre);
                textPrecio.setLayoutParams(gridLayoutPrecio);

                gridComandas.addView(textIdComanda);
                gridComandas.addView(textIdMesa);
                gridComandas.addView(textFecha);
                gridComandas.addView(textPrecio);

            } while (comand.moveToNext());
        }




    }



}
