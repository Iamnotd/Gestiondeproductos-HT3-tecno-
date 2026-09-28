package org.gp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private static Conexion instancia;
    private Connection conexion;

    private String url;
    private String usuario;
    private String contrasena;

    // Constructor privado para aplicar el patrón Singleton
    private Conexion() {
        cargarConfiguracion();
        conectar();
    }

    // Devuelve la única instancia de Conexion
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }

        return instancia;
    }

    // Lee los datos de conexión desde config.properties
    private void cargarConfiguracion() {

        Properties propiedades = new Properties();

        try (FileInputStream archivo =
                new FileInputStream("config.properties")) {

            propiedades.load(archivo);

            url = propiedades.getProperty("db.url");
            usuario = propiedades.getProperty("db.usuario");
            contrasena = propiedades.getProperty("db.contrasena");

        } catch (IOException e) {
            System.err.println("Error al cargar config.properties:");
            System.err.println(e.getMessage());
        }
    }

    // Realiza la conexión a MySQL mediante DriverManager
    private void conectar() {

        try {

            conexion = DriverManager.getConnection(
                    url,
                    usuario,
                    contrasena
            );

            System.out.println(
                    "Conexión a MySQL realizada correctamente."
            );

        } catch (SQLException e) {

            System.err.println("Error al conectar con MySQL:");
            System.err.println(e.getMessage());
        }
    }

    // Devuelve la conexión para utilizarla desde los DAO
    public Connection getConexion() {

        try {

            if (conexion == null || conexion.isClosed()) {
                conectar();
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar la conexión: "
                    + e.getMessage()
            );
        }

        return conexion;
    }
}