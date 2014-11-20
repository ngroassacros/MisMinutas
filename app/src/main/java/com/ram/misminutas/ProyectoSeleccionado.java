package com.ram.misminutas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.ram.misminutas.Clases.Usuario;

public class ProyectoSeleccionado extends ActionBarActivity {
    private MobileServiceClient clienteServicio;
    private Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectoseleccionado);

        usuario = new Usuario();
        usuario.Id = getIntent().getIntExtra("Id", 0);
        usuario.Nombre = getIntent().getStringExtra("Nombre");
        usuario.Email = getIntent().getStringExtra("Email");

        String proyecto = getIntent().getStringExtra("NombreProyecto");

        TextView txtNombre = (TextView) findViewById(android.R.id.text1);
        txtNombre.setText(usuario.Nombre.toString());

        TextView txtProyecto = (TextView) findViewById(android.R.id.text2);
        txtProyecto.setText(proyecto);
    }
}
