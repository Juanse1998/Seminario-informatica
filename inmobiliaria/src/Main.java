import controlador.Inmobiliaria;
import vista.VentanaLogin;

import javax.swing.*;

/**
 * Clase principal del Sistema de Gestión Inmobiliaria
 * Punto de entrada de la aplicación
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al configurar Look and Feel: " + e.getMessage());
        }
        
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            // Crear instancia del controlador principal
            Inmobiliaria inmobiliaria = new Inmobiliaria();
            
            // Crear y mostrar ventana de login
            VentanaLogin ventanaLogin = new VentanaLogin(inmobiliaria);
            ventanaLogin.setVisible(true);
        });
    }
}
