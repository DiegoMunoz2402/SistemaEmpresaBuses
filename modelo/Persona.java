package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

import java.io.Serializable;

//Clase Completo modelo.Persona, Autor: Rodolfo Salas
public abstract class Persona implements Serializable {
    //Atributos

    private IdPersona Idpersona;  //interfaz utilidades.IdPersona
    private Nombre Nombrecompleto;
    private String telefono;

    //Constructor
    public Persona(IdPersona idPersona, Nombre Nombrecompleto)  {
        this.Idpersona = idPersona;
        this.Nombrecompleto = Nombrecompleto;

    }

    //Metodos

    public IdPersona getIdpersona() {
        return Idpersona;
    }

    public Nombre getNombrecompleto() {
        return Nombrecompleto;
    }

    public void setNombrecompleto(Nombre nombrecompleto) {
        Nombrecompleto = nombrecompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return  Idpersona + " " + Nombrecompleto + " " + telefono;
    }
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Persona persona = (Persona) o;
        return persona.equals(persona.Idpersona) &&
                Nombrecompleto.equals(persona.Nombrecompleto) &&
                telefono.equals(persona.telefono);
    }

}
