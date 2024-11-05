//Clase PagoTarjeta: Javiera Duarte

package modelo;

public class PagoTarjeta extends Pago {

    private long numeroTarjeta;

    public PagoTarjeta(int monto, long numeroTarjeta) {

        super(monto);
        this.numeroTarjeta = numeroTarjeta;
    }

    public long getNumeroTarjeta() {
        return numeroTarjeta;
    }
}
