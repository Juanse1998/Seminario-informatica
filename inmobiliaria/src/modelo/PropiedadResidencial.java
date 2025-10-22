package modelo;

/**
 * Clase PropiedadResidencial - Demuestra HERENCIA
 * Extiende de Propiedad y agrega atributos específicos para propiedades residenciales
 */
public class PropiedadResidencial extends Propiedad {
    // Atributos específicos de propiedades residenciales
    private int habitaciones;
    private int baños;
    private boolean garage;
    private double metrosCuadrados;
    
    // Constructor por defecto
    public PropiedadResidencial() {
        super();
        this.habitaciones = 0;
        this.baños = 0;
        this.garage = false;
        this.metrosCuadrados = 0.0;
    }
    
    // Constructor con parámetros completo (con ID)
    public PropiedadResidencial(int idPropiedad, String direccion, String tipo,
                               double precio, String estado, String descripcion, int idPropietario,
                               int habitaciones, int baños, boolean garage, double metrosCuadrados) {
        super(idPropiedad, direccion, tipo, precio, estado, descripcion, idPropietario);
        this.habitaciones = habitaciones;
        this.baños = baños;
        this.garage = garage;
        this.metrosCuadrados = metrosCuadrados;
    }
    
    // Constructor sin ID (para inserción en BD)
    public PropiedadResidencial(String direccion, String tipo, double precio, String estado,
                               String descripcion, int idPropietario,
                               int habitaciones, int baños, boolean garage, double metrosCuadrados) {
        super(direccion, tipo, precio, estado, descripcion, idPropietario);
        this.habitaciones = habitaciones;
        this.baños = baños;
        this.garage = garage;
        this.metrosCuadrados = metrosCuadrados;
    }
    
    // Getters y Setters
    public int getHabitaciones() {
        return habitaciones;
    }
    
    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }
    
    public int getBaños() {
        return baños;
    }
    
    public void setBaños(int baños) {
        this.baños = baños;
    }
    
    public boolean isGarage() {
        return garage;
    }
    
    public void setGarage(boolean garage) {
        this.garage = garage;
    }
    
    public double getMetrosCuadrados() {
        return metrosCuadrados;
    }
    
    public void setMetrosCuadrados(double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }
    
    /**
     * POLIMORFISMO: Sobrescribe el método abstracto de la clase padre
     * Proporciona información específica de propiedades residenciales
     */
    @Override
    public String mostrarInfo() {
        return String.format(
            "=== PROPIEDAD RESIDENCIAL ===\n" +
            "ID: %d\n" +
            "Dirección: %s\n" +
            "Tipo: %s\n" +
            "Operación: %s\n" +
            "Precio: $%.2f\n" +
            "Estado: %s\n" +
            "Agente: %s\n" +
            "Habitaciones: %d\n" +
            "Baños: %d\n" +
            "Garage: %s\n" +
            "Metros cuadrados: %.2f m²",
            getId(), getDireccion(), getTipo(), getOperacion(), getPrecio(),
            getEstado(), getAgente(), habitaciones, baños,
            (garage ? "Sí" : "No"), metrosCuadrados
        );
    }
}
