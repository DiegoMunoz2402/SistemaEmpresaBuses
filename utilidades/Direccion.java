//Class direccion : Rodolfo

package utilidades;

import java.io.Serializable;

public class Direccion implements Serializable {

    private String calle;
    private int numero;
    private String comuna;

    public Direccion(String calle, int numero, String comuna) {
        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getComuna() {
        return comuna;
    }


    public String toString() {
        return calle + " " + numero + " " + comuna;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Direccion direccion = (Direccion) o;
        return direccion.equals(direccion.calle) &&
                direccion.equals(direccion.numero) &&
                direccion.equals(direccion.comuna);
    }
}
