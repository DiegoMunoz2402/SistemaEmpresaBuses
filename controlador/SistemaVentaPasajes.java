package controlador;

import modelo.*;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SistemaVentaPasajes  {

    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Pasajero> pasajeros = new ArrayList<>();
    private ArrayList<Venta> ventas = new ArrayList<>();
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private ArrayList<Bus> buses = new ArrayList<>();

         // metodo createCliente: Rodolfo Salas
    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){
        if(findCliente(id)!=null){
            System.out.println("Ya existe la ID: "+ id);
            return false;
        }

        Cliente nuevoCliente= new Cliente(id,nom,email);
        nuevoCliente.setTelefono(fono);
        clientes.add(nuevoCliente);
        System.out.println("modelo.Cliente agregado :D");
        return true;

    }
    //Metodo createPasajero: Diego Muñoz
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
    //Método createBus : Sirel Henríquez
    public boolean createBus(String patente, String marca, String modelo, int nroAsientos){
        if (findBus(patente) == null){
            Bus nuevoBus = new Bus(patente, marca, modelo, nroAsientos);
            if (!buses.contains(nuevoBus)) {
                buses.add(nuevoBus);
                return true;
            }
        }
        return false;
    }
    //Método createViaje: Sirel Henríquez
    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus){
        Bus bus = findBus(patBus);

        /*Si findViaje es distinto de nullo, osea hay datos y existe retorna false
        porque no puede crear algo que ya existe */
        if (bus == null || findViaje(fecha, hora, patBus) != null) {
            return false;

        }else{
            //Creo un nuevo viaje
            Viaje nuevoViaje = new Viaje(fecha, hora, precio, bus);
            //Añado el nuevo viaje
            viajes.add(nuevoViaje);
            return true;
        }
    }

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {

        Venta venta = findVenta(idDoc, tipo);
        if (venta != null){
            return false;	}

        Cliente cliente = findCliente(idCliente);
        if (cliente == null) {
            return false;	}

        Venta newVenta = new Venta(idDoc, tipo, fechaVenta, cliente);
        ventas.add(newVenta);
        return true;
    }
       //metodo getHorariosDisponibles: Rodolfo Salas
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
    //Metodo listAsientosDeViaje: Diego Muñoz
    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){
        Bus bus = findBus(patBus);
        if (bus!=null) {
            Viaje viaje = findViaje(fecha, hora, patBus);
            if (viaje!=null){
                String[][] listAsientosViaje=new String[viaje.getAsientos().length][2];
                for (int i = 0; i < listAsientosViaje.length; i++) {
                    listAsientosViaje[i][0]=viaje.getAsientos()[i][0];
                    listAsientosViaje[i][1]=viaje.getAsientos()[i][1];
                }
                return listAsientosViaje;
            }
        }
        return new String[0][0];
    }

     public int getMontoVenta(String idDocumento, TipoDocumento tipo){

        Venta venta = findVenta(idDocumento, tipo);
        if (venta!= null){
            return venta.getMonto();
        }
        return 0;
    }
    //Metodo getNombrePasajero: Diego Muñoz
    public String getNombrePasajero(IdPersona idPasajero){
        Pasajero pasajero = findPasajero(idPasajero);
        if (pasajero!=null){
            return pasajero.getNombrecompleto().toString();
        }
        return null;
    }
      // metodo vendePasaje: Rodolfo Salas
    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero){
           Venta venta = null;
           Viaje viaje = null;
           Pasajero pasajero = null;
           for(int i=0;i<ventas.size();i++){
               if(ventas.get(i).getIdDocumento().equals(idDoc)){
                   venta = ventas.get(i);
               }
           }
            if(venta==null){
                return false;
            }
           for(int i=0;i<viajes.size();i++){
               if (viajes.get(i).getFecha().equals(fecha) && viajes.get(i).getHora().equals(hora) && viajes.get(i).getBus().getPatente().equals(patBus)){
                   viaje = viajes.get(i);

               }
           }
           if(viaje==null){
               return false;
           }
           if(asiento < 1||asiento > viaje.getBus().getNroAsientos()){
               return false;
           }
           for(int i=0;i<pasajeros.size();i++){
               if(pasajeros.get(i).getIdpersona().equals(idPasajero)){
                   pasajero = pasajeros.get(i);
                   break;
               }
           }
           if(pasajero==null){
               return false;
           }

           venta.createPasaje(asiento,viaje,pasajero);
        return true;


    }

     public String[][] listVentas(){
        String[][] listaVentas = new String[ventas.size()][6];
        for(int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            listaVentas[i][0] = String.valueOf(venta.getIdDocumento());
            listaVentas[i][1] = String.valueOf(venta.getTipo());
            listaVentas[i][2] = String.valueOf(venta.getFecha());
            listaVentas[i][3] = String.valueOf(venta.getCliente());
            listaVentas[i][4] = String.valueOf(venta.getPasajes().length);
            listaVentas[i][5] = String.valueOf(venta.getMonto());
        }
        return listaVentas;

    }
    //Método listViajes : Sirel Henríquez
    public String[][] listViajes(){
        String[][] listaViajes = new String[viajes.size()][5];
        for (int i = 0; i < viajes.size(); i++){
            Viaje viaje = viajes.get(i);
            listaViajes[i][0] = String.valueOf(viaje.getFecha());
            listaViajes[i][1] = String.valueOf(viaje.getHora());
            listaViajes[i][2] = String.valueOf(viaje.getPrecio());
            listaViajes[i][3] = String.valueOf(viaje.getNroAsientosDisponibles());
            listaViajes[i][4] = viaje.getBus().getPatente();
        }
        return listaViajes;
    }
    //Metodo listPasajeros: Diego Muñoz
    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus){

        Bus bus = findBus(patBus);
        if (bus!=null){
            Viaje viaje = findViaje(fecha,hora,patBus);
            if (viaje!=null){
                String[][] pasajeros = viaje.getListaPasajeros();
                String[][] listPasajeros = new String[pasajeros.length][5];
                //iteracion con for para llenar matriz
                for (int i = 0; i < listPasajeros.length; i++) {
                    listPasajeros[i][0]=pasajeros[i][4];
                    listPasajeros[i][1]=pasajeros[i][0];
                    listPasajeros[i][2]=pasajeros[i][1];
                    listPasajeros[i][3]=pasajeros[i][2];
                    listPasajeros[i][4]=pasajeros[i][3];
                }
                return pasajeros;
            }
        }
        return new String[0][0];
    }
       //metodo findCliente: Rodolfo Salas
   private Cliente findCliente(IdPersona id){
        for (Cliente cliente:clientes){
            if (cliente.getIdpersona().equals(id)){
                return cliente;
            }
        }
        return null;
    }

    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento){
        for(Venta venta: ventas){
            if(venta.getIdDocumento().equals(idDocumento) && venta.getTipo().equals(tipoDocumento)){
                return venta;
            } }
        return null;

    }
    //Metodo findBus: Diego Muñoz
    private Bus findBus(String patente){
        for (Bus bus:buses){
            if (bus.getPatente().equals(patente)){
                return bus;
            }
        }
        return null;
    }
    //Método findViaje : Sirel Henríquez
    private Viaje findViaje(LocalDate fecha, LocalTime hora, String patenteBus){
        /*Busco en la lista de viajes y si tiene misma feha, hora y patente
        retorna el viaje, sino returna null ya que no existe */
        for(Viaje viaje : viajes){
            if(viaje.getFecha().equals(fecha) && viaje.getHora().equals(hora) && viaje.getBus().getPatente().equals(patenteBus)){
                return viaje;
            }
        }
        return null;
    }
    //Metodo findPasajero: Diego Muñoz
    private Pasajero findPasajero(IdPersona idPersona){
        for (Pasajero pasajero:pasajeros){
            if (pasajero.getIdpersona().equals(idPersona)){
                return pasajero;
            }
        }
        return null;
    }
}

