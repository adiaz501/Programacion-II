package P6_I;

import P6.Prestamo;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PrestamoTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private List<Prestamo> prestamos;
    private String[] columnNames = {"Estudiante", "Código", "Libro", "F. Préstamo", "F. Devolución"};

    public PrestamoTableModel(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
        fireTableDataChanged(); // Notifica a la tabla
    }

    public Prestamo getPrestamoAt(int rowIndex) {
        if (prestamos != null && rowIndex >= 0 && rowIndex < prestamos.size()) {
            return prestamos.get(rowIndex);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return prestamos != null ? prestamos.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames != null ? columnNames.length : 0;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnNames != null && columnIndex >= 0 && columnIndex < columnNames.length) {
            return columnNames[columnIndex];
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (prestamos == null || rowIndex < 0 || rowIndex >= prestamos.size()) return null;

        Prestamo prestamo = prestamos.get(rowIndex);
        if (prestamo == null) return null;

        switch (columnIndex) {
            case 0: // Estudiante
                return (prestamo.getEstudiante() != null && prestamo.getEstudiante().getNombre() != null)
                        ? prestamo.getEstudiante().getNombre()
                        : "";
            case 1: // Código
                return (prestamo.getEstudiante() != null && prestamo.getEstudiante().getCodigo() != null)
                        ? prestamo.getEstudiante().getCodigo()
                        : "";
            case 2: // Libro
                return (prestamo.getLibro() != null && prestamo.getLibro().getTitulo() != null)
                        ? prestamo.getLibro().getTitulo()
                        : "";
            case 3: // Fecha Préstamo
                return prestamo.getFechaPrestamo() != null ? prestamo.getFechaPrestamo() : "";
            case 4: // Fecha Devolución
                return prestamo.getFechaDevolucion() != null ? prestamo.getFechaDevolucion() : "";
            default:
                return "";
        }
    }

    /**
     * Permite agregar columnas dinámicamente en el futuro.
     */
    public void setColumnNames(String[] newColumnNames) {
        if (newColumnNames != null) {
            this.columnNames = newColumnNames;
            fireTableStructureChanged();
        }
    }
}
