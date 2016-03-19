package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministracionActivity extends AppCompatActivity {

    ImageButton settings;
    ImageButton botonMenos;
    ImageButton botonMas;
    EditText nMesas;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Categorías");

        // Adding child data
        List<String> categorias = new ArrayList<String>();
        categorias.add("Prueba1");
        categorias.add("Prueba2");
        categorias.add("Prueba");
        categorias.add("Prueba4");
        categorias.add("Prueba5");
        categorias.add("Prueba6");
        categorias.add("Prueba7");


        listDataChild.put(listDataHeader.get(0), categorias); // Header, Child data
    }


}
