package com.example.cabbike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private boolean Registrado = false;

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


        if(Registrado == true){
            finish();
            return;
        }




    }

    //Este método realiza el registro del usuario a un archivo, usando una lista en lazada
    //y reistrando al usuario en un archivo
    public void registrar(View view){
        AdminSQLiteHelper base_admin = new AdminSQLiteHelper(this);
        SQLiteDatabase BaseDeDatos = base_admin.getWritableDatabase();
        String valor_1 = et1.getText().toString();
        String valor_2 = et2.getText().toString();
        String valor_3 = et3.getText().toString();
        String valor_4 = et4.getText().toString();

        //LinkedListGenericInnerNode<Usuario> lista_usuario = new LinkedListGenericInnerNode<>();
        //Usuario user = new Usuario (valor_1,valor_2,valor_3,valor_4);
        //lista_usuario.insert(user);

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

            Registrado = true;
            Toast.makeText(this,"REGISTRO COMPLETADO",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, PermissionsActivity.class));




        }else{
            Registrado = false;
            Toast.makeText(this,"Debe llenar todos los datos",Toast.LENGTH_SHORT).show();

        }


    }

    //Método para buscar un usuario
    public void Buscar (View view){
        AdminSQLiteHelper base_admin = new AdminSQLiteHelper(this);
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

    /*public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int avaliable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (avaliable == ConnectionResult.SUCCESS){
            //todo está bien y el usuario puede solicitar mapa
            Log.d(TAG, "isServicesOK: Google Pay Services is working");
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(avaliable)){
            //hubo un error pero puede ser solucionado
            Log.d(TAG, "isServicesOK: ha ocurrido un error pero podemos arreglarlo");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, avaliable, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "No es posible la solicitud de mapa", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void init(){
        Button registrar = (Button) findViewById(R.id.regitrar);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Pantalla_mapa.class);
                startActivity(intent);
            }
        });
    }*/

    /* class Usuario implements Serializable {

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

   interface LinearList<T>{
        boolean isEmpty();
        int size();
        T get(int index);
        T remove (int index);
        void add(int index, T element);
        String toString();
   }

   class ChainNode<T>{
        T element;
        ChainNode<T> next;

        ChainNode(){
            this(null, null);
        }

        ChainNode(T element, ChainNode<T> next){
            this.element = element;
            this.next = next;
        }
   }

    class Chain<T> implements LinearList<T>, Iterable<T>{

        protected ChainNode<T> firstNode;
        protected int size;

        public Chain(){
            firstNode = null;
            size = 0;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        public int size(){
            return size;
        }

        void checkIndex(int index){
            if (index < 0 || index >= size){
                throw new IndexOutOfBoundsException("index = " + index " size = "+ size);
            }
        }

        public T get(int index){
            checkIndex(index);

            ChainNode<T> currentNode = firstNode;
            for(int i = 0; i < index; i++)
                currentNode = currentNode.next;
            return currentNode.element;
        }

        public int indexOf (T element){
            ChainNode<T> currentNode = firstNode;
            int index = 0;
            while(currentNode != null && !currentNode.element.equals(element)){
                currentNode = currentNode.next;
                index++;
            }
            if(currentNode == null)
                return -1;
            else
                return index;
        }

        public T remove (int index){
            checkIndex(index);

            T removedElement;
            if(index == 0){
                removedElement = firstNode.element;
                firstNode = firstNode.next;
            }else{
                ChainNode<T> q = firstNode;
                for(int i = 0; i< index - 1; i++)
                  q = q.next;
                removedElement = q.next.element;
                q.next = q.next.next;
            }
            size--;
            return removedElement;
        }

        public void add (int index, T theElement) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException
                        ("index = " + index + "  size = " + size);

            if (index == 0)
                firstNode = new ChainNode<T>(theElement, firstNode);
            else {
                ChainNode<T> p = firstNode;
                for (int i = 0; i < index - 1; i++)
                    p = p.next;
                p.next = new ChainNode<T>(theElement, p.next);
            }
            size++;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("[");

            for (T x : this)
                s.append(Objects.toString(x) + ", ");

            if (size > 0)
                s.setLength(s.length()-2);

            s.append("]");

            return new String(s);
        }

        public Iterator<T> iterator() {
            return new ChainIterator();
        }

        private class ChainIterator implements Iterator<T> {

            private ChainNode<T> nextNode;

            public ChainIterator() {
                nextNode = firstNode;
            }

            public boolean hasNext() {
                return nextNode != null;
            }

            public T next() {
                if (nextNode != null) {
                    T elementToReturn = nextNode.element;
                    nextNode = nextNode.next;
                    return elementToReturn;
                } else
                    throw new NoSuchElementException("No next element");
            }

            public void remove() {
                throw new UnsupportedOperationException
                        ("remove not supported");
            }

        }

    }*/


}
