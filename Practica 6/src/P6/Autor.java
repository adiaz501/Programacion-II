package P6;

import java.io.Serializable;

public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String nacionalidad;

    public Autor(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public void mostrarInfo() {
        System.out.println("Autor: " + nombre + " (" + nacionalidad + ")");
    }

    public String getNombre() { return nombre; }
    public String getNacionalidad() { return nacionalidad; }

    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ")";
    }
}
