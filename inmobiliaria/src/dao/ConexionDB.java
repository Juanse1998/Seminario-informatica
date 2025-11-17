package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/inmobiliaria_db";
    private static final String USUARIO = "inmobiliaria";
    private static final String PASSWORD = "inmobiliaria123";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection conexion = null;

    
    public static Connection getConexion() throws SQLException {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName(DRIVER);
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión a MySQL establecida exitosamente");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado");
            System.err.println("Asegúrate de tener mysql-connector-java en el classpath");
            throw new SQLException("Driver no encontrado", e);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            throw e;
        }
        return conexion;
    }

    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    
    public static boolean isConectado() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
