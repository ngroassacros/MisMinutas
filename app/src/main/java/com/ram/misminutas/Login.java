package com.ram.misminutas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;
import com.ram.misminutas.Clases.Usuario;
import com.ram.misminutas.DB.DB;

import java.net.MalformedURLException;
import java.util.List;


public class Login extends ActionBarActivity {

    private EditText  username=null;
    private EditText  password=null;
    private ProgressBar mProgressBar;
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);

        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);

        mProgressBar.setVisibility(ProgressBar.GONE);

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

        DB db = new DB(getBaseContext());

        Usuario usuario = db.ObtenerUsuario(username.getText().toString(), password.getText().toString());
        db.close();
        if(usuario == null) {
            Log.i("OK","Debe de loguearse por internet");
            if(ConectadoWifi()){
                Log.i("WIFI", "Conectado OK");
                if(ValidarUsuarioAzure(username.getText().toString(), password.getText().toString())){
                    Log.i("","Si existe el usuario en internet.");
                }
            }
            else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage(R.string.MensajeLoginWifi)
                     .setTitle(R.string.TituloAlerta)
                     .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert.show();
            }
            //startActivity(proyecto);
        }
        else{
            //startActivity(proyecto);
            Log.i("OK","Devolvio un usuario " + usuario);
        }
    }

    public Boolean ConectadoWifi(){
        try {
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
        }
        catch (Exception e){
            Log.e("ErrorConectadoWifi", e.getMessage());
        }
        return false;
    }

    public boolean ValidarUsuarioAzure(final String user, final String pass){

        try {
            mClient = new MobileServiceClient("https://minuta2.azure-mobile.net/", "jcfUfjOKHoffDgshVokQhvMQiVaBWY98", this)
                        .withFilter(new ProgressFilter());

            MobileServiceTable tbUsuario = mClient.getTable(Usuario.class);

//            Usuario usert = new Usuario();
//            usert.pass = "demo";
//            usert.nombre = "Ramses";
//            usert.email = "demo";
//            tbUsuario.insert(usert, new TableOperationCallback() {
//                @Override
//                public void onCompleted(Object o, Exception e, ServiceFilterResponse serviceFilterResponse) {
//                    if(e == null){
//                        Log.i("OK", "InsertadoOK");
//                    }
//                    else
//                    {
//                        Log.e("Error", e.getMessage());
//                    }
//                }
//            });

            tbUsuario.execute(new TableQueryCallback<Usuario>() {
                @Override
                public void onCompleted(List<Usuario> list, int i, Exception e, ServiceFilterResponse serviceFilterResponse) {
                    if (e == null) {
                        for (Usuario item : list) {
                            if(item.email.equals(user) && item.pass.equals(pass)) {
                                Log.i("Usuarios", item.nombre);
                                DB db = new DB(getBaseContext());
                                db.InsertarUsuario(item);
                                db.close();
                                break;
                            }
                        }
                    }
                    else
                    {
                        Log.e("Error", e.getMessage());
                    }
                }
            });
        }
        catch (MalformedURLException me) {
            Log.e("UrlMalformed", me.getMessage());
        }
        catch (Exception e){
            Log.e("ErrorValidarUsuarioAzure", e.getMessage());
        }
//        }
// new TableJsonQueryCallback() {
//            public void onCompleted(JsonElement result, int count, Exception exception, ServiceFilterResponse response) {
////                if (exception == null) {
////                    JsonArray results = result.getAsJsonArray();
////                    for (JsonElement item : results) {
////                        Log.i("TAG", "Read object with ID " + item.getAsJsonObject().getAsJsonPrimitive("id").getAsInt());
////                    }
////                }
//            }
        return false;
    }

    private class ProgressFilter implements ServiceFilter {

        @Override
        public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,
                                  final ServiceFilterResponseCallback responseCallback) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {

                @Override
                public void onResponse(ServiceFilterResponse response, Exception exception) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    if (responseCallback != null)  responseCallback.onResponse(response, exception);
                }
            });
        }
    }
}
