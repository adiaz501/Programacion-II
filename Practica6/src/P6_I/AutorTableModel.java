package P6_I;

import P6.Autor;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AutorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Autor> autores;
    private String[] columnNames = {"Nombre", "Nacionalidad"};

    public AutorTableModel(List<Autor> autores) {
        this.autores = autores;
    }


    public void setAutores(List<Autor> autores) {
        this.autores = autores;
        fireTableDataChanged(); 
    }
    
    public Autor getAutorAt(int rowIndex) {
        return autores.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return autores.size();
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
        Autor autor = autores.get(rowIndex);
        switch (columnIndex) {
            case 0: // Columna "Nombre"
                return autor.getNombre();
            case 1: // Columna "Nacionalidad"
                return autor.getNacionalidad();
            default:
                return null;
        }
    }
}