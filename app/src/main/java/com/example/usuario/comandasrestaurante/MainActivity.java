package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GridLayout grid;
    //ImageButton settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db = database.getWritableDatabase();

        GridLayout myLayout = (GridLayout) findViewById(R.id.gridMesas);

        Cursor c = db.rawQuery("SELECT * FROM Mesas", null);
        if(c.moveToFirst()){
            do {
                String nombre=c.getString(1);

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.mesalibre);

                TextView textView = new TextView(this);
                textView.append(nombre);
                GridLayout.LayoutParams glptext = new GridLayout.LayoutParams();
                glptext.setMargins(45, 0, 0, 0); // (left, top, right, bottom);
                textView.setLayoutParams(glptext);

                grid = new GridLayout(this);
                grid.setRowCount(2);
                grid.setColumnCount(1);
                grid.addView(textView);
                grid.addView(imageView);
                GridLayout.LayoutParams glpgrid = new GridLayout.LayoutParams();
                glpgrid.setMargins(70, 30, 0, 0); // (left, top, right, bottom);
                grid.setLayoutParams(glpgrid);
                grid.setOnClickListener(this);
                myLayout.addView(grid);

            }while(c.moveToNext());
        }
        db.close();

    }

    @Override
    public void onClick(View v) {
        setContentView(R.layout.activity_comanda);
    }
}
