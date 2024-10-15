package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.util.ArrayList;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;

    //Relaciones
    private ArrayList<Bus> buses;
    private ArrayList<Conductor> conductores;
    private ArrayList<Auxiliar> auxiliares;

    public Empresa(Rut rut, String nombre){
        this.rut=rut;
        this.nombre=nombre;
        buses = new ArrayList<>();
        conductores = new ArrayList<>();
        auxiliares = new ArrayList<>();
    }

    public Rut getRut(){
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addBus(Bus bus){
        if (bus != null && !buses.contains(bus)){
            buses.add(bus);
        }
    }

    public Bus[] getBuses(){
        return buses.toArray(new Bus[0]);
    }

    public boolean addConductor(IdPersona idPersona, Nombre nombre, Direccion direccion){
        Conductor conductor = findConductor(idPersona);
        Auxiliar auxiliar = findAuxiliar(idPersona);
        if (conductor==null && auxiliar == null){
            conductores.add(new Conductor(idPersona,nombre,direccion));
            return true;
        }
        return false;
    }

    public boolean addAuxiliar(IdPersona idPersona, Nombre nombre, Direccion direccion){
        Conductor conductor = findConductor(idPersona);
        Auxiliar auxiliar = findAuxiliar(idPersona);
        if (conductor==null && auxiliar == null){
            auxiliares.add(new Auxiliar(idPersona,nombre,direccion));
            return true;
        }
        return false;
    }

    public Tripulante[] getTripulantes(){
        Tripulante[] tripulantesTotales = new Tripulante[auxiliares.size() + conductores.size()];
        for (int i = 0; i < auxiliares.size(); i++) {
            tripulantesTotales[i] = auxiliares.get(i);
        }
        int j=0;
        for (int i = conductores.size(); i < tripulantesTotales.length; i++){
            tripulantesTotales[i] = conductores.get(j);
            j++;
        }
        return tripulantesTotales;
    }

    public Venta[] getVentas(){
        ArrayList<Venta> ventas = new ArrayList<>();
        Viaje[] viajes;
        Venta[] ventasPorViaje;
        for (Bus bus:buses){
            viajes = bus.getViajes();
            for (Viaje viaje:viajes){
                ventasPorViaje = viaje.getVentas();
                for (Venta venta:ventasPorViaje){
                    ventas.add(venta);
                }
            }
        }
        return ventas.toArray(new Venta[0]);
    }

//    public modelo.Venta[] getVentas(){
//        ArrayList<modelo.Viaje[]> viajesPorBus = new ArrayList<>();
//        for (int i = 0; i < buses.size(); i++) {
//            modelo.Viaje[] viajes=buses.get(i).getViajes();
//            viajesPorBus.add(viajes);
//        }
//        ArrayList<modelo.Venta[]> ventasTotales = new ArrayList<>();
//        for (int i = 0; i < viajesPorBus.size(); i++) {
//            modelo.Viaje[] viaje = viajesPorBus.get(i);
//            ventasTotales.add(viaje[i].getVentas());
//        }
//        return ventasTotales.toArray(new modelo.Venta[0]);
//    }

    private Conductor findConductor(IdPersona idPersona){
        for (Conductor conductores:conductores){
            if (conductores.getIdpersona().equals(idPersona)){
                return conductores;
            }
        }
        return null;
    }
    private Auxiliar findAuxiliar(IdPersona idPersona){
        for (Auxiliar auxiliar: auxiliares){
            if (auxiliar.getIdpersona().equals(idPersona)){
                return auxiliar;
            }
        }
        return null;
    }
}
