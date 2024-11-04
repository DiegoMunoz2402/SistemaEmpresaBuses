package modelo;

import java.util.ArrayList;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;

public class Tripulante extends Persona {
    Direccion direccion;

    private ArrayList<Viaje> viajes;
    public Tripulante(IdPersona id, Nombre nom, Direccion dir) {
        super(id,nom);
        this.direccion = dir;
        this.viajes= new ArrayList<>();
    }
    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void addViaje(Viaje viaje){}

    public int getNroViajes() {
        return viajes.size();
    }
}