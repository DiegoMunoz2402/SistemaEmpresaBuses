package com.company;

public class Pasajero extends Persona{
    private Nombre nomContacto;
    private String fonoContacto;
    //Relaciones con el pasaje
    private Pasaje pasaje;
    public Nombre getNomContacto(){
        return nomContacto;
    }
    public void setNomContacto(Nombre nom){
        this.nomContacto=nom;
    }
    public String getFonoContacto(){
        return fonoContacto;
    }
    public void setFonoContacto(String fono) {
        this.fonoContacto = fono;
    }
}
