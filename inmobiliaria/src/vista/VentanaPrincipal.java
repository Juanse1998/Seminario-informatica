package vista;

import controlador.InmobiliariaDB;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;


public class VentanaPrincipal extends JFrame {
    private InmobiliariaDB inmobiliaria;
    private Usuario usuarioActual;
    private JTabbedPane tabbedPane;
    private PanelPropiedades panelPropiedades;
    private PanelClientes panelClientes;
    private PanelContratos panelContratos;
    private PanelPagos panelPagos;

    public VentanaPrincipal(InmobiliariaDB inmobiliaria, Usuario usuario) {
        this.inmobiliaria = inmobiliaria;
        this.usuarioActual = usuario;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Gestión Inmobiliaria - Usuario: " + usuarioActual.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        
        crearMenu();

        
        tabbedPane = new JTabbedPane();

        
        panelPropiedades = new PanelPropiedades(inmobiliaria);
        panelClientes = new PanelClientes(inmobiliaria);
        panelContratos = new PanelContratos(inmobiliaria);
        panelPagos = new PanelPagos(inmobiliaria, panelContratos);

        
        tabbedPane.addTab("Propiedades", panelPropiedades);
        tabbedPane.addTab("Clientes", panelClientes);
        tabbedPane.addTab("Contratos", panelContratos);
        tabbedPane.addTab("Pagos y Recibos", panelPagos);

        add(tabbedPane, BorderLayout.CENTER);

        
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblEstado = new JLabel("Usuario: " + usuarioActual.getNombre() +
                " | Rol: " + usuarioActual.getRol());
        panelEstado.add(lblEstado);
        add(panelEstado, BorderLayout.SOUTH);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de salir del sistema?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        menuArchivo.add(itemSalir);

        
        JMenu menuGestion = new JMenu("Gestión");

        JMenuItem itemPropiedades = new JMenuItem("Propiedades");
        itemPropiedades.addActionListener(e -> tabbedPane.setSelectedIndex(0));

        JMenuItem itemClientes = new JMenuItem("Clientes");
        itemClientes.addActionListener(e -> tabbedPane.setSelectedIndex(1));

        JMenuItem itemContratos = new JMenuItem("Contratos");
        itemContratos.addActionListener(e -> tabbedPane.setSelectedIndex(2));

        JMenuItem itemPagos = new JMenuItem("Pagos y Recibos");
        itemPagos.addActionListener(e -> tabbedPane.setSelectedIndex(3));

        menuGestion.add(itemPropiedades);
        menuGestion.add(itemClientes);
        menuGestion.add(itemContratos);
        menuGestion.add(itemPagos);

        
        JMenu menuReportes = new JMenu("Reportes");

        JMenuItem itemEstadisticas = new JMenuItem("Estadísticas");
        itemEstadisticas.addActionListener(e -> mostrarEstadisticas());

        menuReportes.add(itemEstadisticas);

        
        JMenu menuAyuda = new JMenu("Ayuda");

        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());

        menuAyuda.add(itemAcercaDe);

        
        menuBar.add(menuArchivo);
        menuBar.add(menuGestion);
        menuBar.add(menuReportes);
        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);
    }

    private void mostrarEstadisticas() {
        String estadisticas = inmobiliaria.obtenerEstadisticas();

        JDialog dialogo = new JDialog(this, "Estadísticas del Sistema", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JTextArea textArea = new JTextArea(estadisticas);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dialogo.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void mostrarAcercaDe() {
        String mensaje = "Sistema de Gestión Inmobiliaria (SGI)\n\n" +
                "Versión: 1.0\n" +
                "Desarrollado en Java con Swing\n\n" +
                "Características:\n" +
                "- Gestión de Propiedades (Residenciales y Comerciales)\n" +
                "- Gestión de Clientes\n" +
                "- Gestión de Reservas\n" +
                "- Sistema de autenticación\n\n" +
                "Conceptos POO implementados:\n" +
                "- Encapsulamiento\n" +
                "- Herencia\n" +
                "- Polimorfismo\n" +
                "- Abstracción\n" +
                "- Manejo de excepciones\n\n" +
                "© 2024 - Sistema Inmobiliario";

        JOptionPane.showMessageDialog(this, mensaje,
                "Acerca de SGI", JOptionPane.INFORMATION_MESSAGE);
    }
}
