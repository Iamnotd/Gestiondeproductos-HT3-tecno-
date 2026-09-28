package org.gp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private Connection conexion;

    private static final String URL =
            "jdbc:mysql://localhost:3306/db_gestion_productos"
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USUARIO = "root";

    // CAMBIA AQUÍ TU CONTRASEÑA DE MYSQL
    private static final String CONTRASENA = "CAMBIA_AQUI_TU_CONTRASEÑA";

    // Constructor privado: evita crear objetos Conexion con "new".
    private Conexion() {
        conectar();
    }

    // Devuelve la única instancia de Conexion.
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }

        return instancia;
    }

    // Realiza la conexión utilizando DriverManager.
    private void conectar() {
        try {
            conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CONTRASENA
            );

            System.out.println("Conexión a MySQL realizada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al conectar con MySQL:");
            System.err.println(e.getMessage());
        }
    }

    // Entrega la conexión para utilizarla desde los DAO.
    public Connection getConexion() {

        try {
            if (conexion == null || conexion.isClosed()) {
                conectar();
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error al verificar la conexión: " + e.getMessage()
            );
        }

        return conexion;
    }
}