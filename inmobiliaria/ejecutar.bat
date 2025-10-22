@echo off
REM Script de ejecución para Windows
REM Sistema de Gestión Inmobiliaria

echo =========================================
echo Sistema de Gestión Inmobiliaria
echo Iniciando aplicación...
echo =========================================

cd src
java Main

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Error al ejecutar la aplicación
    echo Por favor, compile primero el proyecto ejecutando: compilar.bat
    pause
)
