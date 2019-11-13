package com.optativa.optativa3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Listar extends AppCompatActivity {
    static String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        fillTable();
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
  /*  public void cerrar(View view){
        int p = android.os.Process.myPid();
        android.os.Process.killProcess(p);
    }
*/

    public void cerrarSesion(View v) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE); preferences.edit().clear().commit();
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
}}
