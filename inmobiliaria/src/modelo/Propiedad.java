package modelo;


public abstract class Propiedad {
    
    private int idPropiedad;
    private String direccion;
    private String tipo; 
    private double precio;
    private String estado; 
    private String descripcion;
    private int idPropietario; 
    
    
    public Propiedad() {
        this.idPropiedad = 0;
        this.direccion = "";
        this.tipo = "casa";
        this.precio = 0.0;
        this.estado = "disponible";
        this.descripcion = "";
        this.idPropietario = 0;
    }
    
    
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
    
    
    public Propiedad(String direccion, String tipo, double precio, 
                     String estado, String descripcion, int idPropietario) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
        this.idPropietario = idPropietario;
    }
    
    
    public int getIdPropiedad() {
        return idPropiedad;
    }
    
    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }
    
    
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
    
    
    public String getOperacion() {
        
        if (estado.equals("alquilada")) return "Alquiler";
        if (estado.equals("vendida")) return "Venta";
        return "Venta"; 
    }
    
    public void setOperacion(String operacion) {
        
    }
    
    public String getAgente() {
        return ""; 
    }
    
    public void setAgente(String agente) {
        
    }
    
    
    public abstract String mostrarInfo();
    
    
    public String getInfoBasica() {
        return String.format("ID: %d | %s | %s | $%.2f | %s", 
                           idPropiedad, direccion, tipo, precio, estado);
    }
    
    @Override
    public String toString() {
        return getInfoBasica();
    }
}
