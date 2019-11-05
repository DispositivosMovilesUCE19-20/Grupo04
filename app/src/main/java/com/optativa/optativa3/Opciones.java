package com.optativa.optativa3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class Opciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
    }

    public void cerrarSesion(View view){
        Intent cs = new Intent(this, MainActivity.class);
        startActivity(cs);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("New Api")
    public void salir(View view){
       finishAffinity();
    }

    public void vistaListar(View view){
        Intent cs = new Intent(this, Listar.class);
        startActivity(cs);

    }
}
