package P6;

import java.io.*;
import java.util.ArrayList;

public class Biblioteca implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private ArrayList<Libro> libros;
    private ArrayList<Autor> autores;
    private ArrayList<Estudiante> estudiantes;
    private ArrayList<Prestamo> prestamos;
    private Horario horario;

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

    public String getNombre() { 
    	return nombre; 
    	}
    
    public Horario getHorario() { 
        repararListas();
        return horario; 
    }
    
    public ArrayList<Libro> getLibros() { 
    	repararListas(); 
    	return libros; 
    	
    }
    
    public ArrayList<Autor> getAutores() { 
    	repararListas(); 
    	return autores; 
    }
    
    public ArrayList<Estudiante> getEstudiantes() { 
    	repararListas(); 
    	return estudiantes; 
    }
    
    public ArrayList<Prestamo> getPrestamos() { 
    	repararListas(); 
    	return prestamos; 
    }
    
    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public void agregarLibro(Libro libro) { 
    	repararListas(); 
    	libros.add(libro); 
    	}
    
    public void agregarAutor(Autor autor) { 
		repararListas(); 
		autores.add(autor); 
		}
    
    public void agregarEstudiante(Estudiante e) { 
    	repararListas(); 
    	estudiantes.add(e); 
    	}
    
    public void prestarLibro(Estudiante e, Libro l, String fp, String fd) {
        repararListas();
        prestamos.add(new Prestamo(e, l, fp, fd));
    }

    // --- GUARDAR (SERIALIZACIÓN) ---
    public void guardar(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Error al guardar la biblioteca: " + e.getMessage());
        }
    }

    // --- CARGAR (DESERIALIZACIÓN) ---
    public static Biblioteca cargar(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Biblioteca b = (Biblioteca) ois.readObject();
            b.repararListas();
            return b;
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de datos no encontrado. Creando nueva biblioteca...");
            return new Biblioteca("Biblioteca UMSA");
        } catch (Exception e) {
            System.err.println("Error al cargar la biblioteca. Creando nueva biblioteca...");
            return new Biblioteca("Biblioteca UMSA");
        }
    }
}