package com.optativa.optativa3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Detalle extends AppCompatActivity {

    String usuario ="";

    private TextView et_usuario, et_nombre, et_apellido, et_email, et_celular, et_fechaNacimiento,et_sexo,et_becado,et_asignaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        et_usuario = (TextView) findViewById(R.id.txt_detalle_usuario);
        et_nombre = (TextView) findViewById(R.id.txt_detalle_nombre);
        et_apellido = (TextView) findViewById(R.id.txt_detalle_apellido);
        et_email= (TextView) findViewById(R.id.txt_detalle_email);
        et_celular = (TextView) findViewById(R.id.txt_detalle_celular);
        et_fechaNacimiento = (TextView) findViewById(R.id.txt_detalle_fechaNacimiento);
        et_sexo = (TextView) findViewById(R.id.txt_detalle_sexo);
        et_becado = (TextView) findViewById(R.id.txt_detalle_becado);
        et_asignaturas = (TextView) findViewById(R.id.txt_detalle_asignaturas);

        recibirUsuario();
        cargarDatos();
    }


    private void recibirUsuario() {
        Bundle b =getIntent().getExtras();
        usuario = b.getString("usuario");
        Toast.makeText(this,usuario,Toast.LENGTH_SHORT).show();
    }

    private void cargarDatos() {
        ArrayList<String> lista = new ArrayList<>();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();
        Cursor listaUsuarios = BaseDeDatabase.rawQuery
                ("Select usuario, nombre, apellido, email, celular, sexo, fechaNacimiento, becado, asignaturas from usuarios where usuario = '"+usuario+"'",null);
        listaUsuarios.moveToFirst();

        et_usuario.setText("Usuario: "+listaUsuarios.getString(0));
        et_nombre.setText("Nombre: "+listaUsuarios.getString(1));
        et_apellido.setText("Apellido: "+listaUsuarios.getString(2));
        et_email.setText("E-mail: "+listaUsuarios.getString(3));
        et_celular.setText("Celular: "+listaUsuarios.getString(4));
        et_sexo.setText("Sexo: "+listaUsuarios.getString(5));
        et_fechaNacimiento.setText("Fecha de Nacimiento: "+listaUsuarios.getString(6));
        et_becado.setText("Becado: "+listaUsuarios.getString(7));
        et_asignaturas.setText("Asignaturas: "+listaUsuarios.getString(8));

    }

    public void vistaListar(View view){
        Intent vl = new Intent(this, Listar.class);
        startActivity(vl);
    }

}
