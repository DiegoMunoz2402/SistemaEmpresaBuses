package persistencia;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.IdPersona;
import utilidades.Rut;

import java.io.File;
import java.io.FileNotFoundException;
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

    public Object[] readDatosIniciales()throws SistemaVentaPasajesException {
        try {
            Scanner archivo = new Scanner(new File("SVPDatosIniciales.txt"));
            ArrayList<Persona> pasajerosYClientes = new ArrayList<>();
            archivo.useDelimiter("\r\n|;");

        } catch (FileNotFoundException e) {
            throw new SistemaVentaPasajesException("No existe o no se puede abrir el archivo SVPDatosIniciales.txt");
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
