package P6_I;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

import P6.Biblioteca;
import P6.Autor;

public class VentanaAutor {

    private JFrame frame;
    private JTextField textFieldNombre; 
    private JTextField textFieldNacionalidad;
    private Biblioteca sistemaDatos; 

    public VentanaAutor(Biblioteca sistema) {
        this.sistemaDatos = sistema;
        initialize();
    }
    
    public JFrame getFrame() { return frame; }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        panel.setBounds(0, 0, 434, 69);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Registrar Autor");
        lblTitulo.setFont(new Font("Antonio", Font.BOLD, 30));
        lblTitulo.setBounds(120, 11, 250, 40);
        panel.add(lblTitulo);
        
        JPanel panelCuerpo = new JPanel();
        panelCuerpo.setBackground(SystemColor.controlDkShadow);
        panelCuerpo.setBounds(0, 67, 434, 194);
        frame.getContentPane().add(panelCuerpo);
        panelCuerpo.setLayout(null);
        
        // --- NOMBRE ---
        JLabel lblNombre = new JLabel("NOMBRE:");
        lblNombre.setFont(new Font("Antonio", Font.BOLD, 14));
        lblNombre.setBounds(42, 30, 80, 14);
        panelCuerpo.add(lblNombre);
        
        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(140, 25, 231, 30);
        panelCuerpo.add(textFieldNombre);
        
        // --- NACIONALIDAD ---
        JLabel lblNacionalidad = new JLabel("NACIONALIDAD:");
        lblNacionalidad.setFont(new Font("Antonio", Font.BOLD, 14));
        lblNacionalidad.setBounds(42, 70, 100, 14);
        panelCuerpo.add(lblNacionalidad);
        
        textFieldNacionalidad = new JTextField();
        textFieldNacionalidad.setBounds(140, 65, 231, 30);
        panelCuerpo.add(textFieldNacionalidad);
        
        // --- BOTONES ---
        JButton btnAceptar = new JButton("Guardar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarAutor();
            }
        });
        btnAceptar.setBounds(110, 138, 89, 23);
        panelCuerpo.add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cerrar");
        btnCancelar.addActionListener(e -> frame.setVisible(false));
        btnCancelar.setBounds(239, 138, 89, 23);
        panelCuerpo.add(btnCancelar);
    }

    private void guardarAutor() {
        String nombre = textFieldNombre.getText();
        String pais = textFieldNacionalidad.getText();
        
        if(!nombre.isEmpty() && !pais.isEmpty()) {
            // Lógica de Persistencia
            Autor nuevo = new Autor(nombre, pais);
            sistemaDatos.agregarAutor(nuevo);
            sistemaDatos.guardar("datos_biblioteca.dat");
            
            JOptionPane.showMessageDialog(frame, "Autor '" + nombre + "' guardado.");
            textFieldNombre.setText("");
            textFieldNacionalidad.setText("");
            frame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(frame, "Debe llenar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}