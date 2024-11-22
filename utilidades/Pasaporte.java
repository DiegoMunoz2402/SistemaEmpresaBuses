//Class pasaporte : Rodolfo

package utilidades;

import java.io.Serializable;

public class Pasaporte implements IdPersona, Serializable {

    //Atributos

    private String numero;
    private String nacionalidad;

    //Constructor

    private Pasaporte(String numero, String nacionalidad) {
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
        if(nacionalidad==null || nacionalidad.isEmpty() || !nacionalidad.matches("[a-zA-Z]+")){
            System.out.println("Nacionalidad Invalido");
            return null;
        }

        if(numero == null || numero.length() < 6 || !numero.matches("[a-zA-Z0-9]+")){
            System.out.println("Numero Invalido");
            return null;
        }
        return new Pasaporte(numero, nacionalidad);



    }

     //metodo equals() de la interfaz utilidades.IdPersona
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasaporte pasaporte = (Pasaporte) o;
        return numero.equals(pasaporte.numero) && nacionalidad.equals(pasaporte.nacionalidad);

    }
    //metodo toString() de la interfaz utilidades.IdPersona
    public String toString() {
        return " " + numero + " " + nacionalidad;
    }
}
