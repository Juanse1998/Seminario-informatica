import controlador.InmobiliariaDB;
import vista.VentanaLogin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Textos en español para los diálogos
            UIManager.put("OptionPane.yesButtonText", "Sí");
            UIManager.put("OptionPane.noButtonText", "No");
            UIManager.put("OptionPane.cancelButtonText", "Cancelar");
            UIManager.put("OptionPane.okButtonText", "Aceptar");
        } catch (Exception e) {
            System.err.println("Error al configurar Look and Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            InmobiliariaDB inmobiliaria = new InmobiliariaDB();
            VentanaLogin ventanaLogin = new VentanaLogin(inmobiliaria);
            ventanaLogin.setVisible(true);
        });
    }
}