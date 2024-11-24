//Class pasaje : Diego Muñoz
package modelo;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pasaje  implements Serializable {
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

    public String toString(){

        String empresa = viaje.getBus().getEmpresa().getNombre();
        String numeroPasaje = String.valueOf(numero);
        String nombrePasajero = pasajero.getNombrecompleto().toString();
        String rut = pasajero.getIdpersona().toString();
        String patenteBus = viaje.getBus().getPatente();
        int asiento = getAsiento();
        int valorPagado = getVenta().getMontoPagado();
        String terminalOrigen = viaje.getTerminalSalida().getNombre();
        String terminalDestino = viaje.getTerminalLlegada().getNombre();
        String fecha = viaje.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = viaje.getHora().format(DateTimeFormatter.ofPattern("HH:mm"));

        String titulo1 = String.format("%-48s", "----------------------- PASAJE ELECTRÓNICO -------------------------");

        return String.format(
                "%s\n" +
                        "%-20s %s\n" + // Empresa y número de pasaje
                        "%-20s %s\n" + // Empresa y número de pasaje
                        "%-40s %s\n" + // Nombre del pasajero y RUT
                        "%-40s %s\n" + // Nombre del pasajero y RUT
                        "%-15s %-15s %s\n" + // Patente, Asiento y Valor pagado
                        "%-15s %-15s %d\n" + // Patente, Asiento y Valor pagado
                        "%-20s %-20s %-15s %s\n" + //Terminal origen, Terminal Destino, Fecha y Hora
                        "%-20s %-20s %-15s %s\n%s",//Terminal origen, Terminal Destino, Fecha y Hora
                titulo1,
                "Nombre Empresa", "Número de Pasaje",
                empresa, numeroPasaje,
                "Nombre Pasajero", "RUT/Pasaporte",
                nombrePasajero, rut,
                "Patente Bus", "Asiento", "Valor Pagado",
                patenteBus, asiento, valorPagado,
                "Terminal Origen", "Terminal Destino","Fecha","Hora",
                terminalOrigen, terminalDestino,fecha, hora,
                "--------------------------------------------------------------------"
        );
    }
}
