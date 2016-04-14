package com.example.usuario.comandasrestaurante;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import javax.microedition.khronos.opengles.GL;

public class CartaActivity extends AppCompatActivity implements View.OnClickListener{
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);

        GridLayout glayout = (GridLayout) findViewById(R.id.gridCarta);
        BaseDeDatos database = new BaseDeDatos(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Categorias", null);
        if(c.moveToFirst()){
            do {
                Button butCategoría = new Button(this);
                butCategoría.setText(c.getString(1));
                butCategoría.setOnClickListener(this);
                glayout.addView(butCategoría);
                ViewGroup.LayoutParams VGlayoutParams= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                GridLayout.LayoutParams GlayoutParams = new GridLayout.LayoutParams(VGlayoutParams);
                butCategoría.setLayoutParams(GlayoutParams);

                Cursor c2 = db.rawQuery("SELECT * FROM Productos WHERE idCategoria="+c.getString(0), null);

                GridLayout gridProductos = new GridLayout(this);
                gridProductos.setColumnCount(3);
                glayout.addView(gridProductos);
                ViewGroup.LayoutParams VGlayoutParams2= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                GridLayout.LayoutParams GlayoutParams2 = new GridLayout.LayoutParams(VGlayoutParams2);
                gridProductos.setLayoutParams(GlayoutParams2);
                gridProductos.setVisibility(View.GONE);

                if(c2.moveToFirst()){
                    do {
                        TextView textView = new TextView(this);
                        textView.append(c2.getString(2));

                        ImageButton imgBut = new ImageButton(this);
                        imgBut.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@android:drawable/ic_input_add", null, getPackageName())));

                        gridProductos.addView(textView);
                        gridProductos.addView(imgBut,new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(2)));



                    }while(c2.moveToNext());
                }
            }while(c.moveToNext());
        }
    }


    @Override
    public void onClick(View v) {
        int flag=0;
        Button viewClicked = (Button) v;
        ViewGroup row = (ViewGroup) v.getParent();
        for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
            View view = row.getChildAt(itemPos);
            if(flag == 1){
                if(view.getVisibility()==View.VISIBLE){
                    view.setVisibility(View.GONE);
                    break;
                }else{
                    view.setVisibility(View.VISIBLE);
                    break;
                }
            }
            if (view instanceof Button) {
                Button view2 = (Button) view;
                if (view2.getText() == viewClicked.getText()) {
                    flag = 1;
                }
            }
        }
    }


}
