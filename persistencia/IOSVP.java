//Class IOSVP: Diego Muñoz
package persistencia;

import excepciones.SVPException;
import modelo.*;
import utilidades.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IOSVP {
    private static IOSVP instance;

    public static IOSVP getInstance() {
        if (instance == null){
            instance = new IOSVP();
        }
        return instance;
    }

    private IOSVP(){
    }

    //Metodo readDatosIniciales: Diego Muñoz
    public Object[] readDatosIniciales()throws SVPException {
        try {
            Scanner archivo = new Scanner(new File("SVPDatosIniciales.txt"));
            ArrayList<Persona> pasajerosYClientes = new ArrayList<>();
            archivo.useDelimiter("\r\n|;");
            String tipo = archivo.next();
            while (!tipo.equals("+")){
                Rut rut = Rut.of(archivo.next());
                Tratamiento tratamiento = Tratamiento.valueOf(archivo.next().toUpperCase());
                String nombres = archivo.next();
                String apPaterno = archivo.next();
                String apMaterno = archivo.next();
                String telefono = archivo.next();
                Nombre nombre = new Nombre(nombres,apPaterno,apMaterno,tratamiento);
                String email = "";
                if (tipo.equals("C")){
                    email = archivo.next();
                    Cliente cliente = new Cliente(rut,nombre,email);
                    cliente.setTelefono(telefono);
                    pasajerosYClientes.add(cliente);
                }else if (tipo.equals("P")){
                    Tratamiento tratamientoContacto = Tratamiento.valueOf(archivo.next());
                    String nombresContacto = archivo.next();
                    String apPaternoContacto = archivo.next();
                    String apMaternoContacto = archivo.next();
                    String telefonoContacto = archivo.next();
                    Nombre nombreContacto = new Nombre(nombresContacto,apPaternoContacto,apMaternoContacto,tratamientoContacto);
                    Pasajero pasajero = new Pasajero(rut,nombre,nombreContacto,telefono);
                    pasajero.setFonoContacto(telefonoContacto);
                    pasajerosYClientes.add(pasajero);
                }else if (tipo.equals("CP")){
                    email = archivo.next();
                    Cliente cliente = new Cliente(rut,nombre,email);
                    cliente.setTelefono(telefono);
                    pasajerosYClientes.add(cliente);
                    Tratamiento tratamientoContacto = Tratamiento.valueOf(archivo.next());
                    String nombresContacto = archivo.next();
                    String apPaternoContacto = archivo.next();
                    String apMaternoContacto = archivo.next();
                    String telefonoContacto = archivo.next();
                    Nombre nombreContacto = new Nombre(nombresContacto,apPaternoContacto,apMaternoContacto,tratamientoContacto);
                    Pasajero pasajero = new Pasajero(rut,nombre,nombreContacto,telefono);
                    pasajero.setFonoContacto(telefonoContacto);
                    pasajerosYClientes.add(pasajero);
                }
                tipo = archivo.next();
            }
            List<Object> datos = new ArrayList<>(pasajerosYClientes);
            List<Empresa> empresas = new ArrayList<>();

            String rut = archivo.next();
            while (!rut.equals("+")){
                String nombreEmpresa = archivo.next();
                String url = archivo.next();
                Rut rutFormat = Rut.of(rut);
//                if (rutFormat == null) {
//                    throw new SistemaVentaPasajesException("El RUT de la empresa no es válido: " + rut);
//                }
                Empresa empresa = new Empresa(rutFormat,nombreEmpresa);
                empresa.setUrl(url);
                empresas.add(empresa);
                rut = archivo.next();
            }

            datos.addAll(empresas);
            List<Tripulante> tripulantes = new ArrayList<>();

            String tipoTripulante = archivo.next();
            while (!tipoTripulante.equals("+")){
                String rutTripulante = archivo.next();
                Rut rutTripulanteFormat = Rut.of(rutTripulante);
                Tratamiento tratamiento = Tratamiento.valueOf(archivo.next().toUpperCase());
                String nombres = archivo.next();
                String apPaterno = archivo.next();
                String apMaterno = archivo.next();
                Nombre nombre = new Nombre(nombres,apPaterno, apMaterno,tratamiento);
                String calle = archivo.next();
                int numero = archivo.nextInt();
                String nombreComuna = archivo.next();
                Rut rutEmpresa = Rut.of(archivo.next());
                Optional<Empresa> empresa = findEmpresa(empresas,rutEmpresa);
                Direccion direccion = new Direccion(calle,numero,nombreComuna);
                if (empresa.isPresent()) {
                    if (tipoTripulante.equals("A")) {
                        if (!empresa.get().addAuxiliar(rutTripulanteFormat, nombre, direccion)) {
                            throw new SVPException("No fue posible añadir el auxiliar");
                        }
                    } else{
                        if (!empresa.get().addConductor(rutTripulanteFormat, nombre, direccion)) {
                            throw new SVPException("No fue posible añadir el auxiliar");
                        }
                    }
                }
                tipoTripulante = archivo.next();

            }

            datos.addAll(tripulantes);
            List<Terminal> terminales = new ArrayList<>();

            String nombreTerminal = archivo.next();
            while (!nombreTerminal.equals("+")){
                String calle = archivo.next();
                int numero = archivo.nextInt();
                String comuna = archivo.next();
                Direccion direccion = new Direccion(calle,numero,comuna);
                Terminal terminal = new Terminal(nombreTerminal,direccion);
                terminales.add(terminal);
                nombreTerminal = archivo.next();
            }

            datos.addAll(terminales);
            List<Bus> buses = new ArrayList<>();

            String patente = archivo.next();
            while (!patente.equals("+")){
                String marca = archivo.next();
                String modelo = archivo.next();
                int nroAsientos = archivo.nextInt();
                Rut rutEmpresa = Rut.of(archivo.next());
                Optional<Empresa> empresa = findEmpresa(empresas,rutEmpresa);
                if (empresa.isPresent()){
                    Bus bus = new Bus(patente,marca,modelo,nroAsientos,empresa.get());
                    buses.add(bus);
                }
                patente = archivo.next();
            }

            datos.addAll(buses);
            List<Viaje> viajes = new ArrayList<>();

            while (archivo.hasNext()){
                LocalDate fecha = LocalDate.parse(archivo.next(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime hora = LocalTime.parse(archivo.next(), DateTimeFormatter.ofPattern("HH:mm"));
                int precio = archivo.nextInt();
                int duracion = archivo.nextInt();
                String patenteBus = archivo.next();
                Optional<Bus> bus = findBus(buses,patenteBus);
                if (bus.isPresent()){
                    String rutAuxiliar = archivo.next();
                    IdPersona rutAux = Rut.of(rutAuxiliar);
                    Optional<Tripulante> auxiliar = findTripulante(bus.get().getEmpresa(),rutAux);
                    if (auxiliar.isPresent()){
                        String rutConductor = archivo.next();
                        IdPersona idConductor = Rut.of(rutConductor);
                        Optional<Tripulante> conductor = findTripulante(bus.get().getEmpresa(),idConductor);
                        if (conductor.isPresent()){
                            Optional<Terminal> terminalSalida = findTerminal(terminales,archivo.next());
                            Optional<Terminal> terminalLlegada = findTerminal(terminales,archivo.next());
                            if (terminalSalida.isPresent() && terminalLlegada.isPresent()){
                                viajes.add(new Viaje(fecha,hora,precio,duracion,bus.get(), (Auxiliar) auxiliar.get(),(Conductor) conductor.get(),terminalSalida.get(),terminalLlegada.get()));
                            }
                        }
                    }
                }
            }
            datos.addAll(viajes);
            archivo.close();
            return datos.toArray(new Object[0]);
        } catch (FileNotFoundException e) {
            throw new SVPException("No existe o no se puede abrir el archivo SVPDatosIniciales.txt");
        }
    }

    //Debo hacer una validacion para guardar, si es ControladorEmpresas o SVP??? o los guardo directamente
    public void saveControladores(Object[] controladores)throws SVPException {
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream("SVPObjetos.obj"));
            for (Object object:controladores){
                archivo.writeObject(object);
            }
            archivo.close();
        }catch (FileNotFoundException e) {
            throw new SVPException("No se puede abrir o crear el archivo SVPObjetos.obj");
        }catch (IOException e) {
            throw new SVPException("No se puede grabar en el archivo SVPObjetos.obj");
        }
    }

    //Duda con la implementacion
    public Object[] readControladores()throws SVPException {
        ObjectInputStream archivo = null;
        List<Object> datos = new ArrayList<>();
        try {
            archivo = new ObjectInputStream(new FileInputStream("SVPObjetos.obj"));
            while (true){
                datos.add(archivo.readObject());
            }
        }catch (EOFException e){
            assert archivo!=null;
            try {
                archivo.close();
            } catch (IOException ex) {
                throw new SVPException("No se puede cerrar el archivo SVPObjetos.obj");
            }
            return datos.toArray(new Object[0]);
        }catch (FileNotFoundException e){
            throw new SVPException("No existe o no se puede abrir el archivo SVPObjetos.obj");
        }catch (ClassNotFoundException|IOException e){
            throw new SVPException("No se puede leer el archivo SVPObjetos.obj");
        }
    }

    public void savePasajesDeVenta(Pasaje[] pasajes,String nombreArchivo)throws SVPException {
        try {
            PrintStream boletas = new PrintStream(new File(nombreArchivo));
            for (int i = 0; i < pasajes.length; i++) {
                boletas.println(pasajes[i].toString() + "\n");
            }
            boletas.close();
        } catch (FileNotFoundException e) {
            throw new SVPException("Error, no se puede abrir o crear el archivo "+nombreArchivo);
        }
    }

    private Optional<Empresa> findEmpresa(List<Empresa> empresa, Rut rut){
        return empresa.stream().filter(empresa1 -> empresa1.getRut().equals(rut)).findFirst();
    }

    private Optional<Tripulante> findTripulante(Empresa empresa, IdPersona idPersona){
        Tripulante[] tripulantes = empresa.getTripulantes();
        return Arrays.stream(tripulantes).filter(tripulante -> tripulante.getIdpersona().equals(idPersona)).findFirst();
    }

    private Optional<Bus> findBus(List<Bus> buses, String patente){
        return buses.stream().filter(bus -> bus.getPatente().equals(patente)).findFirst();
    }

    private Optional<Terminal> findTerminal(List<Terminal> terminales, String nombre){
        return terminales.stream().filter(terminal -> terminal.getNombre().equals(nombre)).findFirst();
    }
}
