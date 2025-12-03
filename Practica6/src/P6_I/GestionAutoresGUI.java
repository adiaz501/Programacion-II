package P6_I;

import P6.Biblioteca;
import P6.Autor;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class GestionAutoresGUI extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private Biblioteca biblioteca;
    private JTable tablaAutores;
    private AutorTableModel tableModel;
    private JFrame parentFrame; 

    // Colores consistentes
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_TABLA_HEADER = new Color(233, 236, 239);
    private final Color COLOR_TABLA_SELECTION = new Color(0, 123, 255);
    private final Color COLOR_AGREGAR = new Color(0, 123, 255);
    private final Color COLOR_MODIFICAR = new Color(255, 193, 7);
    private final Color COLOR_ELIMINAR = new Color(220, 53, 69);

    public GestionAutoresGUI(Biblioteca biblioteca, JFrame parentFrame) {
        this.biblioteca = biblioteca;
        this.tableModel = new AutorTableModel(biblioteca.getAutores()); 
        this.parentFrame = parentFrame; 
        initialize();
        cargarTablaAutores();
    }
    
    public JPanel getPanel() {
        return this;
    }

    private void cargarTablaAutores() {
        tableModel.setAutores(biblioteca.getAutores());
    }

    private void initialize() {
        setLayout(new BorderLayout(0, 0));
        setBackground(COLOR_FONDO);

        // TABLA
        tablaAutores = new JTable(tableModel);
        configurarTabla();
        JScrollPane scrollPane = new JScrollPane(tablaAutores);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        add(scrollPane, BorderLayout.CENTER);

        // PANEL DE BOTONES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(222, 226, 230)));

        JButton btnAgregar = createStyledButton("NUEVO AUTOR", COLOR_AGREGAR, e -> agregarAutorAction());
        JButton btnModificar = createStyledButton("EDITAR", COLOR_MODIFICAR, e -> modificarAutorAction());
        JButton btnEliminar = createStyledButton("BORRAR", COLOR_ELIMINAR, e -> eliminarAutorAction());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color background, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(background.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(background);
            }
        });
        
        button.addActionListener(action);
        return button;
    }

    private void configurarTabla() {
        tablaAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAutores.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaAutores.setRowHeight(30);
        tablaAutores.setBackground(Color.WHITE);
        tablaAutores.setSelectionBackground(COLOR_TABLA_SELECTION);
        tablaAutores.setSelectionForeground(Color.WHITE);
        tablaAutores.setGridColor(new Color(222, 226, 230));
        tablaAutores.setShowGrid(true);
        tablaAutores.setIntercellSpacing(new Dimension(1, 1));

        // Header de la tabla
        tablaAutores.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaAutores.getTableHeader().setBackground(COLOR_TABLA_HEADER);
        tablaAutores.getTableHeader().setForeground(new Color(33, 37, 41));
        tablaAutores.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tablaAutores.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(222, 226, 230)));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            tablaAutores.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private Autor getSelectedAutor() {
        int selectedRow = tablaAutores.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = tablaAutores.convertRowIndexToModel(selectedRow);
            return tableModel.getAutorAt(modelRow);
        }
        return null;
    }

    private void agregarAutorAction() {
        AutorFormGUI form = new AutorFormGUI(biblioteca, null, this);
        form.getFrame().setVisible(true);
    }

    private void modificarAutorAction() {
        Autor autorSeleccionado = getSelectedAutor();
        if (autorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un autor para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        AutorFormGUI form = new AutorFormGUI(biblioteca, autorSeleccionado, this);
        form.getFrame().setVisible(true);
    }

    private void eliminarAutorAction() {
        Autor autorSeleccionado = getSelectedAutor();
        if (autorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un autor para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar al autor: " + autorSeleccionado.getNombre() + "?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = biblioteca.eliminarAutor(autorSeleccionado); 
            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Autor eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaAutores();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se puede eliminar el autor. Podría tener libros asociados.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void recargarDatos() {
        cargarTablaAutores();
    }
}