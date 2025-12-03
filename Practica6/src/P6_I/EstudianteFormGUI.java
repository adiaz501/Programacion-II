package P6_I;

import P6.Biblioteca;
import P6.Estudiante;

import java.awt.*;
import javax.swing.*;

public class EstudianteFormGUI {

    private JFrame frame;
    private JTextField txtCodigo, txtNombre;
    private Biblioteca biblioteca;
    
    private Estudiante estudianteExistente; 
    private GestionEstudiantesGUI gestorGUI; 

    private final Color COLOR_HEADER = new Color(0, 123, 255);
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_FORM = Color.WHITE;
    private final Color COLOR_GUARDAR = new Color(40, 167, 69);
    private final Color COLOR_CANCELAR = new Color(108, 117, 125);
    private final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_BOTON = new Font("Segoe UI", Font.BOLD, 12);

    public JFrame getFrame() {
        return frame;
    }

    public EstudianteFormGUI(Biblioteca biblioteca, Estudiante estudianteAModificar, GestionEstudiantesGUI gestorGUI) {
        this.biblioteca = biblioteca;
        this.estudianteExistente = estudianteAModificar;
        this.gestorGUI = gestorGUI;
        initialize();
        
        if (estudianteExistente != null) {
            frame.setTitle("Modificar Estudiante: " + estudianteExistente.getNombre());
            txtCodigo.setText(estudianteExistente.getCodigo());
            txtNombre.setText(estudianteExistente.getNombre());
            txtCodigo.setEditable(false); 
        } else {
            frame.setTitle("Registrar Nuevo Estudiante");
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 280);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JLabel lblTitulo = new JLabel("GESTIÓN DE ESTUDIANTE");
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
        
        // CÓDIGO
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(createLabel("Código:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtCodigo = createTextField(20); 
        panelForm.add(txtCodigo, gbc);

        // NOMBRE
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(createLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtNombre = createTextField(20); 
        panelForm.add(txtNombre, gbc);

        
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(222, 226, 230)));
        frame.getContentPane().add(panelButtons, BorderLayout.SOUTH);

        JButton btnGuardar = createStyledButton("GUARDAR", COLOR_GUARDAR, e -> guardarEstudianteAction());
        panelButtons.add(btnGuardar);

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

    private void guardarEstudianteAction() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios.", 
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (estudianteExistente != null) {
            estudianteExistente.setNombre(nombre); 
            JOptionPane.showMessageDialog(frame, 
                "Estudiante '" + nombre + "' actualizado exitosamente.",
                "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Estudiante nuevoEstudiante = new Estudiante(codigo, nombre);
            biblioteca.agregarEstudiante(nuevoEstudiante);

            JOptionPane.showMessageDialog(frame,
                "Estudiante (Cód: " + codigo + ") " + nombre + " registrado exitosamente.", 
                "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }

        if(gestorGUI != null) {
            gestorGUI.recargarDatos();
        }
        frame.dispose();
    }
}