package P6_I;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

import P6.Biblioteca;
import P6.Libro;
import P6.Autor;

public class VentanaLibro {

    private JFrame frame;
    private JTextField textFieldTitulo;
    private JTextField textFieldISBN;
    private JComboBox<Autor> comboAutores; // Combo para seleccionar autor
    private Biblioteca sistemaDatos; 

    public VentanaLibro(Biblioteca sistema) {
        this.sistemaDatos = sistema;
        initialize();
    }
    
    public JFrame getFrame() { return frame; }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        panel.setBounds(0, 0, 434, 69);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Registrar Libro");
        lblTitulo.setFont(new Font("Antonio", Font.BOLD, 30));
        lblTitulo.setBounds(120, 11, 250, 40);
        panel.add(lblTitulo);
        
        JPanel panelCuerpo = new JPanel();
        panelCuerpo.setBackground(SystemColor.controlDkShadow);
        panelCuerpo.setBounds(0, 67, 434, 250);
        frame.getContentPane().add(panelCuerpo);
        panelCuerpo.setLayout(null);
        
        // --- CAMPOS ---
        JLabel lblNombre = new JLabel("TITULO:");
        lblNombre.setFont(new Font("Antonio", Font.BOLD, 14));
        lblNombre.setBounds(42, 30, 60, 14);
        panelCuerpo.add(lblNombre);
        
        textFieldTitulo = new JTextField();
        textFieldTitulo.setBounds(110, 25, 261, 30);
        panelCuerpo.add(textFieldTitulo);
        
        JLabel lblIsbn = new JLabel("ISBN:");
        lblIsbn.setFont(new Font("Antonio", Font.BOLD, 14));
        lblIsbn.setBounds(42, 70, 60, 14);
        panelCuerpo.add(lblIsbn);
        
        textFieldISBN = new JTextField();
        textFieldISBN.setBounds(110, 65, 261, 30);
        panelCuerpo.add(textFieldISBN);
        
        JLabel lblAutor = new JLabel("AUTOR:");
        lblAutor.setFont(new Font("Antonio", Font.BOLD, 14));
        lblAutor.setBounds(42, 110, 60, 14);
        panelCuerpo.add(lblAutor);
        
        comboAutores = new JComboBox<>();
        comboAutores.setBounds(110, 105, 261, 30);
        panelCuerpo.add(comboAutores);
        cargarAutores(); // llenar combo con autores existentes
        
        // --- BOTONES ---
        JButton btnAceptar = new JButton("Guardar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarLibro();
            }
        });
        btnAceptar.setBounds(110, 160, 89, 23);
        panelCuerpo.add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cerrar");
        btnCancelar.addActionListener(e -> frame.setVisible(false));
        btnCancelar.setBounds(239, 160, 89, 23);
        panelCuerpo.add(btnCancelar);
    }

    private void cargarAutores() {
        comboAutores.removeAllItems();
        ArrayList<Autor> lista = sistemaDatos.getAutores(); // suponiendo que Biblioteca tiene getAutores()
        for (Autor a : lista) {
            comboAutores.addItem(a);
        }
    }

    private void guardarLibro() {
        String titulo = textFieldTitulo.getText();
        String isbn = textFieldISBN.getText();
        Autor autorSeleccionado = (Autor) comboAutores.getSelectedItem();
        
        if(!titulo.isEmpty() && !isbn.isEmpty() && autorSeleccionado != null) {
            // CREAR OBJETO LOGICO usando constructor correcto
            Libro nuevo = new Libro(titulo, isbn, autorSeleccionado);
            
            // AGREGAR A LA LISTA
            sistemaDatos.agregarLibro(nuevo); // suponiendo que Biblioteca tiene agregarLibro()
            
            JOptionPane.showMessageDialog(frame, "Libro guardado!");
            textFieldTitulo.setText("");
            textFieldISBN.setText("");
            frame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(frame, "Llena todos los campos");
        }
    }
}
