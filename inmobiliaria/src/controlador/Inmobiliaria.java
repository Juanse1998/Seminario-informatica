package controlador;

import modelo.*;
import excepciones.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Clase Inmobiliaria - Controlador principal del sistema
 * Gestiona todas las operaciones CRUD sobre propiedades, clientes y reservas
 * Demuestra el uso de ESTRUCTURAS DE DATOS (ArrayList) y ALGORITMOS
 */
public class Inmobiliaria {
    // Colecciones para almacenar los datos - ESTRUCTURA DE DATOS: ArrayList
    private ArrayList<Propiedad> propiedades;
    private ArrayList<Cliente> clientes;
    private ArrayList<Reserva> reservas;
    private ArrayList<Usuario> usuarios;
    
    // Contadores para generar IDs automáticos
    private int contadorPropiedades;
    private int contadorClientes;
    private int contadorReservas;
    
    // Constructor por defecto
    public Inmobiliaria() {
        this.propiedades = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.contadorPropiedades = 1;
        this.contadorClientes = 1;
        this.contadorReservas = 1;
        inicializarDatosPrueba();
    }
    
    // ==================== GESTIÓN DE PROPIEDADES ====================
    
    /**
     * Agrega una nueva propiedad al sistema
     * Valida que los datos sean correctos usando EXCEPCIONES
     */
    public void agregarPropiedad(Propiedad propiedad) throws PrecioInvalidoException, CampoVacioException {
        // MANEJO DE EXCEPCIONES: Validación de datos
        if (propiedad.getPrecio() <= 0) {
            throw new PrecioInvalidoException("El precio debe ser mayor a 0");
        }
        
        if (propiedad.getDireccion() == null || propiedad.getDireccion().trim().isEmpty()) {
            throw new CampoVacioException("Dirección");
        }
        
        propiedad.setId(contadorPropiedades++);
        propiedades.add(propiedad);
    }
    
    /**
     * Elimina una propiedad por ID
     * ESTRUCTURA REPETITIVA: for
     */
    public void eliminarPropiedad(int id) throws ElementoNoEncontradoException {
        // ESTRUCTURA REPETITIVA: for con búsqueda
        for (int i = 0; i < propiedades.size(); i++) {
            if (propiedades.get(i).getId() == id) {
                propiedades.remove(i);
                return;
            }
        }
        throw new ElementoNoEncontradoException("Propiedad con ID " + id + " no encontrada");
    }
    
    /**
     * Modifica una propiedad existente
     */
    public void modificarPropiedad(Propiedad propiedadModificada) throws ElementoNoEncontradoException, PrecioInvalidoException {
        if (propiedadModificada.getPrecio() <= 0) {
            throw new PrecioInvalidoException();
        }
        
        // ESTRUCTURA REPETITIVA: for con búsqueda
        for (int i = 0; i < propiedades.size(); i++) {
            if (propiedades.get(i).getId() == propiedadModificada.getId()) {
                propiedades.set(i, propiedadModificada);
                return;
            }
        }
        throw new ElementoNoEncontradoException("Propiedad no encontrada");
    }
    
    /**
     * Busca una propiedad por ID
     * ALGORITMO DE BÚSQUEDA
     */
    public Propiedad buscarPropiedadPorId(int id) throws ElementoNoEncontradoException {
        // ESTRUCTURA REPETITIVA: for-each
        for (Propiedad p : propiedades) {
            // ESTRUCTURA CONDICIONAL: if
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ElementoNoEncontradoException("Propiedad con ID " + id + " no encontrada");
    }
    
    /**
     * Busca propiedades por dirección (búsqueda parcial)
     * ALGORITMO DE BÚSQUEDA
     */
    public ArrayList<Propiedad> buscarPropiedadesPorDireccion(String direccion) {
        ArrayList<Propiedad> resultado = new ArrayList<>();
        
        // ESTRUCTURA REPETITIVA: for-each con búsqueda
        for (Propiedad p : propiedades) {
            // ESTRUCTURA CONDICIONAL: if con búsqueda parcial
            if (p.getDireccion().toLowerCase().contains(direccion.toLowerCase())) {
                resultado.add(p);
            }
        }
        
        return resultado;
    }
    
    /**
     * Ordena propiedades por precio
     * ALGORITMO DE ORDENAMIENTO usando Collections.sort() y Comparator
     */
    public void ordenarPropiedadesPorPrecio() {
        Collections.sort(propiedades, new Comparator<Propiedad>() {
            @Override
            public int compare(Propiedad p1, Propiedad p2) {
                return Double.compare(p1.getPrecio(), p2.getPrecio());
            }
        });
    }
    
    /**
     * Filtra propiedades por estado
     */
    public ArrayList<Propiedad> filtrarPropiedadesPorEstado(String estado) {
        ArrayList<Propiedad> resultado = new ArrayList<>();
        
        // ESTRUCTURA REPETITIVA: for-each
        for (Propiedad p : propiedades) {
            if (p.getEstado().equalsIgnoreCase(estado)) {
                resultado.add(p);
            }
        }
        
        return resultado;
    }
    
    public ArrayList<Propiedad> getPropiedades() {
        return propiedades;
    }
    
    // ==================== GESTIÓN DE CLIENTES ====================
    
    /**
     * Agrega un nuevo cliente al sistema
     */
    public void agregarCliente(Cliente cliente) throws CampoVacioException {
        // MANEJO DE EXCEPCIONES: Validación
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new CampoVacioException("Nombre");
        }
        
        if (cliente.getApellido() == null || cliente.getApellido().trim().isEmpty()) {
            throw new CampoVacioException("Apellido");
        }
        
        cliente.setId(contadorClientes++);
        clientes.add(cliente);
    }
    
    /**
     * Elimina un cliente por ID
     */
    public void eliminarCliente(int id) throws ElementoNoEncontradoException {
        // ESTRUCTURA REPETITIVA: for
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                clientes.remove(i);
                return;
            }
        }
        throw new ElementoNoEncontradoException("Cliente con ID " + id + " no encontrado");
    }
    
    /**
     * Modifica un cliente existente
     */
    public void modificarCliente(Cliente clienteModificado) throws ElementoNoEncontradoException {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == clienteModificado.getId()) {
                clientes.set(i, clienteModificado);
                return;
            }
        }
        throw new ElementoNoEncontradoException("Cliente no encontrado");
    }
    
    /**
     * Busca un cliente por ID
     */
    public Cliente buscarClientePorId(int id) throws ElementoNoEncontradoException {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new ElementoNoEncontradoException("Cliente con ID " + id + " no encontrado");
    }
    
    /**
     * Busca clientes por nombre o apellido
     */
    public ArrayList<Cliente> buscarClientesPorNombre(String nombre) {
        ArrayList<Cliente> resultado = new ArrayList<>();
        
        for (Cliente c : clientes) {
            String nombreCompleto = c.getNombre() + " " + c.getApellido();
            if (nombreCompleto.toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(c);
            }
        }
        
        return resultado;
    }
    
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    
    // ==================== GESTIÓN DE RESERVAS ====================
    
    /**
     * Agrega una nueva reserva al sistema
     */
    public void agregarReserva(Reserva reserva) throws PrecioInvalidoException {
        // MANEJO DE EXCEPCIONES: Validación
        if (reserva.getMonto() <= 0) {
            throw new PrecioInvalidoException("El monto de la reserva debe ser mayor a 0");
        }
        
        reserva.setId(contadorReservas++);
        reservas.add(reserva);
        
        // Actualizar estado de la propiedad
        if (reserva.getPropiedad() != null) {
            reserva.getPropiedad().setEstado("Reservada");
        }
    }
    
    /**
     * Elimina una reserva por ID
     */
    public void eliminarReserva(int id) throws ElementoNoEncontradoException {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId() == id) {
                Reserva r = reservas.get(i);
                // Liberar la propiedad
                if (r.getPropiedad() != null) {
                    r.getPropiedad().setEstado("Disponible");
                }
                reservas.remove(i);
                return;
            }
        }
        throw new ElementoNoEncontradoException("Reserva con ID " + id + " no encontrada");
    }
    
    /**
     * Busca una reserva por ID
     */
    public Reserva buscarReservaPorId(int id) throws ElementoNoEncontradoException {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }
        throw new ElementoNoEncontradoException("Reserva con ID " + id + " no encontrada");
    }
    
    /**
     * Cancela una reserva (cambia su estado)
     */
    public void cancelarReserva(int id) throws ElementoNoEncontradoException {
        Reserva reserva = buscarReservaPorId(id);
        reserva.setEstado("Cancelada");
        
        // Liberar la propiedad
        if (reserva.getPropiedad() != null) {
            reserva.getPropiedad().setEstado("Disponible");
        }
    }
    
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    
    // ==================== GESTIÓN DE USUARIOS ====================
    
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
    
    public Usuario validarUsuario(String email, String password) {
        // ESTRUCTURA REPETITIVA: for-each
        for (Usuario u : usuarios) {
            // ESTRUCTURA CONDICIONAL: if
            if (u.validarCredenciales(email, password)) {
                return u;
            }
        }
        return null;
    }
    
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    
    // ==================== DATOS DE PRUEBA ====================
    
    /**
     * Inicializa el sistema con datos de prueba
     * ESTRUCTURA REPETITIVA: múltiples llamadas a métodos
     */
    private void inicializarDatosPrueba() {
        try {
            // Usuarios de prueba
            usuarios.add(new Usuario("admin", "admin123", "administrador", true));
            usuarios.add(new Usuario("empleado", "empleado123", "empleado", true));
            
            // Clientes de prueba (primero los propietarios)
            Cliente propietario1 = new Cliente(0, "Juan", "Sosa", "40503142", 
                                          "3512345678", "juan.sosa@email.com", "Calle 123", "propietario");
            agregarCliente(propietario1);
            
            Cliente propietario2 = new Cliente(0, "Maria", "Perez", "30123456",
                                          "3512345679", "maria.perez@email.com", "Av. Siempre Viva 742", "propietario");
            agregarCliente(propietario2);
            
            Cliente inquilino1 = new Cliente(0, "Roberto", "Gómez", "25789456",
                                          "11-2345-6789", "roberto@email.com", "Calle Falsa 456", "inquilino");
            agregarCliente(inquilino1);
            
            // Propiedades residenciales de prueba (usando IDs de propietarios)
            PropiedadResidencial casa1 = new PropiedadResidencial(
                0, "Calle Falsa 123", "casa", 
                50000, "disponible", "Casa amplia con jardín", 1,
                3, 2, true, 120.5
            );
            agregarPropiedad(casa1);
            
            PropiedadResidencial depto1 = new PropiedadResidencial(
                0, "Av. Siempre Viva 742", "departamento",
                35000, "disponible", "Departamento luminoso", 2,
                2, 1, false, 65.0
            );
            agregarPropiedad(depto1);
            
            PropiedadResidencial casa2 = new PropiedadResidencial(
                0, "Av. Belgrano 910", "casa",
                180000, "disponible", "Casa de 2 plantas", 1,
                4, 3, true, 150.0
            );
            agregarPropiedad(casa2);
            
            // Propiedades comerciales de prueba
            PropiedadComercial local1 = new PropiedadComercial(
                0, "Av. Santa Fe 2345", "local",
                3000, "disponible", "Local en zona comercial", 2,
                80.0, "Centro", true, 50
            );
            agregarPropiedad(local1);
            
            PropiedadComercial oficina1 = new PropiedadComercial(
                0, "Av. Córdoba 6789", "local",
                120000, "disponible", "Oficina moderna", 1,
                45.0, "Microcentro", false, 15
            );
            agregarPropiedad(oficina1);
            
        } catch (Exception e) {
            System.err.println("Error al inicializar datos de prueba: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene estadísticas del sistema
     */
    public String obtenerEstadisticas() {
        int propDisponibles = 0;
        int propReservadas = 0;
        int propVendidas = 0;
        
        // ESTRUCTURA REPETITIVA: for-each con contadores
        for (Propiedad p : propiedades) {
            // ESTRUCTURA CONDICIONAL: switch
            switch (p.getEstado()) {
                case "Disponible":
                    propDisponibles++;
                    break;
                case "Reservada":
                    propReservadas++;
                    break;
                case "Vendida":
                    propVendidas++;
                    break;
            }
        }
        
        return String.format(
            "=== ESTADÍSTICAS DEL SISTEMA ===\n" +
            "Total de Propiedades: %d\n" +
            "  - Disponibles: %d\n" +
            "  - Reservadas: %d\n" +
            "  - Vendidas: %d\n" +
            "Total de Clientes: %d\n" +
            "Total de Reservas: %d",
            propiedades.size(), propDisponibles, propReservadas, propVendidas,
            clientes.size(), reservas.size()
        );
    }
}
