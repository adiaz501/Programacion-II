package P6_I;

import P6.Estudiante;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EstudianteTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<Estudiante> estudiantes;
    private String[] columnNames = {"Código", "Nombre"};

    public EstudianteTableModel(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        fireTableDataChanged(); // Refresca la tabla
    }
    
    public Estudiante getEstudianteAt(int rowIndex) {
        return estudiantes.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return estudiantes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Estudiante estudiante = estudiantes.get(rowIndex);
        switch (columnIndex) {
            case 0: // Columna "Código"
                return estudiante.getCodigo();
            case 1: // Columna "Nombre"
                return estudiante.getNombre(); 
            default:
                return null;
        }
    }
}