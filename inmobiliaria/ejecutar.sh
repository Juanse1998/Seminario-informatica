#!/bin/bash

# Script de ejecución para el Sistema de Gestión Inmobiliaria

echo "========================================="
echo "Sistema de Gestión Inmobiliaria"
echo "Iniciando aplicación..."
echo "========================================="

# Verificar si existe el directorio bin
if [ -d "bin" ]; then
    cd bin
    java Main
else
    echo "Error: No se encontró el directorio 'bin'"
    echo "Por favor, compile primero el proyecto ejecutando: ./compilar.sh"
    exit 1
fi
