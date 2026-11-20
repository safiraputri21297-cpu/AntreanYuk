
package com.mycompany.antreanyuk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"), 650, 500);

        stage.setTitle("AntreYuk - Sistem Antrean Klinik");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL lokasi = App.class.getResource(
            "/com/mycompany/antreanyuk/" + fxml + ".fxml"
        );

        System.out.println("Lokasi FXML: " + lokasi);

        if (lokasi == null) {
            throw new IOException("File FXML tidak ditemukan: " + fxml + ".fxml");
        }

        return new FXMLLoader(lokasi).load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
