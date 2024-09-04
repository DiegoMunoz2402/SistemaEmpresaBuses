package com.company;

public class Cliente extends Persona {
    //Atributos
    private String email;

    //Constructor
     public Cliente(IdPersona idPersona,Nombre nombre, String email) {
         super(idPersona, nombre); //constructor superclase Persona
         this.email = email;
     }

     //Metodos
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
