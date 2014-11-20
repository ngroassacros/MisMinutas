package com.ram.misminutas.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ram.misminutas.Clases.Minuta;
import com.ram.misminutas.Clases.Proyecto;
import com.ram.misminutas.Clases.Usuario;

import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ramses on 12/11/14.
 */
public class DB extends SQLiteOpenHelper {
    private static final int VERSION_BASEDATOS = 1;

    // Nombre de nuestro archivo de base de datos
    private static final String NOMBRE_BASEDATOS = "MisMinutas.db";

    // CONSTRUCTOR de la clase
    public DB(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);

    }

    // Sentencia SQL para la creación de una tabla
    private static final String TABLA_USUARIO = "CREATE TABLE Usuario " + "( id TEXT, Nombre TEXT, Email TEXT, Telefono TEXT, Pass TEXT, idAux INTEGER PRIMARY KEY AUTOINCREMENT)";
    private static final String TABLA_PROYECTO = "CREATE TABLE Proyecto " + "( id TEXT, Nombre TEXT, Descripcion TEXT, UsuarioCreador INTEGER, FechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP, idAux INTEGER PRIMARY KEY AUTOINCREMENT)";
    private static final String TABLA_MINUTA = "CREATE TABLE Minuta " + "( id TEXT, Titulo TEXT, Cliente TEXT, Lugar TEXT, FechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP, IdProyecto INTEGER, IdUsuario INTEGER, idAux INTEGER PRIMARY KEY AUTOINCREMENT)";
    private static final String TABLA_ASISTENTE = "CREATE TABLE Asistente " + "( id TEXT, Email TEXT, Nombre TEXT, FechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP, idAux INTEGER PRIMARY KEY AUTOINCREMENT)";
    private static final String TABLA_ASISTENTEMINUTA = "CREATE TABLE AsistenteMinuta " + "( id TEXT, IdMinuta INTEGER, IdAsistente INTEGER, idAux INTEGER PRIMARY KEY AUTOINCREMENT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIO);
        Log.i("PATH 1:", db.getPath());
        Log.i("CREATE", "OK");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIO);
        onCreate(db);
        Log.i("PATH 2:", db.getPath());
        Log.i("UPGRADE", "OK");
    }

    public boolean InsertarUsuario(Usuario usuario) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (db != null) {
                ContentValues valores = new ContentValues();
                valores.put("Nombre", usuario.nombre);
                valores.put("Telefono", usuario.telefono);
                valores.put("Email", usuario.email);
                valores.put("Pass", usuario.pass);
                db.insert("Usuario", null, valores);
                db.close();
                return true;
            }
        } catch (Exception e) {
            Log.e("ErrorInsertarUsuario: ", e.getMessage());
        }

        return false;
    }

    public boolean EliminarUsuario(String idUsuario) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete("Usuario", "id=" + idUsuario, null);
            db.close();
            return true;
        }catch (Exception e){
            Log.e("ErrorEliminarUsuario: ", e.getMessage());
        }

        return false;
    }

    public boolean ActualizarUsuario(Usuario usuario) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (db != null) {
                ContentValues valores = new ContentValues();
                valores.put("Nombre", usuario.nombre);
                valores.put("Telefono", usuario.telefono);
                valores.put("Email", usuario.email);
                db.update("Usuario", valores, "id=" + usuario.id, null);
                db.close();
                return true;
            }
        }catch (Exception e){
            Log.e("ErrorActualizarUsuario: ", e.getMessage());
        }

        return false;
    }

    public List<Usuario> ObtenerUsuarios(){
        List<Usuario> lista_usuarios = new ArrayList<Usuario>();
        try {
            SQLiteDatabase db = getWritableDatabase();

            String[] valores_recuperar = {"id", "nombre", "email", "telefono", "pass"};
            Cursor c = db.query("Usuario", valores_recuperar, null, null, null, null, null, null);
            if(c.moveToFirst()) {
                do {
                    Usuario usuario = new Usuario();
                    usuario.id = c.getString(0);
                    usuario.nombre = c.getString(1);
                    usuario.email = c.getString(2);
                    usuario.telefono = c.getString(3);
                    usuario.pass = c.getString(4);
                    lista_usuarios.add(usuario);
                } while (c.moveToNext());
            }
            db.close();
            c.close();
        }
        catch (Exception e){
            Log.e("ErrorConsultarUsuarios: ", e.getMessage());
        }
        return lista_usuarios;
    }

    public Usuario ObtenerUsuario(String user, String pass){
        Usuario usuario = null;

        try {
            List<Usuario> listaUsuarios = this.ObtenerUsuarios();

            for (Usuario usuarioItem : listaUsuarios) {
                if(usuarioItem.email.equals(user) && usuarioItem.pass.equals(pass)){
                    usuario = usuarioItem;
                    break;
                }
            }
        }
        catch (Exception e){
            Log.e("ErrorConsultarUsuario: ", e.getMessage());
            usuario = null;
        }
        return usuario;
    }

    /*
    Seccion de Proyectos
     */

    public boolean InsertarProyecto(Proyecto proyecto) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (db != null) {
                ContentValues valores = new ContentValues();
                valores.put("Nombre", proyecto.Nombre);
                valores.put("Descripcion", proyecto.Descripcion);
                valores.put("FechaCreacion", proyecto.FechaCreacion.toString());
                valores.put("UsuarioCreador", proyecto.UsuarioCreador);
                db.insert("Proyecto", null, valores);
                db.close();
                return true;
            }
        }
        catch (NullPointerException npe){
            Log.e("NPE ErrorInsertarProyecto: ", npe.getMessage());
        }catch (Exception e) {
            Log.e("ErrorInsertarProyecto: ", e.getMessage());
        }

        return false;
    }

    public List<Proyecto> ObtenerProyectos(){
        List<Proyecto> lista_proyectos = new ArrayList<Proyecto>();
        try {
            SQLiteDatabase db = getWritableDatabase();

            String[] valores_recuperar = {"id", "Nombre", "Descripcion", "FechaCreacion", "UsuarioCreador"};
            Cursor c = db.query("Proyecto", valores_recuperar, null, null, null, null, null, null);
            if(c.moveToFirst()) {
                do {
                    Proyecto proyecto = new Proyecto();
                    proyecto.Id = c.getInt(0);
                    proyecto.Nombre = c.getString(1);
                    proyecto.Descripcion = c.getString(2);
                    proyecto.FechaCreacion = new Date(c.getString(3));
                    proyecto.UsuarioCreador = c.getInt(4);
                    lista_proyectos.add(proyecto);
                } while (c.moveToNext());
            }
            db.close();
            c.close();
        }
        catch (Exception e){
            Log.e("ErrorConsultarProyectos: ", e.getMessage());
        }
        return lista_proyectos;
    }

    /*
    Seccion de Minutas
    */

    public boolean InsertarMinuta(Minuta minuta) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (db != null) {
                ContentValues valores = new ContentValues();
                valores.put("Titulo", minuta.Titulo);
                valores.put("Cliente", minuta.Cliente);
                valores.put("FechaCreacion", minuta.Fecha.toString());
                valores.put("Lugar", minuta.Lugar);
                valores.put("IdUsuario", minuta.IdUsuario);
                valores.put("IdProyecto", minuta.IdProyecto);
                db.insert("Minuta", null, valores);
                db.close();
                return true;
            }
        }
        catch (NullPointerException npe){
            Log.e("NPE ErrorInsertarProyecto: ", npe.getMessage());
        }catch (Exception e) {
            Log.e("ErrorInsertarProyecto: ", e.getMessage());
        }

        return false;
    }

    public List<Minuta> ObtenerMinutas(){
        List<Minuta> lista_minutas = new ArrayList<Minuta>();
        try {
            SQLiteDatabase db = getWritableDatabase();

            String[] valores_recuperar = {"id", "Titulo", "Cliente", "FechaCreacion", "Lugar", "IdUsuario", "IdProyecto"};
            Cursor c = db.query("Minuta", valores_recuperar, null, null, null, null, null, null);
            if(c.moveToFirst()) {
                do {
                    Minuta minuta = new Minuta();
                    minuta.Id = c.getString(0);
                    minuta.Titulo = c.getString(1);
                    minuta.Cliente = c.getString(2);
                    minuta.Fecha = new Date(c.getString(3));
                    minuta.Lugar = c.getString(4);
                    minuta.IdUsuario = c.getInt(5);
                    minuta.IdProyecto = c.getInt(6);
                    lista_minutas.add(minuta);
                } while (c.moveToNext());
            }
            db.close();
            c.close();
        }
        catch (Exception e){
            Log.e("ErrorConsultarMinutas: ", e.getMessage());
        }
        return lista_minutas;
    }

    public void InicializarBaseDeDatos(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        db.execSQL("DROP TABLE IF EXISTS Proyecto");
        db.execSQL("DROP TABLE IF EXISTS Tarea");
        db.execSQL("DROP TABLE IF EXISTS Asistente");
        db.execSQL("DROP TABLE IF EXISTS AsistenteMinuta");
        db.execSQL("DROP TABLE IF EXISTS Archivo");
        db.execSQL("DROP TABLE IF EXISTS Minuta");
        db.execSQL(TABLA_USUARIO);
        db.execSQL(TABLA_PROYECTO);
        db.execSQL(TABLA_MINUTA);
        db.execSQL(TABLA_ASISTENTE);
        db.execSQL(TABLA_ASISTENTEMINUTA);
    }
}
