package dao;

import modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;


public class ClienteDAO {
    
    
    public static ArrayList<Cliente> obtenerTodos() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, apellido, dni, telefono, email, direccion, tipo FROM Cliente";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("direccion"),
                    rs.getString("tipo")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        
        return clientes;
    }
    
    
    public static boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nombre, apellido, dni, telefono, email, direccion, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getDni());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getDireccion());
            stmt.setString(7, cliente.getTipo());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cliente.setIdCliente(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean actualizar(Cliente cliente) {
        String sql = "UPDATE Cliente SET nombre = ?, apellido = ?, dni = ?, telefono = ?, email = ?, direccion = ?, tipo = ? WHERE id_cliente = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getDni());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getDireccion());
            stmt.setString(7, cliente.getTipo());
            stmt.setInt(8, cliente.getIdCliente());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static boolean eliminar(int id) {
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
        
        return false;
    }
    
    
    public static Cliente buscarPorId(int id) {
        String sql = "SELECT id_cliente, nombre, apellido, dni, telefono, email, direccion, tipo FROM Cliente WHERE id_cliente = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("direccion"),
                    rs.getString("tipo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    
    public static ArrayList<Cliente> buscarPorTipo(String tipo) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, apellido, dni, telefono, email, direccion, tipo FROM Cliente WHERE tipo = ? OR tipo = 'ambos'";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("direccion"),
                    rs.getString("tipo")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes por tipo: " + e.getMessage());
        }
        
        return clientes;
    }
    
    
    public static ArrayList<Cliente> buscarPorNombre(String nombre) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, apellido, dni, telefono, email, direccion, tipo FROM Cliente WHERE nombre LIKE ? OR apellido LIKE ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String patron = "%" + nombre + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("direccion"),
                    rs.getString("tipo")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes por nombre: " + e.getMessage());
        }
        
        return clientes;
    }
}
