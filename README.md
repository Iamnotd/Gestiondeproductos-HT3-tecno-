# Gestión de Productos

Proyecto académico desarrollado en JavaFX para demostrar el uso práctico de JDBC en una aplicación conectada a una base de datos MySQL.

## Tecnologías utilizadas

- Java
- JavaFX
- FXML
- Scene Builder
- Apache NetBeans
- MySQL
- JDBC
- MySQL Connector/J
- Git y GitHub

## Funcionalidades

La aplicación permite:

- Registrar productos.
- Listar productos.
- Buscar productos por ID.
- Actualizar productos.
- Eliminar productos.
- Limpiar los campos del formulario.
- Mostrar los productos almacenados en MySQL mediante un TableView.
- Mostrar mensajes de éxito, advertencia y error.

## Estructura del proyecto

```text
src/
└── org/gp/
    ├── controller/
    │   └── ProductosController.java
    ├── dao/
    │   └── ProductoDAO.java
    ├── dao/impl/
    │   └── ProductoDAOImpl.java
    ├── model/
    │   └── Producto.java
    ├── system/
    │   └── Main.java
    ├── util/
    │   └── Conexion.java
    └── view/
        └── productos.fxml

database/
└── db_gestion_productos.sql