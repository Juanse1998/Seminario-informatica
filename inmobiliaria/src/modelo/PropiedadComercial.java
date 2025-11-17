package modelo;


public class PropiedadComercial extends Propiedad {
    
    private double metrosCuadrados;
    private String zonaComercial;
    private boolean apto_gastronomia;
    private int capacidadPersonas;
    
    
    public PropiedadComercial() {
        super();
        this.metrosCuadrados = 0.0;
        this.zonaComercial = "";
        this.apto_gastronomia = false;
        this.capacidadPersonas = 0;
    }
    
    
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
