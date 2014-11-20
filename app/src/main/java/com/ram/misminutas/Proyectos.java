package com.ram.misminutas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.ram.misminutas.Clases.Proyecto;
import com.ram.misminutas.Clases.Usuario;
import com.ram.misminutas.DB.DB;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Proyectos extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private MobileServiceClient clienteServicio;
    private Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);

        usuario = new Usuario();
        usuario.Id = getIntent().getIntExtra("Id",0);
        usuario.Nombre = getIntent().getStringExtra("Nombre");
        usuario.Email = getIntent().getStringExtra("Email");

        Recargar();

        TextView txtNombre = (TextView) findViewById(android.R.id.text1);
        txtNombre.setText(usuario.Nombre.toString());

        ImageView btnNuevoProyecto = (ImageView) findViewById(android.R.id.button1);
        btnNuevoProyecto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CrearProyecto();
            }
        });

        ImageView btnPerfil = (ImageView) findViewById(android.R.id.button2);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =  new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (data != null) {
                Uri selectedImage = data.getData();
                InputStream is;
                try {
                    is = getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    ImageView iv = (ImageView)findViewById(android.R.id.button2);
                    iv.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 150, 150, true));
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error en la Uri de la imagen",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error al cargar la imagen",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Recargar();
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position,
                            long ID) {
        String nombreProyecto = (String)adapter.getItemAtPosition(position);
        Intent crearProyecto =  new Intent(Proyectos.this, ProyectoSeleccionado.class);
        crearProyecto.putExtra("Id",usuario.Id);
        crearProyecto.putExtra("Nombre",usuario.Nombre);
        crearProyecto.putExtra("Email",usuario.Email);
        crearProyecto.putExtra("NombreProyecto",nombreProyecto);

        startActivity(crearProyecto);
    }

    public void Recargar(){
        DB bd = new DB(getBaseContext());

        List<Proyecto> proyectos = bd.ObtenerProyectos();
        List<Proyecto> proyectosCreados = new ArrayList<Proyecto>();
        List<Proyecto> proyectosInvitado = new ArrayList<Proyecto>();
        for (Proyecto proyecto : proyectos){
            if (proyecto.UsuarioCreador == usuario.Id) {
                proyectosCreados.add(proyecto);
            }else{
                proyectosInvitado.add(proyecto);
            }
        }
        String[] listaProyectos = new String[proyectosCreados.size()];
        String[] listaInvitado = new String[proyectosInvitado.size()];

        int p = 0,i = 0;
        for (Proyecto proyecto : proyectosCreados){
            listaProyectos[p] = String.valueOf(proyecto.Id) + ".- " + proyecto.Nombre;
            p++;
        }

        for (Proyecto proyecto : proyectosInvitado){
            listaInvitado[i] = String.valueOf(proyecto.Id) + ".- " + proyecto.Nombre;
            i++;
        }

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        final ArrayAdapter<String> adaptadorProyecto = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listaProyectos);
        final ArrayAdapter<String> adaptadorInvitado = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listaInvitado);

        if(tabHost != null) {
            tabHost.setup();
            tabHost.clearAllTabs();
            TabHost.TabSpec specProyectos = tabHost.newTabSpec("Proyectos");
            specProyectos.setIndicator("Proyectos", getResources().getDrawable(R.drawable.ic_launcher));
            specProyectos.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    ListView list = new ListView(Proyectos.this);
                    list.setAdapter(adaptadorProyecto);
                    return list;
                }
            });
            tabHost.addTab(specProyectos);

            TabHost.TabSpec specInvitado = tabHost.newTabSpec("Invitado");
            specInvitado.setIndicator("Invitado", getResources().getDrawable(R.drawable.ic_launcher));
            specInvitado.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    ListView list = new ListView(Proyectos.this);
                    //list.setBackgroundColor(Color.LTGRAY);
                    list.setAdapter(adaptadorInvitado);
                    return list;
                }
            });

            tabHost.addTab(specInvitado);
        }
    }

    public void CrearProyecto(){
        Intent crearProyecto =  new Intent(Proyectos.this, NuevoProyecto.class);
        crearProyecto.putExtra("Id",usuario.Id);
        crearProyecto.putExtra("Nombre",usuario.Nombre);
        crearProyecto.putExtra("Email",usuario.Email);
        startActivity(crearProyecto);
    }
}
