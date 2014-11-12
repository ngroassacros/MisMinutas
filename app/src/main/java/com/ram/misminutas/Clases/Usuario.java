package com.ram.misminutas.Clases;

/**
 * Created by ramses on 10/11/14.
 */
public class Usuario
{
    @com.google.gson.annotations.SerializedName("Id")
    public int Id;
    @com.google.gson.annotations.SerializedName("Nombre")
    public String Nombre;
    @com.google.gson.annotations.SerializedName("Email")
    public String Email;
    @com.google.gson.annotations.SerializedName("Telefono")
    public long Telefono;
}
