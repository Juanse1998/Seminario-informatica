#!/bin/bash

# Script para compilar y ejecutar el proyecto con MySQL
# Ejecutar desde la carpeta del proyecto: bash ejecutar.sh

echo "🏠 Sistema de Gestión Inmobiliaria con MySQL"
echo "============================================="

# Verificar si existe MySQL Connector
if [ ! -f "lib/mysql-connector-j.jar" ]; then
    echo "❌ MySQL Connector/J no encontrado"
    echo "🔧 Ejecutando descarga automática..."
    bash descargar_mysql_connector.sh
    
    if [ ! -f "lib/mysql-connector-j.jar" ]; then
        echo "❌ Error: No se pudo descargar MySQL Connector/J"
        echo "💡 Descarga manual desde: https://dev.mysql.com/downloads/connector/j/"
        echo "   Y copia el JAR a lib/mysql-connector-j.jar"
        exit 1
    fi
fi

echo "✅ MySQL Connector/J encontrado"

# Verificar conexión a MySQL
echo "🔍 Verificando conexión a MySQL..."
mysql -u root -p -e "SELECT VERSION();" 2>/dev/null
if [ $? -ne 0 ]; then
    echo "⚠️  No se pudo conectar a MySQL automáticamente"
    echo "💡 Asegúrate de que MySQL esté ejecutándose y que tengas las credenciales correctas"
    echo "   Puedes continuar si ya configuraste la base de datos"
fi

# Verificar si existe la base de datos (opcional)
echo "🗄️  Verificando base de datos inmobiliaria_db..."
echo "💡 Si ya tienes la base de datos creada, puedes omitir esta verificación"
echo "🔧 ¿Quieres verificar/crear la base de datos? (s/n) [n por defecto]"
read -r respuesta
if [ "$respuesta" = "s" ] || [ "$respuesta" = "S" ]; then
    DB_EXISTS=$(mysql -u root -p -e "SHOW DATABASES LIKE 'inmobiliaria_db';" 2>/dev/null | grep inmobiliaria_db)
    if [ -z "$DB_EXISTS" ]; then
        echo "⚠️  Base de datos inmobiliaria_db no encontrada"
        echo "📊 Ejecutando script de creación..."
        mysql -u root -p < crear_base_datos.sql
        if [ $? -eq 0 ]; then
            echo "✅ Base de datos creada exitosamente"
        else
            echo "❌ Error al crear la base de datos"
            exit 1
        fi
    else
        echo "✅ Base de datos inmobiliaria_db encontrada"
    fi
else
    echo "⏭️  Omitiendo verificación de base de datos (asumiendo que ya existe)"
fi

# Compilar el proyecto
echo "🔨 Compilando proyecto..."
cd src

# Limpiar archivos .class anteriores
find . -name "*.class" -delete

# Compilar con MySQL Connector en el classpath
javac -cp "../lib/mysql-connector-j.jar:." \
    Main.java \
    modelo/*.java \
    controlador/*.java \
    vista/*.java \
    dao/*.java \
    excepciones/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa"
    
    echo "🚀 Ejecutando aplicación..."
    echo "   (Asegúrate de haber configurado la contraseña en dao/ConexionDB.java)"
    echo ""
    
    # Ejecutar la aplicación
    java -cp "../lib/mysql-connector-j.jar:." Main
    
else
    echo "❌ Error en la compilación"
    echo "💡 Verifica que todos los archivos estén presentes y sin errores de sintaxis"
    exit 1
fi

cd ..
echo ""
echo "🎯 Ejecución completada"
