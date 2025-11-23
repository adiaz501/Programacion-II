package P6;

import java.io.Serializable;
import java.util.ArrayList;

public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titulo;
    private String ISBN;
    private Autor autor;
    private ArrayList<Pagina> paginas; 

    
    public Libro(String titulo, String ISBN, Autor autor) {
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.autor = autor;
        this.paginas = new ArrayList<>(); 
    }

    public String getTitulo() { return titulo; }
    public String getISBN() { return ISBN; }
    public Autor getAutor() { return autor; }
    public ArrayList<Pagina> getPaginas() { return paginas; } 

    
    public void agregarPagina(Pagina pagina) {
        paginas.add(pagina);
    }

    public void mostrarInfo() {
        System.out.println("Título: " + titulo);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Autor: " + autor); 
        System.out.println("Número de páginas: " + paginas.size());
    }
}