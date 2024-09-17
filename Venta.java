import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {

    //Attributes
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;

    private ArrayList<Pasaje> pasajes;
    private Cliente cli;
    //Constructor
    public Venta(String id, TipoDocumento tipo, LocalDate fec, Cliente cli) {
        this.idDocumento = id;
        this.tipo = tipo;
        this.fecha = fec;
        this.cli = cli;
        this.pasajes = new ArrayList<>();

        if (cli != null){ // asocia venta a Cliente
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
        return cli;
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
