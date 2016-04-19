package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class Comanda extends AppCompatActivity {

    Button butAbrirCarta;
    TextView text, textLineas;
    String mesa;
    String idMesa;
    int idComanda = 0;
    float pagar = 0;

    Button butPagarComanda;
    EditText precioComanda;

    Button butAñadirComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        mesa=getIntent().getExtras().getString("Mesa");
        text = (TextView) findViewById(R.id.textView);
        text.append(" "+mesa);


        butAbrirCarta = (Button) findViewById(R.id.butAbrirCarta);
        butAbrirCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), CartaActivity.class);
                nextScreen.putExtra("Mesa", mesa);
                startActivity(nextScreen);
            }
        });

        precioComanda = (EditText) findViewById(R.id.precioComanda);
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
                        pagar = pagar + c1.getFloat(4);
                        precioComanda.setText(String.valueOf(pagar));
                    } while (c1.moveToNext());
                }
            }while(c.moveToNext());
        }

        butPagarComanda = (Button) findViewById(R.id.butPagarComanda);
        butPagarComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float time = System.currentTimeMillis();
                db.execSQL("UPDATE Comandas SET horaCierre='" + time + "', precio='" + pagar + "'");
                Toast.makeText(getApplicationContext(), "El cliente debe pagar: " + pagar + " euros" , Toast.LENGTH_LONG).show();
            }
        });



    }

}
