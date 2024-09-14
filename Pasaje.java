import java.security.SecureRandom;

public class Pasaje {
    private long numero;
    private int asiento;

    private Viaje viaje;
    private Pasajero pasajero;
    private Venta venta; //Falta clase venta

    public Pasaje(int asiento, long numero, Viaje viaje, Pasajero pasajero, Venta venta){
        this.asiento=asiento;
        this.numero=numeroAleatorio();
        this.viaje=viaje;
        this.pasajero=pasajero;
        this.venta=venta;
        this.viaje.addPasaje(this);
        //Falta agregar a venta
    }
    public long numeroAleatorio(){
        SecureRandom random=new SecureRandom();
        long num=Math.abs(random.nextLong());
        return num;
    }
    public long getNumero() {
        return numero;
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
