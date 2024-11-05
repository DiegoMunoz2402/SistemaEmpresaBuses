//Class nombre : Diego Muñoz

package utilidades;

public class Nombre {
    private String nombres;
    private Tratamiento tratamiento;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Nombre(String nombres, String apellidoPaterno, String apellidoMaterno, Tratamiento tratamiento){
        this.nombres=nombres;
        this.apellidoMaterno=apellidoMaterno;
        this.apellidoPaterno=apellidoPaterno;
        this.tratamiento=tratamiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    public String toString(){
        return tratamiento + " " + nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Nombre nombre = (Nombre) o;
        return nombres.equals(nombre.nombres) &&
                apellidoPaterno.equals(nombre.apellidoPaterno) &&
                apellidoMaterno.equals(nombre.apellidoMaterno);
    }
}
