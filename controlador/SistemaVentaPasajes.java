package controlador;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class SistemaVentaPasajes  {

    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Pasajero> pasajeros = new ArrayList<>();
    private ArrayList<Venta> ventas = new ArrayList<>();
    private ArrayList<Viaje> viajes = new ArrayList<>();

    private static SistemaVentaPasajes instance;

    private SistemaVentaPasajes(){

    }

    public static SistemaVentaPasajes getInstance(){
        if (instance == null){
            instance = new SistemaVentaPasajes();
        }
        return instance;
    }

   // metodo createCliente: Rodolfo Salas
    public void createCliente(IdPersona id, Nombre nom, String fono, String email){
        if(findCliente(id).isPresent()){
            throw new SistemaVentaPasajesException("Ya existe el cliente con el id indicado");
        }
        Cliente nuevoCliente= new Cliente(id,nom,email);
        nuevoCliente.setTelefono(fono);
        clientes.add(nuevoCliente);
    }
    //Metodo createPasajero: Diego Muñoz
    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto){
        Optional<Pasajero> pasajero = findPasajero(id);
        if (pasajero.isEmpty()){
            Pasajero pasajeroNew = new Pasajero(id,nom,nomContacto,fonoContacto);
            pasajeroNew.setTelefono(fono);
            pasajeros.add(pasajeroNew);
        }else {
            throw new SistemaVentaPasajesException("Ya existe un pasajero con el id dado");
        }
    }

    //Método createViaje: Diego Muñoz
    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patBus, IdPersona[] idTripulantes, String[] nomComunas){
        Optional<Viaje> viaje = findViaje(fecha,hora,patBus);
        if (viaje.isEmpty()){
            Optional<Bus> bus = ControladorEmpresas.getInstance().findBus(patBus);
            if (bus.isPresent()){
                Optional<Auxiliar> auxiliar = ControladorEmpresas.getInstance().findAuxliar(idTripulantes[0],bus.get().getEmpresa().getRut());
                if (auxiliar.isPresent()){
                    Optional<Conductor> conductor = ControladorEmpresas.getInstance().findConductor(idTripulantes[1],bus.get().getEmpresa().getRut());
                    if (conductor.isPresent()){
                        Optional<Terminal> salida = ControladorEmpresas.getInstance().findTerminalPorComuna(nomComunas[0]);
                        if (salida.isPresent()){
                            Optional<Terminal> llegada = ControladorEmpresas.getInstance().findTerminalPorComuna(nomComunas[1]);
                            if (llegada.isPresent()){
                                Viaje viajeValido = new Viaje(fecha,hora,precio,duracion,bus.get(),auxiliar.get(),conductor.get(),salida.get(),llegada.get());
                                if (idTripulantes.length>2){
                                    Optional<Conductor> conductor2 = ControladorEmpresas.getInstance().findConductor(idTripulantes[2],bus.get().getEmpresa().getRut());
                                    viajeValido.addConductor(conductor2.get());
                                }
                                viajes.add(viajeValido);
                            }else {
                                throw new SistemaVentaPasajesException("No existe terminal de llegada en la comuna indicada");
                            }
                        }else {
                            throw new SistemaVentaPasajesException("No existe terminal de salida en la comuna indicada");
                        }
                    }else {
                        throw new SistemaVentaPasajesException("No existe conductor con el id indicado en la empresa con el rut indicado");
                    }
                }else {
                    throw new SistemaVentaPasajesException("No existe auxiliar con el id indicado en la empresa con el rut indicado");
                }
            }else {
                throw new SistemaVentaPasajesException("No existe bus con la patente indicada");
            }
        }else {
            throw new SistemaVentaPasajesException("Ya existe viaje con fecha, hora y patente de bus indicados");
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
    public String[] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){
        Optional<Bus> bus = ControladorEmpresas.getInstance().findBus(patBus);
        if (bus.isPresent()) {
            Optional<Viaje> viaje = findViaje(fecha, hora, patBus);
            if (viaje.isPresent()){
                String[] listAsientosViaje=new String[viaje.get().getAsientos().length];
                for (int i = 0; i < listAsientosViaje.length; i++) {
                    listAsientosViaje[i]=viaje.get().getAsientos()[i];
                }
                return listAsientosViaje;
            }
        }
        return new String[0];
    }

     public int getMontoVenta(String idDocumento, TipoDocumento tipo){

        Venta venta = findVenta(idDocumento, tipo);
        if (venta!= null){
            return venta.getMonto();
        }
        return 0;
    }
    //Metodo getNombrePasajero: Diego Muñoz
    public Optional<String> getNombrePasajero(IdPersona idPasajero){
        Optional<Pasajero> pasajero = findPasajero(idPasajero);
        if (pasajero.isPresent()){
            return Optional.of(pasajero.get().getNombrecompleto().toString());
        }
        return Optional.empty();
    }
      // metodo vendePasaje: Rodolfo Salas
      //Metodo vendePasaje: Rodolfo Salas
      public void vendePasaje(String idDoc, TipoDocumento tipo, LocalDate fechaViaje,LocalTime hora,String patBus,int asiento,IdPersona idPasajero){
          Optional<Venta> venta = findVenta(idDoc,tipo);
          Optional<Pasajero> pasajero= findPasajero(idPasajero);
          Optional<Viaje> viaje = findViaje(fechaViaje,hora,patBus);
          if (venta.isPresent()){
              Optional<Bus> bus = ControladorEmpresas.getInstance().findBus(patBus);
              if (bus.isPresent()) {
                  if (viaje.isPresent()) {
                      if (pasajero.isPresent()) {
                          venta.get().createPasaje(asiento, viaje.get(), pasajero.get());
                      } else {
                          throw new SistemaVentaPasajesException("No existe un pasajero con el id dado");
                      }
                  } else {
                      throw new SistemaVentaPasajesException("No existe un viaje con los datos dados");
                  }
              }else {
                  throw new SistemaVentaPasajesException("No existe un bus con la patente indicada");
              }
          }else {
              throw new SistemaVentaPasajesException("No existe una venta con el idDocumento y tipo dados");
          }
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
    //Método listViajes : Diego Muñoz
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

        Optional<Bus> bus = ControladorEmpresas.getInstance().findBus(patBus);
        if (bus.isPresent()){
            Optional<Viaje> viaje = findViaje(fecha,hora,patBus);
            if (viaje.isPresent()){
                String[][] pasajeros = viaje.get().getListaPasajeros();
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
        }else {
            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente de bus indicados");
        }
        return new String[0][0];
    }
       //metodo findCliente: Rodolfo Salas
   private Optional<Cliente> findCliente(IdPersona id){
        for (Cliente cliente:clientes){
            if (cliente.getIdpersona().equals(id)){
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

   private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento){
        for(Venta venta: ventas){
            if(venta.getIdDocumento().equals(idDocumento) && venta.getTipo().equals(tipoDocumento)){
                return Optional.of(venta);
            } }
        return Optional.empty();

    }

    //Método findViaje : Diego Muñoz
    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus){
        /*Busco en la lista de viajes y si tiene misma feha, hora y patente
        retorna el viaje, sino returna null ya que no existe */
        for(Viaje viaje : viajes){
            if(viaje.getFecha().equals(fecha) && viaje.getHora().equals(hora) && viaje.getBus().getPatente().equals(patenteBus)){
                return Optional.of(viaje);
            }
        }
        return Optional.empty();
    }
    //Metodo findPasajero: Diego Muñoz
    private Optional<Pasajero> findPasajero(IdPersona idPersona){
        for (Pasajero pasajero:pasajeros){
            if (pasajero.getIdpersona().equals(idPersona)){
                return Optional.of(pasajero);
            }
        }
        return Optional.empty();
    }
}

