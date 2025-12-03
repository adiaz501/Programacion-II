package P6;

import java.util.Scanner;
import java.util.ArrayList;

public class TestMain {

    private static final String ARCHIVO_DATOS = "biblioteca.json";

    public static void main(String[] args) {
        
        Biblioteca biblio = Biblioteca.cargar(ARCHIVO_DATOS);
        Scanner scanner = new Scanner(System.in);
        
        boolean salir = false;
        
        System.out.println("--- GESTOR DE BIBLIOTECA (" + biblio.getNombre() + ") ---");
        
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    agregarLibros(biblio, scanner);
                    break;
                case "2":
                    agregarEstudiantes(biblio, scanner);
                    break;
                case "3":
                    registrarPrestamo(biblio, scanner);
                    break;
                case "4":
                    modificarHorario(biblio, scanner);
                    break;
                case "5":
                    mostrarResumen(biblio);
                    break;
                case "6":
                    salir = true;
                    break;
                default:
                    System.out.println(" Opción inválida. Inténtalo de nuevo.");
            }
        }
        
        biblio.guardar(ARCHIVO_DATOS);
        System.out.println("\n--- PROCESO FINALIZADO. Datos guardados en " + ARCHIVO_DATOS + ". ---");
        
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Añadir Libros (Autor y Páginas)");
        System.out.println("2. Registrar Estudiantes");
        System.out.println("3. Registrar Préstamo");
        System.out.println("4. Modificar Horario"); 
        System.out.println("5. Mostrar Resumen de Datos");
        System.out.println("6. Salir y Guardar");
        System.out.print("Elige una opción: ");
    }
    

    private static void agregarLibros(Biblioteca biblio, Scanner scanner) {
        System.out.println("\n--- AÑADIR LIBROS ---");
        int numLibros = preguntarCantidad("¿Cuántos libros deseas agregar?", scanner);
        if (numLibros <= 0) return;

        for (int i = 0; i < numLibros; i++) {
            System.out.println("\n[Libro " + (i + 1) + " de " + numLibros + "]");
            
            System.out.print("Título del libro: ");
            String titulo = scanner.nextLine();
            
            System.out.print("ISBN del libro: ");
            String isbn = scanner.nextLine();

            
            System.out.print("Nombre del autor: ");
            String nombreAutor = scanner.nextLine();
            System.out.print("Nacionalidad del autor: ");
            String nacionalidad = scanner.nextLine();
            Autor nuevoAutor = new Autor(nombreAutor, nacionalidad);
            biblio.agregarAutor(nuevoAutor); 
            
            Libro nuevoLibro = new Libro(titulo, isbn, nuevoAutor);
            
            
            int numPaginas = preguntarCantidad("¿Cuántas páginas tiene el libro?", scanner);
            for (int p = 1; p <= numPaginas; p++) {
                System.out.print("Contenido de la Página " + p + ": ");
                String contenido = scanner.nextLine();
                
                Pagina nuevaPagina = new Pagina(p, contenido);
                nuevoLibro.agregarPagina(nuevaPagina); 
            }
            
            biblio.agregarLibro(nuevoLibro);
            
            System.out.println("✓ Libro, Autor y " + nuevoLibro.getPaginas().size() + " Páginas registrados correctamente.");
        }
    }
    
    private static void agregarEstudiantes(Biblioteca biblio, Scanner scanner) {
        System.out.println("\n--- REGISTRAR ESTUDIANTES ---");
        int numEstudiantes = preguntarCantidad("¿Cuántos estudiantes deseas registrar?", scanner);
        if (numEstudiantes <= 0) return;

        for (int i = 0; i < numEstudiantes; i++) {
            System.out.println("\n[Estudiante " + (i + 1) + " de " + numEstudiantes + "]");
            
            System.out.print("Código del estudiante: ");
            String codigo = scanner.nextLine();
            
            System.out.print("Nombre del estudiante: ");
            String nombre = scanner.nextLine();
            
            Estudiante nuevoEstudiante = new Estudiante(codigo, nombre);
            biblio.agregarEstudiante(nuevoEstudiante);
            
            System.out.println("✓ Estudiante registrado correctamente.");
        }
    }
    
    private static void modificarHorario(Biblioteca biblio, Scanner scanner) {
        System.out.println("\n--- MODIFICAR HORARIO ---");
        System.out.print("Horario Actual: ");
        biblio.getHorario().mostrarHorario(); 
        
        System.out.print("Días de atención (ej. L-V, S, D): ");
        String dias = scanner.nextLine();
        
        System.out.print("Hora de Apertura (ej. 09:00): ");
        String apertura = scanner.nextLine();
        
        System.out.print("Hora de Cierre (ej. 18:00): ");
        String cierre = scanner.nextLine();
        
        Horario nuevoHorario = new Horario(dias, apertura, cierre);
        biblio.setHorario(nuevoHorario);
        
        System.out.println("✓ Horario actualizado:");
        nuevoHorario.mostrarHorario();
    }
    
    private static void registrarPrestamo(Biblioteca biblio, Scanner scanner) {
        ArrayList<Estudiante> estudiantes = biblio.getEstudiantes();
        ArrayList<Libro> libros = biblio.getLibros();

        if (estudiantes.isEmpty() || libros.isEmpty()) {
            System.out.println("⚠ Necesitas al menos 1 estudiante y 1 libro para hacer un préstamo.");
            return;
        }

        System.out.println("\n--- REGISTRAR PRÉSTAMO ---");
        
        System.out.println("\n- Paso 1: Seleccionar Estudiante -");
        mostrarEstudiantesConIndice(estudiantes);
        System.out.print("Selecciona el número del estudiante: ");
        int indiceEstudiante = preguntarIndice(scanner, 1, estudiantes.size());
        if (indiceEstudiante == -1) return;
        Estudiante estudianteSeleccionado = estudiantes.get(indiceEstudiante - 1);

        System.out.println("\n- Paso 2: Seleccionar Libro -");
        mostrarLibrosConIndice(libros);
        System.out.print("Selecciona el número del libro a prestar: ");
        int indiceLibro = preguntarIndice(scanner, 1, libros.size());
        if (indiceLibro == -1) return;
        Libro libroSeleccionado = libros.get(indiceLibro - 1);

        for (Prestamo p : biblio.getPrestamos()) {
            if (p.getLibro().equals(libroSeleccionado)) {
                System.out.println("❌ ERROR: El libro '" + libroSeleccionado.getTitulo() + "' ya se encuentra prestado.");
                return;
            }
        }


        System.out.println("\n- Paso 3: Fechas -");
        System.out.print("Fecha de préstamo (ej. YYYY-MM-DD): ");
        String fp = scanner.nextLine();
        System.out.print("Fecha de devolución (ej. YYYY-MM-DD): ");
        String fd = scanner.nextLine();

        biblio.prestarLibro(estudianteSeleccionado, libroSeleccionado, fp, fd);
        System.out.println("✓ Préstamo registrado:");
        System.out.println("  Estudiante: " + estudianteSeleccionado.getNombre() + " | Libro: " + libroSeleccionado.getTitulo());
    }
    
    
    private static int preguntarCantidad(String mensaje, Scanner scanner) {
        int cantidad = -1;
        while (cantidad < 0) {
            System.out.print(mensaje + " (número): ");
            try {
                String input = scanner.nextLine();
                cantidad = Integer.parseInt(input);
                if (cantidad < 0) {
                    System.out.println(" El número debe ser positivo o cero.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Entrada inválida. Por favor, ingresa un número entero.");
            }
        }
        return cantidad;
    }
    
    private static int preguntarIndice(Scanner scanner, int min, int max) {
        int indice = -1;
        while (true) {
            try {
                String input = scanner.nextLine();
                indice = Integer.parseInt(input);
                if (indice >= min && indice <= max) {
                    return indice; 
                } else {
                    System.out.println(" Índice fuera de rango. Debe ser entre " + min + " y " + max + ".");
                    System.out.print("Selecciona un número válido: ");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Entrada inválida. Por favor, ingresa un número entero.");
                System.out.print("Selecciona un número válido: ");
            }
        }
    }
    
    private static void mostrarLibrosConIndice(ArrayList<Libro> libros) {
        for (int i = 0; i < libros.size(); i++) {
            Libro l = libros.get(i);
            System.out.println((i + 1) + ". " + l.getTitulo() + " (Autor: " + l.getAutor().getNombre() + ")");
        }
    }
    
    private static void mostrarEstudiantesConIndice(ArrayList<Estudiante> estudiantes) {
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante e = estudiantes.get(i);
            System.out.println((i + 1) + ". " + e.getNombre() + " (Código: " + e.getCodigo() + ")");
        }
    }

    private static void mostrarResumen(Biblioteca biblio) {
        System.out.println("\n--- RESUMEN DE DATOS ---");
        
        biblio.getHorario().mostrarHorario(); 
        
        System.out.println("\n LIBROS TOTALES (" + biblio.getLibros().size() + "):");
        for (Libro l : biblio.getLibros()) {
            System.out.println("- Título: " + l.getTitulo() + " | ISBN: " + l.getISBN() + " | Autor: " + l.getAutor().toString());
            System.out.println("  Páginas: " + l.getPaginas().size());
            for (Pagina p : l.getPaginas()) {
                p.mostrarPagina();
            }
            System.out.println("---");
        }
        
        System.out.println("\n ESTUDIANTES TOTALES (" + biblio.getEstudiantes().size() + "):");
        for (Estudiante e : biblio.getEstudiantes()) {
            System.out.println("- " + e.toString()); 
        }
        
        System.out.println("\n PRÉSTAMOS ACTIVOS (" + biblio.getPrestamos().size() + "):");
        for (Prestamo p : biblio.getPrestamos()) {
            p.mostrarInfo();
        }
        
        System.out.println("\n--- FIN DEL RESUMEN ---");
    }
}