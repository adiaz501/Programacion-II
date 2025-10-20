package P5;
import java.util.*;

class Pagina {
 private int numero;
 private String contenido;

 public Pagina(int numero, String contenido) {
     this.numero = numero;
     this.contenido = contenido;
 }

 public void mostrarPagina() {
     System.out.println("Página " + numero + ": " + contenido);
 }
}

class Horario {
 private String dias;
 private String horaApertura;
 private String horaCierre;

 public Horario(String dias, String horaApertura, String horaCierre) {
     this.dias = dias;
     this.horaApertura = horaApertura;
     this.horaCierre = horaCierre;
 }

 public void mostrarHorario() {
     System.out.println("Horario de Atención: " + dias + " de " + horaApertura + " a " + horaCierre);
 }
}

class Libro {
 private String titulo;
 private String ISBN;
 private List<Pagina> paginas;

 public Libro(String titulo, String ISBN, List<String> contenidoPaginas) {
     this.titulo = titulo;
     this.ISBN = ISBN;
     this.paginas = new ArrayList<>();
     for (int i = 0; i < contenidoPaginas.size(); i++) {
         paginas.add(new Pagina(i + 1, contenidoPaginas.get(i)));
     }
 }

 public String getTitulo() {
     return titulo;
 }

 public String getISBN() {
     return ISBN;
 }

 public void leer() {
     System.out.println("\nLeyendo libro: " + titulo);
     for (Pagina p : paginas) {
         p.mostrarPagina();
     }
 }
}

class Autor {
 private String nombre;
 private String nacionalidad;

 public Autor(String nombre, String nacionalidad) {
     this.nombre = nombre;
     this.nacionalidad = nacionalidad;
 }

 public void mostrarInfo() {
     System.out.println("Autor: " + nombre + " (" + nacionalidad + ")");
 }
}

class Estudiante {
 private String codigo;
 private String nombre;

 public Estudiante(String codigo, String nombre) {
     this.codigo = codigo;
     this.nombre = nombre;
 }

 public String getCodigo() {
	 return codigo;
 }
 
 public String getNombre() {
     return nombre;
 }
 
 public void mostrarInfo() {
     System.out.println("- Código: " + codigo);
     System.out.println("- Estudiante: " + nombre);
 	}
}

class Prestamo {
 private String fechaPrestamo;
 private String fechaDevolucion;
 private Estudiante estudiante;
 private Libro libro;

 public Prestamo(Estudiante estudiante, Libro libro, String fechaPrestamo, String fechaDevolucion) {
     this.estudiante = estudiante;
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
}

class Biblioteca {
 private String nombre;
 private List<Libro> libros;
 private List<Autor> autores;
 private List<Prestamo> prestamos;
 private Horario horario;

 public Biblioteca(String nombre) {
     this.nombre = nombre;
     this.libros = new ArrayList<>();
     this.autores = new ArrayList<>();
     this.prestamos = new ArrayList<>();
     this.horario = new Horario("Lunes a Viernes", "08:00", "20:00");
 }

 public void agregarLibro(Libro libro) {
     libros.add(libro);
 }

 public void agregarAutor(Autor autor) {
     autores.add(autor);
 }

 public void prestarLibro(Estudiante estudiante, Libro libro, String fechaPrestamo, String fechaDevolucion) {
     prestamos.add(new Prestamo(estudiante, libro, fechaPrestamo, fechaDevolucion));
 }

 public void mostrarEstado() {
     System.out.println("=== Biblioteca: " + nombre + " ===");
     horario.mostrarHorario();
     System.out.println("\nLibros Disponibles:");
     for (Libro l : libros) {
         System.out.println("- " + l.getTitulo() + " (ISBN: " + l.getISBN() + ")");
     }
     System.out.println("\nAutores Registrados:");
     for (Autor a : autores) {
         a.mostrarInfo();
     }
     System.out.println("\nPréstamos Activos:");
     for (Prestamo p : prestamos) {
         p.mostrarInfo();
     }
 }

 public void cerrarBiblioteca() {
     System.out.println("\nCerrando biblioteca " + nombre);
     prestamos.clear();
     System.out.println("Préstamos eliminados.");
 	}
}


