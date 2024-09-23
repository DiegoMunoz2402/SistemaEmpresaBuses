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
                case 1:
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

    private void createCliente(){}

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

    private void listVentas(){}

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