package modelo;

import java.util.ArrayList;

public class Bus {

    //Atributos
    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private ArrayList<Viaje> listaViaje;
    private Empresa empresa;

    //Constructor
   public Bus(String patente, String marca, String modelo, int nroAsientos, Empresa empresa) {
        this.patente = patente;
        this.marca = marca;
        this.nroAsientos = nroAsientos;
        this.modelo = modelo;
        this.empresa = empresa;
        empresa.addBus(this);
        this.listaViaje = new ArrayList<>();
    }

    //Métodos
    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getNroAsientos() {
        return nroAsientos;
    }

    public Empresa getEmpresa(){
       return empresa;
    }

    public void addViaje(Viaje viaje){
        if (viaje!=null && !listaViaje.contains(viaje)){
            listaViaje.add(viaje);
        }
    }

    public Viaje[] getViajes(){
       return listaViaje.toArray(new Viaje[0]);
    }
}
