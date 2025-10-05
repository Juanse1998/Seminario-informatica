DROP DATABASE IF EXISTS inmobiliaria_db;

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS inmobiliaria_db;
USE inmobiliaria_db;

-- ======================================
-- Tabla: Usuario
-- Contiene los datos de acceso al sistema
-- ======================================
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('administrativo', 'empleado', 'administrador') NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- ======================================
-- Tabla: Cliente
-- Representa tanto a propietarios como a inquilinos
-- ======================================
CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    telefono VARCHAR(30),
    email VARCHAR(100),
    direccion VARCHAR(150),
    tipo ENUM('propietario', 'inquilino', 'ambos') DEFAULT 'inquilino'
);

-- ======================================
-- Tabla: Propiedad
-- ======================================
CREATE TABLE Propiedad (
    id_propiedad INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(150) NOT NULL,
    tipo ENUM('casa', 'departamento', 'local', 'terreno') NOT NULL,
    precio DECIMAL(12,2) NOT NULL,
    estado ENUM('disponible', 'vendida', 'alquilada', 'reservada') DEFAULT 'disponible',
    descripcion TEXT,
    id_propietario INT NOT NULL,
    FOREIGN KEY (id_propietario) REFERENCES Cliente(id_cliente)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ======================================
-- Tabla: Contrato
-- ======================================
CREATE TABLE Contrato (
    id_contrato INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('alquiler', 'venta') NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    monto DECIMAL(12,2) NOT NULL,
    id_propiedad INT NOT NULL,
    id_inquilino INT,
    id_propietario INT NOT NULL,
    id_usuario INT NOT NULL, -- quien generó el contrato
    FOREIGN KEY (id_propiedad) REFERENCES Propiedad(id_propiedad)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (id_inquilino) REFERENCES Cliente(id_cliente)
        ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (id_propietario) REFERENCES Cliente(id_cliente)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ======================================
-- Tabla: Pago
-- ======================================
CREATE TABLE Pago (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_contrato INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    metodo_pago ENUM('efectivo', 'transferencia', 'tarjeta') NOT NULL,
    observaciones TEXT,
    FOREIGN KEY (id_contrato) REFERENCES Contrato(id_contrato)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ======================================
-- Tabla: Reporte (opcional, para históricos o análisis)
-- ======================================
CREATE TABLE Reporte (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    fecha_generado DATETIME DEFAULT CURRENT_TIMESTAMP,
    tipo ENUM('propiedades', 'contratos', 'pagos', 'clientes') NOT NULL,
    generado_por INT,
    FOREIGN KEY (generado_por) REFERENCES Usuarios(id_usuario)
        ON UPDATE CASCADE ON DELETE SET NULL
);

-- ======================================
-- Tabla: Recibo
-- ======================================
CREATE TABLE Recibo (
    id_recibo INT AUTO_INCREMENT PRIMARY KEY,
    id_pago INT NOT NULL,
    fecha_emision DATE NOT NULL,
    formato ENUM('Digital', 'Impreso') NOT NULL,
    FOREIGN KEY (id_pago) REFERENCES Pago(id_pago)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- ======================================
-- Vistas sugeridas
-- ======================================

-- Vista para mostrar propiedades con su propietario
CREATE VIEW vista_propiedades AS
SELECT 
    p.id_propiedad,
    p.direccion,
    p.tipo,
    p.precio,
    p.estado,
    CONCAT(c.nombre, ' ', c.apellido) AS propietario
FROM Propiedad p
JOIN Cliente c ON p.id_propietario = c.id_cliente;

-- Vista para mostrar pagos con datos del contrato e inquilino
CREATE VIEW vista_pagos AS
SELECT 
    pa.id_pago,
    pa.fecha_pago,
    pa.monto,
    pa.metodo_pago,
    co.tipo AS tipo_contrato,
    CONCAT(ci.nombre, ' ', ci.apellido) AS inquilino,
    pr.direccion AS propiedad
FROM Pago pa
JOIN Contrato co ON pa.id_contrato = co.id_contrato
LEFT JOIN Cliente ci ON co.id_inquilino = ci.id_cliente
LEFT JOIN Propiedad pr ON co.id_propiedad = pr.id_propiedad;



INSERT INTO Usuarios (nombre_usuario, contrasena, rol, activo)
VALUES
('admin', 'admin123', 'administrador', TRUE),
('empleado', 'empleado123', 'empleado', TRUE);


INSERT INTO Cliente (nombre, apellido, dni, telefono, email, direccion, tipo)
VALUES ('Juan', 'Sosa', '40503142', '3512345678', 'juan.sosa@email.com', 'Calle 123', 'propietario'),
       ('Maria', 'Perez', '30123456', '3512345679', 'maria.perez@email.com', 'Av. Siempre Viva 742', 'propietario');

-- Propiedades
INSERT INTO Propiedad (tipo, direccion, precio, estado, id_propietario)
VALUES ('Casa', 'Calle Falsa 123', 50000, 'disponible', 1),
       ('Departamento', 'Av. Siempre Viva 742', 35000, 'disponible', 2);

-- Contratos corregidos
INSERT INTO Contrato (tipo, id_propiedad, id_inquilino, id_propietario, id_usuario, fecha_inicio, fecha_fin, monto)
VALUES
('Alquiler', 1, 1, 1, 2, '2025-10-01', '2026-09-30', 1500);
-- Pagos
INSERT INTO Pago (id_contrato, fecha_pago, monto)
VALUES
(1, '2025-10-05', 1500);

-- Recibos
INSERT INTO Recibo (id_pago, fecha_emision, formato)
VALUES
(1, '2025-10-05', 'Digital');



-- Reemplaza el número 1 por el id de la propiedad que quieras ver
SELECT 
    p.id_propiedad,
    p.direccion,
    p.tipo,
    p.precio,
    p.estado,
    p.descripcion,
    CONCAT(c.nombre, ' ', c.apellido) AS propietario
FROM Propiedad p
JOIN Cliente c ON p.id_propietario = c.id_cliente
WHERE p.id_propiedad = 1;


-- Reemplaza 1 por el id del contrato que quieras ver
SELECT 
    co.id_contrato,
    co.tipo AS tipo_contrato,
    co.fecha_inicio,
    co.fecha_fin,
    co.monto,
    CONCAT(prop.nombre, ' ', prop.apellido) AS propietario,
    CONCAT(inq.nombre, ' ', inq.apellido) AS inquilino,
    p.direccion AS direccion_propiedad,
    u.nombre_usuario AS creado_por
FROM Contrato co
JOIN Propiedad p ON co.id_propiedad = p.id_propiedad
JOIN Cliente prop ON co.id_propietario = prop.id_cliente
LEFT JOIN Cliente inq ON co.id_inquilino = inq.id_cliente
JOIN Usuarios u ON co.id_usuario = u.id_usuario
WHERE co.id_contrato = 1;


SELECT 
    id_cliente,
    nombre,
    apellido,
    dni,
    telefono,
    email,
    direccion,
    tipo
FROM Cliente;
