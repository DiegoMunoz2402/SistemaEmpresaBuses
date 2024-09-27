//Sirel Henríquez
import java.util.ArrayList;
import java.util.List;

public class Bus {

    //Atributos
    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private ArrayList<Viaje> listaViaje;

    //Constructor
    public Bus(String patente, String marca, String modelo, int nroAsientos) {
        this.patente = patente;
        this.marca = marca;
        this.nroAsientos = nroAsientos;
        this.modelo = modelo;
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

    public void addViaje(Viaje viaje){
        listaViaje.add(viaje);
        System.out.println("Se agrego el viaje correctamente");
    }
}
