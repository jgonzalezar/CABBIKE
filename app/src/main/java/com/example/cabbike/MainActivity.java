package com.example.cabbike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.campo_nombre);
        et2 = (EditText)findViewById(R.id.campo_email);
        et3 = (EditText)findViewById(R.id.campo_cel);
        et4 = (EditText)findViewById(R.id.campo_clave);
        tv1 = (TextView)findViewById(R.id.textView_1);
    }

    //Este método realiza el registro del usuario a un archivo, usando una lista en lazada
    //y reistrando al usuario en un archivo
    public void registrar(View view){
        AdminSQLiteHelper base_admin = new AdminSQLiteHelper(this,"BaseDatos_CABBIKE",null,1);
        SQLiteDatabase BaseDeDatos = base_admin.getWritableDatabase();
        String valor_1 = et1.getText().toString();
        String valor_2 = et2.getText().toString();
        String valor_3 = et3.getText().toString();
        String valor_4 = et4.getText().toString();

        //ListaUsuario lista = new ListaUsuario();
        //Usuario user = new Usuario (valor_1,valor_2,valor_3,valor_4);
        //lista.insertarNodo(user);

        if(!valor_1.isEmpty()&&!valor_2.isEmpty()&&!valor_3.isEmpty()&&!valor_4.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("contraseña", valor_4);
            registro.put("nombre_usuario", valor_1);
            registro.put("email", valor_2);
            registro.put("celular", valor_3);
            BaseDeDatos.insert("usuarios",null, registro);

            BaseDeDatos.close();
            et1.setText("");
            et2.setText("");
            et3.setText("");
            et4.setText("");

            Toast.makeText(this,"REGISTRO COMPLETADO",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,Pantalla1.class);
            startActivity(i);
        }else{
            Toast.makeText(this,"Debe llenar todos los datos",Toast.LENGTH_SHORT).show();
        }

    }

    //Método para buscar un usuario
    public void Buscar (View view){
        AdminSQLiteHelper base_admin = new AdminSQLiteHelper(this,"BaseDatos_CABBIKE",null,1);
        SQLiteDatabase BaseDeDatos = base_admin.getWritableDatabase();

        String clave = et4.getText().toString();

        if(!clave.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select nombre_usuario, email, celular from usuarios where contraseña =" + clave,null);
            if(fila.moveToFirst()){
                et1.setText(fila.getString(0));
                et2.setText(fila.getString(1));
                et3.setText(fila.getString(2));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        }else {
            Toast.makeText(this,"debe introducir la contraseña para buscar", Toast.LENGTH_SHORT).show();
        }
    }
     /*
     class Usuario implements Serializable{

        private String Nombre;
        private String Email;
        private String Celular;
        private String Contraseña;

        public Usuario (String nombre, String email, String celular, String contraseña){
            Nombre = nombre;
            Email = email;
            Celular = celular;
            Contraseña = contraseña;
        }
        public String getNombre() {
            return Nombre;
        }

        public String getEmail() {
            return Email;
        }

        public String getCelular() {
            return Celular;
        }

        public String getContraseña() {
            return Contraseña;
        }

        public void setNombre(String Nombre) {
            this.Nombre = Nombre;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public void setCelular(String Celular) {
            this.Celular = Celular;
        }

        public void setContraseña(String Contraseña) {
            this.Contraseña = Contraseña;
        }

    }

    class ListaUsuario implements Serializable{
        public class Nodo implements Serializable
        {
            Usuario usuario;
            Nodo siguiente;

            public Nodo(Usuario us)
            {
                usuario = us;
                siguiente = null;
            }

            public Usuario getUsuario() {
                return usuario;
            }
            public void setUsuario(Usuario usuario) {
                this.usuario = usuario;
            }
            public Nodo getSiguiente() {
                return siguiente;
            }
            public void setSiguiente(Nodo siguiente) {
                this.siguiente = siguiente;
            }
        }
        Nodo primero;

        public ListaUsuario()
        { primero = null; }

        public ListaUsuario insertarNodo(Usuario us)
        {
            Nodo nuevo = new Nodo(us);
            nuevo.siguiente = primero;
            primero = nuevo;
            return this;
        }
        public void visualizar()
        {
            Nodo temp = primero;
            while( temp != null )
            {
                System.out.println(temp.usuario.getNombre());
                System.out.println(temp.usuario.getEmail());
                System.out.println(temp.usuario.getCelular());
                System.out.println(temp.usuario.getContraseña());
                temp = temp.getSiguiente();
                System.out.println();
            }
        }
    } */

}
