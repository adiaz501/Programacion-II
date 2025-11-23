package P6_I;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

import P6.Biblioteca;
import P6.Estudiante;
import P6.Libro;

public class VentanaPrestamo {

    private JFrame frame;
    private Biblioteca sistemaDatos;
    private JComboBox<String> cmbLibro;
    private JComboBox<String> cmbEstudiante;
    private JTextField txtFechaPrestamo;
    private JTextField txtFechaDevolucion;

    public VentanaPrestamo(Biblioteca sistema) {
        this.sistemaDatos = sistema;
        initialize();
    }
    
    public JFrame getFrame() { return frame; }

    // Método para recargar los combos cada vez que se abre la ventana
    public void cargarDatosCombo() {
        cmbLibro.removeAllItems();
        cmbEstudiante.removeAllItems();
        
        if (sistemaDatos.getLibros().isEmpty()) {
            cmbLibro.addItem("No hay libros registrados");
        } else {
            for (Libro l : sistemaDatos.getLibros()) {
                cmbLibro.addItem(l.getTitulo() + " (ISBN: " + l.getISBN() + ")");
            }
        }
        
        if (sistemaDatos.getEstudiantes().isEmpty()) {
            cmbEstudiante.addItem("No hay estudiantes registrados");
        } else {
            for (Estudiante e : sistemaDatos.getEstudiantes()) {
                cmbEstudiante.addItem(e.getNombre() + " (" + e.getCodigo() + ")");
            }
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        panel.setBounds(0, 0, 434, 69);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Registrar Préstamo");
        lblTitulo.setFont(new Font("Antonio", Font.BOLD, 30));
        lblTitulo.setBounds(100, 11, 300, 40);
        panel.add(lblTitulo);
        
        JPanel panelCuerpo = new JPanel();
        panelCuerpo.setBackground(SystemColor.controlDkShadow);
        panelCuerpo.setBounds(0, 67, 434, 294);
        frame.getContentPane().add(panelCuerpo);
        panelCuerpo.setLayout(null);
        
        // --- Libro ---
        JLabel lblLibro = new JLabel("Libro:");
        lblLibro.setBounds(42, 30, 80, 14);
        panelCuerpo.add(lblLibro);
        cmbLibro = new JComboBox<>();
        cmbLibro.setBounds(140, 25, 250, 25);
        panelCuerpo.add(cmbLibro);

        // --- Estudiante ---
        JLabel lblEstudiante = new JLabel("Estudiante:");
        lblEstudiante.setBounds(42, 70, 80, 14);
        panelCuerpo.add(lblEstudiante);
        cmbEstudiante = new JComboBox<>();
        cmbEstudiante.setBounds(140, 65, 250, 25);
        panelCuerpo.add(cmbEstudiante);
        
        // --- Fechas ---
        JLabel lblFechaP = new JLabel("Fecha Préstamo:");
        lblFechaP.setBounds(42, 110, 200, 14);
        panelCuerpo.add(lblFechaP);
        txtFechaPrestamo = new JTextField("DD/MM/YYYY");
        txtFechaPrestamo.setBounds(42, 130, 150, 25);
        panelCuerpo.add(txtFechaPrestamo);
        
        JLabel lblFechaD = new JLabel("Fecha Devolución:");
        lblFechaD.setBounds(242, 110, 200, 14);
        panelCuerpo.add(lblFechaD);
        txtFechaDevolucion = new JTextField("DD/MM/YYYY");
        txtFechaDevolucion.setBounds(242, 130, 150, 25);
        panelCuerpo.add(txtFechaDevolucion);

        // --- BOTONES ---
        JButton btnAceptar = new JButton("Registrar Préstamo");
        btnAceptar.addActionListener(e -> registrarPrestamo());
        btnAceptar.setBounds(100, 200, 160, 30);
        panelCuerpo.add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cerrar");
        btnCancelar.addActionListener(e -> frame.setVisible(false));
        btnCancelar.setBounds(270, 200, 100, 30);
        panelCuerpo.add(btnCancelar);
        
        // Inicializar combos (aunque se recargan al abrir desde el Menú)
        cargarDatosCombo();
    }

    private void registrarPrestamo() {
        // Validación básica de selección (para evitar errores de índice si no hay datos)
        if (sistemaDatos.getLibros().isEmpty() || sistemaDatos.getEstudiantes().isEmpty() ||
            cmbLibro.getSelectedIndex() < 0 || cmbEstudiante.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(frame, "Asegúrese de haber registrado Libros y Estudiantes.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String fechaP = txtFechaPrestamo.getText();
        String fechaD = txtFechaDevolucion.getText();
        
        if (fechaP.isEmpty() || fechaD.isEmpty() || fechaP.equals("DD/MM/YYYY") || fechaD.equals("DD/MM/YYYY")) {
            JOptionPane.showMessageDialog(frame, "Las fechas no pueden estar vacías.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Libro libroSeleccionado = sistemaDatos.getLibros().get(cmbLibro.getSelectedIndex());
        // CORRECCIÓN: Usar tipo Estudiante para estudianteSeleccionado
        Estudiante estudianteSeleccionado = sistemaDatos.getEstudiantes().get(cmbEstudiante.getSelectedIndex());
        
        sistemaDatos.prestarLibro(estudianteSeleccionado, libroSeleccionado, fechaP, fechaD);
        sistemaDatos.guardar("datos_biblioteca.dat");
        
        JOptionPane.showMessageDialog(frame, "Préstamo registrado: " + libroSeleccionado.getTitulo() + " a " + estudianteSeleccionado.getNombre());
        frame.setVisible(false);
    }
}