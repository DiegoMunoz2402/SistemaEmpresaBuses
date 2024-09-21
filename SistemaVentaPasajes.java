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

    public String[][] getHorariosDisponibles(LocalDate fechaViaje){
        ArrayList<String[]> ViajesDisponibles = new ArrayList<>();
        for(int i=0; i<viajes.size(); i++){
            Viaje viaje = viajes.get(i);
            if(viaje.getFecha().equals(fechaViaje)){
                String AsientosDisponibles=String.valueOf(viaje.getNroAsientosDisponibles());
                String PatenteBus=viaje.getBus().getPatente();
                String HoraDeSalida= String.valueOf(viaje.getHora());
                String PrecioPasaje= String.valueOf(viaje.getPrecio());
                ViajesDisponibles.add(new String[]{AsientosDisponibles, PatenteBus, HoraDeSalida, PrecioPasaje});

            }
        }
        if(ViajesDisponibles.isEmpty()){
            return new String[0][0];
        }
        return ViajesDisponibles.toArray(new String[ViajesDisponibles.size()][]);

    }

    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){
        Bus bus = findBus(patBus);
        if (bus!=null) {
            Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
            if (viaje!=null){
                String[][] listAsientosViaje=new String[viaje.getAsientos().length][2];
                for (int i = 0; i < listAsientosViaje.length; i++) {
                    listAsientosViaje[i][0]=viaje.getAsientos()[i][0];
                    listAsientosViaje[i][1]=viaje.getAsientos()[i][1];
                    if (!listAsientosViaje[i][0].equals("Disponible")){
                        listAsientosViaje[i][0]="Ocupado";
                    }
                }
                return listAsientosViaje;
            }else {
                System.out.println("No existe un bus con esos datos..");
            }
        }else{
            System.out.println("No existe un bus con esa patente..");
        }
        return new String[0][0];
    }

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
           Pasaje nuevoPasaje= new Pasaje(asiento,viaje,pasajero,venta);
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
