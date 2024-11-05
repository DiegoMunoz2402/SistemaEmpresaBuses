package vista;

import controlador.ControladorEmpresas;
import controlador.SistemaVentaPasajes;
import excepciones.SistemaVentaPasajesException;
import utilidades.Direccion;
import utilidades.Rut;

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
                case 4 ->
                case 5 ->
                case 6 ->
                case 7 ->
                case 8 ->
                case 9 ->
                case 10 ->
                case 11 ->
                case 12 ->
                case 13 ->
                case 14 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida");
            }
        } while (op!=14);
    }

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
}
