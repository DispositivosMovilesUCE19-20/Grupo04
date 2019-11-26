package com.example.proyecto_final_1h_g04;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Listar extends AppCompatActivity {
    static String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        fillTable();
        getPeticion();
    }

    //Método para rellenar la Tabla con un arreglo y seleccionar un usuario para mostrar su detalle
    private void fillTable() {

        String matrix[][]=MainActivity.matriz;
        //matrix[] = {"jeffer", "carlos", "tania", "alex", "fabri"};
        int n = matrix.length;
        TableLayout table = findViewById(R.id.tableLayout1);
        table.removeAllViews();
        table.setBackgroundColor(Color.GREEN);
        TableRow rowCab = new TableRow(Listar.this);
        //Cabecera para la tabla dinámica
        rowCab.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        EditText cabeceraNum = new EditText(Listar.this);
        cabeceraNum.setText("N°");
        rowCab.addView(cabeceraNum);
        EditText cabeceraNombre = new EditText(Listar.this);
        cabeceraNombre.setText("NOMBRE");
        rowCab.addView(cabeceraNombre);
        //EditText cabeceraClave = new EditText(Listar.this);
        //cabeceraClave.setText("CLAVE");
        //rowCab.addView(cabeceraClave);
        rowCab.setBackgroundColor(Color.BLUE);
        table.addView(rowCab);
        //Se procede a la asignación del número y del nombre
        for (int i = 0; i < n; i++) {
            TableRow row = new TableRow(Listar.this);
            //Alternación de colores
            if (i%2==0){
                row.setBackgroundColor(Color.YELLOW);
            }
            //row.setBackgroundColor(Color.BLUE);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            EditText editNum = new EditText(Listar.this);
            editNum.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            editNum.setText(i+1+"");
            row.addView(editNum);
            //for (int j = 0; j < 1; j++) {
                EditText edit = new EditText(Listar.this);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
                edit.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                //edit.setText(matrix[i][j]);
                edit.setText(matrix[i][0]);
                final String userAuxiliar=matrix[i][0];
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user=userAuxiliar;
                        Intent i = new Intent(Listar.this, Detalle.class);
                        startActivity(i);
                    }});
                edit.setKeyListener(null);
                row.addView(edit);
            //}
            table.addView(row);
        }
    }

    public void cerrarSesion(View v) {

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE); preferences.edit().clear().commit();
        try {
            JSONArray mJSONArray = new JSONArray();

            for (int i = 0; i < MainActivity.matriz.length; i++) {
                JSONObject jObjd = new JSONObject();
                jObjd.put("usuario", MainActivity.matriz[i][0]);
                jObjd.put("clave", MainActivity.matriz[i][1]);
                jObjd.put("nombres", MainActivity.matriz[i][2]);
                jObjd.put("apellidos", MainActivity.matriz[i][3]);
                jObjd.put("email", MainActivity.matriz[i][4]);
                jObjd.put("celular", MainActivity.matriz[i][5]);
                jObjd.put("genero", MainActivity.matriz[i][7]);
                jObjd.put("fecha_nacimiento", MainActivity.matriz[i][8]);
                jObjd.put("asginaturas", MainActivity.matriz[i][9]);
                jObjd.put("becado", MainActivity.matriz[i][10]);
                mJSONArray.put(jObjd);

                System.out.println(mJSONArray.toString());

                String link = "http://10.119.17.144:9998/upl/" + mJSONArray.toString();
                System.out.println(link);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                URL url = null;
                HttpURLConnection conexion;

                url = new URL(link);
                conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");

                conexion.connect();

                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent principal = new Intent(this, MainActivity.class);
        startActivity(principal);
    }

    @SuppressLint("NewApi")
    public void cerrarAplicacion(View v) {
       finishAffinity();
    }

    public void irOpciones(View view){
    Intent validacionLogin = new Intent(this, Opciones.class);
    startActivity(validacionLogin);
}
    public void getPeticion(){
        TextView msgGrupo = findViewById(R.id.txtMsgMain);
        MainActivity.getMensaje(msgGrupo,"G4T7");
    }
}
