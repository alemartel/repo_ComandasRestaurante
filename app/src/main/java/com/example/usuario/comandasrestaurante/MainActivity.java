package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.PopupMenu;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GridLayout grid;
    ImageButton settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settings = (ImageButton) findViewById(R.id.settings);

        settings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                final PopupMenu popup = new PopupMenu(MainActivity.this, settings);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent nextScreen;
                        switch (item.getItemId()) {
                            case R.id.login:
                                nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(nextScreen);
                                break;
                            case R.id.PCocina:
                                nextScreen = new Intent(getApplicationContext(), PCocinaActivity.class);
                                startActivity(nextScreen);
                                break;
                        }

                        return false;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method


        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db = database.getWritableDatabase();

        GridLayout myLayout = (GridLayout) findViewById(R.id.gridMesas);

        Cursor c = db.rawQuery("SELECT * FROM Mesas", null);
        if(c.moveToFirst()){
            do {
                int nombre=c.getInt(0);
                int idComanda=0;
                ImageView imageView = new ImageView(this);
                Cursor c2= db.rawQuery("SELECT * FROM Comandas WHERE horaCierre IS NULL AND IdMesa="+nombre,null);
                if(c2.moveToFirst()){
                    do {
                        idComanda= c2.getInt(0);
                    }while(c2.moveToNext());
                }
                if(idComanda==0){
                    imageView.setImageResource(R.drawable.mesalibre);
                }else{
                    imageView.setImageResource(R.drawable.mesaocupada);
                }

                TextView textView = new TextView(this);
                textView.append("Mesa " + nombre);
                GridLayout.LayoutParams glptext = new GridLayout.LayoutParams();
                glptext.setMargins(45, 0, 0, 0); // (left, top, right, bottom);
                textView.setLayoutParams(glptext);

                grid = new GridLayout(this);
                grid.setRowCount(2);
                grid.setColumnCount(1);
                grid.addView(textView);
                grid.addView(imageView);
                GridLayout.LayoutParams glpgrid = new GridLayout.LayoutParams();
                glpgrid.setMargins(70, 30, 0, 30); // (left, top, right, bottom);
                grid.setLayoutParams(glpgrid);
                grid.setOnClickListener(this);
                myLayout.addView(grid);

            }while(c.moveToNext());
        }
        db.close();

    }

    @Override
    public void onClick(View v) {
        Intent nextScreen = new Intent(this, Comanda.class);
        ViewGroup row = (ViewGroup) v;
        for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
            View view = row.getChildAt(itemPos);
            if (view instanceof TextView) {
                TextView view2 = (TextView) view;
                nextScreen.putExtra("Mesa", view2.getText().toString());
                startActivity(nextScreen);
                finish();
            }
        }

    }
}
