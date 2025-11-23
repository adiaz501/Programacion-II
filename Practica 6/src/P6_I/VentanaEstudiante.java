package P6_I;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.*;

import P6.Biblioteca;
import P6.Estudiante;

public class VentanaEstudiante {

    private JFrame frame;
    private JTextField textFieldCodigo;
    private JTextField textFieldNombre;
    private Biblioteca sistemaDatos; 

    public VentanaEstudiante(Biblioteca sistema) {
        this.sistemaDatos = sistema;
        initialize();
    }
    
    public JFrame getFrame() { return frame; }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // ... (Diseño de Cabecera y Cuerpo) ...
        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        panel.setBounds(0, 0, 434, 69);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Registrar Estudiante");
        lblTitulo.setFont(new Font("Antonio", Font.BOLD, 30));
        lblTitulo.setBounds(100, 11, 300, 40);
        panel.add(lblTitulo);
        
        JPanel panelCuerpo = new JPanel();
        panelCuerpo.setBackground(SystemColor.controlDkShadow);
        panelCuerpo.setBounds(0, 67, 434, 194);
        frame.getContentPane().add(panelCuerpo);
        panelCuerpo.setLayout(null);
        
        // --- CODIGO ---
        JLabel lblCodigo = new JLabel("CÓDIGO:");
        lblCodigo.setFont(new Font("Antonio", Font.BOLD, 14));
        lblCodigo.setBounds(42, 30, 80, 14);
        panelCuerpo.add(lblCodigo);
        
        textFieldCodigo = new JTextField();
        textFieldCodigo.setBounds(140, 25, 231, 30);
        panelCuerpo.add(textFieldCodigo);
        
        // --- NOMBRE ---
        JLabel lblNombre = new JLabel("NOMBRE:");
        lblNombre.setFont(new Font("Antonio", Font.BOLD, 14));
        lblNombre.setBounds(42, 70, 100, 14);
        panelCuerpo.add(lblNombre);
        
        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(140, 65, 231, 30);
        panelCuerpo.add(textFieldNombre);
        
        // --- BOTONES ---
        JButton btnAceptar = new JButton("Guardar");
        btnAceptar.addActionListener(e -> guardarEstudiante());
        btnAceptar.setBounds(110, 138, 89, 23);
        panelCuerpo.add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cerrar");
        btnCancelar.addActionListener(e -> frame.setVisible(false));
        btnCancelar.setBounds(239, 138, 89, 23);
        panelCuerpo.add(btnCancelar);
    }

    private void guardarEstudiante() {
        String codigo = textFieldCodigo.getText();
        String nombre = textFieldNombre.getText();
        
        if(!codigo.isEmpty() && !nombre.isEmpty()) {
            Estudiante nuevo = new Estudiante(codigo, nombre);
            sistemaDatos.agregarEstudiante(nuevo); // ¡Usamos el nuevo método!
            sistemaDatos.guardar("datos_biblioteca.dat");
            
            JOptionPane.showMessageDialog(frame, "Estudiante '" + nombre + "' guardado.");
            textFieldCodigo.setText("");
            textFieldNombre.setText("");
            frame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(frame, "Debe llenar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}