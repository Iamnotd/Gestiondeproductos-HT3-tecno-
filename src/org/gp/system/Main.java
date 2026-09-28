package org.gp.system;

import javafx.application.Application;
import javafx.stage.Stage;
import org.gp.util.Conexion;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Conexion.getInstancia().getConexion();

        stage.setTitle("Gestión de Productos");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}