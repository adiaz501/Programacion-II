package P6_I;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import P6.Biblioteca;

public class Menu {

    private JFrame frame;

    // Ventanas del sistema
    private VentanaBiblioteca ventanaBiblioteca;
    private VentanaLibro ventanaLibro;
    private VentanaAutor ventanaAutor;
    private VentanaEstudiante ventanaEstudiante;
    private VentanaPrestamo ventanaPrestamo;

    // Datos persistentes
    private Biblioteca sistemaDatos;
    private final String ARCHIVO_DATOS = "datos_biblioteca.dat";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Menu window = new Menu();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Menu() {

        // -------- 1. CARGAR LOS DATOS AL INICIAR --------
        sistemaDatos = Biblioteca.cargar(ARCHIVO_DATOS);

        if (sistemaDatos == null) {
            sistemaDatos = new Biblioteca("Biblioteca Central");
        }

        // -------- 2. CREAR TODAS LAS VENTANAS --------
        ventanaBiblioteca = new VentanaBiblioteca(sistemaDatos);
        ventanaLibro = new VentanaLibro(sistemaDatos);
        ventanaAutor = new VentanaAutor(sistemaDatos);
        ventanaEstudiante = new VentanaEstudiante(sistemaDatos);
        ventanaPrestamo = new VentanaPrestamo(sistemaDatos);

        initialize();
    }

    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 450, 446);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // === CABECERA ===
        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        panel.setBounds(0, 0, 434, 64);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblMenu = new JLabel("MENÚ");
        lblMenu.setFont(new Font("Antonio", Font.BOLD, 22));
        lblMenu.setBounds(150, 11, 200, 34);
        panel.add(lblMenu);

        // === PANEL BOTONES ===
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.LIGHT_GRAY);
        panelBotones.setBounds(0, 63, 434, 333);
        frame.getContentPane().add(panelBotones);
        panelBotones.setLayout(null);

        // --- AGREGAR LIBRO ---
        JButton btnLibro = new JButton("Agregar Libro");
        btnLibro.addActionListener(e -> ventanaLibro.getFrame().setVisible(true));
        btnLibro.setBounds(28, 34, 142, 23);
        panelBotones.add(btnLibro);

        // --- AGREGAR AUTOR ---
        JButton btnAutor = new JButton("Agregar Autor");
        btnAutor.addActionListener(e -> ventanaAutor.getFrame().setVisible(true));
        btnAutor.setBounds(28, 72, 142, 23);
        panelBotones.add(btnAutor);

        // --- AGREGAR ESTUDIANTE ---
        JButton btnEstudiante = new JButton("Agregar Estudiante");
        btnEstudiante.addActionListener(e -> ventanaEstudiante.getFrame().setVisible(true));
        btnEstudiante.setBounds(28, 117, 142, 23);
        panelBotones.add(btnEstudiante);

        // --- REGISTRAR PRÉSTAMO ---
        JButton btnPrestamo = new JButton("REGISTRAR Préstamo");
        btnPrestamo.addActionListener(e -> {
            ventanaPrestamo.cargarDatosCombo();
            ventanaPrestamo.getFrame().setVisible(true);
        });
        btnPrestamo.setBounds(250, 34, 150, 23);
        panelBotones.add(btnPrestamo);

        // --- VER ESTADO ---
        JButton btnBiblio = new JButton("Ver Estado");
        btnBiblio.addActionListener(e -> ventanaBiblioteca.getFrame().setVisible(true));
        btnBiblio.setBounds(250, 72, 150, 23);
        panelBotones.add(btnBiblio);

        // --- GUARDAR Y SALIR ---
        JButton btnSalir = new JButton("GUARDAR y Salir");
        btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnSalir.addActionListener(e -> {
            sistemaDatos.guardar(ARCHIVO_DATOS);
            JOptionPane.showMessageDialog(frame, "Datos guardados exitosamente.", 
                "Persistencia", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });
        btnSalir.setBounds(250, 151, 150, 23);
        panelBotones.add(btnSalir);
    }
}
