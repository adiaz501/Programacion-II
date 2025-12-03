package P6_I;

import P6.Biblioteca;
import P6.Estudiante;
import P6.Libro;

import java.awt.*;
import javax.swing.*;

public class PrestamoFormGUI {

    private JFrame frame;
    private JComboBox<Estudiante> cmbEstudiante;
    private JComboBox<Libro> cmbLibro;
    private JTextField txtFechaPrestamo, txtFechaDevolucion;
    private Biblioteca biblioteca;
    
    private GestionPrestamosGUI gestorGUI; 

    private final Color COLOR_HEADER = new Color(0, 123, 255);
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_FORM = Color.WHITE;
    private final Color COLOR_REGISTRAR = new Color(40, 167, 69);
    private final Color COLOR_CANCELAR = new Color(108, 117, 125);
    private final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_BOTON = new Font("Segoe UI", Font.BOLD, 12);

    public JFrame getFrame() {
        return frame;
    }

    public PrestamoFormGUI(Biblioteca biblioteca, GestionPrestamosGUI gestorGUI) {
        this.biblioteca = biblioteca;
        this.gestorGUI = gestorGUI;
        initialize();
        cargarDatos();
    }

    private void cargarDatos() {
        cmbEstudiante.removeAllItems();
        biblioteca.getEstudiantes().forEach(cmbEstudiante::addItem);

        cmbLibro.removeAllItems();
        biblioteca.getLibros().forEach(cmbLibro::addItem);

        if (cmbEstudiante.getItemCount() == 0)
            cmbEstudiante.addItem(new Estudiante("", "--- No hay estudiantes ---"));
        if (cmbLibro.getItemCount() == 0)
            cmbLibro.addItem(new Libro("--- No hay libros ---", "", new P6.Autor("", ""))); 
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Registrar Nuevo Préstamo");
        frame.setBounds(100, 100, 550, 380);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JLabel lblTitulo = new JLabel("REGISTRO DE PRÉSTAMO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo);
        frame.getContentPane().add(panelHeader, BorderLayout.NORTH);

        JPanel panelForm = new JPanel();
        panelForm.setBackground(COLOR_FORM);
        panelForm.setLayout(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        frame.getContentPane().add(panelForm, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Estudiante
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(createLabel("Estudiante:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        cmbEstudiante = createStyledComboBox();
        panelForm.add(cmbEstudiante, gbc);

        // 2. Libro
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(createLabel("Libro:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        cmbLibro = createStyledComboBox();
        panelForm.add(cmbLibro, gbc);

        // 3. Fecha Préstamo
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(createLabel("F. Préstamo (DD/MM/AAAA):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtFechaPrestamo = createTextField(15);
        panelForm.add(txtFechaPrestamo, gbc);
        
        // 4. Fecha Devolución
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelForm.add(createLabel("F. Devolución (DD/MM/AAAA):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        txtFechaDevolucion = createTextField(15);
        panelForm.add(txtFechaDevolucion, gbc);
        
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        frame.getContentPane().add(panelButtons, BorderLayout.SOUTH);

        JButton btnRegistrar = createStyledButton("REGISTRAR PRÉSTAMO", COLOR_REGISTRAR, e -> guardarPrestamoAction());
        panelButtons.add(btnRegistrar);

        JButton btnCancelar = createStyledButton("CANCELAR", COLOR_CANCELAR, e -> frame.dispose());
        panelButtons.add(btnCancelar);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(new Color(33, 37, 41));
        return label;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return textField;
    }

    private <T> JComboBox<T> createStyledComboBox() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        combo.setPreferredSize(new Dimension(300, 38));
        return combo;
    }

    private JButton createStyledButton(String text, Color background, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOTON);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
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

    private void guardarPrestamoAction() {
        Estudiante estudianteSeleccionado = (Estudiante) cmbEstudiante.getSelectedItem();
        Libro libroSeleccionado = (Libro) cmbLibro.getSelectedItem();
        String fp = txtFechaPrestamo.getText().trim();
        String fd = txtFechaDevolucion.getText().trim();

        if (estudianteSeleccionado == null || libroSeleccionado == null 
            || estudianteSeleccionado.getNombre().contains("--- No hay") 
            || libroSeleccionado.getTitulo().contains("--- No hay")) {
            JOptionPane.showMessageDialog(frame,
                    "Debe seleccionar un estudiante y un libro válidos. Verifique que estén registrados.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (fp.isEmpty() || fd.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Las fechas de préstamo y devolución son obligatorias.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for(P6.Prestamo p : biblioteca.getPrestamos()) {
            if (p.getLibro().equals(libroSeleccionado)) {
                JOptionPane.showMessageDialog(frame,
                    "Error: El libro '" + libroSeleccionado.getTitulo() + "' ya se encuentra prestado.",
                    "Error de Préstamo", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        biblioteca.prestarLibro(estudianteSeleccionado, libroSeleccionado, fp, fd);

        JOptionPane.showMessageDialog(frame,
                "Préstamo registrado. Libro: " + libroSeleccionado.getTitulo() + " a "
                        + estudianteSeleccionado.getNombre() + ".",
                "Confirmación", JOptionPane.INFORMATION_MESSAGE);

        if(gestorGUI != null) {
            gestorGUI.recargarDatos();
        }
        frame.dispose();
    }
}