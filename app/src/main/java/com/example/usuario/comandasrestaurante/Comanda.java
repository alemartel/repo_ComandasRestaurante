package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Comanda extends AppCompatActivity implements View.OnClickListener {

    Button butAbrirCarta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);



        butAbrirCarta = (Button) findViewById(R.id.butAbrirCarta);
        butAbrirCarta.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent nextScreen = new Intent(getApplicationContext(), CartaActivity.class);
        startActivity(nextScreen);
    }

}
