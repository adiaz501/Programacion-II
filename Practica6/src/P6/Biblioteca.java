package P6;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Biblioteca {

    private String nombre;
    private ArrayList<Libro> libros;
    private ArrayList<Autor> autores;
    private ArrayList<Estudiante> estudiantes;
    private ArrayList<Prestamo> prestamos;
    private Horario horario;

    private static class BibliotecaData {
        String nombre;
        ArrayList<Libro> libros;
        ArrayList<Autor> autores;
        ArrayList<Estudiante> estudiantes;
        ArrayList<Prestamo> prestamos;
        Horario horario;
    }

    public Biblioteca(String nombre) {
        this.nombre = nombre;
        this.libros = new ArrayList<>();
        this.autores = new ArrayList<>();
        this.estudiantes = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.horario = new Horario("Lunes-Viernes", "08:00", "18:00");
    }

    private void repararListas() {
        if (libros == null) libros = new ArrayList<>();
        if (autores == null) autores = new ArrayList<>();
        if (estudiantes == null) estudiantes = new ArrayList<>();
        if (prestamos == null) prestamos = new ArrayList<>();
        if (horario == null) horario = new Horario("Lunes-Viernes", "08:00", "18:00");
    }

    // --- GETTERS Y AGREGAR (CREATE) ---
    public String getNombre() { return nombre; }
    public Horario getHorario() { repararListas(); return horario; }
    public ArrayList<Libro> getLibros() { repararListas(); return libros; }
    public ArrayList<Autor> getAutores() { repararListas(); return autores; }
    public ArrayList<Estudiante> getEstudiantes() { repararListas(); return estudiantes; }
    public ArrayList<Prestamo> getPrestamos() { repararListas(); return prestamos; }

    public void setHorario(Horario horario) { this.horario = horario; }

    public void agregarLibro(Libro libro) { repararListas(); libros.add(libro); }
    public void agregarAutor(Autor autor) { repararListas(); autores.add(autor); }
    public void agregarEstudiante(Estudiante e) { repararListas(); estudiantes.add(e); }
    public void prestarLibro(Estudiante e, Libro l, String fp, String fd) {
        repararListas();
        prestamos.add(new Prestamo(e, l, fp, fd));
    }
    
    // --- ELIMINAR (DELETE) ---
    
    /**
     * Registra la devolución de un libro eliminando el préstamo de la lista activa.
     * REQUIERE que la clase Prestamo implemente correctamente equals() y hashCode().
     * @param prestamo El objeto Prestamo a eliminar.
     * @return true si el préstamo fue eliminado, false si no se encontró.
     */
    public boolean eliminarPrestamo(Prestamo prestamo) {
        repararListas();
        // ESTE ES EL MÉTODO FALTANTE
        return prestamos.remove(prestamo); 
    }
    
    public boolean eliminarLibro(Libro libro) {
        repararListas();
        // 1. Verificar préstamos activos
        for (Prestamo p : prestamos) {
            if (p.getLibro().equals(libro)) {
                return false; // No se puede eliminar si tiene préstamo
            }
        }
        // 2. Eliminar
        return libros.remove(libro);
    }

    public boolean eliminarEstudiante(Estudiante estudiante) {
        repararListas();
        // 1. Verificar préstamos activos
        for (Prestamo p : prestamos) {
            if (p.getEstudiante().equals(estudiante)) {
                return false; // No se puede eliminar si tiene préstamo
            }
        }
        // 2. Eliminar
        return estudiantes.remove(estudiante);
    }
    
    public boolean eliminarAutor(Autor autor) {
        repararListas();
        // 1. Verificar si tiene libros asociados
        for (Libro l : libros) {
            if (l.getAutor().equals(autor)) {
                return false; // No se puede eliminar si tiene libros
            }
        }
        // 2. Eliminar
        return autores.remove(autor);
    }

    // --- GUARDAR (JSON - GSON) ---
    public void guardar(String archivo) {
        repararListas();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(archivo)) {
            // El 'this' se serializa a través de sus campos, incluyendo los de la superclase
            gson.toJson(this, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar la biblioteca (JSON): " + e.getMessage());
        }
    }

    // --- CARGAR (JSON - GSON) ---
    public static Biblioteca cargar(String archivo) {
        Gson gson = new Gson();
        File f = new File(archivo);

        if (f.exists() && f.length() > 0) {
            try (FileReader reader = new FileReader(archivo)) {
                // Gson carga los datos en un objeto temporal y lo copiamos
                BibliotecaData data = gson.fromJson(reader, BibliotecaData.class);
                Biblioteca b = new Biblioteca(data.nombre != null ? data.nombre : "Biblioteca U");
                
                // Copiar listas y asegurar no nulos
                b.libros = data.libros;
                b.autores = data.autores;
                b.estudiantes = data.estudiantes;
                b.prestamos = data.prestamos;
                b.horario = data.horario;
                
                b.repararListas();
                return b;

            } catch (IOException e) {
                System.err.println("Error al cargar la biblioteca (JSON): " + e.getMessage());
                // Si falla la carga, crear una nueva instancia
                return new Biblioteca("Biblioteca Universidad");
            }
        } else {
            System.out.println("Archivo de datos no encontrado o vacío. Creando nueva biblioteca...");
            return new Biblioteca("Biblioteca Universidad");
        }
    }
}