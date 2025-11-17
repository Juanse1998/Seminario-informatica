package modelo;


public class PropiedadResidencial extends Propiedad {
    
    private int habitaciones;
    private int baños;
    private boolean garage;
    private double metrosCuadrados;
    
    
    public PropiedadResidencial() {
        super();
        this.habitaciones = 0;
        this.baños = 0;
        this.garage = false;
        this.metrosCuadrados = 0.0;
    }
    
    
    public PropiedadResidencial(int idPropiedad, String direccion, String tipo,
                               double precio, String estado, String descripcion, int idPropietario,
                               int habitaciones, int baños, boolean garage, double metrosCuadrados) {
        super(idPropiedad, direccion, tipo, precio, estado, descripcion, idPropietario);
        this.habitaciones = habitaciones;
        this.baños = baños;
        this.garage = garage;
        this.metrosCuadrados = metrosCuadrados;
    }
    
    
    public PropiedadResidencial(String direccion, String tipo, double precio, String estado,
                               String descripcion, int idPropietario,
                               int habitaciones, int baños, boolean garage, double metrosCuadrados) {
        super(direccion, tipo, precio, estado, descripcion, idPropietario);
        this.habitaciones = habitaciones;
        this.baños = baños;
        this.garage = garage;
        this.metrosCuadrados = metrosCuadrados;
    }
    
    
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
