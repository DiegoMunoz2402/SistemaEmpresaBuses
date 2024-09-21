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

    public boolean createBus(String patente, String marca, String modelo, int nroAsientos){
        if (findBus(patente) == null){
            Bus nuevoBus = new Bus(patente, marca, modelo, nroAsientos);
            buses.add(nuevoBus);
            return true;
        }
        return false;
    }

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus){
        //Debo preguntar por los tipos de datos a la ayudante
        if (findViaje(fecha, hora, patBus) == null){
            Viaje nuevoViaje = new Viaje(fecha,hora,precio, patBus);
            viajes.add(nuevoViaje);
            return true;
        }
        return false;
    }

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
           }
           if(venta==null){
            System.out.println("no se encontro la venta");
            return false;
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

    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus){
        Bus bus = findBus(patBus);
        String fechastr=fecha.toString();
        String horastr=hora.toString();
        if (bus!=null && !fechastr.isEmpty() && !horastr.isEmpty()){
            Viaje viaje = findViaje(fechastr,horastr,patBus);
            if (viaje!=null){
                String[][] pasajeros = viaje.getListaPasajeros();
                String[][] listPasajeros = new String[pasajeros.length][5];
                ArrayList<String> listString=new ArrayList<>();
                ArrayList<IdPersona> idPersonas = new ArrayList<>();
                for (int i = 0; i < pasajeros.length; i++) {
                    listString.add(viaje.getListaPasajeros()[i][0]);
                }
//                for (String id:listString){
//                    idPersonas.add(new IdPersona(id));
//                }
                //iteracion con for para llenar matriz
                for (int i = 0; i < listPasajeros.length; i++) {
                    //Pasajero pasajero = findPasajero()
                    //listPasajeros[i][0]= no puedo buscar asientos sin tener el pasajero como objeto...
                    listPasajeros[i][1]=pasajeros[i][0]; //Devuelve idPersona como String, asi que no puedo usar sus metodos
                    listPasajeros[i][2]=pasajeros[i][1];
                    listPasajeros[i][3]=pasajeros[i][2];
                    listPasajeros[i][4]=pasajeros[i][3];
                }
                return pasajeros;
            }else{
                System.out.println("Viaje no encontrado...");
            }
        }else{
            System.out.println("Bus no encontrado...");
        }
        return new String[0][0];
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
