package P6_I;

import P6.Biblioteca;
import P6.Estudiante;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class GestionEstudiantesGUI {

    private JPanel mainPanel; 
    private Biblioteca biblioteca;
    private JTable tablaEstudiantes;
    private EstudianteTableModel tableModel;
    
    
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_TABLA_HEADER = new Color(233, 236, 239);
    private final Color COLOR_TABLA_SELECTION = new Color(0, 123, 255);
    private final Color COLOR_NUEVO = new Color(0, 123, 255);
    private final Color COLOR_EDITAR = new Color(255, 193, 7);
    private final Color COLOR_ELIMINAR = new Color(220, 53, 69);

    public JPanel getPanel() {
        return mainPanel;
    }

    public GestionEstudiantesGUI(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.tableModel = new EstudianteTableModel(biblioteca.getEstudiantes()); 
        initialize();
        cargarTablaEstudiantes();
    }

    private void cargarTablaEstudiantes() {
        tableModel.setEstudiantes(biblioteca.getEstudiantes());
    }

    private void initialize() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);

       
        tablaEstudiantes = new JTable(tableModel);
        configurarTabla();
        
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15)); 
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(222, 226, 230))); 

        JButton btnNuevo = createStyledButton("NUEVO USUARIO", COLOR_NUEVO, e -> registrarEstudianteAction());
        JButton btnEditar = createStyledButton("EDITAR", COLOR_EDITAR, e -> modificarEstudianteAction());
        JButton btnEliminar = createStyledButton("BORRAR", COLOR_ELIMINAR, e -> eliminarEstudianteAction());
        
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
        tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEstudiantes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaEstudiantes.setRowHeight(30);
        tablaEstudiantes.setBackground(Color.WHITE);
        tablaEstudiantes.setSelectionBackground(COLOR_TABLA_SELECTION);
        tablaEstudiantes.setSelectionForeground(Color.WHITE);
        tablaEstudiantes.setGridColor(new Color(222, 226, 230));
        tablaEstudiantes.setShowGrid(true);
        tablaEstudiantes.setIntercellSpacing(new Dimension(1, 1));

        
        tablaEstudiantes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaEstudiantes.getTableHeader().setBackground(COLOR_TABLA_HEADER);
        tablaEstudiantes.getTableHeader().setForeground(new Color(33, 37, 41));
        tablaEstudiantes.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tablaEstudiantes.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(222, 226, 230)));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaEstudiantes.getColumnCount(); i++) {
            tablaEstudiantes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    private Estudiante getSelectedEstudiante() {
        int selectedRow = tablaEstudiantes.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = tablaEstudiantes.convertRowIndexToModel(selectedRow);
            return tableModel.getEstudianteAt(modelRow);
        }
        return null;
    }

    private void registrarEstudianteAction() {
        EstudianteFormGUI form = new EstudianteFormGUI(biblioteca, null, this);
        form.getFrame().setVisible(true);
    }

    private void modificarEstudianteAction() {
        Estudiante estudianteSeleccionado = getSelectedEstudiante();
        if (estudianteSeleccionado == null) {
            JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un estudiante para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        EstudianteFormGUI form = new EstudianteFormGUI(biblioteca, estudianteSeleccionado, this);
        form.getFrame().setVisible(true);
    }

    private void eliminarEstudianteAction() {
        Estudiante estudianteSeleccionado = getSelectedEstudiante();
        if (estudianteSeleccionado == null) {
            JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un estudiante para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(mainPanel, 
                "¿Está seguro que desea eliminar al estudiante: " + estudianteSeleccionado.getNombre() + "?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = biblioteca.eliminarEstudiante(estudianteSeleccionado);
            if (eliminado) {
                JOptionPane.showMessageDialog(mainPanel, "Estudiante eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaEstudiantes(); 
            } else {
                JOptionPane.showMessageDialog(mainPanel, 
                    "No se puede eliminar al estudiante. Tiene préstamos activos.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void recargarDatos() {
        cargarTablaEstudiantes();
    }
}