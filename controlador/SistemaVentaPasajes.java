package controlador;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import persistencia.IOSVP;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaVentaPasajes implements Serializable {

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
                                if (idTripulantes.length>=3){
                                    Optional<Conductor> conductor2 = ControladorEmpresas.getInstance().findConductor(idTripulantes[2],bus.get().getEmpresa().getRut());
                                    if (conductor2.isPresent()) {
                                        viajeValido.addConductor(conductor2.get());
                                    }
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

    //Iniciaventa : Javiera Duarte
    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaViaje, String comSalida, String comLlegada, IdPersona idCliente, int nroPasajes) throws SistemaVentaPasajesException {
        if (findVenta(idDoc, tipo).isPresent()) {
            throw new SistemaVentaPasajesException("*** Error: Ya existe una venta con el id indicado ***");
        }
        if (findCliente(idCliente).isEmpty()) {
            throw new SistemaVentaPasajesException("*** Error: No exste un cliente con el id indicado ***");
        }

        ArrayList<Viaje> viajesVenta = new ArrayList<>();
        if (ControladorEmpresas.getInstance().findTerminalPorComuna(comSalida).isEmpty()){
            throw new SistemaVentaPasajesException("* No se encontro el terminal de salida *");
        }
        if (ControladorEmpresas.getInstance().findTerminalPorComuna(comLlegada).isEmpty()){
            throw new SistemaVentaPasajesException("* No se encontro el terminal de llegada *");
        }
        if (nroPasajes<=0) {
            throw new SistemaVentaPasajesException("* La cantidad de pasajes a vender no puede ser 0 *");
        }
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fechaViaje) && viaje.getTerminalSalida().equals(ControladorEmpresas.getInstance().findTerminalPorComuna(comSalida).get()) && viaje.existeDisponibilidad(nroPasajes) && viaje.getTerminalLlegada().equals(ControladorEmpresas.getInstance().findTerminalPorComuna(comLlegada).get()))
                viajesVenta.add(viaje);
        }
        if (viajesVenta.isEmpty())
            throw new SistemaVentaPasajesException("*No existen viajes disponibles en la fecha y con terminales en las comunas de salida y llegada indicados *");

        ventas.add(new Venta(idDoc, tipo, fechaViaje, findCliente(idCliente).get()));
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

    //Metodo getNombrePasajero: Diego Muñoz
    public Optional<String> getNombrePasajero(IdPersona idPasajero){
        Optional<Pasajero> pasajero = findPasajero(idPasajero);
        if (pasajero.isPresent()){
            return Optional.of(pasajero.get().getNombrecompleto().toString());
        }
        return Optional.empty();
    }
    //getMontoVenta: Javiera Duarte
    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipo){
        Optional<Venta> venta = findVenta(idDocumento, tipo);
        if (venta.isPresent()){
            return Optional.of(venta.get().getMonto());
        }
        return Optional.empty();
    }
    
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
    //pagaVenta : Javiera Duarte
    public void pagaVenta(String idDocumento, TipoDocumento tipo) {
        Optional<Venta> ventaOptional = findVenta(idDocumento, tipo);

        if (ventaOptional.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }

        Venta venta = ventaOptional.get();

        boolean pagado = venta.pagaMonto();

        if (!pagado) {
            throw new SistemaVentaPasajesException("El pago no se efectuó (porque el pago ya se hizo)");
        }
    }
    //pagaVenta : Javiera Duarte
    public void pagaVenta(String idDocumento, TipoDocumento tipo, long nroTarjeta) {
        Optional<Venta> ventaOptional = findVenta(idDocumento, tipo);

        if (ventaOptional.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe venta con el id y tipo de documento indicados");
        }

        Venta venta = ventaOptional.get();

        boolean pagado = venta.pagaMonto(nroTarjeta);

        if (!pagado) {
            throw new SistemaVentaPasajesException("El pago no se efectuó (porque el pago ya se hizo)");
        }
    }
    //listVenta : Javiera Duarte
     public String[][] listVentas(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String[][] listaVentas = new String[ventas.size()][7];
        for(int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            listaVentas[i][0] = String.valueOf(venta.getIdDocumento());
            listaVentas[i][1] = String.valueOf(venta.getTipo());
            listaVentas[i][2] = venta.getFecha().format(formatter);
            listaVentas[i][3] = String.valueOf(venta.getCliente().getIdpersona().toString());
            listaVentas[i][4] = String.valueOf(venta.getCliente().getNombrecompleto());
            listaVentas[i][5] = String.valueOf(venta.getPasajes().length);
            listaVentas[i][6] = String.valueOf(venta.getMonto());
        }
        return listaVentas;

    }
    //Método listViajes : Diego Muñoz
    public String[][] listViajes(){



        String[][] listaViajes = new String[viajes.size()][8];
        for (int i = 0; i < viajes.size(); i++){
            Viaje viaje = viajes.get(i);
            LocalTime horaTermino = viaje.getFechaHoraTermino().toLocalTime();
            listaViajes[i][0] = String.valueOf(viaje.getFecha());
            listaViajes[i][1] = String.valueOf(viaje.getHora());
            listaViajes[i][2] = String.valueOf(horaTermino);
            listaViajes[i][3] = String.valueOf(viaje.getPrecio());
            listaViajes[i][4] = String.valueOf(viaje.getNroAsientosDisponibles());
            listaViajes[i][5] = viaje.getBus().getPatente();
            listaViajes[i][6] = viaje.getTerminalSalida().getDireccion().getComuna();
            listaViajes[i][7] = viaje.getTerminalLlegada().getDireccion().getComuna();
        }
        return listaViajes;
    }
    //Metodo listPasajeros: Diego Muñoz
    public String[][] listPasajerosViaje(LocalDate fecha, LocalTime hora, String patBus){

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
                return listPasajeros;
            }
        }else {
            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente de bus indicados");
        }
        return new String[0][0];
    }

    public void saveDatosSistema()throws SistemaVentaPasajesException{
        List<Object> datos = new ArrayList<>();
        datos.add(this);
        datos.add(ControladorEmpresas.getInstance());
        IOSVP.getInstance().saveControladores(datos.toArray());
    }

    public void readDatosIniciales()throws SistemaVentaPasajesException{
        Object[] datos = IOSVP.getInstance().readDatosIniciales();
        if (datos.length>0){
            for (Object object:datos){
                switch (object.getClass().getSimpleName()){
                    case "Cliente" -> clientes.add((Cliente) object);
                    case "Pasajero" -> pasajeros.add((Pasajero) object);
                    case "Viaje" -> viajes.add((Viaje) object);
                }
            }
            ControladorEmpresas.getInstance().setDatosIniciales(datos);
        }
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
    //findVenta : Javiera Duarte
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

