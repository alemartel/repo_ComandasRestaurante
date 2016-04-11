package com.example.usuario.comandasrestaurante;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministracionActivity extends AppCompatActivity {


    ImageButton settings, botonMenos, botonMas, butAñadirCategoría, butEliminarCategoría, butAñadirProducto, butEliminarProducto;
    Button cambiaNumMesas;
    EditText nMesas, txtCategoría, txtProducto, txtPrecio;
    Spinner spinnerCategoría, spinnerCategoría2, spinnerProducto;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion);

        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        final SQLiteDatabase db = database.getWritableDatabase();

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
                                Intent nextScreen = new Intent(getApplicationContext(), cambiar_adminpass.class);
                                startActivity(nextScreen);
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
        cambiaNumMesas = (Button) findViewById(R.id.buttonOk);

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

        cambiaNumMesas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int numMesas = Integer.parseInt(nMesas.getText().toString());
                db.execSQL("UPDATE AdminPanel SET nMesas='" + numMesas + "' WHERE idAdmin='admin'");
                db.execSQL("DROP TABLE Mesas");
                db.execSQL("CREATE TABLE Mesas (idMesa INTEGER PRIMARY KEY AUTOINCREMENT, nombreMesa TEXT)");
                for (int i = 0; i < numMesas; i++) {
                    db.execSQL("INSERT INTO Mesas (nombreMesa) " +
                            "VALUES ('Mesa " + i+1 + "')");
                }
                Toast.makeText(getApplicationContext(), "Numero de mesas modificado" , Toast.LENGTH_LONG).show();
            }
        });

        butAñadirCategoría = (ImageButton) findViewById(R.id.butAñadirCategoría);
        txtCategoría = (EditText) findViewById(R.id.txtCategoría);
        butEliminarCategoría = (ImageButton) findViewById(R.id.butEliminarCategoría);
        spinnerCategoría = (Spinner) findViewById(R.id.spinnerCategorías);

        butAñadirCategoría.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("INSERT INTO Categorias (nombre) " + "VALUES ('"+txtCategoría.getText()+"')");
                getCategorias();
                Toast.makeText(getApplicationContext(), "Categoría '"+txtCategoría.getText()+"' añadida" , Toast.LENGTH_LONG).show();
                txtCategoría.setText("");
            }
        });

        butEliminarCategoría.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textspinnerCategoría = spinnerCategoría.getSelectedItem().toString();
                String[] partstextspinnerCategoría = textspinnerCategoría.split("-");
                db.execSQL("DELETE FROM Categorias WHERE idCategoria = "+partstextspinnerCategoría[0]);
                getCategorias();
                Toast.makeText(getApplicationContext(), "Categoría '"+partstextspinnerCategoría[1]+"' eliminada" , Toast.LENGTH_LONG).show();
            }
        });

        butAñadirProducto = (ImageButton) findViewById(R.id.butAñadirProducto);
        butEliminarProducto = (ImageButton) findViewById(R.id.butEliminarProducto);
        txtProducto = (EditText) findViewById(R.id.txtProducto);
        spinnerCategoría2 = (Spinner) findViewById(R.id.spinnerCategorías2);
        txtPrecio = (EditText) findViewById(R.id.txtPrecio);
        spinnerProducto = (Spinner) findViewById(R.id.spinnerProductos);

        butAñadirProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textspinnerCategoría2 = spinnerCategoría2.getSelectedItem().toString();
                String[] partstextspinnerCategoría2 = textspinnerCategoría2.split("-");
                db.execSQL("INSERT INTO Productos (idCategoria, nombre, precio) " + "VALUES ("+partstextspinnerCategoría2[0]+",'"+txtProducto.getText()+"',"+txtPrecio.getText()+")");
                getProductos();
                Toast.makeText(getApplicationContext(), "Producto '"+txtProducto.getText()+"' añadido" , Toast.LENGTH_LONG).show();
                txtProducto.setText("");
                txtPrecio.setText("");
            }
        });

        butEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textspinnerProducto = spinnerProducto.getSelectedItem().toString();
                String[] partstextspinnerProducto = textspinnerProducto.split("-");
                db.execSQL("DELETE FROM Productos WHERE idProducto = "+partstextspinnerProducto[0]);
                getProductos();
                Toast.makeText(getApplicationContext(), "Producto '"+partstextspinnerProducto[2]+"' eliminado" , Toast.LENGTH_LONG).show();
            }
        });


        getCategorias();
        getProductos();
    }

    private void getCategorias() {
        List<String> categorias =  new ArrayList<String>();
        // Adding data
        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        final SQLiteDatabase db = database.getWritableDatabase();
        categorias.add("Categorías");
        Cursor c = db.rawQuery("SELECT * FROM Categorias", null);
        if(c.moveToFirst()){
            do {
                categorias.add(c.getString(0)+"-"+c.getString(1));
            }while(c.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinnerCategorías);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinnerCategorías2);
        sItems.setAdapter(adapter);
        sItems2.setAdapter(adapter);
    }

    private void getProductos() {
        List<String> productos =  new ArrayList<String>();
        // Adding data
        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        final SQLiteDatabase db = database.getWritableDatabase();
        productos.add("Productos");
        Cursor c = db.rawQuery("SELECT * FROM Productos", null);
        if(c.moveToFirst()){
            do {
                Cursor c2 = db.rawQuery("SELECT * FROM Categorias WHERE idCategoria="+c.getString(1), null);
                if(c2.moveToFirst()){
                    do {
                        productos.add(c.getString(0)+"-"+c2.getString(1)+"-"+c.getString(2));
                    }while(c2.moveToNext());
                }
            }while(c.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinnerProductos);
        sItems.setAdapter(adapter);
    }


}
