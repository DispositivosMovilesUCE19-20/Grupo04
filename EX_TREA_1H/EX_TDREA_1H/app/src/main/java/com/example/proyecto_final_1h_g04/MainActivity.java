package com.example.proyecto_final_1h_g04;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String contenido;
    File file;
    EditText texto;

    static int dimx;
    static int dimy;
    static String matriz[][];
    //static List<Usuario> lista;

    //Atributo y posicion
    private String usuario; //1
    private String clave;   //2
    private String nombre;  //3
    private String apellido;  //4
    private String email;  //5
    private String celular;  //6
    private String foto;  //7
    private String genero;  //8
    private String fechaN;  //9
    private String asignaturas;  //10
    private String becado; //11

    //Se encarga de crear el archivo y la carpeta al iniciar la aplicación en caso que no existan
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //escribir=findViewById(R.id.btn_prueba);
        //Se encarga de realizar la petición del mensaje de servicio
        getPeticion();

        crearFiles(".xml");
        crearFiles("x.txt");
        crearFiles(".txt");
        //Realiza el login a la app con el archivo de preferencias compartidas.
        preferencesLogin();
        //Escribe en el archivo con la matriz de datos local
        escribirFile();
        //Se asigna a la matriz local los datos del archivo en caso que existan.
        matriz = leerToArray();


        //String mStringArray[][] = matriz;
        //String s="{\"idSinger\":1,\"firstName\":\"fabs\",\"lastName\":\"escanor\",\"birthDate\":\"2019-11-17\"}";
    }


    void crearFiles(String formato) {
        File file;
        String archivo = "archivo_datos";
        String carpeta = "/Download/Archivos_OP3/";
        String name = "";
        //Establece el directorio de guardado
        String file_path = (Environment.getExternalStorageDirectory() + carpeta);
        File localFile = new File(file_path);
        //Toast.makeText(this,""+ file_path, Toast.LENGTH_SHORT).show();
        if (!localFile.exists()) {
            //Crea el directorio
            localFile.mkdir();
        }
        name = (archivo + formato);
        file = new File(localFile, name);
        try {
            file.createNewFile();
            // Toast.makeText(this,"Se creo archivo 2", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.file = file;
    }


    //static String matriz[][]=new String[dimx][dimy];
    //Método para escribir en el fichero creado.
    public void escribirFile() {
        boolean valida = true;
        String datos[][];
        System.out.println("1213");
        //Crea un usuario por defecto si no existe ninguno, sino no hace nada
        if (dimx == 0) {
            System.out.println("sadasd");
            datos = new String[1][11];


            datos[0][0] = "admin";
            datos[0][1] = "admin1";
            datos[0][2] = "nom";
            datos[0][3] = "apell";
            datos[0][4] = "admin@uce.com";
            datos[0][5] = "09990";
            datos[0][6] = "";
            datos[0][7] = "f";
            datos[0][8] = "2108";
            datos[0][9] = "";
            datos[0][10] = "no";
            FileWriter fichero = null;
            PrintWriter pw = null;
            try {
                fichero = new FileWriter(file);
                pw = new PrintWriter(fichero);
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

            //}
        } else {
        }
        //Guarda la matriz con los datos almacenados en el archivo.
        matriz = leerToArray();
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

    public void validacionLogin(View view) {
        matriz = leerToArray();
        String a[][] = leerToArray();
        EditText eu = findViewById(R.id.txt_entrada_usuario);
        String textU = eu.getText().toString();
        EditText ep = findViewById(R.id.txt_entrada_password);
        String textP = ep.getText().toString();
        String msg = "Credenciales incorrectas";
        boolean t = false;
        for (int i = 0; i < a.length; i++) {
            if ((a[i][0].equals(textU)) && (a[i][1].equals(textP))) {
                t = true;
                msg = "Usuario Aceptado";
            }
        }
        if (t == true) {
            //Intent validacionLogin = new Intent(this, Opciones.class);
            //startActivity(validacionLogin);
            guardarPreferencias();
            Intent listar = new Intent(this, Listar.class);
            startActivity(listar);
        } else {
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        //Lllamada al metodo
        getPeticionLogin();
    }

    //Método usado en validacionLogin para guardar la sesión
    private void guardarPreferencias() {
        SharedPreferences preferences = getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        EditText eu = findViewById(R.id.txt_entrada_usuario);
        String textU = eu.getText().toString();
        EditText ep = findViewById(R.id.txt_entrada_password);
        String textP = ep.getText().toString();
        String usuario = textU;
        String password = textP;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", usuario);
        editor.putString("pass", password);
        editor.commit();
        System.out.println("sasaasas");
    }

    //Método que se ejecuta al inicio para validar si hay un usuario en sesión
    private void preferencesLogin() {
        SharedPreferences preferences = getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        String u = preferences.getString("user", "");
        String p = preferences.getString("pass", "");

        if (u.length() == 0) {
            System.out.println("000");
        } else {
            System.out.println("999");
            matriz = leerToArray();
            String a[][] = leerToArray();
            String textU = u;
            String textP = p;
            String msg = "Credenciales incorrectas";
            boolean t = false;
            for (int i = 0; i < a.length; i++) {
                if ((a[i][0].equals(textU)) && (a[i][1].equals(textP))) {
                    t = true;
                    msg = "Usuario Aceptado";
                }
            }
            if (t == true) {
                //Intent validacionLogin = new Intent(this, Opciones.class);
                //startActivity(validacionLogin);
                Intent listar = new Intent(this, Listar.class);
                startActivity(listar);
            } else {
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }


    //Se ingresa a la ventana de ingresar usuario
    public void vistaRegistrar(View view) {
        Intent vistaRegistrar = new Intent(this, Registro.class);
        startActivity(vistaRegistrar);
    }

    //Método para obetener el mensaje de servicio
    public void getPeticion() {
        TextView msgGrupo = findViewById(R.id.txtMsgMain);
        getMensaje(msgGrupo, "G4T7");
    }

    //Se invoca desde todas las vistas para obtener el mensaje de servicio
    public static void getMensaje(TextView textView, String servicio) {
        TextView msgGrupo = textView;

        // String link = "https://optativa3-g4-t7.herokuapp.com/G4T7";
        String link = "https://optativa3-g4-t7.herokuapp.com/" + servicio;
        //String link = "http://10.118.215.14:9998/G4T7";
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
            String mensaje = jsonMsg.optString("msg");
            msgGrupo.setText(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

    }

    //Metodo que obtiene el mensaje desde el servidor
    public void getPeticionLogin() {
        TextView msgExa = findViewById(R.id.txt_Msg);
        MainActivity.getMensajeLogin(msgExa,"mensajeExamen");
    }

    public static void getMensajeLogin(TextView textView, String servicio) {
        TextView msgExa = textView;

        // String link = "https://optativa3-g4-t7.herokuapp.com/G4T7";
        String link = "https://optativa3-g4-t7.herokuapp.com/" + servicio;
        //String link = "http://10.118.215.14:9998/G4T7";
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
            String mensajeLogin = jsonMsg.optString("msg");
            msgExa.setText(mensajeLogin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
