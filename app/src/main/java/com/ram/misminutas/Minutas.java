package com.ram.misminutas;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.microsoft.windowsazure.mobileservices.*;
import com.ram.misminutas.Clases.Proyecto;
import com.ram.misminutas.Clases.Usuario;
import com.ram.misminutas.DB.DB;

import java.util.Date;
import java.util.List;


public class Minutas extends ActionBarActivity {

    private MobileServiceClient clienteServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minutas);

        String nombre = getIntent().getStringExtra("NombreUsuario");
        this.setTitle(nombre);

        DB bd = new DB(getBaseContext());

        bd.InicializarBaseDeDatos();

        Usuario usuario = new Usuario();
        usuario.Nombre = "Ramses Santos";
        usuario.Email = "ranabdiel";
        usuario.Telefono = 12;
        bd.InsertarUsuario(usuario);

        List<Usuario> usuarios = bd.ObtenerUsuarios();
        for (Usuario user : usuarios){
            Log.i("Usuarios: ", user.Nombre + " - " + user.Id);
        }

        Proyecto proy = new Proyecto();
        proy.Nombre = "Proyecto 1";
        proy.Descripcion = "Este proyecto se trata de XXX";
        proy.UsuarioCreador = 1;
        proy.FechaCreacion = new Date();
        bd.InsertarProyecto(proy);

        List<Proyecto> proyectos = bd.ObtenerProyectos();
        for (Proyecto proyecto : proyectos){
            Log.i("Proyectos: ", proyecto.Nombre + " - " + proyecto.Id);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_minutas, menu);
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
}
