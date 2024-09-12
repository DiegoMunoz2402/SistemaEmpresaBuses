
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Viaje {

    //Atributos
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private Bus bus;

    //Constructor
    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
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

    public String[][] getAsientos(){
        //CREO SE NECESITA LA CLASE PASAJE
    }

    public void addPasaje(Pasaje pasaje){
        //NECESITA CLASE PASAJE
    }

    public String[][] getListaPasajeros(){
        //NECESITA CLASE PASAJERO Y PASAJE
    }

    public boolean existeDisponibilidad(){
        //NECESITA LO ANTERIOR
    }

    public int getNroAsientosDisponibles(){
        /*NECESITA LO ANTERIOR, PREGUNTAR DIFERENCIA ENTRE ESTE
        MÉTODO Y EL DE ARRIBA*/
    }
}
