import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Venta {

    //Attributes
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;


    //Constructor
    public Venta(String id, TipoDocumento tipo, LocalDate fec, Cliente cli) {

        this.idDocumento = id;
        this.tipo = tipo;
        this.fecha = fec;
        this.Cliente = cli;
        this.pasajes = new ArrayList<>();

        if ( cli != null;){ // asocia venta a Cliente
            cli.addVenta(this);
        }

    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {
        Pasaje nuevoPasaje = new Pasaje(asiento, viaje, pasajero);
        pasajes.add(nuevoPasaje);

    }

    public List<Pasaje> getPasajes() {
        return pasajes;
    }

    public int getMonto() {
        return monto;
    }

}
