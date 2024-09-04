package com.company;

public class Nombre {
    private String nombre;
    private Tratamiento tratamiento;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Nombre(String nombre, String apellidoPaterno, String apellidoMaterno, Tratamiento tratamiento){
        this.nombre=nombre;
        this.apellidoMaterno=apellidoMaterno;
        this.apellidoPaterno=apellidoPaterno;
        this.tratamiento=tratamiento;
    }
}
