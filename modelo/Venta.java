//Clase Venta: Javiera Duarte

package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    private List<Pasaje> pasajes;
    private Pago pago;


    //Constructor
    public Venta(String id, TipoDocumento tipo, LocalDate fec, Cliente cliente) {

        this.idDocumento = id;
        this.tipo = tipo;
        this.fecha = fec;
        this.cliente = cliente;
        this.pasajes = new ArrayList<>();
        this.pago= null;

        if ( cliente != null){
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
        Pasaje nuevoPasaje = new Pasaje(asiento, viaje, pasajero,this);
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


    public int getMontoPagado(){
        return (pago != null) ? pago.getMonto() : 0;
    }

    public boolean pagaMonto(){
        if (pago == null) {
            pago = new PagoEfectivo(getMonto());
            return true;
        }
        return false;
    }

    public boolean pagaMonto(long numeroTarjeta){
        if (pago == null) {
            pago = new PagoTarjeta(getMonto(), numeroTarjeta);
            return true;
        }
        return false;
    }

    public boolean equals(Object otro){
        if (this == otro) return true;
        if (!(otro instanceof Venta)) return false;
        Venta otraVenta = (Venta) otro;
        return this.idDocumento.equals(otraVenta.idDocumento) &&
                this.tipo.equals(otraVenta.tipo);
    }

    public String getTipoPago() {
        if (pago instanceof PagoEfectivo) {
            return "efectivo";
        } else if (pago instanceof PagoTarjeta) {
            return "tarjeta";
        }
        return null;
    }
}

