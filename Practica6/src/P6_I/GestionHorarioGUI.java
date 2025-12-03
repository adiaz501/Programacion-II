package P6_I;

import P6.Biblioteca;
import P6.Horario;
import java.awt.*;
import javax.swing.*;

public class GestionHorarioGUI {

    private JPanel mainPanel;
    private Biblioteca biblioteca;

    private JTextField txtDias, txtApertura, txtCierre;
    private JButton btnAccion;

    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_MODIFICAR = new Color(0, 123, 255);
    private final Color COLOR_GUARDAR = new Color(40, 167, 69);

    public JPanel getPanel() {
        return mainPanel;
    }

    public GestionHorarioGUI(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        initialize();
        cargarHorarioActual();
    }

    private void cargarHorarioActual() {
        Horario h = biblioteca.getHorario();
        txtDias.setText(h.getDias());
        txtApertura.setText(h.getHoraApertura());
        txtCierre.setText(h.getHoraCierre());
        setEditable(false);
    }

    private void setEditable(boolean editable) {
        txtDias.setEditable(editable);
        txtApertura.setEditable(editable);
        txtCierre.setEditable(editable);

        if (editable) {
            btnAccion.setText("GUARDAR CAMBIOS");
            btnAccion.setBackground(COLOR_GUARDAR);
        } else {
            btnAccion.setText("MODIFICAR HORARIO");
            btnAccion.setBackground(COLOR_MODIFICAR);
        }
    }

    private void initialize() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Título
        JLabel lblTitulo = new JLabel("GESTIÓN Y MODIFICACIÓN DEL HORARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(33, 37, 41));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Fila 1: Días de Atención
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(createLabel("Días de Atención:", labelFont), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        txtDias = createTextField(20, textFont);
        panelForm.add(txtDias, gbc);

        // Fila 2: Hora de Apertura
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(createLabel("Hora de Apertura:", labelFont), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        txtApertura = createTextField(20, textFont);
        panelForm.add(txtApertura, gbc);

        // Fila 3: Hora de Cierre
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(createLabel("Hora de Cierre:", labelFont), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        txtCierre = createTextField(20, textFont);
        panelForm.add(txtCierre, gbc);

        mainPanel.add(panelForm, BorderLayout.CENTER);

        
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panelButton.setBackground(Color.WHITE);
        btnAccion = createStyledButton("MODIFICAR HORARIO", COLOR_MODIFICAR, e -> guardarModificarAction());
        panelButton.add(btnAccion);

        mainPanel.add(panelButton, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(33, 37, 41));
        return label;
    }

    private JTextField createTextField(int columns, Font font) {
        JTextField textField = new JTextField(columns);
        textField.setFont(font);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return textField;
    }

    private JButton createStyledButton(String text, Color background, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 35, 12, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        
        final Color originalBackground = background;
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Color currentBg = button.getBackground();
                button.setBackground(currentBg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Restaurar el color actual según el estado del botón
                if (txtDias.isEditable()) {
                    button.setBackground(COLOR_GUARDAR);
                } else {
                    button.setBackground(COLOR_MODIFICAR);
                }
            }
        });
        
        button.addActionListener(action);
        return button;
    }

    private void guardarModificarAction() {
        if (txtDias.isEditable()) {
            if (!validarCampos()) {
                JOptionPane.showMessageDialog(mainPanel, "Todos los campos son obligatorios.", "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Horario nuevoHorario = new Horario(txtDias.getText().trim(),
                                              txtApertura.getText().trim(),
                                              txtCierre.getText().trim());
            biblioteca.setHorario(nuevoHorario);

            JOptionPane.showMessageDialog(mainPanel,
                    "Horario actualizado a: " + txtDias.getText().trim() +
                    " de " + txtApertura.getText().trim() +
                    " a " + txtCierre.getText().trim() + ".", "Confirmación",
                    JOptionPane.INFORMATION_MESSAGE);

            setEditable(false);

        } else {
            setEditable(true);
            txtDias.requestFocus();
        }
    }

    private boolean validarCampos() {
        return !txtDias.getText().trim().isEmpty() &&
               !txtApertura.getText().trim().isEmpty() &&
               !txtCierre.getText().trim().isEmpty();
    }
}