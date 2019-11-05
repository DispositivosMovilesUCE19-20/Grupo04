package com.optativa.optativa3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button escribir;
    String archivo="archivo_datos";
    String carpeta ="/Archivos_OP3/";
    String contenido;
    File file;
    String file_path="";
    EditText texto;
    String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //escribir=findViewById(R.id.btn_prueba);
        this.file_path=(Environment.getExternalStorageDirectory()+this.carpeta);
        File localFile = new File(this.file_path);
        //Toast.makeText(this,""+ file_path, Toast.LENGTH_SHORT).show();
        if (!localFile.exists()){
              localFile.mkdir();
        }
        this.name = (this.archivo+".txt");
        this.file=new File(localFile, this.name);
        try{
            this.file.createNewFile();
          // Toast.makeText(this,"Se creo archivo 2", Toast.LENGTH_SHORT).show();
        } catch(IOException e){
            e.printStackTrace();
        }

       //escribir.setOnClickListener(this);
    }

    public void escribir(View v){
        FileWriter fichero=null;
        PrintWriter pw=null;
        try{
            fichero = new FileWriter(file);
            pw= new PrintWriter(fichero);
            EditText text=findViewById(R.id.txt_entrada_usuario);
            pw.println(text.getText().toString());
            pw.flush();
            pw.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (null != fichero)
                    fichero.close();

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    public void validacionLogin(View view){
        EditText et = findViewById(R.id.txt_entrada_usuario);
        String usuario = et.getText().toString();
        EditText et_pass = findViewById(R.id.txt_entrada_password);
        String pass = et_pass.getText().toString();

        boolean flag = buscarUsuario(usuario,pass);

        if(flag){
            Intent validacionLogin = new Intent(this, Opciones.class);
            startActivity(validacionLogin);
            Toast.makeText(this,"Usuario Aceptado", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean buscarUsuario(String usuario, String pass) {
        boolean flag;
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();
            Cursor usuarios = BaseDeDatabase.rawQuery
                    ("Select * from usuarios where usuario = '" + usuario + "' and password = '" + pass+"'", null);
            if (usuarios.moveToFirst()) {
                flag= true;
            } else {
                flag= false;
            }
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
            Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    public void vistaRegistrar(View view){
            Intent vistaRegistrar = new Intent(this, Registro.class);
            startActivity(vistaRegistrar);
    }

    public void vistaListar(View view){
        Intent vistaListar = new Intent(this, Listar.class);
        startActivity(vistaListar);
    }


    @Override
    public void onClick(View v) {

    }
}
