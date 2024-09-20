
import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {

    //Attributes
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    private ArrayList<Pasaje> pasajes;

    //Constructor
    public Venta(String id, TipoDocumento tipo, LocalDate fec, Cliente cliente) {

        this.idDocumento = id;
        this.tipo = tipo;
        this.fecha = fec;
        this.cliente = cliente;
        this.pasajes = new ArrayList<>();

        if ( cliente != null){ // asocia venta a Cliente
            cliente.addVenta(this);
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
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {
        Pasaje nuevoPasaje = new Pasaje(asiento, viaje, pasajero, this);
        pasajes.add(nuevoPasaje);

    }

     public Pasaje[] getPasajes() {
        return pasajes.toArray(new Pasaje[0]);
    }

    public int getMonto() {
        int monto=0;
        for (Pasaje pasajes:pasajes){
            monto+=pasajes.getViaje().getPrecio();
        }
        return monto;
    }

}
