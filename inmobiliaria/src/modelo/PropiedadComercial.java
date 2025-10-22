package modelo;

/**
 * Clase PropiedadComercial - Demuestra HERENCIA
 * Extiende de Propiedad y agrega atributos específicos para propiedades comerciales
 */
public class PropiedadComercial extends Propiedad {
    // Atributos específicos de propiedades comerciales
    private double metrosCuadrados;
    private String zonaComercial;
    private boolean apto_gastronomia;
    private int capacidadPersonas;
    
    // Constructor por defecto
    public PropiedadComercial() {
        super();
        this.metrosCuadrados = 0.0;
        this.zonaComercial = "";
        this.apto_gastronomia = false;
        this.capacidadPersonas = 0;
    }
    
    // Constructor con parámetros completo (con ID)
    public PropiedadComercial(int idPropiedad, String direccion, String tipo,
                             double precio, String estado, String descripcion, int idPropietario,
                             double metrosCuadrados, String zonaComercial,
                             boolean apto_gastronomia, int capacidadPersonas) {
        super(idPropiedad, direccion, tipo, precio, estado, descripcion, idPropietario);
        this.metrosCuadrados = metrosCuadrados;
        this.zonaComercial = zonaComercial;
        this.apto_gastronomia = apto_gastronomia;
        this.capacidadPersonas = capacidadPersonas;
    }
    
    // Constructor sin ID (para inserción en BD)
    public PropiedadComercial(String direccion, String tipo, double precio, String estado,
                             String descripcion, int idPropietario,
                             double metrosCuadrados, String zonaComercial,
                             boolean apto_gastronomia, int capacidadPersonas) {
        super(direccion, tipo, precio, estado, descripcion, idPropietario);
        this.metrosCuadrados = metrosCuadrados;
        this.zonaComercial = zonaComercial;
        this.apto_gastronomia = apto_gastronomia;
        this.capacidadPersonas = capacidadPersonas;
    }
    
    // Getters y Setters
    public double getMetrosCuadrados() {
        return metrosCuadrados;
    }
    
    public void setMetrosCuadrados(double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }
    
    public String getZonaComercial() {
        return zonaComercial;
    }
    
    public void setZonaComercial(String zonaComercial) {
        this.zonaComercial = zonaComercial;
    }
    
    public boolean isApto_gastronomia() {
        return apto_gastronomia;
    }
    
    public void setApto_gastronomia(boolean apto_gastronomia) {
        this.apto_gastronomia = apto_gastronomia;
    }
    
    public int getCapacidadPersonas() {
        return capacidadPersonas;
    }
    
    public void setCapacidadPersonas(int capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
    }
    
    /**
     * POLIMORFISMO: Sobrescribe el método abstracto de la clase padre
     * Proporciona información específica de propiedades comerciales
     */
    @Override
    public String mostrarInfo() {
        return String.format(
            "=== PROPIEDAD COMERCIAL ===\n" +
            "ID: %d\n" +
            "Dirección: %s\n" +
            "Tipo: %s\n" +
            "Operación: %s\n" +
            "Precio: $%.2f\n" +
            "Estado: %s\n" +
            "Agente: %s\n" +
            "Metros cuadrados: %.2f m²\n" +
            "Zona comercial: %s\n" +
            "Apto gastronomía: %s\n" +
            "Capacidad: %d personas",
            getId(), getDireccion(), getTipo(), getOperacion(), getPrecio(),
            getEstado(), getAgente(), metrosCuadrados, zonaComercial,
            (apto_gastronomia ? "Sí" : "No"), capacidadPersonas
        );
    }
}
