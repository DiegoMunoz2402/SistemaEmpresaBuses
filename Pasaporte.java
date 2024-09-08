package com.company;

public class Pasaporte implements IdPersona {

    //Atributos

    private String numero;
    private String nacionalidad;

    //Constructor

    public Pasaporte(String numero, String nacionalidad) {
        this.numero = numero;
        this.nacionalidad = nacionalidad;
    }

    //Metodos

    public String getNumero() {
        return numero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public static Pasaporte of(String numero, String nacionalidad) {
        if(numero == null || numero.length() < 8){
            System.out.println("Numero Invalido");
            return null;
        }
        if(nacionalidad==null || nacionalidad.isEmpty()){
            System.out.println("Nacionalidad Invalido");
            return null;
        }
        return new Pasaporte(numero, nacionalidad);

    }

     //metodo equals() de la interfaz IdPersona
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasaporte pasaporte = (Pasaporte) o;
        return numero.equals(pasaporte.numero) && nacionalidad.equals(pasaporte.nacionalidad);

    }
    //metodo toString() de la interfaz IdPersona
    public String toString() {
        return " " + numero + " " + nacionalidad;
    }
}
