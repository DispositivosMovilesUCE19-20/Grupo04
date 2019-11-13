package com.optativa.optativa3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Registro extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
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






        matriz=leerToArray();
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

    public void escribirFile() {
        boolean valida = true;
        String datos[][];
        if (dimx == 0) {
            datos=new String[1][8];
            datos[0][0]="admin";
            datos[0][1]="admin1";
            datos[0][2]="nom";
            datos[0][3]="apell";
            datos[0][4]="21/08";
            datos[0][6]="admin1";
            datos[0][5]="admin@uce.com";
            datos[0][7]="09990";
        } else {
            //        String datos1[][]={{"admin","admin"}};
            datos= new String[dimx + 1][dimy];
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    datos[i][j] = matriz[i][j];
                }
            }
            EditText text = findViewById(R.id.txt_registro_usuario);
            EditText pass = findViewById(R.id.txt_registro_password);
            EditText nombre = findViewById(R.id.txt_registro_nombre);
            EditText apellido = findViewById(R.id.txt_registro_apellido);
            EditText fecha = findViewById(R.id.txt_registro_fechaNacimiento);
            EditText passcon = findViewById(R.id.txt_registro_confirmaPassword);
            EditText email = findViewById(R.id.txt_registro_email);
            EditText celular = findViewById(R.id.txt_registro_celular);

            datos[datos.length - 1][0] = text.getText().toString();
            datos[datos.length - 1][1] = pass.getText().toString();
            datos[datos.length - 1][2] = nombre.getText().toString();
            datos[datos.length - 1][3] = apellido.getText().toString();
            datos[datos.length - 1][4] = fecha.getText().toString();
            datos[datos.length - 1][5] = passcon.getText().toString();
            datos[datos.length - 1][6] = email.getText().toString();
            datos[datos.length - 1][7] = celular.getText().toString();
            if (text.getText().length()==0){
                valida=false;
            }
        }
        //String f[][]=leerToArray();
        // String datos[][]={{"jeffer","alex"},{"carlos","albert"},{"tania","rea"}};
        if (valida==true) {
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
            matriz = leerToArray();
        }
        System.out.println(valida);
    }

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
        metodoXML(matriz);
    }
    public void leer (View view){
        dim();
        leerToArray();
    }
    public String [][] leerToArray(){
        dim();
        String matrix[][]=new String[dimx][dimy];
        EditText text=findViewById(R.id.txt_registro_usuario);
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
            text.setText(contenido);
        }catch(Exception e){
            e.printStackTrace();
        }
        return matrix;
    }

    public String metodoXML(String [][] datos){
///////////////////////////////////////////"metodo" para crear un string en foemato xml de una matriz
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        String result = "aa";
        try{
            serializer.setOutput(writer);
            serializer.startDocument("UFT-8",true);
            serializer.startTag("","usuarios");
            for(int i=0;i<datos.length;i++){
                serializer.startTag("","usuario");

                serializer.startTag("","nickname");
                serializer.text(String.valueOf(datos[i][0]));
                serializer.endTag("","nickname");

                serializer.startTag("","pass");
                serializer.text(String.valueOf(datos[i][1]));
                serializer.endTag("","pass");

                serializer.startTag("","nombre");
                serializer.text(String.valueOf(datos[i][2]));
                serializer.endTag("","nombre");

                serializer.startTag("","apellido");
                serializer.text(String.valueOf(datos[i][3]));
                serializer.endTag("","apellido");

                serializer.startTag("","fecha");
                serializer.text(String.valueOf(datos[i][4]));
                serializer.endTag("","fecha");

                serializer.startTag("","email");
                serializer.text(String.valueOf(datos[i][5]));
                serializer.endTag("","email");

                serializer.startTag("","passcon");
                serializer.text(String.valueOf(datos[i][6]));
                serializer.endTag("","passcon");

                serializer.startTag("","celular");
                serializer.text(String.valueOf(datos[i][7]));
                serializer.endTag("","celular");

                serializer.endTag("","usuario");
            }
            serializer.endTag("","usuarios");
            serializer.endDocument();
            result = writer.toString();


        }catch (Exception e){
            e.printStackTrace();
        }

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(filex);
            pw = new PrintWriter(fichero);
            // EditText text=findViewById(R.id.txt_entrada_usuario);

                    pw.print(result);
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

        try {
            fichero = new FileWriter(filexh);
            pw = new PrintWriter(fichero);
            // EditText text=findViewById(R.id.txt_entrada_usuario);

            pw.print(result);
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
        matriz = leerToArray();

        System.out.println("ssssss"+ result);
        return result;
    }

}
