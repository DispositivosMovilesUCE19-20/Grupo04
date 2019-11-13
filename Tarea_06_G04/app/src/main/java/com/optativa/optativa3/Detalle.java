package com.optativa.optativa3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Detalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String matrix[][]=MainActivity.matriz;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        String usuario=Listar.user;
        System.out.println("clap: "+ usuario);
        String usu[]=new String[2];

        for (int i=0;i<matrix.length;i++){
            if (usuario.equals(matrix[i][0])){
                usu=matrix[i];
            }
        }

        TextView txt_user=findViewById(R.id.txt_detalle_usuario);
        txt_user.setText(usu[0]);
        TextView txt_pass=findViewById(R.id.txt_detalle_nombre);
        txt_pass.setText(usu[2]);
        TextView txt_ape=findViewById(R.id.txt_detalle_apellido);
        txt_ape.setText(usu[3]);
        TextView txt_naci=findViewById(R.id.txt_detalle_fechaNacimiento);
        txt_naci.setText(usu[5]);
        TextView txt_email=findViewById(R.id.txt_detalle_email);
        txt_email.setText(usu[6]);
        TextView txt_cel=findViewById(R.id.txt_detalle_celular);
        txt_cel.setText(usu[7]);

    }
}
