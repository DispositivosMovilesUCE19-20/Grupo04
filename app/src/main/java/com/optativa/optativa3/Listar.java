package com.optativa.optativa3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Listar extends AppCompatActivity {

    ListView lv;
    ArrayList<String>lista;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        lv = (ListView)findViewById(R.id.list_listar_usuarios);
        lista = buscarUsuario();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Listar.this,lista.get(position),Toast.LENGTH_SHORT).show();
                Intent vistaListar = new Intent(Listar.this, Detalle.class);
                vistaListar.putExtra("usuario",lista.get(position));
                startActivity(vistaListar);
            }
        });


    }

    public void buscarUser(View view){

        lv = (ListView)findViewById(R.id.list_listar_usuarios);
        lista = buscarUsuario();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(adapter);
    }

    public ArrayList buscarUsuario(){
        ArrayList<String> lista = new ArrayList<>();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();
        Cursor listaUsuarios = BaseDeDatabase.rawQuery("Select usuario from usuarios",null);
        if(listaUsuarios.moveToFirst()){
            do{
                lista.add(listaUsuarios.getString(0));
            }while(listaUsuarios.moveToNext());
        }else {
            Toast.makeText(this,"No se encontraron registros",Toast.LENGTH_SHORT).show();
        }
        return lista;
    }
}
