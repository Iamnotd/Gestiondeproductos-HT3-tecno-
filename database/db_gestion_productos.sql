CREATE DATABASE IF NOT EXISTS db_gestion_productos
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE db_gestion_productos;

CREATE TABLE IF NOT EXISTS productos (
    id_producto INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    PRIMARY KEY (id_producto)
);

INSERT INTO productos (
    nombre,
    descripcion,
    precio,
    stock
) VALUES
('Teclado', 'Teclado USB para computadora', 150.00, 10),
('Mouse', 'Mouse óptico USB', 75.50, 20),
('Monitor', 'Monitor LED de 24 pulgadas', 1250.00, 5),
('Audifonos', 'Audifonos con microfono', 225.00, 15);

DROP PROCEDURE IF EXISTS buscar_producto_por_id;

DELIMITER //

CREATE PROCEDURE buscar_producto_por_id(
    IN p_id_producto INT
)
BEGIN

    SELECT
        id_producto,
        nombre,
        descripcion,
        precio,
        stock
    FROM productos
    WHERE id_producto = p_id_producto;

END //

DELIMITER ;

SELECT * FROM productos;

CALL buscar_producto_por_id(1);