//Class: Conductor Diego Muñoz
package modelo;

import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.util.ArrayList;

public class Conductor extends Tripulante{
    private ArrayList<Viaje> viajes;

    public Conductor(IdPersona idPersona, Nombre Nombrecompleto, Direccion direccion) {
        super(idPersona, Nombrecompleto, direccion);
        viajes = new ArrayList<>();
    }

    @Override
    public void addViaje(Viaje viaje) {
        if (!viajes.contains(viaje) && viaje!=null){
            viajes.add(viaje);
        }
    }

    @Override
    public int getNroViajes() {
        return viajes.size();
    }
}

