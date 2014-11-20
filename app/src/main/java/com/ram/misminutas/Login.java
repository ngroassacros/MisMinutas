package com.ram.misminutas;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ram.misminutas.Clases.Usuario;
import com.ram.misminutas.DB.DB;

import java.util.List;


public class Login extends ActionBarActivity {

    private EditText  username=null;
    private EditText  password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);

        DB bd = new DB(getBaseContext());

        //bd.InicializarBaseDeDatos();
        //Usuario usuario = new Usuario();
        //usuario.Nombre = "ADMINISTRADOR";
        //usuario.Email = "admin";

        //bd.InsertarUsuario(usuario);
        //Log.i("Inserto","Usuario");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);

        DB db = new DB(getBaseContext());
        List<Usuario> listaUsuarios = db.ObtenerUsuarios();
        Usuario usuario = null;

        for(Usuario usuarioLista : listaUsuarios){
            if(usuarioLista.Email.toString().equals(username.getText().toString())){
                usuario = usuarioLista;
            }
        }

        if(usuario != null){
            Intent proyecto =  new Intent(Login.this, Proyectos.class);
            proyecto.putExtra("Id",usuario.Id);
            proyecto.putExtra("Nombre",usuario.Nombre);
            proyecto.putExtra("Email",usuario.Email);
            startActivity(proyecto);
        }
        else {
            Toast.makeText(getApplicationContext(), "Usuario Incorrecto",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
