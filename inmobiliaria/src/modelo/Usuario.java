package modelo;


public class Usuario {
    
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String rol; 
    private boolean activo;
    
    
    public Usuario() {
        this.idUsuario = 0;
        this.nombreUsuario = "";
        this.contrasena = "";
        this.rol = "empleado";
        this.activo = true;
    }
    
    
    public Usuario(int idUsuario, String nombreUsuario, String contrasena, String rol, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = activo;
    }
    
    
    public Usuario(String nombreUsuario, String contrasena, String rol, boolean activo) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = activo;
    }
    
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    public String getNombre() {
        return nombreUsuario;
    }
    
    public void setNombre(String nombre) {
        this.nombreUsuario = nombre;
    }
    
    
    public boolean validarCredenciales(String nombreUsuario, String contrasena) {
        return this.nombreUsuario.equals(nombreUsuario) && this.contrasena.equals(contrasena) && this.activo;
    }
    
    @Override
    public String toString() {
        return String.format("Usuario: %s | Rol: %s | Activo: %s", nombreUsuario, rol, (activo ? "Sí" : "No"));
    }
}
