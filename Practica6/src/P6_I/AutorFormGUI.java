package P6_I;

import P6.Autor;
import P6.Biblioteca;

import java.awt.*;
import javax.swing.*;

public class AutorFormGUI {

    private JFrame frame;
    private JTextField txtNombre, txtNacionalidad;
    private Biblioteca biblioteca;
    private Autor autorExistente;
    private GestionAutoresGUI gestorGUI; 

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

    public AutorFormGUI(Biblioteca biblioteca, Autor autorAModificar, GestionAutoresGUI gestorGUI) {
        this.biblioteca = biblioteca;
        this.autorExistente = autorAModificar;
        this.gestorGUI = gestorGUI;
        initialize();
        
        if (autorExistente != null) {
            frame.setTitle("Modificar Autor: " + autorExistente.getNombre());
            txtNombre.setText(autorExistente.getNombre());
            txtNacionalidad.setText(autorExistente.getNacionalidad());
        } else {
            frame.setTitle("Registrar Nuevo Autor");
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 280);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        frame.getContentPane().setBackground(COLOR_FONDO);

        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel("GESTIÓN DE AUTOR");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
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
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(createLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNombre = createTextField(20);
        panelForm.add(txtNombre, gbc);

        // Nacionalidad
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        panelForm.add(createLabel("Nacionalidad:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNacionalidad = createTextField(20);
        panelForm.add(txtNacionalidad, gbc);

        // BOTONES
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        frame.getContentPane().add(panelButtons, BorderLayout.SOUTH);

        JButton btnGuardar = createStyledButton("GUARDAR", COLOR_GUARDAR, e -> guardarAutorAction());
        panelButtons.add(btnGuardar);

        JButton btnCancelar = createStyledButton("CANCELAR", COLOR_CANCELAR, e -> frame.dispose());
        panelButtons.add(btnCancelar);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return textField;
    }

    private JButton createStyledButton(String text, Color background, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOTON);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        

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

    private void guardarAutorAction() {
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();

        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios.", "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (autorExistente != null) {
            autorExistente.setNombre(nombre);
            autorExistente.setNacionalidad(nacionalidad);
            
            JOptionPane.showMessageDialog(frame, 
                "Autor '" + nombre + "' actualizado exitosamente.",
                "Confirmación", JOptionPane.INFORMATION_MESSAGE);

        } else {
            Autor nuevoAutor = new Autor(nombre, nacionalidad);
            biblioteca.agregarAutor(nuevoAutor);

            JOptionPane.showMessageDialog(frame,
                    "Autor " + nombre + " (" + nacionalidad + ") registrado exitosamente.",
                    "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (gestorGUI != null) {
            gestorGUI.recargarDatos(); 
        }

        frame.dispose();
    }
}