package P6_I;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.*;
import P6.Biblioteca;

public class VentanaBiblioteca {

    private JFrame frame;
    private Biblioteca sistemaDatos;
    private JTextArea textAreaInfo;

    public VentanaBiblioteca(Biblioteca sistema) {
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
        
        JLabel lblTitulo = new JLabel("Estado Biblioteca");
        lblTitulo.setFont(new Font("Antonio", Font.BOLD, 30));
        lblTitulo.setBounds(100, 11, 300, 40);
        panel.add(lblTitulo);
        
        // Botón para actualizar la info
        JButton btnVer = new JButton("Actualizar Info");
        btnVer.setBounds(150, 80, 150, 30);
        btnVer.addActionListener(e -> mostrarInfo());
        frame.getContentPane().add(btnVer);
        
        textAreaInfo = new JTextArea();
        textAreaInfo.setBounds(50, 120, 350, 120);
        frame.getContentPane().add(textAreaInfo);
    }
    
    private void mostrarInfo() {
        String info = "Libros registrados: " + sistemaDatos.getLibros().size() + "\n";
        info += "Autores registrados: " + sistemaDatos.getAutores().size() + "\n";
        info += "Préstamos activos: " + sistemaDatos.getPrestamos().size();
        textAreaInfo.setText(info);
    }
}