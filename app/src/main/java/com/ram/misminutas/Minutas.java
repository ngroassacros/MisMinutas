package com.ram.misminutas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.microsoft.windowsazure.mobileservices.*;
import com.ram.misminutas.Clases.Minuta;
import com.ram.misminutas.Clases.Proyecto;
import com.ram.misminutas.Clases.Usuario;
import com.ram.misminutas.DB.DB;

import java.util.Date;
import java.util.List;


public class Minutas extends Activity {

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
        usuario.nombre = "Ramses Santos";
        usuario.email = "ranabdiel";
        usuario.telefono = "12";
        bd.InsertarUsuario(usuario);

        List<Usuario> usuarios = bd.ObtenerUsuarios();
        for (Usuario user : usuarios){
            Log.i("Usuarios: ", user.nombre + " - " + user.id);
        }

        Proyecto proy = new Proyecto();
        proy.Nombre = "Proyecto 1";
        proy.Descripcion = "Este proyecto se trata de XXX";
        proy.UsuarioCreador = 1;
        proy.FechaCreacion = new Date();
        bd.InsertarProyecto(proy);

        List<Proyecto> proyectos = bd.ObtenerProyectos();
        for (Proyecto proyecto : proyectos){
            Log.i("Proyectos: ", proyecto.Nombre + " - " + proyecto.FechaCreacion);
        }

        Minuta minuta = new Minuta();
        minuta.Titulo = "Minuta de Estimacion";
        minuta.Cliente = "SK";
        minuta.Fecha = new Date(2010, 2, 20);
        minuta.Lugar = "Terraza";
        minuta.IdUsuario = 1;
        minuta.IdProyecto = 1;
        bd.InsertarMinuta(minuta);

        List<Minuta> minutas = bd.ObtenerMinutas();
        for (Minuta minut : minutas){
            Log.i("Minutas: ", minut.Titulo + " - " + minut.Fecha.getDay() + " - " + minut.Fecha.getMonth() + " - " + minut.Fecha.getYear());
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
