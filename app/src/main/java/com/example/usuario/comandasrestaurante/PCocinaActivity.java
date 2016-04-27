package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class PCocinaActivity extends AppCompatActivity {

    GridLayout gridPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcocina);

        gridPanel = (GridLayout) findViewById(R.id.gridPCocina);
        TextView C1 = new TextView(this);
        TextView C3 = new TextView(this);
        TextView C4 = new TextView(this);
        TextView C5 = new TextView(this);
        C1.append("Id");
        C3.append("Producto");
        C3.append("");
        C5.append("Finalizado");
        gridPanel.addView(C1);
        gridPanel.addView(C3);
        gridPanel.addView(C4);
        gridPanel.addView(C5);
        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        final SQLiteDatabase db = database.getWritableDatabase();
        Cursor c= db.rawQuery("SELECT * FROM LineaComanda WHERE cocineroFinalizacion IS NULL AND tipo=1",null);
        if(c.moveToFirst()){
            do {
                String txtComentario="";
                TextView txtidLinea = new TextView(this);
                txtidLinea.append(c.getInt(0)+"");
                if(c.getString(5)==null){
                }else{
                    txtComentario="("+c.getString(5)+")";
                }
                TextView txtproducto = new TextView(this);
                Cursor c2= db.rawQuery("SELECT * FROM Productos WHERE idProducto="+c.getInt(2),null);
                if(c2.moveToFirst()){
                    do {
                        txtproducto.append(c2.getString(2)+"\n"+txtComentario);
                    }while(c2.moveToNext());
                }
                ImageButton deleteBut = new ImageButton(this);
                deleteBut.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@android:drawable/ic_popup_reminder", null, getPackageName())));
                gridPanel.addView(txtidLinea);
                gridPanel.addView(txtproducto);
                TextView espacio = new TextView(this);
                espacio.append("");
                gridPanel.addView(espacio);
                gridPanel.addView(deleteBut);
                final int finalizar = c.getInt(0);
                final Intent nextScreen = new Intent(this, PCocinaActivity.class);
                deleteBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.execSQL("UPDATE LineaComanda SET cocineroFinalizacion=strftime('%d-%m-%Y %H:%M:%S') WHERE idLinea='" + finalizar + "'");
                        startActivity(nextScreen);
                        finish();
                    }
                });

            }while(c.moveToNext());
        }
    }
}
