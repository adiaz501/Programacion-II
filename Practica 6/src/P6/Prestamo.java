package P6;

import java.io.Serializable;

public class Prestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fechaPrestamo;
    private String fechaDevolucion;
    
    private Estudiante estudiante; 
    
    private Libro libro;

    public Prestamo(Estudiante estudianteSeleccionado, Libro libro, String fechaPrestamo, String fechaDevolucion) {
        this.estudiante = estudianteSeleccionado;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public void mostrarInfo() {
        System.out.println("Préstamo:");
        System.out.println("- Estudiante: " + estudiante.getNombre());
        System.out.println("- Código: " + estudiante.getCodigo());
        System.out.println("- Libro: " + libro.getTitulo());
        System.out.println("- Fecha préstamo: " + fechaPrestamo);
        System.out.println("- Fecha devolución: " + fechaDevolucion);
    }

    // Getters para GUI
    public String getNombreEstudiante() { return estudiante.getNombre(); }
    public String getTituloLibro() { return libro.getTitulo(); }
    public String getFechaDevolucion() { return fechaDevolucion; }
    
    // Getters para los objetos completos (útiles para la lógica)
    public Estudiante getEstudiante() { return estudiante; }
    public Libro getLibro() { return libro; }
}