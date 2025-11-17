package dao;

import modelo.Pago;
import java.sql.*;
import java.util.ArrayList;


public class PagoDAO {
    
    
    public static ArrayList<Pago> obtenerTodos() {
        ArrayList<Pago> pagos = new ArrayList<>();
        String sql = "SELECT id_pago, id_contrato, fecha_pago, monto, metodo_pago, observaciones FROM Pago";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Pago pago = new Pago(
                    rs.getInt("id_pago"),
                    rs.getInt("id_contrato"),
                    rs.getDate("fecha_pago").toLocalDate(),
                    rs.getDouble("monto"),
                    rs.getString("metodo_pago"),
                    rs.getString("observaciones")
                );
                pagos.add(pago);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pagos: " + e.getMessage());
        }
        
        return pagos;
    }
    
    
    public static boolean insertar(Pago pago) {
        String sql = "INSERT INTO Pago (id_contrato, fecha_pago, monto, metodo_pago, observaciones) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, pago.getIdContrato());
            stmt.setDate(2, Date.valueOf(pago.getFechaPago()));
            stmt.setDouble(3, pago.getMonto());
            stmt.setString(4, pago.getMetodoPago());
            stmt.setString(5, pago.getObservaciones());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    pago.setIdPago(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar pago: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean actualizar(Pago pago) {
        String sql = "UPDATE Pago SET id_contrato = ?, fecha_pago = ?, monto = ?, metodo_pago = ?, observaciones = ? WHERE id_pago = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pago.getIdContrato());
            stmt.setDate(2, Date.valueOf(pago.getFechaPago()));
            stmt.setDouble(3, pago.getMonto());
            stmt.setString(4, pago.getMetodoPago());
            stmt.setString(5, pago.getObservaciones());
            stmt.setInt(6, pago.getIdPago());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar pago: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean eliminar(int id) {
        String sql = "DELETE FROM Pago WHERE id_pago = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar pago: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static Pago buscarPorId(int id) {
        String sql = "SELECT id_pago, id_contrato, fecha_pago, monto, metodo_pago, observaciones FROM Pago WHERE id_pago = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Pago(
                    rs.getInt("id_pago"),
                    rs.getInt("id_contrato"),
                    rs.getDate("fecha_pago").toLocalDate(),
                    rs.getDouble("monto"),
                    rs.getString("metodo_pago"),
                    rs.getString("observaciones")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pago por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    
    public static ArrayList<Pago> buscarPorContrato(int idContrato) {
        ArrayList<Pago> pagos = new ArrayList<>();
        String sql = "SELECT id_pago, id_contrato, fecha_pago, monto, metodo_pago, observaciones FROM Pago WHERE id_contrato = ? ORDER BY fecha_pago DESC";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idContrato);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pago pago = new Pago(
                    rs.getInt("id_pago"),
                    rs.getInt("id_contrato"),
                    rs.getDate("fecha_pago").toLocalDate(),
                    rs.getDouble("monto"),
                    rs.getString("metodo_pago"),
                    rs.getString("observaciones")
                );
                pagos.add(pago);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pagos por contrato: " + e.getMessage());
        }
        
        return pagos;
    }
    
    
    public static ArrayList<Object[]> obtenerPagosDetallados() {
        ArrayList<Object[]> pagos = new ArrayList<>();
        String sql = "SELECT " +
                    "pa.id_pago, " +
                    "pa.fecha_pago, " +
                    "pa.monto, " +
                    "pa.metodo_pago, " +
                    "pa.observaciones, " +
                    "co.tipo AS tipo_contrato, " +
                    "CONCAT(ci.nombre, ' ', ci.apellido) AS inquilino, " +
                    "pr.direccion AS propiedad " +
                    "FROM Pago pa " +
                    "JOIN Contrato co ON pa.id_contrato = co.id_contrato " +
                    "LEFT JOIN Cliente ci ON co.id_inquilino = ci.id_cliente " +
                    "LEFT JOIN Propiedad pr ON co.id_propiedad = pr.id_propiedad " +
                    "ORDER BY pa.fecha_pago DESC";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_pago"),
                    rs.getDate("fecha_pago").toLocalDate(),
                    rs.getDouble("monto"),
                    rs.getString("metodo_pago"),
                    rs.getString("observaciones"),
                    rs.getString("tipo_contrato"),
                    rs.getString("inquilino"),
                    rs.getString("propiedad")
                };
                pagos.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pagos detallados: " + e.getMessage());
        }
        
        return pagos;
    }
}
