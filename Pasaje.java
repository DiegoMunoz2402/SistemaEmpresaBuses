//Clase pasaje: Diego Muñoz
import java.security.SecureRandom;

public class Pasaje {
    private long numero;
    private int asiento;

    private Viaje viaje;
    private Pasajero pasajero;
    private Venta venta;

    public Pasaje(int asiento, Viaje viaje, Pasajero pasajero, Venta venta){
        this.asiento=asiento;
        this.numero=numeroAleatorio();
        this.viaje=viaje;
        this.pasajero=pasajero;
        this.venta=venta;
        this.viaje.addPasaje(this);

    }
    public long numeroAleatorio(){
        SecureRandom random=new SecureRandom();
        long num=Math.abs(random.nextLong());
        return num;
    }
    public long getNumero() {
        return (int)(numero & Integer.MAX_VALUE);
    }

    public int getAsiento() {
        return asiento;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Venta getVenta() {
        return venta;
    }
}
