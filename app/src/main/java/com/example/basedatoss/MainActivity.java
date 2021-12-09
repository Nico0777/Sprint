package com.example.basedatoss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = findViewById(R.id.txt_codigo);
        et_descripcion = findViewById(R.id.txt_descripcion);
        et_precio = findViewById( R.id.txt_precio);
    }
    // Metodo para registrar los productos
    public void registrar (View view){

        AdminSQLite admin = new AdminSQLite(this, "administracion",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            BaseDeDatos.insert("articulos", null, registro);
            BaseDeDatos.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            Toast.makeText(this, "El Registro fue exitoso", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public void buscar (View view){

        AdminSQLite admin = new AdminSQLite(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos =admin.getWritableDatabase();

        int codigo = Integer.parseInt(et_codigo.getText().toString());

        if (codigo!=0) {
            Cursor fila = BaseDeDatos.rawQuery
                    ("select * from articulos where codigo=" + codigo, null);
            if (fila.moveToFirst()) {

                et_descripcion.setText(fila.getString(1));
                et_precio.setText(fila.getString(2));
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        }else {
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_LONG).show();

            }
        }

        //Metodo para Eliminar un producto o un Articulo
        public void eliminar (View view) {

            AdminSQLite admin = new AdminSQLite(this, "administracion", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            int codigo = Integer.parseInt(et_codigo.getText().toString());

            if (codigo!=0){

                int cantidad = BaseDeDatos.delete("articulos", "codigo="+codigo, null);
                BaseDeDatos.close();

                et_codigo.setText("");
                et_descripcion.setText("");
                et_precio.setText("");

                if (cantidad == 1){
                    Toast.makeText(this, "Articulo fue eliminado exitosamente", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(this, "El articulo no existe", Toast.LENGTH_LONG).show();
                }
        }else {
                Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_LONG).show();
            }
    }
        // Metodo para modificar un articulo o producto

    public void modificar (View view){
        AdminSQLite admin = new AdminSQLite (this, "administracion", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            int cantidad = BaseDeDatos.update("articulos", registro, "codigo="+codigo, null);
            BaseDeDatos.close();

            if (cantidad == 1){
                Toast.makeText(this, "Articulo fue modificado correctamente", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(this,"Debes llenar todos los campos", Toast.LENGTH_LONG).show();
            }
        }


    }

}