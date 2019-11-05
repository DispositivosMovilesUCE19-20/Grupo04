package com.optativa.optativa3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class Registro extends AppCompatActivity {

    private EditText et_usuario, et_nombre, et_apellido, et_email, et_celular, et_password,et_verificarPassword, et_fechaNacimiento;
    private RadioButton et_sexo_hombre, et_sexo_mujer;
    private CheckBox et_matematica, et_fisica, et_quimica, et_ingles, et_lenguaje;
    Button btnSeleccion;
    Switch et_becado;
    final int REQUEST_CODE_GALLERY=999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        et_usuario = (EditText)findViewById(R.id.txt_registro_usuario);
        et_nombre = (EditText)findViewById(R.id.txt_registro_nombre);
        et_apellido = (EditText)findViewById(R.id.txt_registro_apellido);
        et_email = (EditText)findViewById(R.id.txt_registro_email);
        et_celular = (EditText)findViewById(R.id.txt_registro_celular);
        et_password = (EditText)findViewById(R.id.txt_registro_password);
        et_verificarPassword = (EditText)findViewById(R.id.txt_registro_confirmaPassword);
        et_sexo_hombre = (RadioButton)findViewById(R.id.radio_registro_hombre);
        et_sexo_mujer = (RadioButton)findViewById(R.id.radio_registro_mujer);
        et_fechaNacimiento = (EditText)findViewById(R.id.txt_registro_fechaNacimiento);
        et_becado = (Switch)findViewById(R.id.swicht_registro_becado);
        et_matematica = (CheckBox)findViewById(R.id.chk_registro_matematica);
        et_fisica = (CheckBox)findViewById(R.id.chk_registro_fisica);
        et_quimica = (CheckBox)findViewById(R.id.chk_registro_quimica);
        et_ingles = (CheckBox)findViewById(R.id.chk_registro_ingles);
        et_lenguaje = (CheckBox)findViewById(R.id.chk_registro_lenguaje);


        btnSeleccion = (Button)findViewById(R.id.btn_registro_foto);
        btnSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions
                        (Registro.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });

    }

    /////Metodo para guardar usuario
    public void registrarUsuario(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String usuario = et_usuario.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String email= et_email.getText().toString();
        String celular = et_celular.getText().toString();
        String password = validarPassword(et_password, et_verificarPassword);
        String sexo = verSexo(et_sexo_hombre,et_sexo_mujer);
        String fechaNacimiento = et_fechaNacimiento.getText().toString();
        Boolean becado = et_becado.isChecked();
        String asignaturas = asignaturas(et_matematica,et_fisica,et_quimica,et_ingles,et_lenguaje);
        //Toast.makeText(this,"recogio los datos",Toast.LENGTH_SHORT).show();
        if(!usuario.isEmpty()&&!nombre.isEmpty()&&!apellido.isEmpty()&&!email.isEmpty()&&!celular.isEmpty()&&!password.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("usuario",usuario);
            registro.put("nombre",nombre);
            registro.put("apellido",apellido);
            registro.put("email",email);
            registro.put("celular",celular);
            registro.put("password",password);
            registro.put("sexo",sexo);
            registro.put("fechaNacimiento",fechaNacimiento);
            registro.put("becado",becado);
            registro.put("asignaturas",asignaturas);

            BaseDeDatos.insert("usuarios",null,registro);
            BaseDeDatos.close();
            Toast.makeText(this,"Ingreso de usuario correcto",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"NPI",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(),"No tiene permisos para acceder al almacenamiento",Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && data!=null){

            Uri uri = data.getData();
            try {
                InputStream inputStream= getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                btnSeleccion.setBackground(d);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    private String validarPassword(EditText et_password, EditText et_verificarPassword) {
        if(et_password.getText().toString().equals(et_verificarPassword.getText().toString())){
            return et_password.getText().toString();
        }else{
            Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private String asignaturas(CheckBox et_matematica, CheckBox et_fisica, CheckBox et_quimica, CheckBox et_ingles, CheckBox et_lenguaje) {
        String asignaturas ="";
        if(et_matematica.isChecked()==true){
            asignaturas = asignaturas+"Matemática";
        }
        if(et_fisica.isChecked()==true){
            if(asignaturas!=""){
                asignaturas = asignaturas+", ";
            }
            asignaturas=asignaturas+"Física";
        }
        if(et_quimica.isChecked()==true){
            if(asignaturas!=""){
                asignaturas = asignaturas+", ";
            }
            asignaturas=asignaturas+"Quimica";
        }
        if(et_ingles.isChecked()==true){
            if(asignaturas!=""){
                asignaturas = asignaturas+", ";
            }
            asignaturas=asignaturas+"Ingles";
        }
        if(et_lenguaje.isChecked()==true){
            if(asignaturas!=""){
                asignaturas = asignaturas+", ";
            }
            asignaturas=asignaturas+"Lenguaje.";
        }
        return asignaturas;
    }

    private String verSexo(RadioButton et_sexo_hombre, RadioButton et_sexo_mujer) {
        if(et_sexo_hombre.isChecked()==true){
            return "Hombre";
        }else{

            return "Mujer";
        }

    }
}
