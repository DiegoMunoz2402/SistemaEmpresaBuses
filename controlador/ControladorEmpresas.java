package controlador;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorEmpresas {
    private static ControladorEmpresas instance;

    private ArrayList<Terminal> terminales;
    private ArrayList<Bus> buses;
    private ArrayList<Empresa> empresas;

    public static ControladorEmpresas getInstance() {
        if (instance == null) {
            instance = new ControladorEmpresas();
        }
        return instance;
    }

    private ControladorEmpresas() {
        terminales = new ArrayList<>();
        buses = new ArrayList<>();
        empresas = new ArrayList<>();
    }

    //Metodo createEmpresa: Diego Muñoz
    public void createEmpresa(Rut rut, String nombre, String url) {
        if (findEmpresa(rut).isEmpty()) {
            Empresa empresa = new Empresa(rut, nombre);
            empresa.setUrl(url);
            empresas.add(empresa);
        } else {
            throw new SistemaVentaPasajesException("Ya existe una empresa con el rut indicado");
        }
    }

    //Metodo createBus: Rodolfo Salas
    public void createBus(String pat, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        Empresa emp = findEmpresa(rutEmp).orElseThrow(() -> new SistemaVentaPasajesException("No existe empresa con el rut indicado "));

        Optional<Bus> busExistente = findBus(pat);
        if (busExistente.isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe bus con la patente indicada");
        }
        Bus nuevoBus = new Bus(pat, marca, modelo, nroAsientos, emp);
        buses.add(nuevoBus);
    }


    //Metodo createTerminal: Diego Muñoz
    public void createTerminal(String nombre, Direccion direccion){
        if (findTerminalPorComuna(direccion.getComuna()).isEmpty()){
            if (findTerminal(nombre).isEmpty()){
                terminales.add(new Terminal(nombre,direccion));
            }else {
                throw new SistemaVentaPasajesException("Ya existe un terminar con el nombre dado");
            }
        }else {
            throw new SistemaVentaPasajesException("Ya existe un terminal en la comuna indicada");
        }
    }

    //Metodo hireConductorForEmpresa: Diego Muñoz
    public void hireConductorForEmpresa(Rut rutEmpresa, IdPersona idPersona, Nombre nombre, Direccion direccion){
        if (findEmpresa(rutEmpresa).isPresent()){
            Optional<Empresa> empresa = findEmpresa(rutEmpresa);
            if (!empresa.get().addConductor(idPersona,nombre,direccion)){
                throw new SistemaVentaPasajesException("Ya está contratado conductor con el id indicado en la empresa señalada");
            }
        }else {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }
    }

    //Metodo hireAuxiliarForEmpresa: Diego Muñoz
    public void hireAuxiliarForEmpresa(Rut rutEmpresa, IdPersona idPersona, Nombre nombre, Direccion direccion){
        if (findEmpresa(rutEmpresa).isPresent()){
            Optional<Empresa> empresa = findEmpresa(rutEmpresa);
            if (!empresa.get().addAuxiliar(idPersona, nombre, direccion)){
                throw new SistemaVentaPasajesException("Ya está contratado auxiliar con el id indicado en la empresa señalada");
            }
        }else {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }
    }

    //Metodo listEmpresas: Rodolfo Salas
    public String[][] listEmpresas(){
         if(empresas.isEmpty()){
             return new String[0][0];
         }

         String[][] listEmpresas= new String[empresas.size()][6];

         for (int i = 0; i < listEmpresas.length; i++){
             Empresa empresa = empresas.get(i);
             listEmpresas[i][0] = String.valueOf(empresa.getRut());
             listEmpresas[i][1] = String.valueOf(empresa.getNombre());
             listEmpresas[i][2] = empresa.getUrl();
             listEmpresas[i][3] = String.valueOf(empresa.getTripulantes().length);
             listEmpresas[i][4] = String.valueOf(empresa.getBuses().length);
             listEmpresas[i][5] = String.valueOf(empresa.getVentas().length);
         }
         return listEmpresas;

    }

    //Metodo listLlegadaSalidaTerminal: Diego Muñoz
    public String [][] listLlegadasSalidaTerminal(String nombre, LocalDate fecha){
        Optional<Terminal> terminal = findTerminal(nombre);
        if (terminal.isPresent()) {
            Viaje[] llegadas = terminal.get().getLlegadas();
            ArrayList<Viaje> llegadasFecha = new ArrayList<>();
            Viaje[] salidas = terminal.get().getSalidas();
            ArrayList<Viaje> salidasFecha = new ArrayList<>();
            String[][] listLlegadasSalidaTerminal;
            for (int i = 0; i < llegadas.length; i++) {
                if (llegadas[i].getFecha().equals(fecha)){
                    llegadasFecha.add(llegadas[i]);
                }
            }
            for (int j = 0; j < salidas.length; j++) {
                if (salidas[j].getFecha().equals(fecha)){
                    salidasFecha.add(salidas[j]);
                }
            }
            if (!salidasFecha.isEmpty() || !llegadasFecha.isEmpty()) {
                listLlegadasSalidaTerminal = new String[salidasFecha.size() + llegadasFecha.size()][5];
                if (!llegadasFecha.isEmpty()) {
                    for (int i = 0; i < llegadasFecha.size(); i++) {
                        listLlegadasSalidaTerminal[i][0] = "Llegada";
                        LocalDateTime dateTime = llegadasFecha.get(i).getFechaHoraTermino();
                        LocalTime time = dateTime.toLocalTime();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        listLlegadasSalidaTerminal[i][1] = time.format(formatter);
                        listLlegadasSalidaTerminal[i][2] = String.valueOf(llegadasFecha.get(i).getBus().getPatente());
                        listLlegadasSalidaTerminal[i][3] = llegadasFecha.get(i).getBus().getEmpresa().getNombre();
                        listLlegadasSalidaTerminal[i][4] = String.valueOf(llegadasFecha.get(i).getListaPasajeros().length);
                    }
                }
                if (!salidasFecha.isEmpty()) {
                    int j = 0;
                    for (int i = llegadasFecha.size(); i < salidasFecha.size(); i++) {
                        listLlegadasSalidaTerminal[i][0] = "Salida";
//                        LocalDateTime dateTime = salidasFecha.get(j).getFechaHoraTermino();
//                        LocalTime time = dateTime.toLocalTime();
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//                        LocalTime timeLlegada = LocalTime.of(salidasFecha.get(j).getFechaHoraTermino().getHour(),salidasFecha.get(j).getFechaHoraTermino().getMinute());
                        listLlegadasSalidaTerminal[i][1] = String.valueOf(salidasFecha.get(j).getHora());
                        listLlegadasSalidaTerminal[i][2] = String.valueOf(salidasFecha.get(j).getBus().getPatente());
                        listLlegadasSalidaTerminal[i][3] = salidasFecha.get(i).getBus().getEmpresa().getNombre();
                        listLlegadasSalidaTerminal[i][4] = String.valueOf(llegadasFecha.get(i).getListaPasajeros().length);
                        j++;
                    }
                }
                return listLlegadasSalidaTerminal;
            }
        }else {
            throw new SistemaVentaPasajesException("*** Error: No existe terminal con el nombre indicado ***");
        }
        return new String[0][0];
    }

    //listVentaEmpresa : Javiera Duarte
    public String[][] listVentasEmpresa(Rut rut) {

        Optional<Empresa> empresaOpt = findEmpresa(rut);
        if (empresaOpt.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        Empresa empresa = empresaOpt.get();
        List<Venta> ventas = List.of(empresa.getVentas());

        if (ventas.isEmpty()) {
            return new String[0][0];
        }

        String[][] listVentasTerminal = new String[ventas.size()][4];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            listVentasTerminal[i][0] = venta.getFecha().format(formatter);
            listVentasTerminal[i][1] = venta.getTipo().toString().toLowerCase();
            listVentasTerminal[i][2] = String.valueOf(venta.getMonto());
            listVentasTerminal[i][3] = venta.getTipoPago();
        }
        return listVentasTerminal;

    }

    protected void setDatosIniciales(Object[] objetos){
        for (Object object : objetos) {
            switch (object.getClass().getSimpleName()) {
                case "Terminale" -> terminales.add((Terminal) object);
                case "Bus" -> buses.add((Bus) object);
                case "Empresa" -> empresas.add((Empresa) object);
            }
        }
    }

    //Metodo findempresa: Diego Muñoz
    protected Optional<Empresa> findEmpresa(Rut rut){
        for (Empresa empresa:empresas){
            if (empresa.getRut().equals(rut)){
                return Optional.of(empresa);
            }
        }
        return Optional.empty();
    }
    //Metodo findTerminal: Diego Muñoz
    protected Optional<Terminal> findTerminal(String nombre){
        for (Terminal terminal:terminales){
            if (terminal.getNombre().equals(nombre)){
                return Optional.of(terminal);
            }
        }
        return Optional.empty();
    }
    //Metodo findTerminalPorComuna: Diego Muñoz
    protected Optional<Terminal> findTerminalPorComuna(String comuna){
        for (Terminal terminal:terminales){
            if (terminal.getDireccion().getComuna().equals(comuna)){
                return Optional.of(terminal);
            }
        }
        return Optional.empty();
    }
    //Metodo findBus: Diego Muñoz
    protected Optional<Bus> findBus(String patente){
        for (Bus bus:buses){
            if (bus.getPatente().equals(patente)){
                return Optional.of(bus);
            }
        }
        return Optional.empty();
    }
    //Metodo findConductor: Diego Muñoz
    protected Optional<Conductor> findConductor(IdPersona idPersona, Rut rutEmpresa){
        for (Empresa empresa: empresas){
            if (empresa.getRut().equals(rutEmpresa)){
                Tripulante[] conductors = empresa.getTripulantes();
                for (int i = 0; i < conductors.length; i++) {
                    if (conductors[i].getIdpersona().equals(idPersona)){
                        return Optional.of((Conductor) conductors[i]);
                    }
                }
            }
        }
        return Optional.empty();
    }
    //Metodo findAuxiliar: Diego Muñoz
    protected Optional<Auxiliar> findAuxliar(IdPersona idPersona, Rut rutEmpresa){
        for (Empresa empresa:empresas){
            if (empresa.getRut().equals(rutEmpresa)){
                Tripulante[] auxiliares = empresa.getTripulantes();
                for (int i = 0; i < auxiliares.length; i++) {
                    if (auxiliares[i].getIdpersona().equals(idPersona)){
                        return Optional.of((Auxiliar) auxiliares[i]);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
