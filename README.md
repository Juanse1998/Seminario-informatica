# 🏢 Sistema de Gestión Inmobiliaria (SGI)

## 📋 Descripción del Proyecto

Sistema completo de gestión inmobiliaria desarrollado en Java con interfaz gráfica Swing. Permite administrar propiedades, clientes, contratos, pagos y recibos de manera eficiente, demostrando todos los conceptos fundamentales de Programación Orientada a Objetos.

### ✨ Características Principales
- 🏠 Gestión completa de propiedades (residenciales y comerciales)
- 👥 Administración de clientes (propietarios e inquilinos)
- 📝 Sistema de contratos de alquiler y venta
- 💰 Registro de pagos mensuales
- 🧾 Generación e impresión de recibos
- 🔐 Sistema de autenticación de usuarios
- 📊 Reportes y estadísticas

- **Gestión de Propiedades**: Alta, baja, modificación y listado de propiedades residenciales y comerciales
- **Gestión de Clientes**: Administración de clientes propietarios e interesados
- **Gestión de Reservas**: Registro y seguimiento de reservas y contratos
- **Sistema de Login**: Autenticación de usuarios con roles
- **Búsqueda y Filtrado**: Búsqueda por dirección, nombre y otros criterios
- **Ordenamiento**: Ordenamiento de propiedades por precio
- **Estadísticas**: Reportes del estado del sistema

## 🛠️ Tecnologías

- **Java**: JDK 17 o superior
- **Swing**: Para la interfaz gráfica
- **Java Collections**: ArrayList para manejo de datos

## 📁 Estructura del Proyecto

```
inmobiliaria/
├── src/
│   ├── Main.java                           # Clase principal
│   ├── controlador/
│   │   └── Inmobiliaria.java              # Controlador principal
│   ├── modelo/
│   │   ├── Propiedad.java                 # Clase abstracta base
│   │   ├── PropiedadResidencial.java      # Herencia de Propiedad
│   │   ├── PropiedadComercial.java        # Herencia de Propiedad
│   │   ├── Cliente.java                   # Modelo de cliente
│   │   ├── Reserva.java                   # Modelo de reserva
│   │   └── Usuario.java                   # Modelo de usuario
│   ├── vista/
│   │   ├── VentanaLogin.java              # Ventana de autenticación
│   │   ├── VentanaPrincipal.java          # Ventana principal con menú
│   │   ├── PanelPropiedades.java          # Panel de gestión de propiedades
│   │   ├── PanelClientes.java             # Panel de gestión de clientes
│   │   └── PanelReservas.java             # Panel de gestión de reservas
│   └── excepciones/
│       ├── PrecioInvalidoException.java   # Excepción personalizada
│       ├── CampoVacioException.java       # Excepción personalizada
│       └── ElementoNoEncontradoException.java # Excepción personalizada
└── README.md
```

## 🚀 Inicio Rápido

### Compilación
```bash
cd inmobiliaria/src
javac Main.java modelo/*.java controlador/*.java vista/*.java excepciones/*.java
```

### Ejecución
```bash
java Main
```

### Credenciales de Prueba
- **Usuario**: admin | **Contraseña**: admin123 (Administrador)
- **Usuario**: empleado | **Contraseña**: empleado123 (Empleado)

## 👤 Usuarios de Prueba

El sistema incluye usuarios predefinidos para pruebas:

| Email | Contraseña | Rol |
|-------|-----------|-----|
| admin@inmobiliaria.com | admin123 | Administrador |
| juan@inmobiliaria.com | juan123 | Agente |

## 📊 Datos de Prueba

El sistema se inicializa con datos de ejemplo:
- 5 propiedades (3 residenciales, 2 comerciales)
- 3 clientes
- 2 usuarios

## 🎓 Conceptos de POO Implementados

### 1. Encapsulamiento
- Todos los atributos de las clases son privados
- Acceso mediante getters y setters
- **Ejemplo**: Clase `Propiedad` con atributos privados y métodos públicos

### 2. Herencia
- Clase abstracta `Propiedad` como clase base
- `PropiedadResidencial` y `PropiedadComercial` heredan de `Propiedad`
- **Ubicación**: `modelo/Propiedad.java`, `modelo/PropiedadResidencial.java`, `modelo/PropiedadComercial.java`

### 3. Polimorfismo
- Método abstracto `mostrarInfo()` en `Propiedad`
- Cada subclase implementa el método de forma diferente
- Uso de `instanceof` para determinar tipo en tiempo de ejecución
- **Ubicación**: `modelo/Propiedad.java` (línea 85), implementaciones en subclases

### 4. Abstracción
- Clase abstracta `Propiedad` define comportamiento común
- Métodos abstractos que deben ser implementados por subclases
- **Ubicación**: `modelo/Propiedad.java`

## 🔄 Estructuras de Control

### Estructuras Repetitivas
- **for**: Recorrido de colecciones con índice
  - `controlador/Inmobiliaria.java` (líneas 60-65, 80-85)
- **for-each**: Recorrido simplificado de colecciones
  - `controlador/Inmobiliaria.java` (líneas 100-105)
- **while**: Algoritmos de búsqueda y validación

### Estructuras Condicionales
- **if-else**: Validaciones y decisiones
  - `controlador/Inmobiliaria.java` (líneas 50-55)
- **switch**: Evaluación de estados
  - `controlador/Inmobiliaria.java` (líneas 350-360)

## ⚠️ Manejo de Excepciones

### Excepciones Personalizadas
1. **PrecioInvalidoException**: Valida que los precios sean mayores a 0
2. **CampoVacioException**: Valida que los campos obligatorios no estén vacíos
3. **ElementoNoEncontradoException**: Indica que un elemento buscado no existe

### Bloques try-catch
- Validación de entradas del usuario
- Manejo de errores de formato (`NumberFormatException`)
- Captura de excepciones personalizadas
- **Ubicación**: `vista/PanelPropiedades.java` (líneas 150-180)

## 🔍 Algoritmos Implementados

### Búsqueda
- **Búsqueda por ID**: Búsqueda lineal en ArrayList
  - `controlador/Inmobiliaria.java` método `buscarPropiedadPorId()`
- **Búsqueda parcial**: Búsqueda por dirección usando `contains()`
  - `controlador/Inmobiliaria.java` método `buscarPropiedadesPorDireccion()`

### Ordenamiento
- **Collections.sort()** con `Comparator`
- Ordenamiento de propiedades por precio
- **Ubicación**: `controlador/Inmobiliaria.java` método `ordenarPropiedadesPorPrecio()`

## 🏗️ Constructores

Todas las clases del modelo implementan:
- Constructor por defecto (sin parámetros)
- Constructor con parámetros
- **Ejemplo**: `modelo/Propiedad.java` (líneas 20-30, 32-42)

## 🎯 Funcionalidades Principales

### 1. Gestión de Propiedades
- Alta, baja y modificación de propiedades
- Propiedades residenciales (casas, departamentos)
- Propiedades comerciales (locales, oficinas)
- Búsqueda por dirección, tipo y estado
- Ordenamiento por precio
- Estados: Disponible, Alquilada, Vendida, Reservada

### 2. Gestión de Clientes
- Registro completo de clientes con DNI y dirección
- Tipos: Propietario, Inquilino, Ambos
- Búsqueda por nombre y DNI
- Datos de contacto completos

### 3. Gestión de Contratos
- Creación de contratos de alquiler y venta
- Vinculación propiedad-inquilino-propietario
- Gestión de fechas de inicio y fin
- Montos mensuales
- Actualización automática de estados de propiedades

### 4. Gestión de Pagos
- Registro de pagos mensuales por contrato
- Múltiples métodos de pago (efectivo, transferencia, tarjeta)
- Observaciones y notas
- Historial completo de pagos

### 5. Sistema de Recibos
- Generación automática de recibos
- Formatos: Digital e Impreso
- Vista previa antes de imprimir
- Diseño profesional con todos los datos
- Impresión directa desde el sistema


## 📈 Estadísticas

El sistema proporciona estadísticas en tiempo real:
- Total de propiedades por estado (Disponible, Reservada, Vendida)
- Total de clientes
- Total de reservas

## 🎨 Interfaz Gráfica

### Componentes Swing Utilizados
- **JFrame**: Ventanas principales
- **JPanel**: Contenedores de componentes
- **JTable**: Visualización de datos en tabla
- **JTextField**: Campos de texto
- **JButton**: Botones de acción
- **JComboBox**: Listas desplegables
- **JCheckBox**: Casillas de verificación
- **JMenuBar**: Barra de menú
- **JTabbedPane**: Pestañas de navegación
- **JDialog**: Diálogos modales
- **JOptionPane**: Mensajes y confirmaciones

## 🔐 Seguridad

- Sistema de autenticación con usuario y contraseña
- Validación de campos obligatorios
- Validación de tipos de datos
- Manejo de excepciones para entradas inválidas

## 📝 Notas Adicionales

- El sistema utiliza `ArrayList` para almacenar datos en memoria
- Los datos se pierden al cerrar la aplicación (no hay persistencia en archivos)
- La interfaz es responsiva y se adapta al tamaño de la ventana
- Todos los formularios incluyen validación de datos

## 🚀 Mejoras Futuras (Opcionales)

- Persistencia de datos en archivos o base de datos
- Generación de reportes en PDF
- Búsqueda avanzada con múltiples criterios
- Historial de transacciones
- Carga de imágenes de propiedades
- Exportación de datos a Excel

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos para demostrar conceptos de Programación Orientada a Objetos en Java.

---

**Desarrollado con Java y Swing** ☕
