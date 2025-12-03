package P6_I;

import P6.Libro;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LibroTableModel extends AbstractTableModel {

    private List<Libro> libros;
    private String[] columnNames = {"Título", "ISBN", "Autor"}; // Puedes agregar más columnas en el futuro

    public LibroTableModel(List<Libro> libros) {
        this.libros = libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        fireTableDataChanged(); 
    }

    public Libro getLibroAt(int rowIndex) {
        if (libros != null && rowIndex >= 0 && rowIndex < libros.size()) {
            return libros.get(rowIndex);
        }
        return null; // Retorna null si no hay libro en esa fila
    }

    @Override
    public int getRowCount() {
        return libros != null ? libros.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < columnNames.length) {
            return columnNames[columnIndex];
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (libros == null || rowIndex < 0 || rowIndex >= libros.size()) {
            return null;
        }

        Libro libro = libros.get(rowIndex);
        if (libro == null) return null;

        switch (columnIndex) {
            case 0: // Título
                return libro.getTitulo() != null ? libro.getTitulo() : "";
            case 1: // ISBN
                return libro.getISBN() != null ? libro.getISBN() : "";
            case 2: // Autor
                return (libro.getAutor() != null && libro.getAutor().getNombre() != null) 
                        ? libro.getAutor().getNombre() 
                        : "";
            default:
                return ""; 
        }
    }

    public void setColumnNames(String[] newColumnNames) {
        if (newColumnNames != null) {
            this.columnNames = newColumnNames;
            fireTableStructureChanged();
        }
    }
}
