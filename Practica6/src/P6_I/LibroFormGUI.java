package P6_I;

import P6.Autor;
import P6.Biblioteca;
import P6.Libro;
import P6.Pagina;

import java.awt.*;
import javax.swing.*;

public class LibroFormGUI {

    private JFrame frame;
    private JTextField txtTitulo, txtISBN;
    private JComboBox<Autor> cmbAutor;
    private Biblioteca biblioteca;
    
    private Libro libroExistente;
    private GestionLibrosGUI gestorGUI;
    
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

    public LibroFormGUI(Biblioteca biblioteca, Libro libroAModificar, GestionLibrosGUI gestorGUI) {
        this.biblioteca = biblioteca;
        this.libroExistente = libroAModificar;
        this.gestorGUI = gestorGUI;
        initialize();
        cargarAutores();
        
        if (libroExistente != null) {
            frame.setTitle("Modificar Libro: " + libroExistente.getTitulo());
            txtTitulo.setText(libroExistente.getTitulo());
            txtISBN.setText(libroExistente.getISBN());
            cmbAutor.setSelectedItem(libroExistente.getAutor());
            txtISBN.setEditable(false); 
        } else {
            frame.setTitle("Registrar Nuevo Libro");
        }
    }
    
    private void cargarAutores() {
        cmbAutor.removeAllItems();
        biblioteca.getAutores().forEach(cmbAutor::addItem);
        if (cmbAutor.getItemCount() == 0) {
            cmbAutor.addItem(new Autor("--- No hay autores ---", ""));
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 550, 320);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JLabel lblTitulo = new JLabel(libroExistente != null ? "ACTUALIZAR LIBRO" : "REGISTRO DE NUEVO LIBRO");
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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1: Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(createLabel("Título:"), gbc);
        txtTitulo = createTextField(25);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        panelForm.add(txtTitulo, gbc);

        // Fila 2: ISBN
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(createLabel("ISBN:"), gbc);
        txtISBN = createTextField(25);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        panelForm.add(txtISBN, gbc);

        // Fila 3: Autor
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(createLabel("Autor:"), gbc);
        cmbAutor = new JComboBox<>();
        cmbAutor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbAutor.setBackground(Color.WHITE);
        cmbAutor.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        cmbAutor.setPreferredSize(new Dimension(300, 38));
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        panelForm.add(cmbAutor, gbc);
        
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(222, 226, 230)));
        frame.getContentPane().add(panelButtons, BorderLayout.SOUTH);

        JButton btnAceptar = createStyledButton(libroExistente != null ? "ACTUALIZAR" : "GUARDAR", COLOR_GUARDAR, e -> guardarLibroAction());
        panelButtons.add(btnAceptar);

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

    private void guardarLibroAction() {
        String titulo = txtTitulo.getText().trim();
        String isbn = txtISBN.getText().trim();
        Autor autorSeleccionado = (Autor) cmbAutor.getSelectedItem();

        if (titulo.isEmpty() || isbn.isEmpty() || autorSeleccionado == null || autorSeleccionado.getNombre().contains("--- No hay")) {
            JOptionPane.showMessageDialog(frame, "Todos los campos (Título, ISBN, Autor) son obligatorios.", "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (libroExistente != null) {
            libroExistente.setTitulo(titulo);
            libroExistente.setAutor(autorSeleccionado);
            
            JOptionPane.showMessageDialog(frame, "Libro '" + titulo + "' actualizado exitosamente.",
                    "Confirmación", JOptionPane.INFORMATION_MESSAGE);

        } else {
            Libro nuevoLibro = new Libro(titulo, isbn, autorSeleccionado);
            biblioteca.agregarLibro(nuevoLibro);
            
            añadirPaginasALibro(nuevoLibro); 
            
            JOptionPane.showMessageDialog(frame, "Libro '" + titulo + "' registrado exitosamente.",
                    "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (gestorGUI != null) {
            gestorGUI.recargarDatos();
        }

        frame.dispose();
    }
    
    private void añadirPaginasALibro(Libro libro) {
        int numPagina = 1;
        boolean continuar = true;

        JOptionPane.showMessageDialog(frame,
                "A continuación, ingrese el contenido de las páginas. Presione 'Cancelar' para terminar.",
                "Añadir Páginas", JOptionPane.INFORMATION_MESSAGE);

        while (continuar) {
            String contenido = JOptionPane.showInputDialog(frame,
                    "Ingrese el contenido para la Página " + numPagina + ":");

            if (contenido == null) {
                continuar = false;
            } else if (!contenido.trim().isEmpty()) {
                Pagina nuevaPagina = new Pagina(numPagina, contenido.trim());
                libro.agregarPagina(nuevaPagina);
                numPagina++;
            } else {
                JOptionPane.showMessageDialog(frame, "El contenido de la página no puede estar vacío.", "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

        if (numPagina > 1) {
            JOptionPane.showMessageDialog(frame, (numPagina - 1) + " páginas añadidas al libro.", "Resumen de Páginas",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}