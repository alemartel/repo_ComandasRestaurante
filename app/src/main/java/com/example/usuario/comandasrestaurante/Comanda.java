package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Comanda extends AppCompatActivity implements View.OnClickListener {

    Button butAbrirCarta;
    TextView text, textLineas;
    String mesa;
    String idMesa;
    int idComanda=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        mesa=getIntent().getExtras().getString("Mesa");
        text = (TextView) findViewById(R.id.textView);
        text.append(" "+mesa);


        butAbrirCarta = (Button) findViewById(R.id.butAbrirCarta);
        butAbrirCarta.setOnClickListener(this);

        textLineas = (TextView) findViewById(R.id.lineasComandas);
        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        final SQLiteDatabase db = database.getWritableDatabase();
        String[] ArrayIdmesa = mesa.split(" ");
        idMesa= ArrayIdmesa[1];
        Cursor c= db.rawQuery("SELECT * FROM Comandas WHERE horaCierre=0 AND IdMesa="+idMesa,null);
        if(c.moveToFirst()){
            do {
                idComanda= c.getInt(0);
            }while(c.moveToNext());
        }
        c= db.rawQuery("SELECT * FROM LineaComanda WHERE idComanda="+idComanda, null);
        if(c.moveToFirst()){
            do{
                Cursor c1= db.rawQuery("SELECT * FROM Productos WHERE idProducto="+c.getInt(2),null);
                if(c1.moveToFirst()) {
                    do {
                        textLineas.append(c1.getString(2)+"\n");
                    } while (c1.moveToNext());
                }
            }while(c.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        Intent nextScreen = new Intent(this, CartaActivity.class);
        nextScreen.putExtra("Mesa", mesa);
        startActivity(nextScreen);
    }

}
