package com.example.proyecto_final_1h_g04;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Detalle extends AppCompatActivity {

    String archivo = "archivo_datos";
    String carpeta = "/Download/Archivos_OP3/";
    File file;
    String file_path = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.file_path = (Environment.getExternalStorageDirectory() + this.carpeta);
        File localFile = new File(this.file_path);
        //Toast.makeText(this,""+ file_path, Toast.LENGTH_SHORT).show();
        if (!localFile.exists()) {
            localFile.mkdir();
        }
        this.name = (this.archivo + ".txt");
        this.file = new File(localFile, this.name);
        try {
            this.file.createNewFile();
            // Toast.makeText(this,"Se creo archivo 2", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String matrix[][] = MainActivity.matriz;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        String usuario = Listar.user;
        System.out.println("clap: " + usuario);
        String usu[] = new String[1];
        System.out.println("dimension: " + usu.length);
        for (int i = 0; i < MainActivity.matriz.length; i++) {
            if (usuario.equals(matrix[i][0])) {
                usu = matrix[i];
            }
        }

        TextView txt_user = findViewById(R.id.txt_detalle_usuario);
        txt_user.setText(usu[0]);
        //PASSWORD
        TextView txt_pass = findViewById(R.id.txt_detalle_nombre);
        txt_pass.setText(usu[2]);
        TextView txt_ape = findViewById(R.id.txt_detalle_apellido);
        txt_ape.setText(usu[3]);
        TextView txt_email = findViewById(R.id.txt_detalle_email);
        txt_email.setText(usu[4]);
        TextView txt_celu = findViewById(R.id.txt_detalle_celular);
        txt_celu.setText(usu[5]);
        //FOTO
        TextView txt_sexo = findViewById(R.id.txt_detalle_sexo);
        txt_sexo.setText(usu[7]);
        TextView txt_fechaN = findViewById(R.id.txt_detalle_fechaNacimiento);
        txt_fechaN.setText(usu[8]);
        TextView txt_asign = findViewById(R.id.txt_detalle_asignaturas);
        txt_asign.setText(usu[9]);
        TextView txt_beca = findViewById(R.id.txt_detalle_becado);
        txt_beca.setText(usu[10]);
        getPeticion();

    }

    public void getPeticion() {
        TextView msgGrupo = findViewById(R.id.txtMsgMain);
        MainActivity.getMensaje(msgGrupo, "G4T7");
    }

    public void regresar(View v) {
        Intent listar = new Intent(this, Listar.class);
        startActivity(listar);

    }

    public void eliminar(View view) {
        String matrix[][] = MainActivity.matriz;
        String usuario = Listar.user;
        String datos[][] = new String[matrix.length - 1][matrix[0].length];
        for (int i = 0, j = 0; i < matrix.length; i++) {
            if (usuario.equals(matrix[i][0])) {
            } else {
                datos[j] = matrix[i];
                j++;
            }
        }
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(this.file);
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

        MainActivity.matriz = datos;

        Toast.makeText(this, getServicio("eliminar"), Toast.LENGTH_SHORT).show();

        Intent listar = new Intent(this, Listar.class);
        startActivity(listar);

    }

    public static String getServicio(String servicio) {
        String mensaje = "";
        // String link = "https://optativa3-g4-t7.herokuapp.com/G4T7";
        String link = "https://optativa3-g4-t7.herokuapp.com/operacion/" + servicio;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conexion;

        try {
            url = new URL(link);
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            json = response.toString();
            JSONObject jsonMsg = new JSONObject(json);
            mensaje = jsonMsg.optString("msg");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensaje;
    }

    public void vistaEditar(View view) {
        Intent listar = new Intent(this, Editar.class);
        startActivity(listar);
    }
}





