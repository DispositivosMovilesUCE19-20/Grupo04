package com.example.proyecto_final_1h_g04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Editar extends AppCompatActivity {
    String archivo = "archivo_datos";
    String carpeta = "/Download/Archivos_OP3/";
    String contenido;
    File file;
    String file_path = "";
    String name = "";
    static int dimx;
    static int dimy;
    String matrizAux[][];
    String usuarioo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("creadoooedit");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        //Se encarga de realizar la petición del mensaje de servicio
        getPeticion();

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toma la matriz
        MainActivity.matriz = leerToArray();
        matrizAux = MainActivity.matriz;
        for (int i = 0; i < matrizAux.length; i++) {
            System.out.println("elem " + matrizAux[i][0]);
        }

        usuarioo = Listar.user;
        System.out.println("usaas" + usuarioo);
    }

    public void editarUsuario(View view) {
        System.out.println("creatoUsuarioEdit");
        boolean valida = true;
        String usu[][] = new String[1][matrizAux[0].length];
        String datos[][];
        datos = new String[matrizAux.length][matrizAux[0].length];

        EditText usuario = findViewById(R.id.txt_registro_usuarioE);
        EditText pass = findViewById(R.id.txt_registro_passwordE);
        EditText passcon = findViewById(R.id.txt_registro_confirmaPasswordE);
        EditText nombre = findViewById(R.id.txt_registro_nombreE);
        EditText apellido = findViewById(R.id.txt_registro_apellidoE);
        EditText email = findViewById(R.id.txt_registro_emailE);
        EditText celular = findViewById(R.id.txt_registro_celularE);
        String sexo = obtenerSexo();
        EditText fecha = findViewById(R.id.txt_registro_fechaNacimientoE);
        String asignaturas = obtenerAsignatura();
        String beca = obtenerBeca();

        usu[0][0] = usuario.getText().toString();
        usu[0][1] = pass.getText().toString();
        usu[0][2] = nombre.getText().toString();
        usu[0][3] = apellido.getText().toString();
        usu[0][4] = email.getText().toString();
        usu[0][5] = celular.getText().toString();
        usu[0][6] = "FOTO EDITADA";
        usu[0][7] = sexo;
        usu[0][8] = fecha.getText().toString();
        usu[0][9] = asignaturas;
        usu[0][10] = beca;

        //Comprueba si campo el usuario no está vacío
        if (usuario.getText().length() == 0) {
            valida = false;
            String msg = "Usuario vacio";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        //Comprueba si los campos de contraseñas coinciden
        if (!pass.getText().toString().equals(passcon.getText().toString())) {
            valida = false;
            String msg = "Las contraseñas no coinciden";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        int ind = 0;
        for (int i = 0; i < matrizAux.length; i++) {
            if (usuarioo.equals(matrizAux[i][0])) {
                ind = i;
            }
        }

        for (int i = 0; i < matrizAux[0].length; i++) {
            matrizAux[ind][i] = usu[0][i];
        }


        for (int i = 0; i < matrizAux.length; i++) {
            System.out.println("indice: " + matrizAux[i][0]);
        }

        datos = matrizAux;
        if (valida == true) {

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
            MainActivity.matriz = datos;

            Toast.makeText(this, Detalle.getServicio("editar"), Toast.LENGTH_SHORT).show();

            Intent listar = new Intent(this, Listar.class);
            startActivity(listar);
        }
    }


    String obtenerSexo() {
        String resultado = "";
        RadioButton rh = findViewById(R.id.radio_registro_hombreE);
        RadioButton rm = findViewById(R.id.radio_registro_mujerE);

        if (rh.isChecked() == true) {
            resultado += "masculino";
        } else {
            resultado += "femenino";
        }
        return resultado;
    }

    String obtenerAsignatura() {
        String resultado = "";
        CheckBox c1 = findViewById(R.id.chk_registro_fisicaE);
        CheckBox c2 = findViewById(R.id.chk_registro_inglesE);
        CheckBox c3 = findViewById(R.id.chk_registro_lenguajeE);
        CheckBox c4 = findViewById(R.id.chk_registro_matematicaE);
        CheckBox c5 = findViewById(R.id.chk_registro_quimicaE);

        if (c1.isChecked() == true) {
            resultado += "Física ";
        }
        if (c2.isChecked() == true) {
            resultado += "Inglés ";
        }
        if (c3.isChecked() == true) {
            resultado += "Lenguaje ";
        }
        if (c4.isChecked() == true) {
            resultado += "Matemática ";
        }
        if (c5.isChecked() == true) {
            resultado += "Química ";
        }
        return resultado;
    }

    String obtenerBeca() {
        String resultado = "";
        Switch sw = findViewById(R.id.swicht_registro_becadoE);
        if (sw.isChecked() == true) {
            resultado += "Sí";
        } else {
            resultado += "No";
        }
        return resultado;
    }

    public void getPeticion() {
        TextView msgGrupo = findViewById(R.id.txtMsgMain);
        MainActivity.getMensaje(msgGrupo, "G4T7");
    }

    //Se encarga de calcular la dimensión desde el archivo para generar la matriz
    public void dim() {
        //int a[]=new int[2];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader archivo = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(archivo);
        int i = 0;
        int j = 0;
        int ascii;
        try {
            while ((ascii = br.read()) != -1) {
                if (ascii == 10) {
                    i++;
                    j = 0;
                } else {
                    if (ascii == 44) {
                        j++;
                    } else {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dimx = i;
        dimy = j;
        System.out.println("dimx" + i);
        System.out.println("dimy" + j);
    }

    //Lee el archivo y lo convierte a un arreglo
    public String[][] leerToArray() {
        //public List<Usuario> leerToArray() {
        dim();
        String matrix[][] = new String[dimx][dimy];
        //List<Usuario> listaUsers;
        // EditText text=findViewById(R.id.txt_entrada_usuario);
        String contenido = "";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader archivo = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(archivo);
        int ascii;
        try {
            int i = -1;
            int j = 0;
            while ((ascii = br.read()) != -1) {
                char caracter = (char) ascii;
                if (ascii == 10) {
                    i++;
                    j = 0;
                } else {
                    if (ascii == 44) {
                        System.out.println("azxzx" + matrix.length + "mmm" + matrix[0].length);
                        matrix[i][j] = contenido;
                        contenido = "";
                        j++;
                    } else {
                        contenido += caracter;
                    }
                }
            }
            // text.setText(contenido);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matrix;
    }

}
