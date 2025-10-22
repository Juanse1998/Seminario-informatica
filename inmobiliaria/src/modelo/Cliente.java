package modelo;

/**
 * Clase Cliente - Representa un cliente de la inmobiliaria
 * Mapea a la tabla Cliente de la base de datos
 * Puede ser propietario, inquilino o ambos
 */
public class Cliente {
    // ENCAPSULAMIENTO: atributos privados
    private int idCliente;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String direccion;
    private String tipo; // "propietario", "inquilino", "ambos"
    
    // Constructor por defecto
    public Cliente() {
        this.idCliente = 0;
        this.nombre = "";
        this.apellido = "";
        this.dni = "";
        this.telefono = "";
        this.email = "";
        this.direccion = "";
        this.tipo = "inquilino";
    }
    
    // Constructor con parámetros completo
    public Cliente(int idCliente, String nombre, String apellido, String dni,
                   String telefono, String email, String direccion, String tipo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.tipo = tipo;
    }
    
    // Constructor sin ID (para inserción en BD)
    public Cliente(String nombre, String apellido, String dni,
                   String telefono, String email, String direccion, String tipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.tipo = tipo;
    }
    
    // Getters y Setters - ENCAPSULAMIENTO
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    // Mantener compatibilidad con código existente
    public int getId() {
        return idCliente;
    }
    
    public void setId(int id) {
        this.idCliente = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    // Mantener compatibilidad con código existente
    public String getTipoCliente() {
        return tipo;
    }
    
    public void setTipoCliente(String tipoCliente) {
        this.tipo = tipoCliente;
    }
    
    // Método para obtener el nombre completo
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    // Método para mostrar información del cliente
    public String mostrarInfo() {
        return String.format(
            "=== CLIENTE ===\n" +
            "ID: %d\n" +
            "Nombre: %s %s\n" +
            "DNI: %s\n" +
            "Teléfono: %s\n" +
            "Email: %s\n" +
            "Dirección: %s\n" +
            "Tipo: %s",
            idCliente, nombre, apellido, dni, telefono, email, direccion, tipo
        );
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | %s %s | DNI: %s | %s | %s", 
                           idCliente, nombre, apellido, dni, telefono, tipo);
    }
}
