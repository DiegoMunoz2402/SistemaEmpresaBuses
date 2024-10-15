package controlador;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class ControladorEmpresas {
    private static ControladorEmpresas instance;

    private ArrayList<Terminal> terminales;
    private ArrayList<Bus> buses;
    private ArrayList<Empresa> empresas;

    public static ControladorEmpresas getInstance() {
        if (instance == null){
            instance = new ControladorEmpresas();
        }
        return instance;
    }

    private ControladorEmpresas(){
        terminales = new ArrayList<>();
        buses = new ArrayList<>();
        empresas = new ArrayList<>();
    }

    public void createEmpresa(Rut rut, String nombre, String url){
        if (findEmpresa(rut).isEmpty()){
            Empresa empresa = new Empresa(rut,nombre);
            empresa.setUrl(url);
            empresas.add(empresa);
        }else {
            throw new SistemaVentaPasajesException("Ya existe una empresa con el rut indicado");
        }
    }

    public void createTerminal(String nombre, Direccion direccion){
        if (findTerminalPorComuna(direccion.getComuna()).isEmpty()){
            if (findTerminal(nombre).isEmpty(nombre)){
                terminales.add(new Terminal(nombre,direccion));
            }else {
                throw new SistemaVentaPasajesException("Ya existe un terminar con el nombre dado");
            }
        }else {
            throw new SistemaVentaPasajesException("Ya existe un terminal en la comuna indicada");
        }
    }

    //Consultar mañana a la profesora
    public void hireConductorForEmpresa(Rut rutEmpresa, IdPersona idPersona, Nombre nombre, Direccion direccion){
        if (findEmpresa(rutEmpresa).isPresent()){
            Optional<Empresa> empresa = findEmpresa(rutEmpresa);
            if (empresa.get().addConductor(idPersona,nombre,direccion)){

            }else {
                throw new SistemaVentaPasajesException("Ya está contratado conductor con el id indicado en la empresa señalada");
            }
        }else {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }
    }

    public void hireAuxiliarForEmpresa(Rut rutEmpresa, IdPersona idPersona, Nombre nombre, Direccion direccion){
        if (findEmpresa(rutEmpresa).isPresent()){
            Optional<Empresa> empresa = findEmpresa(rutEmpresa);
            if (empresa.get().addAuxiliar(idPersona, nombre, direccion)){

            }else {
                throw new SistemaVentaPasajesException("Ya está contratado auxiliar con el id indicado en la empresa señalada");
            }
        }else {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }
    }

    //Metodo sin resvoler, preguntar a la profe mañana
    public String [][] listLlegadasSalidaTerminal(String nombre, LocalDate fecha){
        Optional<Terminal> terminal = findTerminal(nombre);
        if (terminal.isPresent()) {
            ArrayList<Viaje> viajesPorFecha = new ArrayList<>();
            Viaje[] llegadas = terminal.get().getLlegadas();
            Viaje[] salidas = terminal.get().getSalidas();
            String[][] listLlegadasSalidaTerminal;
            for (int i = 0; i < llegadas.length; i++) {
                viajesPorFecha.add(llegadas[i]);
            }
            for (int i = 0; i < salidas.length; i++) {
                viajesPorFecha.add(salidas[i]);
            }
            if (!viajesPorFecha.isEmpty()) {
                Viaje[] viajesArr = viajesPorFecha.toArray(new Viaje[0]);
                listLlegadasSalidaTerminal = new String[viajesArr.length][5];
                for (int i = 0; i < listLlegadasSalidaTerminal.length; i++) {
                    if (llegadas[i].getFecha().equals(fecha)) {

                    }
                }
            }
        }else {
            throw new SistemaVentaPasajesException("No existe terminal con el nombre indicado");
        }
    }

    protected Optional<Empresa> findEmpresa(Rut rut){
        for (Empresa empresa:empresas){
            if (empresa.getRut().equals(rut)){
                return Optional.of(empresa);
            }
        }
        return Optional.empty();
    }

    protected Optional<Terminal> findTerminal(String nombre){
        for (Terminal terminal:terminales){
            if (terminal.getNombre().equals(nombre)){
                return Optional.of(terminal);
            }
        }
        return Optional.empty();
    }

    protected Optional<Terminal> findTerminalPorComuna(String comuna){
        for (Terminal terminal:terminales){
            if (terminal.getDireccion().getComuna().equals(comuna)){
                return Optional.of(terminal);
            }
        }
        return Optional.empty();
    }

    protected Optional<Bus> findBus(String patente){
        for (Bus bus:buses){
            if (bus.getPatente().equals(patente)){
                return Optional.of(bus);
            }
        }
        return Optional.empty();
    }

    protected Optional<Conductor> findConductor(IdPersona idPersona, Rut rutEmpresa){
        for (Empresa empresa: empresas){
            if (empresa.getRut().equals(rutEmpresa)){
                Tripulante[] conductors = empresa.getTripulantes();
                for (int i = 0; i < conductors.length; i++) {
                    if (conductors[i].getIdpersona().equals(idPersona)){
                        return Optional.of(conductors[i]);
                    }
                }
            }
        }
        return Optional.empty();
    }

    protected Optional<Auxiliar> findAuxliar(IdPersona idPersona, Rut rutEmpresa){
        for (Empresa empresa:empresas){
            if (empresa.getRut().equals(rutEmpresa)){
                Tripulantes[] auxiliares = empresa.getTripulantes();
                for (int i = 0; i < auxiliares.length; i++) {
                    if (auxiliares[i].getIdpersona().equals(idPersona)){
                        return Optional.of(auxiliares[i]);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
