package P6_I;

import P6.Biblioteca;
import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale; 

public class BibliotecaAppGUI {

    private JFrame frame;
    private Biblioteca biblioteca;
    private JPanel contentPanel; 
    private static final String ARCHIVO_DATOS = "biblioteca.json"; 
    
    private final Color COLOR_SIDEBAR = new Color(33, 37, 41);          
    private final Color COLOR_SIDEBAR_HOVER = new Color(52, 58, 64);   
    private final Color COLOR_ACCENT = new Color(0, 123, 255);          
    private final Color COLOR_ACCENT_HOVER = new Color(0, 105, 217);
    private final Color COLOR_BACKGROUND = new Color(248, 249, 250);    
    private final Color COLOR_HEADER = new Color(255, 255, 255);        
    private final Color COLOR_TEXT_LIGHT = Color.WHITE;                 
    private final Color COLOR_TEXT_DARK = new Color(33, 37, 41);        
    private final Color COLOR_BORDER = new Color(222, 226, 230);       
    private final Color COLOR_DANGER = new Color(220, 53, 69);          
    private final Font FONT_MENU = new Font("Segoe UI", Font.BOLD, 15);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 12);

    public BibliotecaAppGUI() {
        this.biblioteca = Biblioteca.cargar(ARCHIVO_DATOS);
        initialize();
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                BibliotecaAppGUI window = new BibliotecaAppGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("BIBLIOTECA UMSA - Sistema de Gestión");
        frame.setBounds(100, 100, 1200, 750);
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        frame.getContentPane().setLayout(new BorderLayout(0, 0)); 

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salirYGuardarAction();
            }
        });

        JPanel sidebar = createSidebar();
        frame.getContentPane().add(sidebar, BorderLayout.WEST);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(COLOR_BACKGROUND);
        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        mostrarVistaPrincipal();
    }
    
    private ImageIcon loadIcon(String path) {
        try {
            java.net.URL imgURL = getClass().getResource("/imagen/" + path);
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            } else {
                System.err.println("Icono no encontrado: /imagen/" + path);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JPanel createSidebar() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(COLOR_SIDEBAR);
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS)); 
        panelMenu.setPreferredSize(new Dimension(240, 750));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(COLOR_SIDEBAR);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 20, 20, 20));
        headerPanel.setMaximumSize(new Dimension(240, 100));
        
        JLabel lbliLib = new JLabel("BIBLIOTECA UMSA", SwingConstants.CENTER);
        lbliLib.setForeground(COLOR_TEXT_LIGHT); 
        lbliLib.setFont(FONT_TITLE);
        lbliLib.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        lblSubtitle.setForeground(new Color(173, 181, 189));
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(lbliLib);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblSubtitle);
        
        panelMenu.add(headerPanel);
        panelMenu.add(Box.createVerticalStrut(10));

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(52, 58, 64));
        separator.setMaximumSize(new Dimension(240, 1));
        panelMenu.add(separator);
        panelMenu.add(Box.createVerticalStrut(15));

        panelMenu.add(createMenuButton("Principal", "1.png", e -> mostrarVistaPrincipal()));
        panelMenu.add(createMenuButton("Préstamos", "2.png", e -> gestionarPrestamosAction()));
        panelMenu.add(createMenuButton("Usuarios", "3.png", e -> gestionarEstudiantesAction()));
        panelMenu.add(createMenuButton("Libros", "4.png", e -> gestionarLibrosAction()));
        panelMenu.add(createMenuButton("Autores", "5.png", e -> registrarAutoresAction())); 
        panelMenu.add(createMenuButton("Horarios", "6.png", e -> modificarHorarioAction()));
        panelMenu.add(createMenuButton("Reportes", "7.png", e -> mostrarResumenAction()));
        
        panelMenu.add(Box.createVerticalGlue());
        
        JSeparator separatorBottom = new JSeparator();
        separatorBottom.setForeground(new Color(52, 58, 64));
        separatorBottom.setMaximumSize(new Dimension(240, 1));
        panelMenu.add(separatorBottom);
        panelMenu.add(Box.createVerticalStrut(10));
        
        panelMenu.add(createMenuButton("Salir y Guardar", "9.png", e -> salirYGuardarAction(), true));
        panelMenu.add(Box.createVerticalStrut(15));

        return panelMenu;
    }
    
    private JButton createMenuButton(String text, String iconPath, java.awt.event.ActionListener action) {
        return createMenuButton(text, iconPath, action, false);
    }
    
    private JButton createMenuButton(String text, String iconPath, java.awt.event.ActionListener action, boolean isDanger) {
        JButton button = new JButton(text);
        button.setFont(FONT_MENU);
        button.setForeground(COLOR_TEXT_LIGHT); 
        button.setBackground(COLOR_SIDEBAR); 
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20)); 
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(240, 48)); 
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setRolloverEnabled(false);

        ImageIcon icon = loadIcon(iconPath);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setIconTextGap(15);
        }

        final Color hoverColor = isDanger ? COLOR_DANGER : COLOR_ACCENT;
        final Color normalColor = COLOR_SIDEBAR;

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
        
        button.addActionListener(action);
        return button;
    }

    private JPanel createContentHeader(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_HEADER);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_TEXT_DARK); 
        header.add(lblTitle, BorderLayout.WEST);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String dateString = sdf.format(new Date());
        
        JLabel lblDate = new JLabel(dateString, SwingConstants.RIGHT); 
        lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDate.setForeground(new Color(108, 117, 125));
        header.add(lblDate, BorderLayout.EAST);
        
        return header;
    }
    
    private void switchContent(String title, JComponent panel) {
        frame.setTitle("BIBLIOTECA UMSA - " + title); 
        contentPanel.removeAll();
        contentPanel.add(createContentHeader(title), BorderLayout.NORTH);
        
        if (!(panel instanceof JScrollPane)) {
             panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
             contentPanel.add(panel, BorderLayout.CENTER);
        } else {
             contentPanel.add(panel, BorderLayout.CENTER);
        }
       
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void mostrarVistaPrincipal() {
        JPanel panelBienvenida = new JPanel(new BorderLayout(40, 40));
        panelBienvenida.setBackground(Color.WHITE);
        panelBienvenida.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JLabel lblTitulo = new JLabel("¡Bienvenido a BIBLIOTECA UMSA!", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_ACCENT); 
        
        JTextArea txtDescripcion = new JTextArea();
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDescripcion.setForeground(new Color(73, 80, 87)); 
        txtDescripcion.setOpaque(false);
        txtDescripcion.setText(
            "Sistema de Gestión para Biblioteca Pública. Controle y administre de\n" +
            "forma óptima y fácil el flujo de préstamos y devoluciones de Libros.\n\n" +
            "Funcionalidades clave:\n" +
            "• Gestión completa de Préstamos y Devoluciones.\n" +
            "• Registro, Modificación y Eliminación de Usuarios (Estudiantes) y Libros.\n" +
            "• Gestión de Autores y Horarios.\n" +
            "• Reportes estadísticos del sistema.\n\n" +
            "Utilice el menú lateral para navegar por las opciones del sistema."
        );
        
        JPanel infoPanel = new JPanel(new BorderLayout(0, 20));
        infoPanel.add(lblTitulo, BorderLayout.NORTH);
        infoPanel.add(txtDescripcion, BorderLayout.CENTER);
        infoPanel.setBackground(Color.WHITE);
        
        ImageIcon bookIcon = loadIcon("10.png");
        JLabel lblImagen;
        if (bookIcon != null) {
            Image img = bookIcon.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH);
            lblImagen = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        } else {
            lblImagen = new JLabel("<html><div style='text-align: center; color: #6C757D;'>[IMAGEN DE LIBRO]<br>Icono no encontrado</div></html>", SwingConstants.CENTER);
            lblImagen.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            lblImagen.setPreferredSize(new Dimension(280, 280));
            lblImagen.setBackground(COLOR_BACKGROUND);
            lblImagen.setOpaque(true);
            lblImagen.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 2));
        }

        panelBienvenida.add(infoPanel, BorderLayout.CENTER);
        panelBienvenida.add(lblImagen, BorderLayout.EAST);

        switchContent("Principal", panelBienvenida);
    }
    
    private void gestionarLibrosAction() {
        GestionLibrosGUI gestor = new GestionLibrosGUI(biblioteca);
        switchContent("Gestión de Libros", gestor.getPanel());
    }
    
    private void gestionarEstudiantesAction() {
        GestionEstudiantesGUI gestor = new GestionEstudiantesGUI(biblioteca);
        switchContent("Gestión de Usuarios (Estudiantes)", gestor.getPanel());
    }
    
    private void gestionarPrestamosAction() {
        GestionPrestamosGUI gestor = new GestionPrestamosGUI(biblioteca);
        switchContent("Préstamos Activos y Devoluciones", gestor.getPanel());
    }
    
    private void modificarHorarioAction() {
        GestionHorarioGUI gestor = new GestionHorarioGUI(biblioteca);
        switchContent("Modificar Horario", gestor.getPanel());
    }

    private void mostrarResumenAction() {
        ReportesGUI gestor = new ReportesGUI(biblioteca);
        switchContent("Reportes y Resumen", gestor.getPanel());
    }
    
    private void registrarAutoresAction() {
        gestionarAutoresAction();
    }

    private void gestionarAutoresAction() {
        GestionAutoresGUI gestor = new GestionAutoresGUI(biblioteca, frame);
        switchContent("Gestión de Autores", gestor.getPanel());
    }

    private void salirYGuardarAction() {
        int confirm = JOptionPane.showConfirmDialog(frame, 
            "¿Desea guardar los cambios y salir del sistema?", 
            "Confirmar Salida", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            biblioteca.guardar(ARCHIVO_DATOS);
            JOptionPane.showMessageDialog(frame, 
                "Datos guardados exitosamente. Saliendo del sistema.", 
                "Guardar",
                JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}