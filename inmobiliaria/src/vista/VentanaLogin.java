package vista;

import controlador.InmobiliariaDB;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;


public class VentanaLogin extends JFrame {
    private InmobiliariaDB inmobiliaria;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancelar;
    
    public VentanaLogin(InmobiliariaDB inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Gestión Inmobiliaria - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN INMOBILIARIA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        
        
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelFormulario.add(new JLabel("Usuario:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);
        
        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e -> validarLogin());
        panelBotones.add(btnLogin);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> System.exit(0));
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        
        JPanel panelInfo = new JPanel();
        JLabel lblInfo = new JLabel("<html><center>Usuarios de prueba:<br>" +
                                    "admin / admin123<br>" +
                                    "empleado / empleado123</center></html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 10));
        lblInfo.setForeground(Color.GRAY);
        panelInfo.add(lblInfo);
        panelPrincipal.add(panelInfo, BorderLayout.AFTER_LAST_LINE);
        
        add(panelPrincipal);
        
        
        txtPassword.addActionListener(e -> validarLogin());
    }
    
    private void validarLogin() {
        String nombreUsuario = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        
        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, complete todos los campos", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usuario usuario = null;
        try {
            usuario = inmobiliaria.autenticarUsuario(nombreUsuario, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (usuario != null) {
            
            JOptionPane.showMessageDialog(this, 
                "Bienvenido, " + usuario.getNombre() + "!", 
                "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(inmobiliaria, usuario);
            ventanaPrincipal.setVisible(true);
            
            
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Usuario o contraseña incorrectos", 
                "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }
}
