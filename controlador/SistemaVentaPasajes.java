package controlador;

import excepciones.SVPException;
import modelo.*;
import persistencia.IOSVP;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
            throw new SVPException("Ya existe el cliente con el id indicado");
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
            throw new SVPException("Ya existe un pasajero con el id dado");
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
                                throw new SVPException("No existe terminal de llegada en la comuna indicada");
                            }
                        }else {
                            throw new SVPException("No existe terminal de salida en la comuna indicada");
                        }
                    }else {
                        throw new SVPException("No existe conductor con el id indicado en la empresa con el rut indicado");
                    }
                }else {
                    throw new SVPException("No existe auxiliar con el id indicado en la empresa con el rut indicado");
                }
            }else {
                throw new SVPException("No existe bus con la patente indicada");
            }
        }else {
            throw new SVPException("Ya existe viaje con fecha, hora y patente de bus indicados");
        }
    }

    //Iniciaventa : Javiera Duarte
    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaViaje, String comSalida, String comLlegada, IdPersona idCliente, int nroPasajes) throws SVPException {
        if (findVenta(idDoc, tipo).isPresent()) {
            throw new SVPException("*** Error: Ya existe una venta con el id indicado ***");
        }
        if (findCliente(idCliente).isEmpty()) {
            throw new SVPException("*** Error: No exste un cliente con el id indicado ***");
        }

        ArrayList<Viaje> viajesVenta = new ArrayList<>();
        if (ControladorEmpresas.getInstance().findTerminalPorComuna(comSalida).isEmpty()){
            throw new SVPException("* No se encontro el terminal de salida *");
        }
        if (ControladorEmpresas.getInstance().findTerminalPorComuna(comLlegada).isEmpty()){
            throw new SVPException("* No se encontro el terminal de llegada *");
        }
        if (nroPasajes<=0) {
            throw new SVPException("* La cantidad de pasajes a vender no puede ser 0 *");
        }
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fechaViaje) && viaje.getTerminalSalida().equals(ControladorEmpresas.getInstance().findTerminalPorComuna(comSalida).get()) && viaje.existeDisponibilidad(nroPasajes) && viaje.getTerminalLlegada().equals(ControladorEmpresas.getInstance().findTerminalPorComuna(comLlegada).get()))
                viajesVenta.add(viaje);
        }
        if (viajesVenta.isEmpty())
            throw new SVPException("*No existen viajes disponibles en la fecha y con terminales en las comunas de salida y llegada indicados *");

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
                          throw new SVPException("No existe un pasajero con el id dado");
                      }
                  } else {
                      throw new SVPException("No existe un viaje con los datos dados");
                  }
              }else {
                  throw new SVPException("No existe un bus con la patente indicada");
              }
          }else {
              throw new SVPException("No existe una venta con el idDocumento y tipo dados");
          }
      }
    //pagaVenta : Javiera Duarte
    public void pagaVenta(String idDocumento, TipoDocumento tipo) {
        Optional<Venta> ventaOptional = findVenta(idDocumento, tipo);

        if (ventaOptional.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }

        Venta venta = ventaOptional.get();

        boolean pagado = venta.pagaMonto();

        if (!pagado) {
            throw new SVPException("El pago no se efectuó (porque el pago ya se hizo)");
        }
    }
    //pagaVenta : Javiera Duarte
    public void pagaVenta(String idDocumento, TipoDocumento tipo, long nroTarjeta) {
        Optional<Venta> ventaOptional = findVenta(idDocumento, tipo);

        if (ventaOptional.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }

        Venta venta = ventaOptional.get();

        boolean pagado = venta.pagaMonto(nroTarjeta);

        if (!pagado) {
            throw new SVPException("El pago no se efectuó (porque el pago ya se hizo)");
        }
    }
    //listVenta : Javiera Duarte
     public String[][] listVentas(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String[][] listaVentas = new String[ventas.size()][7];
//        for(int i = 0; i < ventas.size(); i++) {
//            Venta venta = ventas.get(i);
//            listaVentas[i][0] = String.valueOf(venta.getIdDocumento());
//            listaVentas[i][1] = String.valueOf(venta.getTipo());
//            listaVentas[i][2] = venta.getFecha().format(formatter);
//            listaVentas[i][3] = String.valueOf(venta.getCliente().getIdpersona().toString());
//            listaVentas[i][4] = String.valueOf(venta.getCliente().getNombrecompleto());
//            listaVentas[i][5] = String.valueOf(venta.getPasajes().length);
//            listaVentas[i][6] = String.valueOf(venta.getMonto());
//        }
//        return listaVentas;
         return ventas.stream().map(venta -> new String[]{
                 String.valueOf(venta.getIdDocumento()),
                 String.valueOf(venta.getTipo()),
                 venta.getFecha().format(formatter),
                 String.valueOf(venta.getCliente().getIdpersona().toString()),
                 String.valueOf(venta.getCliente().getNombrecompleto()),
                 String.valueOf(venta.getPasajes().length),
                 String.valueOf(venta.getMonto())
         }).toArray(String[][]::new);
    }
    //Método listViajes : Diego Muñoz
    public String[][] listViajes(){
//        String[][] listaViajes = new String[viajes.size()][8];
//        for (int i = 0; i < viajes.size(); i++){
//            Viaje viaje = viajes.get(i);
//            LocalTime horaTermino = viaje.getFechaHoraTermino().toLocalTime();
//            listaViajes[i][0] = String.valueOf(viaje.getFecha());
//            listaViajes[i][1] = String.valueOf(viaje.getHora());
//            listaViajes[i][2] = String.valueOf(horaTermino);
//            listaViajes[i][3] = String.valueOf(viaje.getPrecio());
//            listaViajes[i][4] = String.valueOf(viaje.getNroAsientosDisponibles());
//            listaViajes[i][5] = viaje.getBus().getPatente();
//            listaViajes[i][6] = viaje.getTerminalSalida().getDireccion().getComuna();
//            listaViajes[i][7] = viaje.getTerminalLlegada().getDireccion().getComuna();
//        }
//        return listaViajes;
        return viajes.stream().map(viaje -> new String[]{
                String.valueOf(viaje.getFecha()),
                String.valueOf(viaje.getHora()),
                viaje.getFechaHoraTermino().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                String.valueOf(viaje.getPrecio()),
                String.valueOf(viaje.getNroAsientosDisponibles()),
                viaje.getBus().getPatente(),
                viaje.getTerminalSalida().getDireccion().getComuna(),
                viaje.getTerminalLlegada().getDireccion().getComuna()
        }).toArray(String[][]::new);
    }
    //Metodo listPasajeros: Diego Muñoz
    public String[][] listPasajerosViaje(LocalDate fecha, LocalTime hora, String patBus){

        Optional<Bus> bus = ControladorEmpresas.getInstance().findBus(patBus);
        if (bus.isPresent()){
            Optional<Viaje> viaje = findViaje(fecha,hora,patBus);
            if (viaje.isPresent()){
                String[][] pasajeros = viaje.get().getListaPasajeros();
                return Arrays.stream(pasajeros).map(pasajero -> new String[]{
                        pasajero[4],
                        pasajero[0],
                        pasajero[1],
                        pasajero[2],
                        pasajero[3]
                }).toArray(String[][]::new);
//                String[][] listPasajeros = new String[pasajeros.length][5];
//                //iteracion con for para llenar matriz
//                for (int i = 0; i < listPasajeros.length; i++) {
//                    listPasajeros[i][0]=pasajeros[i][4];
//                    listPasajeros[i][1]=pasajeros[i][0];
//                    listPasajeros[i][2]=pasajeros[i][1];
//                    listPasajeros[i][3]=pasajeros[i][2];
//                    listPasajeros[i][4]=pasajeros[i][3];
//                }
//                return listPasajeros;
            }
        }else {
            throw new SVPException("No existe viaje con la fecha, hora y patente de bus indicados");
        }
        return new String[0][0];
    }

    public void generatePasajesDeVenta(String idDocumento, TipoDocumento tipoDocumento)throws SVPException {
        Optional<Venta> venta = findVenta(idDocumento,tipoDocumento);
            if (venta.isPresent()){
                Pasaje[] pasajes = venta.get().getPasajes();
                String nombreArchivo = idDocumento + tipoDocumento.toString().toLowerCase()+".txt";
                IOSVP.getInstance().savePasajesDeVenta(pasajes,nombreArchivo);
            }else {
                throw new SVPException("No existe una venta con el id o tipo documento ingresado");
            }
    }

    public void readDatosSistema()throws SVPException {
        Object[] datos = IOSVP.getInstance().readControladores();
        ControladorEmpresas controladorEmpresas = (ControladorEmpresas) datos[1];
        instance = (SistemaVentaPasajes) datos[0];
        ControladorEmpresas.getInstance().setInstanciaPersistente(controladorEmpresas);
    }

    public void saveDatosSistema()throws SVPException {
        Object[] datos = {this,ControladorEmpresas.getInstance()};
        IOSVP.getInstance().saveControladores(datos);
        System.out.println("datos guardados exitosamente");
    }

    public void readDatosIniciales()throws SVPException {
        Object[] datos = IOSVP.getInstance().readDatosIniciales();
        if (datos.length>0){
            clientes.clear();
            pasajeros.clear();
            viajes.clear();
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
        return clientes.stream()
                .filter(cliente -> cliente.getIdpersona().equals(id))
                .findFirst();
    }
    //findVenta : Javiera Duarte
   private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento){
        return ventas.stream()
                .filter(venta -> venta.getIdDocumento().equals(idDocumento) && venta.getTipo().equals(tipoDocumento))
                .findFirst();

    }

    //Método findViaje : Diego Muñoz
    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus){
        return viajes.stream()
                .filter(viaje -> viaje.getFecha().equals(fecha) && viaje.getHora().equals(hora) && viaje.getBus().getPatente().equals(patenteBus))
                .findFirst();
    }
    //Metodo findPasajero: Diego Muñoz
    private Optional<Pasajero> findPasajero(IdPersona idPersona){
        return pasajeros.stream()
                .filter(pasajero -> pasajero.getIdpersona().equals(idPersona))
                .findFirst();
    }
}

