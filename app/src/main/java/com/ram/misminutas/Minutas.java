package com.ram.misminutas;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.microsoft.windowsazure.mobileservices.*;

import java.net.MalformedURLException;


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

            //mAuthService = myApp.getAuthService();
            clienteServicio.login("ZjiPzvGEnVYJmWHXhSvNnYKkmpjPIF30", new UserAuthenticationCallback() {
                @Override
                public void onCompleted(MobileServiceUser mobileServiceUser, Exception e, ServiceFilterResponse serviceFilterResponse) {

                }
            });

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
