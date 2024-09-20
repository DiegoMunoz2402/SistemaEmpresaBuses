//Clase Completa Cliente, Autor: Rodolfo Salas
 import java.util.List;
 import java.util.ArrayList;
 public class Cliente extends Persona {
    //Atributos
    private String email;
    private List<Venta> ventas;
    //Constructor
     public Cliente(IdPersona idPersona,Nombre nombre, String email) {
         super(idPersona, nombre); //constructor superclase Persona
         this.email = email;
         this.ventas = new ArrayList<>();
     }

     //Metodos
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addVenta(Venta venta ){
       ventas.add(venta);
    }
  
     public Venta[] getVentas(){
        return ventas.toArray(new Venta[0]);
    }
}
