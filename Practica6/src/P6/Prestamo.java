package P6;

public class Prestamo {
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

    public String getNombreEstudiante() { return estudiante.getNombre(); }
    public String getTituloLibro() { return libro.getTitulo(); }
    
    public String getFechaPrestamo() { return fechaPrestamo; } 
    public String getFechaDevolucion() { return fechaDevolucion; }
    
    public Estudiante getEstudiante() { return estudiante; }
    public Libro getLibro() { return libro; }
    
    @Override
    public String toString() {
        return "Libro: " + this.getTituloLibro() + 
               " | Estudiante: " + this.getNombreEstudiante() + 
               " | Devolución: " + this.getFechaDevolucion();
    }
}