package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Viaje {

    //Atributos
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private Bus bus;
    private ArrayList<Pasaje> pasajes;

    //Constructor
    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
        this.pasajes = new ArrayList<>();
    }

    //Métodos
    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Bus getBus() {
        return bus;
    }

    public String[][] getAsientos() {
        String[][] asientos = new String[bus.getNroAsientos()][2];
        for (int i = 0; i < bus.getNroAsientos(); i++) {
            asientos[i][0] = String.valueOf(i + 1); // Número de asiento
            boolean ocupado = false;

            for (Pasaje pasaje : pasajes){
                if(pasaje.getAsiento() == i + 1) {
                    ocupado = true;
                    break;
                }
            }
            if(ocupado){
                asientos[i][1] = "Ocupado";
            } else {
                asientos[i][1] = "Disponible";
            }
        }
        return asientos;
    }

    public void addPasaje(Pasaje pasaje){
        pasajes.add(pasaje);
    }

   public String[][] getListaPasajeros() {

        String[][] lista = new String[pasajes.size()][5];

        for (int i = 0; i < pasajes.size(); i++) {
            Pasajero pasajero = pasajes.get(i).getPasajero();
            String asiento = String.valueOf(pasajes.get(i).getAsiento());

            if(pasajero != null) {
                lista[i][0] = String.valueOf(pasajero.getIdpersona());
                lista[i][1] = String.valueOf(pasajero.getNombrecompleto());
                lista[i][2] = String.valueOf(pasajero.getNomContacto());
                lista[i][3] = pasajero.getFonoContacto();
                lista[i][4] = asiento;
            }else {
                lista[i][0] = "Desconocido";
                lista[i][1] = "Desconocido";
                lista[i][2] = "Desconocido";
                lista[i][3] = "Desconocido";
                lista[i][4] = "No adjunto";
            }
        }
        return lista;
    }

    public boolean existeDisponibilidad(){
        return getNroAsientosDisponibles() > 0;}

    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - pasajes.size();
    }
}
