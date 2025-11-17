package controlador;

import modelo.*;
import excepciones.*;
import dao.*;
import java.util.ArrayList;


public class InmobiliariaDB {
    
    
    public InmobiliariaDB() {
        
        try {
            ConexionDB.getConexion();
            System.out.println("Sistema inicializado con conexión a MySQL");
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            System.err.println("El sistema funcionará en modo offline");
        }
    }
    
    
    
    
    public Usuario autenticarUsuario(String nombreUsuario, String contrasena) throws CampoVacioException {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new CampoVacioException("El nombre de usuario no puede estar vacío");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new CampoVacioException("La contraseña no puede estar vacía");
        }
        
        return UsuarioDAO.autenticar(nombreUsuario, contrasena);
    }
    
    
    public ArrayList<Usuario> getUsuarios() {
        return UsuarioDAO.obtenerTodos();
    }
    
    
    public boolean agregarUsuario(Usuario usuario) throws CampoVacioException {
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
            throw new CampoVacioException("El nombre de usuario no puede estar vacío");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new CampoVacioException("La contraseña no puede estar vacía");
        }
        
        return UsuarioDAO.insertar(usuario);
    }
    
    
    
    
    public ArrayList<Cliente> getClientes() {
        return ClienteDAO.obtenerTodos();
    }
    
    
    public boolean agregarCliente(Cliente cliente) throws CampoVacioException {
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new CampoVacioException("El nombre del cliente no puede estar vacío");
        }
        if (cliente.getApellido() == null || cliente.getApellido().trim().isEmpty()) {
            throw new CampoVacioException("El apellido del cliente no puede estar vacío");
        }
        if (cliente.getDni() == null || cliente.getDni().trim().isEmpty()) {
            throw new CampoVacioException("El DNI del cliente no puede estar vacío");
        }
        
        return ClienteDAO.insertar(cliente);
    }
    
    
    public boolean modificarCliente(Cliente cliente) throws CampoVacioException {
        if (cliente.getIdCliente() <= 0) {
            throw new CampoVacioException("ID de cliente inválido");
        }
        
        return ClienteDAO.actualizar(cliente);
    }
    
    
    public boolean eliminarCliente(int id) {
        return ClienteDAO.eliminar(id);
    }
    
    
    public Cliente buscarClientePorId(int id) {
        return ClienteDAO.buscarPorId(id);
    }
    
    
    public ArrayList<Cliente> buscarClientesPorTipo(String tipo) {
        return ClienteDAO.buscarPorTipo(tipo);
    }
    
    
    public ArrayList<Cliente> buscarClientesPorNombre(String nombre) {
        return ClienteDAO.buscarPorNombre(nombre);
    }
    
    
    
    
    public ArrayList<Propiedad> getPropiedades() {
        return PropiedadDAO.obtenerTodas();
    }
    
    
    public boolean agregarPropiedad(Propiedad propiedad) throws PrecioInvalidoException, CampoVacioException {
        if (propiedad.getPrecio() <= 0) {
            throw new PrecioInvalidoException("El precio debe ser mayor a 0");
        }
        if (propiedad.getDireccion() == null || propiedad.getDireccion().trim().isEmpty()) {
            throw new CampoVacioException("La dirección no puede estar vacía");
        }
        if (propiedad.getIdPropietario() <= 0) {
            throw new CampoVacioException("Debe especificar un propietario válido");
        }
        
        return PropiedadDAO.insertar(propiedad);
    }
    
    
    public boolean modificarPropiedad(Propiedad propiedad) throws PrecioInvalidoException, CampoVacioException {
        if (propiedad.getIdPropiedad() <= 0) {
            throw new CampoVacioException("ID de propiedad inválido");
        }
        if (propiedad.getPrecio() <= 0) {
            throw new PrecioInvalidoException("El precio debe ser mayor a 0");
        }
        
        return PropiedadDAO.actualizar(propiedad);
    }
    
    
    public boolean eliminarPropiedad(int id) {
        return PropiedadDAO.eliminar(id);
    }
    
    
    public Propiedad buscarPropiedadPorId(int id) {
        return PropiedadDAO.buscarPorId(id);
    }
    
    
    public ArrayList<Propiedad> buscarPropiedadesPorEstado(String estado) {
        return PropiedadDAO.buscarPorEstado(estado);
    }
    
    
    public ArrayList<Propiedad> buscarPropiedadesPorDireccion(String direccion) {
        return PropiedadDAO.buscarPorDireccion(direccion);
    }
    
    
    public ArrayList<Propiedad> ordenarPropiedadesPorPrecio() {
        ArrayList<Propiedad> propiedades = PropiedadDAO.obtenerTodas();
        propiedades.sort((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()));
        return propiedades;
    }
    
    
    
    
    public ArrayList<Contrato> getContratos() {
        return ContratoDAO.obtenerTodos();
    }
    
    
    public boolean agregarContrato(Contrato contrato) throws CampoVacioException {
        if (contrato.getIdPropiedad() <= 0) {
            throw new CampoVacioException("Debe especificar una propiedad válida");
        }
        if (contrato.getIdPropietario() <= 0) {
            throw new CampoVacioException("Debe especificar un propietario válido");
        }
        if (contrato.getMonto() <= 0) {
            throw new CampoVacioException("El monto debe ser mayor a 0");
        }
        
        
        Propiedad propiedad = PropiedadDAO.buscarPorId(contrato.getIdPropiedad());
        if (propiedad != null) {
            if (contrato.getTipo().equals("alquiler")) {
                propiedad.setEstado("alquilada");
            } else {
                propiedad.setEstado("vendida");
            }
            PropiedadDAO.actualizar(propiedad);
        }
        
        return ContratoDAO.insertar(contrato);
    }
    
    
    public boolean eliminarContrato(int id) {
        
        Contrato contrato = ContratoDAO.buscarPorId(id);
        if (contrato != null) {
            Propiedad propiedad = PropiedadDAO.buscarPorId(contrato.getIdPropiedad());
            if (propiedad != null) {
                propiedad.setEstado("disponible");
                PropiedadDAO.actualizar(propiedad);
            }
        }
        
        return ContratoDAO.eliminar(id);
    }
    
    
    public Contrato buscarContratoPorId(int id) {
        return ContratoDAO.buscarPorId(id);
    }
    
    
    public ArrayList<Object[]> getContratosDetallados() {
        return ContratoDAO.obtenerContratosDetallados();
    }
    
    
    
    
    public ArrayList<Pago> getPagos() {
        return PagoDAO.obtenerTodos();
    }
    
    
    public boolean agregarPago(Pago pago) throws CampoVacioException {
        if (pago.getIdContrato() <= 0) {
            throw new CampoVacioException("Debe especificar un contrato válido");
        }
        if (pago.getMonto() <= 0) {
            throw new CampoVacioException("El monto debe ser mayor a 0");
        }
        
        return PagoDAO.insertar(pago);
    }
    
    
    public ArrayList<Pago> buscarPagosPorContrato(int idContrato) {
        return PagoDAO.buscarPorContrato(idContrato);
    }
    
    
    public Pago buscarPagoPorId(int id) {
        return PagoDAO.buscarPorId(id);
    }
    
    
    public ArrayList<Object[]> getPagosDetallados() {
        return PagoDAO.obtenerPagosDetallados();
    }
    
    
    
    
    public ArrayList<Recibo> getRecibos() {
        return ReciboDAO.obtenerTodos();
    }
    
    
    public boolean agregarRecibo(Recibo recibo) throws CampoVacioException {
        if (recibo.getIdPago() <= 0) {
            throw new CampoVacioException("Debe especificar un pago válido");
        }
        
        return ReciboDAO.insertar(recibo);
    }
    
    
    public Recibo buscarReciboPorIdPago(int idPago) {
        return ReciboDAO.buscarPorIdPago(idPago);
    }
    
    
    public boolean existeReciboPorPago(int idPago) {
        return ReciboDAO.existeReciboPorPago(idPago);
    }
    
    
    
    
    public String obtenerEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DEL SISTEMA ===\n\n");
        
        
        ArrayList<Propiedad> propiedades = getPropiedades();
        int disponibles = 0, alquiladas = 0, vendidas = 0;
        
        for (Propiedad p : propiedades) {
            switch (p.getEstado()) {
                case "disponible": disponibles++; break;
                case "alquilada": alquiladas++; break;
                case "vendida": vendidas++; break;
            }
        }
        
        stats.append("PROPIEDADES:\n");
        stats.append("- Total: ").append(propiedades.size()).append("\n");
        stats.append("- Disponibles: ").append(disponibles).append("\n");
        stats.append("- Alquiladas: ").append(alquiladas).append("\n");
        stats.append("- Vendidas: ").append(vendidas).append("\n\n");
        
        
        ArrayList<Cliente> clientes = getClientes();
        int propietarios = 0, inquilinos = 0, ambos = 0;
        
        for (Cliente c : clientes) {
            switch (c.getTipo()) {
                case "propietario": propietarios++; break;
                case "inquilino": inquilinos++; break;
                case "ambos": ambos++; break;
            }
        }
        
        stats.append("CLIENTES:\n");
        stats.append("- Total: ").append(clientes.size()).append("\n");
        stats.append("- Propietarios: ").append(propietarios).append("\n");
        stats.append("- Inquilinos: ").append(inquilinos).append("\n");
        stats.append("- Ambos: ").append(ambos).append("\n\n");
        
        
        ArrayList<Contrato> contratos = getContratos();
        int alquileres = 0, ventas = 0;
        
        for (Contrato c : contratos) {
            if (c.getTipo().equals("alquiler")) {
                alquileres++;
            } else {
                ventas++;
            }
        }
        
        stats.append("CONTRATOS:\n");
        stats.append("- Total: ").append(contratos.size()).append("\n");
        stats.append("- Alquileres: ").append(alquileres).append("\n");
        stats.append("- Ventas: ").append(ventas).append("\n\n");
        
        
        ArrayList<Pago> pagos = getPagos();
        stats.append("PAGOS:\n");
        stats.append("- Total: ").append(pagos.size()).append("\n");
        
        return stats.toString();
    }
    
    
    public void cerrarConexion() {
        ConexionDB.cerrarConexion();
    }
}
