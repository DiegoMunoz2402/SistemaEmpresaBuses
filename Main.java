import java.util.Scanner;

public class Main {
    private Scanner sc=new Scanner(System.in);
    private SistemaVentaPasajes remoto;
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

            }
        }while(op!=8);
    }
}