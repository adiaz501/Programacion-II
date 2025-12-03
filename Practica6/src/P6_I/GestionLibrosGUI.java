package P6_I;

import P6.Biblioteca;
import P6.Libro;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class GestionLibrosGUI {

    private JPanel mainPanel;
    private Biblioteca biblioteca;
    private JTable tablaLibros;
    private LibroTableModel tableModel;

    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_TABLA_HEADER = new Color(233, 236, 239);
    private final Color COLOR_TABLA_SELECTION = new Color(0, 123, 255);
    private final Color COLOR_NUEVO = new Color(0, 123, 255);
    private final Color COLOR_EDITAR = new Color(255, 193, 7);
    private final Color COLOR_ELIMINAR = new Color(220, 53, 69);

    public JPanel getPanel() {
        return mainPanel;
    }

    public GestionLibrosGUI(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.tableModel = new LibroTableModel(biblioteca.getLibros());
        initialize();
        cargarTablaLibros();
    }

    private void cargarTablaLibros() {
        tableModel.setLibros(biblioteca.getLibros());
    }

    private void initialize() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);

        // TABLA
        tablaLibros = new JTable(tableModel);
        configurarTabla();
        
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(222, 226, 230)));

        JButton btnNuevo = createStyledButton("NUEVO LIBRO", COLOR_NUEVO, e -> registrarLibroAction());
        JButton btnEditar = createStyledButton("EDITAR", COLOR_EDITAR, e -> modificarLibroAction());
        JButton btnEliminar = createStyledButton("BORRAR", COLOR_ELIMINAR, e -> eliminarLibroAction());

        panelButtons.add(btnNuevo);
        panelButtons.add(btnEditar);
        panelButtons.add(btnEliminar);

        mainPanel.add(panelButtons, BorderLayout.SOUTH);
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
        
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

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
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaLibros.setRowHeight(30);
        tablaLibros.setBackground(Color.WHITE);
        tablaLibros.setSelectionBackground(COLOR_TABLA_SELECTION);
        tablaLibros.setSelectionForeground(Color.WHITE);
        tablaLibros.setGridColor(new Color(222, 226, 230));
        tablaLibros.setShowGrid(true);
        tablaLibros.setIntercellSpacing(new Dimension(1, 1));

        tablaLibros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaLibros.getTableHeader().setBackground(COLOR_TABLA_HEADER);
        tablaLibros.getTableHeader().setForeground(new Color(33, 37, 41));
        tablaLibros.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tablaLibros.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(222, 226, 230)));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaLibros.getColumnCount(); i++) {
            tablaLibros.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private Libro getSelectedLibro() {
        int selectedRow = tablaLibros.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = tablaLibros.convertRowIndexToModel(selectedRow);
            return tableModel.getLibroAt(modelRow);
        }
        return null;
    }

    private void registrarLibroAction() {
        LibroFormGUI form = new LibroFormGUI(biblioteca, null, this);
        form.getFrame().setVisible(true);
    }

    private void modificarLibroAction() {
        Libro libroSeleccionado = getSelectedLibro();
        if (libroSeleccionado == null) {
            JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un libro para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LibroFormGUI form = new LibroFormGUI(biblioteca, libroSeleccionado, this);
        form.getFrame().setVisible(true);
    }

    private void eliminarLibroAction() {
        Libro libroSeleccionado = getSelectedLibro();
        if (libroSeleccionado == null) {
            JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un libro para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(mainPanel,
                "¿Está seguro que desea eliminar el libro: " + libroSeleccionado.getTitulo() + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = biblioteca.eliminarLibro(libroSeleccionado);
            if (eliminado) {
                JOptionPane.showMessageDialog(mainPanel, "Libro eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaLibros();
            } else {
                JOptionPane.showMessageDialog(mainPanel,
                        "No se puede eliminar el libro. Tiene préstamos activos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void recargarDatos() {
        cargarTablaLibros();
    }
}