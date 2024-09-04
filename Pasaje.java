package com.company;

public class Pasaje {
    private long numero;
    private int asiento;

    private Viaje viaje;
    private Pasajero pasajero; //Falta clase pasajero
    private Venta venta; //Falta clase venta
    public Pasaje(int asiento, long numero, Viaje viaje, Pasajero pasajero, Venta venta){
        this.asiento=asiento;
        this.numero=numero;
        this.viaje=viaje;
        this.pasajero=pasajero;
        this.venta=venta;
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
