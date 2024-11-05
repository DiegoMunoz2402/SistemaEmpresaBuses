package vista;

import controlador.ControladorEmpresas;
import controlador.SistemaVentaPasajes;
import excepciones.SistemaVentaPasajesException;
import utilidades.Direccion;
import modelo.TipoDocumento;
import utilidades.IdPersona;
import utilidades.Pasaporte;
import utilidades.Rut;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UISVP {
    private Scanner sc;

    private static UISVP instance;

    private UISVP(){
        sc = new Scanner(System.in);
        sc.useDelimiter("[ \\t\\r\\n]+");
    }

    public static UISVP getInstance(){
        if (instance==null){
            instance = new UISVP();
        }
        return instance;
    }

    public void menu() {
        int op;
        do {
            System.out.println("======================");
            System.out.println("...::: Menú principal :::..");
            System.out.println();
            System.out.println("  1) Crear empresa");
            System.out.println("  2) Contratar tripulante");
            System.out.println("  3) Crear terminal");
            System.out.println("  4) Crear cliente");
            System.out.println("  5) Crear bus");
            System.out.println("  6) Crear viaje");
            System.out.println("  7) Vender pasajes");
            System.out.println("  8) Listar ventas");
            System.out.println("  9) Listar viajes");
            System.out.println("  10) Listar pasajeros de viaje");
            System.out.println("  11) Listar empresas");
            System.out.println("  12) Listar llegadas/salidas de terminal");
            System.out.println("  13) Listar ventas de empresa");
            System.out.println("  14) Salir");
            System.out.println("----------------------");
            System.out.println();
            System.out.print("..:: Ingrese número de opcion: ");
            op = sc.nextInt();
            switch (op) {
                case 1 -> crearEmpresa();
                case 2 -> contratarTripulante();
                case 3 -> crearTerminal();
                case 4 -> crearCliente();
                case 5 -> crearBus();
                case 6 -> crearViaje();
                case 7 -> vendePasaje();
                case 8 -> listarVentas();
                case 9 -> listarViajes();
                case 10 -> listarPasajeros();
                case 11 -> listarEmpresas();
                case 12 -> listarLlegadasSalidas();
                case 13 -> listarVentasDeEmpresa();
                case 14 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida");
            }
        } while (op!=14);
    }
    //crearEmpresa = Diego Muñoz
    private void crearEmpresa(){
        System.out.println("...:::: Creando una nueva EMPRESA ::::..");
        System.out.println();
        System.out.printf("%21s","R.U.T : ");
        String rut=sc.next();
        System.out.printf("%21s","Nombre : ");
        String nombre = sc.next();
        System.out.printf("%21s","url : ");
        String url = sc.next();
        try {
            ControladorEmpresas.getInstance().createEmpresa(Rut.of(rut),nombre,url);
            System.out.println("...:::: Empresa guardada exitosamente ::::..");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    private void contratarTripulante(){

    }
    //crearTerminal = Diego Muñoz
    private void crearTerminal(){
        System.out.println("...:::: Creando un nuevo TERMINAL ::::..");
        System.out.println();
        System.out.printf("%21s","Nombre : ");
        String nombre = sc.next();
        System.out.printf("%21s","Calle : ");
        String calle = sc.next();
        System.out.printf("%21s","Numero : ");
        int numero = sc.nextInt();
        System.out.printf("%21s","Comuna : ");
        String comuna = sc.next();
        try {
            Direccion direccion = new Direccion(calle,numero,comuna);
            ControladorEmpresas.getInstance().createTerminal(nombre,direccion);
            System.out.println("...:::: Terminal guardado exitosamente ::::...");
        }catch (SistemaVentaPasajesException e){
            System.out.println("\n" + e.getMessage());
        }
    }

    private void crearCliente(){

    }
    //crearBus = Diego Muñoz
    private void crearBus(){
        System.out.println("...:::: Creación de un nuevo BUS ::::...");
        System.out.println();
        System.out.printf("%21s","Patente : ");
        String patente=sc.next();
        System.out.printf("%21s","Marca : ");
        String marca=sc.next();
        System.out.printf("%21s","Modelo : ");
        String modelo=sc.next();
        System.out.printf("%21s","Numero de asientos : ");
        int numAsientos= sc.nextInt();
        System.out.println();
        System.out.println(":::: Dato de la empresa");
        System.out.printf("%21s","Rut : ");
        String rut = sc.next();
        try {
            ControladorEmpresas.getInstance().createBus(patente,marca,modelo,numAsientos, Rut.of(rut));
            System.out.println("...:::: Bus guardado exitosamente ::::..");
        }catch (SistemaVentaPasajesException e){
            System.out.println("\n"+e.getMessage());
        }
    }
    //crearViaje = Diego Muñoz
    private void crearViaje(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("...:::: Creando un nuevo Viaje ::::...");
        System.out.println();
        System.out.printf("%21s","Fecha[dd/MM/yyyy] : ");
        String fechastr = sc.next();
        LocalDate fecha = LocalDate.parse(fechastr,formatter);
        System.out.printf("%21s","Hora[hh:mm] : ");
        LocalTime hora = LocalTime.parse(sc.next());
        System.out.printf("%21s","Precio : ");
        int precio = sc.nextInt();
        System.out.printf("%21s","Duracion (minutos) : ");
        int duracion = sc.nextInt();
        System.out.printf("%21s","Patente : ");
        String patente = sc.next();
        System.out.printf("%21s","Nro. de conductores : ");
        int nroDeConductores = sc.nextInt();
        System.out.println("\t:: Id Auxiliar ::");
        System.out.println("Rut[1] o Pasaporte[2] : ");
        int op = sc.nextInt();
        IdPersona idAuxiliar = null, idConductor = null, idConductortwo = null;
        IdPersona[] ids = new IdPersona[3];
        if(op==1){
            System.out.printf("%21s","R.U.T : ");
            String rut= sc.next();
            idAuxiliar= Rut.of(rut);
        } else if (op==2) {
            System.out.printf("%21s","NUMERO DE PASAPORTE : ");
            String numero= sc.next();
            System.out.printf("%21s","NACIONALIDAD : ");
            String nacionalidad= sc.next();
            idAuxiliar = Pasaporte.of(numero,nacionalidad);
        }
        ids[0] = idAuxiliar;
        System.out.println("\t:: Id Conductor ::");
        if (nroDeConductores==1){
            System.out.println("Rut[1] o Pasaporte[2] : ");
            op = sc.nextInt();
            if(op==1){
                System.out.printf("%21s","R.U.T : ");
                String rut= sc.next();
                idConductor= Rut.of(rut);
            } else if (op==2) {
                System.out.printf("%21s","NUMERO DE PASAPORTE : ");
                String numero= sc.next();
                System.out.printf("%21s","NACIONALIDAD : ");
                String nacionalidad= sc.next();
                idConductor = Pasaporte.of(numero,nacionalidad);
            }
            ids[1] =idConductor;
        }else if (nroDeConductores==2){
            System.out.println("Rut[1] o Pasaporte[2] : ");
            op = sc.nextInt();
            if(op==1){
                System.out.printf("%21s","R.U.T : ");
                String rut= sc.next();
                idConductor= Rut.of(rut);
            } else if (op==2) {
                System.out.printf("%21s","NUMERO DE PASAPORTE : ");
                String numero= sc.next();
                System.out.printf("%21s","NACIONALIDAD : ");
                String nacionalidad= sc.next();
                idConductor = Pasaporte.of(numero,nacionalidad);
            }
            ids[1] = idConductor;
            System.out.println("\t:: Id Conductor 2::");
            System.out.println("Rut[1] o Pasaporte[2] : ");
            op = sc.nextInt();
            if(op==1){
                System.out.printf("%21s","R.U.T : ");
                String rut= sc.next();
                idConductortwo= Rut.of(rut);
            } else if (op==2) {
                System.out.printf("%21s","NUMERO DE PASAPORTE : ");
                String numero= sc.next();
                System.out.printf("%21s","NACIONALIDAD : ");
                String nacionalidad= sc.next();
                idConductortwo = Pasaporte.of(numero,nacionalidad);
            }
            ids[2] = idConductortwo;
        }
        System.out.println("Nombre comuna salida : ");
        String salida = sc.next();
        System.out.println("Nombre comuna llegada");
        String llegada = sc.next();
        String[] comunas = {salida,llegada};
        try {
            SistemaVentaPasajes.getInstance().createViaje(fecha,hora,precio,duracion,patente,ids,comunas);
            System.out.println("...:::: Viaje guardado existosamente ::::....");
        }catch (SistemaVentaPasajesException e){
            System.out.println("\n" + e.getMessage());
        }
    }

    private void vendePasaje(){

    }

    private void pagaVentaPasajes(String idDoc, TipoDocumento tipo){

    }

    private void listarVentas(){

    }
    //listarViajes = Diego Muñoz
    private void listarViajes(){
        System.out.println("...:::: Listado de viajes ::::...");
        String[][] listViajes;
        listViajes = SistemaVentaPasajes.getInstance().listViajes();
        System.out.println();
        if (listViajes.length>0){
            System.out.println("*--------------*-----------*------------*----------------*---------------*---------------------*---------------------*---------------------*");
            System.out.printf("|%-13s |%10s |%10s |%-15s |%14s |%-20s |%-20s |%-20s |%n","FECHA","HORA SALE","HORA LLEGA","PRECIO","ASIENTOS DISP.","PATENTE","ORIGEN","DESTINO");
            System.out.println("|--------------+-----------+------------+----------------+---------------+---------------------+---------------------+---------------------|");
            for (int i = 0; i < listViajes.length-1; i++) {
                System.out.printf("|%-13s |%10s |%10s |$%-15s |%14s |%-20s |%-20s |%-20s|%n",listViajes[i][0],listViajes[i][1],listViajes[i][2],listViajes[i][3],listViajes[i][4],listViajes[i][5],listViajes[i][6],listViajes[i][7]);
                System.out.println("|--------------+-----------+------------+----------------+---------------+---------------------+---------------------+---------------------|");
            }
            for (int i = listViajes.length-1; i < listViajes.length; i++) {
            System.out.printf("|%-13s |%10s |%10s |$%-15s |%14s |%-20s |%-20s |%-20s|%n",listViajes[i][0],listViajes[i][1],listViajes[i][2],listViajes[i][3],listViajes[i][4],listViajes[i][5],listViajes[i][6],listViajes[i][7]);
            System.out.println("*--------------*-----------*------------*----------------*---------------*---------------------*---------------------*---------------------*");
            }
        }else {
            System.out.println("No existen viajes registrados...");
        }
    }
    //listarPasajeros = Diego Muñoz
    private void listarPasajeros(){
        System.out.println("...:::: Listado de pasajeros de un viaje ::::..");
        System.out.printf("%35s ","Fecha del viaje[dd/mm/yyyy] : ");
        String fechastr = sc.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(fechastr, formatter);
        System.out.printf("%35s ","Hora del viaje[hh:mm] : ");
        String horastr = sc.next();
        LocalTime hora = LocalTime.parse(horastr);
        System.out.printf("%35s ","Patente bus : ");
        String patenteBus = sc.next();
        System.out.println();
        String[][]listaPasajeros;
        try {
            listaPasajeros = SistemaVentaPasajes.getInstance().listPasajeros(fecha,hora,patenteBus);
            if (listaPasajeros.length>0){
                System.out.println("*-----------*---------------------*------------------------------------*-------------------------------*--------------------*");
                System.out.printf("|%10s |%20s |%-35s |%-30s |%-20s|%n", "Asiento", "Rut/Pass", "Pasajero", "Contacto", "Telefono contacto");
                System.out.println("|-----------+---------------------+------------------------------------+-------------------------------+--------------------|");
                for (int i = 0; i < listaPasajeros.length - 1; i++) {
                    System.out.printf("|%10s |%20s |%-35s |%-30s |%-20s|%n", listaPasajeros[i][0], listaPasajeros[i][1], listaPasajeros[i][2], listaPasajeros[i][3], listaPasajeros[i][4]);
                    System.out.println("|-----------+---------------------+------------------------------------+-------------------------------+--------------------|");
                }
                for (int i = listaPasajeros.length - 1; i < listaPasajeros.length; i++) {
                    System.out.printf("|%10s |%20s |%-35s |%-30s |%-20s|%n", listaPasajeros[i][0], listaPasajeros[i][1], listaPasajeros[i][2], listaPasajeros[i][3], listaPasajeros[i][4]);
                    System.out.println("*-----------*---------------------*------------------------------------*-------------------------------*--------------------*");
                }
            }else {
                System.out.println("No existen pasajeros para el viaje indicado...");
            }
        }catch (SistemaVentaPasajesException e){
            System.out.println("\n"+ e.getMessage());
        }
    }
    //listarEmpresas = Diego Muñoz
    private void listarEmpresas(){
        System.out.println("...:::: Listado de empresas ::::..");
        System.out.println();
        String[][] datos;
        datos = ControladorEmpresas.getInstance().listEmpresas();
        if (datos.length>0){
            System.out.println("*----------------*----------------*-------------------------------*------------------*-------------*----------------*");
            System.out.printf("|%-15s |%-15s |%-30s |%-17s |%-12s |%-15s |%n","RUT EMPRESA","NOMBRE","URL","NRO. TRIPULANTES","NRO. BUSES","NRO.VENTAS");
            System.out.println("|----------------+----------------+-------------------------------+------------------+-------------+----------------|");
            for (int i = 0; i < datos.length-1; i++) {
                System.out.printf("|%-15s |%-15s |%-30s |%-17s |%-12s |%-15s |%n",datos[i][0],datos[i][1],datos[i][2],datos[i][3],datos[i][4],datos[i][5]);
                System.out.println("|--------------+-----------+------------+----------------+---------------+---------------------+---------------------+---------------------|");
            }
            for (int i = datos.length-1; i < datos.length; i++) {
                System.out.printf("|%-15s |%-15s |%-30s |%-17s |%-12s |%-15s |%n",datos[i][0],datos[i][1],datos[i][2],datos[i][3],datos[i][4],datos[i][5]);
                System.out.println("*----------------*----------------*-------------------------------*------------------*-------------*----------------*");
            }
        }else {
            System.out.println("No hay empresas registradas...");
        }
    }
    //listarLlegadasSalidas = Diego Muñoz
    private void listarLlegadasSalidas(){
        System.out.println("...:::: Lista llegadas y salidas de un terminal ::::..");
        System.out.println();
        System.out.printf("%21s","Nombre terminal : ");
        String nombre = sc.next();
        System.out.printf("%21s","Fecha[dd/MM/yyyy] : ");
        String fechastr = sc.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(fechastr,formatter);

        String[][] datos;
        try {
            datos = ControladorEmpresas.getInstance().listLlegadasSalidaTerminal(nombre,fecha);
            if (datos.length>0){
                System.out.println("*----------------*--------*-------------*---------------------*---------------*");
                System.out.printf("|%-15s |%-7s |%-12s |%-20s |%-12s |%n","LLEGADA/SALIDA","HORA","PATENTE BUS","NOMBRE EMPRESA","NRO. PASAJEROS");
                System.out.println("|----------------+--------+-------------+---------------------+---------------|");
                for (int i = 0; i < datos.length-1; i++) {
                    System.out.printf("|%-15s |%-7s |%-12s |%-20s |%-12s    |%n",datos[i][0],datos[i][1],datos[i][2],datos[i][3],datos[i][4]);
                    System.out.println("|----------------+--------+-------------+---------------------+---------------|");
                }
                for (int i = datos.length-1; i < datos.length; i++) {
                    System.out.printf("|%-15s |%-7s |%-12s |%-20s |%-12s   |%n",datos[i][0],datos[i][1],datos[i][2],datos[i][3],datos[i][4]);
                    System.out.println("*----------------*--------*-------------*---------------------*---------------*");
                }
            }else {
                System.out.println("No existen datos asignados a la fecha y/o terminal...");
            }
        }catch (SistemaVentaPasajesException e){
            System.out.println("\n" + e.getMessage());
        }
    }
    //listarVentasDeEmpresa: Diego Muñoz
    private void listarVentasDeEmpresa(){
        System.out.println("...:::: Listado de ventas de una empresa ::::..");
        System.out.printf("%21s","R.U.T : ");
        String rut = sc.next();
        String[][] datos;
        try {
            datos = ControladorEmpresas.getInstance().listVentasEmpresas();
            if (datos.length>0){
                System.out.println("*----------------*--------*--------------*----------------*");
                System.out.printf("|%-15s |%-7s |%-13s |%15s |%n","FECHA","TIPO","MONTO PAGADO","TIPO PAGO");
                System.out.println("|----------------+--------+--------------+----------------|");
                for (int i = 0; i < datos.length-1; i++) {
                    System.out.printf("|%-15s |%-7s |$%-13s|%15s|%n",datos[i][0],datos[i][1],datos[i][2],datos[i][3]);
                    System.out.println("|----------------+--------+--------------+----------------|");
                }
                for (int i = datos.length-1; i < datos.length; i++) {
                    System.out.printf("|%-15s |%-7s |$%-13s|%15s |%n",datos[i][0],datos[i][1],datos[i][2],datos[i][3]);
                    System.out.println("*----------------*--------*--------------*----------------*");
                }
            }else {
                System.out.println("No hay datos asignados a la empresa...");
            }
        }catch (SistemaVentaPasajesException e){
            System.out.println("\n" + e.getMessage());
        }
    }

}
