package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Viaje {

    //Atributos
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private int duracion;

    private Terminal llegada;
    private Terminal salida;
    private ArrayList<Conductor> conductors;
    private Auxiliar auxiliar;

    private Bus bus;
    private ArrayList<Pasaje> pasajes;

    //Constructor
    public Viaje(LocalDate fecha, LocalTime hora, int precio, int duracion, Bus bus, Auxiliar auxiliar, Conductor conductor, Terminal salida, Terminal llegada) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
        this.duracion = duracion;
        this.auxiliar = auxiliar;
        this.llegada = llegada;
        this.salida = salida;
        conductors = new ArrayList<>();
        conductors.add(conductor);
        auxiliar.addViaje(this);
        conductor.addViaje(this);
        salida.addSalida(this);
        llegada.addLlegada(this);
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

    public void setDuracion(int duracion){
        this.duracion=duracion;
    }

    public LocalDateTime getFechaHoraTermino(){
        LocalDateTime fechaInicio = LocalDateTime.of(fecha,hora);
        return fechaInicio.plusMinutes(duracion);
    }



    public Bus getBus() {
        return bus;
    }

    public String[] getAsientos() {
        String[] asientos = new String[bus.getNroAsientos()];
        for (int i = 0; i < bus.getNroAsientos(); i++) {
            asientos[i] = String.valueOf(i + 1); // Número de asiento
            boolean ocupado = false;

            for (Pasaje pasaje : pasajes){
                if (pasaje.getAsiento() == i + 1) {
                    ocupado = true;
                    break;
                }
            }
            if(ocupado){
                asientos[i] = "*";
            }
        }
        return asientos;
    }

    public void addPasaje(Pasaje pasaje){
        if (pasaje!=null && !pasajes.contains(pasaje)) {
            pasajes.add(pasaje);
        }
    }



   public String[][] getListaPasajeros() {

        String[][] lista = new String[pasajes.size()][5];

        for (int i = 0; i < pasajes.size(); i++) {
            Pasajero pasajero = pasajes.get(i).getPasajero();
            String asiento = String.valueOf(pasajes.get(i).getAsiento());
            lista[i][0] = String.valueOf(pasajero.getIdpersona());
            lista[i][1] = String.valueOf(pasajero.getNombrecompleto());
            lista[i][2] = String.valueOf(pasajero.getNomContacto());
            lista[i][3] = pasajero.getFonoContacto();
            lista[i][4] = asiento;
        }
        return lista;
    }

    public boolean existeDisponibilidad(int nroAsiento){
        return getNroAsientosDisponibles() >= nroAsiento;
    }

    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - pasajes.size();
    }

   public Venta[] getVentas(){
        ArrayList<Venta> ventasTotales = new ArrayList<>();
        if (!pasajes.isEmpty()) {
            for (Pasaje pasaje : pasajes) {
                if (!ventasTotales.contains(pasaje.getVenta())) {
                    ventasTotales.add(pasaje.getVenta());
                }
            }
            return ventasTotales.toArray(new Venta[0]);
        }else {
            return new Venta[0];
        }
   }

   public void addConductor(Conductor conductor){
        if (conductor!=null && !conductors.contains(conductor) && conductors.size()<2){
            conductors.add(conductor);
            conductor.addViaje(this);
        }
   }

   public Tripulante[] getTripulantes(){
        Tripulante[] tripulantes = new Tripulante[1+conductors.size()];
        tripulantes[0]=auxiliar;
       for (int i = 1; i < conductors.size(); i++) {
           tripulantes[i]=conductors.get(i);
       }
       return tripulantes;
   }

   public Terminal getTerminalLlegada(){
        return llegada;
   }

   public Terminal getTerminalSalida(){
        return salida;
   }
}
