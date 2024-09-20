import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SistemaVentaPasajes {

    private ArrayList<Cliente> clientes;
    private ArrayList<Pasajero> pasajeros;
    private ArrayList<Venta> ventas;
    private ArrayList<Viaje> viajes;
    private ArrayList<Bus> buses;

    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){}

    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto){
        boolean encontrado=false;
        for (Pasajero pasajero:pasajeros){
            if (pasajero.getIdpersona().equals(id)){
                encontrado = true;
            }
        }
        if (!encontrado){
            Pasajero pasajero = new Pasajero(id,nom,nomContacto,fonoContacto);
            pasajero.setTelefono(fono);
            if (!pasajeros.contains(pasajero)) {
                pasajeros.add(pasajero);
                return true;
            }
        }
        return false;
    }

    public boolean createBus(String patente, String marca, String modelo, int nroAsientos){}

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus){}

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente){}

    public String[][] getHorariosDisponibles(LocalDate fechaViaje){}

    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){}

    public int getMontoVenta(String idDocumento, TipoDocumento tipo){}

    public int getNombrePasajero(IdPersona idPasajero){}
    //No aparece en el UML que tipo de dato es idPasajero, aunque por lógica se asume que es del tipo idPersona
    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero){}

    public String[][] listVentas(){}

    public String[][] listViajes(){}

    public String[][] lisPasajeros(LocalDate fecha, LocalTime hora, String patBus){}

    private Cliente findCliente(IdPersona id){}

    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento){}

    private Bus findBus(String patente){
        for (Bus bus:buses){
            if (bus.getPatente().equals(patente)){
                return bus;
            }
        }
        return null;
    }

    private Viaje findViaje(String fecha, String hora, String patenteBus){}

    private Pasajero findPasajero(IdPersona idPersona){
        for (Pasajero pasajero:pasajeros){
            if (pasajero.getIdpersona().equals(idPersona)){
                return pasajero;
            }
        }
        return null;
    }
}
