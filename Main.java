import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class Main {
    private Scanner sc=new Scanner(System.in);
    private SistemaVentaPasajes remoto = new SistemaVentaPasajes();
    public static void main(String[] args) {
        Main main = new Main();
        main.menu();
    }

    private void menu(){
        remoto = new SistemaVentaPasajes();
        int op;
        do {
            System.out.println("======================");
            System.out.println("...::: Menú principal :::..");
            System.out.println();
            System.out.println("  1) Crear cliente");
            System.out.println("  2) Crear bus");
            System.out.println("  3) Crear viaje");
            System.out.println("  4) Vender pasajes");
            System.out.println("  5) Lista de pasajeros");
            System.out.println("  6) Lista de ventas");
            System.out.println("  7) Lista de viajes");
            System.out.println("  8) Salir");
            System.out.println("----------------------");
            System.out.println();
            System.out.print("..:: Ingrese número de opcion: ");
            op = sc.nextInt();
            switch (op){
                case 1:createCliente();
                    break;
                case 2:
                    createBus();
                    break;
                case 3:
                    createViaje();
                    break;
                case 4:
                    vendePasajes();
                    break;
                case 5:
                    listPasajerosViaje();
                    break;
                case 6:
                    listVentas();
                    break;
                case 7:
                    listViajes();
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida...");
                    break;
            }
        }while(op!=8);
    }

    private void createCliente(){
        sc.useDelimiter("[ \\t\\r\\n]+");

        System.out.println(":::: Crear un nuevo Cliente ::::");
        System.out.println("Rut[1] o Pasaporte[2] : ");
        int tipo = sc.nextInt();
        sc.nextLine();
        IdPersona identificador= null;
        if(tipo==1){
            System.out.println("R.U.T : ");
            String rut= sc.next();
            identificador= (IdPersona) Rut.of(rut);
        } else if (tipo==2) {
            System.out.println("NUMERO DE PASAPORTE : ");
            String numero= sc.next();
            System.out.println("NACIONALIDAD : ");
            String nacionalidad= sc.next();
            identificador= Pasaporte.of(numero,nacionalidad);
        }
        System.out.println("Sr.[1] o Sra. [2] : ");
        int opcion = sc.nextInt();
        sc.nextLine();
        Tratamiento tratamiento;
        if(opcion==1){
            tratamiento= Tratamiento.SR;
        }else{
            tratamiento= Tratamiento.SRA;
        }
        System.out.println("Nombres : ");
        String nombres = sc.nextLine();
        System.out.println("Apellido Paterno : ");
        String apellidoPaterno = sc.next();
        System.out.println("Apellido Materno : ");
        String apellidoMaterno = sc.next();
        System.out.println("Telefono movil : ");
        String telefono = sc.next();
        System.out.println("Email : ");
        String email = sc.next();
        Nombre nombrecompleto= new Nombre(nombres,apellidoPaterno,apellidoPaterno,tratamiento);
        remoto.createCliente(identificador,nombrecompleto,telefono,email);
        System.out.println("...::::Cliente guardado exitosamente::::....");

    }
    private void createBus(){
        System.out.println("...:::: Creación de un nuevo BUS ::::..");
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
        if (remoto.createBus(patente,marca,modelo,numAsientos)){
            System.out.println("...:::: Bus guardado exitosamente ::::..");
        }else{
	    System.out.println("...:::: No se pudo crear el bus, existe otro con la misma patente o los datos son invalidos... ::::..");	
	    }
    }

    private void createViaje(){

        DateTimeFormatter tipoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        System.out.println("...:::: Creación de un nuevo Viaje ::::....");
        System.out.println();
        System.out.printf("%21s\n", "Fecha[dd/mm/yyyy] : ");
        LocalDate fecha = LocalDate.parse(sc.next(),tipoFecha);
        System.out.printf("%21s\n", "Hora[hh:mm] : ");
        LocalTime hora = LocalTime.parse(sc.next());
        System.out.printf("%21s\n", "Precio : ");
        int precio = Integer.parseInt(sc.next());
        System.out.printf("%21s\n", "Patente Bus : ");
        String patenteBus = sc.next();
        System.out.println();

        if (remoto.createViaje(fecha,hora,precio,patenteBus)){
            System.out.println("...:::: Viaje guardado existosamente ::::....");
        }else{
            System.out.println("...::::     Error al crear viaje     ::::....");
        }
    }

    private void vendePasajes() {


        Scanner scanner = new Scanner(System.in);

        System.out.println("...:::: Venta de pasajes ::::...");

        System.out.println(":::: Datos de la Venta");
        System.out.print("Ingrese el ID del Documento: ");
        String idDoc = scanner.nextLine();

        System.out.print("Tipo documento: [1] Boleta [2] Factura: ");
        int tipoDocInput = scanner.nextInt();

        TipoDocumento tipo = (tipoDocInput == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;

        System.out.print("Fecha de venta (dd/MM/yyyy): ");
        String fechaVentaStr = scanner.next();
        LocalDate fechaVenta = LocalDate.parse(fechaVentaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println(":::: Datos del cliente");
        System.out.print("Rut[1] o Pasaporte[2]: ");
        int tipoIdCliente = scanner.nextInt();


        IdPersona idCliente;
        IdPersona idPasajero;

        if (tipoIdCliente == 1) { // Rut
            System.out.println("Formato Rut: 12345678-9");
            String rutConDv = sc.next();
            idCliente = Rut.of(rutConDv);
             if (idCliente == null) {
        System.out.println("El RUT ingresado no es válido.");
                return;   }


        } else {                  // Pasaporte
            System.out.println("Pasaporte");
            System.out.println("Ingrese la nacionalidad:");
            String nacionalidad= scanner.next();
            System.out.println("Ingrese el número de pasaporte:");
            String numero= scanner.next();
            idCliente = Pasaporte.of(numero, nacionalidad);
            if (idCliente== null){
                System.out.println("Formato del Pasaporte no valido");
                return;
            }

        }
                  
        boolean venta = remoto.iniciaVenta(idDoc, tipo, fechaVenta,idCliente);

        if (!venta){System.out.println(" ...:::: No se pudo iniciar la venta ::::...");
        return;}


        System.out.println(":::: Pasajes a vender");
        System.out.print("Cantidad de pasajes: ");
        int cantidadPasajes = scanner.nextInt();

        System.out.print("Fecha de viaje (dd/MM/yyyy): ");
        String fechaViajeStr = scanner.next();
        LocalDate fechaViaje = LocalDate.parse(fechaViajeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println(":::: Listado de horarios disponibles ::::");
        String[][] viajesDisponibles = remoto.getHorariosDisponibles(fechaViaje);

        if (viajesDisponibles.length == 0) {
            System.out.println("No hay viajes disponibles con suficientes asientos en la fecha indicada.");
            return;           
        }

        System.out.println(String.format("%-5s | %-10s | %-10s | %-10s | %-10s", "ID", "BUS", "SALIDA", "VALOR", "ASIENTOS"));

        for (int i = 0; i < viajesDisponibles.length; i++) {
            String[] viaje = viajesDisponibles[i];

            System.out.println(String.format("%-5d | %-10s | %-10s | %-10s | %-10s",
                    i + 1,
                    viaje[1],
                    viaje[2],
                    viaje[3],
                    viaje[0]
            ));
        }

        System.out.print("Seleccione el viaje: ");
        int viajeSeleccionado = scanner.nextInt();
        if (viajeSeleccionado < 0 || viajeSeleccionado >= viajesDisponibles.length) {
            System.out.println("Selección inválida.");
            return;
        }


        String patenteSelec = viajesDisponibles[viajeSeleccionado - 1][1];


        String[][] asientosDisponibles = remoto.listAsientosDeViaje(fechaViaje, LocalTime.parse(viajesDisponibles[viajeSeleccionado - 1][2]), patenteSelec);
        if (asientosDisponibles == null || asientosDisponibles.length == 0) {
            System.out.println("...:::: No hay asientos disponibles para el viaje seleccionado ::::...");
            return;
        }

        System.out.println(":::: Asientos disponibles para el viaje seleccionado");
        for (String [] asiento : asientosDisponibles) {
            System.out.printf("| Asiento %-15s | Estado %-15s |\n", asiento[0], asiento[1]);
        }                   // si asiento esta ocupado mostrar * y no el numero // quizas con if?

        System.out.print("Seleccione sus asientos [separe por comas]: ");
        String asientosSeleccionados = scanner.next();
        String[] asientosArray = asientosSeleccionados.split(",");

        if (asientosArray.length != cantidadPasajes) {
            System.out.println("Debe seleccionar el mismo número de asientos que pasajes.");
            return;
        }

            for (int i = 0; i < cantidadPasajes; i++) {           
                int asiento = Integer.parseInt(asientosArray[i].trim());        
                System.out.println("Datos del pasajero " + (i + 1));
                System.out.print("Rut[1] o Pasaporte[2]: ");
                int tipoIdentificadorPasajero = scanner.nextInt();

                System.out.print("Si existe el pasajero indique el identificador del Pasajero[si es pasaporte separe por espacio][Si no existe escriba (no existe)]: ");
                String identificador = scanner.next();
                idPasajero = convertToIdPersona(identificador);
                String nombrePass = remoto.getNombrePasajero(idPasajero);


                if (nombrePass == null) {
                    String tipoId; //este era de uno duplicado
                    if (tipoIdentificadorPasajero == 1) { // Rut
                        System.out.println("Formato Rut: 12345678-9");
                        String rutConDv = scanner.nextLine();
                        tipoId = String.valueOf(Rut.of(rutConDv));
                        idPasajero = Rut.of(tipoId);
                        if (tipoId == null) {
                            System.out.println("Formato del Rut no valido");
                            return;
                        }
                    } else {                  // Pasaporte
                        System.out.println("Pasaporte");
                        System.out.println("Ingrese la nacionalidad:");
                        String nacionalidad = scanner.next();
                        System.out.println("Ingrese el número de pasaporte:");
                        String numero = scanner.next();
                        idPasajero = Pasaporte.of(numero, nacionalidad);
                    }
                }


            System.out.print("Sr.[1] o Sra.[2] :");
                int trata = scanner.nextInt();

            Tratamiento tratamiento = null;
            switch(trata) {
                case 1:
                    tratamiento = Tratamiento.SR; break;
                case 2:
                    tratamiento = Tratamiento.SRA; break;
            }

            scanner.next();
            System.out.print("Ingrese el nombre del pasajero: ");
            String nombre = scanner.next();
            System.out.print("Ingrese el apellido Paterno del pasajero: ");
            String apellidoPaterno = scanner.next();
            System.out.print("Ingrese el apellido Materno del pasajero: ");
            String apellidoMaterno = scanner.next();
            System.out.print("Ingrese el tratamiento del contacto Sr.[1] o Sra.[2] :");
            int opContacto = sc.nextInt();
            Nombre nombrePasajero = new Nombre(nombre,apellidoPaterno,apellidoMaterno,tratamiento);

            Tratamiento trataContacto = null;
            switch(opContacto) {
                case 1:
                    trataContacto = Tratamiento.SR; break;
                case 2:
                    trataContacto = Tratamiento.SRA; break;
            }

            System.out.println("Ingrese el nombre del Contacto");
            String nomContacto = scanner.next();
            System.out.println("Ingrese el apellido Paterno del Contacto");
            String apPaternoContacto = scanner.next();
            System.out.println("Ingrese el apellido Materno del Contacto");
            String apMaternoContacto = scanner.next();
            System.out.println("Ingrese el Fono del Contacto");
            String fonoContaco = scanner.next();
            Nombre nombreContacto = new Nombre(nomContacto, apPaternoContacto, apMaternoContacto, trataContacto);

            Pasajero pasajero = new Pasajero(idPasajero,nombrePasajero,nombreContacto,fonoContaco);
            if (!remoto.createPasajero(idPasajero, nombrePasajero,pasajero.getTelefono(),pasajero.getNomContacto(), pasajero.getFonoContacto())) {
                System.out.println("El pasajero ya existe...");
                return;
            }

            if (!remoto.vendePasaje(idDoc, fechaViaje, LocalTime.parse(viajesDisponibles[viajeSeleccionado - 1][2]), patenteSelec, asiento, idCliente)) {
                System.out.println("No se pudo vender el pasaje para el asiento seleccionado: ");
            }
        }

        int montoTotal = remoto.getMontoVenta(idDoc, tipo);
        System.out.println("Monto total de la venta: " + montoTotal);


        String patenteBus = viajesDisponibles[viajeSeleccionado - 1][1];
        LocalTime horaViaje = LocalTime.parse(viajesDisponibles[viajeSeleccionado - 1][2]);
        String [][] pasajesVendidos = remoto.listPasajeros(fechaViaje,LocalTime.parse(viajesDisponibles[viajeSeleccionado - 1][1]), patenteSelec);
        System.out.println("...:::: Pasajes vendidos ::::...");
        for (String [] pasaje : pasajesVendidos) {
            System.out.println(":::::::::::::  Pasaje :::::::: ");
            System.out.println("Numero de pasaje: " + pasaje[0]);
            System.out.println("Fecha de viaje  : " + fechaViaje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Hora de viaje   : " + horaViaje);
            System.out.println("Patente bus    : " + patenteBus);
            System.out.println("Asiento         : " + pasaje[2]);
            System.out.println("Rut / Pasaporte |   : " + pasaje[1]);
            System.out.println("Nombre pasajero : " + pasaje[3]);
            System.out.println();
        }



    }

    private void listPasajerosViaje(){
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
        String[][] listaPasajeros = remoto.listPasajeros(fecha,hora,patenteBus);
        if (listaPasajeros.length == 0) {
            System.out.println("No existe un bus con la patente indicada...");
        }else {
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
        }
    }

    private void listVentas() {
        String[][] listaVentas = remoto.listVentas();
        if (listaVentas.length == 0) {
            System.out.println("No existen ventas registradas en el sistema.");
        } else {
            System.out.println("...:::: Listado de ventas :::::...");
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("| ID DOCUMENT     | TIPO DOCU      | FECHA          | ID PERSONA       | CANT BOLETOS | TOTAL VENTA |");
            System.out.println("-------------------------------------------------------------------------------------");
            for (String[] datos : listaVentas) {
                System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-12s | %-12s |\n",
                        datos[0],
                        datos[1],
                        datos[2],
                        datos[3],
                        datos[4],
                        String.format("$%,d", Integer.parseInt(datos[5])));}
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

    private void listViajes(){

        System.out.printf("%35s\n","...:::Listado de viajes:::...");
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("| %12s | %12s | %12s | %12s | %12s |\n", "FECHA", "HORA", "PRECIO", "DISPONIBLES", "PATENTE");
        System.out.println("----------------------------------------------------------------------");

        String[][] datosViajes = remoto.listViajes();

        for (String[] datos: datosViajes){
            String fecha = datos[0];
            String hora = datos[1];
            String precio = datos[2];
            String disponibles = datos[3];
            String patente = datos[4];

            System.out.printf("| %12s | %12s | %12s | %12s | %12s |\n", fecha, hora, precio, disponibles, patente);
        }
        System.out.println("----------------------------------------------------------------------");
    }

    private IdPersona convertToIdPersona(String input) {
        // Verificamos si es un RUT (contiene guion "-")
        if (input.contains("-")) {
            return (IdPersona) Rut.of(input);
        } else {
            String[] partes = input.split(" ");
            if (partes.length == 2) {
                return (IdPersona) Pasaporte.of(partes[0], partes[1]); // Número del pasaporte y nacionalidad
            }
        }
        return null;
    }
}
