package dao;

import modelo.Propiedad;
import modelo.PropiedadResidencial;
import modelo.PropiedadComercial;
import java.sql.*;
import java.util.ArrayList;


public class PropiedadDAO {
    
    
    public static ArrayList<Propiedad> obtenerTodas() {
        ArrayList<Propiedad> propiedades = new ArrayList<>();
        String sql = "SELECT p.id_propiedad, p.direccion, p.tipo, p.precio, p.estado, p.descripcion, p.id_propietario, " +
                    "CONCAT(c.nombre, ' ', c.apellido) AS propietario " +
                    "FROM Propiedad p " +
                    "JOIN Cliente c ON p.id_propietario = c.id_cliente";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                
                Propiedad propiedad;
                String tipo = rs.getString("tipo");
                
                if (tipo.equals("casa") || tipo.equals("departamento")) {
                    propiedad = new PropiedadResidencial(
                        rs.getInt("id_propiedad"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("descripcion"),
                        rs.getInt("id_propietario"),
                        3, 2, true, 100.0 
                    );
                } else {
                    propiedad = new PropiedadComercial(
                        rs.getInt("id_propiedad"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("descripcion"),
                        rs.getInt("id_propietario"),
                        100.0, "Centro", false, 50 
                    );
                }
                
                propiedades.add(propiedad);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener propiedades: " + e.getMessage());
        }
        
        return propiedades;
    }
    
    
    public static boolean insertar(Propiedad propiedad) {
        String sql = "INSERT INTO Propiedad (direccion, tipo, precio, estado, descripcion, id_propietario) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, propiedad.getDireccion());
            stmt.setString(2, propiedad.getTipo());
            stmt.setDouble(3, propiedad.getPrecio());
            stmt.setString(4, propiedad.getEstado());
            stmt.setString(5, propiedad.getDescripcion());
            stmt.setInt(6, propiedad.getIdPropietario());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    propiedad.setIdPropiedad(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar propiedad: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean actualizar(Propiedad propiedad) {
        String sql = "UPDATE Propiedad SET direccion = ?, tipo = ?, precio = ?, estado = ?, descripcion = ?, id_propietario = ? WHERE id_propiedad = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, propiedad.getDireccion());
            stmt.setString(2, propiedad.getTipo());
            stmt.setDouble(3, propiedad.getPrecio());
            stmt.setString(4, propiedad.getEstado());
            stmt.setString(5, propiedad.getDescripcion());
            stmt.setInt(6, propiedad.getIdPropietario());
            stmt.setInt(7, propiedad.getIdPropiedad());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar propiedad: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean eliminar(int id) {
        String sql = "DELETE FROM Propiedad WHERE id_propiedad = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar propiedad: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static Propiedad buscarPorId(int id) {
        String sql = "SELECT id_propiedad, direccion, tipo, precio, estado, descripcion, id_propietario FROM Propiedad WHERE id_propiedad = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                
                if (tipo.equals("casa") || tipo.equals("departamento")) {
                    return new PropiedadResidencial(
                        rs.getInt("id_propiedad"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("descripcion"),
                        rs.getInt("id_propietario"),
                        3, 2, true, 100.0
                    );
                } else {
                    return new PropiedadComercial(
                        rs.getInt("id_propiedad"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("descripcion"),
                        rs.getInt("id_propietario"),
                        100.0, "Centro", false, 50
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar propiedad por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    
    public static ArrayList<Propiedad> buscarPorEstado(String estado) {
        ArrayList<Propiedad> propiedades = new ArrayList<>();
        String sql = "SELECT id_propiedad, direccion, tipo, precio, estado, descripcion, id_propietario FROM Propiedad WHERE estado = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Propiedad propiedad;
                
                if (tipo.equals("casa") || tipo.equals("departamento")) {
                    propiedad = new PropiedadResidencial(
                        rs.getInt("id_propiedad"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("descripcion"),
                        rs.getInt("id_propietario"),
                        3, 2, true, 100.0
                    );
                } else {
                    propiedad = new PropiedadComercial(
                        rs.getInt("id_propiedad"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("descripcion"),
                        rs.getInt("id_propietario"),
                        100.0, "Centro", false, 50
                    );
                }
                
                propiedades.add(propiedad);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar propiedades por estado: " + e.getMessage());
        }
        
        return propiedades;
    }
    
    
    public static ArrayList<Propiedad> buscarPorDireccion(String direccion) {
        ArrayList<Propiedad> propiedades = new ArrayList<>();
        String sql = "SELECT id_propiedad, direccion, tipo, precio, estado, descripcion, id_propietario FROM Propiedad WHERE direccion LIKE ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + direccion + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Propiedad propiedad;
                
                if (tipo.equals("casa") || tipo.equals("departamento")) {
                    propiedad = new PropiedadResidencial(
                        rs.getInt("id_propiedad"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("descripcion"),
                        rs.getInt("id_propietario"),
                        3, 2, true, 100.0
                    );
                } else {
                    propiedad = new PropiedadComercial(
                        rs.getInt("id_propiedad"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("descripcion"),
                        rs.getInt("id_propietario"),
                        100.0, "Centro", false, 50
                    );
                }
                
                propiedades.add(propiedad);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar propiedades por dirección: " + e.getMessage());
        }
        
        return propiedades;
    }
}
