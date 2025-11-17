import dao.ConexionDB;
import dao.UsuarioDAO;
import modelo.Usuario;


public class TestConexion {
    public static void main(String[] args) {
        System.out.println("🔍 PROBANDO CONEXIÓN A MYSQL");
        System.out.println("============================");
        
        try {
            
            System.out.println("1. Probando conexión básica...");
            ConexionDB.getConexion();
            System.out.println("✅ Conexión a MySQL exitosa!");
            
            
            System.out.println("\n2. Probando autenticación...");
            Usuario admin = UsuarioDAO.autenticar("admin", "admin123");
            if (admin != null) {
                System.out.println("✅ Usuario 'admin' encontrado: " + admin.getNombreUsuario());
                System.out.println("   Rol: " + admin.getRol());
            } else {
                System.out.println("❌ Usuario 'admin' no encontrado");
            }
            
            Usuario empleado = UsuarioDAO.autenticar("empleado", "empleado123");
            if (empleado != null) {
                System.out.println("✅ Usuario 'empleado' encontrado: " + empleado.getNombreUsuario());
                System.out.println("   Rol: " + empleado.getRol());
            } else {
                System.out.println("❌ Usuario 'empleado' no encontrado");
            }
            
            
            System.out.println("\n3. Usuarios en la base de datos:");
            var usuarios = UsuarioDAO.obtenerTodos();
            if (usuarios.isEmpty()) {
                System.out.println("❌ No hay usuarios en la base de datos");
                System.out.println("💡 Ejecuta el script crear_base_datos.sql");
            } else {
                for (Usuario u : usuarios) {
                    System.out.println("   - " + u.getNombreUsuario() + " (" + u.getRol() + ")");
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            System.out.println("\n🔧 POSIBLES SOLUCIONES:");
            System.out.println("1. Configurar contraseña en dao/ConexionDB.java línea 13");
            System.out.println("2. Verificar que MySQL esté ejecutándose");
            System.out.println("3. Verificar que la base de datos 'inmobiliaria_db' exista");
            System.out.println("4. Descargar MySQL Connector/J");
            e.printStackTrace();
        } finally {
            ConexionDB.cerrarConexion();
        }
    }
}
