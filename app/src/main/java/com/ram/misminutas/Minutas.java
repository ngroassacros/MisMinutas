package com.ram.misminutas;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.*;
import com.ram.misminutas.Clases.Usuarios;

import java.net.MalformedURLException;
import java.util.List;


public class Minutas extends ActionBarActivity {

    private MobileServiceClient clienteServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minutas);

        String nombre = getIntent().getStringExtra("NombreUsuario");
        this.setTitle(nombre);

        try {
            clienteServicio = new MobileServiceClient("https://minuta.azure-mobile.net/", "QrKYYqtZLUXlKEafdAgkwFfvOiINOt32", this);

            //final ListView listaMinutas = (ListView) findViewById(R.id.listaMinutas);

            /*Proyectos proy = new Proyectos();
            proy.Nombre = "ProyectoN";
            proy.Descripcion = "Descripcion Del Proyecto. " + nombre;
            proy.UsuarioCreador = 1;

            clienteServicio.getTable(Proyectos.class).insert(proy, new TableOperationCallback<Proyectos>() {
                public void onCompleted(Proyectos entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        //Toast.makeText(Minutas.class, "OK",Toast.LENGTH_LONG);// Insert succeeded
                        Log.i("OK", exception.getMessage());
                    } else {
                        // Insert failed
                        Log.i("NO OK",exception.getMessage());
                    }
                }
            });*/

            /*Usuarios user = new Usuarios();
            user.Nombre = nombre;
            user.Email = "";
            user.Telefono = 5432;

            clienteServicio.getTable(Usuarios.class).insert(user, new TableOperationCallback<Usuarios>() {
                public void onCompleted(Usuarios entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        //Toast.makeText(Minutas.class, "OK",Toast.LENGTH_LONG);// Insert succeeded
                        Log.i("OK", exception.getMessage());
                    } else {
                        // Insert failed
                        Log.i("NO OK",exception.getMessage());
                    }
                }
            });*/
             MobileServiceTable mToDoTable;


            mToDoTable = clienteServicio.getTable(Usuarios.class);
            /*mToDoTable = clienteServicio.getTable("Usuarios");

            mToDoTable.execute(new TableJsonQueryCallback() {
                @Override
                public void onCompleted(JsonElement jsonElement, int i, Exception e, ServiceFilterResponse serviceFilterResponse) {
                    if (e == null) {
                        Log.i("NO OK",e.getMessage());
                        jsonElement.getAsString().indexOf(0);
                    }
                    else{
                        Log.i("NO OK",e.getMessage());
                    }
                }
            });*/
            mToDoTable.execute(new TableQueryCallback<Usuarios>() {
                @Override
                public void onCompleted(List<Usuarios> usuarioses, int i, Exception e, ServiceFilterResponse serviceFilterResponse) {
                    if (e == null) {
//                        //mAdapter.clear();
//
                        for (Usuarios item : usuarioses) {
                           Log.i("NOMBRE:", item.Nombre);

                        }
//
                    } else {
                        //createAndShowDialog(exception, "Error");
                        Log.i("NO OK",e.getMessage());
                        Log.i("CAUSA: ", String.valueOf(e.getCause()));
                    }
                }
            });
//            mToDoTable.where().field("id").eq(1).execute(new TableQueryCallback<Usuarios>() {
//
//                public void onCompleted(List<Usuarios> result, int count, Exception exception, ServiceFilterResponse response) {
//
//                    if (exception == null) {
//                        //mAdapter.clear();
//
//                        for (Usuarios item : result) {
//                            Log.i("NOMBRE:", item.Nombre);
//                        }
//
//                    } else {
//                        //createAndShowDialog(exception, "Error");
//                        Log.i("NO OK",exception.getMessage());
//                    }
//                }
//            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
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
