package com.optativa.optativa3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String archivo="archivo_datos";
    String archivox="archivo_datosx";
    String archivoxh="archivo_datosxh";
    String carpeta ="/Download/Archivos_OP3/";
    String contenido;
    File file;
    String file_path="";
    File filex;
    String file_pathx="";
    File filexh;
    String file_pathxh="";
    EditText texto;
    String name="";
    static int dimx;
    static int dimy;
    static String matriz[][];

    //Se encarga de crear el archivo y la carpeta al iniciar la aplicación en caso que no existan
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
        preferencesLogin();
        //Se asigna a la matriz local los datos del archivo en caso que existan.
        matriz=leerToArray();
        //Escribe en el archivo con la matriz de datos local
        escribirFile();

        this.file_pathx=(Environment.getExternalStorageDirectory()+this.carpeta);

        File localFilex = new File(this.file_pathx);
        //Toast.makeText(this,""+ file_path, Toast.LENGTH_SHORT).show();
        if (!localFilex.exists()){
            localFilex.mkdir();
        }
        this.name = (this.archivox+".xml");
        this.filex=new File(localFilex, this.name);
        try{
            this.filex.createNewFile();
            // Toast.makeText(this,"Se creo archivo 2", Toast.LENGTH_SHORT).show();
        } catch(IOException e){
            e.printStackTrace();
        }


        this.file_pathxh=(Environment.getExternalStorageDirectory()+this.carpeta);
        File localFilexh = new File(this.file_pathxh);
        //Toast.makeText(this,""+ file_path, Toast.LENGTH_SHORT).show();
        if (!localFilexh.exists()){
            localFilexh.mkdir();
        }
        this.name = (this.archivoxh+".txt");
        this.filexh=new File(localFilex, this.name);
        try{
            this.filexh.createNewFile();
            // Toast.makeText(this,"Se creo archivo 2", Toast.LENGTH_SHORT).show();
        } catch(IOException e){
            e.printStackTrace();
        }




    }




    //static String matriz[][]=new String[dimx][dimy];
    //Método para escribir en el fichero creado.
    public void escribirFile() {
        boolean valida = true;
        String datos[][];
        //Crea un usuario por defecto si no existe ninguno, sino no hace nada
        if (dimx == 0) {
            datos = new String[1][8];
            datos[0][0] = "admin";
            datos[0][1] = "admin1";
            datos[0][2] = "nom";
            datos[0][3] = "apell";
            datos[0][4] = "21/08";
            datos[0][6] = "admin1";
            datos[0][5] = "admin@uce.com";
            datos[0][7] = "09990";
                FileWriter fichero = null;
                PrintWriter pw = null;
                try {
                    fichero = new FileWriter(file);
                    pw = new PrintWriter(fichero);
                    // EditText text=findViewById(R.id.txt_entrada_usuario);
                    for (int i = 0; i < datos.length; i++) {
                        pw.println("");
                        for (int j = 0; j < datos[i].length; j++) {
                            pw.print(datos[i][j] + ",");
                        }
                    }
                    pw.flush();
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (null != fichero)
                            fichero.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                //Guarda la matriz con los datos almacenados en el archivo.
                matriz = leerToArray();
            //}
        }else{}
    }

    //Se encarga de calcular la dimensión desde el archivo para generar la matriz
    public void dim(){
        //int a[]=new int[2];
        FileInputStream fis=null;
        try {
            fis=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader archivo = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(archivo);
        int i=0;
        int j=0;
        int ascii;
        try{
            while ((ascii=br.read())!=-1){
                if(ascii==10){
                    i++;
                    j=0;
                }else{
                    if(ascii==44){
                        j++;
                    }
                    else{
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        dimx=i;
        dimy=j;
        System.out.println("dimx"+ i);
        System.out.println("dimy"+ j);
    }



    public void escribir (View view){
        escribirFile();
    }


    public void leer (View view){
        dim();
        leerToArray();
    }

    //Lee el archivo y lo convierte a un arreglo
    public String [][] leerToArray(){
        dim();
        String matrix[][]=new String[dimx][dimy];
       // EditText text=findViewById(R.id.txt_entrada_usuario);
        String contenido="";
        FileInputStream fis=null;
        try {
            fis=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader archivo = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(archivo);
        int ascii;
        try{
            int i=-1;
            int j=0;
            while ((ascii=br.read())!=-1){
                    char caracter=(char)ascii;
                    if(ascii==10){
                        i++;
                        j=0;
                    }else{
                        if(ascii==44){
                            System.out.println("azxzx"+ matrix.length+ "mmm"+matrix[0].length);
                            matrix[i][j]=contenido;
                            contenido="";
                            j++;
                        }
                        else{
                            contenido += caracter;
                        }
                    }
            }
           // text.setText(contenido);
        }catch(Exception e){
            e.printStackTrace();
        }
    return matrix;
    }


    public void prueba(View view){
        int matriz[][]  = {{2},{1},{4},{2},{6},{10}};
       // matrizToFile("asa",matriz);
    }




    public void validacionLogin(View view){
        matriz=leerToArray();
        String a[][]=leerToArray();
        EditText eu = findViewById(R.id.txt_entrada_usuario);
        String textU = eu.getText().toString();
        EditText ep = findViewById(R.id.txt_entrada_password);
        String textP = ep.getText().toString();
        String msg="Credenciales incorrectas";
        boolean t=false;
        for (int i=0;i<a.length;i++) {
            if ((a[i][0].equals(textU))&&(a[i][1].equals(textP))){
                t=true;
                msg="Usuario Aceptado";
            }
        }
        if (t==true){
            //Intent validacionLogin = new Intent(this, Opciones.class);
            //startActivity(validacionLogin);
            guardarPreferencias();
            Intent listar = new Intent(this, Listar.class);
            startActivity(listar);
        }else{
        }
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    private void guardarPreferencias() {
        SharedPreferences preferences=getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        EditText eu = findViewById(R.id.txt_entrada_usuario);
        String textU = eu.getText().toString();
        EditText ep = findViewById(R.id.txt_entrada_password);
        String textP = ep.getText().toString();
        String usuario = textU;
        String password= textP;
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("user",usuario);
        editor.putString("pass",password);
        editor.commit();
        System.out.println("sasaasas");
    }

    private void preferencesLogin() {
        SharedPreferences preferences=getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
            String u=preferences.getString("user","");
            String p=preferences.getString("pass","");

            if (u.length()==0){
                System.out.println("000");
            }else{
                System.out.println("999");
                matriz=leerToArray();
                String a[][]=leerToArray();
                String textU = u;
                String textP = p;
                String msg="Credenciales incorrectas";
                boolean t=false;
                for (int i=0;i<a.length;i++) {
                    if ((a[i][0].equals(textU))&&(a[i][1].equals(textP))){
                        t=true;
                        msg="Usuario Aceptado";
                    }
                }
                if (t==true){
                    //Intent validacionLogin = new Intent(this, Opciones.class);
                    //startActivity(validacionLogin);
                    Intent listar = new Intent(this, Listar.class);
                    startActivity(listar);
                }else{
                }
                Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
            }
    }


    public void vistaRegistrar(View view){
            Intent vistaRegistrar = new Intent(this, Registro.class);
            startActivity(vistaRegistrar);
    }



    @Override
    public void onClick(View v) {

    }
}
