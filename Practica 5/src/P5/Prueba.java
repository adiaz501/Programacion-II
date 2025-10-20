package P5;
import java.util.Arrays;
public class Prueba {
	 public static void main(String[] args) {
	     Biblioteca biblio = new Biblioteca("Biblioteca UMSA");
	     
	     Autor autor1 = new Autor("Gabriel García Márquez", "Colombia");
	     Autor autor2 = new Autor("Jane Austen", "Reino Unido");

	     Libro libro1 = new Libro("Cien Años de Soledad", "ISBN12345",
	             Arrays.asList("Inicio del libro...", "......", "......", ".....", "Final del libro."));
	     Libro libro2 = new Libro("Orgullo y Prejuicio", "ISBN67890",
	             Arrays.asList("Capítulo 1...", "Capítulo ...", "Capítulo ..."));

	     biblio.agregarLibro(libro1);
	     biblio.agregarLibro(libro2);
	     biblio.agregarAutor(autor1);
	     biblio.agregarAutor(autor2);

	     Estudiante est1 = new Estudiante("1825401", "Alison Diaz");
	     Estudiante est2 = new Estudiante("2025002", "Natalia Flores");

	     System.out.println("==============Usuarios Registrados==============");
	     est1.mostrarInfo();
	     est2.mostrarInfo();

	     biblio.prestarLibro(est1, libro1, "19/10/2025", "25/10/2025");
	     biblio.prestarLibro(est2, libro2, "19/10/2025", "26/10/2025");
	     
	     System.out.println("------------------------------------------------");
	     
	     biblio.mostrarEstado();

	     libro1.leer();
	     libro2.leer();

	     biblio.cerrarBiblioteca();
	 }
}	 

	 
