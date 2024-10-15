package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

import java.util.ArrayList;

public class Auxiliar extends Tripulante{
    private ArrayList<Viaje> viajes;

    public Auxiliar(IdPersona idPersona, Nombre Nombrecompleto, Direccion direccion) {
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

