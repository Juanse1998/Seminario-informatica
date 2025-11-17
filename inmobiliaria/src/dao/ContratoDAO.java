package dao;

import modelo.Contrato;
import java.sql.*;
import java.util.ArrayList;


public class ContratoDAO {
    
    
    public static ArrayList<Contrato> obtenerTodos() {
        ArrayList<Contrato> contratos = new ArrayList<>();
        String sql = "SELECT id_contrato, tipo, fecha_inicio, fecha_fin, monto, id_propiedad, id_inquilino, id_propietario, id_usuario FROM Contrato";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Contrato contrato = new Contrato(
                    rs.getInt("id_contrato"),
                    rs.getString("tipo"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null,
                    rs.getDouble("monto"),
                    rs.getInt("id_propiedad"),
                    rs.getInt("id_inquilino"),
                    rs.getInt("id_propietario"),
                    rs.getInt("id_usuario")
                );
                contratos.add(contrato);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener contratos: " + e.getMessage());
        }
        
        return contratos;
    }
    
    
    public static boolean insertar(Contrato contrato) {
        String sql = "INSERT INTO Contrato (tipo, fecha_inicio, fecha_fin, monto, id_propiedad, id_inquilino, id_propietario, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, contrato.getTipo());
            stmt.setDate(2, Date.valueOf(contrato.getFechaInicio()));
            stmt.setDate(3, contrato.getFechaFin() != null ? Date.valueOf(contrato.getFechaFin()) : null);
            stmt.setDouble(4, contrato.getMonto());
            stmt.setInt(5, contrato.getIdPropiedad());
            stmt.setInt(6, contrato.getIdInquilino());
            stmt.setInt(7, contrato.getIdPropietario());
            stmt.setInt(8, contrato.getIdUsuario());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    contrato.setIdContrato(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar contrato: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean actualizar(Contrato contrato) {
        String sql = "UPDATE Contrato SET tipo = ?, fecha_inicio = ?, fecha_fin = ?, monto = ?, id_propiedad = ?, id_inquilino = ?, id_propietario = ?, id_usuario = ? WHERE id_contrato = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, contrato.getTipo());
            stmt.setDate(2, Date.valueOf(contrato.getFechaInicio()));
            stmt.setDate(3, contrato.getFechaFin() != null ? Date.valueOf(contrato.getFechaFin()) : null);
            stmt.setDouble(4, contrato.getMonto());
            stmt.setInt(5, contrato.getIdPropiedad());
            stmt.setInt(6, contrato.getIdInquilino());
            stmt.setInt(7, contrato.getIdPropietario());
            stmt.setInt(8, contrato.getIdUsuario());
            stmt.setInt(9, contrato.getIdContrato());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar contrato: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean eliminar(int id) {
        String sql = "DELETE FROM Contrato WHERE id_contrato = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar contrato: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static Contrato buscarPorId(int id) {
        String sql = "SELECT id_contrato, tipo, fecha_inicio, fecha_fin, monto, id_propiedad, id_inquilino, id_propietario, id_usuario FROM Contrato WHERE id_contrato = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Contrato(
                    rs.getInt("id_contrato"),
                    rs.getString("tipo"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null,
                    rs.getDouble("monto"),
                    rs.getInt("id_propiedad"),
                    rs.getInt("id_inquilino"),
                    rs.getInt("id_propietario"),
                    rs.getInt("id_usuario")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar contrato por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    
    public static ArrayList<Contrato> buscarPorPropiedad(int idPropiedad) {
        ArrayList<Contrato> contratos = new ArrayList<>();
        String sql = "SELECT id_contrato, tipo, fecha_inicio, fecha_fin, monto, id_propiedad, id_inquilino, id_propietario, id_usuario FROM Contrato WHERE id_propiedad = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPropiedad);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Contrato contrato = new Contrato(
                    rs.getInt("id_contrato"),
                    rs.getString("tipo"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null,
                    rs.getDouble("monto"),
                    rs.getInt("id_propiedad"),
                    rs.getInt("id_inquilino"),
                    rs.getInt("id_propietario"),
                    rs.getInt("id_usuario")
                );
                contratos.add(contrato);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar contratos por propiedad: " + e.getMessage());
        }
        
        return contratos;
    }
    
    
    public static ArrayList<Object[]> obtenerContratosDetallados() {
        ArrayList<Object[]> contratos = new ArrayList<>();
        String sql = "SELECT " +
                    "co.id_contrato, " +
                    "co.tipo AS tipo_contrato, " +
                    "co.fecha_inicio, " +
                    "co.fecha_fin, " +
                    "co.monto, " +
                    "CONCAT(prop.nombre, ' ', prop.apellido) AS propietario, " +
                    "CONCAT(inq.nombre, ' ', inq.apellido) AS inquilino, " +
                    "p.direccion AS direccion_propiedad, " +
                    "u.nombre_usuario AS creado_por " +
                    "FROM Contrato co " +
                    "JOIN Propiedad p ON co.id_propiedad = p.id_propiedad " +
                    "JOIN Cliente prop ON co.id_propietario = prop.id_cliente " +
                    "LEFT JOIN Cliente inq ON co.id_inquilino = inq.id_cliente " +
                    "JOIN Usuarios u ON co.id_usuario = u.id_usuario";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_contrato"),
                    rs.getString("tipo_contrato"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null,
                    rs.getDouble("monto"),
                    rs.getString("propietario"),
                    rs.getString("inquilino"),
                    rs.getString("direccion_propiedad"),
                    rs.getString("creado_por")
                };
                contratos.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener contratos detallados: " + e.getMessage());
        }
        
        return contratos;
    }
}
