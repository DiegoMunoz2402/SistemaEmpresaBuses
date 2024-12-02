package vista;

import controlador.ControladorEmpresas;
import controlador.SistemaVentaPasajes;
import excepciones.SVPException;
import modelo.Pasajero;
import utilidades.*;
import modelo.TipoDocumento;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Optional;
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
            System.out.println("\n======================");
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
            System.out.println("  14) Generar pasajes venta");
            System.out.println("  15) Leer datos iniciales");
            System.out.println("  16) Guardar datos del sistema");
            System.out.println("  17) Leer datos del sistema");
            System.out.println("  18) Salir");
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
                case 14 -> generarPasajesVenta();
                case 15 -> readDatosIniciales();
                case 16 -> saveDatosSistema();
                case 17 -> readDatosSistema();
                case 18 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida");
            }
        } while (op!=18);
    }
    //crearEmpresa = Diego Muñoz
    private void crearEmpresa(){
        GUICreaEmpresa.display();
//        System.out.println("...:::: Creando una nueva EMPRESA ::::..");
//        System.out.println();
//        System.out.printf("%21s","R.U.T : ");
//        String rut=sc.next();
//        System.out.printf("%21s","Nombre : ");
//        String nombre = sc.next();
//        System.out.printf("%21s","url : ");
//        String url = sc.next();
//        if (!rut.isBlank() && !nombre.isBlank() && !url.isBlank()) {
//            try {
//                ControladorEmpresas.getInstance().createEmpresa(Rut.of(rut), nombre, url);
//                System.out.println("...:::: Empresa guardada exitosamente ::::..");
//            } catch (SVPException e) {
//                System.out.println("\n" + e.getMessage());
//            }
//        }else {
//            System.out.println("\nUno o mas campos vacíos, volviendo...");
//        }
    }
    //ContratarTripulante : Diego Muñoz
    private void contratarTripulante(){
        try {
            System.out.println("...:::: Contratando un nuevo Tripulante ::::..");
            System.out.println();
            System.out.println();
            System.out.println("::: Dato de la empresa");
            System.out.printf("%21s", "Rut : ");
            String rutEmpresa = sc.next();
            if (!rutEmpresa.isBlank()) {
                System.out.println();
                System.out.println("::: Datos Tripulante");
                System.out.print("Auxiliar[1] o Conductor[2] : ");
                int op = sc.nextInt();
                if (op > 0 && op < 3) {
                    System.out.print("Rut[1] o Pasaporte[2] : ");
                    int op2 = sc.nextInt();
                    if (op2 > 0 && op2 < 3) {
                        IdPersona id = null;
                        if (op2 == 1) {
                            System.out.printf("%21s", "R.U.T : ");
                            String rut = sc.next();
                            if (rut.isBlank()) {
                                System.out.println("Ingresó un rut vacío, volviendo...");
                                return;
                            }
                            id = Rut.of(rut);
                        } else {
                            System.out.printf("%21s", "NUMERO DE PASAPORTE : ");
                            String numero = sc.next();
                            System.out.printf("%21s", "NACIONALIDAD : ");
                            String nacionalidad = sc.next();
                            if (nacionalidad.isBlank() || !numero.isBlank()) {
                                System.out.println("\nNumero o Nacionalidad vacíos, volviendo...");
                                return;
                            }
                            id = Pasaporte.of(numero, nacionalidad);
                        }
                        if (id == null) {
                            System.out.println("El Id es nulo, volviendo...");
                            return;
                        }
                        System.out.printf("%21s", "Sr.[1] o Sra.[2] : ");
                        int tratamientoint = sc.nextInt();
                        Tratamiento tratamiento = null;
                        if (tratamientoint > 0 && tratamientoint < 3) {
                            if (tratamientoint == 1) {
                                tratamiento = Tratamiento.SR;
                            } else {
                                tratamiento = Tratamiento.SRA;
                            }
                            System.out.printf("%21s", "Nombres : ");
                            String nombre = sc.next();
                            System.out.printf("%21s", "Apellido paterno : ");
                            String apellidoPaterno = sc.next();
                            System.out.printf("%21s", "Apellido materno : ");
                            String apellidoMaterno = sc.next();
                            System.out.printf("%21s", "Calle : ");
                            String calle = sc.next();
                            System.out.printf("%21s", "Numero : ");
                            int numero = sc.nextInt();
                            System.out.printf("%21s", "Comuna : ");
                            String comuna = sc.next();
                            if (!calle.isBlank() && numero > 0 && !comuna.isBlank()) {
                                Direccion direccion = new Direccion(calle, numero, comuna);
                                if (!nombre.isBlank() && !apellidoMaterno.isBlank() && !apellidoPaterno.isBlank()) {
                                    Nombre nombreTipo = new Nombre(nombre, apellidoPaterno, apellidoMaterno, tratamiento);
                                    if (op == 1) {
                                        try {
                                            ControladorEmpresas.getInstance().hireAuxiliarForEmpresa(Rut.of(rutEmpresa), id, nombreTipo, direccion);
                                            System.out.println("...::: Auxiliar contratado exitosamente :::....");
                                        } catch (SVPException e) {
                                            System.out.println("\n" + e.getMessage());
                                        }
                                    } else {
                                        try {
                                            ControladorEmpresas.getInstance().hireConductorForEmpresa(Rut.of(rutEmpresa), id, nombreTipo, direccion);
                                            System.out.println("...::: Conductor contratado exitosamente :::....");
                                        } catch (SVPException e) {
                                            System.out.println("\n" + e.getMessage());
                                        }
                                    }
                                } else {
                                    System.out.println("\nUno o mas campos vacíos (Nombre, apPaterno, apMaterno), volviendo...");
                                }
                            } else {
                                System.out.println("\nComuna o calle vacíos, o numero invalido, volviendo...");
                            }
                        } else {
                            System.out.println("\nOpcion invalida, volviendo...");
                        }
                    } else {
                        System.out.println("\nOpcion invalida, volviendo...");
                    }
                } else {
                    System.out.println("\nOpcion invalida, volviendo...");
                }
            } else {
                System.out.println("Campo empresa vacío, volviendo...");
            }
        }catch (InputMismatchException e){
            System.out.println("\nError, numero invalido, volviendo...");
        }
    }
    //crearTerminal = Diego Muñoz
    private void crearTerminal(){
        try {
            System.out.println("...:::: Creando un nuevo TERMINAL ::::..");
            System.out.println();
            System.out.printf("%21s", "Nombre : ");
            String nombre = sc.next();
            System.out.printf("%21s", "Calle : ");
            String calle = sc.next();
            System.out.printf("%21s", "Numero : ");
            int numero = sc.nextInt();
            System.out.printf("%21s", "Comuna : ");
            String comuna = sc.next();
            if (!nombre.isBlank() && !calle.isBlank() && !comuna.isBlank()) {
                if (numero > 0) {
                    try {
                        Direccion direccion = new Direccion(calle, numero, comuna);
                        ControladorEmpresas.getInstance().createTerminal(nombre, direccion);
                        System.out.println("...:::: Terminal guardado exitosamente ::::...");
                    } catch (SVPException e) {
                        System.out.println("\n" + e.getMessage());
                    }
                } else {
                    System.out.println("\nNumero invalido, volviendo...");
                }
            } else {
                System.out.println("\nUno o mas campos vacios, volviendo...");
            }
        }catch (InputMismatchException e){
            System.out.println("\nNumero invalido, volviendo...");
        }
    }
     //crearCliente: Rodolfo Salas
    private void crearCliente(){
        IdPersona identificador = null;
        Tratamiento tratamiento= null;
        try {
            System.out.println(":::: Crear un nuevo Cliente ::::");
            System.out.print("Rut[1] o Pasaporte[2] : ");
            int tipo = sc.nextInt();
            sc.nextLine();
            if (tipo>0 && tipo<3) {
                if (tipo == 1) {
                    System.out.println("R.U.T: ");
                    String rut = sc.next();
                    if (rut.isBlank()){
                        System.out.println("\nRut vacío, volviendo...");
                        return;
                    }
                    identificador = Rut.of(rut);
                } else {
                    System.out.print("NUMERO DE PASAPORTE: ");
                    String numero = sc.next();
                    System.out.print("NACIONALIDAD: ");
                    String nacionalidad = sc.next();
                    if (numero.isBlank() || nacionalidad.isBlank()){
                        System.out.println("\nUno o los dos campos vacíos, volviendo...");
                    }
                    identificador = Pasaporte.of(numero, nacionalidad);

                }
                if (identificador==null){
                    System.out.println("\nIdentificador nunlo, volviendo...");
                    return;
                }
                System.out.print("Sr.[1] o Sra. [2] : ");
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion>0 && opcion<3) {
                    if (opcion == 1) {
                        tratamiento = Tratamiento.SR;
                    } else {
                        tratamiento = Tratamiento.SRA;
                    }
                    System.out.println("Nombres: ");
                    String nombres = sc.nextLine();
                    System.out.println("Apellido Paterno: ");
                    String apellidoPaterno = sc.nextLine();
                    System.out.println("Apellido Materno: ");
                    String apellidoMaterno = sc.nextLine();
                    System.out.println("Telefono Movil: ");
                    String telefono = sc.next();
                    System.out.println("Email: ");
                    String email = sc.next();
                    if (!nombres.isBlank() && !apellidoMaterno.isBlank() && !apellidoPaterno.isBlank() && !telefono.isBlank() && !email.isBlank()) {
                        Nombre nombrecompleto = new Nombre(nombres, apellidoPaterno, apellidoMaterno, tratamiento);
                        SistemaVentaPasajes.getInstance().createCliente(identificador, nombrecompleto, telefono, email);
                    }
                }else {
                    System.out.println("\nUno o mas campos vacíos, volviendo...");
                }
            }else {
                System.out.println("\nOpcion invalida, volviendo...");
            }
            System.out.println("...:::: Cliente guardado exitosamente ::::...");
        }catch (SVPException e){
            System.out.println("\n" + e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("\nNumero invalido, volviendo...");
        }

    }
    //crearBus = Diego Muñoz
    private void crearBus(){
        GUICreaBus.display();
//        System.out.println("...:::: Creación de un nuevo BUS ::::...");
//        System.out.println();
//        System.out.printf("%21s","Patente : ");
//        String patente=sc.next();
//        System.out.printf("%21s","Marca : ");
//        String marca=sc.next();
//        System.out.printf("%21s","Modelo : ");
//        String modelo=sc.next();
//        System.out.printf("%21s","Numero de asientos : ");
//        int numAsientos= sc.nextInt();
//        System.out.println();
//        System.out.println(":::: Dato de la empresa");
//        System.out.printf("%21s","Rut : ");
//        String rut = sc.next();
//        try {
//            ControladorEmpresas.getInstance().createBus(patente,marca,modelo,numAsientos, Rut.of(rut));
//            System.out.println("...:::: Bus guardado exitosamente ::::..");
//        }catch (SVPException e){
//            System.out.println("\n"+e.getMessage());
//        }
    }
    //crearViaje = Diego Muñoz
    private void crearViaje(){
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.println("...:::: Creando un nuevo Viaje ::::...");
            System.out.println();
            String fechastr = "", horastr = "";
            LocalDate fecha = null;
            LocalTime hora = null;
            try {
                System.out.printf("%21s", "Fecha[dd/MM/yyyy] : ");
                fechastr = sc.next();
                fecha = LocalDate.parse(fechastr, formatter);
                System.out.printf("%21s", "Hora[hh:mm] : ");
                horastr = sc.next();
                hora = LocalTime.parse(horastr);
            } catch (DateTimeException e) {
                System.out.println("\nFecha u hora invalidas, volviendo...");
                return;
            }
            System.out.printf("%21s", "Precio : ");
            int precio = sc.nextInt();
            System.out.printf("%21s", "Duracion (minutos) : ");
            int duracion = sc.nextInt();
            System.out.printf("%21s", "Patente : ");
            String patente = sc.next();
            System.out.printf("%21s", "Nro. de conductores : ");
            int nroDeConductores = sc.nextInt();
            if (!fechastr.isBlank() && !horastr.isBlank() && precio > 0 && duracion > 0 && !patente.isBlank() && nroDeConductores > 0) {
                System.out.println("\t:: Id Auxiliar ::");
                System.out.println("Rut[1] o Pasaporte[2] : ");
                int op = sc.nextInt();
                if (op > 0 && op < 3) {
                    IdPersona idAuxiliar = null, idConductor = null, idConductortwo = null;
                    IdPersona[] ids = new IdPersona[3];
                    if (op == 1) {
                        System.out.printf("%21s", "R.U.T : ");
                        String rut = sc.next();
                        if (rut.isBlank()) {
                            System.out.println("\nRut vacío, volviendo...");
                            return;
                        }
                        idAuxiliar = Rut.of(rut);
                    } else {
                        System.out.printf("%21s", "NUMERO DE PASAPORTE : ");
                        String numero = sc.next();
                        System.out.printf("%21s", "NACIONALIDAD : ");
                        String nacionalidad = sc.next();
                        if (numero.isBlank() || nacionalidad.isBlank()) {
                            System.out.println("\nUno o los dos campos vacíos, volviendo...");
                            return;
                        }
                        idAuxiliar = Pasaporte.of(numero, nacionalidad);
                    }
                    if (idAuxiliar == null) {
                        System.out.println("\nId auxiliar nulo, volviendo...");
                    }
                    ids[0] = idAuxiliar;
                    System.out.println("\t:: Id Conductor ::");
                    if (nroDeConductores == 1) {
                        System.out.println("Rut[1] o Pasaporte[2] : ");
                        op = sc.nextInt();
                        if (op > 0 && op < 3) {
                            if (op == 1) {
                                System.out.printf("%21s", "R.U.T : ");
                                String rut = sc.next();
                                if (rut.isBlank()) {
                                    System.out.println("\nRut vacío, volviendo...");
                                    return;
                                }
                                idConductor = Rut.of(rut);
                            } else {
                                System.out.printf("%21s", "NUMERO DE PASAPORTE : ");
                                String numero = sc.next();
                                System.out.printf("%21s", "NACIONALIDAD : ");
                                String nacionalidad = sc.next();
                                if (numero.isBlank() || nacionalidad.isBlank()) {
                                    System.out.println("\nUno o los dos campos vacíos, volviendo...");
                                    return;
                                }
                                idConductor = Pasaporte.of(numero, nacionalidad);
                            }
                            ids[1] = idConductor;
                        } else {
                            System.out.println("\nOpcion invalida, volviendo...");
                        }
                    } else if (nroDeConductores == 2) {
                        System.out.println("Rut[1] o Pasaporte[2] : ");
                        op = sc.nextInt();
                        if (op > 0 && op < 3) {
                            if (op == 1) {
                                System.out.printf("%21s", "R.U.T : ");
                                String rut = sc.next();
                                if (rut.isBlank()) {
                                    System.out.println("\nRut vacío, volviendo...");
                                    return;
                                }
                                idConductor = Rut.of(rut);
                            } else {
                                System.out.printf("%21s", "NUMERO DE PASAPORTE : ");
                                String numero = sc.next();
                                System.out.printf("%21s", "NACIONALIDAD : ");
                                String nacionalidad = sc.next();
                                if (numero.isBlank() || nacionalidad.isBlank()) {
                                    System.out.println("\nUno o los dos campos vacíos, volviendo...");
                                    return;
                                }
                                idConductor = Pasaporte.of(numero, nacionalidad);
                            }
                            ids[1] = idConductor;
                            System.out.println("\t:: Id Conductor 2::");
                            System.out.println("Rut[1] o Pasaporte[2] : ");
                            op = sc.nextInt();
                            if (op == 1) {
                                System.out.printf("%21s", "R.U.T : ");
                                String rut = sc.next();
                                if (rut.isBlank()) {
                                    System.out.println("\nRut vacío, volviendo...");
                                    return;
                                }
                                idConductortwo = Rut.of(rut);
                            } else if (op == 2) {
                                System.out.printf("%21s", "NUMERO DE PASAPORTE : ");
                                String numero = sc.next();
                                System.out.printf("%21s", "NACIONALIDAD : ");
                                String nacionalidad = sc.next();
                                if (numero.isBlank() || nacionalidad.isBlank()) {
                                    System.out.println("\nUno o los dos campos vacíos, volviendo...");
                                    return;
                                }
                                idConductortwo = Pasaporte.of(numero, nacionalidad);
                            }
                            ids[2] = idConductortwo;
                        }
                    } else {
                        System.out.println("\nOpcion invalida, volviendo...");
                    }
                    if (ids[0] == null || ids[1] == null || ids[2] == null) {
                        System.out.println("\nUna o mas ids son nulas, volviendo...");
                    }
                    System.out.print("Nombre comuna salida : ");
                    String salida = sc.next();
                    System.out.print("Nombre comuna llegada: ");
                    String llegada = sc.next();
                    if (!salida.isBlank() && !llegada.isBlank()) {
                        String[] comunas = {salida, llegada};
                        try {
                            SistemaVentaPasajes.getInstance().createViaje(fecha, hora, precio, duracion, patente, ids, comunas);
                            System.out.println("...:::: Viaje guardado existosamente ::::....");
                        } catch (SVPException e) {
                            System.out.println("\n" + e.getMessage());
                        }
                    } else {
                        System.out.println("\nUno o los dos campos vacíos, volviendo...");
                    }
                } else {
                    System.out.println("\nOpcion invalida, volviendo...");
                }
            } else {
                System.out.println("\nUno o mas campos invalidos, volviendo...");
            }
        }catch (InputMismatchException e){
            System.out.println("\nNumero invalido, volviendo...");
        }
    }
    //vendePasaje : Diego Muñoz Y Javier Duarte
    private void vendePasaje(){
        try {
            System.out.println("\t\t...:::Venta de pasajes :::....");
            System.out.println();
            System.out.println();
            System.out.println(":::: Datos de la venta");
            System.out.printf("%21s", "ID Documento : ");
            String idDocumento = sc.next();
            if (idDocumento.isBlank()){
                System.out.println("\nCampo vacío, volviendo...");
                return;
            }
            System.out.print("Tipo Documento: [1] Boleta [2] Factura : ");
            int tipoDoc = sc.nextInt();
            TipoDocumento tipoDocumento = null;
            if (tipoDoc == 1) {
                tipoDocumento = TipoDocumento.BOLETA;
            } else if (tipoDoc == 2){
                tipoDocumento = TipoDocumento.FACTURA;
            }else {
                System.out.println("\nOpcion invalida, volviendo...");
            }
            System.out.print("Fecha de viaje[dd/mm/yyyy] : ");
            String fechaViaje = sc.next();
            if (fechaViaje.isBlank()){
                System.out.println("\nCampo vacío, volviendo...");
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaViaje, formatter);
            System.out.printf("%21s", "Origen (comuna) : ");
            String comunaOrigen = sc.next();
            System.out.printf("%21s", "Destino (comuna) : ");
            String comunaDestino = sc.next();
            System.out.println();
            System.out.println();
            if (comunaOrigen.isBlank() || comunaDestino.isBlank()){
                System.out.println("\nUno o dos campos vacíos, volviendo....");
                return;
            }
            System.out.println("::: Datos del cliente");
            System.out.println();
            System.out.printf("%21s", "Rut[1] o Pasaporte[2] : ");
            int opCliente = sc.nextInt();
            if (opCliente<0 || opCliente>2){
                System.out.println("\nOpcion invalida, volviendo...");
                return;
            }
            IdPersona idCliente = null;
            if (opCliente == 1) {
                System.out.printf("%21s", "R.U.T : ");
                String rut = sc.next();
                if (rut.isBlank()){
                    System.out.println("\nCampo vacío, volviendo...");
                    return;
                }
                idCliente = Rut.of(rut);
            } else if (opCliente == 2) {
                System.out.printf("%21s", "NUMERO DE PASAPORTE : ");
                String numero = sc.next();
                System.out.printf("%21s", "NACIONALIDAD : ");
                String nacionalidad = sc.next();
                if (numero.isBlank() || nacionalidad.isBlank()){
                    System.out.println("\nUno o dos campos vacíos, volviendo...");
                }
                idCliente = Pasaporte.of(numero, nacionalidad);
            }

            if (idCliente==null){
                System.out.println("\nId cliente es nulo, volviendo...");
                return;
            }

            System.out.println("::: Pasajes a vender ");
            System.out.printf("%21s", "Cantidad de pasajes : ");
            int cantidadPasajes = sc.nextInt();
            if (cantidadPasajes<=0){
                System.out.println("\n0 pasajes a vender, saliendo...");
                return;
            }
            String[][] listHorariosDisponibles;
            try {
                SistemaVentaPasajes.getInstance().iniciaVenta(idDocumento, tipoDocumento, fecha, comunaOrigen, comunaDestino, idCliente, cantidadPasajes);
                listHorariosDisponibles = SistemaVentaPasajes.getInstance().getHorariosDisponibles(fecha);
                if (listHorariosDisponibles.length > 0) {
                    System.out.println();
                    System.out.println("::: Lista de horarios disponibles");
                    System.out.println();
                    System.out.println("  *--------------*-----------*---------*-----------*");
                    System.out.printf("  |%-13s |%-10s |%-8s |%-10s |%n", "BUS", "SALIDA", "VALOR", " ASIENTOS");
                    System.out.println("  |--------------+-----------+---------+-----------|");
                    for (int i = 0; i < listHorariosDisponibles.length - 1; i++) {
                        System.out.printf("%1s |%-13s |%-10s |%-8s |%-10s |%n", i + 1, listHorariosDisponibles[i][1], listHorariosDisponibles[i][2], listHorariosDisponibles[i][3], listHorariosDisponibles[i][0]);
                        System.out.println(" |--------------+-----------+---------+-----------|");
                    }
                    for (int i = listHorariosDisponibles.length - 1; i < listHorariosDisponibles.length; i++) {
                        System.out.printf("%1s |%-13s |%-10s |%-8s |%-10s |%n", i + 1, listHorariosDisponibles[i][1], listHorariosDisponibles[i][2], listHorariosDisponibles[i][3], listHorariosDisponibles[i][0]);
                        System.out.println("  *--------------*-----------*---------*-----------*");
                    }
                    System.out.print("Seleccione viaje [1.." + listHorariosDisponibles.length + "] : ");
                    int nroViaje = sc.nextInt();
                    while (nroViaje<=0){
                        System.out.println("\n\tNumero de viaje invalido, vuelva a ingresar --> ");
                        nroViaje = sc.nextInt();
                    }
                    String patenteViaje = listHorariosDisponibles[nroViaje - 1][1];
                    String horaViaje = listHorariosDisponibles[nroViaje - 1][2];
                    LocalTime horaFormato = LocalTime.parse(horaViaje);
                    String[] listAsientosDeViaje = SistemaVentaPasajes.getInstance().listAsientosDeViaje(fecha, horaFormato, patenteViaje);
                    if (listAsientosDeViaje.length > 0) {
                        for (int i = 0; i < listAsientosDeViaje.length; i += 4) {
                            System.out.print("| " + listAsientosDeViaje[i] + " | " + listAsientosDeViaje[i + 1] + " |");
                            if (i % 8 == 0) {
                                System.out.print(" " + listAsientosDeViaje[i + 3] + " | " + listAsientosDeViaje[i + 2] + " |");
                            } else {
                                System.out.print(" " + listAsientosDeViaje[i + 2] + " | " + listAsientosDeViaje[i + 3] + " |");
                            }
                            System.out.println();
                            System.out.println("|---------------|");
                        }

                        System.out.print("Seleccione sus asientos [separe por ,] : ");
                        String asientos = sc.next();
                        if (asientos.isBlank()){
                            System.out.println("\nAsientos vacíos, volviendo...");
                            return;
                        }
                        String[] asientosArr = asientos.split(",");
                        if (cantidadPasajes == asientosArr.length) {
                            for (int i = 0; i < asientosArr.length; i++) {
                                System.out.println("::: Datos pasajero " + (i + 1));
                                System.out.print("Rut[1] o Pasaporte[2] : ");
                                int opPasajero = sc.nextInt();
                                if (opPasajero<0 || opPasajero>2){
                                    System.out.println("\nOpcion invalida, volviendo...");
                                    return;
                                }
                                IdPersona idPasajero = null;
                                if (opPasajero == 1) {
                                    System.out.printf("%21s", "R.U.T : ");
                                    String rut = sc.next();
                                    if (rut.isBlank()){
                                        System.out.println("\nCampo vacío, volviendo...");
                                    }
                                    idPasajero = Rut.of(rut);
                                } else if (opPasajero == 2) {
                                    System.out.printf("%21s", "NUMERO DE PASAPORTE : ");
                                    String numero = sc.next();
                                    System.out.printf("%21s", "NACIONALIDAD : ");
                                    String nacionalidad = sc.next();
                                    if (numero.isBlank() || nacionalidad.isBlank()){
                                        System.out.println("\nCampos vacíos, volviendo...");
                                        return;
                                    }
                                    idPasajero = Pasaporte.of(numero, nacionalidad);
                                }
                                if (idPasajero==null){
                                    System.out.println("\nEl id pasajero es nulo, volviendo...");
                                    return;
                                }
                                Optional<String> nombrePasajeroOp = SistemaVentaPasajes.getInstance().getNombrePasajero(idPasajero);
                                Pasajero pasajero = null;
                                if (nombrePasajeroOp.isEmpty()) {
                                    System.out.print("Sr.[1] o Sra.[2] :");
                                    int tratamientoPasajero = sc.nextInt();
                                    if (tratamientoPasajero<0 || tratamientoPasajero>2){
                                        System.out.println("\nOpcion invalida, volviendo...");
                                        return;
                                    }
                                    Tratamiento tratamientoPas;
                                    if (tratamientoPasajero == 1) {
                                        tratamientoPas = Tratamiento.SR;
                                    } else {
                                        tratamientoPas = Tratamiento.SRA;
                                    }
                                    System.out.print("Nombres del pasajero : ");
                                    String nombrePasajero = sc.next();
                                    System.out.print("Ingrese el apellido Paterno del pasajero: ");
                                    String apellidoPaternoPasajero = sc.next();
                                    System.out.print("Ingrese el apellido Materno del pasajero: ");
                                    String apellidoMaternoPasajero = sc.next();
                                    System.out.print("Numero de telefono (pasajero) : ");
                                    String telefonoPasajero = sc.next();
                                    if (nombrePasajero.isBlank() || apellidoMaternoPasajero.isBlank() || apellidoPaternoPasajero.isBlank() || telefonoPasajero.isBlank()){
                                        System.out.println("\nUno o mas campos vacios, volviendo...");
                                        return;
                                    }
                                    Nombre nombreCompletoPasajero = new Nombre(nombrePasajero, apellidoPaternoPasajero, apellidoMaternoPasajero, tratamientoPas);

                                    System.out.print("Ingrese el tratamiento del contacto Sr.[1] o Sra.[2] :");
                                    int opContacto = sc.nextInt();
                                    if (opContacto<0 || opContacto>2){
                                        System.out.println("\nOpcion invalida, volviendo...");
                                        return;
                                    }
                                    Tratamiento trataContacto = null;
                                    switch (opContacto) {
                                        case 1:
                                            trataContacto = Tratamiento.SR;
                                            break;
                                        case 2:
                                            trataContacto = Tratamiento.SRA;
                                            break;
                                    }
                                    System.out.print("Ingrese el nombres del Contacto : ");
                                    String nombresContacto = sc.next();
                                    System.out.print("Ingrese el apellido Paterno del Contacto : ");
                                    String apellidoPaternoContacto = sc.next();
                                    System.out.print("Ingrese el apellido Materno del Contacto : ");
                                    String apellidoMaternoContacto = sc.next();
                                    System.out.print("Ingrese el Fono del Contacto : ");
                                    String fonoContacto = sc.next();

                                    if (nombresContacto.isBlank() || apellidoPaternoContacto.isBlank() || apellidoMaternoContacto.isBlank() ||  fonoContacto.isBlank()){
                                        System.out.println("\nUno o mas campos vacíos, volviendo");
                                        return;
                                    }

                                    Nombre nombreContacto = new Nombre(nombresContacto, apellidoPaternoContacto, apellidoMaternoContacto, trataContacto);

                                    pasajero = new Pasajero(idPasajero, nombreCompletoPasajero, nombreContacto, fonoContacto);
                                    pasajero.setTelefono(telefonoPasajero);

                                    SistemaVentaPasajes.getInstance().createPasajero(idPasajero, nombreCompletoPasajero, telefonoPasajero, nombreContacto, fonoContacto);

                                    System.out.println("\n::: Pasaje agregado exitosamente");

                                    int asientoSeleccionado = Integer.parseInt(asientosArr[i]);
                                    SistemaVentaPasajes.getInstance().vendePasaje(idDocumento, tipoDocumento, fecha, horaFormato, patenteViaje, asientoSeleccionado, idPasajero);
                                }else {//else para cuando se encuentra el Idpasajero
                                    int asientoSeleccionado = Integer.parseInt(asientosArr[i]);
                                    SistemaVentaPasajes.getInstance().vendePasaje(idDocumento,tipoDocumento,fecha,horaFormato,patenteViaje,asientoSeleccionado,idPasajero);
                                }
                            }

                            pagaVentaPasajes(idDocumento, tipoDocumento);

                        } else {
                            System.out.println("La cantidad de pasajes no coincide con el numero de asientos seleccionado");
                        }
                    } else {
                        System.out.println("No hay datos asignados...");
                    }
                } else {
                    System.out.println("No hay viajes disponibles...");
                }
            } catch (SVPException e) {
                System.out.println("\n" + e.getMessage());
            }
        }catch (InputMismatchException e){
            System.out.println("\nNumero invalido, volviendo...");
        }catch (DateTimeException e){
            System.out.println("\nFecha u hora invalida, volviendo...");
        }
    }
    //Metodo pagaVentaPasajes: Javiera Duarte
    private void pagaVentaPasajes(String idDoc, TipoDocumento tipo) {
        try {
            Optional<Integer> montoOptional = SistemaVentaPasajes.getInstance().getMontoVenta(idDoc, tipo);
            if (montoOptional.isEmpty()) {
                System.out.println("No se encontró una venta con el ID de documento y tipo especificados.");
                return;
            }
            int monto = montoOptional.get();

            System.out.println(":::: El monto total de la venta : $" + monto);
            System.out.println();
            System.out.println(":::: Pago de la venta");
            System.out.print("Efectivo[1] o Tarjeta[2] : ");
            int opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    // Pago efectivo
                    SistemaVentaPasajes.getInstance().pagaVenta(idDoc, tipo);
                    System.out.println("Pago en efectivo realizado con éxito.");
                    break;
                case 2:
                    // Pago tarjeta
                    System.out.print("Ingrese el numero de tarjeta : ");
                    String nroTarjeta = sc.next();
                    SistemaVentaPasajes.getInstance().pagaVenta(idDoc, tipo, Long.parseLong(nroTarjeta));
                    System.out.println("Pago con tarjeta realizado con éxito.");
                    break;
                default:
                    System.out.println("Opción no válida. Pago cancelado.");
            }
        } catch (SVPException e) {
            System.out.println("Error al procesar el pago: " + e.getMessage());
        }
    }
    //ListarVentas = Diego Muñoz
    private void listarVentas(){
        String[][] datos = SistemaVentaPasajes.getInstance().listVentas();
        if (datos.length>0){
            System.out.println("*--------------*----------------*----------------*----------------*-------------------------------*-------------*------------*");
            System.out.printf("|%-13s |%-15s |%-15s |%-15s |%-30s |%-8s |%-10s |%n","ID DOCUMENTO","TIPO DOCUMENTO","FECHA","RUT/PASAPORTE","CLIENTE","CANT BOLETOS","TOTAL VENTA");
            System.out.println("|--------------+----------------+----------------+----------------+-------------------------------+-------------+------------|");
            for (int i = 0; i < datos.length-1; i++) {
                System.out.printf("|%-13s |%-15s |%-15s |%-15s |%-30s |%-8s |%-10s |%n",datos[i][0],datos[i][1],datos[i][2],datos[i][3],datos[i][4],datos[i][5],datos[i][6]);
                System.out.println("|--------------+----------------+----------------+----------------+-------------------------------+-------------+------------|");
            }
            for (int i = datos.length-1; i < datos.length; i++) {
                System.out.printf("|%-13s |%-15s |%-15s |%-15s |%-30s |%-8s     |%-10s  |%n",datos[i][0],datos[i][1],datos[i][2],datos[i][3],datos[i][4],datos[i][5],datos[i][6]);
                System.out.println("*--------------*----------------*----------------*----------------*-------------------------------*-------------*------------*");
            }
        }else {
            System.out.println("No existen ventas...");
        }
    }
    //listarViajes = Diego Muñoz
    private void listarViajes(){
        System.out.println("...:::: Listado de viajes ::::...");
        String[][] listViajes;
        listViajes = SistemaVentaPasajes.getInstance().listViajes();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println();
        if (listViajes.length>0){
            System.out.println("*--------------*-----------*-----------*----------------*---------------*---------------------*---------------------*---------------------*");
            System.out.printf("|%-13s |%10s |%10s |%-15s |%14s |%-20s |%-20s |%-20s |%n","FECHA","HORA SALE","HORA LLEGA","PRECIO","ASIENTOS DISP.","PATENTE","ORIGEN","DESTINO");
            System.out.println("|--------------+-----------+-----------+----------------+---------------+---------------------+---------------------+---------------------|");
            for (int i = 0; i < listViajes.length-1; i++) {
                LocalDate fecha = LocalDate.parse(listViajes[i][0]);
                System.out.printf("|%-13s |%10s |%10s |$%-15s|%14s |%-20s |%-20s |%-20s |%n",fecha.format(formatter),listViajes[i][1],listViajes[i][2],listViajes[i][3],listViajes[i][4],listViajes[i][5],listViajes[i][6],listViajes[i][7]);
                System.out.println("|--------------+-----------+-----------+----------------+---------------+---------------------+---------------------+---------------------|");
            }
            for (int i = listViajes.length-1; i < listViajes.length; i++) {
                LocalDate fecha = LocalDate.parse(listViajes[i][0]);
                System.out.printf("|%-13s |%10s |%10s |$%-15s|%14s |%-20s |%-20s |%-20s |%n",fecha.format(formatter),listViajes[i][1],listViajes[i][2],listViajes[i][3],listViajes[i][4],listViajes[i][5],listViajes[i][6],listViajes[i][7]);
                System.out.println("*--------------*-----------*-----------*----------------*---------------*---------------------*---------------------*---------------------*");
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
        if (fechastr.isBlank()){
            System.out.println("\nFecha vacía, volviendo...");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechastr, formatter);
        }catch (DateTimeException e){
            System.out.println("\nError con parsear la fecha, volviendo...");
            return;
        }
        System.out.printf("%35s ","Hora del viaje[hh:mm] : ");
        String horastr = sc.next();
        if (horastr.isBlank()){
            System.out.println("\nHora vacía, volviendo...");
            return;
        }
        LocalTime hora;
        try {
            hora = LocalTime.parse(horastr);
        }catch (DateTimeException E){
            System.out.println("\nError con parsear la hora, volviendo...");
            return;
        }
        System.out.printf("%35s ","Patente bus : ");
        String patenteBus = sc.next();
        if (patenteBus.isBlank()){
            System.out.println("\nBus invalido, volviendo");
        }
        System.out.println();
        String[][]listaPasajeros;
        try {
            listaPasajeros = SistemaVentaPasajes.getInstance().listPasajerosViaje(fecha,hora,patenteBus);
            if (listaPasajeros.length>0){
                System.out.println("*-----------*---------------------*-----*-------------------------------*-------------------------------*--------------------*");
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
        }catch (SVPException e){
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
                System.out.println("|----------------+----------------+-------------------------------+------------------+-------------+----------------|");
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
        if (nombre.isBlank() || fechastr.isBlank()){
            System.out.println("\nUno o mas campos vacíos, volviendo...");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = null;
        try {
            fecha = LocalDate.parse(fechastr,formatter);
        }catch (DateTimeException e){
            System.out.println("\nError al parsear la fecha");
            return;
        }

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
        }catch (SVPException e){
            System.out.println("\n" + e.getMessage());
        }
    }
    //listarVentasDeEmpresa: Diego Muñoz
    private void listarVentasDeEmpresa(){
        System.out.println("...:::: Listado de ventas de una empresa ::::..");
        System.out.printf("%21s","R.U.T : ");
        String rut = sc.next();
        if (rut.isBlank()){
            System.out.println("\nRut vacío, volviendo...");
            return;
        }
        String[][] datos;
        try {
            datos = ControladorEmpresas.getInstance().listVentasEmpresa(Rut.of(rut));
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
        }catch (SVPException e){
            System.out.println("\n" + e.getMessage());
        }
    }

    private void generarPasajesVenta(){
        System.out.println("...:::: Pasajes de una ::::..");
        System.out.printf("%21s","Ingrese id Documento: ");
        String id = sc.next();
        System.out.printf("%21s","Ingrese tipo documento: ");
        String tipo = sc.next();
        if (id.isBlank() || tipo.isBlank()){
            System.out.println("\nUno o mas campos vacíos, volviendo...");
            return;
        }
        TipoDocumento tipoDoc = TipoDocumento.valueOf(tipo.toUpperCase());
        try {
            SistemaVentaPasajes.getInstance().generatePasajesDeVenta(id,tipoDoc);
        } catch (SVPException e) {
            System.out.println("\n" + e.getMessage());
        }

    }

    private void readDatosIniciales(){
        try {
            SistemaVentaPasajes.getInstance().readDatosIniciales();
            System.out.println("\nDatos iniciales leidos exitosamente");
        } catch (SVPException e) {
            System.out.println("\n"+e.getMessage());
        }
    }

    private void saveDatosSistema(){
        try {
            SistemaVentaPasajes.getInstance().saveDatosSistema();
            System.out.println("\nDatos guardados exitosamente");
        } catch (SVPException e) {
            System.out.println("\n"+e.getMessage());
        }
    }

    private void readDatosSistema(){
        try {
            SistemaVentaPasajes.getInstance().readDatosSistema();
            System.out.println("\nDatos del sistema leidos exitosamente");
        } catch (SVPException e) {
            System.out.println("\n"+e.getMessage());
        }
    }
}