package com.example.usuario.comandasrestaurante;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class cambiar_adminpass extends AppCompatActivity {

    Button butCambiarContraseña;
    EditText txtPassActual, txtPassNueva, txtPassNuevaRepite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_adminpass);

        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        final SQLiteDatabase db = database.getWritableDatabase();

        txtPassActual = (EditText) findViewById(R.id.txtPassActual);
        txtPassNueva = (EditText) findViewById(R.id.txtPassNueva);
        txtPassNuevaRepite = (EditText) findViewById(R.id.txtPassNuevaRepite);
        butCambiarContraseña = (Button) findViewById(R.id.butCambiarContraseña);
        butCambiarContraseña.setOnClickListener(new View.OnClickListener() {
            String ContraseñaActual;
            @Override
            public void onClick(View view) {
                Cursor c = db.rawQuery("SELECT * FROM AdminPanel", null);
                if(c.moveToFirst()){
                    do {
                        ContraseñaActual = c.getString(1);
                    }while(c.moveToNext());
                }
                if(txtPassActual.getText().toString().equals(ContraseñaActual)){
                    if(txtPassNueva.getText().toString().equals(txtPassNuevaRepite.getText().toString())){
                        db.execSQL("UPDATE AdminPanel SET Contraseña = '"+txtPassNueva.getText().toString()+"' WHERE IdAdmin='admin'");
                        Toast.makeText(getApplicationContext(), "Contraseña cambiada correctamente" , Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error: Debe repetir la contraseña nueva correctamente" , Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Error: Contraseña actual incorrecta : "+ ContraseñaActual , Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
