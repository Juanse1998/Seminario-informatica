#!/bin/bash

# Script de compilación para el Sistema de Gestión Inmobiliaria
# Este script compila todos los archivos Java del proyecto

echo "========================================="
echo "Sistema de Gestión Inmobiliaria"
echo "Compilando proyecto..."
echo "========================================="

# Navegar al directorio src
cd src

# Crear directorio para clases compiladas (opcional)
mkdir -p ../bin

# Compilar todos los archivos Java
echo "Compilando archivos Java..."
javac -d ../bin Main.java modelo/*.java controlador/*.java vista/*.java excepciones/*.java

# Verificar si la compilación fue exitosa
if [ $? -eq 0 ]; then
    echo "========================================="
    echo "✓ Compilación exitosa"
    echo "========================================="
    echo ""
    echo "Para ejecutar la aplicación, use:"
    echo "  cd bin"
    echo "  java Main"
    echo ""
    echo "O ejecute el script: ./ejecutar.sh"
else
    echo "========================================="
    echo "✗ Error en la compilación"
    echo "========================================="
    exit 1
fi
