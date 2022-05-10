package com.petshore.petshore_android.model;


public class Barrio {
    private String id;
    private String nombre;

    public Barrio() {}

    public Barrio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
