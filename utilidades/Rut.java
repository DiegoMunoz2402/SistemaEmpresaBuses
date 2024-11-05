//Class rut : Javiera Duarte
package utilidades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rut implements IdPersona {

    private final int numero;
    private final char dv;

    //Attributes

    private Rut (int num, char dv){
        this.numero = num;
        this.dv = dv;
    }

    public int getNumero() {
        return numero;
    }

    public char getDv() {
        return dv;
    }

    public static Rut of(String rutConDv) {
        Pattern pattern = Pattern.compile("(\\d{1,8})-([\\dKk])");
        Matcher matcher = pattern.matcher(rutConDv);

        if (matcher.matches()) {
            String numString = matcher.group(1);
            char dv = matcher.group(2).charAt(0);

            int num = Integer.parseInt(numString);

            if (isDvValid(rutConDv)) {
                return new Rut(num, dv);
            }
        }

        return null;  // Si el formato o validación fallan
    }

    private static boolean isDvValid(String rut) {

        String[] partes = rut.split("-");
        if (partes.length != 2) {
            return false;
        }

        String numeroStr = partes[0];
        char dv = partes[1].charAt(0);

        if (!numeroStr.matches("\\d+")) {
            return false;
        }

        int numero = Integer.parseInt(numeroStr);
        int suma = 0;
        int factor = 2;

        for (int i = numero; i > 0; i /= 10) {
            suma += (i % 10) * factor;
            factor = (factor == 7) ? 2 : factor + 1;
        }

        int resto = 11 - (suma % 11);
        String calculo;

        if (resto == 11) {
            calculo = "0";
        } else if (resto == 10) {
            calculo = "K";
        } else {
            calculo = String.valueOf(resto);
        }
        return String.valueOf(dv).equalsIgnoreCase(calculo);
    }



    @Override
    public String toString(){
        String numString = String.format("%,d", numero).replace(",",".");
        return numString+ "-"+ dv;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null|| getClass() != obj.getClass()) return false;
        Rut rut = (Rut) obj;
        return numero == rut.numero && dv == rut.dv;
    }

    @Override
    public int hashCode(){
        int result = Integer.hashCode(numero);
        result = 31 * result + Character.hashCode(dv);
        return result;
    }
}
