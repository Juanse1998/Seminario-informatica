package dao;

import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;


public class UsuarioDAO {
    
    
    public static Usuario autenticar(String nombreUsuario, String contrasena) {
        String sql = "SELECT id_usuario, nombre_usuario, contrasena, rol, activo " +
                    "FROM Usuarios WHERE nombre_usuario = ? AND contrasena = ? AND activo = TRUE";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasena"),
                    rs.getString("rol"),
                    rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }
        
        return null;
    }
    
    
    public static ArrayList<Usuario> obtenerTodos() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre_usuario, contrasena, rol, activo FROM Usuarios";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasena"),
                    rs.getString("rol"),
                    rs.getBoolean("activo")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    
    public static boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nombre_usuario, contrasena, rol, activo) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getRol());
            stmt.setBoolean(4, usuario.isActivo());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean actualizar(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nombre_usuario = ?, contrasena = ?, rol = ?, activo = ? WHERE id_usuario = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getRol());
            stmt.setBoolean(4, usuario.isActivo());
            stmt.setInt(5, usuario.getIdUsuario());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean eliminar(int id) {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static Usuario buscarPorId(int id) {
        String sql = "SELECT id_usuario, nombre_usuario, contrasena, rol, activo FROM Usuarios WHERE id_usuario = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasena"),
                    rs.getString("rol"),
                    rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        }
        
        return null;
    }
}
