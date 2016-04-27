package com.example.usuario.comandasrestaurante;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.prefs.Preferences;

public class Comanda extends AppCompatActivity {

    Button butAbrirCarta;
    TextView text;
    GridLayout gridLineas;
    String mesa;
    String idMesa;
    int idComanda = 0;
    float pagar = 0;

    Button butPagarComanda;
    EditText precioComanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                finish();
            }
        });

        precioComanda = (EditText) findViewById(R.id.precioComanda);
        gridLineas = (GridLayout) findViewById(R.id.GridProductos);
        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        final SQLiteDatabase db = database.getWritableDatabase();
        String[] ArrayIdmesa = mesa.split(" ");
        idMesa= ArrayIdmesa[1];
        Cursor c= db.rawQuery("SELECT * FROM Comandas WHERE horaCierre IS NULL AND IdMesa="+idMesa,null);
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
                        TextView textProducto= new TextView(this);
                        TextView textPrecio= new TextView(this);
                        textProducto.append(c1.getString(2));
                        textPrecio.append(c1.getString(4)+"€");
                        ImageButton commentBut = new ImageButton(this);
                        commentBut.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@android:drawable/ic_menu_edit", null, getPackageName())));
                        ImageButton deleteBut = new ImageButton(this);
                        deleteBut.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@android:drawable/ic_delete", null, getPackageName())));
                        GridLayout.LayoutParams glp = new GridLayout.LayoutParams();
                        GridLayout.LayoutParams glpr = new GridLayout.LayoutParams();
                        GridLayout.LayoutParams glc = new GridLayout.LayoutParams();
                        glp.setMargins(30, 0, 30, 0); // (left, top, right, bottom);
                        glpr.setMargins(50, 0, 50, 0);
                        glc.setMargins(30, 0, 5, 0);
                        textProducto.setLayoutParams(glp);
                        textPrecio.setLayoutParams(glpr);
                        commentBut.setLayoutParams(glc);
                        gridLineas.addView(textProducto);
                        gridLineas.addView(textPrecio);
                        gridLineas.addView(commentBut);
                        gridLineas.addView(deleteBut);
                        pagar = pagar + c1.getFloat(4);
                        precioComanda.setText(String.valueOf(pagar));

                        final int comentar = c.getInt(0);

                        commentBut.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final int idLinea = comentar;

                                Cursor coment = db.rawQuery("SELECT * FROM LineaComanda WHERE idLinea=" + idLinea, null);

                                //Toast.makeText(getApplicationContext(), "Base de datos: " + idLinea + "'" , Toast.LENGTH_LONG).show();

                                if (coment.moveToFirst()){
                                    if (coment.getString(5) != null){
                                        //Toast.makeText(getApplicationContext(), "Base de datos: " + coment.getString(5) , Toast.LENGTH_LONG).show();

                                        AlertDialog alertDialog = new AlertDialog.Builder(Comanda.this).create();
                                        alertDialog.setTitle("Ya existe un comentario");
                                        alertDialog.setMessage(coment.getString(5));

                                        //alertDialog.setView(this);


                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Borrar",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        db.execSQL("UPDATE LineaComanda SET comentario =" + null + " WHERE idLinea = '" + idLinea + "'");
                                                        dialog.dismiss();
                                            }
                                        });
                                        alertDialog.show();
                                    } else {
                                        onCreateDialog(idLinea);
                                    }

                                }

                            }
                        });

                        final int eliminar = c.getInt(0);
                        final Intent nextScreen = new Intent(this, Comanda.class);
                        deleteBut.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                db.execSQL("DELETE FROM LineaComanda WHERE idLinea='" + eliminar + "'");
                                nextScreen.putExtra("Mesa", mesa);
                                startActivity(nextScreen);
                                finish();
                            }
                        });

                    } while (c1.moveToNext());
                }
            }while(c.moveToNext());
        }

        butPagarComanda = (Button) findViewById(R.id.butPagarComanda);
        butPagarComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Long time = System.currentTimeMillis();
                //Date fecha;
                //db.execSQL("UPDATE Comandas SET horaCierre='" + time + "', precio='" + pagar + "' WHERE idMesa="+idMesa+" AND horaCierre=0");
                db.execSQL("UPDATE Comandas SET horaCierre=strftime('%d-%m-%Y %H:%M:%S'), precio='" + pagar + "' WHERE idMesa="+idMesa+" AND horaCierre IS NULL");
                Toast.makeText(getApplicationContext(), "El cliente debe pagar: "+pagar+" euros", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

    }

    public void onBackPressed(){
        Intent nextScreen = new Intent(this, MainActivity.class);
        startActivity(nextScreen);
        finish();
    }

    public Dialog onCreateDialog(final int idLinea){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle("Añadir comentario");

        builder.setCancelable(true);

        final View view = inflater.inflate(R.layout.dialog, null);

        builder.setView(view)
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Context context = getBaseContext();

                        BaseDeDatos database = new BaseDeDatos(context, "BaseDeDatos", null, 1);
                        SQLiteDatabase db2 = database.getWritableDatabase();

                        EditText textoDialogo = (EditText) view.findViewById(R.id.textoDialogo);
                        String textoLinea = textoDialogo.getText().toString();

                        db2.execSQL("UPDATE LineaComanda SET comentario='" + textoLinea + "' WHERE idLinea='" + idLinea + "'");
                        dialog.dismiss();

                        Toast.makeText(getApplicationContext(), "Comentario añadido" , Toast.LENGTH_LONG).show();
                    }
                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.show();

        return builder.create();
    }

}