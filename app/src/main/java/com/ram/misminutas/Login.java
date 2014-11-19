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

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.ram.misminutas.Clases.Minuta;
import com.ram.misminutas.Clases.Usuario;
import com.ram.misminutas.DB.DB;

import java.net.MalformedURLException;
import java.util.Date;


public class Login extends ActionBarActivity {

    private EditText  username=null;
    private EditText  password=null;

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);
//
//        try {
//            mClient = new MobileServiceClient("https://minuta2.azure-mobile.net/", "jcfUfjOKHoffDgshVokQhvMQiVaBWY98", this);
//
//            Minuta minuta = new Minuta();
//            minuta.Titulo = "Minuta de Estimacion";
//            minuta.Cliente = "SK";
//            minuta.Fecha = new Date(2010, 2, 20);
//            minuta.Lugar = "Terraza";
//            minuta.IdUsuario = 1;
//            minuta.IdProyecto = 1;
//
//            mClient.getTable(Minuta.class).insert(minuta, new TableOperationCallback<Minuta>() {
//                public void onCompleted(Minuta entity, Exception exception, ServiceFilterResponse response) {
//                    if (exception == null) {
//                        // Insert succeeded
//                        Log.i("OK"," - " + entity.Cliente);
//                    } else {
//                        // Insert failed
//                        Log.i("NOOK",exception.getMessage());
//                    }
//                }
//            });

//            Item item = new Item();
//            item.Text = "Elemento bonito";
//            mClient.getTable(Item.class).insert(item, new TableOperationCallback<Item>() {
//                public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
//                    if (exception == null) {
//                        // Insert succeeded
//                        Log.i("OK", " - " + entity.Text);
//                    } else {
//                        // Insert failed
//                        Log.i("NOOK",exception.getMessage());
//                    }
//                }
//            });

//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

    }

    public class Item {
        public String Id;
        public String Text;
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

        if (username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), "Redirecting...",
                    Toast.LENGTH_SHORT).show();
            Intent proyecto =  new Intent(Login.this, Minutas.class);
            proyecto.putExtra("NombreUsuario", "Ramses Abdiel Santos Beltran");
            proyecto.putExtra("Proyecto", "Proyecto1");

            DB db = new DB(getBaseContext());
            db.InicializarBaseDeDatos();
            Usuario usuario = db.ObtenerUsuario(username.getText().toString(), password.getText().toString());
            db.close();
            if(usuario == null) {
                Log.i("OK","Debe de loguearse por internet");
                Toast.makeText(this, "Debe loguerse por internet",Toast.LENGTH_LONG);
                //startActivity(proyecto);
            }
            else{
                //startActivity(proyecto);
                Log.i("OK","Devolvio un usuario " + usuario);
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
