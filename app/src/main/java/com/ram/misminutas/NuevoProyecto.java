package com.ram.misminutas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.ram.misminutas.Clases.Proyecto;
import com.ram.misminutas.Clases.Usuario;
import com.ram.misminutas.DB.DB;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NuevoProyecto extends ActionBarActivity {
    private MobileServiceClient clienteServicio;
    private Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoproyecto);

        usuario = new Usuario();
        usuario.Id = getIntent().getIntExtra("Id",0);
        usuario.Nombre = getIntent().getStringExtra("Nombre");
        usuario.Email = getIntent().getStringExtra("Email");

        Button btnAceptar = (Button)findViewById(android.R.id.button1);
        Button btnCancelar = (Button)findViewById(android.R.id.button2);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearProyecto();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void CrearProyecto(){
        TextView txtProyecto = (TextView)findViewById(android.R.id.text1);
        TextView txtFecha = (TextView)findViewById(android.R.id.text2);
        TextView txtDescripcion = (TextView)findViewById(android.R.id.edit);

        if(txtProyecto.getText().length() > 0) {
            if(txtFecha.getText().length() > 0) {
                if(txtDescripcion.getText().length() > 0) {
                    DB bd = new DB(getBaseContext());
                    DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha;
                    String fechaCadena = txtFecha.getText().toString();

                    try {
                        fecha = formato.parse(fechaCadena);
                        Proyecto proy = new Proyecto();
                        proy.Nombre = txtProyecto.getText().toString();
                        proy.Descripcion = txtDescripcion.getText().toString();
                        proy.UsuarioCreador = usuario.Id;
                        proy.FechaCreacion = fecha;
                        bd.InsertarProyecto(proy);

                        this.finish();
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), "Es necesario ingresar una fecha válida (dd/MM/yyyy)",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Es necesario ingresar una descripcion del proyecto",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Es necesario ingresar la fecha del proyecto",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Es necesario ingresar el nombre del proyecto",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
