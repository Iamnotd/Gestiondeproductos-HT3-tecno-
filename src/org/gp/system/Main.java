package org.gp.system;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/org/gp/view/productos.fxml"
                    )
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("Gestión de Productos");
            stage.setScene(scene);
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.show();

        } catch (IOException e) {

            System.err.println(
                    "Error al cargar productos.fxml:"
            );

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}