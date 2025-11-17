package dao;

import modelo.Recibo;
import java.sql.*;
import java.util.ArrayList;


public class ReciboDAO {
    
    
    public static ArrayList<Recibo> obtenerTodos() {
        ArrayList<Recibo> recibos = new ArrayList<>();
        String sql = "SELECT id_recibo, id_pago, fecha_emision, formato FROM Recibo";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Recibo recibo = new Recibo(
                    rs.getInt("id_recibo"),
                    rs.getInt("id_pago"),
                    rs.getDate("fecha_emision").toLocalDate(),
                    rs.getString("formato")
                );
                recibos.add(recibo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener recibos: " + e.getMessage());
        }
        
        return recibos;
    }
    
    
    public static boolean insertar(Recibo recibo) {
        String sql = "INSERT INTO Recibo (id_pago, fecha_emision, formato) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, recibo.getIdPago());
            stmt.setDate(2, Date.valueOf(recibo.getFechaEmision()));
            stmt.setString(3, recibo.getFormato());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    recibo.setIdRecibo(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar recibo: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean actualizar(Recibo recibo) {
        String sql = "UPDATE Recibo SET id_pago = ?, fecha_emision = ?, formato = ? WHERE id_recibo = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, recibo.getIdPago());
            stmt.setDate(2, Date.valueOf(recibo.getFechaEmision()));
            stmt.setString(3, recibo.getFormato());
            stmt.setInt(4, recibo.getIdRecibo());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar recibo: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean eliminar(int id) {
        String sql = "DELETE FROM Recibo WHERE id_recibo = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar recibo: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static Recibo buscarPorId(int id) {
        String sql = "SELECT id_recibo, id_pago, fecha_emision, formato FROM Recibo WHERE id_recibo = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Recibo(
                    rs.getInt("id_recibo"),
                    rs.getInt("id_pago"),
                    rs.getDate("fecha_emision").toLocalDate(),
                    rs.getString("formato")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar recibo por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    
    public static Recibo buscarPorIdPago(int idPago) {
        String sql = "SELECT id_recibo, id_pago, fecha_emision, formato FROM Recibo WHERE id_pago = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPago);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Recibo(
                    rs.getInt("id_recibo"),
                    rs.getInt("id_pago"),
                    rs.getDate("fecha_emision").toLocalDate(),
                    rs.getString("formato")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar recibo por ID de pago: " + e.getMessage());
        }
        
        return null;
    }
    
    
    public static boolean existeReciboPorPago(int idPago) {
        String sql = "SELECT COUNT(*) FROM Recibo WHERE id_pago = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPago);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de recibo: " + e.getMessage());
        }
        
        return false;
    }
}
