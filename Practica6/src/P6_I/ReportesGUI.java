package P6_I;

import P6.Biblioteca;
import java.awt.*;
import javax.swing.*;

public class ReportesGUI {

    private JPanel mainPanel;
    private Biblioteca biblioteca;

    private final Color COLOR_AZUL = new Color(0, 123, 255);
    private final Color COLOR_VERDE = new Color(40, 167, 69);
    private final Color COLOR_AMARILLO = new Color(255, 193, 7);
    private final Color COLOR_GRIS = new Color(108, 117, 125);

    public JPanel getPanel() {
        return mainPanel;
    }

    public ReportesGUI(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        initialize();
    }

    private void initialize() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // TÍTULO
        JLabel lblTitulo = new JLabel("RESUMEN Y REPORTES DE LA BIBLIOTECA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelStats = new JPanel(new GridLayout(2, 2, 25, 25));
        panelStats.setBackground(Color.WHITE);
        panelStats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int totalLibros = biblioteca.getLibros().size();
        int totalEstudiantes = biblioteca.getEstudiantes().size();
        int totalPrestamos = biblioteca.getPrestamos().size();
        long librosPrestados = biblioteca.getPrestamos().stream()
                .map(p -> p.getLibro().getISBN())
                .distinct()
                .count();
        int librosDisponibles = totalLibros - (int) librosPrestados;

        panelStats.add(createStatCard("Total de Libros Registrados", String.valueOf(totalLibros), COLOR_AZUL));
        panelStats.add(createStatCard("Libros Disponibles", String.valueOf(librosDisponibles), COLOR_VERDE));
        panelStats.add(createStatCard("Préstamos Activos", String.valueOf(totalPrestamos), COLOR_AMARILLO));
        panelStats.add(createStatCard("Estudiantes Registrados", String.valueOf(totalEstudiantes), COLOR_GRIS));

        mainPanel.add(panelStats, BorderLayout.CENTER);

        JPanel panelInfo = new JPanel(new GridLayout(0, 1, 10, 10));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 2, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                "Información de la Biblioteca",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(33, 37, 41)
            )
        ));
        panelInfo.setBackground(Color.WHITE);

        JLabel lblNombre = new JLabel("  Nombre: " + biblioteca.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNombre.setForeground(new Color(73, 80, 87));
        lblNombre.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel lblHorario = new JLabel("  Horario: " + biblioteca.getHorario().getDias() + " de " +
                biblioteca.getHorario().getHoraApertura() + " a " +
                biblioteca.getHorario().getHoraCierre());
        lblHorario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblHorario.setForeground(new Color(73, 80, 87));
        lblHorario.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        panelInfo.add(lblNombre);
        panelInfo.add(lblHorario);

        mainPanel.add(panelInfo, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        panel.setPreferredSize(new Dimension(240, 130));

        JLabel lblTitle = new JLabel(title, SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(new Color(108, 117, 125));

        JLabel lblValue = new JLabel(value, SwingConstants.RIGHT);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblValue.setForeground(color.darker());

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblValue, BorderLayout.CENTER);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBackground(new Color(248, 249, 250));
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBackground(Color.WHITE);
            }
        });

        return panel;
    }
}