import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class SistemaVentaPasajes  {

    private ArrayList<Cliente> clientes;
    private ArrayList<Pasajero> pasajeros;
    private ArrayList<Venta> ventas;
    private ArrayList<Viaje> viajes;
    private ArrayList<Bus> buses;

    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){
        if(findCliente(id)!=null){
            System.out.println("Ya existe la ID: "+ id);
            return false;
        }

        Cliente nuevoCliente= new Cliente(id,nom,email);
        nuevoCliente.setTelefono(fono);
        clientes.add(nuevoCliente);
        System.out.println("Cliente agregado :D");
        return true;

    }

    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto){}

    public boolean createBus(String patente, String marca, String modelo, int nroAsientos){}

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus){}

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente){}

    public String[][] getHorariosDisponibles(LocalDate fechaViaje){}

    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){}

    public int getMontoVenta(String idDocumento, TipoDocumento tipo){}

    public int getNombrePasajero(IdPersona idPasajero){}
    //No aparece en el UML que tipo de dato es idPasajero, aunque por lógica se asume que es del tipo idPersona
    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero){
           Venta venta = null;
           Viaje viaje = null;
           Pasajero pasajero = null;
           for(int i=0;i<ventas.size();i++){
               if(ventas.get(i).getIdDocumento().equals(idDoc)){
                   venta = ventas.get(i);

               }
              if(venta==null){
                  System.out.println("no se encontro la venta");
                  return false;
              }

           }
           for(int i=0;i<viajes.size();i++){
               if (viajes.get(i).getFecha().equals(fecha) && viajes.get(i).getHora().equals(hora) && viajes.get(i).getBus().getPatente().equals(patBus)){
                   viaje = viajes.get(i);

               }
           }
           if(viaje==null){
               System.out.println("no se encontro el viaje ");
               return false;
           }
           if(asiento < 1||asiento > viaje.getBus().getNroAsientos()){
               System.out.println("asiento incorrecto");
               return false;
           }
           for(int i=0;i<pasajeros.size();i++){
               if(pasajeros.get(i).getIdpersona().equals(idPasajero)){
                   pasajero = pasajeros.get(i);
                   break;;
               }
           }
           if(pasajero==null){
               System.out.println("no se encontro el pasajero");
               return false;
           }
          /*
           long numero= 0;
           Pasaje nuevoPasaje= new Pasaje(asiento,numero,viaje,pasajero,venta);
           nuevoPasaje.numeroAleatorio();
           */
        System.out.println("Pasaje vendido :D");
        return true;


    }

    public String[][] listVentas(){}

    public String[][] listViajes(){}

    public String[][] lisPasajeros(LocalDate fecha, LocalTime hora, String patBus){}

    private Cliente findCliente(IdPersona id){
        for(int i=0;i<clientes.size();i++){
            Cliente cliente= clientes.get(i);
            if(Objects.equals(cliente.getIdpersona(), id)){
                return cliente;
            }



        }
        return null;
    }

    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento){}

    private Bus findBus(String patente){}

    private Viaje findViaje(String fecha, String hora, String patenteBus){}

    private Pasajero findPasajero(IdPersona idPersona){}
}
