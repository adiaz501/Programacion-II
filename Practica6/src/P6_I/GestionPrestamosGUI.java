package P6_I;

import P6.Biblioteca;
import P6.Prestamo;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class GestionPrestamosGUI {

    private JPanel mainPanel; 
    private Biblioteca biblioteca;
    private JTable tablaPrestamos;
    private PrestamoTableModel tableModel;
    
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_TABLA_HEADER = new Color(233, 236, 239);
    private final Color COLOR_TABLA_SELECTION = new Color(0, 123, 255);
    private final Color COLOR_NUEVO = new Color(40, 167, 69);
    private final Color COLOR_DEVOLUCION = new Color(255, 193, 7);

    public JPanel getPanel() {
        return mainPanel;
    }

    public GestionPrestamosGUI(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.tableModel = new PrestamoTableModel(biblioteca.getPrestamos()); 
        initialize();
        cargarTablaPrestamos(); 
    }

    private void cargarTablaPrestamos() {
        tableModel.setPrestamos(biblioteca.getPrestamos());
    }

    private void initialize() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);

        
        tablaPrestamos = new JTable(tableModel);
        configurarTabla();
        
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15)); 
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(222, 226, 230))); 

        JButton btnNuevo = createStyledButton("NUEVO PRÉSTAMO", COLOR_NUEVO, Color.WHITE, e -> registrarPrestamoAction());
        JButton btnDevolucion = createStyledButton("REGISTRAR DEVOLUCIÓN", COLOR_DEVOLUCION, new Color(33, 37, 41), e -> registrarDevolucionAction());
        
        panelButtons.add(btnNuevo);
        panelButtons.add(btnDevolucion);
        
        mainPanel.add(panelButtons, BorderLayout.SOUTH);
    }
    
    private JButton createStyledButton(String text, Color background, Color foreground, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
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
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaPrestamos.setRowHeight(30);
        tablaPrestamos.setBackground(Color.WHITE);
        tablaPrestamos.setSelectionBackground(COLOR_TABLA_SELECTION);
        tablaPrestamos.setSelectionForeground(Color.WHITE);
        tablaPrestamos.setGridColor(new Color(222, 226, 230));
        tablaPrestamos.setShowGrid(true);
        tablaPrestamos.setIntercellSpacing(new Dimension(1, 1));

        // Header de la tabla
        tablaPrestamos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPrestamos.getTableHeader().setBackground(COLOR_TABLA_HEADER);
        tablaPrestamos.getTableHeader().setForeground(new Color(33, 37, 41));
        tablaPrestamos.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tablaPrestamos.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(222, 226, 230)));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaPrestamos.getColumnCount(); i++) {
            tablaPrestamos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    private Prestamo getSelectedPrestamo() {
        int selectedRow = tablaPrestamos.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = tablaPrestamos.convertRowIndexToModel(selectedRow);
            return tableModel.getPrestamoAt(modelRow);
        }
        return null;
    }
    
    private void registrarPrestamoAction() {
        PrestamoFormGUI form = new PrestamoFormGUI(biblioteca, this);
        form.getFrame().setVisible(true);
    }

    private void registrarDevolucionAction() {
        Prestamo prestamoSeleccionado = getSelectedPrestamo();
        if (prestamoSeleccionado == null) {
            JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un préstamo para registrar la devolución.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(mainPanel, 
                "¿Confirma la devolución del libro: " + prestamoSeleccionado.getLibro().getTitulo() + 
                " por parte de: " + prestamoSeleccionado.getEstudiante().getNombre() + "?", 
                "Confirmar Devolución", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = biblioteca.eliminarPrestamo(prestamoSeleccionado); 
            if (eliminado) {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Devolución registrada exitosamente. El libro ya está disponible.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaPrestamos(); 
            } else {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Error al registrar la devolución. El préstamo no fue encontrado o no se pudo eliminar.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void recargarDatos() {
        cargarTablaPrestamos();
    }
}