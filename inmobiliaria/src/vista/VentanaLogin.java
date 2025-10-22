package vista;

import controlador.Inmobiliaria;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de login del sistema
 * Demuestra el uso de SWING para autenticación
 */
public class VentanaLogin extends JFrame {
    private Inmobiliaria inmobiliaria;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancelar;
    
    public VentanaLogin(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Gestión Inmobiliaria - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN INMOBILIARIA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelFormulario.add(new JLabel("Usuario:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);
        
        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e -> validarLogin());
        panelBotones.add(btnLogin);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> System.exit(0));
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Panel de información
        JPanel panelInfo = new JPanel();
        JLabel lblInfo = new JLabel("<html><center>Usuarios de prueba:<br>" +
                                    "admin / admin123<br>" +
                                    "empleado / empleado123</center></html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 10));
        lblInfo.setForeground(Color.GRAY);
        panelInfo.add(lblInfo);
        panelPrincipal.add(panelInfo, BorderLayout.AFTER_LAST_LINE);
        
        add(panelPrincipal);
        
        // Enter para login
        txtPassword.addActionListener(e -> validarLogin());
    }
    
    private void validarLogin() {
        String nombreUsuario = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // ESTRUCTURA CONDICIONAL: if-else
        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, complete todos los campos", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usuario usuario = inmobiliaria.validarUsuario(nombreUsuario, password);
        
        if (usuario != null) {
            // Login exitoso
            JOptionPane.showMessageDialog(this, 
                "Bienvenido, " + usuario.getNombre() + "!", 
                "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir ventana principal
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(inmobiliaria, usuario);
            ventanaPrincipal.setVisible(true);
            
            // Cerrar ventana de login
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Usuario o contraseña incorrectos", 
                "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }
}
