package P6;

import java.util.ArrayList;

public class Libro {
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
    
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return titulo + " (ISBN: " + ISBN + ")";
    }
}