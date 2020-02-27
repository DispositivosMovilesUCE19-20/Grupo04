package com.example.proyecto_final_1h_g04;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Opciones extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        getPeticion();
    }

    public void getPeticion(){
        TextView msgGrupo = findViewById(R.id.txtMsgMain);
        MainActivity.getMensaje(msgGrupo,"G4T7");
    }
}
