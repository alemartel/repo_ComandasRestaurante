package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

public class AdministracionActivity extends AppCompatActivity {

    ImageButton settings;
    ImageButton botonMenos;
    ImageButton botonMas;
    EditText nMesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion);


        settings = (ImageButton) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                final PopupMenu popup = new PopupMenu(AdministracionActivity.this, settings);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_admin, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.cambiaContra:
//                                Intent cambioPass = new Intent(getApplicationContext(), Contraseña.class);
//                                startActivity(cambioPass);
                                break;
                            case R.id.historial:
//                                Intent verHisto = new Intent(getApplicationContext(), Historial.class);
//                                startActivity(verHisto);
                                break;
                            case R.id.logout:
                                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(logout);
                                break;
                        }

                        return false;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        botonMenos = (ImageButton) findViewById(R.id.botonMenos);
        botonMas = (ImageButton) findViewById(R.id.botonMas);
        nMesas = (EditText) findViewById(R.id.nMesas);

        botonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int menos = Integer.parseInt(nMesas.getText().toString());
                menos = menos-1;
                nMesas.setText(String.valueOf(menos));
            }
        });

        botonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mas = Integer.parseInt(nMesas.getText().toString());
                mas = mas+1;
                nMesas.setText(String.valueOf(mas));
            }
        });

    }


}
