import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
                case 5:
                    listPasajerosViaje();
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
        SistemaVentaPasajes sistema= new SistemaVentaPasajes();
        sistema.createCliente(identificador,nombrecompleto,telefono,email);

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
        }
    }

    private void createViaje(){}

    private void vendePasajes(){}

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

    }

     private void listVentas() {
        String[][] listaVentas = remoto.listVentas();
        if (listaVentas.length == 0) {
            System.out.println("No existen ventas registradas en el sistema.");
        } else {
            System.out.println("...:::: Listado de ventas :::::...");
            System.out.println("*-----------------*-----------------*-----------------*-----------------*-----------------*");
            System.out.println("| ID DOCUMENT     | TIPO DOCU      | FECHA          | ID PERSONA       | CANT BOLETOS | TOTAL VENTA |");
            System.out.println("*-----------------*-----------------*-----------------*-----------------*-----------------*");
            for (String[] datos : listaVentas) {
                System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-12s | %-12s |\n",
                        datos[0],
                        datos[1],
                        datos[2],
                        datos[3],
                        datos[4],
                        String.format("$%,d", Integer.parseInt(datos[5])));}
            System.out.println("*-----------------*-----------------*-----------------*-----------------*-----------------*");
        }
    }

    private void listViajes(){

        System.out.printf("%35s\n","...:::Listado de viajes:::...");
        System.out.println("*--------------*--------------*--------------*--------------*--------------*");
        System.out.printf("| %12s | %12s | %12s | %12s | %12s |\n", "FECHA", "HORA", "PRECIO", "DISPONIBLES", "PATENTE");
        System.out.println("*--------------*--------------*--------------*--------------*--------------*");

        String[][] datosViajes = remoto.listViajes();

        for (String[] datos: datosViajes){
            String fecha = datos[0];
            String hora = datos[1];
            String precio = datos[2];
            String disponibles = datos[3];
            String patente = datos[4];

            System.out.printf("| %12s | %12s | %12s | %12s | %12s |\n", fecha, hora, precio, disponibles, patente);
        }
        System.out.println("*--------------*--------------*--------------*--------------*--------------*");
    }
}
