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

    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto){
        boolean encontrado=false;
        for (Pasajero pasajero:pasajeros){
            if (pasajero.getIdpersona().equals(id)){
                encontrado = true;
            }
        }
        if (!encontrado){
            Pasajero pasajero = new Pasajero(id,nom,nomContacto,fonoContacto);
            pasajero.setTelefono(fono);
            if (!pasajeros.contains(pasajero)) {
                pasajeros.add(pasajero);
                return true;
            }
        }
        return false;
    }

    public boolean createBus(String patente, String marca, String modelo, int nroAsientos){}

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus){}

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente){}

    public String[][] getHorariosDisponibles(LocalDate fechaViaje){}

    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){}

    public int getMontoVenta(String idDocumento, TipoDocumento tipo){}

    public String getNombrePasajero(IdPersona idPasajero){
        Pasajero pasajero = findPasajero(idPasajero);
        if (pasajero!=null){
            return pasajero.getNombrecompleto().toString();
        }
        return null;
    }
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
               }
           }
           if(pasajero==null){
               System.out.println("no se encontro el pasajero");
               return false;
           }

           venta.createPasaje(asiento,viaje,pasajero);
        System.out.println("Pasaje vendido :D");
        return true;


    }

    public String[][] listVentas(){}

    public String[][] listViajes(){}

    public String[][] lisPasajeros(LocalDate fecha, LocalTime hora, String patBus){
        Bus bus = findBus(patBus);
        String fechastr=fecha.toString();
        String horastr=hora.toString();
        if (bus!=null && !fechastr.isEmpty() && !horastr.isEmpty()){
            Viaje viaje = findViaje(fechastr,horastr,patBus);
            if (viaje!=null){
                String[][] pasajeros = viaje.getListaPasajeros();

                //iteracion con for para llenar matriz

                return pasajeros;
            }else{
                System.out.println("Viaje no encontrado...");
                return new String[0][0];
            }
        }else{
            System.out.println("Bus no encontrado...");
            return new String[0][0];
        }
        return new String[0][];
    }

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

    private Bus findBus(String patente){
        for (Bus bus:buses){
            if (bus.getPatente().equals(patente)){
                return bus;
            }
        }
        return null;
    }

    private Viaje findViaje(String fecha, String hora, String patenteBus){
        for(Viaje viajes : viajes){
            if(viajes.getFecha().equals(fecha) && viajes.getHora().equals(hora) && viajes.getBus().getPatente().equals(patenteBus)){
                return viajes;
            }
        }
        return null;
    }

    private Pasajero findPasajero(IdPersona idPersona){
        for (Pasajero pasajero:pasajeros){
            if (pasajero.getIdpersona().equals(idPersona)){
                return pasajero;
            }
        }
        return null;
    }
}
