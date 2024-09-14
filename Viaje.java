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
                    asientos[i][1] = String.valueOf(pasaje.getPasajero().getNomContacto());
                    ocupado = true;
                    break;
                }
            }
            if(!ocupado){
                asientos[i][1] = "Disponible";
            }
        }
        return asientos;
    }

    public void addPasaje(Pasaje pasaje){
        pasajes.add(pasaje);
    }

    public String[][] getListaPasajeros() {
        String[][] lista = new String[pasajes.size()][2];
        for (int i = 0; i < pasajes.size(); i++) {
            lista[i][0] = String.valueOf(pasajes.get(i).getPasajero().getNomContacto());
            lista[i][1] = String.valueOf(pasajes.get(i).getAsiento());
        }
        return lista;
    }

    public boolean existeDisponibilidad(){
        return getNroAsientosDisponibles() > 0;}

    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - pasajes.size();
    }
}
