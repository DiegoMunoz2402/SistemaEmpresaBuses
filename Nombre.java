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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    public String toString(){
        return tratamiento + " " + nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Nombre nombre = (Nombre) o;
        return nombre.equals(nombre.nombre) &&
                apellidoPaterno.equals(nombre.apellidoPaterno) &&
                apellidoMaterno.equals(nombre.apellidoMaterno);
    }
}
