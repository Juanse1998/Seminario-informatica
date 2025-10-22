package modelo;

/**
 * Clase base abstracta Propiedad - Demuestra ABSTRACCIÓN
 * Representa una propiedad genérica en el sistema inmobiliario
 * Mapea a la tabla Propiedad de la base de datos
 */
public abstract class Propiedad {
    // ENCAPSULAMIENTO: atributos privados
    private int idPropiedad;
    private String direccion;
    private String tipo; // 'casa', 'departamento', 'local', 'terreno'
    private double precio;
    private String estado; // 'disponible', 'vendida', 'alquilada', 'reservada'
    private String descripcion;
    private int idPropietario; // FK a Cliente
    
    // Constructor por defecto
    public Propiedad() {
        this.idPropiedad = 0;
        this.direccion = "";
        this.tipo = "casa";
        this.precio = 0.0;
        this.estado = "disponible";
        this.descripcion = "";
        this.idPropietario = 0;
    }
    
    // Constructor con parámetros completo
    public Propiedad(int idPropiedad, String direccion, String tipo, 
                     double precio, String estado, String descripcion, int idPropietario) {
        this.idPropiedad = idPropiedad;
        this.direccion = direccion;
        this.tipo = tipo;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
        this.idPropietario = idPropietario;
    }
    
    // Constructor sin ID (para inserción en BD)
    public Propiedad(String direccion, String tipo, double precio, 
                     String estado, String descripcion, int idPropietario) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
        this.idPropietario = idPropietario;
    }
    
    // Getters y Setters - ENCAPSULAMIENTO
    public int getIdPropiedad() {
        return idPropiedad;
    }
    
    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }
    
    // Mantener compatibilidad con código existente
    public int getId() {
        return idPropiedad;
    }
    
    public void setId(int id) {
        this.idPropiedad = id;
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
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getIdPropietario() {
        return idPropietario;
    }
    
    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }
    
    // Métodos de compatibilidad para mantener código existente funcionando
    public String getOperacion() {
        // Inferir operación del estado
        if (estado.equals("alquilada")) return "Alquiler";
        if (estado.equals("vendida")) return "Venta";
        return "Venta"; // Por defecto
    }
    
    public void setOperacion(String operacion) {
        // No hacer nada, la operación se maneja con el tipo de contrato
    }
    
    public String getAgente() {
        return ""; // Ya no se usa, se maneja con id_usuario en Contrato
    }
    
    public void setAgente(String agente) {
        // No hacer nada
    }
    
    // Método abstracto - POLIMORFISMO: cada subclase lo implementará de forma diferente
    public abstract String mostrarInfo();
    
    // Método para obtener información básica
    public String getInfoBasica() {
        return String.format("ID: %d | %s | %s | $%.2f | %s", 
                           idPropiedad, direccion, tipo, precio, estado);
    }
    
    @Override
    public String toString() {
        return getInfoBasica();
    }
}
