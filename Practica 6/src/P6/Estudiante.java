package P6;

import java.io.Serializable;

public class Estudiante implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nombre;

    public Estudiante(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    public void mostrarInfo() {
        System.out.println("- Código: " + codigo);
        System.out.println("- Estudiante: " + nombre);
    }
}
