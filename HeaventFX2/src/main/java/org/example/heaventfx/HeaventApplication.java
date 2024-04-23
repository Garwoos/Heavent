package org.example.heaventfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HeaventApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventConnexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Heavent");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();

        Image applicationIcon = new Image(getClass().getResourceAsStream("/org/example/heaventfx/image/Icones/logoHeavent.png"));
        stage.getIcons().add(applicationIcon);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}