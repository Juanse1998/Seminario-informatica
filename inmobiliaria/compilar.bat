@echo off
REM Script de compilación para Windows
REM Sistema de Gestión Inmobiliaria

echo =========================================
echo Sistema de Gestión Inmobiliaria
echo Compilando proyecto...
echo =========================================

cd src

echo Compilando archivos Java...
javac Main.java modelo\*.java controlador\*.java vista\*.java excepciones\*.java

if %ERRORLEVEL% EQU 0 (
    echo =========================================
    echo Compilación exitosa
    echo =========================================
    echo.
    echo Para ejecutar la aplicación, use:
    echo   java Main
    echo.
    echo O ejecute el script: ejecutar.bat
) else (
    echo =========================================
    echo Error en la compilación
    echo =========================================
    pause
    exit /b 1
)

cd ..
pause
